package io.nitrapp

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.nitrapp.mail.MailService
import io.nitrapp.ui.main.MainFragment

// Primary activity for the app
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val cookies = getSharedPreferences(
            getString(R.string.pref_file), Context.MODE_PRIVATE
        ).getStringSet(getString(R.string.pref_cookies), null)

        startService(Intent(this, MailService::class.java))
        // TODO: store a reference to the service

        // Opens LoginActivity if auth token is not present
        if(cookies==null || !cookies.contains(getString(R.string.auth_token))) {
            startActivity(Intent(this, LoginActivity::class.java))
            finishActivity(0)
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

}
