package ca.cmpt213.asn5.server.models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class Superhuman. This class contains the attributes that describe a superhuman.
 * This class also contains getters and setters and a method that assigns a unique ID to each superhuman instantiated.
 * @author Alex Ramirez
 * @version 1.0
 */
public class Superhuman {
    private long id = 0;
    private String name; 
    private int weight; 
    private int height; 
    private String picUrl; 
    private String category; 
    private int overallAbility; 

    public Superhuman() {
        this.id = assignId();
        this.name = "Some Superhuman";
        this.weight = 75;
        this.height = 175;
        this.picUrl = "https://lh3.googleusercontent.com/pw/ADCreHc2tuTrZjiNj1XImkI6R83o1XKQ35CApiY33GiTFuVzsajh4T8I9lEWpVCbk9bMoFd1-Fcx3gtmmxdLd4UyYB-wY32CtC8GDdQXYtMbAIKzfaYGLo1GFXs-EWqE2Y2cHM756r66TWy0937imjQZzt4=w554-h554-s-no";
        this.category = "";
        this.overallAbility = 0;
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight( int weight ) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight ( int height ) {
        this.height = height;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl( String picUrl ) {
        this.picUrl = picUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory( String category ) {
        this.category = category;
    }

    public int getOverallAbility() {
        return overallAbility;
    }

    public void setOverallAbility( int overallAbility ) {
        this.overallAbility = overallAbility;
    }

    public long assignId() {
        // Get the path of superhuman.json
        String currentDirectory = System.getProperty("user.dir");
        File parentDirectory = new File(currentDirectory);
        String superhumanJsonPath = parentDirectory + "/server/src/main/java/ca/cmpt213/asn5/server/data/superhuman.json";

        String fileContent = "";
        try {
            fileContent = Files.readString(Paths.get(superhumanJsonPath));
        } catch (IOException e) {
            System.out.println("\nError: Cannot find the path: " + superhumanJsonPath);
            System.exit(-1);
        }

        String[] fileContentArr = fileContent.split("},");
        String lastElement = fileContentArr[fileContentArr.length - 1];
            
        String strPattern = ".*\"id\": (\\d+).*";
        Pattern pattern = Pattern.compile(strPattern);
        Matcher match = pattern.matcher(lastElement);

        if ( fileContentArr.length == 1 && match.find() == false ) {
            return 0;
        }
        else if ( lastElement.contains("\"id\": 0") ) {
            return 1;
        }
        else if ( match.find() == true ) {
            String idFound = match.group(1);
            int intIdFound = Integer.parseInt(idFound) + 1;
            return intIdFound;
        }
        
        return 0;
    }

}
