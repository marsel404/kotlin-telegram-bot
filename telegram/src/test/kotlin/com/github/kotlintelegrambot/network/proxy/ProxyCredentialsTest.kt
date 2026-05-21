package com.github.kotlintelegrambot.network.proxy

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.net.InetSocketAddress
import java.net.Proxy

class ProxyCredentialsTest {
    private val httpProxy = Proxy(Proxy.Type.HTTP, InetSocketAddress("proxy.example.com", 8080))
    private val socksProxy = Proxy(Proxy.Type.SOCKS, InetSocketAddress("socks.example.com", 1080))

    @Test
    fun `returns null when both username and password are null`() {
        assertNull(resolveProxyCredentials(null, null, httpProxy))
    }

    @Test
    fun `throws when only username is set`() {
        assertThrows<IllegalArgumentException> {
            resolveProxyCredentials("user", null, httpProxy)
        }
    }

    @Test
    fun `throws when only password is set`() {
        assertThrows<IllegalArgumentException> {
            resolveProxyCredentials(null, "pass", httpProxy)
        }
    }

    @Test
    fun `returns null when credentials are set but proxy is NO_PROXY`() {
        assertNull(resolveProxyCredentials("user", "pass", Proxy.NO_PROXY))
    }

    @Test
    fun `returns credentials for HTTP proxy`() {
        val credentials = resolveProxyCredentials("user", "pass", httpProxy)

        assertEquals(ProxyCredentials("user", "pass"), credentials)
    }

    @Test
    fun `returns credentials for SOCKS proxy`() {
        val credentials = resolveProxyCredentials("user", "pass", socksProxy)

        assertEquals(ProxyCredentials("user", "pass"), credentials)
    }
}
