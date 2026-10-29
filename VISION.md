# Vision

## The idea

Nexolith is intended to become a Minecraft Java Edition runtime and modding platform whose foundations are designed for genuinely three-dimensional worlds, modern multicore hardware, large simulations, shared world intelligence, and long-lived mod ecosystems.

It is not an independent game and not a NeoForge server with a collection of mandatory feature mods. A future Nexolith installation will combine an authorized vanilla Minecraft installation with Nexolith's original loader, built-in runtime systems, and locally applied patches. Vanilla provides the recognizable game and client foundation; Nexolith replaces core backend assumptions that cannot support the desired scale or architecture.

The project begins from a simple question: if vertical scale, entity count, world activity, AI complexity, and mod ambition were treated as first-order design constraints, what would Minecraft's internal platform need to look like?

## What success means

Nexolith succeeds if it can eventually support worlds that are taller, deeper, busier, more intelligent, and more moddable while retaining the qualities that make Minecraft compelling:

- simple actions combine into surprising systems;
- the world remains legible and responsive;
- exploration rewards curiosity;
- building rewards imagination;
- survival creates stories rather than administration;
- multiplayer feels like a shared place;
- mods can extend systems without bypassing or rebuilding the platform.

Performance is therefore a product feature, not the purpose by itself. Cube-native storage and loading, safe parallelism, optimized rendering and networking, and bounded AI services are valuable because together they create room for better play, larger worlds, and more ambitious mods.

## Runtime identity

Nexolith's core features must be part of the locally assembled runtime and initialize before user mods. Removing a mod from a `mods` directory must not disable cubic storage, restore vanilla column ownership, or remove the native lifecycle.

The intended runtime order is:

1. locate or obtain authorized vanilla files;
2. verify the selected Minecraft version;
3. apply Nexolith patches locally;
4. start the Nexolith runtime;
5. initialize the authoritative cubic backend; and
6. discover and load user-selected Nexolith mods.

No pre-patched Minecraft client or server is intended to be distributed.

## Long-term direction

### Native cubic world authority

World space is partitioned into cubic units that can be stored, loaded, generated, simulated, lit, meshed, rendered, and transmitted independently. No column object may own the lifecycle or authoritative data for cubes sharing an X/Z coordinate.

A limited vanilla or legacy column-shaped view may exist behind an isolated compatibility boundary. Such a view must be derived from cubes, non-authoritative, measurable, and unable to dictate native APIs or loading behavior. Unsupported assumptions should fail visibly rather than force permanent emulation.

### Parallel simulation

Simulation should use multiple cores through declared spatial ownership, stable inputs, messages, transactions, and staged reconciliation. Correctness, debuggability, and reproducibility take priority over impressive but fragile parallelism.

### Purpose-built pipelines

Rendering, persistence, lighting, world generation, and networking should share cube identity and lifecycle concepts while remaining separately measurable. Data formats and scheduling decisions should support locality, prioritization, streaming, observability, and graceful degradation.

### Adaptive and shared intelligence

A future core AI layer may offer adjustable levels—from lightweight reactive behavior to richer planning—so players and server operators can choose their performance and complexity balance. Shared world-intelligence services may expose bounded navigation, perception, memory, goals, group coordination, settlement context, and civilization knowledge to vanilla behavior and Nexolith mods.

AI is most valuable here as infrastructure integrated with cube ownership, activity levels, scheduling, and resource budgets. Distant or inactive regions should be able to degrade gracefully without every mod inventing a separate approximation system.

This is a planned direction, not an implemented system. Runtime AI must remain optional where practical, inspectable, bounded, diagnosable, and under operator control.

### A native mod platform

Mods should target explicit Nexolith contracts instead of depending directly on NeoForge or unstable Minecraft internals. The native API should support version negotiation, capability discovery, lifecycle boundaries, data registration, diagnostics, permissions, resource budgets, and automated compatibility testing.

An optional bridge for selected legacy access patterns or mods may be investigated. Compatibility is not promised, and the native design will not be frozen around column assumptions or a temporary host loader.

### Replaceable incubation infrastructure

NeoForm may provide the local machinery that reconstructs authorized vanilla Minecraft inputs and produces a recompilable development workspace. Nexolith owns its loader, patch series, runtime lifecycle, storage, API, and data formats from the beginning. NeoForge is reference material and a possible source of separately reviewed, suitably licensed components—not Nexolith's host loader or runtime foundation.

## Product principles

1. **Make native cubes foundational.** Core systems use cubes directly; compatibility views remain at the edge.
2. **Prove foundations before expanding scope.** Bootstrap reproducibility, storage, loading, and recovery evidence come before broad feature work.
3. **Publish measurements with claims.** Every major performance claim needs reproducible methodology, raw context, and baselines.
4. **Prefer explicit contracts.** Data ownership, scheduling, APIs, compatibility, licensing boundaries, and failure behavior should be documented.
5. **Make degradation graceful.** Large worlds, simulation, networking, rendering, and AI require understandable limits and adjustable budgets.
6. **Keep operators in control.** Servers need observability, configuration, recovery tools, and predictable resource use.
7. **Preserve Minecraft without distributing it.** Nexolith builds on a user's authorized installation and distributes only permitted original or appropriately licensed material.
8. **Protect the creative loop.** Technical novelty must serve play, community, and mod authorship.

## Non-goals for the current stage

- creating or distributing an independent replacement game;
- distributing vanilla or pre-patched Minecraft JARs, source trees, or assets;
- implementing core features as removable built-in mods;
- making NeoForge's API or event system Nexolith's permanent internal architecture;
- promising general NeoForge, Forge, vanilla-client, existing-world, or legacy-mod compatibility;
- claiming performance improvements before benchmarks exist;
- building adaptive AI before the cubic storage and lifecycle foundation is validated; or
- allowing a compatibility layer to become an authoritative column backend.
