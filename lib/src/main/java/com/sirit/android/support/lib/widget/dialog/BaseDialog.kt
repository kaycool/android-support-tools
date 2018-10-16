package com.sirit.android.support.lib.widget.dialog

import android.app.Dialog
import android.content.Context

/**
 * @author kai.w
 * @des  $des
 */
open class BaseDialog(ctx: Context) : Dialog(ctx) {
}


interface DialogCallback {

    fun onConfirmClicked()

    fun onCancelClicked()
}