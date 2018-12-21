package com.sirit.android.support.lib.widget.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface

/**
 * @author kai.w
 * @des  $des
 */
open abstract class BaseDialog : Dialog {

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener)
}


interface DialogCallback {

    fun onConfirmClicked()

    fun onCancelClicked()
}