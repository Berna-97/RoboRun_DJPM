package game.roborun

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView : SurfaceView, Runnable {
    var isPlaying = false
    var gameThread: Thread? = null
    var surfaceHolder: SurfaceHolder
    var paint: Paint
    var canvas: Canvas? = null

    var player: Player
    var blocks: MutableList<Enemy> = mutableListOf()
    var background: Background

    private var enemyTimer: Long = 0L
    private var lastEnemyTime: Long = 0L
    constructor(context: Context, width: Int, height: Int) : super(context) {
        surfaceHolder = holder
        paint = Paint()


        player = Player(context, width, height, 100f, 1120f)
        background = Background(context, width, height)
    }

    override fun run() {
        while (isPlaying) {
            update()
            draw()
            control()
        }
    }

    fun update() {
        player.update()

        for (block in blocks) {
            block.update()

            if (player.detectCollision.intersect(block.detectCollision)) {
                player.shouldBeRemoved = true
                block.shouldBeRemoved = true
                // Você pode adicionar aqui a lógica para tratar a colisão entre jogador e inimigo
            }
        }

        blocks = blocks.filter { !it.shouldBeRemoved }.toMutableList()

        background.update(0.05f) // 0.017f é um valor de tempo fictício (delta time) para atualização do fundo
        generateEnemies()
    }

    private fun generateEnemies() {
        val currentTime = System.currentTimeMillis()

        // Gera um novo inimigo a cada intervalo aleatório entre 1 e 3 segundos
        if (currentTime - lastEnemyTime > enemyTimer) {
            val randomInterval = (1000 * (3 + Math.random() * 2)).toLong() // Intervalo aleatório entre 1 e 3 segundos
            lastEnemyTime = currentTime
            enemyTimer = randomInterval

            // Cria um novo inimigo à direita da tela
            blocks.add(Enemy(context, width, height, 7)) // Ajuste a posição inicial conforme necessário
        }
    }

    fun draw() {
        if (surfaceHolder.surface.isValid) {
            canvas = surfaceHolder.lockCanvas()
            canvas?.drawColor(Color.BLACK)

            background.draw(canvas!!)

            for (block in blocks) {
                if (!block.shouldBeRemoved) {
                    block.draw(canvas)
                }
            }

            player.draw(canvas!!)

            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    fun control() {
        Thread.sleep(17)
    }

    fun resume() {
        gameThread = Thread(this)
        gameThread?.start()
        isPlaying = true
    }

    fun pause() {
        isPlaying = false
        gameThread?.join()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                player.jump()
                return true
            }
            MotionEvent.ACTION_UP -> {
                return true
            }
        }
        return false
    }
}
