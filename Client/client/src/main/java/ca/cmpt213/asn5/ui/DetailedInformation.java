package ca.cmpt213.asn5.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Class DetailedInformation. This class allows the user to enter an ID of a superhuman to show it's attributes.
 * Upon clicking the submit button, a REST GET method will be called display the superhuman data stored in the json file.
 * @author Alex Ramirez
 * @version 1.0
 */
public class DetailedInformation {
    public static void startContent(Stage primaryStage) 
    {
        // Heading text
        Text detailedInfoText = new Text("DETAILED INFORMATION ABOUT A SPECIFIC SUPERHUMAN");
        Font font = Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 48);
        detailedInfoText.setFont(font);
        detailedInfoText.setStrokeWidth(3);
        detailedInfoText.setStroke(Color.BLACK);
        detailedInfoText.setFill(Color.ORANGE);
        detailedInfoText.setWrappingWidth(900);
        detailedInfoText.setTextAlignment(TextAlignment.CENTER);
        
        // Prompt the user
        Text enterSuperhumanId = new Text("Enter superhuman ID: ");
        enterSuperhumanId.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 16));

        TextField responseEnterSuperhumanId = new TextField("");
        responseEnterSuperhumanId.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));

        HBox boxEnterSuperhumanId = new HBox(enterSuperhumanId, responseEnterSuperhumanId);
        boxEnterSuperhumanId.setAlignment(Pos.CENTER);
        boxEnterSuperhumanId.setPadding(new Insets(150, 0, 0, 0));

        // Submit button
        Button submitBtn = new Button("Submit");
        submitBtn.wrapTextProperty();
        submitBtn.setMinHeight(40);
        submitBtn.setMinWidth(150);
        submitBtn.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        HBox selectBtnBox = new HBox(submitBtn);
        selectBtnBox.setAlignment(Pos.CENTER);

        // SERVER
        // Event handler
        submitBtn.setOnAction(e -> {
            if ( !responseEnterSuperhumanId.getText().equalsIgnoreCase("") ) {
                String idEntered = responseEnterSuperhumanId.getText();

                try {
                    int intIdEntered = Integer.parseInt(idEntered);

                    URL newUrl = new URL("http://localhost:8080/api/superhuman/" + intIdEntered);
                    HttpURLConnection connection = (HttpURLConnection)newUrl.openConnection();
                    connection.setRequestMethod("GET");
                    connection.getInputStream();    
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String content = reader.readLine();

                    String[] arrayContent = content.split(",");
 
                    System.out.println("connection.getResponseCode() = " + connection.getResponseCode());
                    connection.disconnect();
                    
                    DisplayInformation.startContent(primaryStage, arrayContent);

                } catch ( IOException ioException ) {
                    ioException.printStackTrace();
                } catch ( NumberFormatException numFormatExecption ) {
                    System.out.println("\nError: you entered a string in a field that requires an int!");
                } catch ( NullPointerException nullPtrException ) {
                    System.out.println("\nError: Cannot find the ID that you entered!");
                }   
            }
        });

        // Return to Main Menu
        Button retMainMenuBtn = new Button("Return to Main Menu");
        retMainMenuBtn.setMinHeight(40);
        retMainMenuBtn.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        
        // Event handler
        retMainMenuBtn.setOnAction(e -> {
            MainMenu.startContent(primaryStage);
        });

        HBox returnBox = new HBox(retMainMenuBtn);
        returnBox.setPadding(new Insets(150, 0, 20, 25));

        VBox vBox = new VBox(detailedInfoText, boxEnterSuperhumanId, submitBtn, returnBox);    
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
