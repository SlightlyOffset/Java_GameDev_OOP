# Product Guidelines - P'J Cafe (Puzzle Game)

## Prose Style
- **Clear & Educational:** Documentation and in-game messages should prioritize clarity, making it easy for students to understand both the gameplay and the underlying code structure.
- **Playful & Conversational:** Use an engaging, friendly tone that encourages exploration and learning without being overly formal.

## Branding & Aesthetic
- **Minimalist & Modern:** The visual design should be clean and uncluttered, focusing on the core puzzle mechanic. Use modern typography and a balanced color palette that doesn't distract from the path-rotating gameplay.

## UX Principles
- **Minimalist UI:** The user interface should be as unobtrusive as possible, emphasizing the puzzle board. Elements like menus and settings should be easily accessible but not dominant.
- **Responsive Anchoring:** UI elements (like the menu panel) should anchor to screen edges (e.g., right side) without stretching to maintain a consistent pixel-perfect look across resolutions.

## Game Design & Mechanics
- **Progressive Difficulty:** Levels should start with simple connection tasks and gradually introduce more complex path configurations.
- **Visual Clues:** Provide subtle visual feedback (e.g., highlighting connected paths) to guide players toward the solution.
- **Playful Quirks:** 
  - **Hiding Objects:** Introduce elements that require players to find hidden switches or components.
  - **Internal MDI Windows:** Use multiple "windows" within the game to manage different parts of the path, requiring coordination between internal views.
  - **Component Activation:** Some path segments might require a separate activation (e.g., toggling a switch) to become rotatable or functional.

## Audio Direction
- **Atmospheric BGM:** Use looping background music to set the mood for different screens.
- **Interactive SFX:** Provide auditory feedback for user actions like button clicks and path rotations.
