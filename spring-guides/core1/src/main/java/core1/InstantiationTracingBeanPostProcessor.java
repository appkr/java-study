package core1;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class InstantiationTracingBeanPostProcessor implements BeanPostProcessor {

    // simply return the instantiated bean as-is
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean; // we could potentially return any object reference here...
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean.getClass().equals(Core1.Greeting.class)) {
            System.out.println("BEAN_POST_PROCESSING: Bean '" + beanName + "' created : " + bean.toString());
        } // BEAN_POST_PROCESSING: Bean 'greeting' created : module17.Module17$Greeting@45c99f66
        return bean;
    }
}
