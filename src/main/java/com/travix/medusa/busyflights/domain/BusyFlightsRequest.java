package com.travix.medusa.busyflights.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.travix.medusa.busyflights.validator.ValidDatesInTime;

@ValidDatesInTime
public class BusyFlightsRequest {

    @Size(min = 3, max = 3)
    private String origin;

    @Size(min = 3, max = 3)
    private String destination;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.NONE)
    private String departureDate;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.NONE)
    private String returnDate;

    @Range(min = 1, max = 4)
    private int numberOfPassengers;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(final String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(final String destination) {
        this.destination = destination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(final String departureDate) {
        this.departureDate = departureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(final String returnDate) {
        this.returnDate = returnDate;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(final int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public String toString() {
        return "{" + "origin='" + origin + '\'' + ", destination='" + destination + '\'' + ", departureDate='"
                + departureDate + '\'' + ", returnDate='" + returnDate + '\'' + ", numberOfPassengers="
                + numberOfPassengers + '}';
    }
}
