# Programmatic Module

## Purpose

Integrate third-party advertising providers.

Current strategy:

No bidding.

Third-party provider supplies campaign inventory.

---

## Responsibilities

* Campaign retrieval
* Campaign validation
* Campaign selection
* Telemetry
* Impression tracking

---

## Packages

### provider

Provider integrations.

### model

Campaign entities.

### cache

Campaign cache.

### telemetry

Tracking services.

### runtime

Execution services.

---

## Flow

Provider

↓

Campaign

↓

Decision Engine

↓

Playback

↓

Impression Tracking
