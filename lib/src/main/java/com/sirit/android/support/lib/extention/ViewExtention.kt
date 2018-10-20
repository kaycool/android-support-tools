package com.sirit.android.support.lib.extention

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * @author kai.w
 * @des  $des
 */


fun EditText.requestKeyboard(){
    this.post {
        this.requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
    }
}