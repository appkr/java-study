package springstudy.events2.autoinvoke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class LogicInConstructorExampleBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Environment env;

    public LogicInConstructorExampleBean(Environment env) {
        this.env = env;
        logger.info(env.toString());
    }
}
