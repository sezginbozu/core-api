/*******************************************************************************
 * Copyright (c) 2018 @gt_tech
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.boz.control;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.EntityPathBase;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import org.bitbucket.gt_tech.spring.data.querydsl.value.operators.ExpressionProviderFactory;
import org.bitbucket.gt_tech.spring.data.querydsl.value.operators.experimental.QuerydslHttpRequestContextAwareServletFilter;
import org.bitbucket.gt_tech.spring.data.querydsl.value.operators.experimental.QuerydslPredicateArgumentResolverBeanPostProcessor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.format.support.DefaultFormattingConversionService;

/**
 * Configures "Querydsl value operator library" experimental advanced features
 * for richer query demonstration even on non-string values (or search fields)
 * 
 * @author gt_tech
 *
 */
@Configuration
@Order(Ordered.LOWEST_PRECEDENCE)
public class QueryDslValueOperators {

    @Inject
    QueryDslMappingsBase mappings;

    @Bean
    public FilterRegistrationBean<QuerydslHttpRequestContextAwareServletFilter> querydslHttpRequestContextAwareServletFilter() {
        FilterRegistrationBean<QuerydslHttpRequestContextAwareServletFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new QuerydslHttpRequestContextAwareServletFilter(
                mappings.querydslHttpRequestContextAwareServletFilterMappings()));
        bean.setAsyncSupported(false);
        bean.setEnabled(true);
        bean.setName("querydslHttpRequestContextAwareServletFilter");
        bean.setUrlPatterns(Arrays.asList(new String[] { "/*" }));
        bean.setOrder(Ordered.LOWEST_PRECEDENCE);
        return bean;
    }

    /**
     * Note the use of delegate ConversionService which comes handy for types like
     * java.util.Date for handling powerful searches natively with Spring data.
     * 
     * @param factory QuerydslBindingsFactory instance
     * @param conversionServiceDelegate delegate ConversionService
     * @return
     */
    @Bean
    public QuerydslPredicateArgumentResolverBeanPostProcessor querydslPredicateArgumentResolverBeanPostProcessor(
            QuerydslBindingsFactory factory, DefaultFormattingConversionService conversionServiceDelegate) {
        return new QuerydslPredicateArgumentResolverBeanPostProcessor(factory, conversionServiceDelegate);
    }

    public static void bindFilterFields(QuerydslBindings bindings, EntityPathBase<?> root,
            Set<Path> includeFieldNames) {
        includeFieldNames.forEach(c -> {
            bindings.bind(c).all((path, values) -> ExpressionProviderFactory.getPredicate(path, values));
        });
        Path[] paths = includeFieldNames.toArray(new Path[includeFieldNames.size()]);
        bindings.including(paths);
        bindings.excludeUnlistedProperties(true);
    }

}
