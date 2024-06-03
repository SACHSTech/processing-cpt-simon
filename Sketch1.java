import processing.core.PApplet;
import processing.core.PImage;

public class Sketch1 extends PApplet {
  PImage imgBackground;
  PImage imgPlatypus;
  PImage[] kickImages;
  PImage[] chopImages;

  boolean isKicking = false;
  boolean isChopping = false;
  int kickImageIndex = 0;
  int chopImageIndex = 0;
  int animationFrameRate = 20;

  public void settings() {
    size(750, 750);
  }

  public void setup() {
    imgBackground = loadImage("FirstStage.jpg");
    imgBackground.resize(width, height);

    imgPlatypus = loadImage("Barry.png");
    imgPlatypus.resize(150, 150);

    // Initialize and load kick images
    kickImages = new PImage[8];
    for (int i = 0; i < kickImages.length; i++) {
      kickImages[i] = loadImage("Kick" + (i + 1) + ".png");
      kickImages[i].resize(150, 150);
    }

    chopImages = new PImage[8];
    for (int i = 0; i < chopImages.length; i++) {
      chopImages[i] = loadImage("Chop" + (i + 1) + ".png");
      chopImages[i].resize(150, 150);
    }

    frameRate(60);
  }

  public void draw() {
    background(imgBackground);
    
    if (isKicking) {
      animateKick();
    } else if (isChopping) {
      animateChop();
    } else {
      image(imgPlatypus, 550, 550);
    }
  }

  private void animateKick() {
    image(kickImages[kickImageIndex], 550, 550);
    if (frameCount % (60 / animationFrameRate) == 0) {
      kickImageIndex = (kickImageIndex + 1) % kickImages.length;
    }
  }

  private void animateChop() {
    image(chopImages[chopImageIndex], 550, 550);
    if (frameCount % (60 / animationFrameRate) == 0) {
      chopImageIndex = (chopImageIndex + 1) % chopImages.length;
    }
  }

  public void keyPressed() {
    if (key == 'a' && !isChopping) {
      isKicking = true;
    }
    if (key == 's' && !isKicking) {
      isChopping = true;
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
  }

  public static void main(String[] args) {
    PApplet.main("Sketch1");
  }
}
