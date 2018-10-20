package com.sirit.android.support.lib.widget.editor

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.SCALE_TYPE_START
import com.sirit.android.support.lib.extention.heightPixels
import com.sirit.android.support.lib.extention.requestKeyboard
import com.sirit.android.support.lib.extention.widthPixels
import java.io.File

/**
 * @author kai.w
 * @des  $des
 */
class EditorLayout : LinearLayout {

    val allView: MutableList<View>
        get() = mutableListOf<View>().apply {
            for (i in 0 until childCount) {
                this.add(getChildAt(i))
            }
        }

    val currentFocusEditor: EditText?
        get() = focusedChild as? EditText?

    val content: String
        get() = StringBuilder().apply {
            allView.forEach {
                when (it) {
                    is EditText -> {
                        this.append(it.text)
                    }
                    is ImageView -> {
                        this.append("图片")
                    }
                    else -> {
                    }
                }
            }
        }.toString()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        orientation = LinearLayout.VERTICAL
    }

    fun initEditor(editorHint: String = "") {
        addEditText()
        currentFocusEditor?.hint = editorHint
    }


    fun addEditText() {
        val index = getNextIndex()
        val editText = AppCompatEditText(context)

        addView(editText, index)
//        editText.addTextChangedListener(TextWatchObject)
        editText.requestKeyboard()
    }

    fun addImage(filePath: String) {
        val index = getNextIndex()
        val imageView = SubsamplingScaleImageView(context)
        addView(imageView, index)
        Glide.with(context)
            .downloadOnly()
            .load(filePath)
            .apply(RequestOptions().centerCrop())
            .into(object : SimpleTarget<File>() {
                override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                    if (resource.exists() && resource.isFile) {
                        val option = BitmapFactory.Options()
                        option.inJustDecodeBounds = true
                        BitmapFactory.decodeFile(resource.absolutePath, option)
                        val width = option.outWidth.toFloat()
                        val height = option.outHeight.toFloat()
                        if ((width / height) < (context.widthPixels.toFloat() / context.heightPixels.toFloat())) {
                            imageView.setMinimumScaleType(SCALE_TYPE_START)
                        }

                        if (!imageView.isImageLoaded) {
                            imageView.setImage(ImageSource.uri(resource.absolutePath))
                        }
                    }
                }
            })
        addEditText()
    }


    private fun getNextIndex(): Int {
        var index = childCount
        val v = focusedChild
        if (v != null) {
            index = indexOfChild(v) + 1
        }
        return index
    }

}

