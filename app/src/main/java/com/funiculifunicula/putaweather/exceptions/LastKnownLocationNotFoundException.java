package com.funiculifunicula.putaweather.exceptions;

public class LastKnownLocationNotFoundException extends Exception {
    public LastKnownLocationNotFoundException() {
        super("The last known location could not be found using the GPS provider");
    }
}