# Architecture

## Status and scope

This document describes a **proposed architecture**, not implemented software. Names, boundaries, languages, formats, and interfaces are working hypotheses to be tested through prototypes and architecture decision records (ADRs).

## Guiding constraints

- World position must not assume a small or fixed vertical range.
- Components must expose measurable resource costs.
- Parallel execution must preserve clear ownership and failure boundaries.
- Persistence must be versioned, recoverable, and testable against corruption.
- Client/server communication must be authoritative, prioritized, and budget-aware.
- Mod APIs must not silently expose unrestricted engine internals.

## Proposed system map

```text
                         Native mods
                              |
                    Versioned capability API
                              |
       +----------------------+----------------------+
       |                      |                      |
 World intelligence     Simulation runtime     Tooling/diagnostics
       |                      |                      |
       +----------- Spatial world model ------------+
                      /        |        \
               Persistence  Networking  Rendering
                      \        |        /
                      Cubic region stream

 Optional legacy bridge -> isolated translation boundary -> native API
```

## Proposed components

### Spatial world model

Defines dimension-independent coordinates, cube identity, neighborhood queries, immutable snapshots or controlled mutations, and lifecycle states such as absent, queued, loaded, active, and retiring.

Open questions include cube dimensions, coordinate width, border behavior, cross-cube references, metadata layout, and whether simulation cells should always match storage cells.

### Persistence and streaming

Responsible for region indexing, cube encoding, compression, integrity checks, version migration, write batching, snapshots, and recovery. The first prototype belongs here.

Candidate designs should be evaluated with generated and captured workloads, including sparse vertical worlds, dense builds, fragmented edits, sequential travel, teleport-like random access, and repeated save cycles.

### Simulation runtime

A future scheduler may divide work by spatial ownership, dependency phase, and resource budget. A likely shape is staged execution:

1. capture stable inputs;
2. run independent work within declared regions;
3. record cross-boundary effects as messages or transactions;
4. reconcile effects at deterministic barriers;
5. publish a new observable state.

This model is provisional. Prototype evidence must determine whether it meets latency and gameplay requirements.

### Rendering

The proposed renderer consumes prioritized cube snapshots, builds or updates meshes asynchronously, performs occlusion and level-of-detail decisions, and limits uploads per frame. Rendering must not own authoritative world state.

### Networking

The proposed protocol prioritizes cube and entity updates by relevance, supports delta and version checks, applies bandwidth budgets, and makes resynchronization explicit. The server remains authoritative.

### World intelligence

A future shared service may provide bounded navigation, perception, memory, goals, group coordination, and settlement knowledge. Work must be budgeted by level and distance, with deterministic or recordable decisions where practical. Mods should be able to query capabilities without gaining unrestricted access to unrelated data.

### Native mod API and loader

The planned API surface should include version negotiation, declared capabilities, lifecycle events, data registration, diagnostics, resource budgets, and compatibility tests. Isolation options and permission boundaries require research.

### Legacy bridge

A legacy bridge is optional and should live outside the core contract. It may translate a deliberately limited subset of APIs. Unsupported behavior must fail visibly; the bridge must never imply universal compatibility.

## First prototype boundary

The storage/loading prototype should contain only:

- a coordinate and cube-key model;
- a versioned on-disk format;
- deterministic workload generation;
- read, write, update, and validation operations;
- a benchmark runner with machine-readable results;
- fault-injection tests for truncated or corrupted data;
- documentation sufficient to reproduce results.

It should avoid gameplay, graphics, networking, AI, general mod APIs, and speculative abstractions that the benchmark does not need.

## Benchmark contract

Every published result should record at least the commit, build mode, runtime version, hardware, operating system, dataset seed and shape, compression settings, cache state, sample count, and summary distribution. Report percentiles rather than only averages and retain raw results when practical.

## Decisions not yet made

- implementation language and runtime;
- cube dimensions and region grouping;
- serialization and compression formats;
- concurrency model;
- client rendering API;
- wire protocol;
- supported Minecraft versions or compatibility mechanism;
- mod packaging and sandboxing;
- AI implementation approach.

Major decisions should be captured as ADRs once the repository begins implementation.

