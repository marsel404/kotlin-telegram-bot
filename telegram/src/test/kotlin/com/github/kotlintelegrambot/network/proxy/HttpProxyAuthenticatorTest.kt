package com.github.kotlintelegrambot.network.proxy

import okhttp3.Credentials
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class HttpProxyAuthenticatorTest {
    @Test
    fun `adds proxy authorization header on 407 response`() {
        val request = Request.Builder().url("https://api.telegram.org/bot/getMe").build()
        val response =
            Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(407)
                .message("Proxy Authentication Required")
                .header("Proxy-Authenticate", "Basic realm=\"test\"")
                .build()

        val authenticator = httpProxyAuthenticator(ProxyCredentials("user", "secret"))
        val authenticatedRequest = authenticator.authenticate(null, response)

        assertNotNull(authenticatedRequest)
        assertEquals(
            Credentials.basic("user", "secret"),
            authenticatedRequest!!.header("Proxy-Authorization"),
        )
    }
}
