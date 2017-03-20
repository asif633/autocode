package com.ngautocode.model;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;


public class App {
	
	@NotBlank(message = "Id must not be blank!")
    private String id;
	@NotBlank(message = "App Name must not be blank!")
    private String appName;
    private String type;
    private String cssFramework;
    private String theme;
    private String primaryColor;
    private String primaryhue;
    private String secondaryColor;
    private String secondaryhue;
    @NotEmpty(message = "There must be some tables!")
    private List<Table> tables;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCssFramework() {
		return cssFramework;
	}
	public void setCssFramework(String cssFramework) {
		this.cssFramework = cssFramework;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getPrimaryColor() {
		return primaryColor;
	}
	public void setPrimaryColor(String primaryColor) {
		this.primaryColor = primaryColor;
	}
	public String getPrimaryhue() {
		return primaryhue;
	}
	public void setPrimaryhue(String primaryhue) {
		this.primaryhue = primaryhue;
	}
	public String getSecondaryColor() {
		return secondaryColor;
	}
	public void setSecondaryColor(String secondaryColor) {
		this.secondaryColor = secondaryColor;
	}
	public String getSecondaryhue() {
		return secondaryhue;
	}
	public void setSecondaryhue(String secondaryhue) {
		this.secondaryhue = secondaryhue;
	}
	public List<Table> getTables() {
		return tables;
	}
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	
	@Override
	public String toString() {
		return "App [id=" + id + ", appName=" + appName + ", type=" + type + ", cssFramework=" + cssFramework
				+ ", theme=" + theme + ", primaryColor=" + primaryColor + ", primaryhue=" + primaryhue
				+ ", secondaryColor=" + secondaryColor + ", secondaryhue=" + secondaryhue + ", tables=" + tables + "]";
	}
       
}
