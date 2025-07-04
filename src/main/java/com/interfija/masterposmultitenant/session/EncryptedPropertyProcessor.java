package com.interfija.masterposmultitenant.session;

import com.interfija.masterposmultitenant.utils.config.ConfigManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

public class EncryptedPropertyProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources sources = environment.getPropertySources();
        PropertySource<?> source = null;

        String name = ConfigManager.getInstance().getPathFile();
        for (PropertySource<?> ps : sources) {
            if (ps.getName().contains(name)) {
                source = ps;
                break;
            }
        }

        if (source != null) {
            String encryptedPassword = (String) source.getProperty("spring.datasource.password");
            String decrypted = encryptedPassword; //MyCryptoUtils.decrypt(encryptedPassword);

            Map<String, Object> decryptedMap = new HashMap<>();
            decryptedMap.put("spring.datasource.password", decrypted);

            sources.addFirst(new MapPropertySource("decrypted", decryptedMap));
        }
    }

}
