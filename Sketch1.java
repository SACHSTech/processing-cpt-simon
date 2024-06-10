import processing.core.PApplet;
import processing.core.PImage;

public class Sketch1 extends PApplet {
    // Initializing / declaring variables
    PImage imgBackground;
    PImage imgPlatypus;
    PImage imgPlatypusR;
    PImage imgDoc;
    PImage imgNorm;

    PImage[] kickImages = new PImage[8];
    PImage[] chopImages = new PImage[8];
    PImage[] walkBarry = new PImage[5];
    PImage[] kickImagesR = new PImage[8];
    PImage[] chopImagesR = new PImage[8];
    PImage[] walkBarryR = new PImage[5];

    PImage[] walkDoc = new PImage[5];
    PImage[] punchDoc = new PImage[6];
    PImage[] walkDocR = new PImage[5];
    PImage[] punchDocR = new PImage[6];

    PImage[] walkNorm = new PImage[5];
    PImage[] biteNorm = new PImage[8];
    PImage[] walkNormR = new PImage[5];
    PImage[] biteNormR = new PImage[8];

    boolean isKicking = false;
    boolean isChopping = false;
    boolean isWalking = false;
    boolean movingLeft = false;
    boolean movingRight = false;

    int intBarryX = 550;
    int intBarryY = 550;
    int intBarrySpeed = 5;

    int kickImageIndex = 0;
    int chopImageIndex = 0;
    int walkImageIndex = 0;
    int kickImageIndexR = 0;
    int chopImageIndexR = 0;
    int walkImageIndexR = 0;
    int animationFrameRate = 20;
    int kickDelay = 0;

    int numDoc = 1;
    int numDocR = 1;
    int[] DocX = new int[numDoc];
    int[] DocXR = new int[numDocR];
    int DocY = intBarryY;
    int DocYR = intBarryY;
    int[] docWalkIndex = new int[numDoc];
    int[] docWalkIndexR = new int[numDocR];
    int docSpeedR = -2;
    int docSpeed = 2;
    boolean[] enemyVisibleDoc = new boolean[numDoc];
    boolean[] isPunching = new boolean[numDoc];
    boolean[] enemyVisibleDocR = new boolean[numDocR];
    boolean[] isPunchingR = new boolean[numDocR];

    int numNorm = 1;
    int numNormR = 1;
    int[] NormX = new int[numNorm];
    int[] NormXR = new int[numNormR];
    int NormY = intBarryY + 50;
    int NormYR = intBarryY + 50;
    int[] normWalkIndex = new int[numNorm];
    int[] normWalkIndexR = new int[numNormR];
    int normSpeed = 4;
    int normSpeedR = -4;
    boolean[] enemyVisibleNorm = new boolean[numNorm];
    boolean[] enemyVisibleNormR = new boolean[numNormR];
    boolean[] isBiting = new boolean[numNorm];
    boolean[] isBitingR = new boolean[numNorm];

    int barryLives;
    boolean isInvincible = false;
    int invincibilityDuration = 120;
    int invincibilityCounter = 0;

    boolean isInContactDoc = false;
    boolean isInContactDocR = false;
    boolean isInContactNorm = false;
    boolean isInContactNormR = false;
    int[] contactCounterDoc = new int[numDoc];
    int[] contactCounterDocR = new int[numDocR];
    int[] contactCounterNorm = new int[numNorm];
    int[] contactCounterNormR = new int[numNormR];
    int contactDuration = 24;

    // Games
    boolean lastMove = false; 

    boolean showHomeScreen = true;

    /**
     * Size of the window
     */
    public void settings() {
        size(750, 750);
    }

