# P'J Cafe (Puzzle Game)

P'J Cafe is a desktop-based puzzle game developed as a project for an Object-Oriented Programming (OOP) class. The core gameplay mechanic involves rotating path components to create a continuous route from a starting point to a finishing point.

## 🌟 Key Features

- **Path Rotation:** Interactive tiles that can be rotated to change their orientation.
- **Level Completion:** Connect the start and end points to progress through levels.
- **Modern Java:** Built using Java 20+ features.
- **Educational Design:** Demonstrates clean OOP principles (encapsulation, inheritance, polymorphism, and abstraction).

## 🛠️ Technology Stack

- **Language:** Java 25 (utilizing modern language features).
- **Framework:** [libGDX 1.13.1](https://libgdx.com/) - High-performance cross-platform game development framework.
- **Build System:** Gradle.
- **Graphics Backend:** LWJGL (Lightweight Java Game Library).

## 📁 Project Structure

- `core/`: Contains the shared game logic, mechanics, and object-oriented implementations.
- `desktop/`: Handles desktop-specific initialization and application configuration.
- `assets/`: Dedicated directory for game textures, sounds, and other resources.

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 20 or higher.
- Gradle (or use the provided Gradle wrapper).

### Running the Project

To run the desktop version of the game, execute the following command in the project root:

```bash
./gradlew desktop:run
```

### Running Tests

```bash
# Run all tests
./gradlew core:test

# Run a single test class
./gradlew core:test --tests "core.TileTest"
```

## 🛠️ Troubleshooting & Environment

### Modified Gradle Wrapper (`gradlew.bat`)
On some Windows environments (especially those with restrictive HIPS/Antivirus settings like ESET), the standard `gradlew.bat` might hang indefinitely when spawning child processes. 

To address this, the `gradlew.bat` in this repository has been simplified to directly invoke the Gradle wrapper JAR using a fixed `JAVA_HOME` pointing to JDK 25.

**If the build fails or hangs:**
1. **Direct JAR Invocation:** Use this command to bypass the batch script entirely:
   ```powershell
   java -jar gradle\wrapper\gradle-wrapper.jar <task>
   # Example:
   java -jar gradle\wrapper\gradle-wrapper.jar core:test
   ```
2. **Reverting the Wrapper:** If you prefer the standard Gradle wrapper (and your environment supports it), you can restore it from the backup:
   ```powershell
   copy gradlew.bat.original gradlew.bat
   # Or using git:
   git checkout gradlew.bat
   ```

3. **Check JDK Path:** If you use the modified script, ensure your JDK is installed at `C:\Program Files\Java\jdk-25` or update the `set JAVA_HOME` line in `gradlew.bat` to match your local installation.

## 🤝 Contributing

All contributors follow a shared workflow to keep the codebase clean and history readable.

### Branching

- `main` — stable, always runnable.
- Feature branches: `feat/<short-description>` (e.g. `feat/level-select`)
- Bug fix branches: `fix/<short-description>` (e.g. `fix/tile-rotation-wrap`)

### Commit Messages

This project uses [Conventional Commits](https://www.conventionalcommits.org/):

```
<type>(<scope>): <short description>
```

| Type | When to use |
|------|-------------|
| `feat` | New feature |
| `fix` | Bug fix |
| `refactor` | Code change with no behaviour change |
| `test` | Adding or fixing tests |
| `docs` | Documentation only |
| `chore` | Build scripts, config, maintenance |

**Examples:**
```bash
git commit -m "feat(grid): Add path connection validation"
git commit -m "fix(tile): Correct counter-clockwise wrap-around"
git commit -m "test(grid): Add tests for out-of-bounds access"
```

### Development Workflow

1. **Branch** off `main` for every task.
2. **Write a failing test first** (Red), then implement to make it pass (Green), then refactor.
3. **Run tests** before committing — `./gradlew core:test`.
4. **Open a Pull Request** against `main` with a clear description of what changed and why.
5. **Merge** only when tests are green and at least one teammate has reviewed.

See [`conductor/workflow.md`](conductor/workflow.md) for the full task lifecycle, commit note conventions, and phase checkpoint process.

## 🎓 OOP Principles in Practice

This project serves as a reference for students learning Object-Oriented Programming. Key concepts implemented include:

- **Abstraction:** Modeling game entities and mechanics.
- **Encapsulation:** Managing state within game objects.
- **Inheritance & Polymorphism:** Extending LibGDX classes and implementing game-specific behaviors.

---
*Developed for OOP Class Project as part of the curriculum in the Faculty of Information Technology, King Mongkut's Institute of Technology Ladkrabang.*

*Developed by:*

1. นาย กฤตภาส ไพรสาลี 68070003
2. นางสาว จีรวรรณ น่วมขยัน 68070017
3. นาย ชนาธิป วาฑิตศุภพล 68070024
4. นางสาว ญาณิศา จันทร์อุดม 68070032
5. นาย ฐิติวัฒน์ มนต์วิเศษ 68070035
6. นางสาว นภัทร อยู่เจริญ 68070078
7. นางสาว นภัสวรรณ เกิดแก้ว 68070079
8. นาย นัธทวัฒน์ พละเดช 68070083
9. นาย นิรัชพล กลิ่นขจร 68070089
10. นาย วิชยุตม์ สร้อยสน 68070172
