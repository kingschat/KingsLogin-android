package com.newmedia.kingsloginexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.newmedia.kingslogin.CallbackManager
import com.newmedia.kingslogin.KingsLoginCallback
import com.newmedia.kingslogin.KingsLoginException
import com.newmedia.kingslogin.KingsLoginManager
import com.newmedia.kingslogin.model.KcScopes
import kotlinx.android.synthetic.main.login_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val callbackManager: CallbackManager by lazy {
        CallbackManager.Factory.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        KingsLoginManager.getInstance().registerCallback(callbackManager,
                object : KingsLoginCallback {
                    override fun onSuccess(token: String, scopes: KcScopes) {
                        val accessTokenRequest = AccessTokenRequest(
                                grant_type = "code",
                                client_id = resources.getString(R.string.client_id),
                                code = token
                        )

                        Services.kingschat.createToken(accessTokenRequest).enqueue(object : Callback<AccessTokenResponse> {
                            override fun onResponse(call: Call<AccessTokenResponse>, response: Response<AccessTokenResponse>) {
                                Snackbar.make(login_activity_content, "Fetched access token", Snackbar.LENGTH_SHORT).show()
                            }

                            override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) {
                                Snackbar.make(login_activity_content, "Error while fetching access token", Snackbar.LENGTH_SHORT).show()
                            }
                        })
                    }

                    override fun onCancel() {
                        Snackbar.make(login_activity_content, "Canceled", Snackbar.LENGTH_SHORT).show()
                    }

                    override fun onError(error: KingsLoginException) {
                        Snackbar.make(login_activity_content, "Error ${error.message}", Snackbar.LENGTH_SHORT).show()
                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (callbackManager.onActivityResult(requestCode, resultCode, data))
        else
            super.onActivityResult(requestCode, resultCode, data)
    }
}
