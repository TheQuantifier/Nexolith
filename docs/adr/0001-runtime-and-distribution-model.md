# ADR 0001: Runtime and distribution model

- **Status:** Accepted
- **Date:** 2026-10-28

## Context

Nexolith intends to preserve Minecraft Java Edition's recognizable gameplay and client foundation while replacing backend assumptions that constrain vertical scale, streaming, simulation, AI, and mod architecture. Distributing Minecraft or a completed modified game is outside Nexolith's intended distribution model and must not be part of project releases.

The project must also avoid being mistaken for an independent game or a server bundle whose foundational features are removable mods.

## Decision

Nexolith will be designed as a Minecraft Java Edition runtime and modding platform assembled locally from:

1. an authorized vanilla installation or download;
2. Nexolith's original installer, loader, runtime components, and patch data; and
3. user-selected Nexolith mods.

The planned installer will locate or obtain a supported vanilla version through authorized sources, verify the inputs, apply Nexolith changes locally, and create a distinct Nexolith profile.

The public project and its releases must not contain vanilla or completed patched Minecraft JARs, generated Minecraft source workspaces, or Minecraft assets. Original Nexolith work, permitted dependencies, patch data, metadata, and installer logic may be distributed subject to their applicable licenses.

## Consequences

- Users will require a legitimate supported Minecraft Java Edition installation.
- Client and server installation requires an installer or reproducible local patch pipeline.
- Nexolith must pin and validate exact supported upstream versions.
- Release engineering must inspect artifacts for prohibited upstream content.
- The Apache License 2.0 applies only to original Nexolith work, not Minecraft or third-party material.
- Nexolith will maintain a prominent unofficial-project disclaimer and links to the Minecraft EULA and Usage Guidelines.

## Alternatives considered

- **Independent game:** rejected because it abandons the intended vanilla gameplay foundation and changes the product identity.
- **Pre-patched game distribution:** rejected because the project does not intend to distribute a modified Minecraft client or server.
- **Mandatory mod bundle:** rejected because core runtime behavior must initialize before and underneath user mods.
