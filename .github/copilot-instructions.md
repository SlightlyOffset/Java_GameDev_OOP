# Copilot Instructions – P'J Cafe

## Build & Run

```bash
# Run the game
./gradlew desktop:run

# Run all tests (core module only)
./gradlew core:test

# Run a single test class
./gradlew core:test --tests "core.TileTest"

# Run a single test method
./gradlew core:test --tests "core.TileTest.testTileRotationClockwise"
```

Tests use JUnit Jupiter (JUnit 5). Only the `core` module has tests; `desktop` has none.

## Architecture

The project follows a standard libGDX multi-module layout:

- **`core/`** – All game logic and rendering. No platform-specific code.
  - `PathPuzzleGame` – Entry point for libGDX; extends `Game`, sets the initial `Screen`.
  - `GameScreen` – Extends `ScreenAdapter`; owns the `Grid`, `ShapeRenderer`, and `OrthographicCamera`. Handles input and the render loop.
  - `Grid` – 2D array of `Tile` objects, indexed as `tiles[row][col]` (i.e., `tiles[y][x]`).
  - `Tile` – Holds a `TileType` and a `rotation` (0, 90, 180, 270 degrees).
  - `TileType` – Enum: `STRAIGHT`, `L_TURN`, `T_JUNCTION`, `CROSS`.
- **`desktop/`** – `DesktopLauncher` only; configures the LWJGL3 window and instantiates `PathPuzzleGame`.
- **`assets/`** – Game resources (currently empty; rendering uses `ShapeRenderer` procedurally).
- **`conductor/`** – Project management docs (not compiled). See `conductor/index.md` for the index.

## Key Conventions

### Grid coordinate system
- The `tiles` array is `tiles[y][x]` (row-major, y=0 is bottom row in world space).
- LibGDX screen input has `screenY=0` at the **top**; the camera has `Y=0` at the **bottom**. Convert with: `float worldY = Gdx.graphics.getHeight() - screenY`.

### Tile connection model
Direction indices used throughout `GameScreen.hasConnection()`: `0=N, 1=E, 2=S, 3=W`.  
Base connections at 0° rotation per type:

| TileType    | N | E | S | W |
|-------------|---|---|---|---|
| STRAIGHT    | ✓ |   | ✓ |   |
| L_TURN      | ✓ | ✓ |   |   |
| T_JUNCTION  | ✓ | ✓ |   | ✓ |
| CROSS       | ✓ | ✓ | ✓ | ✓ |

To check a rotated tile's connection, the direction index is rotated backwards: `base[(direction - steps + 4) % 4]`.

### Workflow (TDD)
Follow the Red → Green → Refactor cycle per `conductor/workflow.md`:
1. Write a failing test first.
2. Implement the minimum code to make it pass.
3. Refactor with tests still green.
4. Target >80% coverage for new code.

### Commit messages
Use conventional commits: `type(scope): description`  
Common types: `feat`, `fix`, `refactor`, `test`, `docs`, `chore`  
Conductor-managed commits use the `conductor` type (e.g., `conductor(plan): Mark phase X as complete`).

### Task tracking
- Active track plans live in `conductor/tracks/<track_name>/plan.md`.
- Completed track archives are in `conductor/archive/`.
- Task status in plan files: `[ ]` pending → `[~]` in progress → `[x]` done (with 7-char commit SHA appended).
- After each completed task, attach a summary via `git notes add -m "<summary>" <commit_hash>`.
- Phase checkpoints are committed as `conductor(checkpoint): Checkpoint end of Phase X` with a verification report attached as a git note.

### Tech stack changes
Any change to the technology stack must be documented in `conductor/tech-stack.md` **before** implementation.
