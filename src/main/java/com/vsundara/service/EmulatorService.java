
//Namespace
package com.vsundara.service;

//Imports
import com.vsundara.domain.Engine;

import java.math.BigDecimal;

/**
 * Class that handles emulation operations
 */
public interface EmulatorService {
    BigDecimal produce(Engine engine, int quantity);
}
