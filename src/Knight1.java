//
//
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.event.ActionEvent;
//import javafx.scene.Group;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.util.Duration;
//
//import java.io.File;
//import java.util.ArrayList;
//
//public class Knight1 {
////    public static String [] args2;
//    //    File file=new File("../Images/Knight/Knight_01__ATTACK_001.png");
//    private String path;// = "src/main/java/com/example/demo1/Images/Knight/Knight_01__ATTACK_%s.png";
//    private Group group = new Group();
//    private int number;// = 10;
//    private int x;
//    private int y;
//
//    public Knight1(String path, int numberOfImages, int x, int y) {
//        this.path = path;
//        this.number = numberOfImages;
//        this.x = x;
//        this.y = y;
//    }
//
//    public Group getGroup() {
//        return this.group;
//    }
//    public void knightTest() {
//        ArrayList<ImageView> imageViews = new ArrayList<>();
//
//        for (int i=0; i<number; i++) {
//            String str = String.format(path, "00"+i);
//            Image image = new Image(new File(str).toURI().toString());
////            Image image =  new Image(getClass().getResource(str).toString());
//            imageViews.add(new ImageView(image));
//        }
//        Timeline t = new Timeline();
//        t.setCycleCount(Timeline.INDEFINITE);
//        for (int i = 0; i<imageViews.size(); i++) {
//            ImageView iv = imageViews.get(i);
//            t.getKeyFrames().add(new KeyFrame(
//                    Duration.millis(200 + i*100),
//                    (ActionEvent event) -> {
//                        group.getChildren().setAll(iv);
//                    }
//            ));
//        }
//        group.setTranslateX(x);
//        group.setTranslateY(y);
//
//        t.play();
//    }
////    public static void main(String[] args) {
////       new Thread(() -> launch(args)).start();
////       args2=args;
//////        new Thread() {
//////            @Override
//////            public void run() {
//////                HelloApplication hp = new HelloApplication("src/main/java/com/example/demo1/Images/Knight/Knight_01__ATTACK_%s.png", 10);
//////                launch();
//////            }
//////        }.start();
////        launch(args);
////    }
//}