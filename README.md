# Nexolith

**A concept-stage Minecraft Java Edition runtime and modding platform built around native cubic worlds, scalable simulation, shared world intelligence, and measurable performance.**

> [!IMPORTANT]
> Nexolith is in the **concept and research stage**. No loader, patched runtime, cubic backend, compatibility layer, or playable build has been implemented yet. All capabilities described below are planned unless explicitly marked implemented.

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
| Java implementation direction | Selected; target JDK depends on the chosen Minecraft version |
| NeoForge incubation boundary | Documented; integration not started |
| Authorized vanilla bootstrap and local patch pipeline | Planned |
| Native cubic world authority | Documented; not implemented |
| Cubic storage format | Planned; first backend prototype milestone |
| Three-dimensional streaming and cube lifecycle | Planned |
| Parallel simulation | Research planned |
| Rendering and networking optimization | Planned |
| Adaptive AI runtime | Concept only |
| Shared world-intelligence API | Concept only |
| Native Nexolith mod API and loader | Concept only |
| Legacy column or mod bridge | Optional; feasibility not established |
| Playable Nexolith client or server profile | Not implemented |

## Architectural commitments

- **Cubes are authoritative.** Storage, loading, world generation, lighting, simulation ownership, meshing, networking relevance, and native APIs are designed around three-dimensional cubes.
- **Columns are compatibility views only.** A limited vanilla or legacy facade may exist at an isolated boundary, but it must not own data, lifecycle, or native API design.
- **Core features load before mods.** The cubic runtime is part of the locally assembled Nexolith platform, not a removable JAR in the `mods` folder.
- **NeoForge is replaceable incubation infrastructure.** Nexolith may initially use NeoForge and NeoForm tooling for development, launch, and patch generation while keeping its API, cubic runtime, storage, and lifecycle independent.
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

The first backend prototype then evaluates a versioned cubic storage and loading model with deterministic datasets, integrity checks, fault injection, and repeatable benchmarks. Gameplay, rendering, networking, native mods, and AI follow only after this foundation produces evidence.

## First playable alpha target

The planned **Alpha 0.1 — Cubic Vanilla** is a future integration target, not the first coding milestone. It should require a legitimate Minecraft Java Edition installation and preserve a small, recognizable vanilla gameplay loop while using Nexolith's built-in cubic storage, lifecycle, generation, lighting, meshing, and streaming systems. It will target new Nexolith worlds and will not promise existing-world, NeoForge-mod, or vanilla-client compatibility.

## Documentation

- [Vision](VISION.md)
- [Architecture](ARCHITECTURE.md)
- [Roadmap](ROADMAP.md)
- [Architecture decisions](docs/adr/README.md)
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
