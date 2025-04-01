package io.github.kls.axochat.client.okhttp

import io.github.kls.axochat.client.okhttp.event.*
import io.github.kls.axochat.packet.AxochatC2SPacket
import io.github.kls.axochat.packet.AxochatClientPacketAdapter
import io.github.kls.axochat.packet.AxochatPacket
import io.github.kls.axochat.packet.AxochatS2CPacket
import okhttp3.*
import java.net.URL
import java.util.IdentityHashMap
import java.util.function.Consumer

class AxochatWebSocket internal constructor(
    private val client: OkHttpClient,
    private val adapter: AxochatClientPacketAdapter,
) {

    private var request: Request? = null
    private var webSocket: WebSocket? = null
    private val wsEventHandlers = IdentityHashMap<Class<out AxochatWSEvent>, Consumer<in AxochatWSEvent>?>().apply {
        AxochatWSEvent.ALL_EVENTS.forEach { put(it, null) }
    }
    private val s2cPacketsHandlers = IdentityHashMap<Class<out AxochatS2CPacket>, Consumer<in AxochatS2CPacket>?>().apply {
        AxochatPacket.S2C_PACKETS.values.forEach { put(it, null) }
    }

    private inline fun <reified T : AxochatWSEvent> exec(provider: () -> T) {
        wsEventHandlers[T::class.java]?.accept(provider())
    }

    fun url(url: HttpUrl) = apply {
        this.request = Request.Builder().url(url).build()
    }

    fun url(url: String) = apply {
        this.request = Request.Builder().url(url).build()
    }

    fun url(url: URL) = apply {
        this.request = Request.Builder().url(url).build()
    }

    @get:JvmName("url")
    val url: HttpUrl get() {
        requireNotNull(request) { "Request is uninitialized" }
        return request!!.url
    }

    fun start() = apply {
        requireNotNull(request) { "Request is uninitialized" }
        require(webSocket == null) { "WebSocket is already started" }
        webSocket = client.newWebSocket(request!!, AxochatWSListener())
    }

    inline fun <reified T : AxochatWSEvent> on(handler: Consumer<T>) = on(T::class.java, handler)

    fun <T : AxochatWSEvent> on(type: Class<T>, handler: Consumer<T>) {
        require(type !in wsEventHandlers) { "Handler of type $type is already registered" }
        wsEventHandlers[type] = handler as Consumer<in AxochatWSEvent>
    }

    inline fun <reified T : AxochatS2CPacket> on(handler: Consumer<T>) = on(T::class.java, handler)

    fun <T : AxochatS2CPacket> on(type: Class<T>, handler: Consumer<T>) {
        require(type !in s2cPacketsHandlers) { "Handler of type $type is already registered" }
        s2cPacketsHandlers[type] = handler as Consumer<in AxochatS2CPacket>
    }

    fun send(packet: AxochatC2SPacket) {
        requireNotNull(webSocket) { "WebSocket is uninitialized" }
    }

    @JvmOverloads
    fun close(code: Int = 1000, reason: String? = null) {
        requireNotNull(webSocket) { "WebSocket is uninitialized" }
        webSocket!!.close(code, reason)
    }

    private inner class AxochatWSListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            exec { WSOpenEvent(response) }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            try {
                val packet = adapter.readPacket(text)
                s2cPacketsHandlers[packet.javaClass]?.accept(packet)
            } catch (t: Throwable) {
                exec { WSMessageErrorEvent(t) }
            }
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            exec { WSClosingEvent(code, reason) }
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            exec { WSClosedEvent(code, reason) }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            exec { WSFailureEvent(t, response) }
        }
    }

}
