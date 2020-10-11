package org.example.financial.calculator.mapping;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class JacksonMappingConfiguration {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Value("${project.groupId}")
    private String projectGroupId;

    @Value("${project.artifactId}")
    private String projectArtifactId;

    @Value("${project.version}")
    private String projectVersion;

    @Value("${locale.language}")
    private String localeLanguage;

    @Value("${locale.region}")
    private String localeRegion;

    @Bean("customMappingJackson2HttpMessageConverter")
    MappingJackson2HttpMessageConverter createMappingJackson2HttpMessageConverter(
            LocalizedJacksonModule localizedModule, JavaTimeModule javaTimeModule, GuavaModule guavaModule) {
        return new MappingJackson2HttpMessageConverter(
                Jackson2ObjectMapperBuilder
                        .json()
                        .modulesToInstall(localizedModule, javaTimeModule, guavaModule)
                        .build());
    }

    @Bean
    Version getVersion() {
        return VersionUtil.parseVersion(projectVersion, projectGroupId, projectArtifactId);
    }

    @Bean
    Locale getLocale() {
        return new Locale.Builder().setLanguage(localeLanguage).setRegion(localeRegion).build();
    }

    @Bean
    JavaTimeModule createJavaTimeModule() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DATE_FORMATTER));
        return javaTimeModule;
    }

    @Bean
    GuavaModule createGuavaModule() {
        return new GuavaModule();
    }
}
