package io.nitrapp.mail.util

import android.content.Context
import io.nitrapp.R
import okhttp3.Interceptor
import okhttp3.Response

// Receives and writes cookies to SharedPref
class ReceiveCookiesInterceptor(
    private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if(! response.header("Set-Cookie").isNullOrEmpty()) {
            val sharedPref = context.getSharedPreferences(
                context.getString(R.string.pref_file), Context.MODE_PRIVATE
            )!!

            val cookies = sharedPref.getStringSet(
                context.getString(R.string.pref_cookies), HashSet<String>()
            )!!

            response.headers("Set-Cookie").forEach { cookies.add(it) }

            sharedPref.edit().putStringSet(
                context.getString(R.string.pref_cookies), cookies
            ).apply()
        }

        return response
    }
}