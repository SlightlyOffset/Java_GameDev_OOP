# Specification: Initialize libGDX project structure and core game loop

## Overview
This track focuses on setting up the fundamental project structure for a libGDX-based puzzle game using Java 20+. It includes configuring the build system (Gradle), setting up the core and desktop modules, and implementing a basic game loop that confirms the environment is functional.

## Requirements
- **Java 20+:** The project must be configured to use Java 20 or higher.
- **libGDX Framework:** Integration of the latest stable libGDX version.
- **Project Structure:**
  - core/: Shared game logic.
  - desktop/: Desktop backend implementation (LWJGL3).
- **Core Game Loop:**
  - Initialization: Setup graphics and resources.
  - Rendering: Clear screen with a background color.
  - Lifecycle: Handle create, render, and dispose events.

## Technical Details
- **Build Tool:** Gradle.
- **Backend:** LWJGL3 for desktop support.
- **Key Classes:**
  - PathPuzzleGame: Extends Game or implements ApplicationListener.
  - DesktopLauncher: Entry point for the desktop application.

## Acceptance Criteria
- [ ] Project builds successfully using Gradle.
- [ ] Running the desktop application opens a window.
- [ ] The window displays a solid background color, indicating the render loop is active.
- [ ] No errors in the console during startup or execution.
