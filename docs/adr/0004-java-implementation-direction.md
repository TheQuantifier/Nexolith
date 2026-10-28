# ADR 0004: Java implementation direction

- **Status:** Accepted
- **Date:** 2026-10-28

## Context

Nexolith will integrate deeply with Minecraft Java Edition classes, bytecode, registries, serialization, client/server lifecycles, and future Java mods. The initial platform tooling is also expected to use the Java ecosystem.

JavaScript remains useful for websites, tooling, or a future restricted scripting layer, but using it for the authoritative runtime would introduce an unnecessary cross-runtime boundary.

## Decision

Nexolith's loader, native API, cubic runtime, storage, and Minecraft integration will be implemented primarily in Java.

The exact JDK is not fixed by this ADR. The project will select the JDK required by the first supported Minecraft version and pin it through reproducible build tooling.

JavaScript or TypeScript may be used for ancillary tools, websites, installer interfaces, or later optional scripting when those choices do not compromise the core boundary.

## Consequences

- The project can interact directly with Minecraft and Java modding infrastructure.
- Contributors need a documented and reproducible JDK and Gradle environment.
- Runtime performance work must use profiling and benchmarks rather than assuming language choice guarantees performance.
- Any future scripting environment requires explicit sandboxing, capability, and resource-budget decisions.

## Alternatives considered

- **JavaScript as the core runtime:** rejected because it adds worker, memory-transfer, and Java-integration complexity to the central systems.
- **Native-language engine core:** deferred because the first goal is a deeply integrated Minecraft Java Edition runtime rather than an independent engine.
