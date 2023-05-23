package com.nicolascastilla.data.network.Deezer

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

object DeezerAuthManager {

private const val AUTH_URL = "https://connect.deezer.com/oauth/auth.php"
private const val TOKEN_URL = "https://connect.deezer.com/oauth/access_token.php"

private const val CLIENT_ID = "607304"
private const val CLIENT_SECRET = "3947dcc6b8daadcb6f058f8ac0689134"
private const val REDIRECT_URI = "http://nicolasChallenge5.com"

fun getAuthUrl(): String {
    val scope = "basic_access,email" // Cambia los permisos según tus necesidades
    return "$AUTH_URL?app_id=$CLIENT_ID&redirect_uri=$REDIRECT_URI&perms=$scope"
}

suspend fun exchangeCodeForToken(code: String): String {
    val requestBody = FormBody.Builder()
        .add("app_id", CLIENT_ID)
        .add("secret", CLIENT_SECRET)
        .add("code", code)
        .add("output", "json")
        .build()

    val request = Request.Builder()
        .url(TOKEN_URL)
        .post(requestBody)
        .build()

    val client = OkHttpClient()
    val response = client.newCall(request).execute()
    val responseData = response.body?.string() ?: ""

    return parseTokenResponse(responseData)
}

    private fun parseTokenResponse(responseData: String): String {
        val json = JSONObject(responseData)
        return json.optString("access_token", "")
    }
}