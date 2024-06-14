import processing.core.PApplet;
import processing.core.PImage;
import java.util.Random;

public class Sketch2 extends PApplet {
    // Initializing / declaring variables
    Random myRandom = new Random();

    PImage imgLab;
    PImage imgPlatypus;
    PImage imgPlatypusR;
    PImage imgSister;
    PImage imgSuzie;
    PImage imgGameOver;

    PImage[] kickImages = new PImage[8];
    PImage[] chopImages = new PImage[8];
    PImage[] walkBarry = new PImage[5];
    PImage[] kickImagesR = new PImage[8];
    PImage[] chopImagesR = new PImage[8];
    PImage[] walkBarryR = new PImage[5];

    PImage[] walkSister = new PImage[5];
    PImage[] punchSister = new PImage[6];
    PImage[] walkSisterR = new PImage[5];
    PImage[] punchSisterR = new PImage[6];

    PImage[] walkSuzie = new PImage[5];
    PImage[] kickSuzie = new PImage[7];
    PImage[] walkSuzieR = new PImage[5];
    PImage[] kickSuzieR = new PImage[7];

    boolean isKicking = false;
    boolean isChopping = false;
    boolean isWalking = false;
    boolean movingLeft = false;
    boolean movingRight = false;

    int intBarryX = 475;
    int intBarryY = 475;
    int intBarrySpeed = 5;

    int kickImageIndex = 0;
    int chopImageIndex = 0;
    int walkImageIndex = 0;
    int kickImageIndexR = 0;
    int chopImageIndexR = 0;
    int walkImageIndexR = 0;
    int animationFrameRate = 20;
    int kickDelay = 0;

    int numSister = myRandom.nextInt(20, 30);
    int numSisterR = myRandom.nextInt(20, 30);
    int[] SisterX = new int[numSister];
    int[] SisterXR = new int[numSisterR];
    int SisterY = intBarryY;
    int SisterYR = intBarryY - 35;
    int[] SisterWalkIndex = new int[numSister];
    int[] SisterWalkIndexR = new int[numSisterR];
    int SisterSpeedR = -1;
    int SisterSpeed = 1;
    boolean[] enemyVisibleSister = new boolean[numSister];
    boolean[] isPunching = new boolean[numSister];
    boolean[] enemyVisibleSisterR = new boolean[numSisterR];
    boolean[] isPunchingR = new boolean[numSisterR];

    int numSuzie = myRandom.nextInt(15, 25);
    int numSuzieR = myRandom.nextInt(15, 25);
    int[] SuzieX = new int[numSuzie];
    int[] SuzieXR = new int[numSuzieR];
    int SuzieY = intBarryY + 25;
    int SuzieYR = intBarryY + 50;
    int[] SuzieWalkIndex = new int[numSuzie];
    int[] SuzieWalkIndexR = new int[numSuzieR];
    int SuzieSpeed = 2;
    int SuzieSpeedR = -2;
    boolean[] enemyVisibleSuzie = new boolean[numSuzie];
    boolean[] enemyVisibleSuzieR = new boolean[numSuzieR];
    boolean[] isBiting = new boolean[numSuzie];
    boolean[] isBitingR = new boolean[numSuzieR];

    int barryLives;
    boolean isInvincible = false;
    int invincibilityDuration = 120;
    int invincibilityCounter = 0;

    boolean isInContactSister = false;
    boolean isInContactSisterR = false;
    boolean isInContactSuzie = false;
    boolean isInContactSuzieR = false;
    int[] contactCounterSister = new int[numSister];
    int[] contactCounterSisterR = new int[numSisterR];
    int[] contactCounterSuzie = new int[numSuzie];
    int[] contactCounterSuzieR = new int[numSuzieR];
    int contactDuration = 24;

    // Games
    boolean lastMove = false; 

    boolean showHomeScreen = true;

    /**
     * Size of the window
     */
    public void settings() {
        size(650, 650);
    }

