package ca.cmpt213.asn5.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class ServerApplication. This class launches the application which acts as a server by using Tomcat.
 * @author Alex Ramirez
 * @version 1.0
 */
@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}
