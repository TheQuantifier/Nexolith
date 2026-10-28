# AI-Assisted Contribution Policy

## Purpose

Nexolith welcomes contributions created with or without artificial-intelligence tools. Contributors may use AI assistants for programming, debugging, testing, research, documentation, design exploration, or other project work, subject to this policy.

The project evaluates contributions by their correctness, safety, maintainability, licensing, performance, and benefit to players—not by whether AI assisted in their creation.

AI assistance is permitted, but it does not transfer responsibility away from the contributor.

## 1. Contributor understanding and accountability

Every contributor must understand the code they submit, regardless of how it was produced.

By submitting a contribution, you affirm that you can:

- explain what the code does and why it is needed;
- describe its important control flow, data flow, dependencies, and assumptions;
- diagnose failures and participate meaningfully in debugging;
- explain its security, privacy, compatibility, performance, and operational consequences;
- maintain or revise the contribution during review;
- identify the tests and evidence supporting its behavior.

“I do not know; the AI wrote it” is not an acceptable response during review or maintenance.

The person submitting a contribution remains accountable for its contents and reasonably foreseeable consequences. AI-generated suggestions must receive the same—or greater—level of inspection, testing, and validation as personally written code.

Accountability does not prevent good-faith technical disagreement. Contributors may challenge a diagnosis or review decision using evidence, tests, benchmarks, or reasoned analysis.

## 2. No code-level AI disclosure requirement

Contributors are not required to identify:

- whether a particular line, file, commit, or pull request was AI-assisted;
- which AI service or model was used;
- how much of a contribution originated from an AI tool;
- which portions were written without AI assistance.

Reviewers must treat AI assistance as permitted and potentially present in any contribution. Contributions must not be accepted, rejected, rewritten, or prioritized merely because someone believes they were—or were not—created with AI.

Technical concerns must address the actual contribution: its behavior, quality, evidence, risks, licensing, maintainability, or compliance with project requirements.

This rule does not remove any disclosure legally required by a third-party license, contract, platform, or applicable law.

## 3. Private contributor AI-use profile

Contributors who submit code must maintain a private AI-use profile with the project. The profile records one general category, not the provenance of particular lines, files, commits, or pull requests.

Completed profiles are confidential. Access is limited to the Project Lead and specifically designated moderation administrators. Profiles are not available to reviewers and must not be used to prejudice technical review.

Private profile information may be used only to:

- administer and enforce this policy;
- produce anonymous, project-wide aggregate information that cannot identify individual contributors;
- investigate a substantiated licensing, security, or policy-compliance concern;
- satisfy an applicable external platform, license, contract, or legal requirement while disclosing no more information than required.

Private categories must never be used as hidden reviewer signals, contributor rankings, measures of ability, or grounds for different technical-review standards.

A contributor may update their category whenever their practices change. The updated category applies from its recorded effective date. Active code contributors will be asked to confirm or update their category monthly. Code submitted during an effective period must be consistent with the category recorded for that period.

An accidental inconsistency should be corrected promptly through a private update. Deliberately providing false information or repeatedly disregarding the profile requirement may result in contribution restrictions.

The project will collect no additional information about a contributor’s AI usage unless reasonably necessary for one of the permitted purposes above. Anonymous aggregate information may be published only when it cannot identify individual contributors.

The public [AI-use profile template](docs/AI_USE_PROFILE_FORM.md) explains the four categories. **Do not submit a completed profile in a public issue, discussion, pull request, or repository file.** The Project Lead will designate a private submission method before code contributions are accepted.

### Category 1: AI-assisted coding

Select this category if AI-generated implementation material is incorporated into a contribution.

Implementation material includes source code, tests, patches, diffs, replacement expressions, pseudocode, configuration, build scripts, migrations, automation, or step-by-step implementation instructions.

This category applies when a contributor copies, manually retypes, translates, restructures, paraphrases, adapts, or directly implements material supplied by AI. Substantially revising AI-generated material does not change the classification.

It also applies when AI supplies a debugging fix, corrected function, regression test, replacement line, or exact implementation that becomes part of the submitted work.

### Category 2: AI code review or diagnosis only

Select this category when AI may inspect or discuss code but does not supply implementation material used in the contribution.

Under this category, AI may:

- identify a possible defect;
- locate suspicious behavior;
- explain an error message, stack trace, or failing test;
- explain why existing code may behave incorrectly;
- identify missing coverage or unhandled cases;
- suggest areas for human investigation;
- review contributor-written code for possible problems.

The contributor must independently investigate the finding and independently design and write any resulting code or tests.

If AI produces a patch, replacement code, exact replacement expression, pseudocode, generated test, or step-by-step implementation, that material must not be copied, retyped, adapted, translated, paraphrased, or directly implemented under this category. If it is used, the correct category is **AI-assisted coding**.

AI may answer **what appears to be wrong** and **why it may be wrong**, but the submitted implementation must be independently produced by the contributor.

### Category 3: AI-assisted documentation only

Select this category when AI is used for prose or other non-code material but is not used to create, inspect, review, diagnose, debug, or modify code.

Permitted uses include assistance with:

- documentation wording and organization;
- spelling, grammar, and readability;
- issue and pull-request descriptions;
- user guides and tutorials;
- release notes and summaries;
- community communications;
- non-implementation project descriptions.

Under this category, AI must not:

- generate or modify source code, tests, configuration, scripts, or patches;
- inspect submitted code for defects;
- diagnose code failures or error messages;
- propose code changes or implementation steps;
- generate pseudocode intended for implementation;
- provide an implementation that influences submitted code.

