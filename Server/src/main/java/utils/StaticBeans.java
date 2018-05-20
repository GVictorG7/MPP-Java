package utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StaticBeans {
    public static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("/XMLApplication.xml");
}
