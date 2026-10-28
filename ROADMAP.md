# Roadmap

This roadmap communicates sequence, not delivery dates. Everything below is planned unless explicitly marked complete.

## Phase 0 — Runtime identity and foundations

**Current phase: architecture documented; implementation not started.**

- [x] State the initial vision and product principles.
- [x] Select the Apache License 2.0 for original Nexolith work.
- [x] Define Nexolith as a locally patch-assembled Minecraft Java Edition runtime and modding platform.
- [x] Establish the authoritative native cubic world model.
- [x] Limit column-shaped access to an isolated compatibility facade.
- [x] Select Java as the implementation language direction.
- [x] Define NeoForge as replaceable incubation infrastructure rather than the native platform contract.
- [x] Record the initial architecture decisions.
- [ ] Choose one exact Minecraft target version and its required JDK.
- [ ] Select and document the initial NeoForge/NeoForm development pipeline.
- [ ] Review upstream licenses, patch formats, redistribution boundaries, and required notices.
- [ ] Add defensive ignore rules and artifact checks for generated Minecraft files.
- [ ] Define representative datasets and benchmark methodology.

## Milestone 0 — Reproducible authorized bootstrap

Goal: prove that Nexolith can construct and launch a development runtime locally without publishing Minecraft files.

- [ ] Locate an existing authorized launcher installation or download the selected version through authorized metadata.
- [ ] Verify input metadata and hashes.
- [ ] Generate an ignored local development workspace.
- [ ] Apply a minimal Nexolith patch that produces a visible development marker or diagnostic.
- [ ] Launch a distinct Nexolith development profile.
- [ ] Generate the permitted patch or transformation artifacts needed to reproduce the change.
- [ ] Reconstruct the same result from a clean checkout.
- [ ] Verify that source control and release artifacts contain no vanilla or completed patched Minecraft JARs, generated source trees, or Minecraft assets.

Exit criteria:

- a contributor with an authorized Minecraft installation can reproduce the development launch;
- upstream inputs and generated outputs remain outside version control;
- the patch is minimal, reviewable, and attributable;
- the temporary NeoForge boundary is isolated from reusable Nexolith modules; and
- the build reports actionable errors for missing or mismatched upstream versions.

## Milestone 1 — Benchmarkable cubic storage/loading prototype

Goal: establish whether the proposed spatial and persistence model is viable with reproducible evidence.

- [ ] Define block, local, cube-key, and three-dimensional region-index models.
- [ ] Test negative coordinates, overflow boundaries, and deterministic key behavior.
- [ ] Specify a versioned storage format with integrity metadata.
- [ ] Implement deterministic dense, sparse, vertical, and fragmented workloads.
- [ ] Implement cold/warm reads, writes, updates, and validation.
- [ ] Add corruption and interrupted-write tests.
- [ ] Measure latency percentiles, throughput, encoded size, compression, and memory.
- [ ] Compare at least one baseline and one alternative.
- [ ] Publish methodology, raw results, and known limitations.

Exit criteria:

- a clean checkout can reproduce the benchmark;
- results contain the required environment metadata;
- format invariants and recovery behavior are tested;
- no authoritative column representation exists; and
- findings are documented even if the candidate design is rejected.

## Milestone 2 — Minecraft cubic-runtime integration

Goal: make the native cube store authoritative inside a locally patched Minecraft runtime.

- [ ] Initialize the cubic runtime before user mod discovery.
- [ ] Route ordinary block access directly through cube and local coordinates.
- [ ] Implement controlled cube mutations and dirty tracking.
- [ ] Introduce a limited, instrumented vanilla column facade only where direct patches are not yet practical.
- [ ] Prevent the facade from owning data or cube lifecycle.
- [ ] Create a Nexolith-specific world and data version.
- [ ] Refuse silent fallback to vanilla region storage.
- [ ] Establish architectural tests that prevent core modules from depending on NeoForge or legacy-column modules.

## Milestone 3 — Cube-native world services

Goal: replace the remaining backend hot paths required for a playable cubic world.

