package io.github.kls.axochat.exception

import java.io.IOException

class IllegalPacketFormatException(text: String) : IOException("Illegal packet format: $text")

class UnknownPacketNameException(name: String) : IOException("Unknown packet name: $name")
