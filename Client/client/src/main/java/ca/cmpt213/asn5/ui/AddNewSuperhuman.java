package ca.cmpt213.asn5.ui;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
 * Class AddNewSuperhuman. This class allows the user to enter several fields about a superhuman.
 * Upon clicking the submit button, a REST POST method will be called to store the data in a json file.
 * @author Alex Ramirez
 * @version 1.0
 */
public class AddNewSuperhuman {
    public static void startContent(Stage primaryStage) 
    {
        Text superhumanTrackingText = new Text("ADD NEW SUPERHUMAN");
        Font font = Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 48);
        superhumanTrackingText.setFont(font);
        superhumanTrackingText.setStrokeWidth(3);
        superhumanTrackingText.setStroke(Color.BLACK);
        superhumanTrackingText.setFill(Color.ORANGE);
        
        // Name
        Text nameText = new Text("Name: ");
        nameText.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        TextField nameTextField = new TextField();

        // Weight
        Text weightText = new Text("Weight (kg): ");
        weightText.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        TextField weightTextField = new TextField();

        // Height
        Text heightText = new Text("Height (cm): ");
        heightText.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        TextField heightTextField = new TextField();

        // Picture URL
        Text picUrlText = new Text("Picture URL: ");
        picUrlText.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        TextField picUrlTextField = new TextField();

        // Category
        Text categoryText = new Text("Category: ");
        categoryText.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        TextField categoryTextField = new TextField();

        // Overall Ability
        Text overallAbilityText = new Text("Overall Ability: ");
        overallAbilityText.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));
        TextField overallAbilityTextField = new TextField();

        // GridPane for Text and TextField objects
        GridPane textGridPane = new GridPane(); // control, column, row
        textGridPane.add(nameText, 0, 0);
        textGridPane.add(nameTextField, 1, 0);
        textGridPane.add(weightText, 0, 1);
        textGridPane.add(weightTextField, 1, 1);
        textGridPane.add(heightText, 0, 2);
        textGridPane.add(heightTextField, 1, 2);
        textGridPane.add(picUrlText, 0, 3);
        textGridPane.add(picUrlTextField, 1, 3);
        textGridPane.add(categoryText, 0, 4);
        textGridPane.add(categoryTextField, 1, 4);
        textGridPane.add(overallAbilityText, 0, 5);
        textGridPane.add(overallAbilityTextField, 1, 5);

        textGridPane.setVgap(10);
        textGridPane.setAlignment(Pos.CENTER);

        textGridPane.setPadding(new Insets(75, 0, 0, 0));

        // Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setMinWidth(150);
        submitButton.setMinHeight(40);
        submitButton.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        submitButton.setAlignment(Pos.CENTER);
        HBox submitButtonBox = new HBox(submitButton);
        submitButtonBox.setAlignment(Pos.CENTER);
        submitButtonBox.setPadding(new Insets(20, 0, 0, 0));

        // Event handler
        submitButton.setOnAction(e -> {
            try {
                URL url = new URL("http://localhost:8080/api/superhuman/add");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

                if ( overallAbilityTextField.getText().length() > 0 ) {
                    if ( Integer.parseInt(overallAbilityTextField.getText()) < 0 ) {
                        overallAbilityTextField.setText("0");
                    } else if ( Integer.parseInt(overallAbilityTextField.getText()) > 100 ) {
                        overallAbilityTextField.setText("100");
                    }
                }

                // See if a string was entered into an int field
                if ( weightTextField.getText().length() > 0 ) {
                    Integer.parseInt(weightTextField.getText());
                }
                if ( heightTextField.getText().length() > 0 ) {
                    Integer.parseInt(heightTextField.getText());
                }
                if ( overallAbilityTextField.getText().length() > 0 ) {
                    Integer.parseInt(overallAbilityTextField.getText());
                }
                
                String defaultName = "Some Superhuman";
                int defaultWeight = 75;
                int defaultHeight = 175;
                String defaultPicUrl = "https://lh3.googleusercontent.com/pw/ADCreHc2tuTrZjiNj1XImkI6R83o1XKQ35CApiY33GiTFuVzsajh4T8I9lEWpVCbk9bMoFd1-Fcx3gtmmxdLd4UyYB-wY32CtC8GDdQXYtMbAIKzfaYGLo1GFXs-EWqE2Y2cHM756r66TWy0937imjQZzt4=w554-h554-s-no";
                int defaultOverallAbility = 70;

                // See if the URL entered is valid
                if ( !picUrlTextField.getText().equalsIgnoreCase("") ) {
                    new Image(picUrlTextField.getText());
                }

                if ( nameTextField.getText().equalsIgnoreCase("") ) {
                    nameTextField.setText(defaultName);
                }

                if ( weightTextField.getText().equalsIgnoreCase("") ) {
                    weightTextField.setText(Integer.toString(defaultWeight));
                }

                if ( heightTextField.getText().equalsIgnoreCase("") ) {
                    heightTextField.setText(Integer.toString(defaultHeight));
                }

                if ( picUrlTextField.getText().equalsIgnoreCase("") ) {
                    picUrlTextField.setText(defaultPicUrl);
                }
                
                if ( overallAbilityTextField.getText().equalsIgnoreCase("") ) {
                    overallAbilityTextField.setText(Integer.toString(defaultOverallAbility));
                }

                // SERVER
                writer.write("{\"name\":\"" + nameTextField.getText() + "\",\"weight\":" + weightTextField.getText() +
                        ",\"height\":" + heightTextField.getText() + ",\"picUrl\":\"" + picUrlTextField.getText() + 
                        "\",\"category\":\"" + categoryTextField.getText() + "\",\"overallAbility\":" + overallAbilityTextField.getText() + "}");
                writer.flush();
                writer.close();

                connection.connect();
                System.out.println("connection.getResponseCode() = " + connection.getResponseCode());
                connection.disconnect();
            } catch ( IOException ioException ) {
                ioException.printStackTrace();
            } catch ( NumberFormatException numFormatExecption ) {
                System.out.println("\nError: you entered a string in a field that required an int!");
                System.out.println("The following fields require ints: Weight (kg), Height (cm), and Overall Ability.\n");
            } catch ( IllegalArgumentException illegalArgException ) {
                System.out.println("\nError: you entered an invalid URL for the image!");
            }

        });

        // Return to Main Menu
        Button retMainMenuBtn = new Button("Return to Main Menu");
        retMainMenuBtn.wrapTextProperty();
        retMainMenuBtn.setMinHeight(40);
        retMainMenuBtn.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        
        // Event handler
        retMainMenuBtn.setOnAction(e -> {
            MainMenu.startContent(primaryStage);
        });

        HBox returnBox = new HBox(retMainMenuBtn);
        returnBox.setPadding(new Insets(25, 0, 0, 25));

        VBox vBox = new VBox(superhumanTrackingText, textGridPane, submitButtonBox, returnBox);    
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
