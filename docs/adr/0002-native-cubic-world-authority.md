# ADR 0002: Native cubic world authority

- **Status:** Accepted
- **Date:** 2026-10-28

## Context

Nexolith's primary architectural purpose is to remove column ownership from world storage, lifecycle, streaming, generation, lighting, simulation, meshing, networking relevance, and native mod contracts.

A design that retains an authoritative vanilla column world and translates every action into cubes would preserve the constraints Nexolith is intended to replace. It would also create duplicate state, synchronization cost, hidden loading, and ambiguous ownership.

## Decision

Three-dimensional cubes are the authoritative world unit throughout the Nexolith runtime.

Native systems will resolve block positions directly to a cube and local position. Storage, lifecycle, generation, lighting, simulation ownership, meshing, networking relevance, and native APIs will use cube identity without routing through a column representation.

A limited `LegacyColumnView` may exist only inside an isolated compatibility module. It must be derived from authoritative cubes, non-owning, lazy, measurable, and unable to force a shared vertical lifecycle. Supported writes become controlled cube mutations; no synchronized column copy is maintained.

Unsupported legacy assumptions must fail visibly.

## Consequences

- Deep Minecraft patches will be required; ordinary mod hooks will not be sufficient for all backend systems.
- Some vanilla systems and existing mods may be incompatible until deliberately adapted.
- Native APIs must not expose column ownership as a fundamental contract.
- Architecture tests should prevent core modules from importing legacy compatibility modules.
- Diagnostics and benchmarks will measure legacy-facade calls and costs.
- Nexolith worlds require their own format and data version and must not silently fall back to vanilla region storage.

## Alternatives considered

- **Authoritative columns with cubic sections:** rejected because columns would still control lifecycle and access.
- **Dual authoritative stores:** rejected because synchronization and recovery would be unsafe and expensive.
- **Transparent universal emulation:** rejected because hidden compatibility costs would dictate the native architecture.
