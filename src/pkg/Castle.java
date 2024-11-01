package pkg;

import Heroes.Hero;
import javafx.animation.*;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static pkg.Controller.t1;
import static pkg.Controller.t3;

public class Castle extends ImageView {
    public enum State {
        HEALTHY, SICK, DEAD;
    }
    private static final Image[][] castles = new Image[3][3];
    private static final double maxHealth = 3000;

    private double health;
    private final boolean enemy;
    private Timeline t;
    private FadeTransition fade = new FadeTransition();
    private Rectangle backRec;
    private Rectangle rec;
    private int number;
    private State state;
    private TranslateTransition shake = new TranslateTransition();

    public Castle(int number, boolean enemy) {
        this.enemy = enemy;
        this.number = number;
        this.health = maxHealth;

        fade.setNode(this);
        fade.setCycleCount(Timeline.INDEFINITE);
        fade.setFromValue(1);
        fade.setToValue(0.5);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setAutoReverse(true);

        shake.setDuration(Duration.millis(50));
        shake.setNode(this);
        shake.setCycleCount(5);
        shake.setAutoReverse(true);

        this.setState(State.HEALTHY);

        t = new Timeline(new KeyFrame(
            Duration.millis(50),
            event -> {
                if (this.health<=0) {
                    this.setState(State.DEAD);
                }
                if (this.health < maxHealth/2 && !this.state.equals(State.DEAD)) {
                    this.setState(State.SICK);
                }
                this.rec.setWidth((health*1350)/maxHealth);

            }
        ));
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

    public static void imagesSetup() throws FileNotFoundException {
        String path = "src/Images/Castles/Castle%s_%s.png";
        castles[0] =  new Image[3];
        castles[1] =  new Image[3];
        castles[2] =  new Image[3];
        castles[0][0] = new Image(new FileInputStream(String.format(path, 1, 1)));
        castles[1][0] = new Image(new FileInputStream(String.format(path, 2, 1)));
        castles[2][0] = new Image(new FileInputStream(String.format(path, 3, 1)));
        castles[0][1] = new Image(new FileInputStream(String.format(path, 1, 2)));
        castles[1][1] = new Image(new FileInputStream(String.format(path, 2, 2)));
        castles[2][1] = new Image(new FileInputStream(String.format(path, 3, 2)));
        castles[0][2] = new Image(new FileInputStream(String.format(path, 1, 3)));
        castles[1][2] = new Image(new FileInputStream(String.format(path, 2, 3)));
        castles[2][2] = new Image(new FileInputStream(String.format(path, 3, 3)));

    }

    public void setState(State s) {
        this.state = s;
        if (s.equals(State.HEALTHY)) {
            this.setImage(castles[number-1][0]);
            this.setFitWidth(400);
            this.setFitHeight(400);
            this.setPreserveRatio(true);
            if (enemy) {
                //backRec
                backRec = new Rectangle(10, 640, 1350, 10);
                backRec.setFill(Color.WHITESMOKE);
                backRec.setArcHeight(10);
                backRec.setArcHeight(10);

                //rec
                rec = new Rectangle(10, 640, 1350, 10);
                rec.setFill(Color.RED);
                rec.setArcHeight(10);
                rec.setArcWidth(10);
                rec.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

                //imageView
                this.setTranslateX(1150);
                this.setTranslateY(200);
                this.setEffect(new DropShadow(3, Color.RED));
                this.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            } else {
                //backRec
                backRec = new Rectangle(10, 100, 1350, 10);
                backRec.setFill(Color.WHITESMOKE);
                backRec.setArcHeight(10);
                backRec.setArcHeight(10);

                //rec
                rec = new Rectangle(10, 100, 1350, 10);
                rec.setFill(Color.BLUE);
                rec.setArcHeight(10);
                rec.setArcWidth(10);
                //imageView
                this.setTranslateX(-155);
                this.setTranslateY(200);
                this.setEffect(new DropShadow(3, Color.BLUE));
                this.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            }
        } else if (s.equals(State.SICK)) {
            this.setImage(castles[number-1][1]);
            fade.play();
            if (enemy) {
                this.setEffect(new DropShadow(10, Color.RED));
            } else {
                this.setEffect(new DropShadow(10, Color.BLUE));
            }
        } else if (s.equals(State.DEAD)) {
            fade.stop();
            this.setImage(castles[number-1][2]);

            if (enemy) {
                this.setEffect(new DropShadow(30, Color.RED));

            } else {
                this.setEffect(new DropShadow(30, Color.BLUE));
            }
            for (Hero h: Hero.getFriendHeroes()) {
                h.setState(Hero.State.DIE);
            }
            for (Hero h: Hero.getEnemyHeroes()) {
                h.setState(Hero.State.DIE);
            }

            Main.getR5().setVisible(true);
            Main.getL().setVisible(true);
            t3.stop();
        }

    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public Rectangle getBackRec() {
        return backRec;
    }

    public Rectangle getRec() {
        return rec;
    }
    private int x = 10;
    public void shake() {
        int dif = enemy? -x : x;
        x = -x;
        shake.setByX(dif);
        shake.play();
    }
}
