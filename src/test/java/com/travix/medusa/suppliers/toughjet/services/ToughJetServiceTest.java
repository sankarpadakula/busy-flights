package com.travix.medusa.suppliers.toughjet.services;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.travix.medusa.common.toughjet.domain.ToughJetRequest;
import com.travix.medusa.common.toughjet.domain.ToughJetResponse;
import com.travix.medusa.supplier.toughjet.service.ToughJetService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("service2test")
public class ToughJetServiceTest {

    @Autowired
    private ToughJetService toughJetService;

    private ToughJetRequest toughJetRequest;

    @Before
    public void setUp() throws Exception {
        setUpCrazyAirRequest();
    }

    @Test
    public void crazyAirSearchTest() throws InterruptedException, ExecutionException {
        List<ToughJetResponse> response = toughJetService.getFlights(toughJetRequest);

        Assert.notNull(response);
        assertThat(response, not(IsEmptyCollection.empty()));
        assertThat(response, hasSize(1));
    }

    private void setUpCrazyAirRequest() {
        toughJetRequest = new ToughJetRequest();
        toughJetRequest.setNumberOfAdults(4);
        toughJetRequest.setFrom("MAA");
        toughJetRequest.setTo("LHR");
        toughJetRequest.setOutboundDate("01-08-2019");
        toughJetRequest.setInboundDate("10-08-2019");
    }

}
