package vn.com.tdtu.common.config.property;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mysql")
@NoArgsConstructor
@Setter
@Getter
public class DatabaseProperty {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private String ddlAuto;
    private String dialect;
    private Boolean showSql;
}
