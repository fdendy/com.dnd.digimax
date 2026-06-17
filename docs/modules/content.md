# Content Module

## Purpose

Manage content lifecycle.

---

## Responsibilities

* Content discovery
* Content validation
* Content caching
* Content resolution

---

## Packages

### cache

Store downloaded content.

### model

Content entities.

### resolver

Resolve playable content.

### validator

Validate content integrity.

---

## Flow

CMS

↓

Content Sync

↓

Validator

↓

Cache

↓

Resolver

↓

Playback
