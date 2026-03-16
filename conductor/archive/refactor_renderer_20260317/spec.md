---
tags:
  - "#conductor"
  - "#gemini"
  - "#AI"
---
# Specification - Pluggable Renderer Refactor & Fix

## Objective
To refine the "Pluggable Rendering System" by adding a custom abstract class (`BaseRenderer`) and fixing a critical bug in `AwtGameCanvas` that prevents the AWT rendering path from functioning. This work ensures full compliance with GitHub Issue #17 and OOP grading requirements.

## Grading Requirements Met
- **1.2 Custom Abstract Class:** Defines `BaseRenderer` to provide shared logic for all renderers.
- **1.2 Robust Architecture:** Ensures that `WorldRenderer` manages all aspects of drawing, including screen clearing, consistently across plugins.

## Key Changes

### 1. `BaseRenderer` (Abstract Class)
- **Role:** Implements `IRenderer` and provides a foundation for specific rendering backends.
- **Shared Logic:** Can hold common properties like `clearColor` or basic validation logic.

### 2. `WorldRenderer` (Refactor)
- **Role:** Add `clearScreen()` method that delegates to the internal `IRenderer`.
- **Reason:** To allow `AwtGameCanvas` and `GameScreen` to rely on `WorldRenderer` for high-level frame lifecycle management.

### 3. `AwtRenderer` & `GdxRenderer` (Refactor)
- **Change:** Extend `BaseRenderer` instead of implementing `IRenderer` directly.

### 4. `AwtGameCanvas` (Bug Fix)
- **Change:** Correct the call to `worldRenderer.clearScreen()` once it is implemented in `WorldRenderer`.
