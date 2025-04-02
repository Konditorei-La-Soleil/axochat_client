# AxoChat Client
A generic server for chat features in Minecraft modifications utilizing the Mojang authentication scheme and WebSockets. LiquidBounce employs it for its global chat feature, which allows users to communicate with other people using the client regardless of the current server.

This project aims to provide an out-of-the-box Axochat client implementation for the Minecraft ecosystem (typically based on the JVM). It includes basic data type definitions and implementations for both the client and serialization modules. Currently, it offers a client based on **OkHttp** and serialization support using **Gson**/**Jackson**.

Users can also integrate this project into Android applications, Spring Boot, Ktor, or other applications, or selectively use parts of the code while utilizing other tools such as **Netty** for implementation.

This project is licensed under the **MIT License**.

## Implementation
[Server implementation (Rust)](https://github.com/CCBlueX/axochat_server/)

[Protocol document](https://github.com/CCBlueX/axochat_server/blob/master/PROTOCOL.md)
