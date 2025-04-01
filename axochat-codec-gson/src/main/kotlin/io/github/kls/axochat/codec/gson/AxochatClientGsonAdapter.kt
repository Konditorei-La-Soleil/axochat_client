package io.github.kls.axochat.codec.gson

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.github.kls.axochat.exception.IllegalPacketFormatException
import io.github.kls.axochat.exception.UnknownPacketNameException
import io.github.kls.axochat.packet.*
import java.io.IOException
import kotlin.jvm.Throws

object AxochatClientGsonAdapter : AxochatClientPacketAdapter {

    private val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    @Throws(IOException::class)
    override fun readPacket(source: String): AxochatS2CPacket {
        val fullPacket = JsonParser.parseString(source)
        val name = (fullPacket as? JsonObject)?.get("m")?.asString ?: throw IllegalPacketFormatException(gson.toJson(fullPacket))
        val type = AxochatPacket.S2C_PACKETS[name] ?: throw UnknownPacketNameException(name)
        return type.kotlin.objectInstance ?: gson.fromJson(fullPacket["c"], type)
    }

    @Throws(IOException::class)
    override fun writePacket(packet: AxochatC2SPacket): String {
        class FullPacket(val m: String, val c: AxochatPacket?)
        val type = packet.javaClass
        val name = AxochatPacket.C2S_PACKETS[type]!!
        val payload = packet.takeUnless { type.kotlin.objectInstance != null }
        return gson.toJson(FullPacket(name, payload))
    }

}
