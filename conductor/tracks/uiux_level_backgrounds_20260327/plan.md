---
tags:
  - "#conductor"
  - "#gemini"
  - "#AI"
---
# Implementation Plan - Load Dynamic Level Backgrounds

## Phase 1: Level Mapping
- [ ] Task: Determine background mapping for each level.
    - [ ] Option A: Naming convention (e.g., `Level1_BG.PNG`).
    - [ ] Option B: Explicit mapping in level JSON files.
    - [ ] Decision: We'll update the Level JSON schema to include a `background` field.

## Phase 2: Schema & Data Update
- [ ] Task: Update `LevelLoader.java` (if it exists) to deserialize the `background` field.
- [ ] Task: Update each level JSON file in `assets/levels/` to include the `background` property pointing to the correct image file.

## Phase 3: Display Implementation
- [ ] Task: Modify `GameScreen` to load and display the background.
    - [ ] Load the `Texture` from the `background` path specified in the level JSON.
    - [ ] Render the texture at the beginning of the `render()` method using `SpriteBatch`.
    - [ ] Implement proper scaling/stretching to fit the screen.

## Phase 4: Verification
- [ ] Task: Test each level to ensure the correct background is displayed.
- [ ] Task: Verify scaling on window resize (if applicable).
- [ ] Task: Update `tracks.md` and project docs.
