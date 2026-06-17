# Cluster Module

## Purpose

Coordinate multiple Digimax nodes.

---

## Responsibilities

* Leader election
* Node discovery
* State synchronization
* Health monitoring

---

## Packages

### election

Leader selection.

### node

Node representation.

### network

Cluster communication.

### role

Leader/Follower roles.

---

## Flow

Node Join

↓

Discovery

↓

Election

↓

Role Assignment

↓

Synchronization