- [ ] Implement deterministic cube-addressed world generation.
- [ ] Prototype three-dimensional load prioritization and lifecycle states.
- [ ] Establish memory and I/O budgets.
- [ ] Implement cube-native lighting boundaries and propagation scheduling.
- [ ] Implement asynchronous cube meshing and prioritized uploads.
- [ ] Exercise long-distance and vertical streaming.
- [ ] Add observability for queues, cache behavior, facade calls, stalls, and fallback attempts.
- [ ] Patch or reject hot paths that still require authoritative columns.

## Alpha 0.1 — Cubic Vanilla

Goal: deliver a small playable Minecraft experience whose backend is built around Nexolith cubes.

- [ ] Install as a distinct profile requiring a legitimate Minecraft Java Edition installation.
- [ ] Create new Nexolith worlds; existing-world conversion is out of scope.
- [ ] Preserve a bounded vanilla gameplay loop: movement, collision, mining, placing, inventory, and basic crafting.
- [ ] Save, close, reopen, and recover a cube-native world.
- [ ] Load, generate, light, mesh, and unload cubes independently in X, Y, and Z.
- [ ] Provide diagnostics for loaded cubes, lifecycle queues, memory, frame time, and legacy-facade use.
- [ ] Document supported hardware, target version, limitations, and known incompatibilities.
- [ ] State clearly that vanilla-client and general NeoForge-mod compatibility are not promised.

Alpha exit criteria:

- the cubic runtime is a built-in platform feature rather than a removable mod;
- native systems do not depend on authoritative columns;
- a new world survives repeated play, save, and reopen cycles;
- the client remains responsive during bounded generation, storage, and meshing workloads; and
- users can reproduce installation without receiving redistributed Minecraft files.

## Milestone 4 — Parallel simulation experiments

- [ ] Define spatial ownership and cross-boundary effects.
- [ ] Compare staged and task-graph approaches.
- [ ] Test determinism, fault behavior, and multicore scaling.
- [ ] Publish contention, throughput, and latency findings.
- [ ] Integrate only approaches that preserve debugging and recovery boundaries.

## Milestone 5 — Networking and server scaling

- [ ] Define cube-relevance and prioritization rules.
- [ ] Prototype versioned cube deltas and resynchronization.
- [ ] Apply bandwidth and processing budgets.
- [ ] Measure behavior under vertical separation, rapid travel, and dense activity.
- [ ] Define graceful degradation under CPU, memory, frame, and bandwidth pressure.

## Milestone 6 — Native Nexolith mod platform

- [ ] Define lifecycle, version negotiation, and capability discovery.
- [ ] Keep public API types independent of NeoForge.
- [ ] Add diagnostics, permissions, resource budgets, and compatibility-test tooling.
- [ ] Build small example mods against `nexolith-api` only.
- [ ] Define packaging and isolation expectations.
- [ ] Ensure the cubic runtime initializes before all user mods.

## Milestone 7 — Independent Nexolith bootstrap

- [ ] Inventory every remaining NeoForge bootstrap dependency.
- [ ] Replace mod discovery, lifecycle, launch, and installation responsibilities with original Nexolith components where justified.
- [ ] Preserve the native API, cubic runtime, storage, and world formats across the transition.
- [ ] Remove the temporary platform module without rewriting the core.
- [ ] Review release artifacts and licensing obligations again before publication.

## Milestone 8 — Adaptive world-intelligence research

- [ ] Define AI levels, operator controls, and resource budgets.
- [ ] Prototype shared navigation, perception, memory, goal, and group-coordination services.
- [ ] Integrate AI activity with cube ownership, distance, lifecycle, and degradation levels.
- [ ] Test vanilla-like mobs, settlements, factions, and mod consumers.
- [ ] Measure simulation cost, determinism, diagnostics, and degradation behavior.
- [ ] Keep AI optional where practical and prevent it from bypassing authoritative simulation rules.

## Exploratory — Legacy compatibility

- [ ] Identify high-value vanilla, NeoForge, Forge, or legacy-mod access patterns.
- [ ] Document semantic gaps, performance costs, and unacceptable constraints.
- [ ] Keep every adapter outside the native runtime contract.
- [ ] Build limited proofs of concept only when evidence supports them.
- [ ] Reject behavior that would restore column ownership or require hidden full-world translation.

No compatibility percentage or release commitment should be made until this work exists.
