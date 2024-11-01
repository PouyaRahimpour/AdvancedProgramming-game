package Heroes;

import com.sun.tools.javac.Main;
import javafx.animation.*;
import javafx.geometry.NodeOrientation;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import pkg.Drag;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static pkg.Main.castle1;
import static pkg.Main.castle2;


public abstract class Hero extends ImageView {
    public static CopyOnWriteArrayList<Hero> enemyHeroes = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Hero> friendHeroes = new CopyOnWriteArrayList<>();

    public static CopyOnWriteArrayList<Hero> getEnemyHeroes() {
        return enemyHeroes;
    }

    public static CopyOnWriteArrayList<Hero> getFriendHeroes() {
        return friendHeroes;
    }


    protected double health;
    protected double speed;
    protected double power;
    protected int count;
    protected State state;
    protected TranslateTransition walk = new TranslateTransition();
    protected Timeline timeline = new Timeline();
    protected boolean enemy;
    protected boolean out = enemy&&this.getTranslateX()<150 || !enemy&&this.getTranslateX()>1050;
//    protected String path;
//    protected int numberOfImages;
    protected Hero target;
    protected double time;
    protected double primaryY;
    protected static final int dis = 100;
    protected boolean inGame = false;

    public void setInGame(boolean b) {
        this.inGame = b;
    }

    public boolean getInGame() {
        return this.inGame;
    }


    public enum State {
        IDLE, RUN, ATTACK, DIE, EXPLODE;
    }


    public Hero(double health, double power, double speed, String path, boolean enemy) throws FileNotFoundException {
        this.health = health;
        this.enemy = enemy;
        this.power = power;
        this.speed = speed;

        Image image = new Image(new FileInputStream(String.format(path, "IDLE", "000")));
        this.setImage(image);

        this.setFitWidth(image.getWidth()/4);
        this.setFitHeight(image.getHeight()/4);


        if (isEnemy()) {
            enemyHeroes.add(this);
            this.setEffect(new DropShadow(2, Color.RED));
            this.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            primaryY = 630;
        } else {
            friendHeroes.add(this);
            this.setEffect(new DropShadow(2, Color.BLUE));
            this.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            primaryY = -10;
        }

        walk.setNode(this);
        walk.setDuration(Duration.millis(speed*1000));
        walk.setCycleCount(1);
        if (enemy) {
            walk.setByX(-1000);
        } else {
            walk.setByX(1000);
        }

        new Drag(this);
    }

    public boolean isEnemy() {
        return enemy;
    }

    public TranslateTransition getWalk () {
        return walk;
    }

    public abstract void setState(State state);

    public State getState() {
        return state;
    }

    public Hero getTarget() {
        return target;
    }

    public void setTarget(Hero target) {
        this.target = target;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getPower() {
        return power;
    }

    public double getSpeed() {
        return speed;
    }

    public void remove() {
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
    }

    protected void setState(State s,int numberOfImages, ArrayList<Image> idleImages, ArrayList<Image> attackImages,
                            ArrayList<Image> runImages, ArrayList<Image> dieImages, ArrayList<Image> explosionImages) {
        if (!s.equals(State.IDLE) && !inGame) {
            return;
        }
        this.state = s;
        ArrayList<Image> tmp;
        if (s.equals(State.IDLE)) {
            tmp = idleImages;
        } else if (s.equals(State.ATTACK)) {
            this.walk.pause();
            tmp = attackImages;
        } else if (s.equals(State.DIE)) {
            walk.stop();
            this.count = 0;
            tmp = dieImages;
            this.remove();
        } else if (s.equals(State.EXPLODE)) {
            walk.stop();
            this.count = 0;
            tmp = explosionImages;
            this.remove();
            if (this.enemy) {
                castle1.setHealth(castle1.getHealth()-this.health);
                castle1.shake();
            } else {
                castle2.setHealth(castle2.getHealth()-this.health);
                castle2.shake();
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
                    time += 100;
                    if (!this.state.equals(State.DIE) && this.health<=0) {
                        this.setState(State.DIE);

                    }
                    out = enemy&&this.getTranslateX()<150 || !enemy&&this.getTranslateX()>1050;
                    if (!this.state.equals(State.EXPLODE) && !this.state.equals(State.IDLE) && this.out) {
                        this.setState(State.EXPLODE);
                    }
                    if (this.state.equals(State.ATTACK) && this.time>=1000) {
//                        if (target != null && !target.isDisable()) {
                        this.time = 0;
                        target.setHealth(target.getHealth() - this.getPower());

//                        }
                    }
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
        });
        this.timeline.play();
    }

    public abstract double getPrimaryX();

    public abstract double getPrimaryY();
}
