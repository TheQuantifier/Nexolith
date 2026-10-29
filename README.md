# Nexolith

**A concept-stage Minecraft Java Edition runtime and modding platform built around native cubic worlds, scalable simulation, shared world intelligence, and measurable performance.**

> [!IMPORTANT]
> Nexolith is in the **early implementation stage**. Phase 1 is complete: the original loader and transformer launch Minecraft 26.3 with visible Nexolith branding, and the first native 3D cube geometry is implemented and tested. Minecraft's world backend is still vanilla; no cube storage, generation, lighting, rendering, networking, compatibility layer, or public release exists yet.

## What Nexolith is

Nexolith is intended to be assembled locally from:

1. an authorized Minecraft Java Edition installation or download;
2. Nexolith's original installer, loader, runtime components, and patch data; and
3. user-selected Nexolith mods.

Minecraft remains the gameplay and client foundation. Nexolith replaces deeply embedded backend assumptions with built-in runtime systems designed around independently stored, loaded, simulated, rendered, and transmitted three-dimensional cubes.

Nexolith is **not** intended to be:

- an independent game that redistributes or replaces Minecraft;
- a NeoForge server bundled with mandatory feature mods;
- a conventional mod that stores an authoritative column world and translates every native operation into cubes; or
- a pre-patched Minecraft client or server distributed to users.

The planned installer will obtain or locate authorized vanilla files, verify the selected version, and construct the Nexolith runtime locally. The project will distribute only material it is permitted to distribute.

## Why Nexolith?

Minecraft modding has repeatedly pushed against assumptions embedded deep in the game: column-owned world lifecycles, constrained vertical scale, mostly serial simulation, expensive world streaming, and APIs tied to implementation details. Nexolith asks what becomes possible when these assumptions are replaced at the runtime foundation instead of worked around by a collection of optional mods.

The aim is to preserve Minecraft's familiar mining, building, exploration, survival, and multiplayer experience while creating foundations for much larger vertical worlds, more active entities, richer civilizations, and ambitious mods. Native cubes, optimized streaming, parallel-safe ownership, and shared AI services are intended to reinforce one another rather than exist as isolated features.

## Project status

| Area | Status |
| --- | --- |
| Runtime and distribution model | Documented; not implemented |
| Java implementation direction | Java selected; Minecraft 26.3 runtime requires Java 25 |
| Original Nexolith loader bootstrap | Implemented and tested |
| Prism Launcher profile installer | Implemented, tested, and launch-verified with Minecraft 26.3 |
| Runtime transformation layer | Implemented; visibly brands Minecraft's window title and adds a dedicated bottom-left Nexolith Loader line through version-pinned transformations |
| Native cube geometry | Implemented and tested; not connected to Minecraft world storage yet |
| NeoForm vanilla workspace and local patch pipeline | Selected; setup not started |
| NeoForge relationship | Private reference mirror only; not a runtime dependency |
| Native cubic world authority | Documented; cube primitives exist, world authority not implemented |
| Cubic storage format | Planned; first backend prototype milestone |
| Three-dimensional streaming and cube lifecycle | Planned |
| Parallel simulation | Research planned |
| Rendering and networking optimization | Planned |
| Adaptive AI runtime | Concept only |
| Shared world-intelligence API | Concept only |
| Native Nexolith mod API | Concept only; the bootstrap SPI is experimental and is not the mod API |
| Legacy column or mod bridge | Optional; feasibility not established |
| Playable Nexolith client profile | Phase 1 transformed profile verified at the Minecraft 26.3 main menu |

## Architectural commitments

- **Cubes are authoritative.** Storage, loading, world generation, lighting, simulation ownership, meshing, networking relevance, and native APIs are designed around three-dimensional cubes.
- **Columns are compatibility views only.** A limited vanilla or legacy facade may exist at an isolated boundary, but it must not own data, lifecycle, or native API design.
- **Core features load before mods.** The cubic runtime is part of the locally assembled Nexolith platform, not a removable JAR in the `mods` folder.
- **Vanilla is the runtime foundation.** NeoForm may reconstruct authorized vanilla inputs locally for development and patch generation, but Nexolith owns its bootstrap, patch series, lifecycle, API, and runtime.
- **NeoForge is reference material, not the parent platform.** Selected suitably licensed ideas or components may be evaluated independently, but Nexolith does not link against or launch through NeoForge.
- **Measured performance.** Storage, loading, memory, simulation, rendering, networking, and AI budgets require reproducible benchmarks.
- **Parallel where safe.** Work should scale across cores through explicit ownership, snapshots, messages, and deterministic boundaries—not uncontrolled shared mutation.
- **AI as shared infrastructure.** Adjustable and bounded world-intelligence services may support mobs, settlements, factions, and civilization mods without each mod rebuilding navigation, perception, memory, and coordination.
- **Compatibility without permanent compromise.** Unsupported legacy behavior should fail visibly rather than silently force the native runtime back into column architecture.
- **Classic fun remains the test.** Scale and sophistication matter only when they improve the experience of playing and extending Minecraft.

## Planned runtime shape

```text
Authorized vanilla Minecraft files
                 +
Nexolith installer, patches, loader, and runtime
                 |
                 v
       Locally assembled Nexolith profile
                 |
        Native Nexolith mod API
                 |
    Authoritative cubic world runtime
       /         |          \
  Storage    Simulation    Streaming
       \         |          /
       Rendering, networking, and AI

Optional legacy access -> isolated compatibility facade -> cube runtime
```

