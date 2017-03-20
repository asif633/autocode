package com.ngautocode.exceptions;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Server file creation error")
public class ServerFileNotFoundException extends FileNotFoundException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6476569650630744298L;

}
