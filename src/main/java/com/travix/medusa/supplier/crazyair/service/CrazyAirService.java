package com.travix.medusa.supplier.crazyair.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.common.crazyair.domain.CrazyAirRequest;
import com.travix.medusa.common.crazyair.domain.CrazyAirResponse;

@Service
public class CrazyAirService implements CommandLineRunner {

    List<CrazyAirResponse> crazyAirs;

    public List<CrazyAirResponse> getFlights(CrazyAirRequest request) {
        List<CrazyAirResponse> response = crazyAirs.stream()
                .filter(r -> r.getDepartureTrimDate().equals(request.getDepartureDate()))
                .filter(r -> r.getArrivalTrimDate().equals(request.getReturnDate()))
                .filter(r -> r.getDepartureAirportCode().equals(request.getOrigin()))
                .filter(r -> r.getDestinationAirportCode().equals(request.getDestination()))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public void run(String... arg0) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<CrazyAirResponse>> typeReference = new TypeReference<List<CrazyAirResponse>>() {
        };
        InputStream inputStream = new FileInputStream(new File("data/CrazyAirs.json"));
        try {
            crazyAirs = mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            System.out.println("Unable to read CrazyAirs.json file " + e.getMessage());
            crazyAirs = new ArrayList<CrazyAirResponse>();
        }
    }
}
