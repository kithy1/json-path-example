package com.kithy.jsonpathexample.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class SchemaBuilder {

    @Bean
    public CommandLineRunner buildDbSchema(ConnectionFactory cf) {
        return (args) ->
                Flux.from(cf.create())
                        .flatMap(c ->
                                Flux.from(c.createBatch()
                                        .add("drop table if exists RESOURCE")
                                        .add("create table resource(" +
                                                "id IDENTITY(1,1)," +
                                                "content clob)")
                                        .execute())
                                        .doFinally((st) -> c.close()))
                        .log()
                        .blockLast();

    }
}