    public void setup() {
        // Resizing image for background
        imgBackground = loadImage("FirstStage.jpg");
        imgBackground.resize(width, height);

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
        ImageArrays(walkDoc, "DocWalk", 160);
        ImageArrays(punchDoc, "DocPunch", 160);
        ImageArrays(walkDocR, "DocWalkR", 160);
        ImageArrays(punchDocR, "DocPunchR", 160);
        ImageArrays(walkNorm, "NormWalk", 100);
        ImageArrays(biteNorm, "NormBite", 100);
        ImageArrays(walkNormR, "NormWalkR", 100);
        ImageArrays(biteNormR, "NormBiteR", 100);

        // Collision and positioning of the characters
        EnemyDetails(numDoc, DocX, 750, 200, docWalkIndex, enemyVisibleDoc, isPunching, contactCounterDoc);        
        EnemyDetails(numDocR, DocXR, 0, -150, docWalkIndexR, enemyVisibleDocR, isPunchingR, contactCounterDocR);
        EnemyDetails(numNorm, NormX, 0, -150, normWalkIndex, enemyVisibleNorm, isBiting, contactCounterNorm);
        EnemyDetails(numNormR, NormXR, 750, 200, normWalkIndexR, enemyVisibleNormR, isBitingR, contactCounterNormR);

        // Life System details
        barryLives = 3;
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
        background(imgBackground);

        // Setting the original boolean values to true so Barry can move left and right
        boolean canMoveLeft = true;
        boolean canMoveRight = true;

        // Checks collision with Barry and Doc enemies to determine whether or not Barry can move or not
        for (int i = 0; i < numDoc; i++) {
            if (enemyVisibleDoc[i]) {
                if (checkCollision(intBarryX - intBarrySpeed, intBarryY, DocX[i], DocY)) {
                    canMoveLeft = false;
                }
                if (checkCollision(intBarryX + intBarrySpeed, intBarryY, DocX[i], DocY)) {
                    canMoveRight = false;
                }
            }
        }
        // Checks collision with Barry and DocR enemies to determine whether or not Barry can move or not
        for (int i = 0; i < numDocR; i++) {
            if (enemyVisibleDocR[i]) {
                if (checkCollision(intBarryX - intBarrySpeed, intBarryY, DocXR[i], DocYR)) {
                    canMoveLeft = false;
                }
                if (checkCollision(intBarryX + intBarrySpeed, intBarryY, DocXR[i], DocYR)) {
                    canMoveRight = false;
                }
            }
        }

        // Check for collisions with Barry and Norm enemies to determine whether or not Barry can move or not
        for (int i = 0; i < numNorm; i++) {
            if (enemyVisibleNorm[i]) {
                if (checkCollision(intBarryX - intBarrySpeed, intBarryY, NormX[i], NormY)) {
                canMoveLeft = false;
                }
                if (checkCollision(intBarryX + intBarrySpeed, intBarryY, NormX[i], NormY)) {
                canMoveRight = false;
                }
            }
        }
        // Checks collision with Barry and NormR enemies to determine whether or not Barry can move or not
        for ( int i = 0; i < numNormR; i++) {
            if (enemyVisibleNormR[i]) {
                if (checkCollision(intBarryX - intBarrySpeed, intBarryY, NormXR[i], NormYR)) {
                    canMoveLeft = false;
                }
                if (checkCollision(intBarryX + intBarrySpeed, intBarryY, NormXR[i], NormYR)) {
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
        isInContactDoc = false;
        isInContactDocR = false;
        isInContactNorm = false;
        isInContactNormR = false;

        // Adds invincibility when the boolean variable is true
        if (isInvincible) {
            invincibilityCounter++;
            // When the counter adds to a certain value, the invincibility is lost
            if (invincibilityCounter >= invincibilityDuration) {
                isInvincible = false;
                invincibilityCounter = 0;
            }
        }

        // Determines the action of Doc enemies 
        for (int i = 0; i < numDoc; i++) {
            if (enemyVisibleDoc[i]) {
                if (dist(intBarryX, intBarryY, DocX[i], DocY) < 60) {
                    isPunching[i] = true;
                    BarryLives(true, contactCounterDoc);
                } else {
                    isPunching[i] = false;
                    DocX[i] += docSpeedR;
                }
                // When this boolean array is true, Doc will start punching Barry
                if (isPunching[i]) {
                    animateEnemyPunch(i);
                } else {
                    animateEnemyWalk(i);
                }
                // Checks whether or not Barry loses a life due to a Doc enemy or not
                BarryLives(isInContactDoc, contactCounterDoc);

                // Check for collision with Barry's kick
                if (isKicking && checkCollision(intBarryX, intBarryY, DocX[i], DocY) && !lastMove) {
                    enemyVisibleDoc[i] = false;
                }
            }
        }
        // Determines the action of DocR enemies 
        for (int i = 0; i < numDocR; i++) {
            if (enemyVisibleDocR[i]) {
                if (dist(intBarryX, intBarryY, DocXR[i], DocYR) < 60) {
                    isPunchingR[i] = true;
                     BarryLives(true, contactCounterDocR);
                } else {
                    isPunchingR[i] = false;
                    DocXR[i] += docSpeed;
                }

                // When this boolean array is true, DocR will start punching Barry
                if (isPunchingR[i]) {
                    animateEnemyPunchR(i);
                } else {
                    animateEnemyWalkR(i);
                }
                // Checks whether or not Barry loses a life due to a DocR enemy or not
                BarryLives(isInContactDocR, contactCounterDocR);

                // Check for collision with Barry's kick
                if (isKicking && checkCollision(intBarryX, intBarryY, DocXR[i], DocYR) && lastMove) {
                    enemyVisibleDocR[i] = false;
                }
            }
        }

        // Determines the action of Norm enemies 
        for (int i = 0; i < numNorm; i++) {
            if (enemyVisibleNorm[i]) {
                if (dist(intBarryX, intBarryY, NormX[i], NormY) < 60) {
                    isBiting[i] = true;
                    BarryLives(true, contactCounterNorm);
                } else {
                    isBiting[i] = false;
                    NormX[i] += normSpeed;
                }
                
                // When this boolean array is true, Norm will start punching Barry
                if (isBiting[i]) {
                    animateNormBite(i);
                } else {
                    animateNormWalk(i);
                }
                // Checks whether or not Barry loses a life due to a Norm enemy or not
               BarryLives(isInContactNorm, contactCounterNorm);

                // Check for collision with Barry's kick
                if (isChopping && checkCollision(intBarryX, intBarryY, NormX[i], NormY) && lastMove) {
                    enemyVisibleNorm[i] = false;
                }
            }
        }

        for (int i = 0; i < numNormR; i++) {
            if (enemyVisibleNormR[i]) {
                if (dist(intBarryX, intBarryY, NormXR[i], NormYR) < 60) {
                    isBitingR[i] = true;
                    BarryLives(true, contactCounterNormR);
                } else {
                    isBitingR[i] = false;
                    NormXR[i] += normSpeedR;
                }
                // When this boolean array is true, NormR will start punching Barry
                if (isBitingR[i]) {
                    animateNormBiteR(i);
                } else {
                    animateNormWalkR(i);
                }
                // Checks whether or not Barry loses a life due to a NormR enemy or not
               BarryLives(isInContactNormR, contactCounterNormR);

                // Check for collision with Barry's kick
                if (isChopping && checkCollision(intBarryX, intBarryY, NormXR[i], NormYR) && !lastMove) {
                    enemyVisibleNormR[i] = false;
                }
            }
        }
        killBill();
    }
    /**
     * A method Image Arrays 
     * @param images
     * @param name
     * @param intSize
     */
    private void ImageArrays(PImage[] images, String name, int intSize){
        for (int i = 0; i < images.length; i++) {
            images[i] = loadImage( name + (i + 1) + ".png");
            images[i].resize(intSize, intSize);
        }
    }

    private void EnemyDetails(int intNumEnemies, int[] intEnemies, int intStartPosition, int intSpacing, int[] intIndex, boolean[] isVisible, boolean[] isAttacking, int[] intContact){
        for (int i = 0; i < intNumEnemies; i++) {
            intEnemies[i] = intStartPosition + intSpacing * (i + 1);
            intIndex[i] = 0;
            isVisible[i] = true;
            isAttacking[i] = false;
            intContact[i] = 0;
        } 
    }

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

    private void animateEnemyWalk(int i) {
        if (enemyVisibleDoc[i]) {
            int index = docWalkIndex[i] % walkDoc.length;
            image(walkDoc[index], DocX[i], DocY);
            if (frameCount % (60 / animationFrameRate) == 0) {
                docWalkIndex[i] = (docWalkIndex[i] + 1) % walkDoc.length;
            }
        }
    }

    private void animateEnemyPunch(int i) {
        if (enemyVisibleDoc[i]) {
            image(punchDoc[docWalkIndex[i]], DocX[i], DocY);
            if (frameCount % (60 / animationFrameRate) == 0) {
                docWalkIndex[i] = (docWalkIndex[i] + 1) % punchDoc.length;
            }
        }
    }

    private void animateEnemyWalkR(int i) {
        if (enemyVisibleDocR[i]) {
            int index = docWalkIndexR[i] % walkDocR.length;
            image(walkDocR[index], DocXR[i], DocYR);
            if (frameCount % (60 / animationFrameRate) == 0) {
                docWalkIndexR[i] = (docWalkIndexR[i] + 1) % walkDocR.length;
            }
        }
    }

  

    private void animateEnemyPunchR(int i) {
        if (enemyVisibleDocR[i]) {
            image(punchDocR[docWalkIndexR[i]], DocXR[i], DocYR);
            if (frameCount % (60 / animationFrameRate) == 0) {
                docWalkIndexR[i] = (docWalkIndexR[i] + 1) % punchDocR.length;
            }
        }
    }

    private void animateNormWalk(int i) {
        int index = normWalkIndex[i] % walkNorm.length;
        image(walkNorm[index], NormX[i], NormY);
        if (frameCount % (60 / animationFrameRate) == 0) {
            normWalkIndex[i] = (normWalkIndex[i] + 1) % walkNorm.length;
        }
    }

    private void animateNormBite(int i) {
        if (enemyVisibleNorm[i]) {
            image(biteNorm[normWalkIndex[i]], NormX[i], NormY);
            if (frameCount % (60 / animationFrameRate) == 0) {
                normWalkIndex[i] = (normWalkIndex[i] + 1) % biteNorm.length;
            }
        }
    }

    private void animateNormWalkR(int i) {
        int index = normWalkIndexR[i] % walkNormR.length;
        image(walkNormR[index], NormXR[i], NormYR);
        if (frameCount % (60 / animationFrameRate) == 0) {
            normWalkIndexR[i] = (normWalkIndexR[i] + 1) % walkNormR.length;
        }
    }

    private void animateNormBiteR(int i) {
        if (enemyVisibleNormR[i]) {
            image(biteNormR[normWalkIndexR[i]], NormXR[i], NormYR);
            if (frameCount % (60 / animationFrameRate) == 0) {
                normWalkIndexR[i] = (normWalkIndexR[i] + 1) % biteNormR.length;
            }
        }
    }

    public void killBill() {
        if (barryLives <= 0) {
            println("Game Over");
            noLoop(); 
        }
    }

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
        PApplet.main("Sketch1");
    }
}



