package com.example.demo.utility;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer addDateScalar() {

        GraphQLScalarType scalarType = ExtendedScalars.Date;

        return wiringBuilder -> wiringBuilder
                .scalar(scalarType);
    }
}