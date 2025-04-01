package io.github.kls.axochat.packet

import java.util.*

/**
 * A axochat user
 *
 * @param name of user
 * @param uuid of user
 */
data class AxochatUser(
    val name: String,
    val uuid: UUID
)
