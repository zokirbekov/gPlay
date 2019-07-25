package uz.zokirbekov.gplay.utils

import android.animation.ObjectAnimator
import android.os.Handler
import android.view.View
import android.widget.ImageView

class AnimationHelper {

    companion object {

        val DURATION = 500L

        fun fadeIn(v: View)
        {
            v.visibility = View.VISIBLE
            var objectAnimator = ObjectAnimator.ofFloat(v,View.ALPHA,0f,1f)
            objectAnimator.duration = DURATION
            objectAnimator.start()
        }

        fun fadeOut(v: View)
        {
            var objectAnimator = ObjectAnimator.ofFloat(v,View.ALPHA,1f,0f)
            objectAnimator.duration = DURATION
            objectAnimator.start()
            Handler().postDelayed({v.visibility = View.INVISIBLE}, DURATION + 10)
        }

        fun animateDrawLine(imageView:ImageView,startX:Float, startY:Float, endX:Float, endY:Float)
        {
            val handler = Handler()

            val stepX = (endX - startX)/100
            val stepY = (endY - startY)/100

            var currentX = startX
            var currentY = startY

            for (i in 0..99)
            {
                handler.postDelayed(
                        {
                            imageView.setImageBitmap(Drawer.drawLine(imageView,startX,startY,currentX,currentY))
                            imageView.invalidate()
                            currentX += stepX
                            currentY += stepY
                        },(10*i).toLong())
            }
        }

    }
}