## First implementation path

Before changing world storage, the project must prove that a clean checkout can:

1. obtain or reuse an authorized vanilla installation for one exact Minecraft version;
2. verify its expected metadata and hashes;
3. apply a minimal Nexolith patch locally;
4. launch a distinct development profile; and
5. reproduce the result without tracking or publishing Minecraft files or a completed patched JAR.

The first piece of that path is now present: `loader/` builds an original Apache-licensed bootstrap JAR, initializes Nexolith-owned extensions, and delegates to a launcher-provided vanilla main class. It has no Minecraft or NeoForge compile-time dependency. See [Phase 1: Nexolith Loader Bootstrap](docs/phase-1-loader.md).

## Building and installing the current loader bootstrap

On Windows:

```powershell
.\gradlew.bat test jar
```

On Linux or macOS:

```bash
./gradlew test jar
```

The bootstrap JAR is written to `loader/build/libs/nexolith-loader-0.0.1-SNAPSHOT.jar`.

To create a distinct **Nexolith Phase 1** instance in an existing Prism Launcher installation on Windows:

```powershell
.\gradlew.bat :installer:installPrismInstance
```

The installer builds the loader, adds it as an instance-local Prism library, selects `dev.nexolith.loader.NexolithBootstrap` as the main class, and references vanilla Minecraft 26.3. Prism remains responsible for account authentication, authorized game downloads, assets, and the Java 25 runtime. The installer refuses to overwrite an existing instance.

Phase 1 is complete: the profile reached the Minecraft 26.3 main menu on Java 25, the live game trace showed `NexolithBootstrap` delegating to vanilla's `Main.main`, and Nexolith's transformer changed the real window title to **Minecraft 26.3 | Nexolith Phase 1**. The main menu also displays **Nexolith Loader 0.0.1 (Phase 1)** on its own bottom-left line above Minecraft's version. The `cubic-core` module implements native cube positions, negative-coordinate mapping, cube bounds, and a dense 4,096-block local index. It is deliberately not connected to Minecraft's chunk backend yet.

The build also produces `transformer/build/libs/nexolith-transformer-0.0.1-SNAPSHOT.jar`, a self-contained Java agent installed only in the Nexolith profile. It bundles ASM under the BSD 3-Clause license documented in [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md).

The first backend prototype then evaluates a versioned cubic storage and loading model with deterministic datasets, integrity checks, fault injection, and repeatable benchmarks. Gameplay, rendering, networking, native mods, and AI follow only after this foundation produces evidence.

## First playable alpha target

The planned **Alpha 0.1 — Cubic Vanilla** is a future integration target, not the first coding milestone. It should require a legitimate Minecraft Java Edition installation and preserve a small, recognizable vanilla gameplay loop while using Nexolith's built-in cubic storage, lifecycle, generation, lighting, meshing, and streaming systems. It will target new Nexolith worlds and will not promise existing-world, NeoForge-mod, or vanilla-client compatibility.

## Documentation

- [Vision](VISION.md)
- [Architecture](ARCHITECTURE.md)
- [Roadmap](ROADMAP.md)
- [Architecture decisions](docs/adr/README.md)
- [Phase 1 loader bootstrap](docs/phase-1-loader.md)
- [Contributing](CONTRIBUTING.md)
- [AI-assisted contribution policy](AI_CONTRIBUTION_POLICY.md)
- [Community discussions](DISCUSSIONS.md)
- [Code of Conduct](CODE_OF_CONDUCT.md)

## Website

The planned project website is [nexolith.thequantifier.com](https://nexolith.thequantifier.com). It is not represented here as launched or operational.

## Contributing

The most useful contributions today are runtime-boundary critiques, bootstrap and patch-pipeline research, cubic workload definitions, benchmark proposals, licensing analysis, and small experiments tied to the documented milestones. Please read [CONTRIBUTING.md](CONTRIBUTING.md) before opening a proposal.

### AI-assisted development

Nexolith permits AI-assisted development and may contain AI-assisted code and documentation. Every contribution remains under human responsibility and must meet the same review, testing, security, licensing, and maintainability requirements regardless of the tools used. Individual contributors are not publicly labeled according to their use of AI. See the [AI-Assisted Contribution Policy](AI_CONTRIBUTION_POLICY.md) for the complete requirements.

## Licensing and upstream files

Original Nexolith work in this repository is licensed under the [Apache License 2.0](LICENSE). Upstream tools, libraries, or derived components retain their own applicable licenses and must be reviewed before inclusion. The Apache license does not grant rights to Minecraft or third-party material.

This repository must not contain vanilla or patched Minecraft JARs, decompiled Minecraft source trees, Minecraft assets, or other proprietary game files. Development-generated upstream artifacts belong in ignored local caches or workspaces.

## Unofficial project disclaimer

**NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.**

Nexolith is an independent, unofficial community project. “Minecraft” is a trademark of Microsoft. Users and contributors remain responsible for complying with the [Minecraft EULA](https://www.minecraft.net/eula) and [Minecraft Usage Guidelines](https://www.minecraft.net/usage-guidelines).
