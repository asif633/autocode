package com.ngautocode.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Server could not build app some error happened")
public class ServerIoException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6223460603899481359L;

}
