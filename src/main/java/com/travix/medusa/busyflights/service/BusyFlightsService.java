package com.travix.medusa.busyflights.service;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travix.medusa.busyflights.domain.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.BusyFlightsResponse;
import com.travix.medusa.busyflights.facade.FlightFacade;

@Service
public class BusyFlightsService {

    @Autowired
    FlightFacade facade;
    
    public List<BusyFlightsResponse> searchFlights(BusyFlightsRequest busyFlightsRequest) throws URISyntaxException {
        return facade.getFlights(busyFlightsRequest);
    }
}
