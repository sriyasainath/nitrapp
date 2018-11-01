package io.nitrapp.mail

import io.nitrapp.mail.types.MailList
import retrofit2.Call
import retrofit2.http.*

// Connect to REST API of Zimbra
interface MailApi {

    // To retrieve ZM_AUTH_TOKEN
    @HEAD("/home/{email}")
    fun getToken(@Path("email") email: String,
                 @Header("authorization") credentials: String,
                 @Query("auth") auth: String = "sc") : Call<Void>

    // To get inbox mails
    @GET("home/{email}/inbox")
    fun getInbox(@Path("email") email: String,
                 @Query("fmt") format: String = "json") : Call<MailList>

    // To get sent mails
    @GET("home/{email}/sent")
    fun getSent(@Path("email") email: String,
                @Query("fmt") format: String = "json") : Call<MailList>
}