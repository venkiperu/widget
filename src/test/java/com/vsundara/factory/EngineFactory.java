
//Namespace
package com.vsundara.factory;

//Imports
import com.vsundara.domain.Engine;
import com.vsundara.domain.InternalCombustionEngine;
import com.vsundara.domain.SteamEngine;
import com.vsundara.domain.constants.FuelType;

/**
 * Engine factory class
 */
public abstract class EngineFactory {

    /**
     * Retrieves a petrol engine instance
     * @return
     */
    public static Engine getPetrolEngine(){
        return new InternalCombustionEngine(FuelType.PETROL);
    }

    /**
     * Retrieves a diesel engine instance
     * @return
     */
    public static Engine getDieselEngine(){
        return new InternalCombustionEngine(FuelType.DIESEL);
    }

    /**
     * Retrieves a coal engine instance
     * @return
     */
    public static Engine getCoalEngine(){
        return new SteamEngine(FuelType.COAL);
    }

    /**
     * Retrieves a wood engine instance
     * @return
     */
    public static Engine getWoodEngine(){
        return new SteamEngine(FuelType.WOOD);
    }
}
