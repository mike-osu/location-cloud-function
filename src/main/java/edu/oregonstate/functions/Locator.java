package edu.oregonstate.functions;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import edu.oregonstate.models.Coordinates;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.function.Function;

public class Locator implements Function<String, Coordinates> {

    @Value("${google.maps.services.apikey}")
    private String apiKey;

    @Override
    public Coordinates apply(String address) {

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        GeocodingResult[] results = new GeocodingResult[0];

        try {
            results = GeocodingApi.geocode(context, address).await();
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

        Geometry geometry = results[0].geometry;

        return new Coordinates(geometry.location.lat, geometry.location.lng);
    }
}
