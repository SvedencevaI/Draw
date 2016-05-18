/**
 * Created by 4 on 16.03.2016.
 */

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
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * Created by 4 on 15.03.2016.
 */
public class Main extends Application {

    public static Group root;

    public static ArrayList<Rectangle> allRectangles;

    public static ArrayList<Line> allLines;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new Group();
        Scene scene = new Scene(root, 630, 710);
        allLines = new ArrayList<Line>();
        allRectangles = new ArrayList<Rectangle>();

        //рисуем прямоугольник
        final Rectangle rectangle = new Rectangle(1, 1, 30, 30);
        root.getChildren().add(rectangle);

        Label labelDraw = new Label("Закрасить квадрат черным - Q");
        setElementOnScene(0, labelDraw, 10,645);

        Label labelClean = new Label("Стереть черный квадрат - W");
        setElementOnScene(1, labelClean, 10 ,670);

        Label info = new Label("Перемещение: up - I, right - L,\n\t\t\t\t left - J, down - K");
        setElementOnScene(2,info, 240, 645);

        Button btnClean = new Button("Очистить");
        btnClean.setMinWidth(100);
        setElementOnScene(3,btnClean, 540, 685);

        /*группа для radio button*/
        ToggleGroup groupRB = new ToggleGroup();

        final RadioButton rbRobot = new RadioButton();
        rbRobot.setText("Робот");
        rbRobot.setSelected(true);
        rbRobot.setToggleGroup(groupRB);
        setElementOnScene(5, rbRobot, 250, 685);

        

        final RadioButton rbNote = new RadioButton();
        rbNote.setText("Нота");
        rbNote.setToggleGroup(groupRB);
        setElementOnScene(7, rbNote, 410, 685);

        btnClean.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                cleanAll(allRectangles);
            }
        });

        Button btnDrawRobot = new Button("Раскрасить");
        btnDrawRobot.setMinWidth(100);
        setElementOnScene(4, btnDrawRobot, 540, 645);
        btnDrawRobot.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if(rbRobot.isSelected()){
                    cleanAll(allRectangles);
                    Robot robot = new Robot();
                    robot.DrawFigure();
                }
                if(rbCat.isSelected()){
                    cleanAll(allRectangles);
                    Cat cat = new Cat();
                    cat.DrawFigure();
                }
                if(rbNote.isSelected()){
                    cleanAll(allRectangles);
                    Note note = new Note();
                    note.DrawFigure();
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
