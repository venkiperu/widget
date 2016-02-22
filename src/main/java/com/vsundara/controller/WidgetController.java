
//Namesapce
package com.vsundara.controller;

//Imports
import com.vsundara.domain.Engine;
import com.vsundara.domain.InternalCombustionEngine;
import com.vsundara.domain.SteamEngine;
import com.vsundara.domain.message.ErrorMessage;
import com.vsundara.domain.message.Message;
import com.vsundara.domain.message.SuccessMessage;
import com.vsundara.exception.EngineException;
import com.vsundara.service.EmulatorService;
import com.vsundara.service.EngineService;
import com.vsundara.service.PropertiesLoaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Controller class that handles API calls for widget.
 */
@RestController
@RequestMapping("/api/v1")
public class WidgetController {

    @Resource(name = "coalEngine")
    private SteamEngine coalEngine;

    @Resource(name = "woodEngine")
    private SteamEngine woodEngine;

    @Resource(name = "petrolEngine")
    private InternalCombustionEngine petrolEngine;

    @Resource(name = "dieselEngine")
    private InternalCombustionEngine dieselEngine;

    @Resource
    private EngineService engineService;

    @Resource
    private EmulatorService emulatorService;

    @Resource
    private PropertiesLoaderService propertiesLoaderService;


    /**
     * Method to fill the the fuel(s) along with the amount of quantity
     *
     * @endpoint {POST} /api/v1/fills/{engineType}/{fuelType}/{quantity}
     * @return
     */
    @RequestMapping(
            value = "/fills/{engineType}/{fuelType}/{quantity}",
            method = {RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Message> fills(@PathVariable final String engineType, @PathVariable final String fuelType, @PathVariable final int quantity) {

        if (engineType.equalsIgnoreCase("internal") && fuelType.equalsIgnoreCase("petrol")) {
            petrolEngine = (InternalCombustionEngine) engineService.fill(petrolEngine, quantity);
        }
        else if (engineType.equalsIgnoreCase("internal") && fuelType.equalsIgnoreCase("diesel")) {
            dieselEngine = (InternalCombustionEngine) engineService.fill(dieselEngine, quantity);
        }
        else if (engineType.equalsIgnoreCase("steam") && fuelType.equalsIgnoreCase("wood")) {
            woodEngine = (SteamEngine) engineService.fill(woodEngine, quantity);
        }
        else if (engineType.equalsIgnoreCase("steam") && fuelType.equalsIgnoreCase("coal")) {
            coalEngine = (SteamEngine) engineService.fill(coalEngine, quantity);
        }
        else {
            throw new IllegalArgumentException(propertiesLoaderService.getMessage("error.message.invalid.entity"));
        }

        //Return message
        SuccessMessage Message = new SuccessMessage(HttpStatus.OK, propertiesLoaderService.getMessage("engine.fill.success"));
        return new ResponseEntity<>(Message, HttpStatus.OK);
    }


    /**
     * Method to alculate the  production cost
     *
     * @endpoint {GET} /api/v1/emulate/{engineType}/{fuelType}/produces/{quantity}
     * @return
     */
    @RequestMapping(
            value = "/emulate/{engineType}/{fuelType}/produces/{quantity}",
            method = {RequestMethod.GET},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<BigDecimal> emulation(@PathVariable final String engineType, @PathVariable final String fuelType, @PathVariable final int quantity) {

        BigDecimal cost;

        if (engineType.equalsIgnoreCase("internal") && fuelType.equalsIgnoreCase("petrol")) {
            cost = runsEmulation(petrolEngine, quantity);
        }
        else if (engineType.equalsIgnoreCase("internal") && fuelType.equalsIgnoreCase("diesel")) {
            cost = runsEmulation(dieselEngine, quantity);
        }
        else if (engineType.equalsIgnoreCase("steam") && fuelType.equalsIgnoreCase("wood")) {
            cost = runsEmulation(woodEngine, quantity);
        }
        else if (engineType.equalsIgnoreCase("steam") && fuelType.equalsIgnoreCase("coal")) {
            cost = runsEmulation(coalEngine, quantity);
        }
        else {
            throw new IllegalArgumentException(propertiesLoaderService.getMessage("error.message.invalid.entity"));
        }

        //Return cost
        return new ResponseEntity<>(cost, HttpStatus.OK);
    }

    /**
     * local utility method
     *
     * @param engine
     * @param quantity
     * @return
     */
    private BigDecimal runsEmulation(Engine engine, int quantity) {

        BigDecimal cost;
        engine = engineService.start(engine);
        cost = emulatorService.produce(engine, quantity);
        engine = engineService.stop(engine);

        return cost;
    }


    /**
     * Error handle for EngineException
     *
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(EngineException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public Message engineExceptionHandler(final EngineException exception) throws Exception
    {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(exception.getMessage());
        errorMessage.setStatus(HttpStatus.CONFLICT);
        errorMessage.setMoreInfo(propertiesLoaderService.getMessage("error.message.info"));
        return errorMessage;
    }

    /**
     * Error handle for IllegalArgumentException
     *
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Message illegalArgumentExceptionHandler(final IllegalArgumentException exception) throws Exception
    {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(exception.getMessage());
        errorMessage.setStatus(HttpStatus.BAD_REQUEST);
        errorMessage.setMoreInfo(propertiesLoaderService.getMessage("error.message.info"));
        return errorMessage;
    }


    /**
     * Error handle for unexpected exception
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody ErrorMessage handleException(Exception e) {
        //Create the message
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        //Return the message
        return errorMessage;
    }
}
