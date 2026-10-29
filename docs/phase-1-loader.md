# Phase 1: Nexolith Loader Bootstrap

## Status

**Complete.** The original loader bootstrap, runtime transformer, native cube foundation, and Prism Launcher profile installer are implemented, tested, and launch-verified. The **Nexolith Phase 1** profile reaches the Minecraft 26.3 main menu with a visibly transformed window title. No NeoForge or Forge runtime code is used.

## Purpose

Phase 1 proves that Nexolith owns the process that runs before Minecraft. The Nexolith bootstrap:

1. starts as the launcher-selected main class;
2. creates a Nexolith launch context;
3. discovers original Nexolith extensions through Java's `ServiceLoader` mechanism;
4. initializes those extensions before the game; and
5. delegates to the authorized vanilla Minecraft main class.

The bootstrap has no compile-time dependency on Minecraft, NeoForge, Forge, or another mod loader. It contains no Minecraft code or assets and does not download or redistribute them.

## Prism profile installer

Run:

```powershell
.\gradlew.bat :installer:installPrismInstance
```

The installer creates a new Prism instance rather than modifying a vanilla instance. Its component metadata:

- references vanilla Minecraft 26.3;
- adds the Nexolith loader JAR as an instance-local library;
- selects `dev.nexolith.loader.NexolithBootstrap` as the launch main class;
- starts the self-contained Nexolith transformer as a Java agent before Minecraft classes load; and
- refuses to overwrite an existing instance.

Prism continues to provide:

- a legitimate Minecraft Java Edition 26.3 installation;
- the Minecraft client JAR and its declared libraries on the runtime class path;
- the authentication, asset, game-directory, and version arguments normally supplied by the launcher; and
- Java 25 for the Minecraft 26.3 runtime.

The bootstrap, cube primitives, and transformer currently compile to Java 17 bytecode because they contain no compile-time Minecraft linkage. Java 17 bytecode runs on the Java 25 runtime required by Minecraft 26.3. Nexolith modules that compile against Minecraft 26.3 will target Java 25.

## Native cube foundation

The original `cubic-core` module now defines:

- 16×16×16 cube geometry and a volume of 4,096 blocks;
- immutable three-dimensional `CubePos` coordinates;
- floor-based block-to-cube mapping that behaves correctly below zero;
- local coordinates from 0 through 15 on each axis;
- cube block bounds; and
- a dense, reversible local block index.

Automated tests visit every local block index and verify positive, boundary, and negative world coordinates. These are native cube primitives, not wrappers around Minecraft columns. They are not connected to Minecraft storage or lifecycle yet.

## Minecraft transformation

The Phase 1 transformer targets Minecraft 26.3's `net.minecraft.client.Minecraft#createTitle()` method and appends `| Nexolith Phase 1` to its returned value. A second version-pinned transformation targets `TitleScreen#extractRenderState(...)` and draws **Nexolith Loader 0.0.1 (Phase 1)** as a separate bottom-left line above Minecraft's own version. These transformations change loaded classes in memory and do not alter or redistribute the Minecraft JAR.

The target is intentionally narrow and version-pinned. Failure to find the required method is treated as an incompatible Minecraft version rather than silently pretending that Nexolith loaded correctly.

## Launch verification

Phase 1 was launch-verified on October 28, 2026. The generated profile:

- ran on Prism's Java 25 runtime;
- reached the Minecraft 26.3 main menu;
- included the instance-local `nexolith-loader-0.0.1-SNAPSHOT.jar` on the runtime class path;
- started `nexolith-transformer-0.0.1-SNAPSHOT.jar` as a Java agent;
- showed `NexolithBootstrap.main` and `NexolithBootstrap.launch` immediately above vanilla `Main.main` in the live game trace; and
- displayed **Minecraft 26.3 | Nexolith Phase 1** as the operating-system window title; and
- displayed **Nexolith Loader 0.0.1 (Phase 1)** on its own bottom-left main-menu line.

This proves that the original Nexolith entry point controls startup and that the Nexolith-owned transformer changes a real Minecraft class. It does not claim that Minecraft's world backend uses cubes yet.

## What comes next

The next development step is the authorized NeoForm workspace and the first cube-backed integration seam. That work should select one narrow subsystem—without claiming full chunk replacement—so reconstruction, source patching, runtime transformation, launch, and artifact boundaries remain independently testable.

Future milestones must distinguish this transformed but vanilla-backed runtime from a world-authoritative cubic runtime and from the eventual cubic-world alpha.

## Licensing

The Nexolith loader, transformer, installer, and cube modules are original Nexolith work licensed under Apache License 2.0. The transformer bundles ASM under its BSD 3-Clause license and reproduces that notice inside the artifact. No NeoForge code is linked or copied. Future use of NeoForm is limited to locally reconstructing and patching authorized Minecraft inputs; generated Minecraft sources, game JARs, assets, and completed patched JARs must not be committed or distributed.
