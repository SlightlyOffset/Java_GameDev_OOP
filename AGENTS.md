# AGENTS.md

## Project Orientation (read this first)
- Gradle multi-module setup: `core/` holds gameplay, rendering, and screens; `desktop/` hosts the LWJGL launcher declared in `settings.gradle` and `desktop/src/main/java/desktop/DesktopLauncher.java`.
- `PathPuzzleGame` (`core/mechanics/PathPuzzleGame.java`) is the LibGDX `Game` implementation that creates the global `AssetManager`, hydrates preferences (audio + level unlocks), and sets the first screen.
- Assets live in `assets/`; `desktop/build.gradle` points the run task’s working directory there so relative loads like `images/background.png` resolve without extra path math.

## Gameplay & Progression Flow
- Screen pipeline: `MenuScreen -> StoryScreen -> LevelSelectionScreen -> GameScreen -> CompleteScreen`; screens live under `core/windows/` and pass the `PathPuzzleGame` reference for shared assets and navigation.
- Level metadata is centralized in `PathPuzzleGame.LEVEL_PATH` and `LEVELS`; progress arrays (`unlockedLevels`, `completedLevels`) gate level buttons in `LevelSelectionScreen` and get persisted via `saveProgress()`.
- `GameScreen` loads grids through `LevelLoader.loadLevel(...)`, rotates tiles on click, and marks completion which unlocks the next level before navigating to `CompleteScreen`.

## Grid & Tile Model
- `Grid` (`core/mechanics/Grid.java`) owns board dimensions, tile matrix, and DFS-based completion (`isPathComplete()` delegates to `hasPath` + `TileType.getConnections(rotation)`).
- `Tile.rotateClockwise()` enforces 90° increments; the implicit end tile is bottom-right unless `setStartAndEnd()` supplies explicit coordinates, mirroring the default level JSONs in `assets/levels/`.
- Background selection is encoded in level JSON (`backgroundImage`) and loaded in `GameScreen` before drawing.

## Rendering Contract
- Rendering stays backend-agnostic via `IRenderer`; `WorldRenderer` orchestrates draw order and tile rotation math while `GdxRenderer` or `AwtRenderer` adapt to LibGDX/AWT specifics.
- Direction indices are fixed (`0=N,1=E,2=S,3=W`). LibGDX math is Y-up; the AWT path flips Y in its renderer implementation—never mix the coordinate assumptions when adding effects.
- Keep world drawing inside `WorldRenderer.render(...)`; backend classes should stay thin (clear screen + primitive draw helpers).

## UI & Audio Patterns
- Every screen creates a `Stage` with `FitViewport(1920,1080)` to match the artwork and reassigns `Gdx.input` to that stage inside `show()`.
- Buttons are backed by textures under `assets/buttons` / `assets/LevelSel`; pressed-state textures follow the `*press_bttn.PNG` pattern (e.g., `Startpress_bttn.PNG`) already preloaded in `PathPuzzleGame.create()`.
- Background music (`sounds/menu_bgm.mp3`) is reused across menu-like screens; they fetch it from the shared `AssetManager`, update volume from `game.musicVolume`, and only start playback when `!music.isPlaying()` to avoid double starts.

## Build, Run, Test
- Standard commands (run at repo root): `./gradlew desktop:run` for the game, `./gradlew core:test` for unit tests, `./gradlew core:runAwt` for the Swing/AWT harness.
- Windows note from `README.md`: if the wrapper batch script hangs, execute `java -jar gradle\wrapper\gradle-wrapper.jar <task>` directly or restore the original `gradlew.bat` from `gradlew.bat.original`.

## Testing Conventions
- Tests live under `core/src/test/java`; they mock LibGDX statics (see `GameScreenTest` / `MenuScreenTest` mocking `Gdx.gl`) and rely on lightweight stubs (e.g., `MenuScreenTest.StubAssetManager`).
- File I/O in tests uses paths relative to the `core/` module (`../assets/levels/level_1.json` in `LevelLoaderTest`).
- Renderer tests (`core/src/test/java/core/rendering/`) cover contract expectations without real GL contexts by mocking `IRenderer` collaborators.

## Process Notes
- Follow Conventional Commits + branch naming from `README.md` and escalate to `conductor/workflow.md` when an issue references Conductor checkpoints.
