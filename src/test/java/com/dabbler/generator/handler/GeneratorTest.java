package com.dabbler.generator.handler;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GeneratorTest {

    Generator g;
    @Before
    public void get(){
        g = new Generator();
    }

    @Test
    public void generate()throws Exception {
        g.generateByAllTable();
    }

    @Test
    public void getTempalteFile(){

    }
}