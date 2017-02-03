package com.ngautocode.model;

import java.util.List;

public class Table {
	
    private String id;
    private String name;
    private String nameLowercase;
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
