package io.github.kls.axochat.packet

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import kotlin.jvm.Throws

interface AxochatClientPacketAdapter {

    @Throws(IOException::class)
    fun InputStream.readPacket(): AxochatS2CPacket

    @Throws(IOException::class)
    fun OutputStream.writePacket(packet: AxochatC2SPacket)

}
