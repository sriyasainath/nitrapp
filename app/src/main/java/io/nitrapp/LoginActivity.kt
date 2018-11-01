package io.nitrapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_signin.setOnClickListener { authenticate() }
    }

    fun authenticate() {
        val roll = text_rollno.text.toString()
        val password = text_password.text.toString()

        // TODO: authenticate using MailService
    }
}
