# Proxy configuration

The library can route Telegram API traffic through a proxy. **Authenticated proxies are supported for HTTP proxies only.** Set `proxyUsername` and `proxyPassword` on `Bot.Builder`.

You can still set `Proxy.Type.SOCKS` (or any other `java.net.Proxy`) **without** credentials — same as before; only HTTP proxy authentication was added.

## HTTP proxy with authentication

```kotlin
import java.net.InetSocketAddress
import java.net.Proxy

fun main() {
    val bot = bot {
        token = "YOUR_API_KEY"
        proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress("proxy.corp.example", 8080))
        proxyUsername = "proxy-user"
        proxyPassword = "proxy-password"
        dispatch {
            // ...
        }
    }
    bot.startPolling()
}
```

## How authentication is applied

For `Proxy.Type.HTTP`, OkHttp `proxyAuthenticator` adds `Proxy-Authorization` (HTTP Basic) on `407 Proxy Authentication Required` and for preemptive CONNECT to HTTPS targets.

Implementation details live in the internal `network.proxy` package; only `Bot.Builder` options are part of the public API.

## Behaviour and limitations

**Credentials without a proxy**

If `proxy` is `Proxy.NO_PROXY` but username/password are set, credentials are **ignored** (no error). Configure `proxy` when you need authentication.

**Incomplete credentials**

If only `proxyUsername` or only `proxyPassword` is set, `build()` throws `IllegalArgumentException`. Set both or neither.

**SOCKS or other proxy types with credentials**

Username and password are applied only for `Proxy.Type.HTTP`. For SOCKS (and other types), credentials are resolved but **not used** — the proxy still works if it does not require authentication. Do not rely on `proxyUsername` / `proxyPassword` with SOCKS.

**HTTP proxy auth scope**

Credentials apply only to the OkHttp client inside each `ApiClient` instance (not JVM-global).

## Related

- [Logging](logging.md) — optional HTTP request/response logs when debugging proxy issues.
