# CS105 Game Project

Welcome! This project is a simple turn-based game featuring various characters with unique abilities, maps with different effects, and dynamic weather conditions that influence gameplay.

## **Project Overview**
The game is designed as a two-dimensional battlefield where two teams, each consisting of two characters, face off. The players can select their champions, map, and battle while observing the effects of maps, weather, and characters’ unique stats on gameplay.

## **Repository Structure**
```plaintext
root/
├── src/                  # Source code for the project
│   ├── entities/         # Character-related classes 
│   ├── game/             # Core game logic
│   ├── utilities/        # Map, Weather, and other related environmental features
│   └── main/             # Entry point of the application
└── README.md             # Project overview and instructions
```

## **Getting Started**

### **Setup Instructions**
1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Compile and run the project from the `main/` directory.

### **Running the Game**
1. The main entry point for the game is in the `main/` package.
2. Execute the `main` class to start the game.

## **Contribution Guidelines**
- The fundemental branches will be `main` and `dev`. 
- The `main` branch will be used for complete, final-version code. **Writing directly onto the main branch is by default disabled, and merging into it will require you to do a PR (pull request). All four collaborators need to approve of it first.** The `dev` branch can be merged into when specific features are finished, **do not write directly into the dev branch**. For example, if you finished working on the `feature/game-logic` branch, merge it into the `dev` branch afterwards. 
- If you merged your feature branch into ``dev``, make sure to delete it by typing 
```bash
    git branch -d branch_name
```
which deletes the branch locally, and delete it remotely (on Github) by writing 
```bash
git push origin --delete branch_name
```
**MAKE SURE YOU HAVE NO UNMERGED CHANGES BEFORE DELETING BRANCHES. OTHERWISE, DATA WILL BE LOST.**
- Use meaningful branch names when working on a feature (e.g., `feature/game-logic` or `fix/character-bug`). **DO NOT WRITE NON-DESCRIPTIVE BRANCH NAMES SUCH AS `abdullahqalambranch`, MAKE SURE YOUR BRANCHES ARE FEATURE-SPECIFIC.**
- Commit messages should be concise but descriptive. **Do not commit a lot at once, seperate your commits reasonably**
- **Make sure to pull/fetch changes before you do ANY coding.**
## **Key Components**

### **1. Characters**
- Located in the `entities/` package.
- Contains the base abstract class `Character`.
- Implement specific behaviors for:
  - **Assassin** (e.g., `Torva`, `Mortem`)
  - **Archer** (e.g., `Cito`, `Sagitta`)
  - **Fighter** (e.g., `Tigris`, `Ursi`)
  - **Healer** (e.g., `Sanita`, `Nutrix`)


### **2. Maps and Weather**
- Maps and weather types impact character attributes dynamically.
- Effects are implemented in the `utilities/` package.

### **3. Game Logic**
- Managed in the `game/` package.
- Includes team setup, battle progression, and result calculation.

## **Acknowledgments**
This project is part of the CS105 course. Thank you Nitel Muhtaroğlu for overseeing this. 
