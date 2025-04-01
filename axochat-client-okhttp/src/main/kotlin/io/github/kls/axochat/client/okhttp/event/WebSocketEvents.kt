package io.github.kls.axochat.client.okhttp.event

import okhttp3.Response

sealed interface AxochatWSEvent {
    companion object {
        internal val ALL_EVENTS = arrayOf(
            WSOpenEvent::class.java,
            WSMessageErrorEvent::class.java,
            WSClosingEvent::class.java,
            WSClosedEvent::class.java,
            WSFailureEvent::class.java,
        )
    }
}

class WSOpenEvent(val response: Response) : AxochatWSEvent

class WSMessageErrorEvent(val throwable: Throwable) : AxochatWSEvent

class WSClosingEvent(val code: Int, val reason: String) : AxochatWSEvent

class WSClosedEvent(val code: Int, val reason: String) : AxochatWSEvent

class WSFailureEvent(val throwable: Throwable, val response: Response?) : AxochatWSEvent
