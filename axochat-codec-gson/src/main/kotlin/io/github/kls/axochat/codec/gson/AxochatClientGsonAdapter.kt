package io.github.kls.axochat.codec.gson

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.github.kls.axochat.exception.IllegalPacketFormatException
import io.github.kls.axochat.exception.UnknownPacketNameException
import io.github.kls.axochat.packet.*
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import kotlin.jvm.Throws

object AxochatClientGsonAdapter : AxochatClientPacketAdapter {

    private val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    @Throws(IOException::class)
    override fun InputStream.readPacket(): AxochatS2CPacket {
        val fullPacket = JsonParser.parseReader(gson.newJsonReader(this.reader()))
        val name = (fullPacket as? JsonObject)?.get("m")?.asString ?: throw IllegalPacketFormatException(gson.toJson(fullPacket))
        val type = AxochatPacket.S2C_PACKETS[name] ?: throw UnknownPacketNameException(name)
        return gson.fromJson(fullPacket["c"], type)
    }

    @Throws(IOException::class)
    override fun OutputStream.writePacket(packet: AxochatC2SPacket) {
        class FullPacket(val m: String, val c: AxochatPacket?)
        val name = AxochatPacket.C2S_PACKETS[packet.javaClass]!!
        gson.toJson(FullPacket(name, packet), this.writer())
        this.flush()
    }

}
