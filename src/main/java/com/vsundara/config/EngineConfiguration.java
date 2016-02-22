
//Namespace
package com.vsundara.config;

//Imports
import com.vsundara.domain.InternalCombustionEngine;
import com.vsundara.domain.SteamEngine;
import com.vsundara.domain.constants.FuelType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * The central Configuration class for the different engines
 */

@Configuration
public class EngineConfiguration {


    @Bean
    @Primary
    public InternalCombustionEngine petrolEngine() {
        InternalCombustionEngine internalCombustionEngine = new InternalCombustionEngine(FuelType.PETROL);
        return internalCombustionEngine;
    }


    @Bean
    public InternalCombustionEngine dieselEngine() {
        InternalCombustionEngine internalCombustionEngine = new InternalCombustionEngine(FuelType.DIESEL);
        return internalCombustionEngine;
    }



    @Bean
    @Primary
    public SteamEngine coalEngine() {
        SteamEngine steamEngine = new SteamEngine(FuelType.COAL);
        return steamEngine;
    }


    @Bean
    public SteamEngine woodEngine() {
        SteamEngine steamEngine = new SteamEngine(FuelType.WOOD);
        return steamEngine;
    }

}
