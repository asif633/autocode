package com.ngautocode.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ngautocode.model.App;
import com.ngautocode.service.AppService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AppRestController {
	
	@Autowired
	AppService appServ;
	
	@RequestMapping(method=RequestMethod.POST,path="/appGenerate")
	public String generateApp(@RequestBody App app){
		appServ.setAllModelValues(app);
		return "Done";
	}
}
