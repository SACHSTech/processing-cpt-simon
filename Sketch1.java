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

    boolean isKicking = false;
    boolean isChopping = false;
    boolean isWalking = false;
    boolean movingLeft = false;
    boolean movingRight = false;

    float fltBarryX = 550;
    float fltBarryY = 550;
    int intBarrySpeed = 5;

    int kickImageIndex = 0;
    int chopImageIndex = 0;
    int walkImageIndex = 0;
    int kickImageIndexR = 0;
    int chopImageIndexR = 0;
    int walkImageIndexR = 0;
    int animationFrameRate = 20;
    int kickDelay = 0;

    int numDoc = 10;
    int numDocR = 10;
    float[] DocX = new float[numDoc];
    float[] DocXR = new float[numDocR];
    float DocY = fltBarryY;
    float DocYR = fltBarryY;
    int[] docWalkIndex = new int[numDoc];
    int[] docWalkIndexR = new int[numDocR];
    int docSpeedR = -2;
    int docSpeed = 2;
    boolean[] enemyVisibleDoc = new boolean[numDoc];
    boolean[] isPunching = new boolean[numDoc];
    boolean[] enemyVisibleDocR = new boolean[numDocR];
    boolean[] isPunchingR = new boolean[numDocR];

    int numNorm = 5;
    float[] NormX = new float[numNorm];
    float NormY = fltBarryY + 50;
    int[] normWalkIndex = new int[numNorm];
    int normSpeed = 4;
    boolean[] enemyVisibleNorm = new boolean[numNorm];
    boolean[] isBiting = new boolean[numNorm];

    int barryLives;
    boolean isInvincible = false;
    int invincibilityDuration = 120;
    int invincibilityCounter = 0;

    boolean isInContact = false;
    int contactCounter = 0;
    int contactDuration = 48;

    // Games
    boolean lastMove = false; 

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

        imgDoc = loadImage("Doc.png");
        imgDoc.resize(160, 160);

        imgNorm = loadImage("Norm.png");
        imgNorm.resize(100, 100);

        // Resizing images and putting them into an Array for Barry's kicking animation
        for (int i = 0; i < kickImages.length; i++) {
            kickImages[i] = loadImage("Kick" + (i + 1) + ".png");
            kickImages[i].resize(150, 150);
        }

        // Resizing images and putting them into an Array for Barry's chopping animation
        for (int i = 0; i < chopImages.length; i++) {
            chopImages[i] = loadImage("Chop" + (i + 1) + ".png");
            chopImages[i].resize(150, 150);
        }

        for (int i = 0; i < walkBarry.length; i++) {
            walkBarry[i] = loadImage("Walk" + (i + 1) + ".png");
            walkBarry[i].resize(150, 150);
        }
        for (int i = 0; i < kickImagesR.length; i++) {
            kickImagesR[i] = loadImage("KickReversed" + (i + 1) + ".png");
            kickImagesR[i].resize(150, 150);
        }
        for (int i = 0; i < chopImagesR.length; i++) {
            chopImagesR[i] = loadImage("ChopReversed" + (i + 1) + ".png");
            chopImagesR[i].resize(150, 150);
        }
        for (int i = 0; i < walkBarryR.length; i++) {
            walkBarryR[i] = loadImage("WalkReversed" + (i + 1) + ".png");
            walkBarryR[i].resize(150, 150);
        }

        for (int i = 0; i < walkDoc.length; i++) {
            walkDoc[i] = loadImage("DocWalk" + (i + 1) + ".png");
            walkDoc[i].resize(160, 160);
        }

        for (int i = 0; i < punchDoc.length; i++) {
            punchDoc[i] = loadImage("DocPunch" + (i + 1) + ".png");
            punchDoc[i].resize(160, 160);
        }

        for (int i = 0; i < walkDocR.length; i++) {
            walkDocR[i] = loadImage("DocWalkR" + (i + 1) + ".png");
            walkDocR[i].resize(160, 160);
        }

        for (int i = 0; i < punchDocR.length; i++) {
            punchDocR[i] = loadImage("DocPunchR" + (i + 1) + ".png");
            punchDocR[i].resize(160, 160);
        }

        for (int i = 0; i < walkNorm.length; i++) {
            walkNorm[i] = loadImage("NormWalk" + (i + 1) + ".png");
            walkNorm[i].resize(100, 100);
        }

        for (int i = 0; i < biteNorm.length; i++) {
            biteNorm[i] = loadImage("NormBite" + (i + 1) + ".png");
            biteNorm[i].resize(100, 100);
        }

        for (int i = 0; i < numDoc; i++) {
            DocX[i] = 750 + 200 * (i + 1);
            docWalkIndex[i] = 0;
            enemyVisibleDoc[i] = true;
            isPunching[i] = false;
        }

        for (int i = 0; i < numDocR; i++) {
            DocXR[i] = -150 * (i + 1);
            docWalkIndexR[i] = 0;
            enemyVisibleDocR[i] = true;
            isPunchingR[i] = false;
        }

        for (int i = 0; i < numNorm; i++) {
            NormX[i] = -150 * (i + 1); 
            normWalkIndex[i] = 0;
            enemyVisibleNorm[i] = true;
            isBiting[i] = false;
        }

        barryLives = 3;
        isInvincible = false;
        invincibilityCounter = 0;

        frameRate(60);
    }

    public void draw() {
        if (kickDelay > 0){ 
            kickDelay--;
        }
        background(imgBackground);

        boolean canMoveLeft = true;
        boolean canMoveRight = true;

        for (int i = 0; i < numDoc; i++) {
            if (enemyVisibleDoc[i]) {
                if (checkCollision(fltBarryX - intBarrySpeed, fltBarryY, DocX[i], DocY)) {
                    canMoveLeft = false;
                }
                if (checkCollision(fltBarryX + intBarrySpeed, fltBarryY, DocX[i], DocY)) {
                    canMoveRight = false;
                }
            }
        }

        for (int i = 0; i < numDocR; i++) {
            if (enemyVisibleDocR[i]) {
                if (checkCollision(fltBarryX - intBarrySpeed, fltBarryY, DocXR[i], DocYR)) {
                    canMoveLeft = false;
                }
                if (checkCollision(fltBarryX + intBarrySpeed, fltBarryY, DocXR[i], DocYR)) {
                    canMoveRight = false;
                }
            }
        }

        // Check for collisions with Norms
        for (int i = 0; i < numNorm; i++) {
            if (enemyVisibleNorm[i]) {
                if (checkCollision(fltBarryX - intBarrySpeed, fltBarryY, NormX[i], NormY)) {
                canMoveLeft = false;
                }
                if (checkCollision(fltBarryX + intBarrySpeed, fltBarryY, NormX[i], NormY)) {
                canMoveRight = false;
                }
            }
        }

        if (movingLeft && canMoveLeft) {
            fltBarryX -= intBarrySpeed;
            fltBarryX = max(fltBarryX, 0);
        }
        if (movingRight && canMoveRight) {
            fltBarryX += intBarrySpeed;
            fltBarryX = min(fltBarryX, width);
        }

        if (isKicking) {
            animateKick();
        } else if (isChopping) {
            animateChop();
        } else if (isWalking) {
            animateWalk();
        } else {
            if (lastMove) {
                image(imgPlatypus, fltBarryX, fltBarryY);
            } else {
                image(imgPlatypusR, fltBarryX, fltBarryY);
            }
        }

        fill(255);
        textSize(24);
        text("Lives: " + barryLives, 10, 30);

        isInContact = false;

        // Handle invincibility
        if (isInvincible) {
            invincibilityCounter++;
            if (invincibilityCounter >= invincibilityDuration) {
                isInvincible = false;
                invincibilityCounter = 0;
            }
        }

        for (int i = 0; i < numDoc; i++) {
            if (enemyVisibleDoc[i]) {
                if (dist(fltBarryX, fltBarryY, DocX[i], DocY) < 60) {
                    isPunching[i] = true;
                    isInContact = true;
                } else {
                    isPunching[i] = false;
                    DocX[i] += docSpeedR;
                }

                if (isPunching[i]) {
                    animateEnemyPunch(i);
                } else {
                    animateEnemyWalk(i);
                }
                

                if (isInContact && !isInvincible) {
                    contactCounter++;
                    if (contactCounter >= contactDuration) {
                        barryLives--;
                        isInvincible = true;
                        contactCounter = 0; 
                        if (barryLives <= 0) {
                            println("Game Over");
                            noLoop(); 
                        }
                    }
                } else {
                    contactCounter = 0;
                }

                // Check for collision with Barry's kick
                if (isKicking && checkCollision(fltBarryX, fltBarryY, DocX[i], DocY)) {
                    enemyVisibleDoc[i] = false;
                }
            }
        }
        for (int i = 0; i < numDocR; i++) {
            if (enemyVisibleDocR[i]) {
                if (dist(fltBarryX, fltBarryY, DocXR[i], DocYR) < 60) {
                    isPunching[i] = true;
                    isInContact = true;
                } else {
                    isPunching[i] = false;
                    DocXR[i] += docSpeed;
                }

                if (isPunching[i]) {
                    animateEnemyPunchR(i);
                } else {
                    animateEnemyWalkR(i);
                }

                if (isInContact && !isInvincible) {
                    contactCounter++;
                    if (contactCounter >= contactDuration) {
                        barryLives--;
                        isInvincible = true;
                        contactCounter = 0; 
                        if (barryLives <= 0) {
                            println("Game Over");
                            noLoop();
                        }
                    }
                } else {
                    contactCounter = 0;
                }

                // Check for collision with Barry's kick
                if (isKicking && checkCollision(fltBarryX, fltBarryY, DocXR[i], DocYR)) {
                    enemyVisibleDocR[i] = false;
                }
            }
        }

        // Move and animate Norms
        for (int i = 0; i < numNorm; i++) {
            if (enemyVisibleNorm[i]) {
                if (dist(fltBarryX, fltBarryY, NormX[i], NormY) < 60) {
                    isBiting[i] = true;
                    isInContact = true;
                } else {
                    isBiting[i] = false;
                    NormX[i] += normSpeed;
                }

                if (isBiting[i]) {
                    animateNormBite(i);
                } else {
                    animateNormWalk(i);
                }

                if (isInContact && !isInvincible) {
                    contactCounter++;
                    if (contactCounter >= contactDuration) {
                        barryLives--;
                        isInvincible = true;
                        contactCounter = 0;
                        if (barryLives <= 0) {
                            println("Game Over");
                            noLoop(); 
                        }
                    }
                } else {
                    contactCounter = 0;
                }

                // Check for collision with Barry's kick
                if (isChopping && checkCollision(fltBarryX, fltBarryY, NormX[i], NormY)) {
                    enemyVisibleNorm[i] = false;
                }
            }
        }
    }

    private void animateKick() {
        if (lastMove) {
            image(kickImages[kickImageIndex], fltBarryX, fltBarryY);
        } else {
            image(kickImagesR[kickImageIndexR], fltBarryX, fltBarryY);
        }
        if (frameCount % (120 / animationFrameRate) == 0) {
            if (lastMove) {
                kickImageIndex = (kickImageIndex + 1) % kickImages.length;
            } else {
                kickImageIndexR = (kickImageIndexR + 1) % kickImagesR.length;
            }
        }
        if (frameCount % (120 / animationFrameRate) == 0) {
            if (movingLeft) {
                kickImageIndex = (kickImageIndex + 1) % kickImages.length;
            } else if (movingRight) {
                kickImageIndexR = (kickImageIndexR + 1) % kickImagesR.length;
            } else {
                if (lastMove) {
                    kickImageIndex = (kickImageIndex + 1) % kickImages.length;
                } else {
                    kickImageIndexR = (kickImageIndexR + 1) % kickImagesR.length;
                }
            }
        }
    }

    private void animateChop() {
        if (lastMove) {
            image(chopImages[chopImageIndex], fltBarryX, fltBarryY);
        } else {
            image(chopImagesR[chopImageIndexR], fltBarryX, fltBarryY);
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
            image(walkBarry[walkImageIndex], fltBarryX, fltBarryY);
        } else if (movingRight) {
            image(walkBarryR[walkImageIndexR], fltBarryX, fltBarryY);
        } else {
            image(walkBarry[walkImageIndex], fltBarryX, fltBarryY);
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

    private boolean checkCollision(float barryX, float barryY, float enemyX, float enemyY) {
        float barryWidth = 50; 
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
    }

    public static void main(String[] args) {
        PApplet.main("Sketch1");
    }
}
