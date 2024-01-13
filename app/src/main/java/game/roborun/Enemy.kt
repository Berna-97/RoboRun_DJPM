package game.roborun

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF

class Enemy(context: Context, private val screenWidth: Int, private val screenHeight: Int, private val position: Int) {

    private val blockWidth = 130f
    private val blockHeight = 60f
    private var baseScale = 3.0f

    var x = 3000f
    var y = 1150f
    var dX = 0
    var dY = 0

    private val speed = 20


    val detectCollision: RectF

    private lateinit var enemyBitmap: Bitmap

    var shouldBeRemoved = false

    init {

        val blockX = screenWidth.toFloat()  // Posição inicial à direita da tela
        val blockY = screenHeight.toFloat() - blockHeight // Posição no chão
        detectCollision = RectF(blockX, blockY, blockX + blockWidth, blockY + blockHeight)

        // Carregar a imagem do inimigo usando BitmapFactory
        enemyBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.enemy) // substitua pelo seu recurso enemy.png
        enemyBitmap = Bitmap.createScaledBitmap(enemyBitmap, (blockWidth * baseScale).toInt(), (blockHeight * baseScale).toInt(), false)
    }

    fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(enemyBitmap, detectCollision.left, detectCollision.top, null)
    }

    fun update() {

        // Atualiza a posição do jogador
        x -= speed

        // Atualiza a área de detecção de colisão
        detectCollision.left = x
        detectCollision.top = y
        detectCollision.right = x + enemyBitmap.width
        detectCollision.bottom = y + enemyBitmap.height
    }
}
