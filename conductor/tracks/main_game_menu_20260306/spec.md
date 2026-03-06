# Specification - Main Game Menu

## Overview
Implement the main game menu for P'J Cafe. This menu will be the initial screen the player sees upon launching the game, allowing them to start a new game, access options, or exit.

## Functional Requirements
- **Main Menu Screen:** Create a dedicated screen for the main menu using libGDX's `Screen` interface and `Stage` for UI management.
- **Visual Layout:**
    - **Panel:** A UI panel containing the buttons, aligned to the **right side** of the screen.
    - **Grid Layout:** Use a `Table` (libGDX's equivalent to `GridBagLayout`) to organize the buttons vertically.
    - **Logo:** Display the game logo at the top of the button panel.
- **Menu Options (Buttons):**
    - **Start:** Transition to the game's core gameplay screen.
    - **Options:** Open a placeholder options menu for future configuration (e.g., volume, controls).
    - **Exit:** Terminate the application.
- **User Interaction:** Support **Mouse Only** for selecting menu options.
- **Background:** Use a **static image asset** as the background for the main menu.
- **Audio:**
    - **Background Music:** Loop a background music track while the menu is active.
    - **Sound Effects (SFX):** Play sound effects when buttons are clicked.

## Non-Functional Requirements
- **UI Scaling:** Ensure the menu layout remains centered and correctly scaled across different screen resolutions using a libGDX `Viewport` (e.g., `FitViewport`).
- **Asset Management:** Utilize libGDX's `AssetManager` to load textures and audio efficiently.
- **OOP Design:** Use a clean, modular approach for screen management and UI components.

## Acceptance Criteria
- [ ] The game launches into the Main Menu screen by default.
- [ ] The UI layout matches the specification (buttons on the right, logo on top).
- [ ] The "Start" button correctly transitions the user to the `GameScreen`.
- [ ] The "Exit" button successfully closes the application.
- [ ] The static background image is displayed correctly.
- [ ] Background music loops and button clicks trigger sound effects.

## Out of Scope
- Detailed implementation of the "Options" menu (only a placeholder is required).
- Keyboard navigation for the menu.
- Animated transitions between screens.
