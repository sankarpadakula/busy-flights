package com.travix.medusa.supplier.crazyair.controller;

import com.travix.medusa.common.crazyair.domain.CrazyAirRequest;
import com.travix.medusa.common.crazyair.domain.CrazyAirResponse;
import com.travix.medusa.supplier.crazyair.service.CrazyAirService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crazyair")
public class CrazyAirController {

    @Autowired
    CrazyAirService crazyAirService;

    @RequestMapping(value = "/flights", method = RequestMethod.POST)
    public @ResponseBody List<CrazyAirResponse> getFlights(@RequestBody CrazyAirRequest request){
        return crazyAirService.getFlights(request);
    }
}
