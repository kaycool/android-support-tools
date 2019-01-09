package com.sirit.android.support.lib.extention

import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber


/**
 * @author kai.w
 *      ${DES}
 */
/**
 * 统一线程切换方法
 *
 * @param <T> Observable
 * @return Transformer对象
</T> */
fun <T> ioMain(): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> io2MainObservable(): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
}

fun <T> applySchedulers(): ObservableTransformer<T, T> {
    return io2MainObservable()
}

fun <T> io2MainFlowable(): FlowableTransformer<T, T> {
    return FlowableTransformer { upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
}

fun <T> applyFlowSchedulers(): FlowableTransformer<T, T> {
    return io2MainFlowable()
}


fun unsubscribe(disposable: Disposable?) {
    disposable?.also {
        if (!it.isDisposed) {
            it.dispose()
        }
    }
}