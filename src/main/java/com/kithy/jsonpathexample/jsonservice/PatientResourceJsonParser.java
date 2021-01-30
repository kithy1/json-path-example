package com.kithy.jsonpathexample.jsonservice;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientResourceJsonParser {

    private DocumentContext documentContext;

    private final String PATIENT_NAME = "$.name[*]";
    private final String PATIENT_LAST_NAME = "$.name[*].family";
    private final String PATIENT_FIRST_NAME = "$.name[*].given[*]";
    private final String RESOURCE_TYPE = "$.resourceType";
    private final String PATIENT_GENERAL_PRACTITIONER = "$.generalPractitioner[*].reference";
    private final String PATIENT_MANAGING_ORGANIZATION = "$.managingOrganization[*].reference";
    private final String PATIENT_LINKS = "$.link.other[*].reference";

    public Mono<List<Object>> readName(String json) {
        documentContext = JsonPath.parse(json);
        JSONArray patientName = documentContext.read(PATIENT_NAME);
        List<Object> collect = patientName.stream().distinct().collect(Collectors.toList());
        return Mono.just(collect);
    }

    public Mono<List<String>> readLastName(String json) {
        documentContext = JsonPath.parse(json);
        JSONArray patientLastName = documentContext.read(PATIENT_LAST_NAME);
        List<String> collect = patientLastName.stream().map(o -> (String) o).collect(Collectors.toList());
        return Mono.just(collect);
    }

    public Mono<List<String>> readFirstName(String json) {
        documentContext = JsonPath.parse(json);
        JSONArray patientLastName = documentContext.read(PATIENT_FIRST_NAME);
        List<String> collect = patientLastName.stream()
                .distinct()
                .map(o -> (String) o)
                .collect(Collectors.toList());
        return Mono.just(collect);
    }

    public Mono<String> readResourceType(String json) {
        documentContext = JsonPath.parse(json);
        String type = documentContext.read(RESOURCE_TYPE);
        return Mono.just(type);
    }
}
