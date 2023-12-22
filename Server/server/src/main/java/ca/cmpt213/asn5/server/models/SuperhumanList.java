package ca.cmpt213.asn5.server.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Class SuperhumanList. This class is used to represent a list of superhumans
 * This class contains methods that allow the user to get, post, and delete superhumans from the json file.
 * @author Alex Ramirez
 * @version 1.0
 */
public class SuperhumanList {
    private List<Superhuman> superhumans = new ArrayList<>();
    
    public void addSuperhuman(Superhuman newSuperhuman) {
        superhumans.add(newSuperhuman);

        // Get the path of superhuman.json
        String currentDirectory = System.getProperty("user.dir");
        File parentDirectory = new File(currentDirectory);
        String superhumanJsonPath = parentDirectory + "/server/src/main/java/ca/cmpt213/asn5/server/data/superhuman.json";

        // Create my GSON object
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // 1) Get the list of objects in the json file by converting it fromJson()
        try {
            FileReader reader = new FileReader(superhumanJsonPath);
            Type superhumanListType = new TypeToken<ArrayList<Superhuman>>(){}.getType();
            superhumans = gson.fromJson(reader, superhumanListType);

            // 2) Append newSuperhuman to this list
            superhumans.add(newSuperhuman);
            
            // 3) Write to the json file by using toJson()
            try (FileWriter writer = new FileWriter(superhumanJsonPath)) {
                gson.toJson(superhumans, writer);
            } catch (IOException e) {
                System.out.println("Error: IOException");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found!");
            System.exit(-1);
        }
    }

    public Superhuman getSuperhuman(long id){
        // Get the path of superhuman.json
        String currentDirectory = System.getProperty("user.dir");
        File parentDirectory = new File(currentDirectory);
        String superhumanJsonPath = parentDirectory + "/server/src/main/java/ca/cmpt213/asn5/server/data/superhuman.json";

        // Create my GSON object
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Get the list of objects in the json file by converting it fromJson()
        try {
            FileReader reader = new FileReader(superhumanJsonPath);
            Type superhumanListType = new TypeToken<ArrayList<Superhuman>>(){}.getType();
            superhumans = gson.fromJson(reader, superhumanListType);
        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found!");
            System.exit(-1);
        }

        // Find the superhuman
        for ( int i = 0; i < superhumans.size(); i++ ) {
            if ( superhumans.get(i).getId() == id ) {
                return superhumans.get(i);
            }
        }

        return null;
    }

    public List<Superhuman> getSuperhumans() {
        // Get the path of superhuman.json
        String currentDirectory = System.getProperty("user.dir");
        File parentDirectory = new File(currentDirectory);
        String superhumanJsonPath = parentDirectory + "/server/src/main/java/ca/cmpt213/asn5/server/data/superhuman.json";

        // Create my GSON object
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // 1) Get the list of objects in the json file by converting it fromJson()
        try {
            FileReader reader = new FileReader(superhumanJsonPath);
            Type superhumanListType = new TypeToken<ArrayList<Superhuman>>(){}.getType();
            superhumans = gson.fromJson(reader, superhumanListType);
        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found!");
            System.exit(-1);
        }

        return superhumans;
    }

    public int getNumOfSuperhumans() {
        return superhumans.size();
    }

    public void deleteSuperhuman(long id) {
        for (int i = 0; i < superhumans.size(); i++) {
            if (superhumans.get(i).getId() == id) {
                superhumans.remove(i);
                break;
            }
        }

        // Get the path of superhuman.json
        String currentDirectory = System.getProperty("user.dir");
        File parentDirectory = new File(currentDirectory);
        String superhumanJsonPath = parentDirectory + "/server/src/main/java/ca/cmpt213/asn5/server/data/superhuman.json";

        // Create my GSON object
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Replace the old list with the new list
        try (FileWriter writer = new FileWriter(superhumanJsonPath)) {
            gson.toJson(superhumans, writer);
        } catch (IOException e) {
            System.out.println("Error: IOException");
            System.exit(-1);
        }
    }
}
