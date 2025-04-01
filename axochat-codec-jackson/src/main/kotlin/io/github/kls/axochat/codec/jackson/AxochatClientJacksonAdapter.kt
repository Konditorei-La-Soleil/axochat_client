package io.github.kls.axochat.codec.jackson

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.kls.axochat.exception.IllegalPacketFormatException
import io.github.kls.axochat.exception.UnknownPacketNameException
import io.github.kls.axochat.packet.*
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import kotlin.jvm.Throws

object AxochatClientJacksonAdapter : AxochatClientPacketAdapter {

    private val objectMapper = ObjectMapper()
        .registerKotlinModule()
        .setPropertyNamingStrategy(PropertyNamingStrategies.SnakeCaseStrategy())
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)

    @Throws(IOException::class)
    override fun InputStream.readPacket(): AxochatS2CPacket {
        val fullPacket = objectMapper.readTree(this)
        val name = fullPacket["m"]?.asText() ?: throw IllegalPacketFormatException(objectMapper.writeValueAsString(fullPacket))
        val type = AxochatPacket.S2C_PACKETS[name] ?: throw UnknownPacketNameException(name)
        return objectMapper.treeToValue(fullPacket["c"], type)
    }

    @Throws(IOException::class)
    override fun OutputStream.writePacket(packet: AxochatC2SPacket) {
        class FullPacket(val m: String, val c: AxochatPacket?)
        val name = AxochatPacket.C2S_PACKETS[packet.javaClass]!!
        objectMapper.writeValue(this, FullPacket(name, packet))
        this.flush()
    }

}
