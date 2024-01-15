package ru.otus.hw.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Locale;
import java.util.Map;

@Setter
//@Configuration
//@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "test")
//@PropertySource("application.yml")
//@EnableConfigurationProperties
//@EnableAutoConfiguration
public class AppProperties implements TestConfig, TestFileNameProvider, LocaleConfig {

    @Getter
    private int rightAnswersCountToPass;

    @Getter
    private Locale locale;

    private Map<String, String> fileNameByLocaleTag;

    AppProperties(
            int rightAnswersCountToPass,
            String locale,
            Map<String, String> fileNameByLocaleTag
    ) {

        this.rightAnswersCountToPass = rightAnswersCountToPass;
        this.locale = Locale.forLanguageTag(locale);
        this.fileNameByLocaleTag = fileNameByLocaleTag;
        System.out.println(this.locale.toLanguageTag());

    }

//    public void setLocale(String locale) {
//        System.out.println(locale);
//        System.out.println(this.locale);
//        this.locale = Locale.forLanguageTag(locale);
//    }

    @Override
    public String getTestFileName() {
        return fileNameByLocaleTag.get(this.locale.toLanguageTag());
    }
}
