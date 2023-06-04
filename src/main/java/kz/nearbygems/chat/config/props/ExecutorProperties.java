package kz.nearbygems.chat.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "executor")
public record ExecutorProperties(Integer corePoolSize,
                                 Long delay) {
}
