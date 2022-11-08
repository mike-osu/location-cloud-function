package edu.oregonstate.functions;

import edu.oregonstate.models.Coordinates;

import java.util.function.Function;

public class Locator implements Function<String, Coordinates> {

    @Override
    public Coordinates apply(String address) {
        return new Coordinates(35.6761919, 139.6503106);
    }
}
