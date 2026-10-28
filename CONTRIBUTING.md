# Contributing to Nexolith

Thank you for helping shape Nexolith. The project is currently at the concept and research stage, so precise questions, counterexamples, benchmark designs, and narrowly scoped experiments are especially valuable.

## Before contributing

1. Read the [vision](VISION.md), [architecture](ARCHITECTURE.md), and [roadmap](ROADMAP.md).
2. Read the [AI-Assisted Contribution Policy](AI_CONTRIBUTION_POLICY.md). AI-assisted work is welcome, and all contributors remain responsible for understanding and supporting their submissions.
3. Search existing issues and discussions before opening a new topic.
4. Use a discussion for early design exploration and an issue for defined, actionable work.
5. Keep proposals tied to evidence, user value, or a roadmap question.
6. Follow the [Code of Conduct](CODE_OF_CONDUCT.md).

## Current contribution priorities

- representative cubic-world workload definitions;
- storage-format trade-off analysis;
- reproducible benchmark methodology;
- corruption, recovery, and migration scenarios;
- prior art with clear lessons and licensing notes;
- documentation corrections and clearer terminology.

Please avoid large implementation pull requests until the prototype language, repository license, and initial format experiment have been agreed.

## Proposals

A strong proposal explains:

- the problem and who experiences it;
- assumptions and constraints;
- the smallest experiment that could disprove the idea;
- alternatives considered;
- expected measurements and acceptance criteria;
- compatibility, migration, security, and operational consequences;
- what is explicitly out of scope.

## Pull requests

Keep pull requests focused and include:

- a clear summary and motivation;
- the related issue or discussion;
- tests or reproducible benchmark commands when applicable;
- before/after results with environment details for performance claims;
- documentation for changed contracts or formats;
- any known limitations or follow-up work.

Code contributors must maintain the private AI-use profile required by the [AI-Assisted Contribution Policy](AI_CONTRIBUTION_POLICY.md). Never submit a completed profile publicly. The public [profile template](docs/AI_USE_PROFILE_FORM.md) describes the categories and acknowledgement; the Project Lead will designate the private submission method before code contributions are accepted.

Performance changes must report distributions or percentiles, not just a single average. Results should identify the commit, hardware, operating system, runtime, build mode, dataset, cache state, and sample count.

## Commit and review expectations

- Prefer small, reviewable commits with descriptive messages.
- Do not include Minecraft code, assets, decompiled sources, credentials, or third-party material without compatible rights.
- Expect design decisions to be recorded as ADRs once implementation begins.
- Be willing to revise or discard an experiment when evidence contradicts it.

## Licensing note

Nexolith is licensed under the Apache License 2.0. By submitting a contribution, you agree that your contribution is licensed under the same terms and that you have the right to submit it. Do not contribute material whose license is incompatible or unclear.

## Community

Questions and early ideas belong in GitHub Discussions once enabled. See [DISCUSSIONS.md](DISCUSSIONS.md) for suggested categories and posting guidance.

