package edu.oregonstate;

import edu.oregonstate.models.Coordinates;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class LocationCloudFunction {

    public static void main(String[] args) {
        SpringApplication.run(LocationCloudFunction.class, args);
    }

    @Bean
    public Function<String, Coordinates> getLatLong() {

        Coordinates coordinates = new Coordinates(51.5072178, -0.1275862);
        return (address) -> coordinates;
    }
}
