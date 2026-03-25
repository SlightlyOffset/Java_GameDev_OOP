# AGENTS.md

## Project map (read this first)
- Gradle multi-module Java project: `core` (game logic/rendering) + `desktop` (launcher), wired in `settings.gradle`.
- Runtime entry point is `desktop/src/main/java/desktop/DesktopLauncher.java`.
- Main game lifecycle is `PathPuzzleGame -> MenuScreen -> GameScreen` (`core/src/main/java/core/mechanics/PathPuzzleGame.java`, `core/src/main/java/core/windows/MenuScreen.java`, `core/src/main/java/core/windows/GameScreen.java`).
- Assets are loaded via `AssetManager` from `assets/` and desktop run task sets working dir to that folder (`desktop/build.gradle`).
- Level data is JSON-deserialized directly into `Grid` (`core/src/main/java/core/mechanics/LevelLoader.java`, `assets/levels/level_1.json`).

## Rendering architecture (project-specific)
- Rendering is intentionally pluggable: `IRenderer` + backend implementations (`GdxRenderer`, `AwtRenderer`) with shared draw orchestration in `WorldRenderer`.
- `GameScreen` injects a `GdxRenderer` into `WorldRenderer`; AWT path does the same through `AwtGameCanvas`/`AwtGameWindow`.
- Keep world drawing logic in `WorldRenderer`; backend classes should stay thin adapters.
- Direction contract is fixed as `0=N, 1=E, 2=S, 3=W` across renderer code.
- Coordinate systems differ by backend (LibGDX Y-up, AWT Y-down); preserve existing per-backend conversions.

## Core gameplay/data model patterns
- `Grid` owns board dimensions, tile matrix, start/end coords, and solved state (`core/src/main/java/core/mechanics/Grid.java`).
- `Grid.isPathComplete()` uses DFS (`hasPath`) and `TileType.getConnections(rotation)` to validate links.
- Tile rotation is always 90-degree increments (`Tile.rotateClockwise/rotateCounterClockwise`).
- Default end point is implicit bottom-right when `endX/endY` are unset; if UI logic needs explicit end tile markers, call `setStartAndEnd(...)`.

## Build, run, and test workflows
- Preferred commands from repo root:
  - `./gradlew desktop:run` (LibGDX desktop game)
  - `./gradlew core:test` (unit tests)
  - `./gradlew core:runAwt` (AWT renderer harness)
- On Windows, this repo documents wrapper issues and fallback command: `java -jar gradle/wrapper/gradle-wrapper.jar <task>` (`README.md`).
- `desktop` module also supports `-awt` launch arg in `DesktopLauncher` for alternate rendering path.

## Testing conventions that matter here
- Tests are in `core/src/test/java/...` and use JUnit 5 + Mockito (declared in root `build.gradle`).
- Headless-style tests commonly guard LibGDX globals (example: mocking `Gdx.gl` in `GameScreenTest` and `MenuScreenTest`).
- When tests load level files, use paths relative to `core/` (example: `../assets/levels/level_1.json` in `LevelLoaderTest`).
- Simple manual stubs are preferred for some collaborators (see `MenuScreenTest.StubAssetManager`, `WorldRendererTest.MockRenderer`).

## Repo process notes
- `README.md` captures contributor expectations (Conventional Commits, branch naming).
- `conductor/workflow.md` contains stricter team workflow/checkpoint process; follow it when task requests reference Conductor artifacts.

