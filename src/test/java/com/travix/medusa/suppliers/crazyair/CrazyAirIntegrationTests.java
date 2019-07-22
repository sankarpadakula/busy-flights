package com.travix.medusa.suppliers.crazyair;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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
import com.travix.medusa.busyflights.domain.BusyFlightsResponse;
import com.travix.medusa.common.crazyair.domain.CrazyAirRequest;
import com.travix.medusa.common.crazyair.domain.CrazyAirResponse;
import com.travix.medusa.supplier.crazyair.service.CrazyAirService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("crazytest")
public class CrazyAirIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CrazyAirService crazyAirService;
    
    @Test
    public void invalidDateFormt() throws JsonProcessingException, Exception {
        CrazyAirRequest request = new CrazyAirRequest();
        request.setDepartureDate("01-08-2019");
        request.setReturnDate("10-08-2019");
        request.setOrigin("MAA");
        request.setDestination("LHR");
        request.setPassengerCount(1);

        MvcResult result = mockMvc.perform(post("/crazyair/flights")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(
                        request
                )))
                .andExpect(status().isOk())
                .andReturn();

        TypeReference<List<CrazyAirResponse>> typeReference = new TypeReference<List<CrazyAirResponse>>() {
        };
        List<BusyFlightsResponse> flights = objectMapper.readValue(result.getResponse().getContentAsByteArray(), typeReference);
        
        Assert.assertNotNull(flights);
    }

}
