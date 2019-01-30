package com.dabbler.generator.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class PropertiesUtilsTest {

    @Test
    public void getClassPath() {
        String rootPath = PropertiesUtils.getProjectBootPath();
        String pat = PropertiesUtils.getClassLoaderPath();
        log.info(rootPath);
        log.info(pat);
    }


}