package com.dabbler.generator.handler;

import org.junit.Test;

import static org.junit.Assert.*;

public class GeneratorTest {

    @Test
    public void generate()throws Exception {
        Generator g = new Generator();
        g.generateByAllTable();
    }
}