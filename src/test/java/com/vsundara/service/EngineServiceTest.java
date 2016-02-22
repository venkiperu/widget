
//Namespace
package com.vsundara.service;

//Imports
import com.vsundara.Application;
import com.vsundara.domain.Engine;
import com.vsundara.domain.constants.FuelType;
import com.vsundara.exception.EngineException;
import com.vsundara.factory.EngineFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Unit tests for class IdeaService
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EngineServiceTest {

    //Fields
    @Resource
    private EngineService engineService;


    /**
     * Scenario when users fill an engine with a valid quantity as parameter
     * @throws Exception
     */
    @Test
    public void test_fill_success() throws Exception {
        Engine engine = EngineFactory.getPetrolEngine();
        engine = engineService.fill(engine, 50);
        Assert.assertEquals(50, engine.getFuelLevel());
    }


    /**
     * Scenario when users fill an engine with an invalid quantity as parameter
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_fill_wrong_parameter_1() throws Exception {
        Engine engine = EngineFactory.getPetrolEngine();
        engine = engineService.fill(engine, -50);
    }


    /**
     * Scenario when users fill an engine with an invalid quantity as parameter
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_fill_wrong_parameter_2() throws Exception {
        Engine engine = EngineFactory.getPetrolEngine();
        engine = engineService.fill(engine, 500);
    }




    /**
     * Scenario when user tries to start an engine with 0 level of fuel
     * @throws Exception
     */
    @Test(expected = EngineException.class)
    public void test_start_invalid_fuel() throws Exception {
        engineService.start(EngineFactory.getPetrolEngine());
    }

    /**
     * Scenario when user starts an engine previous filled
     * @throws Exception
     */
    @Test
    public void test_start_success() throws Exception {
        //Get the petrol instance
        Engine engine = EngineFactory.getPetrolEngine();

        //Fill the engine
        engine = engineService.fill(engine, 50);

        //Ensure engine is not running
        Assert.assertFalse(engine.isRunning());

        //Tries to start engine
        engine = engineService.start(engine);

        //Ensure engine is running
        Assert.assertTrue(engine.isRunning());
    }


    /**
     * Scenario when user tries to start an invalid engine
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_start_invalid_engine() throws Exception {
        //Get the instance
        Engine engine = EngineFactory.getPetrolEngine();

        //Set an invalid fuelType
        engine.setFuelType(FuelType.COAL);

        //Try to start engine
        engineService.start(engine);
    }


    /**
     * Scenario when tries to stop an engine
     * @throws Exception
     */
    @Test
    public void test_stop_engine() throws Exception {

        //Get the petrol instance
        Engine engine = EngineFactory.getPetrolEngine();

        //Fill the engine
        engine = engineService.fill(engine, 50);

        //Ensure engine is not running
        Assert.assertFalse(engine.isRunning());

        //Tries to start engine
        engine = engineService.start(engine);

        //Ensure engine is running
        Assert.assertTrue(engine.isRunning());

        //Tries to start engine
        engine = engineService.stop(engine);

        //Ensure engine is running
        Assert.assertFalse(engine.isRunning());

    }
}
