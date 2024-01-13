package game.roborun

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect

class Background(private val context: Context, private val screenWidth: Int, private val screenHeight: Int) {

    private var x1 = 0
    private var x2 = screenWidth
    private val speed = 800
    private val image: Bitmap =
        BitmapFactory.decodeResource(context.resources, R.drawable.background)
    private val image2: Bitmap =
        BitmapFactory.decodeResource(context.resources, R.drawable.background2)
    private var distanceTraveled = 0

    fun update(dt: Float) {
        x1 -= (speed * dt).toInt()
        x2 -= (speed * dt).toInt()

        if (x1 <= -image.width) {
            x1 = x2 + image.width
        }

        if (x2 <= -image.width) {
            x2 = x1 + image.width
        }

        distanceTraveled += (speed * dt).toInt()
        //usa isto para o score, i guess
    }

    fun draw(canvas: Canvas) {
        val rect1 = Rect(x1, 0, x1 + image.width, screenHeight)
        canvas.drawBitmap(image2, null, rect1, null)

        val rect2 = Rect(x2, 0, x2 + image.width, screenHeight)
        canvas.drawBitmap(image, null, rect2, null)
    }
}


