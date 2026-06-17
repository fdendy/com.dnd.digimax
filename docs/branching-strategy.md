# Branching Strategy

## Main Branches

### main

Production-ready source code.

### develop

Integration branch for ongoing development.

---

## Feature Branch

Naming:

feature/<module-name>

Examples:

feature/content
feature/scheduler
feature/playback
feature/programmatic
feature/security

---

## Release Branch

Naming:

release/<version>

Examples:

release/1.0.0

---

## Hotfix Branch

Naming:

hotfix/<issue>

Examples:

hotfix/player-crash
hotfix/memory-leak

---

## Merge Flow

feature/*

↓

develop

↓

release/*

↓

main

---

## Commit Convention

feat(module): new feature

fix(module): bug fix

refactor(module): code improvement

docs(module): documentation update

test(module): test improvement

chore(module): maintenance task

Examples:

feat(content): add content validator

feat(programmatic): add campaign resolver

fix(playback): prevent renderer crash

docs(architecture): update module diagram
