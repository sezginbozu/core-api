package com.boz.control;

import com.infobip.spring.data.common.InfobipSpringDataCommonConfiguration;
import com.querydsl.sql.SQLServer2012Templates;
import com.querydsl.sql.SQLServerTemplates;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.SQLTemplatesRegistry;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Extended to add schema to SQL statements
 * 
 *
 */
@Configuration
@Primary
public class InfobipSpringDataCommonConfigurationExt extends InfobipSpringDataCommonConfiguration {

    @Bean
    public SQLTemplates sqlTemplates(DataSource dataSource) throws SQLException {
        SQLTemplatesRegistry sqlTemplatesRegistry = new SQLTemplatesRegistry();
        DatabaseMetaData metaData;
        try (Connection connection = dataSource.getConnection()) {
            metaData = connection.getMetaData();
        }

        SQLTemplates.Builder templateRegistry = sqlTemplatesRegistry.getBuilder(metaData);
        templateRegistry.printSchema(); //Add schema to SQL statements
        SQLTemplates templates = templateRegistry.build();

        if (templates instanceof SQLServerTemplates || metaData.getDatabaseMajorVersion() > 11) {
            return new SQLServer2012Templates();
        }

        return templates;
    }
}
