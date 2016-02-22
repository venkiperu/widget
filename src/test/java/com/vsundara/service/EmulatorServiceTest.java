
//Namespace
package com.vsundara.service;

//Imports
import com.vsundara.Application;
import com.vsundara.factory.EngineFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Unit tests for class IdeaService
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EmulatorServiceTest {

    //Fields
    @Resource
    private EmulatorService emulatorService;


    /**
     * Scenario to calculate production run for petrol engine
     * @throws Exception
     */
    @Test
    public void test_produce() throws Exception {
        Assert.assertTrue(emulatorService.produce(EngineFactory.getPetrolEngine(), 50).doubleValue() == 63d);
        Assert.assertTrue(emulatorService.produce(EngineFactory.getDieselEngine(), 50).doubleValue() == 84d);
        Assert.assertTrue(emulatorService.produce(EngineFactory.getCoalEngine(), 50).doubleValue() == 141.25);
        BigDecimal bd = emulatorService.produce(EngineFactory.getWoodEngine(), 50);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        Assert.assertTrue(bd.doubleValue() == 108.75);
    }
}
