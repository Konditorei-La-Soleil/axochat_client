package io.github.kls.axochat.packet

import java.util.*

/**
 * A axochat user
 *
 * @param name of user
 * @param uuid of user
 */
data class User(
    val name: String,
    val uuid: UUID
)
