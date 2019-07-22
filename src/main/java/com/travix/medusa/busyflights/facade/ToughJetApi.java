package com.travix.medusa.busyflights.facade;

import com.travix.medusa.busyflights.domain.BusyFlightsResponse;
import com.travix.medusa.busyflights.mapper.ToughJetMapper;
import com.travix.medusa.common.toughjet.domain.ToughJetRequest;
import com.travix.medusa.common.toughjet.domain.ToughJetResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ToughJetApi {

    @Value("${supplier.endpoint.flight.toughJet}")
    private String toughJetUrl;

    public List<BusyFlightsResponse> getFlights(ToughJetRequest request) {
        List<BusyFlightsResponse> listOfBusyFlight = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        ToughJetResponse[] response = restTemplate.postForEntity(toughJetUrl, request, ToughJetResponse[].class)
                .getBody();

        Arrays.stream(response).forEach(item -> listOfBusyFlight.add(ToughJetMapper.convertToughResponse(item)));

        return listOfBusyFlight;
    }
}
