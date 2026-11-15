# Fishing Day

A small RPG game made with JavaFX 25, developed for the Programming Methodologies exam (Unicam).

You are a fisherman who has just bought a bay for a suspiciously low price. Fish, sell your catch, upgrade your rod and
read the messages in bottles floating on the lake: they tell what happened to the previous owners, and what is waiting
for you at the bottom.

All the documentation (features, responsibilities, classes, persistence, extensibility) is in
the [Wiki](https://github.com/GigioCreeper/FishingDay/wiki).

<img width="2560" height="1440" alt="Fishing-Day-gameplay-screenshot" src="https://github.com/user-attachments/assets/d0a4dfb2-5994-4827-a1f7-54deaf4bb2d4" />

## 🚀 How to run the project

### Prerequisites

- Java 25 (LTS)
- Gradle

### Instructions

```bash
git clone https://github.com/GigioCreeper/FishingDay
cd FishingDay
```

### Project build

```bash
./gradlew build
```

### Execution

```bash
./gradlew run
```

### Commands

| Key | Action |
|---|---|
| Arrow keys | Move the hook |
| `N` | Open the shop |
| `Esc` | Back to the main menu |
| `F11` | Toggle full screen |

## Use of AI tools

AI tools were used as support during development, never as a replacement for design and implementation work:

- **GitHub Copilot**: code review of pull requests, commit message suggestions, autocompletion of repetitive code and
  Javadoc comments.
- **Claude Code**: help organizing the project structure, explanations and review of JavaFX and of some design patterns
  used, and help drafting the documentation (README and Wiki).
- **Gemini**: generation of the graphic assets (sprites and backgrounds).

The game design, the architecture and the core game logic are my own work. Every AI suggestion was reviewed, tested and
adapted before being included in the project. More details are on the AI usage page of
the [Wiki](https://github.com/GigioCreeper/FishingDay/wiki).

## License

[MIT](LICENSE). [Pixelify Sans](src/main/resources/fonts/PixelifySans-OFL.txt) font under the SIL Open Font License.
