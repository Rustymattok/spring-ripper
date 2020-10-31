package ru.makarov.springripper.profilirovanie;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main realization for bean post processor Sample Proxy by Annotation of @Profiling.
 */
@Component
public class ProfilingAnnotationBeanPostProcessor implements BeanPostProcessor {
    private Logger logger = Logger.getLogger(ProfilingAnnotationBeanPostProcessor.class.getName());

    /**
     * HashMap of class which have annotation @Profiling.
     */
    private HashMap<String, Class> map = new HashMap<>();

    /**
     * parameter for inject MBean sample .
     */
    private ProfileController controller = new ProfileController();

    /**
     * Need init and regisatartion of Mbean.
     *
     * @throws Exception -  general not to spend time.
     */
    public ProfilingAnnotationBeanPostProcessor() throws Exception {
        MBeanServer platformBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformBeanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
    }

    /**
     * before post processor check classes for annotation @Profiling if annotated put to Map.
     *
     * @param bean     - object of scan bean.
     * @param beanName - name of object.
     * @return original bean.
     * @throws BeansException - general.
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Profiling.class)) {
            logger.log(Level.INFO, "PERSON Profil postBefore done: ");
            map.put(beanName, bean.getClass());
        }
        return bean;
    }

    /**
     * After post processor. If map not empty - we have annotation of @Profiling start to make Proxy sample.
     *
     * @param bean     - object of scan bean.
     * @param beanName - name of object.
     * @return - original bean or proxy.
     * @throws BeansException - general.
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class aClass = map.get(beanName);
        if (aClass != null) {
            logger.log(Level.INFO, "PERSON1 Proxi start ");
            return Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    logger.log(Level.INFO, "PERSON Proxi start ");
                    if (controller.isEneabled()) {
                        logger.log(Level.INFO, "PROFIL VKLU4EN!!!!! ");
                    }
                    Object invoke = method.invoke(bean, args);
                    logger.log(Level.INFO, "PERSON Proxi finish ");
                    return invoke;
                }
            });
        }
        return bean;
    }
}
