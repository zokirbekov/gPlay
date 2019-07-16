package uz.zokirbekov.gplay.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import uz.zokirbekov.gplay.R
import android.graphics.drawable.Drawable



class ImageWithTextButton : LinearLayout {

    lateinit var imageView:ImageView
    lateinit var textView:TextView

    constructor(context: Context) : super(context){
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet):    super(context, attrs){
        init(context)
        setupAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?,    defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
        setupAttributes(attrs)
    }


    private fun init(context:Context)
    {

        inflate(context,R.layout.image_with_text_button,this)
        imageView = this.findViewById<ImageView>(R.id.button_image)
        textView = this.findViewById<TextView>(R.id.button_text)
    }

    private fun setupAttributes(attributeSet: AttributeSet?)
    {
        val typedArray = context.theme.obtainStyledAttributes(attributeSet, R.styleable.ImageWithTextButton, 0, 0)

        textView.text = typedArray.getString(R.styleable.ImageWithTextButton_text)
        val drawable = typedArray.getDrawable(R.styleable.ImageWithTextButton_image)
        imageView.setImageDrawable(drawable)

    }
}