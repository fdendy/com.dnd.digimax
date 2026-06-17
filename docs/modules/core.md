# Core Module

## Purpose

Core is the foundation layer of Digimax.

All modules depend on Core.

Core must not depend on Feature, Plugin, Security, or Cluster modules.

---

## Packages

### core.app

Application foundation.

Classes:

* DigimaxApp
* AppController

---

### core.bootstrap

Boot lifecycle.

Classes:

* BootPolicy
* BootStateManager

---

### core.runtime

Runtime services.

Responsibilities:

* Lifecycle
* Health
* Capability
* Runtime storage

---

### core.device

Device abstraction.

Responsibilities:

* Display
* Audio
* Power
* Storage
* Profile

---

### core.state

Global state management.

Responsibilities:

* Runtime state
* Event propagation

---

### core.system

System services.

Responsibilities:

* Watchdog
* Process monitoring

---

## Design Principles

* Platform independent
* Stateless when possible
* Reusable across modules
