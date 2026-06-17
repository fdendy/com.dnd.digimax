# Scheduler Module

## Purpose

Determine what content should play and when.

---

## Responsibilities

* Playlist execution
* Time scheduling
* Campaign scheduling
* Runtime orchestration

---

## Packages

### datasource

Schedule providers.

### model

Schedule entities.

### parser

Schedule parsing.

### engine

Scheduling logic.

### runtime

Execution layer.

### sync

Schedule synchronization.

---

## Execution Flow

Schedule

↓

Parser

↓

Engine

↓

Runtime

↓

Playback
