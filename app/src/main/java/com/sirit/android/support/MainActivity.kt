package com.sirit.android.support

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.sirit.android.support.lib.utils.logd
import com.tencent.mmkv.MMKV
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import retrofit2.Retrofit

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by closestKodein()

    private val inflater: LayoutInflater by instance()
    private val retrofit: Retrofit by instance()
    private val netApi: NetApi by instance()
    private val mmkv: MMKV by instance("MMKV_DEFAULT")
    private val mmkv_dir: String by instance("mmkv_dir")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logd("MainActivity", "inflater = $inflater")
        logd("MainActivity", "retrofit = $retrofit")
        logd("MainActivity", "netApi = $netApi")
        logd("MainActivity", "mmkv = $mmkv")
        logd("MainActivity", "mmkv_dir = $mmkv_dir")
    }

//    private val EMAIL = "email"
//    private val USER_POSTS = "user_posts"
//    private val PUBLIC_PROFILE = "public_profile"
//    private val AUTH_TYPE = "rerequest"
//    private val shareDialog by lazy { ShareDialog(this) }
//
//    override fun onSuccess(result: com.facebook.login.LoginResult?) {
//        runOnUiThread {
//            Toast.makeText(this, result?.accessToken.toString()
//                ?: "success", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onCancel() {
//        runOnUiThread {
//            Toast.makeText(this, "onCancel", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onError(error: com.facebook.FacebookException?) {
//        runOnUiThread {
//            Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//
//        // Set the initial permissions to request from the user while logging in
//        login_button.setReadPermissions(Arrays.asList(EMAIL, USER_POSTS, PUBLIC_PROFILE))
//        login_button.authType = AUTH_TYPE
////        login_button.setReadPermissions("email")
//        // If using in a fragment
////        login_button.setFragment(this)
//
//        // Callback registration
//        login_button.registerCallback(callbackManager, this)
//
//        btShare.setOnClickListener {
//            val content = ShareLinkContent.Builder()
//                .setContentUrl(Uri.parse("https://developers.facebook.com"))
//                .build()
//            shareDialog.show(content, ShareDialog.Mode.AUTOMATIC)
//        }
//
////        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"))
//
//    }


}
