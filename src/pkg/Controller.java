package pkg;

import Heroes.*;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import pkg.Main;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public static Timeline t3;
    public static Thread t1;
    // Knight
    public void createHero1(boolean enemy) {
        Knight_01 hero = null;
        try {
            hero = new Knight_01(enemy);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Main.getRoot().getChildren().add(hero);

    }
    public void createHero2(boolean enemy) {
        Knight_02 hero = null;
        try {
            hero = new Knight_02(enemy);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Main.getRoot().getChildren().add(hero);
    }
    public void createHero3(boolean enemy) {
        Knight_03 hero = null;
        try {
            hero = new Knight_03(enemy);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Main.getRoot().getChildren().add(hero);
    }
    // Elf
    public void createHero4(boolean enemy) {
        Elf_01 hero = null;
        try {
            hero = new Elf_01(enemy);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Main.getRoot().getChildren().add(hero);
    }
    public void createHero5(boolean enemy) {
        Elf_02 hero = null;
        try {
            hero = new Elf_02(enemy);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Main.getRoot().getChildren().add(hero);
    }
    public void createHero6(boolean enemy) {
        Elf_03 hero = null;
        try {
            hero = new Elf_03(enemy);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Main.getRoot().getChildren().add(hero);
    }
    //Fairy
    public void createHero7(boolean enemy) {
        Fairy_01 hero = null;
        try {
            hero = new Fairy_01(enemy);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Main.getRoot().getChildren().add(hero);
    }
    public void createHero8(boolean enemy) {
        Fairy_02 hero = null;
        try {
            hero = new Fairy_02(enemy);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Main.getRoot().getChildren().add(hero);
    }
    public void createHero9(boolean enemy) {
        Fairy_03 hero = null;
        try {
            hero = new Fairy_03(enemy);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Main.getRoot().getChildren().add(hero);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        t1 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Hero h1 : Hero.getFriendHeroes()) {
                    for (Hero h2 : Hero.getEnemyHeroes()) {
                        if (Math.abs(h1.getTranslateX() - h2.getTranslateX()) < 60 &&
                                Math.abs(h1.getTranslateY() - h2.getTranslateY()) < 20) {
                            if (!h1.getState().equals(Hero.State.ATTACK) && h2.getInGame()) {
                                h1.setState(Hero.State.ATTACK);
                                h1.setTarget(h2);
                            }
                            if (!h2.getState().equals(Hero.State.ATTACK) && h1.getInGame()) {
                                h2.setState(Hero.State.ATTACK);
                                h2.setTarget(h1);
                            }
                        }
                    }
                }
            }
        });
        t1.setDaemon(true);
        t1.start();

        t3 = new Timeline(new KeyFrame(
            Duration.millis(2000),
            event -> {
                Random random = new Random();
                int tmp = random.nextInt(1, 10);
                boolean tmp1 = random.nextInt(100) % 2 == 0;
                System.out.println(tmp);
                switch (tmp) {
                    case 1 -> createHero1(tmp1);
                    case 2 -> createHero2(tmp1);
                    case 3 -> createHero3(tmp1);
                    case 4 -> createHero4(tmp1);
                    case 5 -> createHero5(tmp1);
                    case 6 -> createHero6(tmp1);
                    case 7 -> createHero7(tmp1);
                    case 8 -> createHero8(tmp1);
                    case 9 -> createHero9(tmp1);
                }
            }
        ));
        t3.setCycleCount(Timeline.INDEFINITE);
        t3.play();
    }
}
