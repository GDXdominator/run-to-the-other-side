import processing.core.PApplet;
import processing.core.PImage;

public class Main extends PApplet {
    int score, scoreZoneHeight, heightToReach, bottomScoreZoneHeight;
    PImage vignette, vignette2;
    Boolean gameOver, left, right, up, down;
    int plrX, plrY, plrRad, plrSpeed;
    float enemyX, enemyY, enemySpeedX, enemySpeedY, speedInc;
    int enemyRad;
    public void settings() {
        size(600, 600);
    }

    public void setup() {
        // game
        score = 0;
        scoreZoneHeight = height/8;
        heightToReach = scoreZoneHeight; // top of screen
        bottomScoreZoneHeight = height - scoreZoneHeight;
        vignette = vignette2 = loadImage("vignette.png"); // there are 2 to make it darker
        gameOver = false;

        // plr
        left = right = up = down = false;
        plrX = width/2;
        plrY = height - scoreZoneHeight/2;
        plrRad = 25;
        plrSpeed = height/100;
        vignette.resize(width * plrRad/12, height * plrRad/12);
        vignette2.resize(width * plrRad/12, height * plrRad/12);

        // enemy
        enemyX = (float) width / 2;
        enemyY = (float) height / 2;
        enemyRad = 50;
        enemySpeedX = (float) width / 110;
        enemySpeedY = (float) height / 50;
        speedInc = .15f;
    }

    public void draw() {
        // map & score
        background(color(180));
        fill(100,180,100);
        rect(0,0, width, scoreZoneHeight);
        rect(0,bottomScoreZoneHeight, width, scoreZoneHeight);
        fill(0);
        textSize(14);
        text("Score: " + score, 25, 50);

        // plr
        fill(0,0,180);
        ellipse(plrX, plrY, plrRad, plrRad);
        if (!gameOver) {
            if (up && plrY - plrRad/2 > 0) {
                plrY -= plrSpeed;
            }
            if (down && plrY + plrRad/2 < height) {
                plrY += plrSpeed;
            }
            if (left && plrX - plrRad/2 > 0) {
                plrX -= plrSpeed;
            }
            if (right && plrX + plrRad/2 < width) {
                plrX += plrSpeed;
            }
        }
        if (heightToReach == scoreZoneHeight) {
            if (plrY - plrRad / 2 <= heightToReach) {
                addScore(bottomScoreZoneHeight);
            }
        } else {
            if (plrY + plrRad >= heightToReach) {
                addScore(scoreZoneHeight);
            }
        }

        // enemy
        fill(180,0,0);
        ellipse(enemyX, enemyY, enemyRad, enemyRad);
        if (!gameOver) {
            enemyX += enemySpeedX;
            enemyY -= enemySpeedY;
        }
        if (enemyX - enemyRad/2f <= 0) {
            enemySpeedX = -enemySpeedX;
            enemyX = enemyRad;
        }
        if (enemyX + enemyRad/2f >= width) {
            enemySpeedX = -enemySpeedX;
            enemyX = width - enemyRad;
        }
        if (enemyY - enemyRad/2f <= scoreZoneHeight) {
            enemyY = scoreZoneHeight + enemyRad;
            enemySpeedY = -enemySpeedY;
        }
        if (enemyY + enemyRad/2f >= bottomScoreZoneHeight) {
            enemyY = bottomScoreZoneHeight - enemyRad;
            enemySpeedY = -enemySpeedY;
        }

        if (score >= 10) {
            image(vignette, plrX - (float) vignette.width / 2, plrY - (float) vignette.height / 2);
        }
        if (score >= 20) {
            image(vignette2, plrX - (float) vignette2.width / 2, plrY - (float) vignette2.height / 2);
        }

        if (dis(plrX, plrY, (int) (enemyX), (int) (enemyY)) <= plrRad || gameOver) {
            fill(0);
            textSize(25);
            textAlign(CENTER);
            text("Game over", (float) width/2, (float) height/2);
            textSize(15);
            text("Press 'R' to restart", (float) width/2, (float) height/2 + 25);
            gameOver = true;
        }
    }

    public void keyPressed() {
        if (key == 'w') {up = true;}
        if (key == 's') {down = true;}
        if (key == 'a') {left = true;}
        if (key == 'd') {right = true;}
    }

    public void keyReleased() {
        if (key == 'w') {up = false;}
        if (key == 's') {down = false;}
        if (key == 'a') {left = false;}
        if (key == 'd') {right = false;}
        if (key == 'r') {
            setup();
        }
        if (key == 'q') {
            addScore(heightToReach);
        }
    }

    public void addScore(int newHeight) {
        score++;
        if (enemySpeedX > 0) {
            enemySpeedX = Math.abs(enemySpeedX) + speedInc;
        } else {
            enemySpeedX = -Math.abs(enemySpeedX) - speedInc;
        }
        if (enemySpeedY > 0) {
            enemySpeedY = Math.abs(enemySpeedY) + speedInc;
        } else {
            enemySpeedY = -Math.abs(enemySpeedY) - speedInc;
        }
        heightToReach = newHeight;
    }

    public static double dis(int x1, int y1, int x2, int y2) {
        int disX = x2 - x1;
        int disY = y2 - y1;
        return Math.sqrt(disX * disX + disY * disY);
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}