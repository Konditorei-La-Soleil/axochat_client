# AxoChat Client
基于 Minecraft 中 Mojang 身份验证的聊天服务, 采用 WebSockets/JSON 通信. LiquidBounce 客户端使用它作为客户端聊天功能, 该功能允许用户使用客户端与其他人进行通信, 而不管当前服务器是什么.

本项目旨在为 Minecraft 生态 (通常基于 JVM) 提供开箱即用的 Axochat 客户端实现, 提供了基本的数据类型定义以及客户端和序列化模块的实现.

目前提供了基于 **OkHttp** 的客户端实现和使用 **Gson**/**Jackson** 的序列化模块.

用户也可以在安卓应用或 SpringBoot/Ktor 等应用中使用这个项目的内容, 或者只使用其中的一部分代码, 自行使用其他工具比如 **Netty** 来完成实现. (这是 LiquidBounce 客户端选择的实现方案)

本项目遵循 **MIT License**.

## Implementation
[服务端实现 (Rust)](https://github.com/CCBlueX/axochat_server/)

[协议文档](https://github.com/CCBlueX/axochat_server/blob/master/PROTOCOL.md)
