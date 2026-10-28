# Nexolith

**A concept-stage Minecraft engine and mod-loader project built around cubic worlds, scalable simulation, and a native modding platform.**

> [!IMPORTANT]
> Nexolith is in the **concept and research stage**. No engine, mod loader, compatibility layer, or playable build has been implemented yet. The first target is a benchmarkable cubic-world storage and loading prototype.

## Why Nexolith?

Minecraft modding has repeatedly pushed against assumptions embedded deep in the game: fixed-height world sections, mostly serial simulation, expensive world streaming, and APIs constrained by implementation details. Nexolith explores what becomes possible when those constraints are treated as design inputs rather than permanent rules.

The aim is not to replace what makes Minecraft fun. It is to preserve the familiar creative and survival experience while building foundations that can support much larger vertical worlds, more active entities, richer civilizations, and ambitious mods without making every feature reinvent core infrastructure.

## Project status

| Area | Status |
| --- | --- |
| Vision and architectural principles | Documented |
| Cubic storage format | Planned; first prototype milestone |
| Chunk loading and streaming | Planned |
| Parallel simulation | Research planned |
| Rendering optimization | Planned |
| Networking optimization | Planned |
| Adaptive AI runtime | Concept only |
| Shared world intelligence API | Concept only |
| Native mod API and loader | Concept only |
| Legacy mod bridge | Optional; feasibility not established |
| Playable client or server | Not implemented |

## Design pillars

- **Cubic worlds by default.** World data is divided into independently streamable three-dimensional regions rather than columns with a privileged vertical axis.
- **Measured performance.** Storage, loading, memory use, simulation throughput, rendering, and networking are designed around reproducible benchmarks.
- **Parallel where safe.** Simulation work should scale across cores through explicit ownership, scheduling, and deterministic boundaries—not uncontrolled shared-state concurrency.
- **AI as shared infrastructure.** Adjustable AI levels and common world-intelligence services can support mobs, settlements, factions, and civilization mods without each mod rebuilding the same systems.
- **Modding as a first-class contract.** A native, versioned API should be observable, testable, capability-aware, and designed alongside the engine.
- **Compatibility without permanent compromise.** A legacy bridge may be explored, but it must not dictate the native architecture or be promised before feasibility work.
- **Classic fun remains the test.** Scale and sophistication matter only if mining, building, exploration, survival, and multiplayer remain understandable and enjoyable.

## First milestone: cubic storage and loading prototype

The first milestone is intentionally narrow. It should demonstrate a world divided into cubic regions and produce repeatable evidence about:

- encoded size and compression ratio;
- cold and warm read latency;
- random and sequential load throughput;
- memory used per loaded cube;
- write and update cost;
- corruption detection and recovery behavior;
- performance under representative vertical and sparse-world workloads.

A successful prototype includes a documented format, deterministic test data, a benchmark harness, baseline results, and enough instrumentation for others to reproduce the measurements. It does **not** need rendering, gameplay, multiplayer, AI, or mod compatibility.

## Documentation

- [Vision](VISION.md)
- [Architecture](ARCHITECTURE.md)
- [Roadmap](ROADMAP.md)
- [Contributing](CONTRIBUTING.md)
- [AI-assisted contribution policy](AI_CONTRIBUTION_POLICY.md)
- [Community discussions](DISCUSSIONS.md)
- [Code of Conduct](CODE_OF_CONDUCT.md)

## Website

The planned project website is [nexolith.thequantifier.com](https://nexolith.thequantifier.com). It is not represented here as launched or operational.

## Contributing

The most useful contributions today are design critiques, benchmark proposals, workload definitions, prior-art references, and small experiments tied to the first milestone. Please read [CONTRIBUTING.md](CONTRIBUTING.md) before opening a proposal.

### AI-assisted development

Nexolith permits AI-assisted development and may contain AI-assisted code and documentation. Every contribution remains under human responsibility and must meet the same review, testing, security, licensing, and maintainability requirements regardless of the tools used. Individual contributors are not publicly labeled according to their use of AI. See the [AI-Assisted Contribution Policy](AI_CONTRIBUTION_POLICY.md) for the complete requirements.

## License

Nexolith is licensed under the [Apache License 2.0](LICENSE). This includes an explicit patent grant; see the license text for the complete terms.

## Unofficial project disclaimer

Nexolith is an independent, unofficial community project. It is not affiliated with, endorsed by, sponsored by, or approved by Mojang Studios or Microsoft. “Minecraft” is a trademark of Microsoft. This repository does not distribute Minecraft code, assets, or game files, and users remain responsible for complying with Minecraft's EULA and usage guidelines.

