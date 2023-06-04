package kz.nearbygems.chat.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "server")
public record ServerProperties(String host,
                               Integer port) {
}
