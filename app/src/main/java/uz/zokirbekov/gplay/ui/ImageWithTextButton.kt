package uz.zokirbekov.gplay.ui

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

class ImageWithTextButton(context: Context, attr:AttributeSet) : FrameLayout(context, attr) {

    lateinit var image:ImageView
    lateinit var text:TextView

}