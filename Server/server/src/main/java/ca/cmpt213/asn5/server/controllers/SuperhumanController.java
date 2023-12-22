package ca.cmpt213.asn5.server.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.cmpt213.asn5.server.models.Superhuman;
import ca.cmpt213.asn5.server.models.SuperhumanList;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Class SuperhumanController. This class acts as a controller between the model and the user interface.
 * This class follows the REST API style.
 * @author Alex Ramirez
 * @version 1.0
 */
@RestController
public class SuperhumanController {
    private SuperhumanList superhumansList = new SuperhumanList();

    @GetMapping("/api/superhuman/all")
    public List<Superhuman> getSuperhumans( @RequestParam(value="uname", defaultValue="") String name, 
                                  @RequestParam(value="uweight", defaultValue="75") int weight,
                                  @RequestParam(value="uheight", defaultValue="175") int height,
                                  @RequestParam(value="upicUrl", defaultValue="") String picUrl,
                                  @RequestParam(value="uCategory", defaultValue="") String category ) {
        System.out.println("GET /api/superhuman/all");
        System.out.println(name);
        System.out.println(weight);
        System.out.println(height);
        System.out.println(picUrl);
        System.out.println(category);

        return superhumansList.getSuperhumans();
    }

    @GetMapping("/api/superhuman/{id}")
    public Superhuman getSuperhuman(@PathVariable long id) {
        System.out.println("GET /api/superhuman/" + id);
        return superhumansList.getSuperhuman(id);
    }

    @PostMapping("/api/superhuman/add")
    public String addSuperhuman(@RequestBody Superhuman newSuperhuman, HttpServletResponse response) {
        System.out.println("POST /api/superhuman/add");
        System.out.println(newSuperhuman.getName());
        System.out.println(newSuperhuman.getWeight());
        System.out.println(newSuperhuman.getHeight());
        System.out.println(newSuperhuman.getPicUrl());
        System.out.println(newSuperhuman.getCategory());

        superhumansList.addSuperhuman(newSuperhuman);

        response.setStatus(HttpServletResponse.SC_CREATED);
        
        String finalResponse = "HTTP " + response.getStatus();

        if ( response.getStatus() == 201 ) {
            finalResponse = "HTTP " + response.getStatus() + " (Created)";
        }

        return finalResponse;
    }

    @DeleteMapping("/api/superhuman/{id}")
    public String deleteSuperhuman(@PathVariable long id, HttpServletResponse response) {
        System.out.println("DELETE /api/superhuman/{id}");

        superhumansList.deleteSuperhuman(id);

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        
        String finalResponse = "HTTP " + response.getStatus();

        if ( response.getStatus() == 204 ) {
            finalResponse = "HTTP " + response.getStatus() + " (No Content)";
        }

        return finalResponse;
    }


    @PostConstruct
    public void init() {
        System.out.println("POST CONSTRUCT CODE");
        superhumansList.getSuperhumans();
    }

}
