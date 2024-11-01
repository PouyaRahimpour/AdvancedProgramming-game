//import javafx.scene.Node;
//import javafx.util.Duration;
//
//public interface Draggable {
//     double x, y;
//     static void makeDraggable(Node node) {
//        node.setOnMousePressed(mouseEvent -> {
//            x = mouseEvent.getSceneX() - node.getTranslateX();
//            y = mouseEvent.getSceneY() - node.getTranslateY();
//
//        });
//        node.setOnMouseDragged(mouseEvent -> {
//            node.setTranslateX(mouseEvent.getSceneX() - x);
//            node.setTranslateY(mouseEvent.getSceneY() - y);
//        });
//        node.setOnMouseReleased(mouseEvent -> {
//            walk.setNode(node);
//            walk.setDuration(Duration.millis(10000));
//            if (enemy) {
//                walk.setByX(-1000);
//            } else {
//                walk.setByX(1000);
//            }
//            walk.setAutoReverse(true);
////            if (enemy) {
////                RotateTransition transition1 = new RotateTransition();
////                transition1.setNode(node);
////                transition1.setDuration(Duration.ZERO);
////                transition1.setAxis(Rotate.Y_AXIS);
////                transition1.setByAngle(180);
////                transition1.play();
////            }
//            walk.play();
//            setState(Heroes.Hero.State.RUN);
//            makeNonDraggable(node);
//        });
//    }
//
//    void makeNonDraggable(Node node) {
//        node.setOnMousePressed(mouseEvent -> {});
//        node.setOnMouseDragged(mouseEvent -> {});
//        node.setOnMouseReleased(mouseEvent -> {});
//    }
//
//
//}
