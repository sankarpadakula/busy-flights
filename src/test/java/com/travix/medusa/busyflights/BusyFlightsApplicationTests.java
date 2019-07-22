package com.travix.medusa.busyflights;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.domain.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.BusyFlightsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BusyFlightsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BusyFlightsService busyFlightsService;
    
    @Test
    public void invalidDateFormt() throws JsonProcessingException, Exception {
        BusyFlightsRequest busyFlightsRequest = new BusyFlightsRequest();
        busyFlightsRequest.setDepartureDate("01-AUG-2019");
        busyFlightsRequest.setReturnDate("10-AUG-2019");
        busyFlightsRequest.setOrigin("MAA");
        busyFlightsRequest.setDestination("LHR");
        busyFlightsRequest.setNumberOfPassengers(1);

        MvcResult result = mockMvc.perform(post("/busyflights/search")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(
                        busyFlightsRequest
                )))
                .andExpect(status().isBadRequest())
                .andReturn();

        Map<String, String> flights = objectMapper.readValue(result.getResponse().getContentAsByteArray(), Map.class);
        Assert.assertEquals("Date is in wrong format or return date is lessthan departure data, expected in dd-MM-yyyy",flights.get("message"));
    }

    @Test
    public void arrivalLessThanDeparture() throws JsonProcessingException, Exception {
        BusyFlightsRequest busyFlightsRequest = new BusyFlightsRequest();
        busyFlightsRequest.setDepartureDate("10-10-2019");
        busyFlightsRequest.setReturnDate("01-10-2019");
        busyFlightsRequest.setOrigin("MAA");
        busyFlightsRequest.setDestination("LHR");
        busyFlightsRequest.setNumberOfPassengers(1);
        MvcResult result = mockMvc.perform(post("/busyflights/search")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(
                        busyFlightsRequest
                )))
                .andExpect(status().isBadRequest())
                .andReturn();

        Map<String, String> flights = objectMapper.readValue(result.getResponse().getContentAsByteArray(), Map.class);
        Assert.assertEquals("Date is in wrong format or return date is lessthan departure data, expected in dd-MM-yyyy",flights.get("message"));

    }

    @Test
    public void wrongAirportCourt() throws JsonProcessingException, Exception {
        BusyFlightsRequest busyFlightsRequest = new BusyFlightsRequest();
        busyFlightsRequest.setDepartureDate("01-10-2019");
        busyFlightsRequest.setReturnDate("10-10-2019");
        busyFlightsRequest.setOrigin("London");
        busyFlightsRequest.setDestination("MAA");
        busyFlightsRequest.setNumberOfPassengers(1);
        MvcResult result = mockMvc.perform(post("/busyflights/search")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(
                        busyFlightsRequest
                )))
                .andExpect(status().isBadRequest())
                .andReturn();

        Map<String, String> flights = objectMapper.readValue(result.getResponse().getContentAsByteArray(), Map.class);
        Assert.assertEquals("size must be between 3 and 3",flights.get("message"));
    }
    
    @Test
    public void morePassengerCourt() throws JsonProcessingException, Exception {
        BusyFlightsRequest busyFlightsRequest = new BusyFlightsRequest();
        busyFlightsRequest.setDepartureDate("01-10-2019");
        busyFlightsRequest.setReturnDate("10-10-2019");
        busyFlightsRequest.setOrigin("MAA");
        busyFlightsRequest.setDestination("LHR");
        busyFlightsRequest.setNumberOfPassengers(6);
        MvcResult result = mockMvc.perform(post("/busyflights/search")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(
                        busyFlightsRequest
                )))
                .andExpect(status().isBadRequest())
                .andReturn();

        Map<String, String> flights = objectMapper.readValue(result.getResponse().getContentAsByteArray(), Map.class);
        Assert.assertEquals("must be between 1 and 4",flights.get("message"));
    }
    
    @Test
    public void validMessage() throws JsonProcessingException, Exception {
        when(busyFlightsService.searchFlights(any(BusyFlightsRequest.class))).thenReturn(Arrays.asList(new BusyFlightsResponse()));

        BusyFlightsRequest busyFlightsRequest = new BusyFlightsRequest();
        busyFlightsRequest.setDepartureDate("01-10-2019");
        busyFlightsRequest.setReturnDate("10-10-2019");
        busyFlightsRequest.setOrigin("MAA");
        busyFlightsRequest.setDestination("LHR");
        busyFlightsRequest.setNumberOfPassengers(4);
        MvcResult result = mockMvc.perform(post("/busyflights/search")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(
                        busyFlightsRequest
                )))
                .andExpect(status().isOk())
                .andReturn();

        TypeReference<List<BusyFlightsResponse>> typeReference = new TypeReference<List<BusyFlightsResponse>>() {
        };
        List<BusyFlightsResponse> flights = objectMapper.readValue(result.getResponse().getContentAsByteArray(), typeReference);
        Assert.assertEquals(1,flights.size());
    }
}
