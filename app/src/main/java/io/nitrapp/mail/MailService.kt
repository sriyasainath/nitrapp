package io.nitrapp.mail

import android.app.IntentService
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Base64
import android.util.Log
import android.widget.Toast
import io.nitrapp.R
import io.nitrapp.mail.util.ReceiveCookiesInterceptor
import io.nitrapp.mail.util.SendCookiesInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Main class to interact with mail system
class MailService : Service() {

    private val mailApi: MailApi
    private lateinit var email: String

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ReceiveCookiesInterceptor(this))
            .addInterceptor(SendCookiesInterceptor(this))
            .build()

        mailApi = Retrofit.Builder()
            .baseUrl(getString(R.string.mailserver_url))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MailApi::class.java)
    }

    override fun onBind(intent: Intent?): IBinder? {
        // TODO: Connect to an activity
        return null
    }

    // For generating an auth cookie
    fun authenticate(
        roll: String,
        password: String,
        onSuccess: () -> Unit // Callback used in case of success
    ) {
        email = roll + "@" + getString(R.string.mailserver_domain)

        val authHeader = "Basic " +
                Base64.encodeToString("$roll:$password".toByteArray(), Base64.NO_WRAP)

        mailApi.getToken(email, authHeader).enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                onSuccess()
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@MailService, "Authentication failed", Toast.LENGTH_LONG)
            }
        })
    }
}