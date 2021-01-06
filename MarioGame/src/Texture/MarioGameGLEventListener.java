package Texture;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package project;

import Retriving.TextureReader;

import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;

import java.time.LocalTime;
import java.util.BitSet;
import javax.media.opengl.glu.GLU;

public class MarioGameGLEventListener extends AnimListener {

    int numberOfTurtles = 2;
    int animationIndex = 0;
    final float maxWidth = 1000;
    final float maxHeight = 650;
    final float borderSize = 100;
    float speed[] = new float[numberOfTurtles];
    float[] x = new float[numberOfTurtles];
    float[] y = new float[numberOfTurtles];

    static float scrollBackground;
    boolean isMovingRight[] = new boolean[numberOfTurtles];
    boolean isMovingUp[] = new boolean[numberOfTurtles];
    int frames = 0;
    public static int level = 0;
    public static int currentPlayer = 1;
    public static boolean multiGameEnd = false;

    //( boolean moving, boolean movingRight, boolean movingUp, float x, float y, float minX, float minY, float maxX, float maxY, float xSpeed, float ySpeed) 
    public static Bear bear = new Bear(false, true, false, -400f, 50f, -400f, +50, 0, 400, 10, 25);
    public static int bear2Coins = 0;
    public static String[] textureNames2
            = {"Bear2/01.png",
            "Bear2/02.png",
            "Bear2/03.png",
            "Bear2/04.png",
            "Bear2/05.png",
            "Bear2/06.png",
            "..\\..\\Backgrounds\\Player1.png",
            "..\\..\\Backgrounds\\Player2.png",
            "..\\..\\Backgrounds\\Player1Won.png",
            "..\\..\\Backgrounds\\Player2Won.png",
            "..\\..\\Backgrounds\\Draw.png"
    };
    public static String textureNames[]
            = {"Turtles/turtRM01.png",
            "Turtles/turtRM02.png",
            "Turtles/turtRM03.png",
            "Turtles/turtDiedR.png",
            "Bear/01.png",
            "Bear/02.png",
            "Bear/03.png",
            "Bear/04.png",
            "Bear/05.png",
            "Bear/06.png",

            "..\\..\\blocks\\oneBlock.png",
            "..\\..\\blocks\\coin.png",
            "..\\..\\numbers\\0.png",
            "..\\..\\numbers\\1.png",
            "..\\..\\numbers\\2.png",
            "..\\..\\numbers\\3.png",
            "..\\..\\numbers\\4.png",
            "..\\..\\numbers\\5.png",
            "..\\..\\numbers\\6.png",
            "..\\..\\numbers\\7.png",
            "..\\..\\numbers\\8.png",
            "..\\..\\numbers\\9.png",
            "..\\..\\blocks\\verticalBlock.png",

            "..\\..\\Backgrounds\\Start.png",
            "..\\..\\Backgrounds\\background.jpg",
            "..\\..\\Backgrounds\\how.png",
            "..\\..\\Backgrounds\\howtoplay.png",
            "..\\..\\Backgrounds\\levels.png",
            "..\\..\\Backgrounds\\multi.png",
            "..\\..\\Backgrounds\\resumeOrClose.png",
            "..\\..\\Backgrounds\\pause.png",
            "..\\..\\Backgrounds\\BackgroundLevel2.png",
            "..\\..\\Backgrounds\\levelUp.png",
            "..\\..\\Backgrounds\\finish.png",
            "..\\..\\Backgrounds\\gameOver.png",
            "..\\..\\Backgrounds\\startFlag.png",
            "..\\..\\Backgrounds\\BackgroundLevel1.png"

    };
    public static TextureReader.Texture textures[] = new TextureReader.Texture[textureNames.length];
    public static TextureReader.Texture textures2[] = new TextureReader.Texture[textureNames2.length];

    public static int textureIndecies[] = new int[textureNames.length];
    public static int textureIndecies2[] = new int[textureNames2.length];

