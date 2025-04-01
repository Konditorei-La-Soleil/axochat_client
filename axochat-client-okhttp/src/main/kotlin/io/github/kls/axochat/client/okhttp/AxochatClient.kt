@file:JvmName("AxochatClient")

package io.github.kls.axochat.client.okhttp

import io.github.kls.axochat.packet.AxochatClientPacketAdapter
import okhttp3.OkHttpClient

@JvmName("create")
fun OkHttpClient.createAxochat(
    adapter: AxochatClientPacketAdapter
): AxochatWebSocket = AxochatWebSocket(this, adapter)
