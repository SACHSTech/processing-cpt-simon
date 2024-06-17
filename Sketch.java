import processing.core.PApplet;
import processing.core.PImage;
import java.util.Random;

public class Sketch extends PApplet {

// Initialize and declaring variables
  Random myRandom = new Random();

  PImage imgBackground;
  PImage imgPlatypus;
  PImage imgPlatypusR;
  PImage imgDoc;
  PImage imgNorm;
  PImage imgHome;
  PImage imgWinningBackground;

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

  PImage imgLab;
  PImage imgSister;
  PImage imgSuzie;

  PImage[] walkSister = new PImage[5];
  PImage[] punchSister = new PImage[6];
  PImage[] walkSisterR = new PImage[5];
  PImage[] punchSisterR = new PImage[6];

  PImage[] walkSuzie = new PImage[5];
  PImage[] kickSuzie = new PImage[7];
  PImage[] walkSuzieR = new PImage[5];
  PImage[] kickSuzieR = new PImage[7];

  int numSister = myRandom.nextInt(30, 40);
  int numSisterR = myRandom.nextInt(30, 40);
  int[] SisterX = new int[numSister];
  int[] SisterXR = new int[numSisterR];
  int SisterY = intBarryY;
  int SisterYR = intBarryY - 35;
  int[] SisterWalkIndex = new int[numSister];
  int[] SisterWalkIndexR = new int[numSisterR];
  int SisterSpeedR = -1;
  int SisterSpeed = 1;
  boolean[] enemyVisibleSister = new boolean[numSister];
  boolean[] isPunchingS = new boolean[numSister];
  boolean[] enemyVisibleSisterR = new boolean[numSisterR];
  boolean[] isPunchingSR = new boolean[numSisterR];

  int numSuzie = myRandom.nextInt(25, 35);
  int numSuzieR = myRandom.nextInt(25, 35);
  int[] SuzieX = new int[numSuzie];
  int[] SuzieXR = new int[numSuzieR];
  int SuzieY = intBarryY + 25;
  int SuzieYR = intBarryY + 50;
  int[] SuzieWalkIndex = new int[numSuzie];
  int[] SuzieWalkIndexR = new int[numSuzieR];
  int SuzieSpeed = 3;
  int SuzieSpeedR = -3;
  boolean[] enemyVisibleSuzie = new boolean[numSuzie];
  boolean[] enemyVisibleSuzieR = new boolean[numSuzieR];
  boolean[] isKickingS = new boolean[numSuzie];
  boolean[] isKickingSR = new boolean[numSuzieR];

  boolean isInContactSister = false;
  boolean isInContactSisterR = false;
  boolean isInContactSuzie = false;
  boolean isInContactSuzieR = false;
  int[] contactCounterSister = new int[numSister];
  int[] contactCounterSisterR = new int[numSisterR];
  int[] contactCounterSuzie = new int[numSuzie];
  int[] contactCounterSuzieR = new int[numSuzieR];
 
