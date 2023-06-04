package kz.nearbygems.chat.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "bootstrap")
public record BootstrapProperties(Integer parents,
                                  Integer children,
                                  Integer timeout,
                                  Integer backlog,
                                  Boolean nodelay,
                                  Boolean keepAlive) {
}
