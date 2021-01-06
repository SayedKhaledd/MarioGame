package Texture;

import static Texture.MarioGameGLEventListener.bear;
import static Texture.MarioGameGLEventListener.scrollBackground;

/**
 *
 * @author MohamedMashhor
 */
public class Levels {

    public static int currBackgroundIdx = -1;

    public static void levels(int level) {
        if (level == 1) {
            levelOne();
        } else if (level == 2) {
            levelTwo();
        } else if (level == 3) {
            levelThree();
        }
    }

    private static void levelOne() {
        currBackgroundIdx = MarioGameGLEventListener.textureNames.length - 1;
        Blocks.blocks.add(new Blocks(-150, -140f, false));
        Blocks.blocks.add(new Blocks(0, 10f, true));
        Blocks.blocks.add(new Blocks(50, 10f, true));
        Blocks.blocks.add(new Blocks(100, 10f, true));
        Blocks.blocks.add(new Blocks(250, -140f, false));
        VerticalBlocks.verticalBlocks.add(new VerticalBlocks(600f, -255f, false));
        VerticalBlocks.verticalBlocks.add(new VerticalBlocks(1100f, -255f, false));
        Turtles.turtles.add(new Turtles(false, false, 780f, -60f, 780f, -55, 1095f, 225, 5, 25));

    }

    private static void levelTwo() {

        currBackgroundIdx = MarioGameGLEventListener.textureNames.length - 6;
        Blocks.blocks.add(new Blocks(-400f, 0f, true));
        Blocks.blocks.add(new Blocks(-350f, 0f, true));

        Blocks.blocks.add(new Blocks(-250f, -150f, true));
        Blocks.blocks.add(new Blocks(-200f, -150f, true));
        Blocks.blocks.add(new Blocks(-150f, -150f, true));

        Blocks.blocks.add(new Blocks(-50f, 0f, true));
        Blocks.blocks.add(new Blocks(0f, 0f, true));

        Blocks.blocks.add(new Blocks(50f, 130f, true));
        Blocks.blocks.add(new Blocks(150f, 130f, true));
        Blocks.blocks.add(new Blocks(250f, 130f, true));
        Blocks.blocks.add(new Blocks(350f, 130f, true));
        VerticalBlocks.verticalBlocks.add(new VerticalBlocks(800f, -255f, false));
        Turtles.turtles.add(new Turtles(false, false, 950f, -60f, 950f, -55, 1150f, 225, 8, 25));
        Blocks.blocks.add(new Blocks(930f, 80f, true));
        Blocks.blocks.add(new Blocks(1030f, 80f, true));
        Blocks.blocks.add(new Blocks(1080f, 80f, true));

        VerticalBlocks.verticalBlocks.add(new VerticalBlocks(1200f, -255f, false));

        Turtles.turtles.add(new Turtles(false, false, 0f, -60f, 0f, -55, 450f, 225, 5, 25));
        Turtles.turtles.add(new Turtles(false, false, 450f, -60f, 450f, -55, 750f, 225, 10, 25));
        MarioGameGLEventListener.startTime= System.currentTimeMillis();

    }

    private static void levelThree() {

        currBackgroundIdx = MarioGameGLEventListener.textureNames.length - 1;
        Blocks.blocks.add(new Blocks(-400f, 0f, true));
        Blocks.blocks.add(new Blocks(-350f, 0f, true));

        Blocks.blocks.add(new Blocks(-250f, -150f, true));
        Blocks.blocks.add(new Blocks(-200f, -150f, true));
        Blocks.blocks.add(new Blocks(-150f, -150f, true));

        Blocks.blocks.add(new Blocks(-50f, 0f, true));
        Blocks.blocks.add(new Blocks(0f, 0f, true));

        Blocks.blocks.add(new Blocks(50f, 130f, true));
        Blocks.blocks.add(new Blocks(150f, 130f, true));
        Blocks.blocks.add(new Blocks(250f, 130f, true));
        Blocks.blocks.add(new Blocks(350f, 130f, true));
        VerticalBlocks.verticalBlocks.add(new VerticalBlocks(800f, -255f, false));
        Turtles.turtles.add(new Turtles(false, false, 950f, -60f, 950f, -55, 1150f, 225, 8, 25));
        Turtles.turtles.add(new Turtles(false, false, 950f, -60f, 950f, -55, 1150f, 225, 3, 25));
        Turtles.turtles.add(new Turtles(false, false, 950f, -60f, 950f, -55, 1150f, 225, 10, 25));

        Blocks.blocks.add(new Blocks(980f, 80f, true));
        VerticalBlocks.verticalBlocks.add(new VerticalBlocks(1200f, -255f, false));

        Turtles.turtles.add(new Turtles(false, false, -300f, -60f, -300f, -55, 450f, 225, 5, 25));
        Turtles.turtles.add(new Turtles(false, false, -350f, -60f, -350f, -55, 450f, 225, 7, 25));
        Turtles.turtles.add(new Turtles(false, false, -200f, -60f, -200f, -55, 450f, 225, 12, 25));

        Turtles.turtles.add(new Turtles(false, false, 0f, -60f, 0f, -55, 450f, 225, 5, 25));
        Turtles.turtles.add(new Turtles(false, false, 450f, -60f, 450f, -55, 750f, 225, 10, 25));
        Turtles.turtles.add(new Turtles(false, false, 450f, -60f, 450f, -55, 750f, 225, 12, 25));

    }

    public static void resetLevel() {
        Turtles.turtles.clear();
        Blocks.blocks.clear();
        VerticalBlocks.verticalBlocks.clear();
        bear.setX(-400f);
        bear.setY(50f);
        bear.setMovingRight(true);
        bear.setCrashed(false);
        Bear.numberOfCoinsGathered = 0;
        scrollBackground = 0;
        bear.isLeveledUp = false;
    }
}
