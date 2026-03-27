---
tags:
  - "#conductor"
  - "#gemini"
  - "#AI"
---
# Specification - Load Dynamic Level Backgrounds

## Overview
Implement the functionality to load and display specific background images for each level in the game. This replaces the static background with level-appropriate visuals.

## Functional Requirements
1.  **Level-Specific Loading**: The `GameScreen` (or a dedicated `LevelManager`) must load the correct background asset based on the current level ID/filename.
2.  **Asset Integration**: Integrate assets like `Level1_BG.PNG`, `Level2_BG.PNG`, etc.
3.  **Dynamic Rendering**: The background must be rendered as the bottom layer in the `GameScreen`.
4.  **Scaling**: The background should scale appropriately to fit different screen resolutions while maintaining its aspect ratio or filling the screen as intended.

## Technical Constraints
- Use libGDX `Texture` or `TextureRegion`.
- Backgrounds are stored in `assets/images/`.
- Level definitions are in `assets/levels/`.

## Acceptance Criteria
- Level 1 displays `Level1_BG.PNG`.
- Level 2 displays `Level2_BG.PNG`.
- Level 3 displays `Level3_BG.PNG`.
- Level 4 displays `Level4_BGpng.png`.
- The background fills the screen correctly.
