package com.sirit.android.support.lib.widget.editor

import android.content.Context
import android.graphics.BitmapFactory
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
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

    fun getNextIndex(): Int {
        var index = childCount
        val v = focusedChild
        if (v != null) {
            index = indexOfChild(v) + 1
        }
        return index
    }


    interface EditorAction {

        fun initEdit(editorHint: String = "")

        fun addImage()

        fun addEdit()
    }

}

