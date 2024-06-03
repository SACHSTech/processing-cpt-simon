import processing.core.PApplet;
import processing.core.PImage;

public class Sketch1 extends PApplet {
    // Initializing / declaring variables
    PImage imgBackground;
    PImage imgPlatypus;
    PImage[] kickImages = new PImage[8];
    PImage[] chopImages = new PImage[8];
    PImage[] walkBarry = new PImage[5];
    PImage[] kickImagesR = new PImage[8];
    PImage[] chopImagesR = new PImage[8];
    PImage[] walkBarryR = new PImage[5];

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
    }else{
        image(walkBarry[walkImageIndex], fltBarryX, fltBarryY);
    }
    if (frameCount % (60 / animationFrameRate) == 0) {
        if (movingLeft) {
            walkImageIndex = (walkImageIndex + 1) % walkBarry.length;
        } else if (movingRight) {
            walkImageIndexR = (walkImageIndexR + 1) % walkBarryR.length;
        }else{
            walkImageIndex = (walkImageIndex + 1) % walkBarry.length;
        }
    }
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
