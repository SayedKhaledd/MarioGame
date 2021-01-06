package Texture;

import java.util.ArrayList;
import javax.media.opengl.GL;

public class VerticalBlocks {

    private float fromX, toX, fromY, toY;
    boolean thereCoin = false;
    static ArrayList<VerticalBlocks> verticalBlocks = new ArrayList<>();

    public VerticalBlocks(float fromX, float fromY, boolean thereCoin) {
        this.fromX = fromX;
        this.toX = fromX + 130;
        this.fromY = fromY;
        this.toY = fromY + 175;
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
        gl.glBindTexture(GL.GL_TEXTURE_2D, MarioGameGLEventListener.textureIndecies[22]);
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

    }

    public static VerticalBlocks isThereAVerticalBlockRight(float currBearX, float currBearY) {
        for (VerticalBlocks b : verticalBlocks) { 
            if (currBearY >= b.getFromY() + 130+175 && currBearY <= b.getToY() + 130 +175 ) {
                if (currBearX == b.getFromX()) {
                    return b;
                }
            }
        }
        return null;
    }

    public static VerticalBlocks isThereAVerticalBlockLeft(float currBearX, float currBearY) {
        for (VerticalBlocks b : verticalBlocks) {
            if (currBearY >= b.getFromY() + 130+175 && currBearY <= b.getToY() + 130+175) {
                if (currBearX == b.getToX()) {
                    return b;
                }
            }
        }
        return null;
    }

    public static float isThereAVerticalBlockUnder(float currBearX, float currBearY) {
        for (VerticalBlocks b : verticalBlocks) {
            if (currBearY >= b.getToY() + 130 + 175) {
                if (currBearX > b.getFromX() && currBearX < b.getToX()) {
                    return (b.getToY() + 130 + 175);
                }
            }
        }
        return -888;
    }
}
