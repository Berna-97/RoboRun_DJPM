package game.roborun

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private var canJump = true
    private var gameThread: GameThread? = null
    private var background: Background
    private var player: Player
    private val enemy: Enemy = Enemy.getInstance()

    init {
        holder.addCallback(this)
        gameThread = GameThread(holder, this)

        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels

        // Inicializa o background
        background = Background(context, screenWidth, screenHeight)

        // Inicializa o player
        player = Player(BitmapFactory.decodeResource(resources, R.drawable.player), 100, -500, enemy)

        isFocusable = true

        isFocusable = true
        setOnKeyListener { view, keyCode, event ->
            when (keyCode) {
                KeyEvent.KEYCODE_SPACE-> {
                    if (canJump) {
                        jumpPlayer()

                    }
                    return@setOnKeyListener true  // Indica que a tecla foi processada
                }
                else -> return@setOnKeyListener false
            }
        }

    }


    override fun surfaceCreated(holder: SurfaceHolder) {
        gameThread?.start()

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}


    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                gameThread?.join()
                retry = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        background.draw(canvas)
        player.draw(canvas)
        enemy.draw(canvas)
    }

    fun update() {
        player.update(enemy.getEnemies())
        background.update(0.016f)  // 0.016 segundos (aproximadamente 60 FPS)
        enemy.update(0.016f)
    }

    fun jumpPlayer() {
        player.jump()
    }

    fun resume() {
        gameThread?.setRunning(true)
    }

    fun pause() {
        gameThread?.setRunning(false)
    }
}
