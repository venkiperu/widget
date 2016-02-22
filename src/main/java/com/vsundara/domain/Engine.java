
//Namespace
package com.vsundara.domain;

//Imports
import com.vsundara.domain.constants.EngineType;
import com.vsundara.domain.constants.FuelType;

/**
 * Class that represents engine entity
 */
public abstract class Engine {

    //Fields
    protected boolean running;
    protected int fuelLevel;

    protected FuelType[] requiredFuelType;
    protected FuelType fuelType;
    protected EngineType engineType;

    /**
     * Default constructor
     */
    protected Engine(){}

    /**
     * Constructor
     * @param fuelType
     */
    protected Engine(FuelType fuelType) {
        this.fuelLevel = 0;
        this.running = false;
        this.fuelType = fuelType;
    }


    //Getters and Setters

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(int fuelLevel) {
        this.fuelLevel = fuelLevel;
    }


    public FuelType[] getRequiredFuelType() {
        return requiredFuelType;
    }

    public void setRequiredFuelType(FuelType[] requiredFuelType) {
        this.requiredFuelType = requiredFuelType;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }
}