  boolean lastMove = false; 
  boolean showHomeScreen = true;
  boolean redoGame = false;
  boolean onDeath = false;
  boolean onDeath2 = false;
  boolean isOld = false;
  boolean onWin = false;
  boolean onWin2 = false;
  boolean nextLevel = false;
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
  }

    private void setup2() {
        imgLab = loadImage("Lab.jpg");
        imgLab.resize(width, height);

        imgWinningBackground = loadImage("Winning background.png");
        imgWinningBackground.resize(width, height);

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
        ImageArrays(walkSister, "SisterWalk", 160);
        ImageArrays(punchSister, "SisterPunch", 160);
        ImageArrays(walkSisterR, "SisterWalkR", 225);
        ImageArrays(punchSisterR, "SisterPunchR", 225);
        ImageArrays(walkSuzie, "SuzieWalkR", 160);
        ImageArrays(kickSuzie, "SuzieKickR", 160);
        ImageArrays(walkSuzieR, "SuzieWalk", 100);
        ImageArrays(kickSuzieR, "SuzieKick", 100);
        
        // Collision and positioning of the characters
        EnemyDetails(SisterX, false, SisterWalkIndex, enemyVisibleSister, isPunchingS, contactCounterSister);        
        EnemyDetails(SisterXR, true, SisterWalkIndexR, enemyVisibleSisterR, isPunchingSR, contactCounterSisterR);
        EnemyDetails(SuzieX, true, SuzieWalkIndex, enemyVisibleSuzie, isKickingS, contactCounterSuzie);
        EnemyDetails(SuzieXR,false, SuzieWalkIndexR, enemyVisibleSuzieR, isKickingSR, contactCounterSuzieR);

        // Life System details
        barryLives = 5;
        isInvincible = false;
        invincibilityCounter = 0;

    }

  /**
   * Helps distingush home screen and the actual game
   */
  public void draw() {
      if (showHomeScreen) {
        displayHomeScreen();
      } 
      else {
        runGame();
      }
      if (redoGame) {
        runGame();
        variableStorage();
        setup();
      }
      if (nextLevel) {
        variableStorage2();
        setup2();
        runGame2();
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
              if (dist(intBarryX, intBarryY, DocX[i], DocY) < 58) {
                  isPunching[i] = true;
                  BarryLives(true, contactCounterDoc);
              } else {
                  isPunching[i] = false;
                  DocX[i] += docSpeedR;
              }
              // When this boolean array is true, Doc will start punching Barry
              if (isPunching[i]) {
                  animateEnemyAttacks(i, enemyVisibleDoc, punchDoc, docWalkIndex, DocX, DocY);
              } else {
                  animateEnemyWalks(i, docWalkIndex, walkDoc, DocX, DocY);
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
              if (dist(intBarryX, intBarryY, DocXR[i], DocYR) < 58) {
                  isPunchingR[i] = true;
                    BarryLives(true, contactCounterDocR);
              } else {
                  isPunchingR[i] = false;
                  DocXR[i] += docSpeed;
              }

              // When this boolean array is true, DocR will start punching Barry
              if (isPunchingR[i]) {
                  animateEnemyAttacks(i, enemyVisibleDocR, punchDocR, docWalkIndexR, DocXR, DocY);
              } else {
                  animateEnemyWalks(i, docWalkIndexR, walkDocR, DocXR, DocYR);
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
                  animateEnemyAttacks(i, enemyVisibleNorm, biteNorm, normWalkIndex, NormX, NormY);
              } else {
                  animateEnemyWalks(i, normWalkIndex, walkNorm, NormX, NormY);
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
                  animateEnemyAttacks(i, enemyVisibleNormR, biteNormR, normWalkIndexR, NormXR, NormYR);
              } else {
                  animateEnemyWalks(i, normWalkIndexR, walkNormR, NormXR, NormYR);
              }
              // Checks whether or not Barry loses a life due to a NormR enemy or not
              BarryLives(isInContactNormR, contactCounterNormR);

              // Check for collision with Barry's kick
              if (isChopping && checkCollision(intBarryX, intBarryY, NormXR[i] - 50, NormYR) && !lastMove) {
                  enemyVisibleNormR[i] = false;
              }
          }
      } 
      killBarryPage();
      if (enemyDetector(DocX, DocXR, NormX, NormXR, enemyVisibleDoc, enemyVisibleDocR, enemyVisibleNorm, enemyVisibleNormR)) {
        winPage();
      }
}

