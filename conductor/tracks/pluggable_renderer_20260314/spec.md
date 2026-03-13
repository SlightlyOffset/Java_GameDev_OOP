---
tags:
  - "#conductor"
  - "#gemini"
  - "#AI"
---
# Specification - Pluggable Rendering System

## Objective
To implement a "Pluggable Rendering System" that decouples the game's core logic from any specific rendering library. This satisfies strict grading criteria requiring custom interfaces, classes, and the explicit use of the Java Standard Library (`java.awt.Graphics`) while preserving the ability to run via libGDX.

## Grading Requirements Met
- **1.1 Graphics:** Uses `java.awt.Graphics` via the `AwtRenderer` plugin.
- **1.2 Custom Interface:** Defines `IRenderer`.
- **1.2 Custom Abstract Class:** Defines `BaseRenderer` (optional shared logic) or ensures logic uses abstract rendering concepts.
- **1.2 Custom Class:** Implements `AwtRenderer` and `GdxRenderer`.
- **1.2 Method with Custom Interface:** `WorldRenderer` relies on `IRenderer`.

## Key Components

### 1. `IRenderer` (Interface)
- **Role:** The contract for all drawing operations.
- **Methods:** `drawBackground`, `drawTile`, `drawPath`, `clearScreen`.

### 2. `WorldRenderer` (Custom Class)
- **Role:** Translates the game state (`Grid`, `Tile`s) into rendering commands.
- **Dependency:** It accepts an `IRenderer` via constructor dependency injection.

### 3. `AwtRenderer` (Custom Class)
- **Role:** Implements `IRenderer` using Standard Java (`java.awt.Graphics2D`).
- **Proof:** This is the fallback/proof for the "No Frameworks" grading criteria.

### 4. `GdxRenderer` (Custom Class)
- **Role:** Implements `IRenderer` using libGDX (`ShapeRenderer`, `SpriteBatch`).
- **Usage:** The primary renderer for a smoother user experience.

## Architectural Flow
Game Logic -> `WorldRenderer` -> `IRenderer` -> (`AwtRenderer` | `GdxRenderer`)