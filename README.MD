# Programming course assignment - tower defense game

Programming course short project to create a tower defense game from scratch.
Written in Scala using Java Swing library for the GUI.

Run from src/main/scala/TowerGame/Game.scala

Supports saving game progress and creating custom maps. Custom maps have some rules when generating from
text files to ensure the pathfinder algorithm can parse the enemies movement route.

## Demo video

<a href="https://www.youtube.com/watch?v=xCErFDo947w">
    <img src="https://i.postimg.cc/YCbPbL4D/Screenshot-2023-11-25-at-11-13-28.png" width="600" alt="Screenshot">
</a>

## Required fixes/additions/updates:

- Redesing the GUI and add main menu for more pleasant gaming experience
- Multithreading
- Adding tower and enemy animations and sprites
- Music and SFX
- Tower enemy scanning optimizing
- Refactor some of the messy controller code
- And bunch of other spaghetti code and shortcuts taken during development...
