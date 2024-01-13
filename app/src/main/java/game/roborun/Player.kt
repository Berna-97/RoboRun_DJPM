package game.roborun

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import android.util.Log

class Player() {

    private val gravity = 3
    private var yVelocity = 2
    private var groundLevel = 1350
    private var canJump = true

    private var baseScale = 7.0f


    var x = 0f
    var y = 0f
    var dX = 0
    var dY = 0

    lateinit var playerBitmap: Bitmap
    lateinit var detectCollision: RectF

    private val circleDim = 50f

    var shouldBeRemoved = false

    constructor(context: Context, width: Int, height: Int, x: Float, y: Float) : this() {
        this.x = x
        this.y = y

        dY = height
        dX = width

        // Agora, o contexto está disponível para carregar o bitmap
        playerBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.player)
        playerBitmap = Bitmap.createScaledBitmap(playerBitmap, (circleDim * baseScale).toInt(), (circleDim * baseScale).toInt(), false)

        detectCollision = RectF(x, y, playerBitmap.width.toFloat(), playerBitmap.height.toFloat())
    }

    init {
        // Aqui, o contexto ainda não está disponível, então não podemos carregar o bitmap
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(playerBitmap, x, y, null)
    }

    fun update() {
        // Aplicar gravidade
        yVelocity += gravity
        y += yVelocity

        // Verificar colisão com o chão
        if (y > groundLevel - playerBitmap.height) {
            y = (groundLevel - playerBitmap.height).toFloat()
            yVelocity = 0
            canJump = true  // Permite o jogador pular novamente quando atinge o chão
        }
    }

    fun jump() {
        if (canJump) {
            yVelocity = -50
            canJump = false
        }
    }

}

