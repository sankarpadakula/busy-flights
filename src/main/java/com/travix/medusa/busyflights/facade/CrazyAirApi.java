package com.travix.medusa.busyflights.facade;

import com.travix.medusa.busyflights.domain.BusyFlightsResponse;
import com.travix.medusa.busyflights.mapper.CrazyAirMapper;
import com.travix.medusa.common.crazyair.domain.CrazyAirRequest;
import com.travix.medusa.common.crazyair.domain.CrazyAirResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CrazyAirApi {

    @Value("${supplier.endpoint.flight.crazyAir}")
    private String crazyAirUrl;

    public List<BusyFlightsResponse> getFlights(CrazyAirRequest request) {
        List<BusyFlightsResponse> listOfBusyFlight = new ArrayList<>();

        CrazyAirResponse[] response = new RestTemplate().postForEntity(crazyAirUrl, request, CrazyAirResponse[].class)
                .getBody();

        Arrays.stream(response).forEach(item -> listOfBusyFlight.add(CrazyAirMapper.convertCrazyResponse(item)));

        return listOfBusyFlight;
    }
}
