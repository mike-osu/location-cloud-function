package edu.oregonstate.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import edu.oregonstate.models.Coordinates;
import edu.oregonstate.models.Input;

import java.io.IOException;

public class GetCoordinates implements RequestHandler<Input, Coordinates> {

    @Override
    public Coordinates handleRequest(Input input, Context context) {

        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey(System.getenv("google_maps_apikey"))
                .build();

        GeocodingResult[] results = new GeocodingResult[0];

        try {
            results = GeocodingApi.geocode(geoApiContext, input.getAddress()).await();
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

        Geometry geometry = results[0].geometry;

        return new Coordinates(input.getId(), geometry.location.lat, geometry.location.lng);
    }
}
