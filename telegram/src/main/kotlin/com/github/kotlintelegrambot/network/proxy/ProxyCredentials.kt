package com.github.kotlintelegrambot.network.proxy

import java.net.Proxy

internal data class ProxyCredentials(
    val username: String,
    val password: String,
)

internal fun resolveProxyCredentials(
    username: String?,
    password: String?,
    proxy: Proxy,
): ProxyCredentials? =
    when {
        username == null && password == null -> null
        username == null || password == null ->
            throw IllegalArgumentException("proxyUsername and proxyPassword must both be set or both omitted")
        proxy == Proxy.NO_PROXY -> null
        else -> ProxyCredentials(username, password)
    }
