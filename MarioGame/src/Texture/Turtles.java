package Texture;

import java.util.ArrayList;
import javax.media.opengl.GL;

/**
 *
 * @author MohamedMashhor
 */
public class Turtles {

    static ArrayList<Turtles> turtles = new ArrayList<>();

    public static int numberOfTurtles = 0;
    private boolean died = false;
    private boolean crashed = false;
    private boolean jumping = false;
    boolean movingRight;
    boolean movingUp;
    private float x;
    private float y;
    private float minX;
    private float minY;
    private float maxX;
    private float maxY;
    private float xSpeed;
    private float ySpeed;
    private int animationIndex = 1;

    public Turtles(boolean movingRight, boolean movingUp, float x, float y, float minX, float minY, float maxX, float maxY, float xSpeed, float ySpeed) {
        this.movingRight = movingRight;
        this.movingUp = movingUp;
        this.jumping = movingUp;
        this.x = x;
        this.y = y;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public boolean isDied() {
        return died;
    }

    public void setDied(boolean died) {
        numberOfTurtles--;
        this.died = died;
        if (died) {
            SoundEffects turtleDied = new SoundEffects();
            turtleDied.playSound(turtleDied.TURTLEDIED, 0);
        }
    }

    public boolean isCrashed() {
        return crashed;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
        this.jumping = movingUp;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public float getMinX() {
        return minX;
    }

    public void setMinX(float minX) {
        this.minX = minX;
    }

    public float getMinY() {
        return minY;
    }

    public void setMinY(float minY) {
        this.minY = minY;
    }

    public float getMaxX() {
        return maxX;
    }

    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    public float getMaxY() {
        return maxY;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public void move() {
        if (!MarioGameGLEventListener.paused) {
            if (!jumping && !crashed) {
                if (movingRight) {
                    if (x < maxX) {
                        x += xSpeed;
                        movingRight = true;
                        animationIndex++;
                    } else {
                        movingRight = false;
                    }
                }
                if (!movingRight) {
                    if (x > minX) {
                        x -= xSpeed;
                        movingRight = false;
                        animationIndex++;
                    } else {
                        movingRight = true;
                    }
                }
            }

            if (movingUp) {
                if (y < maxY) {
                    y += ySpeed;
                    movingUp = true;
                } else {
                    movingUp = false;
                }
            }

            if (!movingUp) {
                if (y > minY) {
                    y -= ySpeed;
                } else {
                    jumping = false;
                }
            }
        }
    }

    public void jumpAndMove() {
        jumping = false;
        movingUp = true;

    }

    public void jumpOnly() {
        jumping = true;
        movingUp = true;
    }

    public void crashed() {
        crashed = true;
        animationIndex = 3;
    }

    public static void isThereATurtleCrashed(float currBearX, float currBearY) {
        if (!MarioGameGLEventListener.bear.isMovingUp()) {
            for (int i = 0; i < turtles.size(); i++) {
                if (currBearY == 125) {
                    if (currBearX > turtles.get(i).getX() && currBearX < turtles.get(i).getX() + 70) {
                        turtles.get(i).setDied(true);
                        turtles.remove(i);
                    }
                }
            }
        }
    }

    public static boolean isBearDied(float currBearX, float currBearY) {
        if (!MarioGameGLEventListener.bear.isJumping()) {
            for (int i = 0; i < turtles.size(); i++) {
                if (currBearY < 125) {
                    if (currBearX > turtles.get(i).getX() && currBearX < turtles.get(i).getX() + 70) {
                        if (!MarioGameGLEventListener.bear.isDied()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void draw(GL gl) {
        if (!died) {
            gl.glEnable(GL.GL_BLEND);
            if (animationIndex == 3) {
                gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies[3]);
            } else {
                gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies[animationIndex % 3]);
            }
            gl.glPushMatrix();
            gl.glTranslatef(x, y, 0);
            gl.glScalef(movingRight ? 1 : -1, 1, 1);
            gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-50.0f, -200.0f, -1.0f);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(20.0f, -200.0f, -1.0f);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(20.0f, -130.0f, -1.0f);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-50.0f, -130.0f, -1.0f);
            gl.glEnd();
            gl.glPopMatrix();
            gl.glDisable(GL.GL_BLEND);
        }
    }

}
