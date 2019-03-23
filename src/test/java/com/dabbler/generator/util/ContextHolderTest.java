package com.dabbler.generator.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

@Slf4j
public class ContextHolderTest {

    @Test
    public void getProperties() {
      Properties properties =  ContextHolder.getProperties();
      System.out.println(properties);
    }
}