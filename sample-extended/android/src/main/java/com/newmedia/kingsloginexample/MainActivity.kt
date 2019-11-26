package com.newmedia.kingsloginexample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.newmedia.kingslogin.model.CreateFolderRequest
import com.newmedia.kingslogin.model.CreateFolderResponse
import kotlinx.android.synthetic.main.main_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val tokenDatabase: TokenDatabase by lazy {
        TokenDatabase(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        create_directory_button.setOnClickListener {
            val token = tokenDatabase.getAuthorizationToken()

            val request = CreateFolderRequest.newBuilder()
                    .setName("Name")
                    .build()

            Services.kingscloud.createDirectory(token, request).enqueue(object : Callback<CreateFolderResponse> {
                override fun onResponse(call: Call<CreateFolderResponse>, response: Response<CreateFolderResponse>) {
                    Snackbar.make(main_activity_content, "Directory created!", Snackbar.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<CreateFolderResponse>, t: Throwable) {
                    Snackbar.make(main_activity_content, "Error while creating directory", Snackbar.LENGTH_SHORT).show()
                }
            })
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
