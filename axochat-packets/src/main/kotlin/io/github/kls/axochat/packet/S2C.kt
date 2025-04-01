package io.github.kls.axochat.packet

/**
 * AXOCHAT PROTOCOL
 *
 * https://github.com/CCBlueX/axochat_server/blob/master/PROTOCOL.md
 *
 * The client receives client Packets.
 */

/**
 * After the client sent the server a RequestMojangInfo packet, the server will provide the client with a session_hash.
 * A session hash is synonymous with a server id in the context of authentication with Mojang.
 * The client has to send a LoginMojang packet to the server after authenticating itself with Mojang.
 *
 * @param sessionHash session_hash to authenticate with Mojang
 */
data class S2CMojangInfoPacket(
    val sessionHash: String
) : AxochatS2CPacket

/**
 * After the client sent the server a RequestJWT packet, the server will provide the client with json web token.
 * This token can be used in the LoginJWT packet.
 *
 * @param token JWT token
 */
data class S2CNewJWTPacket(
    val token: String
) : AxochatS2CPacket

/**
 * This packet will be sent to every authenticated client
 * if another client successfully sent a message to the server.
 *
 * @param id author_id is an ID.
 * @param user author_info is optional and described in detail in UserInfo.
 * @param content content is any message fitting the validation scheme of the server.
 */
data class S2CMessagePacket(
    val authorId: String,
    val authorInfo: User,
    val content: String
) : AxochatS2CPacket

/**
 * This packet will be sent to an authenticated client with allow_messages turned on,
 * if another client successfully sent a private message to the server with the id.
 *
 * @param id author_id is an ID.
 * @param user author_info is optional and described in detail in UserInfo.
 * @param content content is any message fitting the validation scheme of the server.
 */
data class S2CPrivateMessagePacket(
    val authorId: String,
    val authorInfo: User,
    val content: String
) : AxochatS2CPacket

/**
 * This packet is sent after either LoginMojang, LoginJWT, BanUser or UnbanUser were processed successfully.
 *
 * @param reason of success packet
 */
data class S2CSuccessPacket(
    val reason: String
) : AxochatS2CPacket

/**
 * This packet may be sent at any time, but is usually a response to a failed action of the client.
 *
 * @param message Error message
 */
data class S2CErrorPacket(
    val message: String
) : AxochatS2CPacket
