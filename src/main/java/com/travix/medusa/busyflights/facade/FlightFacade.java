package com.travix.medusa.busyflights.facade;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.travix.medusa.busyflights.domain.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.BusyFlightsResponse;
import com.travix.medusa.busyflights.mapper.CrazyAirMapper;
import com.travix.medusa.busyflights.mapper.ToughJetMapper;

@Component
public class FlightFacade  {

    @Autowired
    private CrazyAirApi crazyAirApi;
    @Autowired
    private ToughJetApi toughJetApi;

    public List<BusyFlightsResponse> getFlights(BusyFlightsRequest request) {
        List<BusyFlightsResponse> response = new ArrayList<>();
        response.addAll(crazyAirApi.getFlights(CrazyAirMapper.convertCrazyRequest(request)));
        response.addAll(toughJetApi.getFlights(ToughJetMapper.convertToughRequest(request)));
        return sort(response);
    }

    private List<BusyFlightsResponse> sort(List<BusyFlightsResponse> list){
        Collections.sort(list, new Comparator<BusyFlightsResponse>() {
            @Override
            public int compare(BusyFlightsResponse o1, BusyFlightsResponse o2) {
                if(o1.getFare() < o2.getFare())
                    return -1;
                else if(o1.getFare() > o2.getFare())
                    return 1;
                else
                    return 0;
            }
        });
        return list;
    }
}
