package ca.cmpt213.asn5.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
 * Class DisplayInformation. This class is opened when the user clicks the submit button in the 
 * DetailedInformation class. It displays all the attributes of the superhuman entered.
 * @author Alex Ramirez
 * @version 1.0
 */
public class DisplayInformation {
    public static void startContent(Stage primaryStage, String[] arrayContent) 
    {
        // Heading text
        Text displayInfoText = new Text("DISPLAY INFORMATION");
        Font font = Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 48);
        displayInfoText.setFont(font);
        displayInfoText.setStrokeWidth(3);
        displayInfoText.setStroke(Color.BLACK);
        displayInfoText.setFill(Color.ORANGE);
        
        HBox superheroImageBox = new HBox();
        superheroImageBox.setPrefWidth(300);
        superheroImageBox.setPrefHeight(200);
        superheroImageBox.setAlignment(Pos.CENTER);

        // Superhero id
        Text superheroId = new Text("ID: ");
        superheroId.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        Text superheroIdResponse = new Text("");
        superheroIdResponse.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        superheroIdResponse.setWrappingWidth(150);
        HBox superheroIdBox = new HBox(superheroId, superheroIdResponse);
        superheroIdBox.setStyle("-fx-border-color: black;-fx-border-style: solid;");

        // Superhero name
        Text superheroName = new Text("Name: ");
        superheroName.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        Text superheroNameResponse = new Text("");
        superheroNameResponse.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        superheroNameResponse.setWrappingWidth(150);
        HBox superheroNameBox = new HBox(superheroName, superheroNameResponse);
        superheroNameBox.setStyle("-fx-border-color: black;-fx-border-style: solid;");

        // Superhero weight
        Text superheroWeight = new Text("Weight: ");
        superheroWeight.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        Text superheroWeightResponse = new Text("");
        superheroWeightResponse.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        superheroWeightResponse.setWrappingWidth(150);
        HBox superheroWeightBox = new HBox(superheroWeight, superheroWeightResponse);
        superheroWeightBox.setStyle("-fx-border-color: black;-fx-border-style: solid;");

        // Superhero height
        Text superheroHeight = new Text("Height: ");
        superheroHeight.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        Text superheroHeightResponse = new Text("");
        superheroHeightResponse.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        superheroHeightResponse.setWrappingWidth(150);
        HBox superheroHeightBox = new HBox(superheroHeight, superheroHeightResponse);
        superheroHeightBox.setStyle("-fx-border-color: black;-fx-border-style: solid;");

        // Superhero category
        Text superheroCategory = new Text("Category: ");
        superheroCategory.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        Text superheroCategoryResponse = new Text("");
        superheroCategoryResponse.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        superheroCategoryResponse.setWrappingWidth(150);
        HBox superheroCategoryBox = new HBox(superheroCategory, superheroCategoryResponse);
        superheroCategoryBox.setStyle("-fx-border-color: black;-fx-border-style: solid;");

        // Overall ability
        Text overallAbility = new Text("Overall Ability: ");
        overallAbility.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        Text overallAbilityResponse = new Text("");
        overallAbilityResponse.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        overallAbilityResponse.setWrappingWidth(150);
        HBox overallAbilityBox = new HBox(overallAbility, overallAbilityResponse);
        overallAbilityBox.setStyle("-fx-border-color: black;-fx-border-style: solid;");

        // SERVER
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
        superheroImageBox.getChildren().add(superheroImageView);
        
        superheroImageView.setFitWidth(300);
        superheroImageView.setFitHeight(200);
        
        String actualResponseCategory = getAttribute(arrayContent[5]);
        String actualResponseOverallAbility = getAttribute(arrayContent[6]);

        superheroIdResponse.setText(actualResponseId);
        superheroNameResponse.setText(actualResponseName);
        superheroWeightResponse.setText(actualResponseWeight);
        superheroHeightResponse.setText(actualResponseHeight);
        
        superheroCategoryResponse.setText(actualResponseCategory);
        overallAbilityResponse.setText(actualResponseOverallAbility);

        // VBox for the second column
        VBox mainSection = new VBox(superheroImageBox, superheroIdBox, superheroNameBox, superheroWeightBox, superheroHeightBox, superheroCategoryBox, overallAbilityBox);
        mainSection.setMaxWidth(300);
        mainSection.setPadding(new Insets(24, 0, 0, 0));

        // Return to Main Menu
        Button retMainMenuBtn = new Button("Return to Main Menu");
        retMainMenuBtn.setMinHeight(40);
        retMainMenuBtn.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        
        // Event handler
        retMainMenuBtn.setOnAction(e -> {
            MainMenu.startContent(primaryStage);
        });

        HBox returnBox = new HBox(retMainMenuBtn);
        returnBox.setPadding(new Insets(75, 0, 20, 25));

        VBox vBox = new VBox(displayInfoText, mainSection, returnBox);    
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
