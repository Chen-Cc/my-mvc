package com.cqf.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Created by 78544 on 2016/11/24.
 */
public class ScanPackage {
    public Set<Class> scan(String packageName) throws IOException {
        Set<Class> classSet = new HashSet<Class>();
//        String packageName = "com.cqf.controller";
        String packageDirName = packageName.replace(".", "/");
        boolean recursive = true;

        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packageDirName);

        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            if ("file".equals(url.getProtocol())) {
                String filePath = URLDecoder.decode(url.getFile(), "utf-8");
                System.out.println("filePath=" + filePath);
                findClasses(packageName, filePath, recursive, classSet);
            }
        }
        return classSet;

    }

    private void findClasses(String packageName, String filePath, final boolean recursive, Set<Class> classSet) {
        File file = new File(filePath);
        if (!file.isDirectory() || !file.exists()) {
            System.out.println("啥也没有。。。");
            return;
        }

        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return ( recursive || pathname.isDirectory())
                        || (pathname.getName().endsWith(".class"));
            }
        });
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    System.out.println("这里是目录" + f.getName());
                    findClasses(packageName + "." + f.getName(), filePath + "/" + f.getName(), recursive, classSet);
                } else {
                    String fileName = packageName + "." + f.getName().substring(0, f.getName().length() - 6);
                    System.out.println("文件名：" + fileName);
                    try {
                        classSet.add(Thread.currentThread().getContextClassLoader().loadClass(fileName));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("目录下面没有文件或文件夹");
        }
    }

    public static void main(String[] args) throws IOException {
        ScanPackage scanPackage = new ScanPackage();
        System.out.println(scanPackage.scan("com.cqf.controller"));
    }
}
