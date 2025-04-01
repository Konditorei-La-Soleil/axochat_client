package io.github.kls.axochat.codec.jackson

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.kls.axochat.exception.IllegalPacketFormatException
import io.github.kls.axochat.exception.UnknownPacketNameException
import io.github.kls.axochat.packet.*
import java.io.IOException
import kotlin.jvm.Throws

object AxochatClientJacksonAdapter : AxochatClientPacketAdapter {

    private val objectMapper = ObjectMapper()
        .registerKotlinModule()
        .setPropertyNamingStrategy(PropertyNamingStrategies.SnakeCaseStrategy())
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)

    @Throws(IOException::class)
    override fun readPacket(source: String): AxochatS2CPacket {
        val fullPacket = objectMapper.readTree(source)
        val name = fullPacket["m"]?.asText() ?: throw IllegalPacketFormatException(objectMapper.writeValueAsString(fullPacket))
        val type = AxochatPacket.S2C_PACKETS[name] ?: throw UnknownPacketNameException(name)
        return objectMapper.treeToValue(fullPacket["c"], type)
    }

    @Throws(IOException::class)
    override fun writePacket(packet: AxochatC2SPacket): String {
        class FullPacket(val m: String, val c: AxochatPacket?)
        val name = AxochatPacket.C2S_PACKETS[packet.javaClass]!!
        return objectMapper.writeValueAsString(FullPacket(name, packet))
    }

}
