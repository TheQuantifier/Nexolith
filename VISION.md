# Vision

## The idea

Nexolith explores a Minecraft-compatible creative and survival platform whose foundations are designed for genuinely three-dimensional worlds, modern multicore hardware, large simulations, and long-lived mod ecosystems.

The project begins from a simple question: if height, entity count, world activity, and mod complexity were not afterthoughts, what would the engine need to look like?

## What success means

Nexolith succeeds if it can eventually support worlds that are taller, deeper, busier, and more moddable while retaining the qualities that make classic Minecraft compelling:

- simple actions combine into surprising systems;
- the world remains legible and responsive;
- exploration rewards curiosity;
- building rewards imagination;
- survival creates stories rather than administration;
- multiplayer feels like a shared place.

Performance is therefore a product feature, not the purpose by itself. Architecture should create room for fun, discovery, and mod authorship.

## Long-term direction

### Native cubic chunks

World space is partitioned into cubic units that can be stored, loaded, simulated, rendered, and transmitted independently. The design should handle dense settlements, immense caverns, floating worlds, and sparse extremes without assuming a fixed vertical band.

### Parallel simulation

Simulation should use multiple cores through declared ownership and staged work. Correctness, debuggability, and reproducibility take priority over impressive but fragile parallelism.

### Purpose-built pipelines

Rendering, persistence, and networking should share spatial concepts while remaining separately measurable. Data formats and scheduling decisions should support locality, streaming, prioritization, and graceful degradation.

### Adaptive intelligence

A core AI layer may offer adjustable levels—from lightweight reactive behavior to richer planning—so servers and players can choose their performance/complexity balance. Shared world intelligence could expose navigation, perception, memory, group coordination, settlement context, and other services to mobs and civilization mods.

This is a planned direction, not an implemented system. AI must remain optional, inspectable, bounded, and safe for server operators.

### A native mod platform

Mods should target explicit, versioned contracts instead of depending on unstable internals. The native API should support capability discovery, lifecycle boundaries, diagnostics, permissions, and automated compatibility testing.

An optional bridge for selected legacy mods may be investigated. Compatibility is not promised, and the native design will not be frozen around legacy assumptions.

## Product principles

1. **Prove foundations before expanding scope.** Storage and loading evidence comes before engine ambition.
2. **Publish measurements with claims.** Every major performance claim needs reproducible methodology and baselines.
3. **Prefer explicit contracts.** Data ownership, scheduling, APIs, compatibility, and failure behavior should be documented.
4. **Make degradation graceful.** Large worlds and complex AI must have understandable limits and adjustable budgets.
5. **Keep operators in control.** Servers need observability, configuration, recovery tools, and predictable resource use.
6. **Protect the creative loop.** Technical novelty must serve play and mod authorship.

## Non-goals for the current stage

- shipping a playable replacement client;
- promising compatibility with existing loaders or every legacy mod;
- choosing a final programming language or graphics API without prototypes;
- distributing proprietary Minecraft code or assets;
- claiming performance improvements before benchmarks exist;
- building adaptive AI before the world-storage foundation is validated.