    public void setup() {
        // Resizing image for background
        imgLab = loadImage("Background For COM SCI.png");
        imgLab.resize(width, height);
        imgGameOver = loadImage("GameOver.png");
        imgGameOver.resize(width, height);

        // Resizing image for Platypus
        imgPlatypus = loadImage("Barry.png");
        imgPlatypus.resize(150, 150);
        imgPlatypusR = loadImage("BarryR.png");
        imgPlatypusR.resize(150, 150);

        // Resizing images and putting them into an Array for character movement 
        ImageArrays(kickImages, "Kick", 150);
        ImageArrays(chopImages, "Chop", 150);
        ImageArrays(walkBarry, "Walk", 150);
        ImageArrays(kickImagesR, "KickReversed", 150);
        ImageArrays(chopImagesR, "ChopReversed", 150);
        ImageArrays(walkBarryR, "WalkReversed", 150);
        ImageArrays(walkSister, "SisterWalk", 160);
        ImageArrays(punchSister, "SisterPunch", 160);
        ImageArrays(walkSisterR, "SisterWalkR", 225);
        ImageArrays(punchSisterR, "SisterPunchR", 225);
        ImageArrays(walkSuzie, "SuzieWalkR", 160);
        ImageArrays(kickSuzie, "SuzieKickR", 160);
        ImageArrays(walkSuzieR, "SuzieWalk", 100);
        ImageArrays(kickSuzieR, "SuzieKick", 100);

        // Collision and positioning of the characters
        EnemyDetails(SisterX, false, SisterWalkIndex, enemyVisibleSister, isPunching, contactCounterSister);        
        EnemyDetails(SisterXR, true, SisterWalkIndexR, enemyVisibleSisterR, isPunchingR, contactCounterSisterR);
        EnemyDetails(SuzieX, true, SuzieWalkIndex, enemyVisibleSuzie, isBiting, contactCounterSuzie);
        EnemyDetails(SuzieXR,false, SuzieWalkIndexR, enemyVisibleSuzieR, isBitingR, contactCounterSuzieR);

        // Life System details
        barryLives = 5;
        isInvincible = false;
        invincibilityCounter = 0;

        // Frame Rate
        frameRate(60);
    }

