package com.kithy.jsonpathexample.controller;

import com.kithy.jsonpathexample.jsonservice.PatientResourceJsonParser;
import com.kithy.jsonpathexample.service.ResourceService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/resource")
public class MainController {

    private final ResourceService resourceService;
    private final PatientResourceJsonParser patientResourceJsonParser;

    public MainController(ResourceService resourceService, PatientResourceJsonParser patientResourceJsonParser) {
        this.resourceService = resourceService;
        this.patientResourceJsonParser = patientResourceJsonParser;
    }

    @PostMapping
    public Flux<Object> postAccount(@RequestBody String json) {
        return resourceService.saveResource(json);
    }

    @GetMapping
    public Flux<String> getAll(){
        return resourceService.getResources();
    }

    @GetMapping("/name")
    public Mono<List<Object>> getPatientName(@RequestBody String json){
        return patientResourceJsonParser.readName(json);
    }

    @GetMapping("/last-name")
    public Mono<List<String>> getPatientLastName(@RequestBody String json){
        return patientResourceJsonParser.readLastName(json);
    }

    @GetMapping("/first-name")
    public Mono<List<Object>> getPatientFirstName(@RequestBody String json){
        return patientResourceJsonParser.readFirstName(json);
    }

    @GetMapping("/type")
    public Mono<String> getResourceType(@RequestBody String json){
        return patientResourceJsonParser.readResourceType(json);
    }
}
