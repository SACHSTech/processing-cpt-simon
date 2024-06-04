import processing.core.PApplet;
import processing.core.PImage;

public class Sketch1 extends PApplet {
    // Initializing / declaring variables
    PImage imgBackground;
    PImage imgPlatypus;
    PImage imgDoc;
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

    int numDoc = 10;
    int numDocR = 10; 
    float[] DocX = new float[numDoc];
    float[] DocXR = new float[numDoc];
    float DocY = fltBarryY;
    float DocYR = fltBarryY;
    int[] docWalkIndex = new int[numDoc];
    int[] docWalkIndexR = new int[numDoc];
    int docSpeedR = -2;
    int docSpeed = 2;
    boolean[] enemyVisible = new boolean[numDoc];
    boolean[] isPunching = new boolean[numDoc];

    int barryLives;
    boolean isInvincible = false;
    int invincibilityDuration = 120;
    int invincibilityCounter = 0;

    boolean isInContact = false;  
    int contactCounter = 0;  
    int contactDuration = 48;

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

        imgDoc = loadImage("Doc.png");
        imgDoc.resize(160, 160);

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
            walkDocR[i].resize(160,160);
        }

        for (int i = 0; i < punchDocR.length; i++) {
            punchDocR[i] = loadImage("DocPunchR" + (i + 1) + ".png");
            punchDocR[i].resize(160,160);
        }
    

        for (int i = 0; i < numDoc; i++) {
            DocX[i] = 750 + 200 * (i + 1); 
            docWalkIndex[i] = 0;  
            enemyVisible[i] = true;
            isPunching[i] = false;
        }

        for (int i = 0; i < numDocR; i++) {
            DocXR[i] = - 150 * (i + 1); 
            docWalkIndexR[i] = 0;  
            enemyVisible[i] = true;
            isPunching[i] = false;
        }

        barryLives = 3;
        isInvincible = false;
        invincibilityCounter = 0;

        frameRate(60);
    }

    public void draw() {
        background(imgBackground);

        if (movingLeft) {
            fltBarryX -= intBarrySpeed;
            fltBarryX = max(fltBarryX, 0);
        }
        if (movingRight) {
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
            image(imgPlatypus, fltBarryX, fltBarryY);
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
            if (enemyVisible[i]) {
                if (dist(fltBarryX, fltBarryY, DocX[i], DocY) < 60) {  
                    isPunching[i] = true;
                    isInContact = true; 
                } else {
                    isPunching[i] = false;
                    DocX[i] += docSpeedR;
                    DocXR[i] += docSpeed;
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
                        contactCounter = 0;  // Reset contact counter after losing a life
                        if (barryLives <= 0) {
                            // Handle game over (restart or end game)
                            println("Game Over");
                            noLoop();  // Stop the draw loop for now
                        }
                    }
                } 

                else {
                    contactCounter = 0;  
                }
                // Check for collision with Barry's kick
                if (isKicking && checkCollision(fltBarryX, fltBarryY, DocX[i], DocY)) {
                    enemyVisible[i] = false;
                }
            }
        }

        
    }

   private void animateKick() {
    if (movingLeft) {
        image(kickImages[kickImageIndex], fltBarryX, fltBarryY);
    } else if (movingRight) {
        image(kickImagesR[kickImageIndexR], fltBarryX, fltBarryY);
    }else{
        image(kickImages[kickImageIndex], fltBarryX, fltBarryY);
    }
    if (frameCount % (60 / animationFrameRate) == 0) {
        if (movingLeft) {
            kickImageIndex = (kickImageIndex + 1) % kickImages.length;
        } else if (movingRight) {
            kickImageIndexR = (kickImageIndexR + 1) % kickImagesR.length;
        }else{
            kickImageIndex = (kickImageIndex + 1) % kickImages.length;
        }
        
    }
}

private void animateChop() {
    if (movingLeft) {
        image(chopImages[chopImageIndex], fltBarryX, fltBarryY);
    } else if (movingRight) {
        image(chopImagesR[chopImageIndexR], fltBarryX, fltBarryY);
    }else{
        image(chopImages[chopImageIndex], fltBarryX, fltBarryY);
    }
    if (frameCount % (60 / animationFrameRate) == 0) {
        if (movingLeft) {
            chopImageIndex = (chopImageIndex + 1) % chopImages.length;
        } else if (movingRight) {
            chopImageIndexR = (chopImageIndexR + 1) % chopImagesR.length;
        }else{
            chopImageIndex = (chopImageIndex + 1) % chopImages.length;
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
    if (enemyVisible[i]) {
        int index = docWalkIndex[i] % walkDoc.length;
        image(walkDoc[index], DocX[i], DocY);
        if (frameCount % (60 / animationFrameRate) == 0) {
            docWalkIndex[i] = (docWalkIndex[i] + 1) % walkDoc.length;
        }
    }
}

private void animateEnemyPunch(int i) {
    if (enemyVisible[i]) {
        image(punchDoc[docWalkIndex[i]], DocX[i], DocY);
        if (frameCount % (60 / animationFrameRate) == 0) {
            docWalkIndex[i] = (docWalkIndex[i] + 1) % punchDoc.length;  
        }
    }
}

private boolean checkCollision(float barryX, float barryY, float enemyX, float enemyY) {
    float barryWidth = 50;  // Adjusted width for Barry's kick
    float barryHeight = 50; // Adjusted height for Barry's kick
    float enemyWidth = 50;  // Adjusted width for enemy
    float enemyHeight = 50; // Adjusted height for enemy

    // Calculate bounding box centers
    float barryCenterX = barryX + 75; // Half of original width
    float barryCenterY = barryY + 75; // Half of original height
    float enemyCenterX = enemyX + 80; // Half of original width
    float enemyCenterY = enemyY + 80; // Half of original height

    return barryCenterX < enemyCenterX + enemyWidth && 
           barryCenterX + barryWidth > enemyCenterX && 
           barryCenterY < enemyCenterY + enemyHeight && 
           barryCenterY + barryHeight > enemyCenterY;
}


 public void keyPressed() {
    if (key == 'a' && !isChopping) {
            isKicking = true;
     } else if (key == 's' && !isKicking) {
            isChopping = true;
     } else if (keyCode == LEFT) {
            movingLeft = true;
            isWalking = true;
     } else if (keyCode == RIGHT) {
            movingRight = true;
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
