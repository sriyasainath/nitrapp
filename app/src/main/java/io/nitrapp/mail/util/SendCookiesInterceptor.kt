package io.nitrapp.mail.util

import android.content.Context
import io.nitrapp.R
import okhttp3.Interceptor
import okhttp3.Response

// Reads and sends cookies from SharedPref
class SendCookiesInterceptor(
    private val context: Context
) : Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val cookies = context.getSharedPreferences(
            context.getString(R.string.pref_file), Context.MODE_PRIVATE
        )!!.getStringSet(
            context.getString(R.string.pref_cookies), HashSet<String>()
        )!!

        cookies.forEach { builder.addHeader("Cookie", it) }

        return chain.proceed(builder.build())
    }
}