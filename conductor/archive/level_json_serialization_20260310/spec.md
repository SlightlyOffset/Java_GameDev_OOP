# Specification - Level JSON Serialization

## Overview
Implement a system to load and save level data using the JSON format. This decouples level design from the game's source code, allowing for external level creation and easier content management.

## Functional Requirements
1.  **Level Schema**: Define a standard JSON structure for levels, including grid dimensions, start/end points, and tile configurations.
2.  **Deserialization**: Implement logic to convert JSON files into `Grid` and `Tile` objects using libGDX's `Json` utility.
3.  **Serialization**: (Optional but recommended) Implement logic to save current grid states to JSON for a level editor or progress saving.
4.  **Resource Loading**: Levels should be loaded from the `assets/levels/` directory.

## Technical Constraints
- Use `com.badlogic.gdx.utils.Json`.
- Maintain compatibility with the existing `Grid` and `Tile` class structures.
- Ensure efficient loading from internal storage.

## Acceptance Criteria
- A JSON file representing a level can be successfully loaded into a playable `Grid` instance.
- Error handling is in place for malformed JSON files.
- The `GameScreen` can initialize its grid from a specified JSON asset.
