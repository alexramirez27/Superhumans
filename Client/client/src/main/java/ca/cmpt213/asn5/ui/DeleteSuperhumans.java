package ca.cmpt213.asn5.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class DeleteSuperhumans. This class allows the user to delete a superhuman from the database.
 * Upon clicking the submit button, a REST DELETE method will be called to delete the superhuman stored in the json file.
 * @author Alex Ramirez
 * @version 1.0
 */
public class DeleteSuperhumans {
    public static void startContent(Stage primaryStage) 
    {
        Text superhumanTrackingText = new Text("DELETE SUPERHUMANS");
        Font font = Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 48);
        superhumanTrackingText.setFont(font);
        superhumanTrackingText.setStrokeWidth(3);
        superhumanTrackingText.setStroke(Color.BLACK);
        superhumanTrackingText.setFill(Color.ORANGE);
        
        // Select Superhumans Text
        Text selectSuperhumansText = new Text("Select Superhumans to delete (Name, ID): ");
        selectSuperhumansText.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        HBox selectHBox = new HBox(selectSuperhumansText);
        selectHBox.setPadding(new Insets(25, 0, 0, 0));
        selectHBox.setAlignment(Pos.CENTER);

        // ComboBox  
        ComboBox<String> superhumansComboBox = new ComboBox<>();
        HBox superhumansComboBoxBox = new HBox(superhumansComboBox);
        superhumansComboBox.setPrefWidth(450);
        superhumansComboBoxBox.setPrefWidth(450);

        // SERVER: Get the ComboBox
        try {
            URL url = new URL("http://localhost:8080/api/superhuman/all");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String jsonFileContent = reader.readLine();

            List<String> allNameAndIds = new ArrayList<>();

            String[] arrayFileContent = jsonFileContent.split("},");
            
            if ( arrayFileContent.length >= 1 && arrayFileContent[0].contains("\"id\":") ) { 
                String idPattern = ".*\"id\":(\\d+),.*";
                Pattern patternForId = Pattern.compile(idPattern);

                String namePattern = ".*\"name\":\"([\\w ]+)\",.*";
                Pattern patternForName = Pattern.compile(namePattern);

                for ( String currentSuperhuman : arrayFileContent ) {
                    Matcher matchId = patternForId.matcher(currentSuperhuman);
                    Matcher matchName = patternForName.matcher(currentSuperhuman);

                    if ( matchId.find() && matchName.find() ) {
                        String idFound = matchId.group(1);
                        String nameFound = matchName.group(1);
                        String currentNameAndId = nameFound + ", " + idFound;
                        allNameAndIds.add(currentNameAndId);
                    }
                }

                // Convert the ArrayList to an ObservableList
                ObservableList<String> observableListNamesIds = FXCollections.observableArrayList(allNameAndIds);

                superhumansComboBox.setItems(observableListNamesIds);
            }
            System.out.println("connection.getResponseCode() = " + connection.getResponseCode());
            connection.disconnect();
        } catch ( IOException ioException ) {
            ioException.printStackTrace();
        }

        // Select Button
        Button selectButton = new Button("Select");
        selectButton.setMinWidth(150);
        selectButton.setMinHeight(40);
        selectButton.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        selectButton.setAlignment(Pos.CENTER);
        HBox submitButtonBox = new HBox(selectButton);
        submitButtonBox.setPadding(new Insets(20, 0, 20, 0));

        // SERVER
        // Event handler
        selectButton.setOnAction(e -> {
            String nameAndIdSelected = superhumansComboBox.getValue().toString();
            String[] arrayNameAndId = nameAndIdSelected.split(", ");
            String idSelected = arrayNameAndId[1];

            try {
                URL url = new URL("http://localhost:8080/api/superhuman/" + idSelected);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("DELETE");
                connection.getInputStream();    
                System.out.println("connection.getResponseCode() = " + connection.getResponseCode());
                connection.disconnect();
            } catch ( IOException ioException ) {
                ioException.printStackTrace();
            }
        });

        HBox horizontalContainSubmitBtn = new HBox(superhumansComboBoxBox, submitButtonBox);
        horizontalContainSubmitBtn.setSpacing(5);
        horizontalContainSubmitBtn.setAlignment(Pos.CENTER);

        // Return to Main Menu
        Button retMainMenuBtn = new Button("Return to Main Menu");
        retMainMenuBtn.setMinHeight(40);
        retMainMenuBtn.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        
        // Event handler
        retMainMenuBtn.setOnAction(e -> {
            MainMenu.startContent(primaryStage);
        });

        HBox returnBox = new HBox(retMainMenuBtn);
        returnBox.setPadding(new Insets(300, 0, 20, 25));

        VBox vBox = new VBox(superhumanTrackingText, selectHBox, horizontalContainSubmitBtn, returnBox);    
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(50, 0, 0, 0));

        vBox.setBackground(new Background(new BackgroundFill(Color.STEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
      
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);

        primaryStage.setWidth(900);
        primaryStage.setMinHeight(650);

        // Show the window
        primaryStage.show();
    }
}
