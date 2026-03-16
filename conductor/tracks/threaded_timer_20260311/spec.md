---
tags:
  - "#conductor"
  - "#gemini"
  - "#AI"
---
# Specification - Threaded Playtime Timer

## Overview
Implement a simple, non-critical background thread to track the player's total time spent in-game. This satisfies the "Manual Thread Implementation" academic requirement without disrupting the main LibGDX render thread.

## Functional Requirements
1.  **Manual Thread Management**: Create and manage a `java.lang.Thread` or `java.util.concurrent.ScheduledExecutorService` manually.
2.  **Playtime Calculation**: Track elapsed seconds while the game is active.
3.  **UI Display**: Show the current playtime (e.g., in a corner of the `GameScreen`).
4.  **Pause/Resume Support**: The timer should pause when the game is minimized or in a menu state.

## Technical Constraints
- The timer logic MUST run in its own manual thread, separated from the LibGDX render thread.
- Use `synchronized` or `AtomicInteger` for thread-safe access to the playtime value.
- Update the UI on the LibGDX render thread using the values from the background timer thread.

## Acceptance Criteria
- A background thread is explicitly instantiated and started manually.
- The UI reflects the incrementing playtime correctly.
- No `UnsatisfiedLinkError` or `GdxRuntimeException` when accessing UI elements from the background thread.
