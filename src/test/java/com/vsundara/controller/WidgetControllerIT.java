
//Namespace
package com.vsundara.controller;

//Imports
import com.vsundara.Application;
import com.vsundara.domain.message.ErrorMessage;
import com.vsundara.domain.message.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Locale;

/**
 * Integration test / E2E testing for Widget Controller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
public class WidgetControllerIT {
    //Fields
    @Value("${local.server.port}")
    private int port;
    private String baseUrl;
    private RestTemplate restTemplate;

    @Resource
    private MessageSource messageSource;


    /**
     * Init method
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        this.baseUrl = "http://localhost:" + port + "/api/v1";
        this.restTemplate = new TestRestTemplate();
    }


    /**
     * Test filling the tank
     * @throws Exception
     */
    @Test
    public void test_fill_tank() throws Exception {

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();

        //Create the http request
        HttpEntity<?> request = new HttpEntity(requestHeaders);

        //Invoke the API service
        ResponseEntity<Message> fillResponse = restTemplate.exchange(baseUrl+"/fills/steam/coal/50", HttpMethod.POST, request, Message.class);

        Assert.assertEquals(HttpStatus.OK, fillResponse.getStatusCode());
        Assert.assertEquals(HttpStatus.OK, fillResponse.getBody().getStatus());
        Assert.assertEquals(messageSource.getMessage("engine.fill.success",null, Locale.getDefault()), fillResponse.getBody().getMessage());
    }


    /**
     * Test filling the tank with invalid quantity
     * @throws Exception
     */
    @Test
    public void test_fill_tank_invalid_quantity() throws Exception {

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();

        //Create the http request
        HttpEntity<?> request = new HttpEntity(requestHeaders);

        //Invoke the API service
        ResponseEntity<Message> fillResponse = restTemplate.exchange(baseUrl+"/fills/steam/wood/580", HttpMethod.POST, request, Message.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, fillResponse.getStatusCode());
        Assert.assertEquals(HttpStatus.BAD_REQUEST, fillResponse.getBody().getStatus());
        Assert.assertEquals(messageSource.getMessage("engine.fill.exception.fuel.level",null, Locale.getDefault()), fillResponse.getBody().getMessage());


        //Retry with another invalid scenario
        fillResponse = restTemplate.exchange(baseUrl+"/fills/internal/diesel/-30", HttpMethod.POST, request, Message.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, fillResponse.getStatusCode());
        Assert.assertEquals(HttpStatus.BAD_REQUEST, fillResponse.getBody().getStatus());
        Assert.assertEquals(messageSource.getMessage("engine.fill.exception.fuel.level",null, Locale.getDefault()), fillResponse.getBody().getMessage());
    }

    /**
     * Runs production emulation without fuel
     * @throws Exception
     */
    @Test
    public void test_emulates_empty() throws Exception {

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();

        //Create the http request
        HttpEntity<?> request = new HttpEntity(requestHeaders);

        //Invoke the API service
        ResponseEntity<ErrorMessage> response = restTemplate.exchange(baseUrl+"/emulate/internal/petrol/produces/50", HttpMethod.GET, request, ErrorMessage.class);

        Assert.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Assert.assertEquals(HttpStatus.CONFLICT, response.getBody().getStatus());
        Assert.assertEquals(messageSource.getMessage("engine.start.exception.fuel.level",null, Locale.getDefault()), response.getBody().getMessage());
    }


    /**
     * Runs production emulation with wrong wngine
     * @throws Exception
     */
    @Test
    public void test_emulates_wrong_engine() throws Exception {

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();

        //Create the http request
        HttpEntity<?> request = new HttpEntity(requestHeaders);

        //Invoke the API service
        ResponseEntity<ErrorMessage> response = restTemplate.exchange(baseUrl+"/emulate/internal/coal/produces/50", HttpMethod.GET, request, ErrorMessage.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getBody().getStatus());
        Assert.assertEquals(messageSource.getMessage("error.message.invalid.entity",null, Locale.getDefault()), response.getBody().getMessage());
    }

    /**
     * Runs production emulation without fuel
     * @throws Exception
     */
    @Test
    public void test_emulates() throws Exception {

        /**************   Fill the tank   *******************/

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();

        //Create the http request
        HttpEntity<?> request = new HttpEntity(requestHeaders);

        //Invoke the API service
        ResponseEntity<Message> fillResponse = restTemplate.exchange(baseUrl+"/fills/steam/coal/100", HttpMethod.POST, request, Message.class);


        /**************   Runs emulation   *******************/

        //Invoke the API service
        ResponseEntity<BigDecimal> response = restTemplate.exchange(baseUrl+"/emulate/steam/coal/produces/50", HttpMethod.GET, request, BigDecimal.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertTrue(response.getBody().doubleValue() > 0d);
    }




}