    public static SoundEffects backgroundSound = new SoundEffects();
    public static boolean paused = false;
    static boolean how = true, start = true;
    static boolean HowToPlay, levels;
    boolean isStarted = false;
    static long startTime;


    public void init(GLAutoDrawable gld) {
        // level 017
        backgroundSound.playSound(backgroundSound.BACKGROUNDMUSIC, -1);

        assetsFolderName = "Assets\\Animation\\Running";
        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black
        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textureIndecies, 0);
        for (int i = 0; i < textureNames.length; i++) {
            try {
                textures[i] = TextureReader.readTexture(assetsFolderName + "\\" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndecies[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        textures[i].getWidth(), textures[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        textures[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        gl.glGenTextures(textureNames2.length, textureIndecies2, 0);
        for (int i = 0; i < textureNames2.length; i++) {
            try {
                textures2[i] = TextureReader.readTexture(assetsFolderName + "\\" + textureNames2[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndecies2[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        textures2[i].getWidth(), textures2[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        textures2[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        gl.glLoadIdentity();

    }

    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        if (start && how) {
            DrawStartMenuBackground(gl);
            DrawMenues(gl, 0, 0.5, 23);
            DrawMenues(gl, 0, 0, 25);
            DrawMenues(gl, 0, -.3, 28);
        } else {

        }
        if (HowToPlay) {
            DrawStartMenuBackground(gl);
            DrawMenues(gl, 0, -0.5, 26);
        }

        if (levels) {
            DrawStartMenuBackground(gl);
            DrawMenues(gl, 0, 0, 27);
        }
        if (!levels && !start && !how) {
            if (!isStarted) {
                gl.glOrtho(-500, 500, -325, 325, -1, 1);
                isStarted = true;
            }

            for (Turtles t : Turtles.turtles) {
                t.move();
            }

            DrawBackgroundElements(gl, scrollBackground);
            handleKeyPress();

            if (!bear.isDied()) {
                bear.move();
                bear.draw(gl);
            }

            drawNumberOfCoin(gl);
        }

    }

    public void DrawStartMenuBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndecies[24]);    // Turn Blending On
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void DrawMenues(GL gl, double x, double y, int index) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndecies[index]);
        gl.glPushMatrix();
        gl.glTranslated(x, y, 0);
        gl.glScaled(0.3, 0.3, 1);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawBackgroundElements(GL gl, float x) {
        // draw background
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndecies[Levels.currBackgroundIdx]);
        gl.glPushMatrix();
        gl.glTranslated(x, 0, 0);
        gl.glScalef(4 * maxWidth / 2, maxHeight / 2, 1);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(x, 0, 0);
        // draw H Blocks
        for (Blocks b : Blocks.blocks) {
            b.draw(gl);
        }

        // draw V Blocks
        for (VerticalBlocks b : VerticalBlocks.verticalBlocks) {
            b.draw(gl);
        }

        // draw Turtles
        for (Turtles t : Turtles.turtles) {
            t.draw(gl);
        }

        // draw start flag
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies[textureIndecies.length - 2]);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-495f, -250f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-420f, -250f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-420f, -160f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-495f, -160f, -1.0f);
        gl.glEnd();
        gl.glDisable(GL.GL_BLEND);
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);

        // draw finish flag
        gl.glPushMatrix();
        gl.glTranslated(x, 0, 0);
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies[textureIndecies.length - 4]);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1400f, -250f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1600f, -250f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1600f, -69f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1400f, -69f, -1.0f);
        gl.glEnd();
        gl.glDisable(GL.GL_BLEND);
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);

        // draw go to next Level Menu
        if (bear.isLeveledUp) {
            gl.glEnable(GL.GL_BLEND);
            gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies[textureIndecies.length - 5]);
            gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-233f, -25f, -1.0f);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(233f, -25f, -1.0f);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(233f, 300f, -1.0f);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-233f, 300f, -1.0f);
            gl.glEnd();
            gl.glDisable(GL.GL_BLEND);
            gl.glPopMatrix();
            gl.glDisable(GL.GL_BLEND);
        }

