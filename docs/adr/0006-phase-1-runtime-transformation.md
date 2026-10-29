# ADR 0006: Phase 1 Runtime Transformation

- Status: Accepted
- Date: 2026-10-28

## Context

The original Nexolith bootstrap proved that Nexolith could run before vanilla Minecraft, but it initially delegated to an unchanged game. Phase 1 needed a stronger, independently verifiable boundary: original three-dimensional cube logic plus a real modification to Minecraft, without making NeoForge the runtime foundation or distributing a modified Minecraft JAR.

Starting with a broad chunk-storage replacement would combine too many risks at once: transformation infrastructure, mappings, persistence, generation, lighting, rendering, networking, migration, and world corruption. A one-off pre-patched game JAR would also be the wrong distribution model.

## Decision

Phase 1 uses a Nexolith-owned Java instrumentation layer that runs before Minecraft classes load. Its first transformations are deliberately narrow: one modifies Minecraft 26.3's private `createTitle()` method to append `| Nexolith Phase 1` to the window title, and another adds a dedicated **Nexolith Loader 0.0.1 (Phase 1)** line above the vanilla version text on the title screen.

The transformation layer is separate from the bootstrap and from the pure `cubic-core` module. `cubic-core` defines the first native cube geometry, three-dimensional cube position, negative-coordinate mapping, local-coordinate mapping, and dense local block index. No column owns a `CubePos`.

ASM is bundled only inside the transformer artifact under its BSD 3-Clause license. No NeoForge or Forge runtime code is linked or copied. Vanilla Minecraft files remain unmodified on disk.

NeoForm remains the selected local workspace tool for later source-level Minecraft reconstruction and deep backend patches. Runtime transformation and source patches may coexist, but every hook must have a documented owner and version boundary.

## Phase 1 completion evidence

Phase 1 is complete when all of the following are true:

1. cube geometry and indexing tests cover positive and negative coordinates and every local index;
2. a transformer test modifies a synthetic Minecraft title method;
3. the installed Prism profile includes the original loader and transformer;
4. Minecraft 26.3 reaches its main menu; and
5. the operating-system window title visibly identifies the transformed Nexolith runtime; and
6. the main menu displays a dedicated Nexolith Loader identity line in the bottom-left corner.

These conditions were verified on October 28, 2026. They do not claim that Minecraft chunk storage, generation, lighting, rendering, simulation, or networking uses cubes yet.

## Consequences

- Nexolith now has an original, version-pinned mechanism for changing Minecraft at runtime.
- The first cube types can evolve behind tests before they become world-authoritative.
- Minecraft updates may break transformation targets, so required transformations fail visibly and must be tested against each supported version.
- Phase 2 can focus on the first real cube-backed subsystem rather than simultaneously inventing the loader boundary.
