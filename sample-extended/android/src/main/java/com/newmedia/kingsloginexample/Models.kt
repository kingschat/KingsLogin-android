package com.newmedia.kingsloginexample

data class AccessTokenRequest(val grant_type: String, val client_id: String, val code: String)

data class AccessTokenResponse(val access_token: String, val expires_in_millis: String, val refresh_token: String)
