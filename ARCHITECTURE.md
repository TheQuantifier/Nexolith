# Architecture

## Status and scope

This document describes a **proposed architecture**, not implemented software. Nexolith currently contains documentation and an icon, but no loader, patches, runtime, storage backend, or playable profile.

The accepted direction is a Minecraft Java Edition runtime assembled locally from authorized vanilla files plus Nexolith's original installer, loader, runtime components, and patches. NeoForge may serve as temporary incubation infrastructure, but it must not become the owner of Nexolith's native contracts or backend.

See the accepted decisions in [`docs/adr`](docs/adr/README.md).

## Foundational invariants

1. Cubes are the authoritative spatial, storage, streaming, and lifecycle unit.
2. No X/Z column owns cube data or forces all vertical cubes to share a lifecycle.
3. Native systems access cubes directly; they do not translate every operation through a legacy column model.
4. Column-shaped access is allowed only through an isolated, non-authoritative compatibility facade.
5. Core runtime systems initialize before user mods and cannot be disabled as ordinary mods.
6. NeoForge-specific types and lifecycles must not appear in the Nexolith public API or reusable core.
7. A clean checkout must be able to construct a development runtime without tracking or publishing Minecraft files or completed patched JARs.
8. Unsupported compatibility behavior must fail visibly rather than silently compromise the native architecture.

## Runtime and distribution model

```text
Authorized Minecraft installation or download
                      |
             verify version and hashes
                      |
       apply Nexolith patches on the user's machine
                      |
          locally assembled Nexolith profile
                      |
        initialize built-in cubic runtime
                      |
       initialize native loader and mod API
                      |
             load user-selected mods
```

The public project may distribute original Nexolith code, permitted dependencies, patch data, metadata, and installer logic. It must not publish vanilla or patched Minecraft JARs, generated Minecraft source workspaces, or Minecraft assets.

Development caches and generated upstream workspaces must remain ignored and external to published artifacts. The installer should prefer authorized Mojang manifests or an existing legitimate launcher installation and verify every supported input version.

## Proposed system map

```text
                           Nexolith mods
                                 |
                    Versioned Nexolith capability API
                                 |
          +----------------------+----------------------+
          |                      |                      |
  World intelligence     Simulation runtime     Tooling/diagnostics
          |                      |                      |
          +----------- Authoritative CubeWorld --------+
                         /       |       \
                 Persistence  Streaming  Generation
                         \       |       /
                   Lighting, meshing, networking
                                 |
                  Minecraft runtime integration
                                 |
                     Patched vanilla foundation

Temporary NeoForge bootstrap -> platform boundary -> Nexolith runtime
Optional legacy access       -> compatibility facade -> CubeWorld
```

## Proposed module boundaries

```text
runtime/cubic-core
runtime/storage
runtime/streaming
runtime/worldgen
runtime/lighting
runtime/simulation
runtime/networking
api/nexolith-api

minecraft/vanilla-adapter
minecraft/patches/<version>
loader/bootstrap
loader/discovery

platform/neoforge-bootstrap     temporary and replaceable
compat/legacy-column            optional and isolated
```

Core runtime and API modules must not depend on the NeoForge platform or legacy compatibility modules. Architecture tests should enforce dependency direction once implementation begins.

## Bootstrap order

The planned runtime startup order is:

1. validate the selected Minecraft and Nexolith versions;
2. initialize patched Minecraft entry points;
3. initialize Nexolith registries and the authoritative cubic runtime;
4. validate the world format and recovery state;
5. initialize storage, streaming, lighting, simulation, and diagnostics;
6. initialize the native Nexolith mod lifecycle; and
7. discover and load user-selected mods.

If the cubic runtime cannot initialize, Nexolith should stop with a diagnostic error. It must not silently fall back to vanilla column storage.

## Authoritative cubic world model

### Coordinates and access

The core model defines dimension-aware block coordinates, cube coordinates, local coordinates, cube identity, neighborhood queries, controlled mutations, and lifecycle states such as absent, requested, loading, loaded, active, saving, and retiring.

Ordinary block access maps a block position directly to one cube and one local position. This is indexing, not conversion between duplicate world representations. Negative-coordinate behavior, overflow boundaries, and camera-relative rendering require explicit tests.

Open questions include cube dimensions, coordinate width, region grouping, border behavior, cross-cube references, metadata layout, and whether simulation cells always match storage cubes.

### Legacy column facade

Some retained vanilla systems or future compatibility targets may request an X/Z column-shaped object. A `LegacyColumnView` may expose only the required behavior while delegating directly to authoritative cubes.

The facade must be:

