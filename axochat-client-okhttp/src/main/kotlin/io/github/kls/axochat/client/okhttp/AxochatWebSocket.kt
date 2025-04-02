package io.github.kls.axochat.client.okhttp

import io.github.kls.axochat.client.okhttp.event.*
import io.github.kls.axochat.client.okhttp.internal.createEventMap
import io.github.kls.axochat.packet.AxochatC2SPacket
import io.github.kls.axochat.packet.AxochatClientPacketAdapter
import io.github.kls.axochat.packet.AxochatPacket
import io.github.kls.axochat.packet.AxochatS2CPacket
import okhttp3.*
import java.net.URL
import java.util.function.Consumer

class AxochatWebSocket internal constructor(
    private val client: OkHttpClient,
    private val adapter: AxochatClientPacketAdapter,
) {

    private var request: Request? = null
    private var webSocket: WebSocket? = null

    private val wsEventHandlers =
        createEventMap<AxochatWSEvent, Consumer<in AxochatWSEvent>?>(AxochatWSEvent.ALL_EVENTS) { null }
    private val s2cPacketsHandlers =
        createEventMap<AxochatS2CPacket, Consumer<in AxochatS2CPacket>?>(AxochatPacket.S2C_PACKETS.values) { null }

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

    @JvmOverloads
    fun start(closeExisting: Boolean = false) = apply {
        requireNotNull(request) { "Request is uninitialized" }
        if (closeExisting) {
            webSocket?.close(WebSocketCloseCodes.NORMAL_CLOSURE, null)
        } else {
            require(webSocket == null) { "WebSocket is already started" }
        }
        webSocket = client.newWebSocket(request!!, AxochatWSListener())
    }

    @JvmName("ws")
    fun <T : AxochatWSEvent> on(type: Class<T>, handler: Consumer<T>) = apply {
        require(wsEventHandlers[type] == null) { "Handler of type $type is already registered" }
        wsEventHandlers[type] = handler as Consumer<in AxochatWSEvent>
    }

    @JvmName("ws")
    inline fun <reified T : AxochatWSEvent> on(crossinline handler: AxochatWebSocket.(T) -> Unit) =
        on(T::class.java) { handler(this, it) }

    fun <T : AxochatS2CPacket> on(type: Class<T>, handler: Consumer<T>) = apply {
        require(s2cPacketsHandlers[type] == null) { "Handler of type $type is already registered" }
        s2cPacketsHandlers[type] = handler as Consumer<in AxochatS2CPacket>
    }

    inline fun <reified T : AxochatS2CPacket> on(crossinline handler: AxochatWebSocket.(T) -> Unit) =
        on(T::class.java) { handler(this, it) }

    fun send(packet: AxochatC2SPacket) = apply {
        requireNotNull(webSocket) { "WebSocket is uninitialized" }
        val text = adapter.writePacket(packet)
        webSocket!!.send(text)
    }

    @JvmOverloads
    fun close(code: Int = WebSocketCloseCodes.NORMAL_CLOSURE, reason: String? = null) = apply {
        requireNotNull(webSocket) { "WebSocket is uninitialized" }
        webSocket!!.close(code, reason)
        webSocket = null
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
