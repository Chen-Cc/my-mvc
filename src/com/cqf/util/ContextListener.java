package com.cqf.util; /**
 * Created by 78544 on 2016/11/25.
 */

import com.cqf.annotation.Path;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebListener()
public class ContextListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    // Public constructor is required by servlet spec
    public ContextListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
        ScanPackage scanPackage = new ScanPackage();
        try {
            Set<Class> classSet = scanPackage.scan();
            Path path;
            Map<String, String> actionMap = new HashMap<>();
            for (Class clazz : classSet) {
                try {
                    System.out.println(clazz.getName());
                    Method[] methods = clazz.getDeclaredMethods();
                    for (Method method : methods) {
                        method.setAccessible(true);
                        System.out.println(method.getName());
                        if (method.isAnnotationPresent(Path.class)) {
                            path = method.getAnnotation(Path.class);
                            System.out.println("value=" + path.value());
                            actionMap.put(path.value(), clazz.getName() + "|" + method.getName());
                        }
                        System.out.println("----------------");
                        method.setAccessible(false);
                    }
                    System.out.println(actionMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            sce.getServletContext().setAttribute("actionMap", actionMap);
            System.out.println("成功扫描指定包下面的所有类：");
            System.out.println(actionMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
        System.out.println("应用销毁...");
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
      /* Session is created. */
    }

    public void sessionDestroyed(HttpSessionEvent se) {
      /* Session is destroyed. */
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
    }
}
