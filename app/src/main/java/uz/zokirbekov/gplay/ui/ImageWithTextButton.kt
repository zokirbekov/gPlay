package uz.zokirbekov.gplay.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import uz.zokirbekov.gplay.R

class ImageWithTextButton(context: Context, attr:AttributeSet) : FrameLayout(context, attr) {

    lateinit var imageView:ImageView
    lateinit var textView:TextView

    init {
        initViews()
        setupAttributes(attr)
    }

    private fun setupAttributes(attributeSet: AttributeSet)
    {
        val typedArray = context.theme.obtainStyledAttributes(attributeSet, R.styleable.ImageWithTextButton, 0, 0)

        textView.text = typedArray.getString(R.styleable.ImageWithTextButton_text)
        imageView.setImageResource(typedArray.getInteger(R.styleable.ImageWithTextButton_image,0))

    }

    private fun initViews()
    {
        var view = LayoutInflater.from(context).inflate(R.layout.image_with_text_button,this,false)
        imageView = view.findViewById<ImageView>(R.id.button_image)
        textView = view.findViewById<TextView>(R.id.button_text)
    }
}