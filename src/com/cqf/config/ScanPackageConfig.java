package com.cqf.config;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 78544 on 2016/12/5.
 */
public class ScanPackageConfig {

    private static Set packages = new HashSet();
    public Set getPackages() {
        packages.add("com.cqf.controller");
        return packages;
    }
}
