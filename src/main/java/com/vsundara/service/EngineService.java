
//Namespace
package com.vsundara.service;

import com.vsundara.domain.Engine;

/**
 * Class that handles operations regarding engine
 */
public interface EngineService {
    Engine start(Engine engine);
    Engine stop(Engine engine);
    Engine fill(Engine engine, int fuelLevel);
}
