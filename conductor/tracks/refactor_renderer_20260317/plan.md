---
tags:
  - "#conductor"
  - "#gemini"
  - "#AI"
---
# Implementation Plan - Pluggable Renderer Refactor & Fix

## Phase 1: Core Architecture Update
- [x] Task: Create `BaseRenderer` abstract class in `core.rendering`. f8fe5ac
    - [ ] Implement `IRenderer`.
    - [ ] Provide basic implementation or empty defaults for optional methods (e.g., `endFrame`).
- [ ] Task: Update `WorldRenderer` in `core.rendering`.
    - [ ] Add `public void clearScreen()` which calls `renderer.clearScreen(0.2f, 0.2f, 0.2f, 1)`.
- [ ] Task: Refactor `AwtRenderer` and `GdxRenderer`.
    - [ ] Extend `BaseRenderer` instead of `IRenderer`.

## Phase 2: Bug Fixes & Refactoring
- [ ] Task: Fix `AwtGameCanvas`.
    - [ ] Ensure the call to `worldRenderer.clearScreen()` is consistent with the new implementation.
- [ ] Task: Update `GameScreen`.
    - [ ] Replace `gdxRenderer.clearScreen(...)` with `worldRenderer.clearScreen()`.

## Phase 3: Verification
- [ ] Task: Run the AWT version with `-awt` flag.
- [ ] Task: Run the libGDX version.
- [ ] Task: Run unit tests to ensure no regressions.
