package cn.jason;

import cn.jason.service.StorageService;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication

public class Application {

    /**
     * Start
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService){
        return (args)->{
            storageService.deleteAll();
            storageService.init();
        };
     }

    @Bean
    public MultipartConfigElement configElement() {
        MultipartConfigFactory multipartConfig = new MultipartConfigFactory();
        multipartConfig.setMaxFileSize("102400KB");
        multipartConfig.setMaxRequestSize("102400KB");
        return multipartConfig.createMultipartConfig();
    }
}
