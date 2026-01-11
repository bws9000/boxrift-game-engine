# Boxrift Block Engine

Run:
- ./gradlew :app-launcher:run
- ./gradlew :libgdx-desktop:run

### Goal
Build block-based games by composing rules instead of hardcoding behavior.

### Overview
A rules-first game engine for block-based games.

The engine provides:
- A generic block and board model
- A deterministic game loop
- Modular, pluggable rules
- Platform adapters (LibGDX, JavaFX)

Games are created by composing rules, not rewriting logic.

