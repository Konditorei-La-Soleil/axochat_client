package io.github.kls.axochat.packet

import java.util.*

/**
 * AXOCHAT PROTOCOL
 *
 * https://github.com/CCBlueX/axochat_server/blob/master/PROTOCOL.md
 *
 * The server receives Server Packets.
 */

/**
 * A client can send this packet to ban other users from using this chat.
 *
 * @param user user is an ID.
 */
data class C2SBanUserPacket(
    val user: String
) : AxochatC2SPacket

/**
 * To log in using a json web token, the client has to send a LoginJWT packet.
 * it will send Success if the login was successful.
 *
 * @param token can be retrieved by sending RequestJWT on an already authenticated connection.
 * @param allowMessages If allow_messages is true, other clients may send private messages to this client.
 */
data class C2SLoginJWTPacket(
    val token: String,
    val allowMessages: Boolean
) : AxochatC2SPacket

/**
 * After the client received a MojangInfo packet and authenticating itself with mojang,
 * it has to send a LoginMojang packet to the server.
 * After the server receives a LoginMojang packet, it will send Success if the login was successful.

 * @param name name needs to be associated with the uuid.
 * @param uuid uuid is not guaranteed to be hyphenated.
 * @param allowMessages If allow_messages is true, other clients may send private messages to this client.
 */
data class C2SLoginMojangPacket(
    val name: String,
    val uuid: UUID,
    val allowMessages: Boolean
) : AxochatC2SPacket

/**
 * The content of this packet will be sent to every client as Message if it fits the validation scheme.
 *
 * @param content content of the message.
 */
data class C2SMessagePacket(
    val content: String
) : AxochatC2SPacket

/**
 * The content of this packet will be sent to the specified client as PrivateMessage if it fits the validation scheme.
 *
 * @param receiver receiver is an ID.
 * @param content content of the message.
 */
data class C2SPrivateMessagePacket(
    val receiver: String,
    val content: String
) : AxochatC2SPacket

/**
 * To log in using LoginJWT, a client needs to own a json web token.
 * This token can be retrieved by sending RequestJWT as an already authenticated client to the server.
 * The server will send a NewJWT packet to the client.
 *
 * This packet has no body.
 */
data object C2SRequestJWTPacket : AxochatC2SPacket

/**
 * To log in via mojang, the client has to send a RequestMojangInfo packet.
 * The server will then send a MojangInfo to the client.
 * This packet has no body.
 */
data object C2SRequestMojangInfoPacket : AxochatC2SPacket

/**
 * After receiving this packet, the server will then send a UserCount packet to the client.
 * This packet has no body.
 */
data object C2SRequestUserCountPacket : AxochatC2SPacket

/**
 * A client can send this packet to unban other users.
 *
 * @param user user is an ID.
 */
data class C2SUnbanUserPacket(
    val user: String
) : AxochatC2SPacket
