# Roadmap

This roadmap communicates sequence, not delivery dates. Everything below is planned unless explicitly marked complete.

## Phase 0 — Foundation and evidence

**Current phase: documentation prepared; implementation not started.**

- [x] State the initial vision and design principles.
- [x] Separate planned capabilities from implemented work.
- [x] Select the Apache License 2.0.
- [ ] Choose the prototype language and record the decision.
- [ ] Define representative datasets and benchmark methodology.
- [ ] Create lightweight ADR and benchmark-result templates.

## Milestone 1 — Benchmarkable cubic storage/loading prototype

Goal: establish whether the proposed spatial and persistence model is viable with reproducible evidence.

- [ ] Define coordinate, cube-key, and region-index models.
- [ ] Specify a versioned storage format with integrity metadata.
- [ ] Implement deterministic dense, sparse, vertical, and fragmented workloads.
- [ ] Implement cold/warm reads, writes, updates, and validation.
- [ ] Add corruption and interrupted-write tests.
- [ ] Measure latency percentiles, throughput, encoded size, compression, and memory.
- [ ] Publish methodology, raw results, and known limitations.

Exit criteria:

- a clean checkout can reproduce the benchmark;
- results contain the required environment metadata;
- format invariants and recovery behavior are tested;
- at least one baseline and one alternative are compared;
- findings are documented even if the design is rejected.

## Milestone 2 — Streaming world service

- [ ] Prototype load prioritization and lifecycle states.
- [ ] Establish memory and I/O budgets.
- [ ] Exercise long-distance travel and vertical streaming.
- [ ] Add observability for queues, cache behavior, and stalls.

## Milestone 3 — Parallel simulation experiments

- [ ] Define ownership and cross-boundary effects.
- [ ] Compare staged and task-graph approaches.
- [ ] Test determinism, fault behavior, and scaling.
- [ ] Publish contention and latency findings.

## Milestone 4 — Rendering and networking experiments

- [ ] Prototype asynchronous cube meshing and prioritization.
- [ ] Prototype relevance-based streaming and deltas.
- [ ] Define graceful degradation under frame, CPU, and bandwidth pressure.

## Milestone 5 — Native mod platform skeleton

- [ ] Define lifecycle, version negotiation, and capability discovery.
- [ ] Add diagnostics and compatibility-test tooling.
- [ ] Build small example mods against public contracts only.
- [ ] Evaluate isolation and permission models.

## Milestone 6 — Adaptive world intelligence research

- [ ] Define AI levels and operator budgets.
- [ ] Prototype shared navigation, perception, and memory services.
- [ ] Test mob and settlement consumers.
- [ ] Measure simulation cost and degradation behavior.

## Exploratory — Legacy bridge

- [ ] Identify high-value compatibility targets.
- [ ] Document semantic gaps and unacceptable constraints.
- [ ] Build a limited proof of concept only if evidence supports it.

No compatibility percentage or release commitment should be made until this work exists.

