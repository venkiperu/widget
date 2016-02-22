
//Namespace
package com.vsundara.service.impl;

//Imports
import com.vsundara.domain.Engine;
import com.vsundara.exception.EngineException;
import com.vsundara.service.EngineService;
import com.vsundara.service.PropertiesLoaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * Class that hadndles engine operations
 */
@Service
public class EngineServiceImpl implements EngineService {


    //The LOG
    private static final Logger LOGGER = LoggerFactory.getLogger(EngineServiceImpl.class);

    //Fields
    @Resource
    private PropertiesLoaderService propertiesLoaderService;

    /**
     * Starts the engine
     * @param engine
     * @return
     */
    @Override
    public Engine start(Engine engine) {

        LOGGER.info("Starting engine of Type [{}] [{}]", engine.getEngineType(), engine.getFuelType());

        //Verify conditions

        if (Arrays.asList(engine.getRequiredFuelType()).indexOf(engine.getFuelType()) == -1) {
            LOGGER.error("Unable to start engine, Engine of Type  [{}] does not support [{}] fuel type", engine.getEngineType(), engine.getFuelType());
            throw new IllegalArgumentException(propertiesLoaderService.getMessage("engine.start.exception.fuel.type"));
        }

        if(engine.getFuelLevel() <= 0) {
            LOGGER.warn("Unable to start engine of Type [{}] [{}]; fuel level [{}] not valid", engine.getEngineType(), engine.getFuelType(), engine.getFuelLevel());
            throw new EngineException(propertiesLoaderService.getMessage("engine.start.exception.fuel.level"));
        }

        //Put engine in running mode
        engine.setRunning(true);

        LOGGER.info("Engine of Type [{}] [{}] started successfully", engine.getEngineType(), engine.getFuelType());

        return engine;
    }

    /**
     * Stops the engine
     * @param engine
     * @return
     */
    @Override
    public Engine stop(Engine engine) {
        LOGGER.info("Stopping engine of Type [{}] [{}]", engine.getEngineType(), engine.getFuelType());

        //Removes engine from running mode
        engine.setRunning(false);

        LOGGER.info("Engine of Type [{}] [{}] stopped successfully", engine.getEngineType(), engine.getFuelType());
        return engine;
    }

    /**
     * Fill the engine
     * @param engine
     * @param fuelLevel
     * @return
     */
    @Override
    public Engine fill(Engine engine, int fuelLevel) {
        LOGGER.info("Filling engine of Type [{}] [{}] with quantity [{}]", engine.getEngineType(), engine.getFuelType(), fuelLevel);

        if ( fuelLevel >= 0 && fuelLevel <= 100) {
            engine.setFuelLevel(fuelLevel);
        }
        else {
            LOGGER.error("Unable fill Engine of Type [{}] [{}]. Quantity [{}] not valid", engine.getEngineType(), engine.getFuelType(), fuelLevel);
            throw new IllegalArgumentException(propertiesLoaderService.getMessage("engine.fill.exception.fuel.level"));
        }

        LOGGER.info("Engine of Type [{}] [{}] filled with quantity [{}] successfully", engine.getEngineType(), engine.getFuelType(), fuelLevel);
        return engine;
    }
}
