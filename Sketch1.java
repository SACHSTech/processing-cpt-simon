import processing.core.PApplet;
import processing.core.PImage;
import java.util.Random;

public class Sketch1 extends PApplet {
    // Initializing / declaring variables
    Random myRandom = new Random();

    PImage imgBackground;
    PImage imgPlatypus;
    PImage imgPlatypusR;
    PImage imgDoc;
    PImage imgNorm;
    PImage imgHome;

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

    int numDoc = myRandom.nextInt(20, 30);
    int numDocR = myRandom.nextInt(20, 30);
    int[] DocX = new int[numDoc];
    int[] DocXR = new int[numDocR];
    int DocY = intBarryY;
    int DocYR = intBarryY;
    int[] docWalkIndex = new int[numDoc];
    int[] docWalkIndexR = new int[numDocR];
    int docSpeedR = -1;
    int docSpeed = 1;
    boolean[] enemyVisibleDoc = new boolean[numDoc];
    boolean[] isPunching = new boolean[numDoc];
    boolean[] enemyVisibleDocR = new boolean[numDocR];
    boolean[] isPunchingR = new boolean[numDocR];

    int numNorm = myRandom.nextInt(15, 25);
    int numNormR = myRandom.nextInt(15, 25);
    int[] NormX = new int[numNorm];
    int[] NormXR = new int[numNormR];
    int NormY = intBarryY + 50;
    int NormYR = intBarryY + 50;
    int[] normWalkIndex = new int[numNorm];
    int[] normWalkIndexR = new int[numNormR];
    int normSpeed = 2;
    int normSpeedR = -2;
    boolean[] enemyVisibleNorm = new boolean[numNorm];
    boolean[] enemyVisibleNormR = new boolean[numNormR];
    boolean[] isBiting = new boolean[numNorm];
    boolean[] isBitingR = new boolean[numNormR];

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
        // Resizing image for background of level 1
        imgBackground = loadImage("FirstStage.jpg");
        imgBackground.resize(width, height);

        //Resizing image for HomeScreen 
        imgHome = loadImage("HomeScreen.jpg");
        imgHome.resize(width, height);

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
        EnemyDetails(DocX, false, docWalkIndex, enemyVisibleDoc, isPunching, contactCounterDoc);        
        EnemyDetails(DocXR, true, docWalkIndexR, enemyVisibleDocR, isPunchingR, contactCounterDocR);
        EnemyDetails(NormX, true, normWalkIndex, enemyVisibleNorm, isBiting, contactCounterNorm);
        EnemyDetails(NormXR,false, normWalkIndexR, enemyVisibleNormR, isBitingR, contactCounterNormR);

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
        background(imgHome); 
        fill(255); // White text
        textSize(96);
        textAlign(CENTER, CENTER);
        text("Barry's Adventure", width / 2, height / 2 - 325);
        fill(249, 255, 207);
        rectMode(CORNER);
        rect(50, 550, 200, 100, 20);
        fill(0);
        textSize(56);
        text("Play", 150, 600);
        fill(249, 255, 207);
        rect(500, 550, 200, 100, 20);
        fill(0);
        text("Tutorial", 600, 600);
        image(imgPlatypusR, 300, 550);
    
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
            }
        }

        // Check for collisions with Barry and Norm enemies to determine whether or not Barry can move or not
        for (int i = 0; i < numNorm; i++) {
            if (enemyVisibleNorm[i]) {
                if (checkCollision(intBarryX - intBarrySpeed, intBarryY, NormX[i], NormY)) {
                canMoveLeft = false;
                }
            }
        }
        // Checks collision with Barry and NormR enemies to determine whether or not Barry can move or not
        for ( int i = 0; i < numNormR; i++) {
            if (enemyVisibleNormR[i]) {
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
                if (dist(intBarryX, intBarryY, NormXR[i] - 50, NormYR) < 60) {
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
                if (isChopping && checkCollision(intBarryX, intBarryY, NormXR[i] - 50, NormYR) && !lastMove) {
                    enemyVisibleNormR[i] = false;
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
        if (enemyVisibleDoc[i]) {
            int index = docWalkIndex[i] % walkDoc.length;
            image(walkDoc[index], DocX[i], DocY);
            if (frameCount % (60 / animationFrameRate) == 0) {
                docWalkIndex[i] = (docWalkIndex[i] + 1) % walkDoc.length;
            }
        }
    }
    /** 
     * 
     */
    private void animateEnemyPunch(int i) {
        if (enemyVisibleDoc[i]) {
            image(punchDoc[docWalkIndex[i]], DocX[i], DocY);
            if (frameCount % (60 / animationFrameRate) == 0) {
                docWalkIndex[i] = (docWalkIndex[i] + 1) % punchDoc.length;
            }
        }
    }
    /**
     * 
     * @param i
     */
    private void animateEnemyWalkR(int i) {
        if (enemyVisibleDocR[i]) {
            int index = docWalkIndexR[i] % walkDocR.length;
            image(walkDocR[index], DocXR[i], DocYR);
            if (frameCount % (60 / animationFrameRate) == 0) {
                docWalkIndexR[i] = (docWalkIndexR[i] + 1) % walkDocR.length;
            }
        }
    }

  
    /**
     * 
     * @param i
     */
    private void animateEnemyPunchR(int i) {
        if (enemyVisibleDocR[i]) {
            image(punchDocR[docWalkIndexR[i]], DocXR[i], DocYR);
            if (frameCount % (60 / animationFrameRate) == 0) {
                docWalkIndexR[i] = (docWalkIndexR[i] + 1) % punchDocR.length;
            }
        }
    }
    /**
     * 
     * @param i
     */
    private void animateNormWalk(int i) {
        int index = normWalkIndex[i] % walkNorm.length;
        image(walkNorm[index], NormX[i], NormY);
        if (frameCount % (60 / animationFrameRate) == 0) {
            normWalkIndex[i] = (normWalkIndex[i] + 1) % walkNorm.length;
        }
    }
    /**
     * 
     * @param i
     */
    private void animateNormBite(int i) {
        if (enemyVisibleNorm[i]) {
            image(biteNorm[normWalkIndex[i]], NormX[i], NormY);
            if (frameCount % (60 / animationFrameRate) == 0) {
                normWalkIndex[i] = (normWalkIndex[i] + 1) % biteNorm.length;
            }
        }
    }
    /**
     * 
     * @param i
     */
    private void animateNormWalkR(int i) {
        int index = normWalkIndexR[i] % walkNormR.length;
        image(walkNormR[index], NormXR[i], NormYR);
        if (frameCount % (60 / animationFrameRate) == 0) {
            normWalkIndexR[i] = (normWalkIndexR[i] + 1) % walkNormR.length;
        }
    }
    /**
     * 
     * @param i
     */
    private void animateNormBiteR(int i) {
        if (enemyVisibleNormR[i]) {
            image(biteNormR[normWalkIndexR[i]], NormXR[i], NormYR);
            if (frameCount % (60 / animationFrameRate) == 0) {
                normWalkIndexR[i] = (normWalkIndexR[i] + 1) % biteNormR.length;
            }
        }
    }
    /** 
     * 
     */
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
    }

    public void mouseClicked(){
        if(mouseX > 50 && mouseX < 250 && mouseY > 550 && mouseY < 650) {
            showHomeScreen = false;
        }
    }

    public static void main(String[] args) {
        PApplet.main("Sketch1");
    }
}



