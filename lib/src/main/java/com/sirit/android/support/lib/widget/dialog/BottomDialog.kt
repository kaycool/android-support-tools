package com.sirit.android.support.lib.widget.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.sirit.android.support.lib.R
import com.sirit.android.support.lib.widget.dialog.adapter.BottomSheetAdapter
import kotlinx.android.synthetic.main.dialog_bottom_sheet.*

/**
 * @author kai.w
 * @des  $des
 */
class BottomDialog:BaseDialog {

    constructor(context: Context) : this(context,R.style.common_dialog)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.BOTTOM)
//         window?.setWindowAnimations(R.style.anim_dialog);
        window?.setBackgroundDrawable(ColorDrawable(0))
        window?.setDimAmount(0f)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        setCancelable(true)

        setContentView(R.layout.dialog_bottom_sheet)

        recycleBottomSheet.adapter = BottomSheetAdapter()



    }



}