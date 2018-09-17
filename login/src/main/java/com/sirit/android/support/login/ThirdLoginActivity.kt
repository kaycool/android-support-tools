package com.sirit.android.support.login

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import java.util.*


/**
 * @author kai.w
 * @des  $des
 */
abstract class ThirdLoginActivity : AppCompatActivity(), FacebookCallback<LoginResult> {
    private val callbackManager by lazy { CallbackManager.Factory.create() }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        LoginManager.getInstance().registerCallback(callbackManager, this)
//        val linkContent = ShareLinkContent.Builder()
//            .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
//            .build()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


    protected fun isLoginToFb(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null && !accessToken.isExpired
    }


    protected fun toFbLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"))
    }
}