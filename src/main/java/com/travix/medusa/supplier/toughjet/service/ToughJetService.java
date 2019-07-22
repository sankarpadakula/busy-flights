package com.travix.medusa.supplier.toughjet.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.common.crazyair.domain.CrazyAirResponse;
import com.travix.medusa.common.toughjet.domain.ToughJetRequest;
import com.travix.medusa.common.toughjet.domain.ToughJetResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToughJetService implements CommandLineRunner {

    List<ToughJetResponse> toughJets;

    public List<ToughJetResponse> getFlights(ToughJetRequest request) {
        List<ToughJetResponse> response = toughJets.stream()
        .filter(r -> r.getDepartureAirportName().equals(request.getFrom()))
        .filter(r -> r.getArrivalAirportName().equals(request.getTo()))
        .filter(r -> r.getOutboundTrimDate().equals(request.getOutboundDate()))
        .filter(r -> r.getInboundTrimDate().equals(request.getInboundDate()))
        .collect(Collectors.toList());
        return response;
    }

    @Override
    public void run(String... arg0) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<ToughJetResponse>> typeReference = new TypeReference<List<ToughJetResponse>>() {
        };
        InputStream inputStream = new FileInputStream(new File("data/ToughJets.json"));
        try {
            toughJets = mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            System.out.println("Unable to read ToughJets.json file " + e.getMessage());
            toughJets = new ArrayList<ToughJetResponse>();
        }
    }
}