Ordinary spelling, grammar, and formatting tools do not need to be treated as AI use unless they generate or substantially rewrite content.

### Category 4: No AI use

Select this category when AI is not used for any Nexolith contribution work.

This includes code, code review, debugging, testing, documentation, planning, research, descriptions, and community contributions.

Conventional tools such as compilers, linters, formatters, static analyzers, test frameworks, debuggers, profilers, search tools, and integrated-development-environment features do not count as AI unless they use generative or conversational AI functionality.

### Quick classification examples

| Activity | Category |
| --- | --- |
| AI generates or proposes code that is used or adapted | AI-assisted coding |
| AI supplies a patch that the contributor manually retypes | AI-assisted coding |
| AI identifies a defect, but the contributor independently investigates and writes the fix | AI code review or diagnosis only |
| AI explains a stack trace without supplying a used implementation | AI code review or diagnosis only |
| AI improves README wording but never examines or influences code | AI-assisted documentation only |
| AI writes an issue description but performs no code-related work | AI-assisted documentation only |
| No generative or conversational AI is used for project work | No AI use |
| The contributor is uncertain whether AI supplied part of the implementation | AI-assisted coding |

Classification is administrative, not a judgment of contribution quality. Selecting **AI-assisted coding** carries no penalty or negative review consequence.

## 4. Tool-neutral technical review

All contributions are subject to the same technical standards.

Reviewers may request:

- explanations of implementation decisions;
- tests or additional validation;
- benchmarks and reproducible results;
- security or privacy analysis;
- dependency and license information;
- revisions needed for readability or maintainability;
- removal of code whose origin or licensing cannot be responsibly established.

A contributor’s ability to explain and support the work is part of the contribution’s quality. Reviewers must not attempt to determine AI involvement from writing style, coding style, automated detectors, or speculation.

## 5. AI-assisted review and preventable errors

Contributors are expected to take reasonable steps to catch straightforward mistakes before submitting their work. Typographical errors, inconsistent names, obvious logic mistakes, and similar defects should not be left for maintainers to discover when they could reasonably have been detected through careful review and available quality checks.

Using AI assistants or chatbots to review code, identify possible defects, suggest test cases, and question assumptions is expressly permitted and recommended. Contributors who use these tools must treat their findings as suggestions that require verification, not as proof that the code is correct.

AI-assisted review supplements rather than replaces compilers, linters, static analysis, automated tests, benchmarks, security checks, and human judgment. Contributors are not required to use AI, but every contributor is expected to perform an appropriate level of review using the tools available to them before submitting code.

## 6. Intellectual property, licensing, and confidential material

Contributors are responsible for ensuring that they have the right to submit everything in their contribution.

AI tools must not be used to introduce:

- Minecraft source code, assets, decompiled material, or other protected content that the project is not permitted to distribute;
- code copied from another project under incompatible or unknown terms;
- personal information, credentials, security secrets, or confidential material;
- generated notices, attributions, or licensing claims that have not been verified;
- material that violates the Apache License 2.0 or another applicable obligation.

Do not provide private project information, unpublished vulnerabilities, credentials, or third-party confidential material to an AI service unless the Project Lead has explicitly approved that service and use.

When the origin or licensing of generated output is uncertain, the contributor must replace it, independently reimplement it from legitimate sources, or provide sufficient provenance for review.

## 7. Testing and safety

AI assistance is not a substitute for testing, review, threat analysis, or performance measurement.

Contributors must not rely solely on an AI tool’s statement that code is correct, secure, original, compatible, or efficient. Claims that affect architecture, security, compatibility, or performance must be supported by appropriate evidence.

Generated tests must themselves be reviewed. Passing tests do not excuse incorrect assumptions, inadequate coverage, or harmful behavior.

## 8. Respectful discussion

Nexolith does not permit attacks on contributors based on their choice to use or not use AI.

The following behavior is unacceptable:

- describing AI users as lazy, fraudulent, unintelligent, or not “real” developers;
- pressuring contributors to use AI as the only modern or legitimate way to work;
- treating non-AI contributors as inefficient, obsolete, or hostile to progress;
- demanding code-level AI disclosure when this policy does not require it;
- proposing changes solely because code is believed to be AI-generated;
- repeatedly turning technical discussions into generalized arguments for or against AI.

Good-faith discussion of specific technical, legal, environmental, privacy, security, labor, or community effects remains allowed when it is relevant and respectful. Criticism must focus on evidence and project impact rather than stereotypes about people or tools.

Moderators may redirect or end discussions that become ideological, repetitive, insulting, or unrelated to the work being reviewed.

## 9. Project priority

Nexolith exists to create a reliable, enjoyable product that benefits players and supports ambitious modding.

Programming is essential to that goal, but coding style, personal workflow, and tool preference are not the project’s ultimate purpose. Tools are evaluated by whether they help contributors deliver understandable, lawful, safe, maintainable, and effective results.

No tool—including AI—is more important than the product, its users, or the people building it.

## 10. Enforcement

Minor or accidental violations will normally be addressed through private clarification and correction.

Serious, deliberate, or repeated violations may result in:

- requested revisions;
- rejection or removal of a contribution;
- temporary contribution restrictions;
- moderation action under the Code of Conduct;
- removal from the project in severe cases.

Enforcement should be proportionate, documented privately where appropriate, and based on conduct and evidence rather than assumptions about a contributor’s tools.

## 11. Policy changes

This policy may evolve as the project, applicable rules, and development tools change. Material changes should be announced and should not retroactively penalize contributors for conduct that complied with the policy in effect at the time.

Questions about private AI-use profiles or possible violations should be directed privately to the Project Lead or a designated moderation administrator.

