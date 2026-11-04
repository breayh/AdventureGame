# A Night Market Adventure

A text-based adventure game written in Scala as a university course project. You find yourself in a busy night market and need to find a way out by exploring stalls, talking to vendors, and collecting the right items before time runs out.

> **Note:** This game was built on top of a university course assignment. The course-provided starter code and the game's source code are not included in this repository. This page covers the game itself and my part in making it.

## About the game

The game is played by typing commands into a graphical window that shows your current location, the events of each turn, and a turn counter. The market is made up of stalls, each with its own vendors, items, and requests. Progress depends on exploring carefully, managing your inventory, and helping vendors by giving them items or making the right dialogue choices.

The goal is to find and collect **6 photos**, which together unlock the main escape route.

## Screenshots

<img width="1000" height="700" alt="image" src="https://github.com/user-attachments/assets/0e3b472d-9cc0-452b-af05-1ac0ebd68762" />

<img width="1002" height="702" alt="image" src="https://github.com/user-attachments/assets/eb53b5ab-a7d6-469e-92b0-b3e082ec1a82" />

<img width="1006" height="779" alt="image" src="https://github.com/user-attachments/assets/af17e399-aaba-4202-a282-2be88cfe5d33" />


## How to play

| Command | What it does |
|---|---|
| `go [place]` | Move to another location |
| `talk [character]` | Talk to a character at your current location |
| `examine [item]` | See details about an item or photo |
| `get [item]` | Pick up an item |
| `drop [item]` | Leave an item at your current location |
| `status` | Check the current state of the game |
| `help` | List the available commands |
| `quit` | Quit the game |
| `inventory` | Check the current state of your inventory |
| `view [photo]` | View a photo |

## My role

This was a two-person project with split responsibilities.

- **Me (programmer):** I worked on the game loop, extended the command parser, and designed the data structures for items and the inventory.
- **My partner (designer):** They created the market setting, wrote the dialogue and quest flow, and designed the visuals and room layouts.

## Technical notes

- Language: Scala 3
- Build tool: sbt
- Interface: Scala Swing GUI

## Repository contents

- `photos/`: screenshots and visuals from the game
