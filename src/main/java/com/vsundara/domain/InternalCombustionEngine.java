
//Namespace
package com.vsundara.domain;

import com.vsundara.domain.constants.EngineType;
import com.vsundara.domain.constants.FuelType;
import org.springframework.stereotype.Component;

/**
 * Class that represents an InternalCombustionEngine
 */
@Component
public class InternalCombustionEngine extends Engine{

    /**
     * Constructor
     */
    private InternalCombustionEngine(){}

    /**
     * Constructor
     * @param fuelType
     */
    public InternalCombustionEngine(FuelType fuelType) {
        super(fuelType);
        this.engineType = EngineType.INTERNAL_COMBUSTION;
        this.requiredFuelType = new FuelType[]{FuelType.DIESEL, FuelType.PETROL};
    }






}