        // draw game over
        if (bear.isDied()) {
            gl.glEnable(GL.GL_BLEND);
            gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies[textureIndecies.length - 3]);
            gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-300f, 0f, -1.0f);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(300f, 0f, -1.0f);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(300f, 300f, -1.0f);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-300f, 300f, -1.0f);
            gl.glEnd();
            gl.glDisable(GL.GL_BLEND);

        }

        //draw player 1

        if (Bear.isMulti && currentPlayer == 1 && System.currentTimeMillis() - startTime < 2000) {
            drawCurrentPlayerFlag(gl, 6);
        }

//draw player 2

        if (Bear.isMulti && currentPlayer == 2 && System.currentTimeMillis() - startTime < 2000) {
            drawCurrentPlayerFlag(gl, 7);
        }


        // draw pause button
        if (!paused && !bear.isLeveledUp) {
            gl.glEnable(GL.GL_BLEND);
            gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies[textureIndecies.length - 7]);
            gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(450f, 275f, -1.0f);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(500f, 275f, -1.0f);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(500f, 325f, -1.0f);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(450f, 325f, -1.0f);
            gl.glEnd();
            gl.glDisable(GL.GL_BLEND);
        }

        // draw resume or close buttons
        if ((paused || bear.isDied()) && !bear.isLeveledUp) {
            gl.glEnable(GL.GL_BLEND);
            gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies[textureIndecies.length - 8]);
            gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(368f, 262f, -1.0f);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(500f, 262f, -1.0f);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(500f, 325f, -1.0f);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(368f, 325f, -1.0f);
            gl.glEnd();
            gl.glDisable(GL.GL_BLEND);
        }

    }

    public static void drawCurrentPlayerFlag(GL gl, int index) {

        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies2[index]);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-300f, 0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(300f, 0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(300f, 300f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-300f, 300f, -1.0f);
        gl.glEnd();
        gl.glDisable(GL.GL_BLEND);


    }

    public void drawNumberOfCoin(GL gl) {
        if (bear.isLeveledUp) {
            gl.glPushMatrix();
            gl.glTranslatef(365, -120, 0);
        }
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies[(Bear.numberOfCoinsGathered < 10 ? 12 : (Bear.numberOfCoinsGathered / 10) + 12)]);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-450f, 225, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-400f, 225, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-400f, 301, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-450f, 301, -1.0f);
        gl.glEnd();
        gl.glDisable(GL.GL_BLEND);

        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies[(Bear.numberOfCoinsGathered % 10) + 12]);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-395f, 225, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-345f, 225, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-345f, 301, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-395f, 301, -1.0f);
        gl.glEnd();
        gl.glDisable(GL.GL_BLEND);

        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies[11]);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-340f, 225, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-290f, 225, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-290f, 300, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-340f, 300, -1.0f);
        gl.glEnd();

        gl.glDisable(GL.GL_BLEND);
        if (bear.isLeveledUp) {
            gl.glPopMatrix();
        }
    }

    /*
     * KeyListener
     */
    public void handleKeyPress() {
        if (!bear.isLeveledUp && !bear.isDied() && !paused) {
            if (isKeyPressed(KeyEvent.VK_LEFT)) {
                Bear.cantMoveRight = false;
                if (!Bear.cantMoveLeft) {
                    // move bear left
                    bear.setMovingRight(false);
                    if (scrollBackground <= 0 - bear.getxSpeed()) {
                        if (bear.getX() >= bear.getMinX()) {
                            scrollBackground += bear.getxSpeed();
                        }
                    }
                    bear.setMoving(true);
                } else {
                    bear.setMoving(false);
                }

            } else if (isKeyPressed(KeyEvent.VK_RIGHT)) {
                if (bear.getX() == 1500) {
                    bear.levelUp();

                } else {
                    Bear.cantMoveLeft = false;
                    if (!Bear.cantMoveRight) {
                        // move bear right
                        bear.setMovingRight(true);
                        if (scrollBackground >= -1500 + bear.getxSpeed()) {
                            if (bear.getX() >= bear.getMaxX()) {
                                scrollBackground -= bear.getxSpeed();
                            }
                        }
                        bear.setMoving(true);
                    } else {
                        bear.setMoving(false);
                    }
                }

            } else {
                bear.setMoving(false);
            }

            // move up and down
            if (isKeyPressed(KeyEvent.VK_SPACE)) {
                // jump
                bear.jump();
            }
            if (isKeyPressed(KeyEvent.VK_UP)) {
                // turtles.get(0).jumpAndMove();

            }
        }
    }

    public BitSet keyBits = new BitSet(256);

    @Override
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void keyTyped(final KeyEvent event) {
        // don't care 
    }

    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        double x = me.getX();
        double y = me.getY();
        if (x > 394 && x < 581 && y < 181 && y > 117 && start) {
            HowToPlay = start = how = false;
            levels = true;
        }
        if (x < 621 && x > 355 && y < 320 && y > 276 && how) {
            HowToPlay = true;
        }
        if (x < 637 && x > 628 && y < 376 && y > 367 && HowToPlay) {
            HowToPlay = false;
        }

        if (x < 630 && x > 350 && y < 420 && y > 401 && start) {
            level = 2;
            Bear.isMulti = true;
            start = how = levels = false;
            Levels.levels(level);


        }

        if (levels) {
            if (x < 562 && x > 418 && y < 263 && y > 222) {
                level = 1;
                start = how = levels = false;
                Levels.levels(level);
            }
            if (x < 562 && x > 418 && y < 329 && y > 286) {

                level = 2;
                Levels.levels(level);
                start = how = levels = false;

            }
            if (x < 562 && x > 418 && y < 390 && y > 350) {
                level = 3;
                Levels.levels(level);
                start = how = levels = false;

            }
            if (x < 398 && x > 370 && y < 235 && y > 222) {
                start = how = true;
                levels = false;
            }
        }
        if (bear.isLeveledUp) {
            if (x < 440 && x > 387 && y < 280 && y > 230) {

                level++;
                Levels.resetLevel();
                backgroundSound.playSound(backgroundSound.BACKGROUNDMUSIC, -1);
                Levels.levels(level);
            }
        }

        // closed clicked
        if (bear.isLeveledUp) {
            if (x < 590 && x > 540 && y < 276 && y > 230) {
                System.exit(1);
            }
        }

        // paused clicked
        if (!paused && !bear.isLeveledUp) {
            if (x < 980 && x > 940 && y < 35 && y > 3) {
                paused = true;
                backgroundSound.clip.stop();

                return;
            }
        }

        if (paused || bear.isDied()) {
            if (x < 910 && x > 860 && y < 50 && y > 5) { // resume clicked

                if (!bear.isDied()) {
                    paused = false;

                } else {
                    Levels.resetLevel();
                    Levels.levels(level);
                    bear.setDied(false);
                }
                if (multiGameEnd) {
                    Levels.resetLevel();
                    Levels.levels(2);
                    multiGameEnd = false;
                    bear2Coins = 0;
                    currentPlayer = 1;
                    Bear.isMulti = true;
                    startTime = System.currentTimeMillis();
                    // bear.setDied(true);

                }
                backgroundSound.playSound(backgroundSound.BACKGROUNDMUSIC, -1);
            }

            if (x < 977 && x > 930 && y < 48 && y > 5) { // close clicked
                System.exit(1);
            }
        }

        // System.out.println(x);
        // System.out.println(y);
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
