---
tags:
  - "#conductor"
  - "#gemini"
  - "#AI"
---
# Implementation Plan - Pluggable Rendering System

## Phase 1: Core Abstraction
- [ ] Task: Create `IRenderer` interface in `core.rendering`.
    - [ ] Define abstract methods: `clearScreen(float r, float g, float b)`, `drawRect(...)`, `drawPathLine(...)`.
- [ ] Task: Create `WorldRenderer` in `core.rendering`.
    - [ ] Create a constructor that accepts `IRenderer`.
    - [ ] Move the mathematical logic for positioning tiles from `GameScreen.render()` into a new `render(Grid grid, ...)` method here.

## Phase 2: Standard Java Plugin (AWT)
- [ ] Task: Create `AwtRenderer` in `core.rendering` (or a specific AWT package).
    - [ ] Implement `IRenderer`.
    - [ ] Wrap `java.awt.Graphics` and map drawing calls.
- [ ] Task: Create a basic `JFrame`/`Canvas` test harness to verify `AwtRenderer` works independently of libGDX.

## Phase 3: libGDX Plugin
- [ ] Task: Create `GdxRenderer` in `core.rendering`.
    - [ ] Implement `IRenderer`.
    - [ ] Map drawing calls to libGDX's `ShapeRenderer`.
- [ ] Task: Refactor `GameScreen`.
    - [ ] Replace direct `ShapeRenderer` logic with instantiation of `GdxRenderer` and `WorldRenderer`.
    - [ ] Call `worldRenderer.render(grid, ...)` in the libGDX render loop.

## Phase 4: Verification & Grading Proof
- [ ] Task: Unit Test Rerun & Verification.
- [ ] Task: Ensure both AWT and Gdx rendering paths function correctly.
- [ ] Task: Update `tracks.md`.