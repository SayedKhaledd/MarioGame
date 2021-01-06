package Texture;

import javax.media.opengl.GL;

/**
 * @author MohamedMashhor
 */
public class Bear {

    public static int numberOfTurtles = 0;
    public static int numberOfCoinsGathered = 0;
    public static boolean isMulti = false;
    private boolean died = false;
    private boolean crashed = false;
    private boolean jumping = true;
    static boolean cantMoveRight = false;
    static boolean cantMoveLeft = false;
    boolean moving;
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
    private int animationIndex = 0;
    private float scaleFactor = 1f;
    boolean isLeveledUp = false;


    public int coinsGatherd = 0;

    public Bear() {
    }

    public Bear(boolean moving, boolean movingRight, boolean movingUp, float x, float y, float minX, float minY, float maxX, float maxY, float xSpeed, float ySpeed) {
        this.moving = moving;
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
        this.died = died;
        if (died) {
            MarioGameGLEventListener.backgroundSound.clip.stop();
            SoundEffects characterDied = new SoundEffects();
            characterDied.playSound(characterDied.DIED, 0);
            setCrashed(true);
            setMoving(false);
        }

    }

    public boolean isCrashed() {
        return crashed;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getCoinsGatherd() {
        return coinsGatherd;
    }

    public void setCoinsGatherd(int coinsGatherd) {
        this.coinsGatherd = coinsGatherd;
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

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
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

    public float getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    float currentMaxJump = minY + 250;

    public void move() {
        Turtles.isThereATurtleCrashed(x, y);
        if (Turtles.isBearDied(x, y)) {
            setDied(true);
        }
        //System.out.println("y : " + y);
        //System.out.println("x : " + x);

        moveHorizontal();//  method to handle move H
        try {// check if there a Horizontal block
            Blocks currBlock = Blocks.isThereABlock(x, y);
            float tmpMinY = currBlock.getToY() + 280;
            float tmpMaxY = currBlock.getFromY() + 300;
            // handle jumping
            if (y >= tmpMinY) { // if block under bear
                if (currBlock.isThereCoin()) {//if ther a coin on block gather it and remove it from block
                    currBlock.setThereCoin(false);
                    SoundEffects coinCollected = new SoundEffects();
                    coinCollected.playSound(coinCollected.COLLECTCOIN, 0);
                    numberOfCoinsGathered++;
                }
                if (jumping) {// jump when is set on block
                    if (movingUp) {
                        if (y < Math.min(600, currentMaxJump)) {
                            y += ySpeed;
                            movingUp = true;
                        } else {
                            movingUp = false;
                        }
                    }
                }
                if (!movingUp) {
                    if (y >= tmpMinY + ySpeed) {// set ground to be the top of block
                        y -= ySpeed;
                        jumping = true;
                    } else {
                        jumping = false;
                        currentMaxJump = y + 250;// set max jump to the top of block + 200
                    }
                }
            } else if (y < tmpMinY) { // if block up bear
                if (jumping) {
                    if (movingUp) {
                        if (y < tmpMaxY - 100) {// set max jump to the min y of the block
                            y += ySpeed;
                            movingUp = true;
                        } else {
                            movingUp = false;
                        }
                    }
                }
                if (!movingUp) {
                    if (y > minY) {// return to the ground
                        y -= ySpeed;
                        jumping = true;
                    } else {
                        jumping = false;
                    }
                }
            }
        } catch (NullPointerException n) {// if there is not a horiaontal block
            float tmpMinY = VerticalBlocks.isThereAVerticalBlockUnder(x, y);
            if (tmpMinY != -888) {// if there under vertical block
                if (jumping) {
                    if (movingUp) {
                        if (y < currentMaxJump) {
                            y += ySpeed;
                            movingUp = true;
                        } else {
                            movingUp = false;
                        }
                    }
                }
                if (!movingUp) {
                    if (y > tmpMinY) {// jump to it
                        y -= ySpeed;
                        jumping = true;
                    } else {
                        jumping = false;
                        currentMaxJump = y + 250;// set max jump to the top of v block +200
                    }
                }
            } else {// if there not a V block or H block
                if (jumping) {
                    if (movingUp) {
                        if (y < currentMaxJump) {// jump to curr max 
                            y += ySpeed;
                            movingUp = true;
                        } else {
                            movingUp = false;
                        }
                    }
                }
                if (!movingUp) {
                    if (y > minY) {
                        y -= ySpeed;
                        jumping = true;
                    } else {
                        jumping = false;
                        currentMaxJump = y + 250; // set max jump to ground level + 200
                    }
                }
            }
        }
    }

    public void moveHorizontal() {
        try {// check if there a V block Right of bear then set  cantMoveRight = true
            VerticalBlocks verticalBlockRight = VerticalBlocks.isThereAVerticalBlockRight(x + xSpeed, y);
            verticalBlockRight.getFromX();
            cantMoveRight = true;
        } catch (NullPointerException n) {

        }
        try {// check if there a V block Left of bear then set   cantMoveLeft = true
            VerticalBlocks verticalBlockLeft = VerticalBlocks.isThereAVerticalBlockLeft(x - xSpeed, y);
            verticalBlockLeft.getFromX();
            cantMoveLeft = true;
        } catch (NullPointerException n) {

        }

        if (!crashed && moving) {// if not just move as normal
            if (movingRight) {
                if (x < 1500f) {
                    x += xSpeed;
                } else {
                    if (x < 1700f) {
                        x += xSpeed;
                    }
                }
            }
            if (!movingRight) {
                if (x > minX) {
                    x -= xSpeed;
                } else {
                }

            }

            animationIndex++;
        }
    }

    public void jump() {
        if (!jumping) {
            SoundEffects jumpingSE = new SoundEffects();
            jumpingSE.playSound(jumpingSE.JUMP, 0);
            jumping = true;
            movingUp = true;
        }

    }

    public void draw(GL gl) {
        if (!died) {
            gl.glEnable(GL.GL_BLEND);
            if (!moving) {
                if (!isMulti || MarioGameGLEventListener.currentPlayer == 1)
                    gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies[9]);
                else {

                    gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies2[5]);


                }

            } else if (isLeveledUp) {

                if (!isMulti || MarioGameGLEventListener.currentPlayer == 1)
                    gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies[9]);
                else {

                    gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies2[5]);


                }


            } else {
                if (!isMulti || MarioGameGLEventListener.currentPlayer == 1)
                    gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies[(animationIndex % 5) + 4]);
                else {
                    gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies2[(animationIndex % 5)]);

                }
            }
            gl.glPushMatrix();
            if (x <= maxX) {
                gl.glTranslatef(x, y, 0);
            } else {
                gl.glTranslatef(0, y, 0);

            }
            gl.glScalef(movingRight ? scaleFactor : -scaleFactor, scaleFactor, 1);
            gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-50.0f, -305.0f, -1.0f);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(15.0f, -305.0f, -1.0f);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(15.0f, -205.0f, -1.0f);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-50.0f, -205.0f, -1.0f);
            gl.glEnd();
            gl.glPopMatrix();
            gl.glDisable(GL.GL_BLEND);

        }
        if (MarioGameGLEventListener.multiGameEnd) {
            if (MarioGameGLEventListener.bear2Coins > MarioGameGLEventListener.bear.coinsGatherd) {
                MarioGameGLEventListener.drawCurrentPlayerFlag(gl, 9);
            } else if (MarioGameGLEventListener.bear2Coins < MarioGameGLEventListener.bear.coinsGatherd) {
                MarioGameGLEventListener.drawCurrentPlayerFlag(gl, 8);

            } else MarioGameGLEventListener.drawCurrentPlayerFlag(gl, 10);

            MarioGameGLEventListener.paused=true;

        }
    }

    public void levelUp() {
        MarioGameGLEventListener.backgroundSound.clip.stop();
        SoundEffects levelUp = new SoundEffects();
        levelUp.playSound(levelUp.LEVELUP, 0);
        setCrashed(true);

        if (!isMulti) {

            isLeveledUp = true;
        } else {
            if (MarioGameGLEventListener.currentPlayer == 2) {
                MarioGameGLEventListener.multiGameEnd = true;
                MarioGameGLEventListener.bear2Coins = Bear.numberOfCoinsGathered;
                System.out.println(MarioGameGLEventListener.bear2Coins);


            } else {

                MarioGameGLEventListener.bear.coinsGatherd = Bear.numberOfCoinsGathered;
                System.out.println(MarioGameGLEventListener.bear.coinsGatherd);
                MarioGameGLEventListener.currentPlayer++;
                Levels.resetLevel();
                Levels.levels(2);
            }
        }

    }

}
