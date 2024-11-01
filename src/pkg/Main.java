package pkg;

import Heroes.*;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class Main extends Application {
    //        120
//    200     1000
//        670
    public static void main(String[] args) {
        //images setup
        try {
            Knight_01.imageSetup();
            Knight_02.imageSetup();
            Knight_03.imageSetup();

//            Warrior_01.imageSetup();
//            Warrior_02.imageSetup();
//            Warrior_03.imageSetup();

            Elf_01.imageSetup();
            Elf_02.imageSetup();
            Elf_03.imageSetup();

            Fairy_01.imageSetup();
            Fairy_02.imageSetup();
            Fairy_03.imageSetup();

            Castle.imagesSetup();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        root = new Pane();
        root.setPrefSize(1400, 800);
        root.getChildren().add(new FXMLLoader(getClass().getResource("test.fxml")).load());
        Scene scene = new Scene(createContent(root));
        stage.setScene(scene);
        stage.setTitle("GAME");
        stage.setWidth(1400);
        stage.setHeight(800);
        stage.setResizable(false);
        stage.show();
//        Random random = new Random();
//        Thread t3 = new Thread(() -> {
//            while (true) {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                int tmp = random.nextInt(1, 10);
//                boolean tmp1 = random.nextInt(100) % 2 == 0;
//                System.out.println(tmp);
//                switch (tmp) {
//                    case 1 -> createHero1(tmp1);
//                    case 2 -> createHero2(tmp1);
//                    case 3 -> createHero3(tmp1);
//                    case 4 -> createHero4(tmp1);
//                    case 5 -> createHero5(tmp1);
//                    case 6 -> createHero6(tmp1);
//                    case 7 -> createHero7(tmp1);
//                    case 8 -> createHero8(tmp1);
//                    case 9 -> createHero9(tmp1);
//                    default -> System.out.println("nothing");
//                }
//            }
//        });
//        t3.setDaemon(true);
//        t3.start();
    }


    public static Castle getCastle1() {
        return castle1;
    }
    public static Castle getCastle2() {
        return castle2;
    }
    public static Castle castle1;
    public static Castle castle2;
    public static Stage stage;
    private static Pane root;
    public static Pane getRoot() {
        return root;
    }


    private Parent createContent(Pane root) {
        Random random = new Random();
        int backgroundNumber = random.nextInt(3) + 1;
        int castle1Number = 1;
        int castle2Number = 2;
        root.setBackground(new Background(
            new BackgroundImage(
                new Image(String.format("Images/Backgrounds/game_background_%s.png", backgroundNumber)),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
            )
        ));
        Rectangle r1 = new Rectangle(0, 300, 700, 350);
        r1.setFill(Color.BLUE);
        r1.setOpacity(0.15);
        Rectangle r2 = new Rectangle(700, 300, 1400, 350);
        r2.setFill(Color.RED);
        r2.setOpacity(0.15);
        root.getChildren().add(r1);
        root.getChildren().add(r2);
        try {
            ImageView iv = new ImageView(new Image(new FileInputStream("src/Images/Grid/grid_02.png")));
            iv.setFitWidth(1400);
            iv.setFitHeight(550);
            iv.setTranslateY(120);
            iv.setOpacity(0.15);
            root.getChildren().add(iv);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        castle1 = new Castle(castle1Number, false);
        castle2 = new Castle(castle2Number, true);
        root.getChildren().addAll(castle1, castle1.getBackRec(), castle1.getRec());
        root.getChildren().addAll(castle2, castle2.getBackRec(), castle2.getRec());
        Rectangle r3 = new Rectangle(10, 10, 1350, 80);
        r3.setFill(Color.BLUE);
        r3.setArcHeight(50);
        r3.setArcWidth(50);
        r3.setOpacity(0.5);
        Rectangle r4 = new Rectangle(10, 660, 1350, 80);
        r4.setFill(Color.RED);
        r4.setArcHeight(50);
        r4.setArcWidth(50);
        r4.setOpacity(0.5);
        root.getChildren().add(r3);
        root.getChildren().add(r4);

        r5 = new Rectangle(600,350, 200, 100);
        r5.setFill(Color.SNOW);
        r5.setArcHeight(30);
        r5.setArcWidth(30);
        l = new Label("GAME OVER");
        l.setTranslateX(600);
        l.setTranslateY(350);
        l.setPrefWidth(200);
        l.setPrefHeight(100);
        l.setAlignment(Pos.CENTER);
        l.setFont(Font.font("Bauhaus 93", 20));
        r5.setVisible(false);
        l.setVisible(false);
//            Main.getRoot().getChildren().addAll(r5, l);
        Main.getRoot().getChildren().add(r5);
        Main.getRoot().getChildren().add(l);

//        TranslateTransition s = new TranslateTransition();
//        s.setNode(l);
//        s.setFromX(400);
//        s.setFromY(-400);
//        s.setToX(400);
//        s.setToY(300);
//        s.setDuration(Duration.millis(1000));
//        s.setCycleCount(1);


        return root;
    }
    public static Label l;
    public static Rectangle r5;

    public static Label getL() {
        return l;
    }

    public static Rectangle getR5() {
        return r5;
    }
}
