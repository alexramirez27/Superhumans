package ca.cmpt213.asn5.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class MainMenu. This class contains the starting scene which contains four buttons
 * These four buttons launch scenes that have functionalities that follow a REST API style.
 * @author Alex Ramirez
 * @version 1.0
 */
public class MainMenu {
    public static void startContent(Stage primaryStage) 
    {
        // Set the stage title
        primaryStage.setTitle("Superhumans Tracking Client");

        Text superhumanTrackingText = new Text("SUPERHUMANS TRACKING");
        Font font = Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 48);
        superhumanTrackingText.setFont(font);
        superhumanTrackingText.setStrokeWidth(3);
        superhumanTrackingText.setStroke(Color.BLACK);
        superhumanTrackingText.setFill(Color.ORANGE);
        
        // Create a GridPane
        GridPane gridPane = new GridPane();

        gridPane.setPadding(new Insets(25, 45, 10, 45));
        gridPane.setHgap(40);
        gridPane.setVgap(40);

        Button addButton = new Button();
        addButton.setText("Add new superhumans");
        addButton.setPrefWidth(370);
        addButton.setMinHeight(100);
        addButton.setWrapText(true);
        addButton.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 22));

        // Event handler
        addButton.setOnAction(e -> {
            AddNewSuperhuman.startContent(primaryStage);
        });

        gridPane.add(addButton, 0, 0);        
                    
        Button deleteButton = new Button();
        deleteButton.setText("Delete superhumans");
        deleteButton.setPrefWidth(370);
        deleteButton.setMinHeight(100);
        deleteButton.setWrapText(true);
        deleteButton.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 22));

        // Event handler
        deleteButton.setOnAction(e -> {
            DeleteSuperhumans.startContent(primaryStage);
        });

        gridPane.add(deleteButton, 1, 0);   

        Button displayButton = new Button();
        displayButton.setText("Display superhumans");
        displayButton.setPrefWidth(370);
        displayButton.setMinHeight(100);
        displayButton.setWrapText(true);
        displayButton.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 22));
        
        // Event handler
        displayButton.setOnAction(e -> {
            DisplayAllSuperhumans.startContent(primaryStage);
        });

        gridPane.add(displayButton, 0, 1);

        Button detailedButton = new Button();
        detailedButton.setText("Detailed information about a specific superhuman");
        detailedButton.setPrefWidth(370);
        detailedButton.setMinHeight(100);
        detailedButton.setWrapText(true);
        detailedButton.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 22));

        // Event handler
        detailedButton.setOnAction(e -> {
            DetailedInformation.startContent(primaryStage);
        });

        gridPane.add(detailedButton, 1, 1);

        gridPane.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(superhumanTrackingText, gridPane);    
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(75);
        vBox.setPadding(new Insets(50, 0, 50, 0));

        vBox.setBackground(new Background(new BackgroundFill(Color.STEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);

        primaryStage.setWidth(900);
        primaryStage.setMinHeight(650);

        // Show the window
        primaryStage.show();
    }

}
