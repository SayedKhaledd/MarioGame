package Texture;

import java.util.ArrayList;
import javax.media.opengl.GL;
/**
 *
 * @author MohamedMashhor
 */
public class Blocks {

    private float fromX, toX, fromY, toY;
    boolean thereCoin = false;
    static ArrayList<Blocks> blocks = new ArrayList<>();

    
    public Blocks(float fromX, float fromY, boolean thereCoin) {
        this.fromX = fromX;
        this.toX = fromX + 50;
        this.fromY = fromY;
        this.toY = fromY + 51;
        this.thereCoin = thereCoin;
    }

    public float getFromX() {
        return fromX;
    }

    public void setFromX(float fromX) {
        this.fromX = fromX;
        this.toX = fromX + 50;
    }

    public float getToX() {
        return toX;
    }

    public void setToX(float toX) {
        this.toX = toX;
    }

    public float getFromY() {
        return fromY;
    }

    public void setFromY(float fromY) {
        this.fromY = fromY;
    }

    public float getToY() {
        return toY;
    }

    public void setToY(float toY) {
        this.toY = toY;
    }

    public boolean isThereCoin() {
        return thereCoin;
    }

    public void setThereCoin(boolean thereCoin) {
        this.thereCoin = thereCoin;
    }

    public void draw(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies[10]);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(fromX, fromY, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(toX, fromY, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(toX, toY, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(fromX, toY, -1.0f);
        gl.glEnd();
        gl.glDisable(GL.GL_BLEND);

        if (thereCoin) {
            gl.glEnable(GL.GL_BLEND);
            gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies[11]);
            gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(fromX, fromY + 50, -1.0f);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(toX, fromY + 50, -1.0f);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(toX, toY + 50, -1.0f);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(fromX, toY + 50, -1.0f);
            gl.glEnd();
            gl.glDisable(GL.GL_BLEND);
        }
    }

    public static Blocks isThereABlock(float currBearX, float currBearY) {
        for (Blocks b : blocks) {
            if (currBearX + 5 >= b.getFromX() && currBearX - 5 <= b.getToX()) {
                return b;
            }
        }
        return null;
    }
}