    /**
     * Helps distingush home screen and the actual game
     */
    public void draw() {
        if (showHomeScreen) {
            displayHomeScreen();
        } else {
            runGame();
        }
    }
    /**
     * Home screen of the game
     */
    public void displayHomeScreen() {
        background(0); // Black background
        fill(255); // White text
        textSize(48);
        textAlign(CENTER, CENTER);
        text("Barry's Adventure", width / 2, height / 2 - 50);
        textSize(24);
        text("Press any key to start", width / 2, height / 2 + 50);
    }
    /**
     * The main game code
     */
    public void runGame() {
        // Kick Delay
        if (kickDelay > 0){ 
            kickDelay--;
        }
        // Adding an image of a background
        background(imgLab);

        // Setting the original boolean values to true so Barry can move left and right
        boolean canMoveLeft = true;
        boolean canMoveRight = true;

        // Checks collision with Barry and Sister enemies to determine whether or not Barry can move or not
        for (int i = 0; i < numSister; i++) {
            if (enemyVisibleSister[i]) {
                if (checkCollision(intBarryX + intBarrySpeed, intBarryY, SisterX[i], SisterY)) {
                    canMoveRight = false;
                }
            }
        }
        // Checks collision with Barry and SisterR enemies to determine whether or not Barry can move or not
        for (int i = 0; i < numSisterR; i++) {
            if (enemyVisibleSisterR[i]) {
                if (checkCollision(intBarryX - intBarrySpeed, intBarryY, SisterXR[i], SisterYR)) {
                    canMoveLeft = false;
                }
            }
        }

        // Check for collisions with Barry and Suzie enemies to determine whether or not Barry can move or not
        for (int i = 0; i < numSuzie; i++) {
            if (enemyVisibleSuzie[i]) {
                if (checkCollision(intBarryX - intBarrySpeed, intBarryY, SuzieX[i], SuzieY)) {
                canMoveLeft = false;
                }
            }
        }
        // Checks collision with Barry and SuzieR enemies to determine whether or not Barry can move or not
        for ( int i = 0; i < numSuzieR; i++) {
            if (enemyVisibleSuzieR[i]) {
                if (checkCollision(intBarryX + intBarrySpeed, intBarryY, SuzieXR[i], SuzieYR)) {
                    canMoveRight = false;
                }
            }
        }
        // Boundaries on the x - position of Barry
        if (movingLeft && canMoveLeft) {
            intBarryX -= intBarrySpeed;
            intBarryX = max(intBarryX, 0);
        }
        if (movingRight && canMoveRight) {
            intBarryX += intBarrySpeed;
            intBarryX = min(intBarryX, width);
        }
        // Using boolean values to perform Barry's actions
        if (isKicking) {
            animateKick();
        } else if (isChopping) {
            animateChop();
        } else if (isWalking) {
            animateWalk();
        } else {
            if (lastMove) {
                image(imgPlatypus, intBarryX, intBarryY);
            } else {
                image(imgPlatypusR, intBarryX, intBarryY);
            }
        }

        // Life indicator
        fill(255);
        textSize(24);
        text("Lives: " + barryLives, 50, 30);

        // Setting initial boolean contact values for all enemies to false
        isInContactSister = false;
        isInContactSisterR = false;
        isInContactSuzie = false;
        isInContactSuzieR = false;

        // Adds invincibility when the boolean variable is true
        if (isInvincible) {
            invincibilityCounter++;
            // When the counter adds to a certain value, the invincibility is lost
            if (invincibilityCounter >= invincibilityDuration) {
                isInvincible = false;
                invincibilityCounter = 0;
            }
        }

        // Determines the action of Sister enemies 
        for (int i = 0; i < numSister; i++) {
            if (enemyVisibleSister[i]) {
                if (dist(intBarryX, intBarryY, SisterX[i], SisterY) < 60) {
                    isPunching[i] = true;
                    BarryLives(true, contactCounterSister);
                } else {
                    isPunching[i] = false;
                    SisterX[i] += SisterSpeedR;
                }
                // When this boolean array is true, Sister will start punching Barry
                if (isPunching[i]) {
                    animateEnemyPunch(i);
                } else {
                    animateEnemyWalk(i);
                }
                // Checks whether or not Barry loses a life due to a Sister enemy or not
                BarryLives(isInContactSister, contactCounterSister);

                // Check for collision with Barry's kick
                if (isKicking && checkCollision(intBarryX, intBarryY, SisterX[i], SisterY) && !lastMove) {
                    enemyVisibleSister[i] = false;
                }
            }
        }
        // Determines the action of SisterR enemies 
        for (int i = 0; i < numSisterR; i++) {
            if (enemyVisibleSisterR[i]) {
                if (dist(intBarryX, intBarryY, SisterXR[i], SisterYR) < 60) {
                    isPunchingR[i] = true;
                     BarryLives(true, contactCounterSisterR);
                } else {
                    isPunchingR[i] = false;
                    SisterXR[i] += SisterSpeed;
                }

                // When this boolean array is true, SisterR will start punching Barry
                if (isPunchingR[i]) {
                    animateEnemyPunchR(i);
                } else {
                    animateEnemyWalkR(i);
                }
                // Checks whether or not Barry loses a life due to a SisterR enemy or not
                BarryLives(isInContactSisterR, contactCounterSisterR);

                // Check for collision with Barry's kick
                if (isKicking && checkCollision(intBarryX, intBarryY, SisterXR[i], SisterYR) && lastMove) {
                    enemyVisibleSisterR[i] = false;
                }
            }
        }

        // Determines the action of Suzie enemies 
        for (int i = 0; i < numSuzie; i++) {
            if (enemyVisibleSuzie[i]) {
                if (dist(intBarryX, intBarryY, SuzieX[i], SuzieY) < 60) {
                    isBiting[i] = true;
                    BarryLives(true, contactCounterSuzie);
                } else {
                    isBiting[i] = false;
                    SuzieX[i] += SuzieSpeed;
                }
                
                // When this boolean array is true, Suzie will start punching Barry
                if (isBiting[i]) {
                    animateSuzieKick(i);
                } else {
                    animateSuzieWalk(i);
                }
                // Checks whether or not Barry loses a life due to a Suzie enemy or not
               BarryLives(isInContactSuzie, contactCounterSuzie);

                // Check for collision with Barry's kick
                if (isChopping && checkCollision(intBarryX, intBarryY, SuzieX[i], SuzieY) && lastMove) {
                    enemyVisibleSuzie[i] = false;
                }
            }
        }

        for (int i = 0; i < numSuzieR; i++) {
            if (enemyVisibleSuzieR[i]) {
                if (dist(intBarryX, intBarryY, SuzieXR[i] - 50, SuzieYR) < 60) {
                    isBitingR[i] = true;
                    BarryLives(true, contactCounterSuzieR);
                } else {
                    isBitingR[i] = false;
                    SuzieXR[i] += SuzieSpeedR;
                }
                // When this boolean array is true, SuzieR will start punching Barry
                if (isBitingR[i]) {
                    animateSuzieKickR(i);
                } else {
                    animateSuzieWalkR(i);
                }
                // Checks whether or not Barry loses a life due to a SuzieR enemy or not
               BarryLives(isInContactSuzieR, contactCounterSuzieR);

                // Check for collision with Barry's kick
                if (isChopping && checkCollision(intBarryX, intBarryY, SuzieXR[i] - 50, SuzieYR) && !lastMove) {
                    enemyVisibleSuzieR[i] = false;
                }
            }
        } 
        killBillPage();
    }
    /**
     * A method named, Image Arrays, that takes images that follow a simple name pattern and input them in an array
     * @param images    images is the array that the images were placed in
     * @param name      name is the main phrase that the images all follow
     * @param intSize   intSize is the size of the image in the game
     */
    private void ImageArrays(PImage[] images, String name, int intSize){
        for (int i = 0; i < images.length; i++) {
            images[i] = loadImage( name + (i + 1) + ".png");
            images[i].resize(intSize, intSize);
        }
    }
    /**
     * 
     * @param intEnemies
     * @param leftOrRight
     * @param intIndex
     * @param isVisible
     * @param isAttacking
     * @param intContact
     */
    private void EnemyDetails(int[] intEnemies, boolean leftOrRight, int[] intIndex, boolean[] isVisible, boolean[] isAttacking, int[] intContact){
        for (int i = 0; i < intEnemies.length; i++) {
            try{
            if (leftOrRight) {
                intEnemies[i] = myRandom.nextInt( -3000, -10); 
            }
            else {
                intEnemies[i] = myRandom.nextInt(760, 3700);
            }
            intIndex[i] = 0;
            isVisible[i] = true;
            isAttacking[i] = false;
            intContact[i] = 0;
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println(i);
            System.out.println(intIndex.length);
            System.out.println(isVisible.length);
            System.out.println(isAttacking.length);
            System.out.println(intContact.length);
            System.out.println("An error occured, Im too lazy to fix: " + e);
            break;
        }
        } 
    }
    /**
     * 
     */
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
    /**
     * 
     */
    private void animateChop() {
        if (lastMove) {
            image(chopImages[chopImageIndex], intBarryX, intBarryY);
        } else {
            image(chopImagesR[chopImageIndexR], intBarryX, intBarryY);
        }
        if (frameCount % (60 / animationFrameRate) == 0) {
            if (movingLeft) {
                chopImageIndex = (chopImageIndex + 1) % chopImages.length;
            } else if (movingRight) {
                chopImageIndexR = (chopImageIndexR + 1) % chopImagesR.length;
            } else {
                if (lastMove) {
                    chopImageIndex = (chopImageIndex + 1) % chopImages.length;
                } else {
                    chopImageIndexR = (chopImageIndexR + 1) % chopImagesR.length;
                }
            }
        }
    }
    /**
     * 
     */
    private void animateWalk() {
        if (movingLeft) {
            image(walkBarry[walkImageIndex], intBarryX, intBarryY);
        } else if (movingRight) {
            image(walkBarryR[walkImageIndexR], intBarryX, intBarryY);
        } else {
            image(walkBarry[walkImageIndex], intBarryX, intBarryY);
        }
        if (frameCount % (60 / animationFrameRate) == 0) {
            if (movingLeft) {
                walkImageIndex = (walkImageIndex + 1) % walkBarry.length;
            } else if (movingRight) {
                walkImageIndexR = (walkImageIndexR + 1) % walkBarryR.length;
            } else {
                walkImageIndex = (walkImageIndex + 1) % walkBarry.length;
            }
        }
    }
    /*
     * 
     */
    private void animateEnemyWalk(int i) {
        if (enemyVisibleSister[i]) {
            int index = SisterWalkIndex[i] % walkSister.length;
            image(walkSister[index], SisterX[i], SisterY);
            if (frameCount % (60 / animationFrameRate) == 0) {
                SisterWalkIndex[i] = (SisterWalkIndex[i] + 1) % walkSister.length;
            }
        }
    }
    /** 
     * 
     */
    private void animateEnemyPunch(int i) {
        if (enemyVisibleSister[i]) {
            image(punchSister[SisterWalkIndex[i]], SisterX[i], SisterY);
            if (frameCount % (60 / animationFrameRate) == 0) {
                SisterWalkIndex[i] = (SisterWalkIndex[i] + 1) % punchSister.length;
            }
        }
    }
    /**
     * 
     * @param i
     */
    private void animateEnemyWalkR(int i) {
        if (enemyVisibleSisterR[i]) {
            int index = SisterWalkIndexR[i] % walkSisterR.length;
            image(walkSisterR[index], SisterXR[i], SisterYR);
            if (frameCount % (60 / animationFrameRate) == 0) {
                SisterWalkIndexR[i] = (SisterWalkIndexR[i] + 1) % walkSisterR.length;
            }
        }
    }

  
    /**
     * 
     * @param i
     */
    private void animateEnemyPunchR(int i) {
        if (enemyVisibleSisterR[i]) {
            image(punchSisterR[SisterWalkIndexR[i]], SisterXR[i], SisterYR);
            if (frameCount % (60 / animationFrameRate) == 0) {
                SisterWalkIndexR[i] = (SisterWalkIndexR[i] + 1) % punchSisterR.length;
            }
        }
    }
    /**
     * 
     * @param i
     */
    private void animateSuzieWalk(int i) {
        int index = SuzieWalkIndex[i] % walkSuzie.length;
        image(walkSuzie[index], SuzieX[i], SuzieY);
        if (frameCount % (60 / animationFrameRate) == 0) {
            SuzieWalkIndex[i] = (SuzieWalkIndex[i] + 1) % walkSuzie.length;
        }
    }
    /**
     * 
     * @param i
     */
    private void animateSuzieKick(int i) {
        if (enemyVisibleSuzie[i]) {
            image(kickSuzie[SuzieWalkIndex[i]], SuzieX[i], SuzieY);
            if (frameCount % (60 / animationFrameRate) == 0) {
                SuzieWalkIndex[i] = (SuzieWalkIndex[i] + 1) % kickSuzie.length;
            }
        }
    }
    /**
     * 
     * @param i
     */
    private void animateSuzieWalkR(int i) {
        int index = SuzieWalkIndexR[i] % walkSuzieR.length;
        image(walkSuzieR[index], SuzieXR[i], SuzieYR);
        if (frameCount % (60 / animationFrameRate) == 0) {
            SuzieWalkIndexR[i] = (SuzieWalkIndexR[i] + 1) % walkSuzieR.length;
        }
    }
    /**
     * 
     * @param i
     */
    private void animateSuzieKickR(int i) {
        if (enemyVisibleSuzieR[i]) {
            image(kickSuzieR[SuzieWalkIndexR[i]], SuzieXR[i], SuzieYR);
            if (frameCount % (60 / animationFrameRate) == 0) {
                SuzieWalkIndexR[i] = (SuzieWalkIndexR[i] + 1) % kickSuzieR.length;
            }
        }
    }
    /** 
     * 
     */
    public void killBillPage() {
        if (barryLives <= 0) {
            background(imgGameOver);
            image(imgPlatypus, 250, 450);
            textSize(48);
            textAlign(CENTER, CENTER);
            fill(0);
            text("YOU KILLED BARRY", width / 2, height / 2 - 50);
            textSize(36);
            text("Click the R key to restart", width / 2, height / 2 - 10);
        }
    }
    /**
     * 
     * @param isInContactEnemy
     * @param contactCounter
     */
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
    /**
     * 
     * @param barryX
     * @param barryY
     * @param enemyX
     * @param enemyY
     * @return
     */
    private boolean checkCollision(float barryX, float barryY, float enemyX, float enemyY) {
        float barryWidth = 60; 
        float barryHeight = 100; 
        float enemyWidth = 100; 
        float enemyHeight = 100; 
    
        return barryX < enemyX + enemyWidth &&
               barryX + barryWidth > enemyX &&
               barryY < enemyY + enemyHeight &&
               barryY + barryHeight > enemyY;
    }
    /**
     * 
     */
    public void keyPressed() {
        if (key == 'a' && !isChopping) {
            isKicking = true;
        } else if (key == 's' && !isKicking && kickDelay == 0) {
            isChopping = true;
        } else if (keyCode == LEFT) {
            movingLeft = true;
            lastMove = true;
            isWalking = true;
        } else if (keyCode == RIGHT) {
            movingRight = true;
            lastMove = false;
            isWalking = true;
        }
    }
    /**
     * 
     */
    public void keyReleased() {
        if (key == 'a') {
            isKicking = false;
            kickImageIndex = 0;
        }
        if (key == 's') {
            isChopping = false;
            chopImageIndex = 0;
        }
        if (keyCode == LEFT || keyCode == RIGHT) {
            movingLeft = false;
            movingRight = false;
            isWalking = false;
            walkImageIndex = 0;
        }
        if (showHomeScreen) {
            showHomeScreen = false;
        }
    }

    public static void main(String[] args) {
        PApplet.main("Sketch2");
    }
}




