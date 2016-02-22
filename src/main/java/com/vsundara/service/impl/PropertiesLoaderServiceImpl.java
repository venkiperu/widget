
//Namespace
package com.vsundara.service.impl;

//Imports

import com.vsundara.service.PropertiesLoaderService;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * Class that handles application properties/messages
 */
@Service
public class PropertiesLoaderServiceImpl implements PropertiesLoaderService {

    //Fields
    private static final String DEFAULT_VALUE = "{%s}";

	@Resource
	private Environment environment;

	@Resource
	private MessageSource messageSource;


    /**
     *  Returns the value for the given property key.
     *
     * @param key
     * @return
     */
	@Override
	public String getMessage(final String key) {
		return getMessage(key, (Object) null);
	}

	private String getMessage(final String key, final Object... args) {
		return messageSource.getMessage(key, args, String.format(DEFAULT_VALUE, key), Locale.UK);
	}


    /**
     *  Returns value of the property of the given key.
     *
     * @param key
     * @return
     */
	@Override
	public String getProperty(final String key) {
        return environment.getProperty(key);
	}
}
