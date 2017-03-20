package com.ngautocode.model;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class Table {
	
    private String id;
    @NotBlank(message ="Table name can not be blank!")
    private String name;
    @NotBlank(message ="Table lowercase name can not be blank!")
    private String nameLowercase;
    @NotEmpty(message = "There must be some fields of a table!")
    private List<Field> fields;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameLowercase() {
		return nameLowercase;
	}
	public void setNameLowercase(String nameLowercase) {
		this.nameLowercase = nameLowercase;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
    
}