- non-authoritative and unable to own cube lifetime;
- isolated from native APIs and core modules;
- lazy and non-copying where practical;
- instrumented so calls, costs, and unsupported operations are visible;
- unable to force every cube in a vertical line to load; and
- replaceable by direct cube-native patches in hot paths.

Writes through an allowed facade operation become ordinary controlled cube mutations. No synchronized column copy should exist.

## Core runtime components

### Persistence and streaming

Responsible for three-dimensional region indexing, cube encoding, compression, integrity metadata, version migration, write batching, snapshots, recovery, prioritization, memory budgets, and lifecycle transitions.

Candidate designs should be evaluated with deterministic dense, sparse, vertical, fragmented, travel, teleport-like, and repeated-save workloads. Native persistence must never emit vanilla region files for Nexolith worlds.

### World generation

Generation must be addressable by cube, deterministic for a given seed and version, and independent of request order. Cross-cube features require explicit ownership and reconciliation rather than hidden dependence on column generation order.

### Lighting and meshing

Lighting propagation, dirty tracking, mesh generation, upload prioritization, occlusion, and future level-of-detail decisions use cube identity. Rendering consumes snapshots and must not own authoritative world state.

### Simulation runtime

A future scheduler may divide work by spatial ownership, dependency phase, and resource budget. A likely provisional shape is:

1. capture stable inputs;
2. run independent work within declared regions;
3. record cross-boundary effects as messages or transactions;
4. reconcile effects at deterministic barriers; and
5. publish a new observable state.

Correctness, debugging, and reproducibility take priority over uncontrolled shared-state concurrency.

### Networking

The proposed protocol prioritizes cubes and entities by relevance, supports deltas and version checks, applies bandwidth budgets, and makes resynchronization explicit. The server remains authoritative. Once Nexolith changes world representation and packets, vanilla-client compatibility must not be assumed.

### World intelligence

A future shared service may provide bounded navigation, perception, memory, goals, group coordination, settlement knowledge, and adjustable AI levels. The service should integrate with cube activity, ownership, scheduling, and degradation budgets so that native and modded actors share infrastructure.

AI remains planned. It must be observable, diagnosable, bounded, configurable, and unable to bypass authoritative simulation rules.

### Native mod API and loader

The planned API surface includes version negotiation, declared capabilities, lifecycle events, data registration, diagnostics, resource budgets, permissions, and compatibility tests. Public types must be owned by Nexolith rather than re-exporting NeoForge APIs.

Mods execute above the initialized cubic runtime. The runtime itself is not a collection of mandatory mods.

## NeoForge incubation boundary

NeoForge and NeoForm tooling may initially provide a development workspace, launch support, patch generation, and temporary bootstrap services. Nexolith should use that leverage to reach a running Minecraft environment without treating NeoForge events, registries, capabilities, networking wrappers, or mod discovery as permanent native contracts.

The temporary platform module may depend on NeoForge. Reusable runtime, storage, and API modules may not. General NeoForge mod compatibility is not promised.

An independent Nexolith loader can later replace the temporary bootstrap if the core remains isolated. Copied or modified upstream code retains its applicable license; original Nexolith modules do not erase upstream obligations.

## First bootstrap milestone

Before backend work begins, a clean checkout should be able to:

- select one exact supported Minecraft version;
- obtain or reuse authorized vanilla inputs;
- verify expected metadata and hashes;
- generate an ignored local development workspace;
- apply one minimal Nexolith patch;
- launch a distinct development profile; and
- prove that published artifacts contain no vanilla or completed patched game JAR.

## First backend prototype boundary

The storage/loading prototype should contain only:

- a coordinate and cube-key model;
- a versioned three-dimensional on-disk format;
- deterministic workload generation;
- read, write, update, and validation operations;
- a benchmark runner with machine-readable results;
- fault-injection tests for truncated or corrupted data; and
- documentation sufficient to reproduce results.

It should avoid gameplay features, broad mod APIs, adaptive AI, and speculative abstractions that the benchmark does not need.

## Benchmark contract

Every published result should record at least the commit, Minecraft and Nexolith versions when applicable, build mode, runtime version, hardware, operating system, dataset seed and shape, compression settings, cache state, sample count, and summary distribution. Report percentiles rather than only averages and retain raw results when practical.

## Decisions still open

- initial Minecraft target version and its required JDK;
- exact NeoForge or NeoForm incubation approach;
- cube dimensions and region grouping;
- serialization and compression formats;
- concurrency model;
- packet and client synchronization design;
- mod packaging, isolation, and sandboxing;
- supported legacy compatibility targets; and
- AI implementation approach.
