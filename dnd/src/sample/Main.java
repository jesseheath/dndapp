package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        StackPane allofit = new StackPane();
        BorderPane root = new BorderPane();
        StackPane navBox = new StackPane();
        HBox navButtons = new HBox();

        // creating all navigation bar items

        Rectangle navRec = new Rectangle();
        navRec.setHeight(60);
        navRec.widthProperty().bind(root.widthProperty());
        navRec.setFill(Color.rgb(208, 114, 88));

        Button statsButton = new Button();
        statsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: rgb(255, 255, 255); -fx-border:none; ");
        statsButton.setMinHeight(59);
        statsButton.setMinWidth(100);
        statsButton.setText("Stats");

        Button settingsButton = new Button();
        settingsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: rgb(255, 255, 255); -fx-border:none;");
        settingsButton.setMinHeight(59);
        settingsButton.setMinWidth(100);
        settingsButton.setText("Settings");

        Button mapButton = new Button();
        mapButton.setStyle("-fx-background-color: transparent; -fx-text-fill: rgb(255, 255, 255); -fx-border:none;");
        mapButton.setMinHeight(59);
        mapButton.setMinWidth(100);
        mapButton.setText("Map");

        navButtons.getChildren().addAll(statsButton, settingsButton, mapButton);

        navBox.getChildren().addAll(navRec, navButtons);

        // Stats page
        BorderPane statsPage = new BorderPane();

        VBox statListing = new VBox();
        statListing.setPadding(new Insets(50, 50, 50, 50));

        int statNum = 7;
        String[] availableStats = new String[]{"Name", "Health", "Str", "Dex", "Int", "Con", "Int", "Wis"};
        HBox[] stats = new HBox[statNum];
        TextField[] statVals = new TextField[statNum];
        Label[] statNames = new Label[statNum];

        for(int i = 0; i < 7; i++){
            stats[i] = new HBox();
            stats[i].setPadding(new Insets(10, 20, 10, 20));

            statNames[i] = new Label();
            statNames[i].setText(availableStats[i]);
            statNames[i].setPadding(new Insets(0, 10, 0, 0));
            statNames[i].setMinWidth(60);

            statVals[i] = new TextField();
            statVals[i].setPromptText(availableStats[i]);

            stats[i].getChildren().addAll(statNames[i], statVals[i]);
            statListing.getChildren().add(stats[i]);
        }

        // creation of inventory
        VBox invPage = new VBox();
        Map<String, String> invMap = new HashMap<String, String>();
        String invText = "";

        Label invTitle = new Label();
        invTitle.setText("Inventory");

        ScrollPane invPane = new ScrollPane();
        invPane.setMinHeight(300);
        invPane.setMaxHeight(300);
        invPane.setMinWidth(200);

        TextField addItem = new TextField();
        addItem.setPromptText("Add Item");
        TextField addItemAmount = new TextField();
        addItemAmount.setPromptText("#");

        Text invTextField = new Text();
        invPane.setContent(invTextField);

        Button addItemButton = new Button();
        addItemButton.setText("Add");
        addItemButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String toAdd = "";
                if(invMap.get(addItem.getText()) == null) { //checks if item exists yet
                    invMap.put(addItem.getText(), addItemAmount.getText());
                    for (Map.Entry<String, String> entry : invMap.entrySet()) {
                        toAdd += entry.getKey() + ": " + entry.getValue() + "\n";
                        invTextField.setText(toAdd);
                    }
                }
                else{
                    int amt = Integer.parseInt(addItemAmount.getText());
                    int gottenval = Integer.parseInt(invMap.get(addItem.getText())); //get the value of the existing key
                    invMap.replace(addItem.getText(), "" +(gottenval+amt)); // add new amt to the old value
                    for (Map.Entry<String, String> entry : invMap.entrySet()) { //refreshing invTextfield
                        toAdd += entry.getKey() + ": " + entry.getValue() + "\n";
                        invTextField.setText(toAdd);
                    }
                }
            }
        });

        invPage.setPadding(new Insets(50, 50, 50, 50));

        invPage.getChildren().addAll(invTitle, invPane, addItem, addItemAmount, addItemButton);

        statsPage.setLeft(statListing);
        statsPage.setCenter(invPage);

        // map page
        ScrollPane mapPage = new ScrollPane();
        Button addMap = new Button();
        StackPane maplayout = new StackPane();
        addMap.setText("Add map");
        mapPage.setContent(addMap);

        addMap.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser filechooser = new FileChooser();
                File file = filechooser.showOpenDialog(primaryStage);
                Image map = new Image(file.toURI().toString());
                ImageView mapView = new ImageView(map);
                maplayout.getChildren().addAll(mapView);
                mapPage.setContent(maplayout);
            }
        });

        mapButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.setCenter(mapPage);
            }
        });

        statsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.setCenter(statsPage);
            }
        });

        // settings page
        VBox settingsPage = new VBox();
        Button navBarColor = new Button();
        Button bgColor = new Button();
        bgColor.setText("Change BG Color");
        TextField red = new TextField();
        red.setPromptText("Red");
        TextField green = new TextField();
        green.setPromptText("Green");
        TextField blue = new TextField();
        blue.setPromptText("Blue");
        Button finish = new Button();
        finish.setText("Make it so!");
        navBarColor.setText("Change nav bar color");
        navBarColor.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                settingsPage.getChildren().clear();
                finish.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        navRec.setFill(Color.rgb(Integer.parseInt(red.getText()), Integer.parseInt(green.getText()), Integer.parseInt(blue.getText())));
                    }
                });
                settingsPage.getChildren().addAll(navBarColor, red, green, blue, finish);
            }
        });

        bgColor.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                finish.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //root.setBack(Color.rgb(Integer.parseInt(red.getText()), Integer.parseInt(green.getText()), Integer.parseInt(blue.getText())));
                    }
                });
            }
        });

        settingsPage.getChildren().addAll(navBarColor);

        settingsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.setCenter(settingsPage);
            }
        });

        root.setTop(navBox);

        root.setCenter(statsPage);

        allofit.getChildren().add(root);

        primaryStage.setTitle("DnD App");
        primaryStage.setScene(new Scene(allofit, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
