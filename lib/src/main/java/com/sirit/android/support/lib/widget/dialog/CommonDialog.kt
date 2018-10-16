package com.sirit.android.support.lib.widget.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DialogTitle

/**
 * @author kai.w
 * @des    基础弹窗dialog
 */
class CommonDialog(builder: Builder) : BaseDialog(builder.ctx) {

    private val mDialogTitle: String? by lazy { builder.mDialogTitle }
    private val mDialogSubTitle: String? by lazy { builder.mDialogSubTitle }
    private val mDialogContent: String? by lazy { builder.mDialogContent }
    private val mDialogLeftBtnRes: String? by lazy { builder.mDialogLeftBtnRes }
    private val mDialogRightBtnRes: String? by lazy { builder.mDialogRightBtnRes }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mDialogTitle?.let {
            //todo view.visible
        }?:kotlin.run {
            //todo view.gone
        }


        mDialogSubTitle?.let {


        }?:kotlin.run {

        }


        mDialogContent?.let {


        }?:kotlin.run {

        }


        mDialogLeftBtnRes?.let {


        }?:kotlin.run {

        }


        mDialogRightBtnRes?.let {


        }?:kotlin.run {

        }
    }


    companion object {
        class Builder(val ctx: Context) {
            var mDialogTitle: String? = null
            var mDialogSubTitle: String? = null
            var mDialogContent: String? = null
            var mDialogLeftBtnRes: String? = null
            var mDialogRightBtnRes: String? = null

            fun setDialogTitle(dialogTitle: String): Builder {
                this.mDialogTitle = dialogTitle
                return this
            }

            fun setDialogSubTitle(dialogSubTitle: String): Builder {
                this.mDialogSubTitle = dialogSubTitle
                return this
            }

            fun setDialogContent(dialogContent: String): Builder {
                this.mDialogContent = dialogContent
                return this
            }

            fun setDialogLeftBtnRes(dialogLeftBtnRes: String): Builder {
                this.mDialogLeftBtnRes = dialogLeftBtnRes
                return this
            }

            fun setDialogRightBtnRes(dialogRightBtnRes: String): Builder {
                this.mDialogRightBtnRes = dialogRightBtnRes
                return this
            }

            fun build(): CommonDialog {
                return CommonDialog(this)
            }
        }
    }

}
