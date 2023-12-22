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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class DisplayAllSuperhumans. This class allows the user to select a superhuman to view it's attributes.
 * Upon clicking the select button, a REST GET method will be called to display the superhuman data in the json file.
 * @author Alex Ramirez
 * @version 1.0
 */
public class DisplayAllSuperhumans {
    public static void startContent(Stage primaryStage) 
    {
        // Heading text
        Text displayAllText = new Text("DISPLAY ALL SUPERHUMANS");
        Font font = Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 48);
        displayAllText.setFont(font);
        displayAllText.setStrokeWidth(3);
        displayAllText.setStroke(Color.BLACK);
        displayAllText.setFill(Color.ORANGE);
        
        // CheckBoxes
        CheckBox idCheckBox = new CheckBox("ID");
        idCheckBox.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        CheckBox nameCheckBox = new CheckBox("Name");
        nameCheckBox.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        CheckBox weightCheckBox = new CheckBox("Weight");
        weightCheckBox.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        CheckBox heightCheckBox = new CheckBox("Height");
        heightCheckBox.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        CheckBox pictureCheckBox = new CheckBox("Picture");
        pictureCheckBox.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        CheckBox categoryCheckBox = new CheckBox("Category");
        categoryCheckBox.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        CheckBox overallAbilityCheckBox = new CheckBox("Overall Ability");
        overallAbilityCheckBox.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));

        // GridPane for the CheckBoxes
        GridPane checkBoxGridPane = new GridPane();
        checkBoxGridPane.add(idCheckBox, 0, 0);
        checkBoxGridPane.add(nameCheckBox, 1, 0);
        checkBoxGridPane.add(weightCheckBox, 2, 0);
        checkBoxGridPane.add(heightCheckBox, 3, 0);
        checkBoxGridPane.add(pictureCheckBox, 4, 0);
        checkBoxGridPane.add(categoryCheckBox, 5, 0);
        checkBoxGridPane.add(overallAbilityCheckBox, 6, 0);
        checkBoxGridPane.setAlignment(Pos.CENTER);
        checkBoxGridPane.setHgap(20);

        /* First column in the main section */
        // Name, ID Text
        Text nameAndId = new Text("Name, ID:");
        nameAndId.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        HBox nameAndIdBox = new HBox(nameAndId);
        nameAndIdBox.setPadding(new Insets(0, 0, 0, 25));

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
        
        // Select button
        Button selectBtn = new Button("Select");
        selectBtn.wrapTextProperty();
        selectBtn.setMinHeight(40);
        selectBtn.setMinWidth(100);
        selectBtn.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        HBox selectBtnBox = new HBox(selectBtn);
        
        HBox horizontalContainSubmitBtn = new HBox(superhumansComboBoxBox, selectBtnBox);
        horizontalContainSubmitBtn.setSpacing(5);

        // VBox for the first column
        VBox firstColumnMain = new VBox(nameAndIdBox, horizontalContainSubmitBtn);
        firstColumnMain.setPrefWidth(450);
        firstColumnMain.setSpacing(5);

        /* Second column in the main section */
        HBox superheroImageBox = new HBox();
        superheroImageBox.setPrefWidth(300);
        superheroImageBox.setPrefHeight(200);
        superheroImageBox.setStyle("-fx-border-color: black;-fx-border-style: solid;");
        superheroImageBox.setAlignment(Pos.CENTER);

        // Superhero id
        Text superheroId = new Text("ID: ");
        superheroId.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        Text superheroIdResponse = new Text("");
        superheroIdResponse.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        superheroIdResponse.setWrappingWidth(150);
        superheroIdResponse.setVisible(false);
        HBox superheroIdBox = new HBox(superheroId, superheroIdResponse);
        superheroIdBox.setStyle("-fx-border-color: black;-fx-border-style: solid;");

        // Superhero name
        Text superheroName = new Text("Name: ");
        superheroName.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        Text superheroNameResponse = new Text("");
        superheroNameResponse.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        superheroNameResponse.setWrappingWidth(150);
        superheroNameResponse.setVisible(false);
        HBox superheroNameBox = new HBox(superheroName, superheroNameResponse);
        superheroNameBox.setStyle("-fx-border-color: black;-fx-border-style: solid;");

        // Superhero weight
        Text superheroWeight = new Text("Weight: ");
        superheroWeight.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        Text superheroWeightResponse = new Text("");
        superheroWeightResponse.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        superheroWeightResponse.setWrappingWidth(150);
        superheroWeightResponse.setVisible(false);
        HBox superheroWeightBox = new HBox(superheroWeight, superheroWeightResponse);
        superheroWeightBox.setStyle("-fx-border-color: black;-fx-border-style: solid;");

        // Superhero height
        Text superheroHeight = new Text("Height: ");
        superheroHeight.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        Text superheroHeightResponse = new Text("");
        superheroHeightResponse.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        superheroHeightResponse.setWrappingWidth(150);
        superheroHeightResponse.setVisible(false);
        HBox superheroHeightBox = new HBox(superheroHeight, superheroHeightResponse);
        superheroHeightBox.setStyle("-fx-border-color: black;-fx-border-style: solid;");

        // Superhero category
        Text superheroCategory = new Text("Category: ");
        superheroCategory.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        Text superheroCategoryResponse = new Text("");
        superheroCategoryResponse.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        superheroCategoryResponse.setWrappingWidth(150);
        superheroCategoryResponse.setVisible(false);
        HBox superheroCategoryBox = new HBox(superheroCategory, superheroCategoryResponse);
        superheroCategoryBox.setStyle("-fx-border-color: black;-fx-border-style: solid;");

        // Overall ability
        Text overallAbility = new Text("Overall Ability: ");
        overallAbility.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        Text overallAbilityResponse = new Text("");
        overallAbilityResponse.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        overallAbilityResponse.setWrappingWidth(150);
        overallAbilityResponse.setVisible(false);
        HBox overallAbilityBox = new HBox(overallAbility, overallAbilityResponse);
        overallAbilityBox.setStyle("-fx-border-color: black;-fx-border-style: solid;");

        // SERVER
        // Event Handler
        selectBtn.setOnAction(e -> {
            if ( superhumansComboBox.getValue() != null ) {
                String nameAndIdSelected = superhumansComboBox.getValue();
                String[] arrayNameAndId = nameAndIdSelected.split(", ");
                String idSelected = arrayNameAndId[1];

                try {
                    URL newUrl = new URL("http://localhost:8080/api/superhuman/" + idSelected);
                    HttpURLConnection connection = (HttpURLConnection)newUrl.openConnection();
                    connection.setRequestMethod("GET");
                    connection.getInputStream();    
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String content = reader.readLine();

                    String[] arrayContent = content.split(",");

                    // Responses parsed
                    String actualResponseId = getAttribute(arrayContent[0]);
                    String actualResponseName = getAttribute(arrayContent[1]);
                    String actualResponseWeight = getAttribute(arrayContent[2]);
                    String actualResponseHeight = getAttribute(arrayContent[3]);
                    String actualPicUrl = getAttribute(arrayContent[4]);

                    // If there is already a picture, remove it
                    if ( superheroImageBox.getChildren().size() == 1 ) {
                        superheroImageBox.getChildren().clear();
                    }

                    Image superheroImage = new Image(actualPicUrl);
        
                    ImageView superheroImageView = new ImageView(superheroImage);
                    
                    if ( !pictureCheckBox.isSelected() ) {
                        superheroImageView.setVisible(false);
                    }

                    superheroImageView.setFitWidth(300);
                    superheroImageView.setFitHeight(200);
                    

                    superheroImageBox.getChildren().add(superheroImageView);
                    
                    String actualResponseCategory = getAttribute(arrayContent[5]);
                    String actualResponseOverallAbility = getAttribute(arrayContent[6]);


                    superheroIdResponse.setText(actualResponseId);
                    superheroNameResponse.setText(actualResponseName);
                    superheroWeightResponse.setText(actualResponseWeight);
                    superheroHeightResponse.setText(actualResponseHeight);
                    
                    superheroCategoryResponse.setText(actualResponseCategory);
                    overallAbilityResponse.setText(actualResponseOverallAbility);

                    System.out.println("connection.getResponseCode() = " + connection.getResponseCode());
                    connection.disconnect();
                } catch ( IOException ioException ) {
                    ioException.printStackTrace();
                }
            }
        });

        // ID check box action
        idCheckBox.setOnAction(e -> {
            // See if the CheckBoxes are checked
            if ( idCheckBox.isSelected() ) {
                superheroIdResponse.setVisible(true);
            }
            else {
                superheroIdResponse.setVisible(false);
            }
        });

        // Name check box action
        nameCheckBox.setOnAction(e -> {
            // See if the CheckBoxes are checked
            if ( nameCheckBox.isSelected() ) {
                superheroNameResponse.setVisible(true);
            }
            else {
                superheroNameResponse.setVisible(false);
            }
        });

        // Weight check box action
        weightCheckBox.setOnAction(e -> {
            // See if the CheckBoxes are checked
            if ( weightCheckBox.isSelected() ) {
                superheroWeightResponse.setVisible(true);
            }
            else {
                superheroWeightResponse.setVisible(false);
            }
        });

        // Height check box action
        heightCheckBox.setOnAction(e -> {
            // See if the CheckBoxes are checked
            if ( heightCheckBox.isSelected() ) {
                superheroHeightResponse.setVisible(true);
            }
            else {
                superheroHeightResponse.setVisible(false);
            }
        });

        // Picture check box action
        pictureCheckBox.setOnAction(e -> {
            // See if the CheckBoxes are checked
            if ( pictureCheckBox.isSelected() && superheroImageBox.getChildren().size() == 1 ) {       
                superheroImageBox.getChildren().get(0).setVisible(true);
            }
            else if ( superheroImageBox.getChildren().size() == 1 ) {
                superheroImageBox.getChildren().get(0).setVisible(false);
            }
        });

        // Category check box action
        categoryCheckBox.setOnAction(e -> {
            // See if the CheckBoxes are checked
            if ( categoryCheckBox.isSelected() ) {
                superheroCategoryResponse.setVisible(true);
            }
            else {
                superheroCategoryResponse.setVisible(false);
            }
        });

        // Overall Ability check box action
        overallAbilityCheckBox.setOnAction(e -> {
            // See if the CheckBoxes are checked
            if ( overallAbilityCheckBox.isSelected() ) {
                overallAbilityResponse.setVisible(true);
            }
            else {
                overallAbilityResponse.setVisible(false);
            }
        });

        // VBox for the second column
        VBox secondColumnMain = new VBox(superheroImageBox, superheroIdBox, superheroNameBox, superheroWeightBox, superheroHeightBox, superheroCategoryBox, overallAbilityBox);
        secondColumnMain.setPrefWidth(300);
        secondColumnMain.setPadding(new Insets(24, 0, 0, 0));

        // Put both columns in an HBox
        HBox mainSection = new HBox(firstColumnMain, secondColumnMain);
        mainSection.setSpacing(50);
        mainSection.setAlignment(Pos.CENTER);

        // Return to Main Menu
        Button retMainMenuBtn = new Button("Return to Main Menu");
        retMainMenuBtn.setMinHeight(40);
        retMainMenuBtn.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        
        // Event handler
        retMainMenuBtn.setOnAction(e -> {
            MainMenu.startContent(primaryStage);
        });

        HBox returnBox = new HBox(retMainMenuBtn);
        returnBox.setPadding(new Insets(50, 0, 20, 25));

        VBox vBox = new VBox(displayAllText, checkBoxGridPane, mainSection, returnBox);    
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(15);
        vBox.setPadding(new Insets(15, 0, 0, 0));

        vBox.setBackground(new Background(new BackgroundFill(Color.STEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
      
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);

        primaryStage.setWidth(900);
        primaryStage.setMinHeight(650);

        // Show the window
        primaryStage.show();
    }

    // Helper function
    public static String getAttribute( String content ) {
        String[] arrayResponse = content.split("\":");

        if ( Character.compare(arrayResponse[1].charAt(arrayResponse[1].length() - 1), '}') == 0 ) {
            arrayResponse[1] = arrayResponse[1].substring(0, arrayResponse[1].length() - 1);
        }

        String response = "";
        if ( Character.compare(arrayResponse[1].charAt(0), '\"') == 0 ) {
            response = arrayResponse[1].substring(1, arrayResponse[1].length() - 1);
        } else {
            response = arrayResponse[1].substring(0, arrayResponse[1].length());
        }

        return response;
    }
}
