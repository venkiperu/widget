//Namespace
package com.vsundara.controller;

//Imports

import com.vsundara.domain.message.ErrorMessage;
import com.vsundara.service.PropertiesLoaderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles that handles common exceptions of all controllers
 */
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Resource
	private PropertiesLoaderService propertiesLoaderService;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
		List<String> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
		String error;
		for (FieldError fieldError : fieldErrors) {
			error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
			errors.add(error);
		}
		for (ObjectError objectError : globalErrors) {
			error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
			errors.add(error);
		}
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage(propertiesLoaderService.getMessage("error.message.invalid.entity"));
		errorMessage.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
		errorMessage.setFieldErrors(errors);
		errorMessage.setMoreInfo(propertiesLoaderService.getMessage("error.message.info"));

		return new ResponseEntity(errorMessage, headers, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String unsupported = "Unsupported content type: " + ex.getContentType();
		String supported = "Supported content types: " + MediaType.toString(ex.getSupportedMediaTypes());
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage(unsupported + ".\n" + supported);
		return new ResponseEntity(errorMessage, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Throwable mostSpecificCause = ex.getMostSpecificCause();
		ErrorMessage errorMessage = new ErrorMessage();
		if (mostSpecificCause != null) {
			errorMessage.setMessage(mostSpecificCause.getMessage());
			errorMessage.setMoreInfo("Exception name: " + mostSpecificCause.getClass().getName());

		} else {
			errorMessage.setMessage(ex.getMessage());
		}
		return new ResponseEntity(errorMessage, headers, status);
	}

}