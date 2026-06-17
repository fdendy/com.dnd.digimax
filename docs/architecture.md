# Digimax Architecture

## Root Package

com.dnd.digimax

## Layers

### App

Android entry points.

### Core

Platform-independent foundation.

Modules:

* app
* bootstrap
* runtime
* state
* system
* device

### Feature

Business capabilities.

Modules:

* scheduler
* content
* playback
* programmatic

### Plugin

Renderer abstraction.

Modules:

* base
* manager
* impl

### Security

Integrity and protection services.

Modules:

* checker
* detector
* verifier
* integrity
* response

### Cluster

Distributed coordination.

Modules:

* election
* node
* network
* role

## Dependency Rules

feature -> core

plugin -> core

security -> core

cluster -> core

core -> none


