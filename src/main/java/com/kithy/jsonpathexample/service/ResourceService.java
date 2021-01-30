package com.kithy.jsonpathexample.service;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Clob;

@Service
public class ResourceService {

    private final ConnectionFactory cf;

    public ResourceService(ConnectionFactory cf) {
        this.cf = cf;
    }

    public Flux<Object> saveResource(String json) {
        return Flux.from(cf.create())
                .flatMap(c ->
                        Flux.from(c.createBatch()
                                .add("INSERT INTO RESOURCE(CONTENT) VALUES('" + json + "')")
                                .execute()));
    }

    public Flux<String> getResources() {
        return Mono.from(cf.create())
                .flatMap(c ->
                        Mono.from(c.createStatement("SELECT CONTENT FROM RESOURCE")
                                .execute())
                                .doFinally((st) -> c.close()))
                .flatMapMany(result -> Flux.from(result.map((row, meta) -> {
                    return row.get("content", String.class);
                })));
    }
}
