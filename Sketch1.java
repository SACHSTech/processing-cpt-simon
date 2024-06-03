import processing.core.PApplet;
import processing.core.PImage;

public class Sketch1 extends PApplet {
    PImage imgBackground;
    PImage imgPlatypus;
    PImage[] kickImages = new PImage[8];
    PImage[] chopImages = new PImage[8];
    PImage[] walkBarry = new PImage[5];

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
    int animationFrameRate = 20;

    public void settings() {
        size(750, 750);
    }

    public void setup() {
        imgBackground = loadImage("FirstStage.jpg");
        imgBackground.resize(width, height);

        imgPlatypus = loadImage("Barry.png");
        imgPlatypus.resize(150, 150);

        for (int i = 0; i < kickImages.length; i++) {
            kickImages[i] = loadImage("Kick" + (i + 1) + ".png");
            kickImages[i].resize(150, 150);
        }

        for (int i = 0; i < chopImages.length; i++) {
            chopImages[i] = loadImage("Chop" + (i + 1) + ".png");
            chopImages[i].resize(150, 150);
        }

        for (int i = 0; i < walkBarry.length; i++) {
            walkBarry[i] = loadImage("Walk" + (i + 1) + ".png");
            walkBarry[i].resize(150, 150);
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
        image(kickImages[kickImageIndex], fltBarryX, fltBarryY);
        if (frameCount % (60 / animationFrameRate) == 0) {
            kickImageIndex = (kickImageIndex + 1) % kickImages.length;
        }
    }

    private void animateChop() {
        image(chopImages[chopImageIndex], fltBarryX, fltBarryY);
        if (frameCount % (60 / animationFrameRate) == 0) {
            chopImageIndex = (chopImageIndex + 1) % chopImages.length;
        }
    }

    private void animateWalk() {
        image(walkBarry[walkImageIndex], fltBarryX, fltBarryY);
        if (frameCount % (60 / animationFrameRate) == 0) {
            walkImageIndex = (walkImageIndex + 1) % walkBarry.length;
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
