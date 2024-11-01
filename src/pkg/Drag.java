package pkg;

import Heroes.Hero;

public class Drag {
    private double xx, yy;
    public Drag(Hero hero) {
        makeDraggable(hero);
    }
    private void makeDraggable(Hero hero) {
        hero.setOnMousePressed(mouseEvent -> {
            xx = mouseEvent.getSceneX() - hero.getTranslateX();
            yy = mouseEvent.getSceneY() - hero.getTranslateY();
        });
        hero.setOnMouseDragged(mouseEvent -> {
            hero.setTranslateX(mouseEvent.getSceneX() - xx);
            hero.setTranslateY(mouseEvent.getSceneY() - yy);
        });
        hero.setOnMouseReleased(mouseEvent -> {
            double tmpx = mouseEvent.getSceneX();
            double tmpy = mouseEvent.getSceneY();
            if ((hero.isEnemy() && tmpx > 700 && tmpy > 300 && tmpy < 650) ||
                    (!hero.isEnemy() && tmpx < 700 && tmpy > 300 && tmpy < 650)) {
                hero.setInGame(true);
                hero.setState(Hero.State.RUN);
                makeNonDraggable(hero);
            } else {
                hero.setTranslateX(hero.getPrimaryX());
                hero.setTranslateY(hero.getPrimaryY());
            }
        });
    }
    public void makeNonDraggable(Hero hero) {
        hero.setOnMousePressed(mouseEvent -> {
        });
        hero.setOnMouseDragged(mouseEvent -> {
        });
        hero.setOnMouseReleased(mouseEvent -> {
        });
    }
}
