package com.newmedia.kingsloginexample

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.newmedia.kingslogin.CallbackManager
import com.newmedia.kingslogin.KingsLoginCallback
import com.newmedia.kingslogin.KingsLoginException
import com.newmedia.kingslogin.KingsLoginManager
import com.newmedia.kingslogin.helper.KingsLogin
import com.newmedia.kingslogin.model.KcScopes
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.view.*


class LoginFragment : Fragment() {

    private val callbackManager: CallbackManager by lazy {
        CallbackManager.Factory.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.login_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.button.setFragment(this)

        custom_button.setOnClickListener {
            KingsLogin.requestPermissions(this, listOf("user"))
        }

        KingsLoginManager.getInstance().registerCallback(callbackManager,
                object : KingsLoginCallback {
                    override fun onSuccess(token: String, scopes: KcScopes) {
                        Snackbar.make(login_activity_content, "Retrieved token successfully", Snackbar.LENGTH_SHORT).show()
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

    companion object {

        fun newInstance(): Fragment = LoginFragment()

    }
}