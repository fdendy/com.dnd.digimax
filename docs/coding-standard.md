# Coding Standard

## Language

Primary Language:

Java

---

## Package Naming

Lowercase only.

Example:

com.dnd.digimax.feature.scheduler

---

## Class Naming

PascalCase.

Example:

SchedulerEngine

ContentResolver

RuntimeExecutor

---

## Interface Naming

Use meaningful names.

Example:

Lifecycle

RendererPlugin

EngineModule

Avoid:

IScheduler

IPlayer

---

## Constants

Uppercase.

Example:

MAX_RETRY

DEFAULT_TIMEOUT

---

## Logging

Use centralized logging.

Avoid:

System.out.println()

Preferred:

Log.d()

Log.i()

Log.e()

or future centralized logger.

---

## Architecture Rules

Core modules must not depend on:

* feature
* plugin
* security
* cluster

Feature modules may depend only on core.

Plugin modules may depend only on core.

Security modules may depend only on core.

Cluster modules may depend only on core.

---

## Code Style

* Keep methods focused.
* Prefer composition over inheritance.
* Avoid god classes.
* Keep business logic outside Android components.
* Use dependency injection whenever possible.
