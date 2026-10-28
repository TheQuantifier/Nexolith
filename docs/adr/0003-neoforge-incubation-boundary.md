# ADR 0003: NeoForge incubation boundary

- **Status:** Accepted
- **Date:** 2026-10-28

## Context

NeoForge and NeoForm provide valuable development, launch, patch-generation, and modding infrastructure. Reusing that infrastructure can shorten the path to a running Minecraft environment. However, allowing NeoForge types, events, registries, and lifecycles to become Nexolith's core contracts would make later independence require a rewrite.

General NeoForge mod compatibility would also create a permanent promise that conflicts with deep cubic-world changes.

## Decision

Nexolith may initially use NeoForge and NeoForm as replaceable incubation infrastructure.

NeoForge-specific dependencies belong in a dedicated platform module. Nexolith's cubic runtime, storage, world formats, scheduling, public API, and native mod lifecycle must remain in separate original modules with no NeoForge types in their public contracts.

Core runtime systems will be initialized as built-in platform features before user mod discovery. They will not be implemented as mandatory mods loaded through NeoForge's ordinary mod lifecycle.

General NeoForge mod compatibility is not promised. A future independent Nexolith bootstrap and loader may replace the temporary platform layer without rewriting the core.

## Consequences

- Early builds may use NeoForge internally while being clearly labeled as incubation builds.
- Platform adapters require explicit dependency boundaries and architecture tests.
- Sample Nexolith mods must compile against `nexolith-api`, not NeoForge APIs.
- Existing NeoForge mods may not load or behave correctly.
- Copied or modified NeoForge components retain their applicable licenses and require review.
- A later milestone will inventory and replace remaining bootstrap dependencies where justified.

## Alternatives considered

- **Permanent NeoForge fork:** not selected as the native architecture because it would couple Nexolith's identity and contracts to upstream internals.
- **Independent loader before any prototype:** deferred because it would spend substantial effort on bootstrap plumbing before validating the cubic backend.
- **Ordinary NeoForge overhaul mod:** rejected as the final model because foundational runtime systems must not be removable mods.
