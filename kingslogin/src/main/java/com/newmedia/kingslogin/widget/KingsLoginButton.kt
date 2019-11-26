package com.newmedia.kingslogin.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.newmedia.kingslogin.R
import com.newmedia.kingslogin.helper.KingsLogin.requestPermissions
import kotlinx.android.synthetic.main.kingslogin_button_view.view.*

class KingsLoginButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.kingslogin_button_view, this, true)
    }

    private var fragment : Fragment? = null

    private var scopes: List<String> = listOf("user")

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        kings_login_button
                .setOnClickListener {
                    if (fragment != null) {
                        requestPermissions(fragment!!, scopes)
                    } else {
                        requestPermissions(context, null , scopes)
                    }
                }
    }

    fun setFragment(fragment: Fragment) {
        this.fragment = fragment
    }

    fun setScopes(scopes: List<String>) {
        this.scopes = scopes
    }

}