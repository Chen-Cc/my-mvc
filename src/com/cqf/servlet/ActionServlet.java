package com.cqf.servlet;

import com.cqf.annotation.Param;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * Created by 78544 on 2016/11/24.
 */
public class ActionServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        if (requestURI.endsWith(".do")) {
//            System.out.println(requestURI.substring(0, requestURI.length() - 3));
            Map<String, String> actionMap = (Map<String, String>) this.getServletContext().getAttribute("actionMap");
//            System.out.println(actionMap);
            String actionClass = actionMap.get(requestURI.substring(0, requestURI.length() - 3));
            if (null == actionClass) {
                try {
                    throw new NoSuchMethodException("NoSuchMethodException");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    response.getWriter().print(e);
                }
                return;
            }
            String[] actionClasses = actionClass.split("\\|");
            String className = actionClasses[0];
            String methodName = actionClasses[1];

            try {
                Class clazz = Class.forName(className);
                Object instance = clazz.newInstance();
//                Method method = clazz.getDeclaredMethod(methodName);

                Class entityClass = null;
                Object classObj = null;
                String value = null;
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (methodName.equals(method.getName())) {
                        Parameter[] parameters = method.getParameters();
                        Object[] objects = new Object[parameters.length];
                        int i = 0;
                        for (Parameter parameter : parameters) {
                            String paramName = parameter.getName();
                            System.out.println("paramName=" + paramName);

                            String typeName = parameter.getParameterizedType().getTypeName();
                            System.out.println("typeName=" + typeName);
                            //有注解的参数
                            if (parameter.isAnnotationPresent(Param.class)) {
                                Param parameterAnnotation = parameter.getAnnotation(Param.class);
                                paramName = parameterAnnotation.value();
                                value = request.getParameter(paramName);
                                System.out.println("value=" + value);
                                objects[i]= typeTransport(typeName, value);
                                System.out.println("objects[i]=" + objects[i].toString());
                            } else {
                                if ("javax.servlet.http.HttpServletRequest".equals(typeName)) {
                                    objects[i] = request;
                                } else if ("javax.servlet.http.HttpServletResponse".equals(typeName)) {
                                    objects[i] = response;
                                } else {
                                    entityClass = Class.forName(typeName);
                                    classObj = entityClass.newInstance();
                                    Field[] fields = entityClass.getDeclaredFields();
                                    for (Field field : fields) {
                                        value = request.getParameter(field.getName());
                                        typeName = field.getType().getName();
                                        System.out.println("typeName=" + typeName + " " + field.getName() + "=" + value);
                                        field.setAccessible(true);
                                        field.set(classObj, typeTransport(typeName, value));
                                        field.setAccessible(false);
                                    }
                                    objects[i] = classObj;
                                }
                            }
                            i++;
                        }
                        Object invoke = method.invoke(instance, objects);
                        System.out.println("invoke=" +invoke.toString());
                        response.getWriter().print(invoke);
                        break;
                    }
                }
                return;
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        } else {
            response.getWriter().print("your request is invalid, check the url whether if end with '.do'" + request.getRequestURI());
            return;
        }

        response.getWriter().print("your method is " + request.getMethod() + " | your request uri is " + request.getRequestURI());
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        this.doPost(request, response);
    }

    private static Object typeTransport(String type, String value) {
        switch (type) {
            case "java.lang.Byte":
                return Byte.valueOf(value);
            case "java.lang.Short ":
                return Short.valueOf(value);
            case "java.lang.Integer":
                return Integer.parseInt(value);
            case "java.lang.Long":
                return Long.valueOf(value);
            case "java.lang.Float":
                return Float.valueOf(value);
            case "java.lang.Double":
                return Double.valueOf(value);
            case "java.lang.Character":
                return value.charAt(0);
            case "int":
                return Integer.valueOf(value);
            case "float":
                return Float.valueOf(value);
            case "double":
                return Double.valueOf(value);
            default:
                return value;
        }
    }

}
