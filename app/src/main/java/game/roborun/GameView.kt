package game.roborun

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.SurfaceHolder
import android.view.SurfaceView


class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private var gameThread: GameThread? = null
    private var background: Bitmap
    private var player: Player
    private val groundLevel: Int


    init {
        holder.addCallback(this)
        gameThread = GameThread(holder, this)

        background = BitmapFactory.decodeResource(resources, R.drawable.background)
        player = Player(BitmapFactory.decodeResource(resources, R.drawable.player), 100, 100)
        groundLevel = height - 100  // Adjust the ground level as needed

        isFocusable = true
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
        canvas.drawBitmap(background, 0f, -1130f, null)
        player.draw(canvas)



    }

    fun update() {
        player.update(groundLevel)
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


