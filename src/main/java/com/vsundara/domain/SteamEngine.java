
//Namespace
package com.vsundara.domain;

//Imports
import com.vsundara.domain.constants.EngineType;
import com.vsundara.domain.constants.FuelType;

/**
 * Class that represents a SteamEngine
 */
public class SteamEngine extends Engine {


    /**
     * Constructor
     */
    private SteamEngine(){}


    /**
     * Constructor
     * @param fuelType
     */
    public SteamEngine(FuelType fuelType) {
        super(fuelType);
        this.engineType = EngineType.STEAM;
        this.requiredFuelType = new FuelType[]{FuelType.COAL, FuelType.WOOD};
    }

}
