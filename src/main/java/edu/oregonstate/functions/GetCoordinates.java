package edu.oregonstate.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import edu.oregonstate.models.Coordinates;
import edu.oregonstate.models.Input;

import java.io.IOException;

public class GetCoordinates implements RequestHandler<Input, Coordinates> {

    private static final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

    private static final String QUEUE_URL = System.getenv("sqs_queue_url");

    String msgPayload = "";

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
        Coordinates coordinates = new Coordinates(input.getId(), geometry.location.lat, geometry.location.lng);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            msgPayload = objectMapper.writeValueAsString(coordinates);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        SendMessageRequest sendMsgRequest = new SendMessageRequest()
                .withQueueUrl(QUEUE_URL)
                .withMessageBody(msgPayload)
                .withDelaySeconds(5);
        sqs.sendMessage(sendMsgRequest);

        return coordinates;
    }
}
