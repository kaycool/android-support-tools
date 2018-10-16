package com.sirit.android.support.lib.widget.dialog

import android.content.Context
import android.os.Bundle

/**
 * @author kai.w
 * @des  $des
 */
class BottomDialog(builder: Builder) : BaseDialog(builder.ctx) {

    private lateinit var mDialogTitle: String
    private lateinit var mDialogSubTitle: String
    private lateinit var mDialogSContent: String
    private lateinit var mDialogLeftBtnRes: String
    private lateinit var mDialogRightBtnRes: String


    init {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    companion object {
        class Builder(val ctx: Context) {

            fun build(): BottomDialog {
                return BottomDialog(this)
            }
        }
    }

}