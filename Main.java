

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

                }
            }
        });

        init(rectangle);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.Q){
                    Rectangle fill = new Rectangle(rectangle.getLayoutX() + 1,
                            rectangle.getLayoutY() + 1,
                            rectangle.getWidth() - 1,
                            rectangle.getHeight() - 1);
                    root.getChildren().remove(rectangle);
                    root.getChildren().add(fill);
                    root.getChildren().add(rectangle);
                    allRectangles.add(fill);
                }
                if(event.getCode() == KeyCode.W){
                    Rectangle fill = new Rectangle(rectangle.getLayoutX() + 1,
                            rectangle.getLayoutY() + 1,
                            rectangle.getWidth() - 1,
                            rectangle.getHeight() - 1);
                    fill.setFill(Color.WHITE);
                    root.getChildren().remove(rectangle);
                    root.getChildren().add(fill);
                    root.getChildren().add(rectangle);
                    allRectangles.add(fill);
                }
                if(event.getCode() == KeyCode.L) {
                    if(rectangle.getLayoutX() == 608){
                        rectangle.setLayoutX(0);
                    }else{
                        rectangle.setLayoutX(rectangle.getLayoutX() + 32);
                    }
                }
                if(event.getCode() == KeyCode.J) {
                    if(rectangle.getLayoutX() == 0){
                        rectangle.setLayoutX(608);
                    }else {
                        rectangle.setLayoutX(rectangle.getLayoutX() - 32);
                    }
                }
                if(event.getCode() == KeyCode.K) {
                    if(rectangle.getLayoutY() == 608){
                        rectangle.setLayoutY(0);
                    }else {
                        rectangle.setLayoutY(rectangle.getLayoutY() + 32);
                    }
                }
                if(event.getCode() == KeyCode.I) {
                    if(rectangle.getLayoutY() == 0){
                        rectangle.setLayoutY(608);
                    }else {
                        rectangle.setLayoutY(rectangle.getLayoutY() - 32);
                    }
                }
            }
        });

        /*Обработчик на группу RadioButton*/
        groupRB.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                cleanAll(allRectangles);
                cleanOutline(allLines);

                
                if(rbNote.isSelected()){
                    Note note = new Note();
                    note.DrawOutlineFigure();
                }
                if(rbRobot.isSelected()){
                    Robot robot = new Robot();
                    robot.DrawOutlineFigure();
                }
            }
        });

        Robot robot = new Robot();
        robot.DrawOutlineFigure();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Draw");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void init(Rectangle rectangle){
        //рисуем таблицу
        int scale = 0;
        for(int i = 0; i <= 20; i++){
            Line line = new Line(scale, 0, scale, 640);
            root.getChildren().add(line);

            line = new Line(0, scale, 640, scale);
            root.getChildren().add(line);

            scale += 32;
        }

        //ставим таймер для смены прозрачности прямоугольника
        KeyValue xValue  = new KeyValue(rectangle.opacityProperty(), 0);
        KeyFrame keyFrame  = new KeyFrame(Duration.millis(1000), xValue);
        Timeline timeline  = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().addAll(keyFrame);
        timeline.play();
    }

    public void cleanAll(ArrayList<Rectangle> r){
        for(int i = 0; i < r.size(); i++){
            root.getChildren().remove(r.get(i));
        }
    }

    public void cleanOutline(ArrayList<Line> l){
        for (int i = 0; i < l.size(); i++){
            root.getChildren().remove(l.get(i));
        }
    }

    public static void setElementOnScene (int number, Node n, int x, int y){
        root.getChildren().add(number, n);
        root.getChildren().get(number).setLayoutX(x);
        root.getChildren().get(number).setLayoutY(y);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
