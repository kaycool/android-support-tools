package com.sirit.android.support.lib.base

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.Lifecycle
import android.support.annotation.NonNull


/**
 * @author kai.w
 * @des  $des
 */
// 如果你使用的是 Java 8, 那么只需实现 DefaultLifecycleObserver 接口。
// 为此你需要引入 lifecycle:common-java8 库
class LifecycleObserverDemo : DefaultLifecycleObserver {
    // 在这里注册回调
    fun setLifecycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    override fun onCreate(@NonNull owner: LifecycleOwner) {

    }

    override fun onStart(@NonNull owner: LifecycleOwner) {

    }

    override fun onResume(@NonNull owner: LifecycleOwner) {

    }

    override fun onPause(@NonNull owner: LifecycleOwner) {

    }

    override fun onStop(@NonNull owner: LifecycleOwner) {

    }

    override fun onDestroy(@NonNull owner: LifecycleOwner) {

    }
}