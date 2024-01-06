package game.roborun

import android.graphics.Bitmap
import android.graphics.Canvas

class Player(private val bitmap: Bitmap, private var x: Int, private var y: Int) {

    private val gravity = 2
    private var yVelocity = 0

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, x.toFloat(), y.toFloat(), null)
    }

    fun update(groundLevel: Int) {
        yVelocity += gravity
        y += yVelocity

        // Check for collision with the ground
        if (y > groundLevel - bitmap.height) {
            y = groundLevel - bitmap.height
            yVelocity = 0
        }
    }

    fun jump() {
        yVelocity = -30
    }
}

