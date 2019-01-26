package com.dabbler.generator.common.utils;

import com.dabbler.generator.entity.db.Table;
import com.dabbler.generator.handler.Generator;
import org.junit.Test;

public class BeanHelperTest {

    @Test
    public void descibe() {
        Generator generator = new Generator();
        Table table = new Table();
        BeanHelper.descibe(table);
    }

    @Test
    public void getPropertyDescriptors() {
    }
}