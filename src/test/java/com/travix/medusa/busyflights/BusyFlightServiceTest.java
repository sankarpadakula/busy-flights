package com.travix.medusa.busyflights;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.travix.medusa.busyflights.domain.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.BusyFlightsResponse;
import com.travix.medusa.busyflights.facade.CrazyAirApi;
import com.travix.medusa.busyflights.facade.ToughJetApi;
import com.travix.medusa.busyflights.service.BusyFlightsService;
import com.travix.medusa.common.crazyair.domain.CrazyAirRequest;
import com.travix.medusa.common.toughjet.domain.ToughJetRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("servicetest")
public class BusyFlightServiceTest {

    private static final double DELTA = 1e-15;
    
    @Autowired
    private BusyFlightsService busyFlightsService;

    @MockBean
    private CrazyAirApi crazyAirApi;
    @MockBean
    private ToughJetApi toughJetApi;
    
    static final double CRAZY_AIR_PRICE = 200.22;
    static final double TOUGH_JET_PRICE = 149.22;

      @Test
    public void flightService_shouldbeInjected() {
        assertNotNull(busyFlightsService);
    }

    @Before
    public void setup() {
        BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
        busyFlightsResponse.setSupplier("Crazy Air");
        busyFlightsResponse.setFare(CRAZY_AIR_PRICE);
        when(crazyAirApi.getFlights(any(CrazyAirRequest.class))).
            thenReturn(Arrays.asList(busyFlightsResponse));

        BusyFlightsResponse busyFlightsResponse2 = new BusyFlightsResponse();
        busyFlightsResponse2.setSupplier("Tough Jet");
        busyFlightsResponse2.setFare(TOUGH_JET_PRICE);
        when(toughJetApi.getFlights(any(ToughJetRequest.class))).
        thenReturn(Arrays.asList(busyFlightsResponse2));
    }

    @Test
    public void searchFlight_whenSuppliersSearchesAreProvided_thenAggregateResultIntoListOfFlight() throws URISyntaxException {
        final List<BusyFlightsResponse> flights = busyFlightsService.searchFlights(new BusyFlightsRequest());
        assertEquals(flights.size(), 2);
    }

    @Test
    public void searchFlight_whenSuppliersSearchesAreProvided_thenOrderResultByFareAsc() throws URISyntaxException {
        final List<BusyFlightsResponse> flights = busyFlightsService.searchFlights(new BusyFlightsRequest());
        
        assertEquals(flights.get(0).getSupplier(), "Tough Jet");
        assertEquals(flights.get(0).getFare(), TOUGH_JET_PRICE, DELTA);
        
        assertEquals(flights.get(1).getSupplier(), "Crazy Air");
        assertEquals(flights.get(1).getFare(), CRAZY_AIR_PRICE, DELTA);



    }

}
