package io.github.kls.axochat.packet

import java.io.IOException
import kotlin.jvm.Throws

interface AxochatClientPacketAdapter {

    @Throws(IOException::class)
    fun readPacket(source: String): AxochatS2CPacket

    @Throws(IOException::class)
    fun writePacket(packet: AxochatC2SPacket): String

}
