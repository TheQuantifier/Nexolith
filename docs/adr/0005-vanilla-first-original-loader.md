# ADR 0005: Vanilla-first foundation and original loader

- **Status:** Accepted
- **Date:** 2026-10-28
- **Supersedes:** [ADR 0003](0003-neoforge-incubation-boundary.md) where it allowed NeoForge to host the initial bootstrap

## Context

Nexolith intends to replace foundational Minecraft backend assumptions rather than operate as a collection of ordinary mods. Beginning inside NeoForge would shorten initial setup, but it would also encourage NeoForge lifecycles, types, and packaging to become accidental Nexolith architecture.

NeoForm can reconstruct authorized vanilla Minecraft inputs locally and provide a patchable development workspace without making NeoForge the runtime host. Nexolith can therefore start from vanilla while still using established build machinery.

## Decision

Nexolith will own its loader and pre-game lifecycle from the first implementation phase. The loader, API, runtime, and patch series will contain no required NeoForge dependency.

Minecraft Java Edition 26.3 is the initial upstream target. Its runtime requires Java 25. The first loader bootstrap may compile to Java 17 bytecode while it remains independent of Minecraft classes, because that bytecode runs on Java 25. Modules compiled against Minecraft 26.3 will target Java 25.

NeoForm may be used as local development tooling to reconstruct and patch authorized vanilla inputs. Generated sources, assets, vanilla JARs, and completed patched JARs remain outside version control and release artifacts.

NeoForge is reference material only. A NeoForge component may be reused later only after an explicit architectural and licensing review, and it must not cause original Nexolith work to be represented as Apache-licensed when another license applies.

## Consequences

- Phase 1 includes an original Nexolith bootstrap and launcher-profile installer.
- The initial bootstrap delegates to a launcher-provided vanilla main class after running the Nexolith lifecycle.
- Authentication and Minecraft acquisition remain the responsibility of an authorized launcher or locally executed installer workflow.
- Nexolith must build more installation and lifecycle infrastructure itself.
- General NeoForge mod compatibility is neither inherited nor promised.
- The private NeoForge mirror remains useful for comparison and license-aware research but is not the codebase from which Nexolith is built.

## Alternatives considered

- **Build Nexolith as a NeoForge fork:** rejected because the initial runtime would inherit the wrong architectural center and extensive LGPL-derived implementation.
- **Create a normal NeoForge mod:** rejected because core runtime behavior must initialize beneath user mods.
- **Avoid all existing tooling:** rejected because using NeoForm as a local build tool does not make NeoForge Nexolith's runtime or license.
