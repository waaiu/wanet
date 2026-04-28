# wanet Framework Guide (for AI Assistants)

> **wanet** is a Java distributed network programming framework built on [Aeron](https://github.com/real-logic/aeron) (inter-process/inter-machine messaging) and [Netty](https://netty.io/) (client-facing transport). It uses a lock-free, asynchronous, event-driven architecture designed for ultra-low latency systems such as online game servers.
>
> **License:** AGPL-3.0  
> **Min Java version:** 25 (see `pom.xml`)  
> **Group ID:** `com.waaiu.net`  
> **Root package:** `com.waaiu.net`

---

## Table of Contents

1. [How to Use wanet in Your Project (Maven Dependencies)](#how-to-use-wanet-in-your-project-maven-dependencies)
2. [Architecture Overview](#architecture-overview)
3. [Module Map & Maven Coordinates](#module-map--maven-coordinates)
4. [Core Concepts & Terminology](#core-concepts--terminology)
5. [Routing Model (cmd / subCmd)](#routing-model-cmd--subcmd)
6. [Writing an Action Controller](#writing-an-action-controller)
7. [Building a Logic Server](#building-a-logic-server)
8. [External Server (Client-Facing Gateway)](#external-server-client-facing-gateway)
9. [Combined Startup with RunOne](#combined-startup-with-runone)
10. [FlowContext — The Request Context](#flowcontext--the-request-context)
11. [Communication Patterns](#communication-patterns)
12. [Interceptors (ActionMethodInOut)](#interceptors-actionmethodinout)
13. [Data Codec & Protocol Wrappers](#data-codec--protocol-wrappers)
14. [Extension Modules](#extension-modules)
15. [Spring Integration](#spring-integration)
16. [Code Generation (Multi-Language SDK)](#code-generation-multi-language-sdk)
17. [Key Files Quick-Reference](#key-files-quick-reference)
18. [Common Pitfalls](#common-pitfalls)

---

## How to Use wanet in Your Project (Maven Dependencies)

wanet is published as a multi-module Maven project. Each module is an independent JAR artifact under the group `com.waaiu.net`. You import **only the modules you need** into your application's `pom.xml`.

### Quick Start — Minimal Game Server

For a typical game server that uses the combined single-process deployment, add `run-one` (which transitively pulls in the core, networking, and external modules):

```xml
<dependencies>
    <!-- All-in-one bootstrap (includes core-framework, net-*, external-*) -->
    <dependency>
        <groupId>com.waaiu.net</groupId>
        <artifactId>run-one</artifactId>
        <version>25.4</version>
    </dependency>

    <!-- Protobuf serialization support -->
    <dependency>
        <groupId>com.waaiu.net</groupId>
        <artifactId>extension-jprotobuf</artifactId>
        <version>25.4</version>
    </dependency>
</dependencies>
```

### Quick Start — Game Server with Room Support

```xml
<dependencies>
    <dependency>
        <groupId>com.waaiu.net</groupId>
        <artifactId>run-one</artifactId>
        <version>25.4</version>
    </dependency>

    <dependency>
        <groupId>com.waaiu.net</groupId>
        <artifactId>extension-jprotobuf</artifactId>
        <version>25.4</version>
    </dependency>

    <!-- Room/Player abstractions for multiplayer games -->
    <dependency>
        <groupId>com.waaiu.net</groupId>
        <artifactId>extension-room</artifactId>
        <version>25.4</version>
    </dependency>
</dependencies>
```

### Quick Start — With Spring Boot Integration

```xml
<dependencies>
    <dependency>
        <groupId>com.waaiu.net</groupId>
        <artifactId>run-one</artifactId>
        <version>25.4</version>
    </dependency>

    <dependency>
        <groupId>com.waaiu.net</groupId>
        <artifactId>extension-jprotobuf</artifactId>
        <version>25.4</version>
    </dependency>

    <!-- Spring DI integration for ActionControllers -->
    <dependency>
        <groupId>com.waaiu.net</groupId>
        <artifactId>extension-spring</artifactId>
        <version>25.4</version>
    </dependency>
</dependencies>
```

### Pick-and-Choose — Individual Modules

You can also import individual modules if you only need specific functionality:

```xml
<!-- Just the core business framework (annotations, FlowContext, protocol) -->
<dependency>
    <groupId>com.waaiu.net</groupId>
    <artifactId>core-framework</artifactId>
    <version>25.4</version>
</dependency>

<!-- Just the utility library -->
<dependency>
    <groupId>com.waaiu.net</groupId>
    <artifactId>common-kit</artifactId>
    <version>25.4</version>
</dependency>

<!-- Domain event bus (can be used standalone) -->
<dependency>
    <groupId>com.waaiu.net</groupId>
    <artifactId>extension-domain-event</artifactId>
    <version>25.4</version>
</dependency>

<!-- Code generation for client SDKs -->
<dependency>
    <groupId>com.waaiu.net</groupId>
    <artifactId>extension-codegen</artifactId>
    <version>25.4</version>
</dependency>

<!-- Simulation/test client -->
<dependency>
    <groupId>com.waaiu.net</groupId>
    <artifactId>extension-client</artifactId>
    <version>25.4</version>
    <scope>test</scope>
</dependency>
```

### Installing to Local Maven Repository

Before other projects can use wanet, you must install it to your local Maven repository:

```bash
# From the wanet root directory
mvn clean install -Dgpg.skip=true -Dmaven.test.skip=true
```

This will install all 15 modules to `~/.m2/repository/com/waaiu/net/`.

---

## Architecture Overview

```
┌──────────────────────────────────────────────────────────┐
│                      RunOne (Bootstrap)                   │
│  Coordinates startup of all servers in a single process   │
├───────────────┬──────────────────┬────────────────────────┤
│ ExternalServer│   NetServer      │   CenterServer (opt.)  │
│ (Netty-based) │ (Aeron backbone) │  (Service discovery)   │
│ TCP/WS/UDP    │                  │                        │
├───────────────┤                  ├────────────────────────┤
│ UserSessions  │  ┌──────────────┐│                        │
│ ExternalMsg   │  │ LogicServer1 ││  Balanced routing      │
│               │  │ LogicServer2 ││  Connection management │
│               │  │ LogicServerN ││  Server lifecycle      │
│               │  └──────────────┘│                        │
└───────────────┴──────────────────┴────────────────────────┘
                            │
            ┌───────────────┼───────────────┐
            │         BarSkeleton           │
            │  (Business framework core)    │
            │  ┌─────────────────────────┐  │
            │  │ ActionCommandRegions    │  │
            │  │ ActionCommand[][]       │  │
            │  │ FlowContext pipeline    │  │
            │  │ InOut interceptors      │  │
            │  └─────────────────────────┘  │
            └───────────────────────────────┘
```

**Data flow:** Client → ExternalServer (Netty) → Aeron IPC → NetServer → LogicServer → BarSkeleton → ActionController method → response back via the same path.

---

## Module Map & Maven Coordinates

All modules share `groupId = com.waaiu.net` and `version = 25.4`.

### Core Modules (required for most apps)

| artifactId | Purpose | Typical Usage |
|---|---|---|
| `common-kit` | Utility classes: collections, concurrency, time, string, network | Transitive dep of `core-framework` |
| `core-framework` | Business framework: `@ActionController`, `@ActionMethod`, `FlowContext`, interceptors, protocol, communication, i18n | Transitive dep of `run-one` |

### Networking Modules (pulled in by `run-one`)

| artifactId | Purpose | Typical Usage |
|---|---|---|
| `net-common` | Shared Aeron primitives: `Publisher`, `OnFragment`, SBE codecs | Transitive dep |
| `net-center` | Optional center server for multi-machine service discovery | Transitive dep |
| `net-server` | `NetServer`: connection management, load balancing, cmd routing, lifecycle | Transitive dep |
| `net-logic-server` | `LogicServer` bootstrap, Aeron fragment handlers | Transitive dep |
| `external-core` | `ExternalServer` core: session management, hooks, transport abstraction | Transitive dep |
| `external-netty` | Netty-based transport implementations (TCP, WebSocket, UDP) | Transitive dep |
| `run-one` | **`RunOne`** — single-process combined startup orchestrator | **Import this** |

### Extension Modules (optional, import as needed)

| artifactId | Purpose | When to Import |
|---|---|---|
| `extension-jprotobuf` | JProtobuf serialization integration | Almost always — default data codec |
| `extension-room` | `Room`, `Player`, `RoomService` for multiplayer games | Building game rooms |
| `extension-domain-event` | In-process domain event bus | Decoupled event handling |
| `extension-spring` | `ActionFactoryBeanForSpring` — Spring DI integration | Using Spring Boot |
| `extension-codegen` | Multi-language client SDK generation (TypeScript, C#, GDScript) | Generating client code |
| `extension-client` | Interactive simulation client for load testing | Testing / debugging |

---

## Core Concepts & Terminology

| Term | Meaning |
|---|---|
| **ActionController** | A class annotated with `@ActionController(cmd)` that groups related request handlers under one command module ID. |
| **ActionMethod** | A method annotated with `@ActionMethod(subCmd)` inside an ActionController. It handles requests matching `(cmd, subCmd)`. |
| **CmdInfo** | A record `(cmd, subCmd, cmdMerge)` identifying a unique route. `cmdMerge = cmd << 16 | subCmd`. Uses flyweight pattern. |
| **BarSkeleton** | The core business processing engine. Holds the action command registry, interceptors, and flow executor. |
| **BarSkeletonBuilder** | Builder for `BarSkeleton`. Scans action controllers, registers interceptors and runners. |
| **FlowContext** | Per-request context object. Provides access to request data, user ID, error state, and all communication APIs (broadcast, cross-service calls, etc.). |
| **ActionMethodInOut** | Interceptor interface with `fuckIn(FlowContext)` (pre-processing) and `fuckOut(FlowContext)` (post-processing) hooks. |
| **ExternalServer** | Client-facing gateway server (Netty). Accepts TCP/WebSocket/UDP connections and forwards to logic via Aeron. |
| **LogicServer** | Business logic server. Contains ActionControllers and the BarSkeleton. Multiple logic servers can run in the same process. |
| **NetServer** | Internal backbone server managing Aeron connections, load balancing, cmd routing between logic servers. |
| **RunOne** | Convenience bootstrap class that starts ExternalServer + NetServer + LogicServers in a single JVM process. |
| **CenterServer** | Optional service-discovery server for multi-machine deployments. |
| **RangeBroadcast** | Targeted broadcast to a specific set of users (e.g., all players in a room). |
| **Room / Player** | Extension abstractions for multiplayer game rooms with seat management, player lifecycle, and operation patterns. |

---

## Routing Model (cmd / subCmd)

wanet uses a two-level numeric routing system:

- **cmd** (int): Identifies the module/controller (set via `@ActionController(cmd)`)
- **subCmd** (int): Identifies the specific action method (set via `@ActionMethod(subCmd)`)
- **cmdMerge** (int): Combined key `cmd << 16 | subCmd`, used internally for O(1) lookup in a 2D array

```java
// Route: cmd=1, subCmd=1 → merged as 0x00010001
CmdInfo cmdInfo = CmdInfo.of(1, 1);
```

Routes are registered at startup when `BarSkeletonBuilder.build()` scans `@ActionController` classes. Duplicate routes are detected globally via `ActionCommandRegionGlobalCheckKit.detectGlobalDuplicateRoutes()`.

---

## Writing an Action Controller

```java
import com.waaiu.net.framework.annotations.ActionController;
import com.waaiu.net.framework.annotations.ActionMethod;
import com.waaiu.net.framework.core.flow.FlowContext;

@ActionController(1) // cmd = 1
public class HelloController {

    // Route: cmd=1, subCmd=0
    @ActionMethod(0)
    public String hello(FlowContext flowContext) {
        long userId = flowContext.getUserId();
        return "Hello, user " + userId;
    }

    // Route: cmd=1, subCmd=1 — accepts a request body
    @ActionMethod(1)
    public MyResponse doSomething(MyRequest request, FlowContext flowContext) {
        // process request...
        return new MyResponse(/* ... */);
    }

    // Route: cmd=1, subCmd=2 — void return (fire-and-forget)
    @ActionMethod(2)
    public void onEvent(MyEvent event, FlowContext flowContext) {
        // handle event, no response sent back
    }
}
```

**Rules:**
- Return type can be `void`, a primitive wrapper (`int`, `long`, `boolean`, `String`), or a Protobuf-serializable object.
- Method parameters: the framework injects `FlowContext` automatically. A single data parameter (request body) is deserialized from the incoming bytes.
- `set`, `map`, and raw primitive types are **not supported** as return/parameter types. Use wrapper types (`IntValue`, `LongValue`, `BoolValue`, `StringValue`) from `com.waaiu.net.framework.protocol.wrapper`.

---

## Building a Logic Server

```java
import com.waaiu.net.server.LogicServer;
import com.waaiu.net.framework.core.BarSkeletonBuilder;
import com.waaiu.net.framework.protocol.ServerBuilder;

public class GameLogicServer implements LogicServer {

    @Override
    public void settingBarSkeletonBuilder(BarSkeletonBuilder builder) {
        // Scan a package for @ActionController classes
        builder.scanActionPackage(HelloController.class);
        
        // Or register individually
        builder.addActionController(AnotherController.class);
        
        // Add interceptors
        builder.addInOut(new DebugInOut());
    }

    @Override
    public void settingServerBuilder(ServerBuilder builder) {
        builder.setId(1001)
               .setName("GameLogicServer")
               .setTag("game-logic");
    }
}
```

---

## External Server (Client-Facing Gateway)

```java
import com.waaiu.net.external.core.ExternalServerBuilder;

var externalServer = new ExternalServerBuilder()
        .setPort(10100)
        // .setJoinEnum(ExternalJoinEnum.WEBSOCKET) // default
        // .setJoinEnum(ExternalJoinEnum.TCP)
        // .setJoinEnum(ExternalJoinEnum.UDP)
        .build();
```

The `ExternalServer` handles:
- Client connections (WebSocket/TCP/UDP via Netty)
- Session management (`UserSessions`)
- User lifecycle hooks (`UserHook` — login/logout callbacks)
- Heartbeat / idle detection (`IdleProcessSettingBuilder`)
- Message encoding/decoding (`ExternalMessage`)

---

## Combined Startup with RunOne

```java
import com.waaiu.net.app.RunOne;
import io.aeron.Aeron;
import io.aeron.driver.MediaDriver;

public class Application {
    public static void main(String[] args) {
        // 1. Start Aeron MediaDriver (embedded or external)
        var mediaDriver = MediaDriver.launchEmbedded();
        var aeron = Aeron.connect(new Aeron.Context()
                .aeronDirectoryName(mediaDriver.aeronDirectoryName()));

        // 2. Build external server
        var externalServer = new ExternalServerBuilder()
                .setPort(10100)
                .build();

        // 3. Create logic servers
        var gameLogic = new GameLogicServer();

        // 4. Bootstrap everything
        new RunOne()
                .setAeron(aeron)
                .setExternalServer(externalServer)
                .setLogicServerList(List.of(gameLogic))
                .startup();
    }
}
```

**Startup sequence:**
1. Aeron MediaDriver starts (IPC transport layer)
2. `RunOne.startup()` builds the `NetServer`
3. External server binds to the client port
4. Each `LogicServer` is bootstrapped via `LogicServerApplication.startup()`
5. Action routes are registered and duplicate routes are checked
6. Banner is printed with server info

---

## FlowContext — The Request Context

`FlowContext` is created per-request and provides:

| Category | Methods |
|---|---|
| **Identity** | `getUserId()`, `setUserId(long)` |
| **Request** | `getRequest()`, `getCmdInfo()`, `getDataParam()` |
| **Response** | `getMethodResult()`, `setMethodResult(Object)` |
| **Error** | `getErrorCode()`, `setErrorCode(int)`, `getErrorMessage()`, `hasError()` |
| **Broadcast** | `broadcastMe(CmdInfo, data)`, `broadcastUser(CmdInfo, userId, data)`, `broadcastUserList(...)`, `broadcastMulticast(...)` |
| **Cross-service call** | via `FlowLogicCallCommunication` — request-response to another LogicServer |
| **Fire-and-forget** | via `FlowLogicSendCommunication` — send to another LogicServer without waiting |
| **EventBus** | via `FlowCommunicationEventBus` — publish events across services |
| **External write** | via `FlowExternalWriteCommunication` — write directly to external connections |
| **Attachments** | via `FlowAttachmentCommunication` — key-value metadata on the request |

---

## Communication Patterns

### 1. Request / Response (default)
An `@ActionMethod` that returns a value automatically sends the result back to the requesting client.

### 2. Request / Void
An `@ActionMethod` returning `void` processes the request but sends no response.

### 3. Broadcast
```java
// Broadcast to the requesting user
flowContext.broadcastMe(CmdInfo.of(2, 1), someData);

// Broadcast to a specific user
flowContext.broadcastUser(CmdInfo.of(2, 1), targetUserId, someData);

// Broadcast to a list of users
flowContext.broadcastUserList(CmdInfo.of(2, 1), userIdList, someData);
```

### 4. Cross-Service Calls
```java
// Synchronous call to another logic server's action
ResponseCollect result = flowContext.invokeModuleCollect(CmdInfo.of(3, 1), requestData);
```

### 5. EventBus
Publish events that can be consumed by any logic server:
```java
flowContext.fireEventBus(eventData);
```

### 6. RangeBroadcast (Room Extension)
```java
// Broadcast to all players in a room
room.ofRangeBroadcast(CmdInfo.of(2, 1))
    .setData(gameState)
    .execute(flowContext);
```

---

## Interceptors (ActionMethodInOut)

Interceptors wrap every action method invocation. Implement `ActionMethodInOut`:

```java
import com.waaiu.net.framework.core.flow.ActionMethodInOut;
import com.waaiu.net.framework.core.flow.FlowContext;

public class AuthInterceptor implements ActionMethodInOut {
    @Override
    public void fuckIn(FlowContext flowContext) {
        // Pre-processing: check auth, log, validate, etc.
        if (flowContext.getUserId() == 0) {
            flowContext.setErrorCode(1001);
            flowContext.setErrorMessage("Not authenticated");
        }
    }

    @Override
    public void fuckOut(FlowContext flowContext) {
        // Post-processing: metrics, logging, cleanup, etc.
    }
}
```

Register via: `barSkeletonBuilder.addInOut(new AuthInterceptor());`

Built-in interceptors include `DebugInOut` (logging), `StatActionInOut` (statistics), `ThreadMonitorInOut`, and `TimeRangeInOut`.

---

## Data Codec & Protocol Wrappers

The framework uses a pluggable `DataCodec` for serialization (default: Protobuf via jprotobuf).

### Wrapper Types
For primitive-like values, use protocol wrappers from `com.waaiu.net.framework.protocol.wrapper`:

| Java Type | Wrapper |
|---|---|
| `int` / `Integer` | `IntValue` |
| `long` / `Long` | `LongValue` |
| `boolean` / `Boolean` | `BoolValue` |
| `String` | `StringValue` |

### Custom Types
Annotate your Protobuf classes with `@ProtoFileMerge` for code generation support:

```java
@ProtobufClass
public class MyRequest {
    public int id;
    public String name;
}
```

---

## Extension Modules

### extension-room (Multiplayer Game Rooms)
Provides `Room`, `Player`, `RoomService` abstractions:

```java
// Room interface provides:
room.addPlayer(player);
room.removePlayer(player);
room.getPlayerById(userId);
room.hasSeat();
room.isReadyPlayers();
room.executeTask(runnable);         // thread-safe task execution
room.executeDelayTask(task, 1000);  // delayed task

// Operation pattern for game logic:
room.operation(MyOperationCode.START_GAME, flowContext);
```

### extension-domain-event
Decoupled event handling within a single JVM:
```java
// Define event source entity, register event handlers,
// and fire events that are processed by subscribers.
```

### extension-client
Interactive simulation client for load testing and debugging. Supports:
- Defining `InputCommandRegion` with predefined requests
- Pressure testing via `PressureKit`

---

## Spring Integration

Add `extension-spring` as a dependency and register `ActionFactoryBeanForSpring`:

```java
@Configuration
public class WanetConfig {
    @Bean
    public ActionFactoryBeanForSpring<?> actionFactoryBean() {
        return new ActionFactoryBeanForSpring<>();
    }
}
```

This allows `@ActionController` classes to also be Spring `@Component` beans, enabling full dependency injection. The framework's `DependencyInjectionPart` automatically detects the Spring context.

When using Spring, set the `actionFactoryBean` on `BarSkeletonBuilder`:

```java
builder.setActionFactoryBean(springActionFactoryBean);
```

---

## Code Generation (Multi-Language SDK)

The `extension-codegen` module generates client SDK code from your action definitions:

**Supported languages:**
- TypeScript (`.ts`)
- C# (`.cs`)
- GDScript (`.gd`)

**Generated artifacts:**
- Action classes with route constants and request/response methods
- Broadcast listener classes
- Error code enums

**Template files** are in `extension-codegen/src/main/resources/generate/{ts,csharp,gdscript}/`.

Usage:
```java
var generate = new TypeScriptDocumentGenerate();
generate.setPath("./target/code/ts");
generate.generate(document);
```

---

## Key Files Quick-Reference

| What | Path |
|---|---|
| Root POM | `pom.xml` |
| ActionController annotation | `core-framework/.../framework/annotations/ActionController.java` |
| ActionMethod annotation | `core-framework/.../framework/annotations/ActionMethod.java` |
| FlowContext interface | `core-framework/.../framework/core/flow/FlowContext.java` |
| BarSkeletonBuilder | `core-framework/.../framework/core/BarSkeletonBuilder.java` |
| ActionCommand record | `core-framework/.../framework/core/ActionCommand.java` |
| CmdInfo record | `core-framework/.../framework/core/CmdInfo.java` |
| Interceptor interface | `core-framework/.../framework/core/flow/ActionMethodInOut.java` |
| Broadcast API | `core-framework/.../framework/core/flow/FlowBroadcastCommunication.java` |
| Cross-service call API | `core-framework/.../framework/core/flow/FlowLogicCallCommunication.java` |
| Protocol wrappers | `core-framework/.../framework/protocol/wrapper/` |
| ExternalServerBuilder | `external-core/.../external/core/ExternalServerBuilder.java` |
| NetServerBuilder | `net-server/.../server/NetServerBuilder.java` |
| LogicServer interface | `net-server/.../server/LogicServer.java` |
| RunOne bootstrap | `run-one/.../app/RunOne.java` |
| Room interface | `extension-room/.../extension/room/Room.java` |
| Player interface | `extension-room/.../extension/room/Player.java` |
| Spring integration | `extension-spring/.../extension/spring/ActionFactoryBeanForSpring.java` |
| i18n properties | `core-framework/src/main/resources/waaiu.properties` |

---

## Common Pitfalls

1. **Route conflicts:** Each `(cmd, subCmd)` pair must be globally unique across all LogicServers. The framework detects duplicates at startup via `detectGlobalDuplicateRoutes()`.

2. **Unsupported parameter types:** `Set`, `Map`, and raw primitives (`int`, `long`) are **not** valid action method parameter or return types. Use `IntValue`, `LongValue`, `BoolValue`, `StringValue`, or Protobuf-annotated classes.

3. **Aeron MediaDriver required:** An Aeron `MediaDriver` must be running (embedded or external) before calling `RunOne.startup()`.

4. **FlowContext is request-scoped:** Do not store or share `FlowContext` instances across threads or requests.

5. **Thread safety in Rooms:** Use `room.executeTask(runnable)` or the `OperationContext` pattern for thread-safe room mutations. Direct map operations on room state from action methods are NOT safe.

6. **Spring integration order:** When using Spring, ensure `ActionFactoryBeanForSpring` is registered as a bean **before** the `BarSkeleton` is built, so that `DependencyInjectionPart` is properly initialized.

7. **File encoding:** All source files must be UTF-8 **without BOM**. PowerShell's `Set-Content` with `-Encoding UTF8` adds a BOM on older versions — use `[System.IO.File]::WriteAllText()` with `UTF8Encoding($false)` instead.

8. **Local install required:** Before other projects can reference wanet modules, run `mvn clean install -Dgpg.skip=true -Dmaven.test.skip=true` from the wanet root to install all artifacts to your local `~/.m2` repository.
