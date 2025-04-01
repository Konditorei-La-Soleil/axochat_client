package io.github.kls.axochat.packet

sealed interface AxochatPacket

sealed interface AxochatS2CPacket : AxochatPacket

sealed interface AxochatC2SPacket : AxochatPacket
