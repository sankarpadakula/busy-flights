package com.travix.medusa.busyflights.controller;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.travix.medusa.busyflights.domain.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.BusyFlightsService;

@RestController
@RequestMapping("/busyflights")
public class BusyFlightController {

    @Autowired
    BusyFlightsService busyFlightsService;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public @ResponseBody
    List<BusyFlightsResponse> searchFlights(@Valid @RequestBody BusyFlightsRequest request) throws URISyntaxException {
        return busyFlightsService.searchFlights(request);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Object> handleEntityNotFoundException(MethodArgumentNotValidException ex) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            map.put("message", error.getDefaultMessage());
        }
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
