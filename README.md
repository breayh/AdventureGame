# A Night Market Adventure - text game project
A school text-based adventure game project built in Scala. Players must navigate a market, talk to characters, and gather key items to find a way out before time runs out.

# Game Description

The project features terminal command gameplay and visuals, incorporating text layouts, defined commands, and great gameplay script to bring all  market stalls to life. The interactive command system utilizes a text-parsing engine that supports natural player inputs like examining items, talking to characters, picking up or dropping objects, and navigating the map. Item and inventory management form a central mechanic, requiring players to visit stalls, manage their inventory, and make good use of these goods. Underneath, the game relies on a functional Scala architecture using various state representations, pattern matching, and functional game loops. Players need to find an escape routes and a combination of key items and vendor favors they need to pursue.

# Authors

This project was developed collaboratively by a two-person team with distinct responsibilities. My responsibility was to serve as the programmer, driving the technical execution of the project. This included me developing the game loop, modifying the command parser, and designing the data structures for the inventory and item mechanics. My partner acted as the designer, creating the market setting, scripting the dialogue trees and quest flows, and crafting all the visuals and room layouts.

# How to play

## Overview

To escape the market, players explore different stalls and interact with local vendors. The ultimate goal is to discover and acquire 6 photos  required to unlock the main escape route. Progression requires smart inventory management, careful exploration, and resolving vendor requests through item usage or dialog choices.

## Commands

Players interact with the environment using a simple command format. Type _go [place]_ to change the current location or _talk [character]_ to converse with character at a current location. To interact with objects, players can use _examine [item]_ for  item or photo details, _get [item]_ to pick up objects, or _drop [item]_ to leave an item at the current location. Other commands include _help_ to inspect possible actions to perform or _status_ to check current game status.

## Code details

The application is written in Scala using sbt as its primary build tool. The underlying architecture applies functional programming principles, including state objects to track game updates, pattern matching to interpret player commands, and clean rendering loops.

# Project Structure
- `game` - Full game code content.
- `photos` - Visuals that are presented during the gameplay.
