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

public class Warrior_03 extends Hero {
    private static final double maxHealth = 300;
    private static final double power = 50;
    private static final double speed = 10;
    private static final String path = "src/Images/Heroes/Warrior_03/Warrior_03__%s_%s.png";
    private static final int numberOfImages = 10;
    private static final int explosionNumber = 1;
    private static double primaryX = 490;
    private static double primaryY = 10;
    private static ArrayList<Image> runImages = new ArrayList<>();
    private static ArrayList<Image> attackImages = new ArrayList<>();
    private static ArrayList<Image> dieImages = new ArrayList<>();
    private static ArrayList<Image> idleImages = new ArrayList<>();
    private static ArrayList<Image> explosionImages = new ArrayList<>();

    public Warrior_03(boolean enemy) throws FileNotFoundException {
        super(maxHealth, power, speed, path, enemy);
        setState(State.IDLE);
        if (enemy) {
            primaryY = 610;
        }
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
        this.state = s;
        ArrayList<Image> tmp;
        if (s.equals(State.IDLE)) {
            tmp = idleImages;
        } else if (s.equals(State.ATTACK)) {
            this.walk.pause();
            tmp = attackImages;
        } else if (s.equals(State.DIE) || s.equals(State.EXPLODE)) {
            walk.stop();
            this.count = 0;
            tmp = s.equals(State.DIE) ? dieImages : explosionImages;
            if (enemy) {
                getEnemyHeroes().remove(this);
                for (Hero h: getFriendHeroes()) {
                    Hero target = h.getTarget();
                    if (target!=null && target.equals(this)) {
                        h.setState(Hero.State.RUN);
                    }
                }
            } else {
                getFriendHeroes().remove(this);
                for (Hero h: getEnemyHeroes()) {
                    Hero target = h.getTarget();
                    if (target!=null && target.equals(this)) {
                        h.setState(Hero.State.RUN);
                    }
                }
            }
        } else { // s.equals(State.RUN)
            this.walk.play();
            tmp = runImages;
        }
        ArrayList<Image> finalTmp = tmp;
        this.timeline.stop();
        this.timeline = new Timeline(new KeyFrame(
                Duration.millis(100),
                event -> {
                    this.setImage(finalTmp.get(this.count));
                    this.count++;
                    if (this.count == numberOfImages) this.count = 0;
                }));
        if (s.equals(State.DIE) || s.equals(State.EXPLODE)) {
            this.timeline.setCycleCount(10);
        } else {
            this.timeline.setCycleCount(Timeline.INDEFINITE);
        }
        this.timeline.setOnFinished(e -> {
            try {
                Image image = new Image(new FileInputStream("src/Images/grave.png"));
                this.setTranslateX(this.getTranslateX() + this.getFitWidth()/3);
                this.setTranslateY(this.getTranslateY() + this.getFitHeight()/4);
                this.setFitHeight(image.getHeight()/10);
                this.setFitWidth(image.getWidth()/10);
                this.setImage(image);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setNode(this);
            fadeTransition.setDuration(Duration.millis(3000));
            fadeTransition.setInterpolator(Interpolator.LINEAR);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.play();
            this.setDisable(true);
        });
        this.timeline.play();
    }


    public double getPrimaryX() {
        return primaryX;
    }

    public double getPrimaryY() {
        return primaryY;
    }



}
