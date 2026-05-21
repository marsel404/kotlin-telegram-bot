package com.github.kotlintelegrambot.network.proxy

import okhttp3.Authenticator
import okhttp3.Credentials
import okhttp3.Response
import okhttp3.Route

internal fun httpProxyAuthenticator(credentials: ProxyCredentials): Authenticator {
    val proxyAuthorization = Credentials.basic(credentials.username, credentials.password)
    return Authenticator { _: Route?, response: Response ->
        response.request.newBuilder()
            .header("Proxy-Authorization", proxyAuthorization)
            .build()
    }
}
