package com.dabbler.generator;

import com.dabbler.generator.handler.Generator;

public class GenerateFacade {

    private Generator g ;

    public void init(){
        g = new Generator();
    }

    public void generatorByTable() throws Exception{
        g.generateByAllTable();
    }

}
