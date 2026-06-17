# Plugin Module

## Purpose

Provide renderer abstraction.

---

## Responsibilities

* Renderer registration
* Renderer lifecycle
* Renderer orchestration

---

## Packages

### base

Plugin contracts.

### manager

Plugin management.

### impl

Renderer implementations.

---

## Current Implementations

* ExoPlayerRenderer
* WebViewRenderer
* RunningTextRenderer

---

## Future Implementations

* PDF Renderer
* Image Sequence Renderer
* IPTV Renderer
* Interactive Renderer

---

## Plugin Lifecycle

Register

↓

Initialize

↓

Render

↓

Destroy
