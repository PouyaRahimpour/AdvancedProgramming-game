package Heroes;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Knight_03 extends Hero {
    private static final double maxHealth = 100;
    private static final double power = 10;
    private static final double speed = 10;
    private static final String path = "src/Images/Heroes/Knight_03/Knight_03__%s_%s.png";
    private static final int numberOfImages = 10;
    private static final int explosionNumber = 1;
    private static final double primaryX = 100+2*dis;
    private static ArrayList<Image> runImages = new ArrayList<>();
    private static ArrayList<Image> attackImages = new ArrayList<>();
    private static ArrayList<Image> dieImages = new ArrayList<>();
    private static ArrayList<Image> idleImages = new ArrayList<>();
    private static ArrayList<Image> explosionImages = new ArrayList<>();

    public Knight_03(boolean enemy) throws FileNotFoundException {
        super(maxHealth, power, speed, path, enemy);
        setState(Hero.State.IDLE);

        this.setTranslateX(primaryX);
        this.setTranslateY(primaryY);
    }

    public static void imageSetup() throws FileNotFoundException {
        Image image;
        for (int i = 0; i < numberOfImages; i++) {
            String str = String.format(path, "RUN", "00" + i);
            image = new Image(new FileInputStream(str));
            runImages.add(image);
        }
        for (int i = 0; i < numberOfImages; i++) {
            String str = String.format(path, "ATTACK", "00" + i);
            image = new Image(new FileInputStream(str));
            attackImages.add(image);
        }
        for (int i = 0; i < numberOfImages; i++) {
            String str = String.format(path, "DIE", "00" + i);
            image = new Image(new FileInputStream(str));
            dieImages.add(image);
        }
        for (int i = 0; i < numberOfImages; i++) {
            String str = String.format(path, "IDLE", "00" + i);
            image = new Image(new FileInputStream(str));
            idleImages.add(image);
        }
        for (int i = 1; i <= numberOfImages; i++) {
            String str = String.format("src/Images/Explosions/Explosion_%s/Explosion_%s.png", explosionNumber, i);
            image = new Image(new FileInputStream(str));
            explosionImages.add(image);
        }
    }

    @Override
    public void setState(State s) {
        super.setState(s, numberOfImages, idleImages, attackImages, runImages, dieImages, explosionImages);
    }


    public double getPrimaryX() {
        return primaryX;
    }

    public double getPrimaryY() {
        return primaryY;
    }

}
