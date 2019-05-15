package com.sirit.android.support.lib.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.arch.lifecycle.LifecycleRegistry



/**
 * @author kai.w
 * @des  $des
 */
open abstract class BaseActivity : AppCompatActivity(),LifecycleOwner {
    private lateinit var lifecycleObserverDemo: LifecycleObserverDemo
//    private lateinit var mLifecycleRegistry: LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

//        mLifecycleRegistry = LifecycleRegistry(this)
//        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        lifecycleObserverDemo = LifecycleObserverDemo()
        lifecycleObserverDemo.setLifecycle(lifecycle)
    }

}