package com.travix.medusa.supplier.toughjet.controller;

import com.travix.medusa.common.toughjet.domain.ToughJetRequest;
import com.travix.medusa.common.toughjet.domain.ToughJetResponse;
import com.travix.medusa.supplier.toughjet.service.ToughJetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/toughjet")
public class ToughJetController {

    @Autowired
    ToughJetService toughJetService;

    @RequestMapping(value = "/flights", method = RequestMethod.POST)
    public @ResponseBody
    List<ToughJetResponse> getFlights(@RequestBody ToughJetRequest request){
        return toughJetService.getFlights(request);
    }
}
