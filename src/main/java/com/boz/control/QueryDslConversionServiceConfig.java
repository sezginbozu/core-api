package com.boz.control;

import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;

@Configuration
public class QueryDslConversionServiceConfig {

    @Bean
    public DefaultFormattingConversionService conversionServiceDelegate() {

        // Use the DefaultFormattingConversionService but do not register defaults
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);

        DateTimeFormatterRegistrar dateTimeRegistrar = new DateTimeFormatterRegistrar();
        dateTimeRegistrar.setDateFormatter(DateTimeFormatter.ISO_LOCAL_DATE);
        dateTimeRegistrar.setTimeFormatter(DateTimeFormatter.ISO_LOCAL_TIME);
        dateTimeRegistrar.setDateTimeFormatter(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        dateTimeRegistrar.registerFormatters(conversionService);

        return conversionService;
    }

}
