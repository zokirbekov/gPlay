package uz.zokirbekov.gplay.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class Drawer {

    companion object {

        fun drawLine(v: View, startX:Float, startY:Float, endX:Float, endY:Float) : Bitmap
        {
            val bitmap = Bitmap.createBitmap(
                    v.width,
                    v.height,
                    Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(bitmap)
            val paint = Paint()

            paint.color = Color.LTGRAY
            paint.strokeWidth = 40f
            paint.isAntiAlias = true

            canvas.drawColor(Color.TRANSPARENT)

            canvas.drawLine(startX,startY,endX,endY,paint)
            //canvas.drawLine(0f,0f,100f,100f,paint)

            return bitmap
        }

    }
}