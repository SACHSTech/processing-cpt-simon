import processing.core.PApplet;
import processing.core.PImage;

public class Sketch1 extends PApplet {
  PImage imgBackground;
  PImage imgPlatypus;
  PImage[] kickImages;

  boolean isKicking = false;
  int currentImageIndex = 0;
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

    frameRate(60);
  }

  public void draw() {
    background(imgBackground);
    
    if (isKicking) {
      animateKick();
    } else {
      image(imgPlatypus, 550, 550);
    }
  }

  private void animateKick() {
    image(kickImages[currentImageIndex], 550, 550);
    if (frameCount % (60 / animationFrameRate) == 0) { // Change frame at the desired rate
      currentImageIndex = (currentImageIndex + 1) % kickImages.length;
    }
  }

  public void keyPressed() {
    if (key == 'a') {
      isKicking = true;
    }
  }

  public void keyReleased() {
    if (key == 'a') {
      isKicking = false;
      currentImageIndex = 0; 
    }
  }

  public static void main(String[] args) {
    PApplet.main("Sketch1");
  }
}
