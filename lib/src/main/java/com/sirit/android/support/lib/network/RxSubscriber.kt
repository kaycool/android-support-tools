package com.sirit.android.support.lib.network

import android.app.Activity
import rx.Subscriber

/**
 * @author zou.sq
 * 封装Subscriber
 */
class RxSubscriber<T>(private val mActivity: Activity?) : Subscriber<ResultBean<T>>() {

    override fun onCompleted() {

    }

    override fun onError(e: Throwable) {
        onErrorAction(ResultBean.RESULT_CODE_NET_ERROR, null)
        e.printStackTrace()
    }

    override fun onNext(t: ResultBean<T>) {
        when (t.code) {
            ResultBean.RESULT_CODE_SUCCESS -> onNextAction(t.data)
            else -> onErrorAction(t.code, t.data)
        }
    }

    fun onNextAction(t: T?) {

    }

    fun onErrorAction(code: Int, t: T?) {
        onErrorAction(code)
    }

    fun onErrorAction(code: Int) {

    }

}