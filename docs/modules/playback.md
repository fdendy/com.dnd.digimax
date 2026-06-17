# Playback Module

## Purpose

Render content on screen.

---

## Responsibilities

* Media playback
* Renderer lifecycle
* Playlist execution

---

## Supported Renderers

### ExoPlayerRenderer

Video playback.

### WebViewRenderer

HTML content.

### RunningTextRenderer

Ticker content.

---

## Playback Flow

Scheduler

↓

PluginOrchestrator

↓

RendererPlugin

↓

Display
