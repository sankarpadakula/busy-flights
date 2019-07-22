package com.travix.medusa.suppliers.crazyair.services;

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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.travix.medusa.common.crazyair.domain.CrazyAirRequest;
import com.travix.medusa.common.crazyair.domain.CrazyAirResponse;
import com.travix.medusa.supplier.crazyair.service.CrazyAirService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("service2test")
public class CrazyAirServiceTest {

    @Autowired
    private CrazyAirService crazyAirService;

    private CrazyAirRequest crazyAirRequest;

    @Before
    public void setUp() throws Exception {
        setUpCrazyAirRequest();
    }

    @Test
    public void crazyAirSearchTest() throws InterruptedException, ExecutionException {
        List<CrazyAirResponse> crazyAirResponse = crazyAirService.getFlights(crazyAirRequest);

        Assert.notNull(crazyAirResponse);
        assertThat(crazyAirResponse, not(IsEmptyCollection.empty()));
        assertThat(crazyAirResponse, hasSize(2));
    }

    private void setUpCrazyAirRequest() {
        crazyAirRequest = new CrazyAirRequest();
        crazyAirRequest.setPassengerCount(4);
        crazyAirRequest.setOrigin("MAA");
        crazyAirRequest.setDestination("LHR");
        crazyAirRequest.setDepartureDate("01-08-2019");
        crazyAirRequest.setReturnDate("10-08-2019");
    }

}
