
//Namespace
package com.vsundara.service.impl;

//Imports
import com.vsundara.domain.Engine;
import com.vsundara.domain.constants.FuelType;
import com.vsundara.service.EmulatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Class that handles emulator operations
 */
@Service
public class EmulatorServiceImpl implements EmulatorService {

    //The LOG
    private static final Logger LOGGER = LoggerFactory.getLogger(EmulatorServiceImpl.class);


    //Fields
    @Value("${petrol.cost.per.batch}")
    private double petrolCostPerBatch;

    @Value("${diesel.cost.per.batch}")
    private double dieselCostPerBatch;

    @Value("${wood.cost.per.batch}")
    private double woodCostPerBatch;

    @Value("${coal.cost.per.batch}")
    private double coalCostPerBatch;

    @Value("${internal.combustion.batch}")
    private int combustionEngineBatchSize;

    @Value("${steam.batch}")
    private int steamBatchSize;


    /**
     * Retrieves the cost of the engine production run
     * @param engine
     * @param quantity
     * @return
     */
    @Override
    public BigDecimal produce(Engine engine, int quantity) {

        LOGGER.info("Calculation the production run for engine [{}][{}] with quantity [{}]", engine.getEngineType(), engine.getFuelType(), quantity);

        //Variables
        int batch = 0;
        int batchCount = 0;
        int batchSize = 0;
        double costPerBatch=0d;
        BigDecimal total;

        //Set variables
        if (engine.getFuelType() == FuelType.PETROL) {
            costPerBatch = petrolCostPerBatch;
            batchSize = combustionEngineBatchSize;

        } else if (engine.getFuelType() == FuelType.DIESEL) {
            costPerBatch = dieselCostPerBatch;
            batchSize = combustionEngineBatchSize;
        }
        else if (engine.getFuelType() == FuelType.COAL) {
            costPerBatch = coalCostPerBatch;
            batchSize = steamBatchSize;
        }
        else if (engine.getFuelType() == FuelType.WOOD) {
            costPerBatch = woodCostPerBatch;
            batchSize = steamBatchSize;
        }

        //Calculate batch
        while (batch < quantity) {
            batch = batch + batchSize;
            batchCount++;
        }

        //Calculate total
        total = new BigDecimal(batchCount * costPerBatch);
        LOGGER.info("The production run for engine [{}][{}] with quantity [{}] is [£{}]", engine.getEngineType(), engine.getFuelType(), quantity, total);

        return total;
    }
}
