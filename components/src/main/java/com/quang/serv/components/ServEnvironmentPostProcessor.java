package com.quang.serv.components;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

/**
 * @author Lianquan Yang
 */
public class ServEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String env = environment.getProperty("spring.profile.active");
        // components需要引入缓存和nacos的配置
        String[] profiles = new String[]{
            String.format("%s/nacos.properties", env),
            String.format("%s/redis.properties", env)
        };
        // 开始加载
        for (String profile : profiles) {
            Resource resource = new ClassPathResource(profile);
            if(!resource.exists()) throw new IllegalStateException("cannot load profile [" + profile + "] in components, please check");
            try{
                Properties properties = new Properties();
                properties.load(resource.getInputStream());
                environment.getPropertySources().addLast(new PropertiesPropertySource(resource.getFilename(), properties));
            }catch (Exception e){
                throw new IllegalStateException("exception occurred when load profile [" + profile + "] in components", e);
            }
        }
    }
}
