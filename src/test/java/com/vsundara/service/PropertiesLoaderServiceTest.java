//Namespace
package com.vsundara.service;

//Imports
import com.vsundara.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Unit tests
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PropertiesLoaderServiceTest {

    //Fields
    @Resource
    private PropertiesLoaderService propertiesLoaderService;

    @Test
    public void test_getMessage() throws Exception {
        String message = propertiesLoaderService.getMessage("error.message.info");
        Assert.assertEquals("Please refer to the Widget API documentation for more Information", message);
    }

    @Test

    public void test_getProperty() throws Exception {
        String property = propertiesLoaderService.getProperty("internal.combustion.batch");
        Assert.assertEquals("8", property);
    }
}