public void runGame2() {
  // Adding an image of a background
  background(imgLab);
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
              isPunchingS[i] = true;
              BarryLives(true, contactCounterSister);
          } else {
              isPunchingS[i] = false;
              SisterX[i] += SisterSpeedR;
          }
          // When this boolean array is true, Sister will start punching Barry
          if (isPunchingS[i]) {
              animateEnemyAttacks(i, enemyVisibleSister, punchSister, SisterWalkIndex, SisterX, SisterY);
          } else {
              animateEnemyWalks(i, SisterWalkIndex, walkSister, SisterX, SisterY);
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
              isPunchingSR[i] = true;
               BarryLives(true, contactCounterSisterR);
          } else {
              isPunchingSR[i] = false;
              SisterXR[i] += SisterSpeed;
          }

          // When this boolean array is true, SisterR will start punching Barry
          if (isPunchingSR[i]) {
            animateEnemyAttacks(i, enemyVisibleSisterR, punchSisterR, SisterWalkIndexR, SisterXR, SisterYR);
          } else {
              animateEnemyWalks(i, SisterWalkIndexR, walkSisterR, SisterXR, SisterYR);
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
              isKickingS[i] = true;
              BarryLives(true, contactCounterSuzie);
          } else {
              isKickingS[i] = false;
              SuzieX[i] += SuzieSpeed;
          }
          
          // When this boolean array is true, Suzie will start punching Barry
          if (isKickingS[i]) {
              animateEnemyAttacks(i, enemyVisibleSuzie, kickSuzie, SuzieWalkIndex, SuzieX, SuzieY);
          } else {
              animateEnemyWalks(i, SuzieWalkIndex, walkSuzie, SuzieX, SuzieY);
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
              isKickingSR[i] = true;
              BarryLives(true, contactCounterSuzieR);
          } else {
              isKickingSR[i] = false;
              SuzieXR[i] += SuzieSpeedR;
          }
          // When this boolean array is true, SuzieR will start punching Barry
          if (isKickingSR[i]) {
              animateEnemyAttacks(i, enemyVisibleSuzieR, kickSuzieR, SuzieWalkIndexR, SuzieXR, SuzieYR);
          } else {
              animateEnemyWalks(i, SuzieWalkIndexR, walkSuzieR, SuzieXR, SuzieYR);
          }
          // Checks whether or not Barry loses a life due to a SuzieR enemy or not
         BarryLives(isInContactSuzieR, contactCounterSuzieR);

          // Check for collision with Barry's kick
          if (isChopping && checkCollision(intBarryX, intBarryY, SuzieXR[i] - 50, SuzieYR) && !lastMove) {
              enemyVisibleSuzieR[i] = false;
          }
      }
  } 
  killBarryPage();

  if (enemyDetector(SisterX, SisterXR, SuzieX, SuzieXR, enemyVisibleSister, enemyVisibleSisterR, enemyVisibleSuzie, enemyVisibleSuzieR)) {
    winningBackground();
  }
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
   * A method named Enemy Details that takes in a number of variables and arrays and sets their value and the positions of enemies
   * @param intEnemies The number of a specific type of enemy
   * @param leftOrRight Determining whether or not the enemy should be positioned from the left of right side
   * @param intIndex  The animation array for the type of enemy , Sets all the animation to the first frame  
   * @param isVisible Makes all the enemies visible on screen
   * @param isAttacking Sets the enemies attacking condition to false so they walk up to the enemy first
   * @param intContact  The array for the contact of enemies. Setting it zero ensures it starts from the first frame of animation
   */
  private void EnemyDetails(int[] intEnemies, boolean leftOrRight, int[] intIndex, boolean[] isVisible, boolean[] isAttacking, int[] intContact){
      for (int i = 0; i < intEnemies.length; i++) {
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
      }
  }
  /**
   * animateKick is a method that contains the animations for Barry's Kick
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
   * animateChop is a method the contains the animations for Barry's chop
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
   * animateWalk is a method that contains all the animations for Barry's movement
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
    /**
     * A method animateEnemyWalks that sets the movement animations for different enemies
     * @param i i refers to the specific enemy out the many
     * @param enemyWalkIndex Keeps track of the frame of the specific enemy
     * @param walkEnemy walkEnemy refers to the image frames of the enemy's movement
     * @param enemyX X coordinate of enemy
     * @param enemyY Y coordinate of enemy
     */
    private void animateEnemyWalks(int i, int[] enemyWalkIndex, PImage[] walkEnemy, int[] enemyX, int enemyY) {
        int index = enemyWalkIndex[i] % walkEnemy.length;
        image(walkEnemy[index], enemyX[i], enemyY);
        if (frameCount % (60 / animationFrameRate) == 0) {
            enemyWalkIndex[i] = (enemyWalkIndex[i] + 1) % walkEnemy.length;
        }
    }
    /**
     * A method animate EnemyWalks that sets the attacking animations for different enemies
     * @param i  i refers to the specific enemy out the many
     * @param enemyVisible Keeps track of the frame of the specific enemy
     * @param attackEnemy Refers to the image frames of the enemy's attacks
     * @param enemyWalkIndex Keeps track of the frame of the specific enemy
     * @param enemyX X coordinate of enemy
     * @param enemyY Y coordinate of enemy
     */
    private void animateEnemyAttacks(int i, boolean[] enemyVisible, PImage[] attackEnemy, int[] enemyWalkIndex, int[] enemyX, int enemyY) {
        if (enemyVisible[i]) {
            image(attackEnemy[enemyWalkIndex[i]], enemyX[i], enemyY);
            if (frameCount % (60 / animationFrameRate) == 0) {
                enemyWalkIndex[i] = (enemyWalkIndex[i] + 1) % attackEnemy.length;
            }
        }
    }

    /**
     * A method enemyDectector that checks whether all enemies are defeated or not
     * @param enemyX1 One type of enemy on the x - axis 
     * @param enemyX2 Another type of enemy on the x - axis
     * @param enemyX3 Another type of enemy on the x - axis
     * @param enemyX4 Another type of enemy on the x - axis
     * @param enemyVisible1 A boolean variable that returns whether or not a specific type of enemy is defeat or not
     * @param enemyVisible2 A boolean variable that returns whether or not a specific type of enemy is defeat or not
     * @param enemyVisible3 A boolean variable that returns whether or not a specific type of enemy is defeat or not
     * @param enemyVisible4 A boolean variable that returns whether or not a specific type of enemy is defeat or not
     * @return Returns whether or not all enemies are defearted or not, true being all defeated, false being the opposite
     */
    private boolean enemyDetector(int[] enemyX1, int[] enemyX2, int[] enemyX3, int[] enemyX4, boolean[] enemyVisible1, boolean[] enemyVisible2, boolean[] enemyVisible3, boolean[] enemyVisible4) {
        boolean allEnemiesDead = true;

        for (int i = 0; i < enemyX1.length; i++) {
            if (enemyVisible1[i]) {
                allEnemiesDead = false;
                break;
            }
        }
        if (allEnemiesDead) {
            for (int i = 0; i < enemyX2.length; i++) {
                if (enemyVisible2[i]) {
                    allEnemiesDead = false;
                    break;
                }
            }
        }

        if (allEnemiesDead) {
            for (int i = 0; i < enemyX3.length; i++) {
                if (enemyVisible3[i]) {
                    allEnemiesDead = false;
                    break;
                }
            }
        }

        if (allEnemiesDead) {
            for (int i = 0; i < enemyX4.length; i++) {
                if (enemyVisible4[i]) {
                    allEnemiesDead = false;
                    break;
                }
            }
        }
        return allEnemiesDead;
    }
    /**
     * A method that holds all the variables for level 1
     */
    public void variableStorage() {
        isKicking = false;
        isChopping = false;
        isWalking = false;
        movingLeft = false;
        movingRight = false;

        intBarryX = 550;
        intBarryY = 550;
        intBarrySpeed = 5;

        kickImageIndex = 0;
        chopImageIndex = 0;
        walkImageIndex = 0;
        kickImageIndexR = 0;
        chopImageIndexR = 0;
        walkImageIndexR = 0;
        animationFrameRate = 20;
        kickDelay = 0;

        numDoc = myRandom.nextInt(20, 30);
        numDocR = myRandom.nextInt(20, 30);
        DocX = new int[numDoc];
        DocXR = new int[numDocR];
        DocY = intBarryY;
        DocYR = intBarryY;
        docWalkIndex = new int[numDoc];
        docWalkIndexR = new int[numDocR];
        enemyVisibleDoc = new boolean[numDoc];
        isPunching = new boolean[numDoc];
        enemyVisibleDocR = new boolean[numDocR];
        isPunchingR = new boolean[numDocR];

        numNorm = myRandom.nextInt(15, 25);
        numNormR = myRandom.nextInt(15, 25);
        NormX = new int[numNorm];
        NormXR = new int[numNormR];
        NormY = intBarryY + 50;
        NormYR = intBarryY + 50;
        normWalkIndex = new int[numNorm];
        normWalkIndexR = new int[numNormR];
        enemyVisibleNorm = new boolean[numNorm];
        enemyVisibleNormR = new boolean[numNormR];
        isBiting = new boolean[numNorm];
        isBitingR = new boolean[numNormR];

        barryLives = 5;
        isInvincible = false;
        invincibilityDuration = 120;
        invincibilityCounter = 0;

        isInContactDoc = false;
        isInContactDocR = false;
        isInContactNorm = false;
        isInContactNormR = false;
        contactCounterDoc = new int[numDoc];
        contactCounterDocR = new int[numDocR];
        contactCounterNorm = new int[numNorm];
        contactCounterNormR = new int[numNormR];
        contactDuration = 24;

        // Games
        lastMove = false; 
        redoGame = false;
    }
    /**
     * A method variableStorage2 that holds all the variables for level 2
     */
    public void variableStorage2() {
        isKicking = false;
        isChopping = false;
        isWalking = false;
        movingLeft = false;
        movingRight = false;

        intBarryX = 550;
        intBarryY = 550;
        intBarrySpeed = 5;

        kickImageIndex = 0;
        chopImageIndex = 0;
        walkImageIndex = 0;
        kickImageIndexR = 0;
        chopImageIndexR = 0;
        walkImageIndexR = 0;
        animationFrameRate = 20;
        kickDelay = 0;

        numSister = myRandom.nextInt(30, 40);
        numSisterR = myRandom.nextInt(30, 40);
        SisterX = new int[numSister];
        SisterXR = new int[numSisterR];
        SisterY = intBarryY;
        SisterYR = intBarryY - 35;
        SisterWalkIndex = new int[numSister];
        SisterWalkIndexR = new int[numSisterR];
        SisterSpeedR = -1;
        SisterSpeed = 1;
        enemyVisibleSister = new boolean[numSister];
        isPunchingS = new boolean[numSister];
        enemyVisibleSisterR = new boolean[numSisterR];
        isPunchingSR = new boolean[numSisterR];

        numSuzie = myRandom.nextInt(25, 35);
        numSuzieR = myRandom.nextInt(25, 35);
        SuzieX = new int[numSuzie];
        SuzieXR = new int[numSuzieR];
        SuzieY = intBarryY + 25;
        SuzieYR = intBarryY + 50;
        SuzieWalkIndex = new int[numSuzie];
        SuzieWalkIndexR = new int[numSuzieR];
        SuzieSpeed = 3;
        SuzieSpeedR = -3;
        enemyVisibleSuzie = new boolean[numSuzie];
        enemyVisibleSuzieR = new boolean[numSuzieR];
        isKickingS = new boolean[numSuzie];
        isKickingSR = new boolean[numSuzieR];

        barryLives = 5;
        isInvincible = false;
        invincibilityDuration = 120;
        invincibilityCounter = 0;

        isInContactSister = false;
        isInContactSisterR = false;
        isInContactSuzie = false;
        isInContactSuzieR = false;
        contactCounterSister = new int[numSister];
        contactCounterSisterR = new int[numSisterR];
        contactCounterSuzie = new int[numSuzie];
        contactCounterSuzieR = new int[numSuzieR];
        lastMove = false; 
    }

  /** 
   *  A method loadScreen that displays a load screen
   */
  public void loadScreen(){
      for(int i = 0; i <= 2; i++){
          background(0);
          image(imgPlatypusR, 225, 375);
          fill(255);
          textSize(64);
          text("Loading", 375, 250);
          delay(2000);
      }
  }
  /**
   * A method winPage that shows a win page when users beat the first level
   */
  public void winPage() {
      onWin = true;
      onDeath = false;
      background(imgBackground);
      textSize(75);
      fill(255);
      text("Barry Survived!!!", 375, 250);
      rectMode(CORNER);
      fill(249, 255, 207);
      rect (100, 360, 150, 50);
      fill(0);
      textSize(36);
      text("Next Level", 175, 385);
      fill(249, 255, 207);
      rect (450, 360, 150, 50);
      fill(0);
      textSize(30);
      text("Restart", 525, 385);
      fill(249, 255, 207);
      rect (300, 450, 150, 50);
      fill(0);
      textSize(30);
      text("Main Menu", 375, 475);
          numDoc = 0;
          numDocR = 0;
          numNorm = 0;
          numNorm = 0;
  }
