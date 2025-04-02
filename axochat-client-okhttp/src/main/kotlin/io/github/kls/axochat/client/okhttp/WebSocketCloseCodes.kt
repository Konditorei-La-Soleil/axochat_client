package io.github.kls.axochat.client.okhttp

/**
 * https://www.rfc-editor.org/rfc/rfc6455.html
 */
object WebSocketCloseCodes {
    const val NORMAL_CLOSURE = 1000
    const val GOING_AWAY = 1001
    const val PROTOCOL_ERROR = 1002
    const val UNSUPPORTED_DATA = 1003
    const val RESERVED = 1004
    const val NO_STATUS_RECEIVED = 1005
    const val ABNORMAL_CLOSURE = 1006
    const val INVALID_FRAME_PAYLOAD_DATA = 1007
    const val POLICY_VIOLATION = 1008
    const val MESSAGE_TOO_BIG = 1009
    const val MANDATORY_EXTENSION = 1010
    const val INTERNAL_SERVER_ERROR = 1011
    const val SERVICE_RESTART = 1012
    const val TRY_AGAIN_LATER = 1013
    const val BAD_GATEWAY = 1014
    const val TLS_HANDSHAKE_FAILURE = 1015
}
