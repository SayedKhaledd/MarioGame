package Texture;



import com.sun.opengl.util.*;

import java.awt.*;
import javax.media.opengl.*;
import javax.swing.*;

public class MarioGame extends JFrame {

    static GLCanvas glc;
    static FPSAnimator animator;

    public static void main(String[] args) {
        new MarioGame();
    }

    public MarioGame() {
        GLCanvas glcanvas;
        FPSAnimator animator;
        AnimListener listener = new MarioGameGLEventListener();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        glcanvas.addMouseListener(listener);

        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(20);
        animator.add(glcanvas);
        glc = glcanvas;
        animator.start();

        setTitle("Mario Special Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }
}
