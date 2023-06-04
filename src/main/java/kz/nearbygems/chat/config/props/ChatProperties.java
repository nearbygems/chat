package kz.nearbygems.chat.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "chat")
public record ChatProperties(Integer clients,
                             Integer messages) {


}
