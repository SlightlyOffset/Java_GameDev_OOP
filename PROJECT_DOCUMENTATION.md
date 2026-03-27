# Project Documentation: P'J Cafe

## 1. Project Overview

**P'J Cafe** is a desktop-based puzzle game developed as an educational project for an Object-Oriented Programming (OOP) course. It is designed to be a practical demonstration of clean OOP principles within a real-world application.

### 1.1. Vision

The project's vision is to create an engaging puzzle game while serving as a high-quality reference implementation for students learning Java and object-oriented design. The core gameplay is intuitive, but the underlying architecture showcases advanced concepts like dependency inversion and modularity.

### 1.2. Target Audience

The primary audience is fellow students in an OOP class. The codebase is intended to be studied and understood as an example of applying theoretical concepts to a tangible product.

### 1.3. Core Gameplay

The central mechanic involves a grid of rotatable path tiles. The player's objective is to rotate these tiles to form a continuous, unbroken path from a designated start point to a designated end point.

---

## 2. Architecture and Design

The application is architected to be modular, testable, and flexible, emphasizing a strong separation of concerns.

### 2.1. High-Level Design

A key architectural feature is the **pluggable rendering system**. The core game logic is completely decoupled from the rendering technology. This is achieved through an `IRenderer` interface, which defines a contract for all rendering operations (e.g., drawing tiles, backgrounds).

This allows the game to be run with two different backends:
1.  **LibGDX Renderer (`GdxRenderer`):** A high-performance backend using LWJGL (OpenGL) for smooth graphics.
2.  **AWT Renderer (`AwtRenderer`):** A pure Java2D backend using standard AWT/Swing. This was implemented for compliance with course requirements that may restrict external libraries.

### 2.2. Gradle Project Modules

The project is structured as a multi-module Gradle build, promoting code organization and reusability.

-   `rootProject ('P'J_Cafe')`
    -   `:core`: This module contains all shared game logic, including the grid system, tile mechanics, pathfinding, level loading, and the rendering abstraction layer. It has no dependency on any specific graphics framework.
    -   `:desktop`: This module acts as the launcher for the desktop platform. It includes the main application entry point, configures the LibGDX backend, and instantiates the `GdxRenderer` to run the game.

### 2.3. Key Directories

-   `assets/`: Contains all game resources, such as images (backgrounds, logos, buttons), level data (`.json` files), and sound files.
-   `conductor/`: This directory is the single source of truth for project management, planning, and documentation. It follows a methodology where tasks are planned, tracked, and verified through markdown files and Git history.
-   `.github/`: Contains CI/CD workflows and automation scripts, primarily for use with GitHub Actions.

---

## 3. Technology Stack

| Category              | Technology                                      | Description                                                                                             |
| --------------------- | ----------------------------------------------- | ------------------------------------------------------------------------------------------------------- |
| **Language & Runtime**| Java 25                                         | Utilizes modern language features for clean, expressive, and performant code.                             |
| **Game Framework**    | LibGDX 1.13.1                                   | A powerful cross-platform game development framework for graphics, input, and audio.                      |
| **Graphics Backend**  | LWJGL 3                                         | The Lightweight Java Game Library provides the high-performance OpenGL bindings for the desktop backend.  |
| **Build System**      | Gradle                                          | A robust build automation tool used to manage dependencies, modules, and build tasks.                     |
| **Data Format**       | JSON                                            | Human-readable format for defining level layouts and configurations.                                      |
| **Serialization**     | LibGDX JSON Utilities                           | Used for seamlessly converting Java objects to and from JSON files for level loading.                     |
| **Testing**           | JUnit 5 (Jupiter) & Mockito 5                   | The standard frameworks for writing unit tests and creating mock objects to isolate components for testing. |

---

## 4. Development Workflow

The project follows a disciplined, test-driven, and documentation-centric workflow to ensure code quality and maintainability.

### 4.1. Getting Started

**Prerequisites:**
-   Java Development Kit (JDK) 20 or higher.
-   Gradle (the included wrapper is recommended).

**Running the Game:**
To run the desktop application, execute:
```bash
./gradlew desktop:run
```

**Running Tests:**
To run the full suite of unit tests in the `core` module:
```bash
./gradlew core:test
```

### 4.2. Version Control

**Branching Strategy:**
-   `main`: The stable, always-runnable branch.
-   `feat/<description>`: For developing new features.
-   `fix/<description>`: For addressing bugs.

**Commit Messages:**
The project adheres to the [Conventional Commits](https://www.conventionalcommits.org/) specification. This provides a clear and standardized commit history.
-   **Examples:**
    -   `feat(grid): Add path validation logic`
    -   `fix(tile): Correct rotation on double-click`
    -   `docs(readme): Update build instructions`
    -   `refactor(renderer): Extract base class for renderers`

### 4.3. Test-Driven Development (TDD)

All development follows the Red-Green-Refactor cycle:
1.  **Red:** Write a failing test that defines the desired functionality.
2.  **Green:** Write the simplest possible code to make the test pass.
3.  **Refactor:** Improve the code's design and clarity without changing its behavior.

A high test coverage (>80%) is required.

### 4.4. The Conductor Workflow

All tasks and project phases are meticulously planned and tracked in the `conductor/` directory. See [`conductor/workflow.md`](conductor/workflow.md) for the detailed process of task selection, implementation, verification, and checkpointing.

---

## 5. OOP Principles in Practice

As an educational project, P'J Cafe explicitly demonstrates core OOP concepts:

-   **Abstraction:** The `IRenderer` interface and `LevelLoader` class abstract away the details of rendering and file I/O, allowing the core logic to remain platform-agnostic.
-   **Encapsulation:** Game objects like `Tile` and `Grid` manage their own internal state. State is modified through well-defined public methods, not direct access.
-   **Inheritance:** The `GdxRenderer` and `AwtRenderer` classes inherit from a common `BaseRenderer`, reusing common logic while providing specialized implementations.
-   **Polymorphism:** The main `GameScreen` can work with any object that implements the `IRenderer` interface, allowing the rendering backend to be swapped out with no changes to the core game loop.

---

## 6. Project Team

This project was developed by students of the Faculty of Information Technology, KMITL.

1.  นาย กฤตภาส ไพรสาลี (68070003)
2.  นางสาว จีรวรรณ น่วมขยัน (68070017)
3.  นาย ชนาธิป วาฑิตศุภพล (68070024)
4.  นางสาว ญาณิศา จันทร์อุดม (68070032)
5.  นาย ฐิติวัฒน์ มนต์วิเศษ (68070035)
6.  นางสาว นภัทร อยู่เจริญ (68070078)
7.  นางสาว นภัสวรรณ เกิดแก้ว (68070079)
8.  นาย นัธทวัฒน์ พละเดช (68070083)
9.  นาย นิรัชพล กลิ่นขจร (68070089)
10. นาย วิชยุตม์ สร้อยสน (68070172)
