package com.ngautocode.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ngautocode.exceptions.ServerFileNotFoundException;
import com.ngautocode.exceptions.ServerIoException;
import com.ngautocode.model.App;
import com.ngautocode.model.Variables;
import com.ngautocode.service.AppService;
import com.ngautocode.service.CounterService;
import com.ngautocode.validators.ValidationError;
import com.ngautocode.validators.ValidationErrorBuilder;

@RestController
@CrossOrigin(origins = "*")
public class AppRestController {
	
	@Autowired
	AppService appServ;
	@Autowired
	CounterService counterServ;
	
	@RequestMapping(method=RequestMethod.POST,path="/appGenerate")
	public ResponseEntity<String> generateApp(@Valid @RequestBody App app) throws ServerFileNotFoundException, ServerIoException{
        if (app == null) {
            throw new IllegalArgumentException("The app request body must not be null or empty");
        }
		appServ.setAllModelValues(app);
		return new ResponseEntity<String>("App creation done click on the download link to get it", HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST,path="/downloadApp", produces="application/zip")
	public @ResponseBody HttpEntity<byte[]> downloadApp(HttpServletResponse response,@Valid @RequestBody App app) throws IOException{
		File file = new File(Variables.pathTarget + app.getId()+"/"+app.getAppName()+ ".zip");

	        byte[] document = FileCopyUtils.copyToByteArray(file);

	        HttpHeaders header = new HttpHeaders();
	        header.setContentType(new MediaType("application", "zip"));
	        header.set("Content-Disposition", "inline; filename=" + file.getName());
	        header.setContentLength(document.length);
	        return new HttpEntity<byte[]>(document, header);
	}
	
    @ModelAttribute("BeforeRequest")
    public void BeforeRequest(HttpServletRequest request) {
        if (request.getRequestURI().contains("appGenerate")) {
        	if(counterServ.getIndex() == 10){
        		counterServ.setIndex(1);
        	}
        	else{
        	counterServ.setIndex(counterServ.getIndex() + 1);
        	}
        }
    }
    
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationError handleException(MethodArgumentNotValidException exception) {
        return createValidationError(exception);
    }

    private ValidationError createValidationError(MethodArgumentNotValidException e) {
        return ValidationErrorBuilder.fromBindingErrors(e.getBindingResult());
    }
    
   
}
