package io.github.kls.axochat.packet

sealed interface AxochatPacket {
    companion object {
        val S2C_PACKETS = hashMapOf(
            "Error" to S2CErrorPacket::class.java,
            "Message" to S2CMessagePacket::class.java,
            "MojangInfo" to S2CMojangInfoPacket::class.java,
            "NewJWT" to S2CNewJWTPacket::class.java,
            "PrivateMessage" to S2CPrivateMessagePacket::class.java,
            "Success" to S2CSuccessPacket::class.java,
            "UserCount" to S2CUserCountPacket::class.java,
        ).unmodifiable()

        val C2S_PACKETS = hashMapOf(
            C2SBanUserPacket::class.java to "BanUser",
            C2SLoginJWTPacket::class.java to "LoginJWT",
            C2SLoginMojangPacket::class.java to "LoginMojang",
            C2SMessagePacket::class.java to "Message",
            C2SPrivateMessagePacket::class.java to "PrivateMessage",
            C2SRequestJWTPacket::class.java to "RequestJWT",
            C2SPrivateMessagePacket::class.java to "PrivateMessage",
            C2SRequestJWTPacket::class.java to "RequestJWT",
            C2SRequestMojangInfoPacket::class.java to "RequestMojangInfo",
            C2SRequestUserCountPacket::class.java to "RequestUserCount",
            C2SUnbanUserPacket::class.java to "UnbanUser",
        ).unmodifiable()

        @Suppress("NOTHING_TO_INLINE")
        private inline fun <K, V> Map<K, V>.unmodifiable(): Map<K, V> = java.util.Collections.unmodifiableMap(this)
    }
}

sealed interface AxochatS2CPacket : AxochatPacket

sealed interface AxochatC2SPacket : AxochatPacket
