package game.roborun

import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.KeyEvent

class Player(originalBitmap: Bitmap, private var x: Int, private var y: Int, private val enemy: Enemy) {

    private val gravity = 2
    private var yVelocity = 2
    private var groundLevel = 100
    private var canJump = true

    private var scale = 0.3f  // Ajuste a escala conforme necessário

    private val bitmap: Bitmap

    init {
        // Obtém a largura e altura da imagem original do player
        val width = (originalBitmap.width * scale).toInt()
        val height = (originalBitmap.height * scale).toInt()

        // Redimensiona a imagem do player
        bitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, true)
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, x.toFloat(), y.toFloat(), null)
    }

    fun update(enemies: List<Enemy.EnemyInstance>) {
        yVelocity += gravity
        y += yVelocity
        groundLevel = 1360

        // Verifica a colisão com o chão
        if (y > groundLevel - bitmap.height) {
            y = groundLevel - bitmap.height
            yVelocity = 0
            canJump = true  // Permite o jogador pular novamente quando atinge o chão
        }
        for (enemyInstance in enemy.getEnemies()) {
            if (x < enemyInstance.x + enemyInstance.width &&
                x + bitmap.width > enemyInstance.x &&
                y < enemyInstance.y + enemyInstance.height &&
                y + bitmap.height > enemyInstance.y
            ) {
                Log.d("Player", "morreu")
            }
        }

    }

    fun jump() {
        if (canJump) {
            yVelocity = -30
            canJump = false
        }

    }
}