/**
 *  A method WinningBackground when the users beat the second level
 */
  public void winningBackground(){
    onWin = true;
    onDeath = false;
    background(imgWinningBackground);
    textSize(75);
    fill(255);
    text("Congraulations,You help Barry Escape!!!", 375, 250);
    fill(249, 255, 207);
    rect (300, 450, 150, 50);
    fill(0);
    textSize(30);
    text("Main Menu", 375, 475);
  }

  /**
   * A method called killBarryPage that displays a page when Barry runs out of lives
   */
  public void killBarryPage() {
      onDeath = true;
      if (barryLives <= 0) {
          background(imgBackground);
          image(imgPlatypus, 250, 550);
          textSize(48);
          textAlign(CENTER, CENTER);
          text("YOU KILLED BARRY", width / 2, height / 2 - 40);
          rectMode(CORNER);
          fill(249, 255, 207);
          rect (100, 360, 150, 50);
          fill(0);
          textSize(36);
          text("Restart", 175, 385);
          fill(249, 255, 207);
          rect (450, 360, 150, 50);
          fill(0);
          textSize(30);
          text("Main Menu", 525, 385);
          numDoc = 0;
          numDocR = 0;
          numNorm = 0;
          numNorm = 0;
          
      }
  }
  /**
   * BarryLives is a method that checks the collision with enemies and determines whether or not Barry should lose a life or not after a certain time
   * @param isInContactEnemy A boolean variable that checks whether or not Barry is in collision with an enemy
   * @param contactCounter A buffer timmer before Barry loses a life
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
   * A method checkCollision that takes parameters and sets the bounds of collision
   * @param barryX Barry's x coordinate
   * @param barryY Barry's y coordinate
   * @param enemyX Enemies' x coordinate
   * @param enemyY Enemies' y coordinate
   * @return Returns true if the enemies overlap with Barry, false if not
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
   * A method keyPressed that does actions when the user does inputs on their keyboard
   */
  public void keyPressed() {
        // Barry Kicking
      if (key == 'a' && !isChopping) {
          isKicking = true;
        // Barry Chopping
      } else if (key == 's' && !isKicking && kickDelay == 0) {
          isChopping = true;
        // Barry Walking left
      } else if (keyCode == LEFT) {
          movingLeft = true;
          lastMove = true;
          isWalking = true;
        // Barry Walking right
      } else if (keyCode == RIGHT) {
          movingRight = true;
          lastMove = false;
          isWalking = true;
      }
  }
  /**
   * KeyReleased is a method that does actions when the user releases certain keys.
   */
  public void keyReleased() {
    // Barry stops kicking
      if (key == 'a') {
          isKicking = false;
          kickImageIndex = 0;
      }
      // Barry stops Chopping
      if (key == 's') {
          isChopping = false;
          chopImageIndex = 0;
      }
      // Barry stops moving
      if (keyCode == LEFT || keyCode == RIGHT) {
          movingLeft = false;
          movingRight = false;
          isWalking = false;
          walkImageIndex = 0;
      }
  }
  /**
   * A method mouseClicked that does certain actions when the user clicks their mouse
   */
  public void mouseClicked(){
    // Button for homepage
    if(mouseX > 50 && mouseX < 250 && mouseY > 550 && mouseY < 650) {
        showHomeScreen = false;
        barryLives = 5;
        if (isOld){
            isOld = false;
            redoGame = true;
        }
    }
    // Buttons for deathPage on level 1
    if(onDeath) {
      if(barryLives <= 0 && mouseX > 100 && mouseX < 250 && mouseY > 360 && mouseY < 410) {
        loadScreen();
        redoGame = true;
        numDoc = 0;
        numDocR = 0;
        numNorm = 0;
        numNormR = 0;
        barryLives = 5;
      }

      else if(mouseX > 450 && mouseX < 600 && mouseY > 360 && mouseY < 410) {
        loadScreen();
        showHomeScreen = true;
        isOld = true;
        numDoc = 0;
        numDocR = 0;
        numNorm = 0;
        numNormR = 0;
        barryLives = 5;
      }
    }
    // Buttons for winPage on level 1
    if(onWin){
      if(mouseX > 100 && mouseX < 250 && mouseY > 360 && mouseY < 410) {
        loadScreen();
        nextLevel = true;
      }
      else if (mouseX > 450 && mouseX < 600 && mouseY > 360 && mouseY < 410) {
        loadScreen();
        redoGame = true;
        numDoc = 0;
        numDocR = 0;
        numNorm = 0;
        numNormR = 0;
        barryLives = 5;
      }
      else if (mouseX > 300 && mouseX < 450 && mouseY > 450 && mouseY < 500) {
        loadScreen();
        showHomeScreen = true;
        isOld = true;
        numDoc = 0;
        numDocR = 0;
        numNorm = 0;
        numNormR = 0;
        barryLives = 5;
      }
    }
  }
}