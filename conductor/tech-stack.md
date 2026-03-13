---
tags:
  - "#conductor"
  - "#gemini"
  - "#AI"
---
# Technology Stack - P'J Cafe (Puzzle Game)

## Language & Runtime
- **Java 25:** Utilizing modern language features for clean object-oriented design and performance.

## Core Framework
- **libGDX 1.13.1:** A cross-platform game development framework providing efficient handling of graphics (OpenGL/LWJGL), input, audio, and more for the desktop platform.

## Build System
- **Gradle:** Standard build automation tool for Java and libGDX projects, managing dependencies and project structure.

## Data & Serialization
- **JSON:** Used for defining level data, game configurations, and persistent state.
- **libGDX Json Utility:** Utilized for seamless object-to-JSON serialization and deserialization.
  - **Abstraction:** The `LevelLoader` provides an abstraction to support file reading outside the libGDX context for unit testing and AWT rendering.

## Graphics & Rendering
- **LWJGL (Lightweight Java Game Library):** Underpins libGDX's desktop backend for high-performance access to OpenGL.
- **AWT/Swing (Standard Java):** Fallback backend used for grading compliance, providing a pure Java alternative for graphical rendering.
- **Custom Rendering Bridge:** Pluggable `IRenderer` interface implemented by both `AwtRenderer` and `GdxRenderer`.

## Testing Frameworks
- **JUnit 5 (Jupiter):** Standard testing framework for Java, used for writing and running unit tests.
- **Mockito 5:** Mocking framework for Java, used to isolate components during unit testing (especially useful for libGDX context objects).
