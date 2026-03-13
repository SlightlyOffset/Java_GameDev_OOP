---
tags:
  - "#conductor"
  - "#gemini"
  - "#AI"
---
﻿# Initial Concept

A puzzle game /w main mechanic being rotating paths to the finishing point using java 20+ and libGDX as framework.

# Product Guide - P'J Cafe (Puzzle Game)

## Vision
The project aims to develop a desktop-based puzzle game with a central mechanic involving rotating path components to create a continuous route from a starting point to a finishing point.

## Target Audience
- Fellow students in an Object-Oriented Programming (OOP) class.

## Key Features
- **Path Rotation:** Rotate path tiles/segments to change their orientation.
- **Level Completion:** Successfully connect the start and end points to complete a level.
- **JSON Level Loading:** Levels are defined in external JSON files, decoupling level design from the game's source code.
- **Main Game Menu:** Initial screen for starting games, accessing options, and exiting.
- **Java 20+ Compatibility:** Modern Java environment utilizing contemporary language features.
- **Pluggable Rendering System:** Architecture that decouples game logic from rendering, allowing for both high-performance (libGDX) and standard Java (AWT/Swing) backends.
- **libGDX Framework:** High-performance cross-platform game development framework for graphics and input management.

## Technical Goals
- **Platform:** Desktop (Windows/macOS/Linux via JVM).
- **Technology Stack:** Java 20+, libGDX.
- **OOP Principles:** Demonstrate clean object-oriented design (encapsulation, inheritance, polymorphism, and abstraction) as a reference for students.
  - **Abstraction:** Use of custom interfaces (e.g., `IRenderer`) to abstract framework-specific dependencies.
