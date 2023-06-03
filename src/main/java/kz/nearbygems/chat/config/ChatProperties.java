package kz.nearbygems.chat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Data
@ConstructorBinding
@ConfigurationProperties(prefix = "chat")
public class ChatProperties {

    private final String  host;
    private final Integer port;
    private final Integer parents;
    private final Integer children;
    private final Integer timeout;
    private final Integer backlog;
    private final Integer maxFrameLength;
    private final Integer corePoolSize;
    private final Long    taskTimeout;
    private final Integer maxActiveClients;
    private final Boolean nodelay;
    private final Boolean keepAlive;

}
