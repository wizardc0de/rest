package cn.jason.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//@ConfigurationProperties("storage")
@Configuration
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    @Value("${rest.upload.dir}")
    private String location;

    public String getLocation() {
        return location;
    }
}
