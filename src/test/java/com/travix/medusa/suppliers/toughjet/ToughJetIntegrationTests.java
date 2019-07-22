package com.travix.medusa.suppliers.toughjet;

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
import com.travix.medusa.common.toughjet.domain.ToughJetRequest;
import com.travix.medusa.common.toughjet.domain.ToughJetResponse;
import com.travix.medusa.supplier.toughjet.service.ToughJetService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("ToughJettest")
public class ToughJetIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ToughJetService toughJetService;
    
    @Test
    public void invalidDateFormt() throws JsonProcessingException, Exception {
        ToughJetRequest request = new ToughJetRequest();
        request.setOutboundDate("01-08-2019");
        request.setInboundDate("10-08-2019");
        request.setFrom("MAA");
        request.setTo("LHR");
        request.setNumberOfAdults(1);

        MvcResult result = mockMvc.perform(post("/toughjet/flights")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(
                        request
                )))
                .andExpect(status().isOk())
                .andReturn();

        TypeReference<List<ToughJetResponse>> typeReference = new TypeReference<List<ToughJetResponse>>() {
        };
        List<BusyFlightsResponse> flights = objectMapper.readValue(result.getResponse().getContentAsByteArray(), typeReference);
        
        Assert.assertNotNull(flights);
    }

}
