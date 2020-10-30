package com.mercadolibre.firequasar.jpa;

import com.mercadolibre.firequasar.application.FactoriaPropiedadYaml;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Properties;

@Configuration
@PropertySource(value = "classpath:application-${application.environment}.yml", factory = FactoriaPropiedadYaml.class)
public class JpaConfiguration {

    public static final String PERSISTENCE_UNIT_NAME = "firequasarDS";

    @Bean("entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean dataSourceAplicacionLocal(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.driver}") String driver,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password,
            @Value("${application.environment}") String amb) {
        DriverManagerDataSource ds = new DriverManagerDataSource();

        ds.setUrl(url);
        ds.setDriverClassName(driver);
        ds.setUsername(username);
        ds.setPassword(password);

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.getJpaPropertyMap().put("hibernate.jdbc.batch_size", "5");
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(ds);
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);
        factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        factory.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
        Properties properties = new Properties();
        factory.setJpaProperties(properties);
        return factory;
    }
}
