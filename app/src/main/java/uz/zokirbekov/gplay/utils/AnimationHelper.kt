package uz.zokirbekov.gplay.utils

import android.animation.ObjectAnimator
import android.os.Handler
import android.view.View

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

    }
}