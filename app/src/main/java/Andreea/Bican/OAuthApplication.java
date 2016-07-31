package Andreea.Bican;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class OAuthApplication
{
    private static final String targetURL = "http://localhost:8181/loginGoogle";

    public static void main(String[] args) throws IOException {

        SpringApplication.run(OAuthApplication.class, args);

        if (args.length > 2) {
            ClientAppDetails.setServiceAccountId(args[0]);
            ClientAppDetails.setServiceAccountUser(args[1]);
            ClientAppDetails.setP12FileName(args[2]);
        }
    }
}