package com.travix.medusa.busyflights.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.travix.medusa.busyflights.domain.BusyFlightsRequest;

@Aspect
@Component
public class BusyFlightLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusyFlightLogger.class);

    @Before("execution(* com.travix.medusa.busyflights.service.BusyFlightsService.searchFlights(..)) && args(busyFlightsRequest)")
    public void beforeSearch(BusyFlightsRequest busyFlightsRequest) {
        LOGGER.info("Received a request for searching:" + busyFlightsRequest.toString());
    }

    @AfterReturning(pointcut = "execution(* com.travix.medusa.busyflights.service.BusyFlightsService.searchFlights(..))", returning = "result")
    public void afterSearch(JoinPoint joinPoint, Object result) {
        LOGGER.info("The response for the request was:" + result.toString());
    }
}
