[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/B2OnycBl)
[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-718a45dd9cf7e7f842a935f5ebbe5719a5e09af4491e668f4dbf3b35d5cca122.svg)](https://classroom.github.com/online_ide?assignment_repo_id=15157894&assignment_repo_type=AssignmentRepo)
# Barry's Adventures 

Barry's Adventure is a fun game based on the funny TV Series
Barry's Adventure - README
Introduction
Welcome to "Barry's Adventure"! This is an exciting side-scrolling game where you control Barry, a brave platypus, on a quest to navigate through various obstacles and enemies. This document provides an overview of the game's structure, functionality, and how to get started.

Prerequisites
To run this game, you need to have:

Java Development Kit (JDK) installed.
Processing library for Java.
Setup Instructions
Install JDK: Ensure you have the JDK installed. You can download it from the Oracle website.

Install Processing Library: Download the Processing core library from Processing.org.

Set Up Your Project:

Create a new Java project in your preferred IDE.
Add the Processing core library to your project's build path.
Add Game Assets:

Ensure you have the required image assets in your project directory: FirstStage.jpg, Barry.png, BarryR.png, and other character images like Kick1.png, Chop1.png, etc.
Place all images in the same directory as your game source code or adjust the file paths in the code accordingly.
Game Structure
Variables and Initial Setup
Random Object: Used to generate random numbers for enemy placement and counts.
PImage Objects: For background and character images.
Boolean Flags: Track various states such as kicking, chopping, walking, and movement directions.
Position and Speed Variables: For Barry's coordinates and speed.
Arrays: Store images for animations and enemy attributes.
Enemy Counts and Positions: Randomly determined within specified ranges.
Game Loop
Settings
The settings method sets the size of the game window.

java
public void settings() {
    size(650, 650);
}
Setup
The setup method initializes game assets, resizes images, and prepares the initial state of the game.

java
public void setup() {
    // Load and resize images
    imgBackground = loadImage("FirstStage.jpg");
    imgBackground.resize(width, height);
    // Load and resize Barry's images
    imgPlatypus = loadImage("Barry.png");
    imgPlatypus.resize(150, 150);
    imgPlatypusR = loadImage("BarryR.png");
    imgPlatypusR.resize(150, 150);
    
    // Load animation frames
    ImageArrays(kickImages, "Kick", 150);
    ImageArrays(chopImages, "Chop", 150);
    ImageArrays(walkBarry, "Walk", 150);
    ImageArrays(kickImagesR, "KickReversed", 150);
    ImageArrays(chopImagesR, "ChopReversed", 150);
    ImageArrays(walkBarryR, "WalkReversed", 150);
    ImageArrays(walkDoc, "DocWalk", 160);
    ImageArrays(punchDoc, "DocPunch", 160);
    ImageArrays(walkDocR, "DocWalkR", 160);
    ImageArrays(punchDocR, "DocPunchR", 160);
    ImageArrays(walkNorm, "NormWalk", 100);
    ImageArrays(biteNorm, "NormBite", 100);
    ImageArrays(walkNormR, "NormWalkR", 100);
    ImageArrays(biteNormR, "NormBiteR", 100);
    
    // Initialize enemy positions and states
    EnemyDetails(DocX, false, docWalkIndex, enemyVisibleDoc, isPunching, contactCounterDoc);
    EnemyDetails(DocXR, true, docWalkIndexR, enemyVisibleDocR, isPunchingR, contactCounterDocR);
    EnemyDetails(NormX, true, normWalkIndex, enemyVisibleNorm, isBiting, contactCounterNorm);
    EnemyDetails(NormXR, false, normWalkIndexR, enemyVisibleNormR, isBitingR, contactCounterNormR);
    
    // Initial player settings
    barryLives = 5;
    isInvincible = false;
    invincibilityCounter = 0;
    
    // Set frame rate
    frameRate(60);
}
Game Display and Logic
The draw method is the main game loop which handles the game state, rendering, and interactions.

java
public void draw() {
    if (showHomeScreen) {
        displayHomeScreen();
    } else {
        runGame();
    }
}
Home Screen
Displays the home screen until the player starts the game.

java
public void displayHomeScreen() {
    background(0);
    fill(255);
    textSize(48);
    textAlign(CENTER, CENTER);
    text("Barry's Adventure", width / 2, height / 2 - 50);
    textSize(24);
    text("Press any key to start", width / 2, height / 2 + 50);
}
Game Mechanics
Movement and Collision
Barry can move left and right, and collisions with enemies prevent movement and reduce lives.

java
// Handle Barry's movement and collision detection
if (movingLeft && canMoveLeft) {
    intBarryX -= intBarrySpeed;
    intBarryX = max(intBarryX, 0);
}
if (movingRight && canMoveRight) {
    intBarryX += intBarrySpeed;
    intBarryX = min(intBarryX, width);
}
Animations
Handles the animation frames for various actions (kick, chop, walk).

java
private void animateKick() {
    if (lastMove) {
        image(kickImages[kickImageIndex], intBarryX, intBarryY);
    } else {
        image(kickImagesR[kickImageIndexR], intBarryX, intBarryY);
    }
    if (frameCount % (60 / animationFrameRate) == 0) {
        if (lastMove) {
            kickImageIndex = (kickImageIndex + 1) % kickImages.length;
        } else {
            kickImageIndexR = (kickImageIndexR + 1) % kickImagesR.length;
        }
    }
}
Enemy Behavior
Enemies are initialized with random positions and have defined behaviors (walking, punching, biting).

java
private void animateEnemyWalk(int i) {
    if (enemyVisibleDoc[i]) {
        int index = docWalkIndex[i] % walkDoc.length;
        image(walkDoc[index], DocX[i], DocY);
        if (frameCount % (60 / animationFrameRate) == 0) {
            docWalkIndex[i] = (docWalkIndex[i] + 1) % walkDoc.length;
        }
    }
}
Life System
Barry has a limited number of lives, and collisions with enemies reduce lives.

java
private void BarryLives(boolean isInContactEnemy, int[] contactCounter) {
    for (int i = 0; i < contactCounter.length; i++) {
        if (isInContactEnemy && !isInvincible) {
            contactCounter[i]++;
        }
        if (contactCounter[i] >= contactDuration) {
            barryLives--;
            isInvincible = true;
            contactCounter[i] = 0;
        }
    }
}
Game Over Screen
Displays a game over screen when Barry's lives reach zero.

java
public void killBillPage() {
    if (barryLives <= 0) {
        background(imgBackground);
        image(imgPlatypus, 250, 550);
        textSize(48);
        textAlign(CENTER, CENTER);
        text("YOU KILLED BARRY", width / 2, height / 2 - 50);
        textSize(36);
        text("Click the R key to restart", width / 2, height / 2 - 10);
    }
}
Controls
Left Arrow: Move Barry left.
Right Arrow: Move Barry right.
'A' Key: Barry performs a kick.
'S' Key: Barry performs a chop.
Any Key: Start the game from the home screen.
'R' Key: Restart the game after Barry is defeated.
Conclusion
Enjoy guiding Barry through his adventure, avoiding enemies, and overcoming challenges! Feel free to expand and modify the game to add more features and enhance the gameplay experience.
