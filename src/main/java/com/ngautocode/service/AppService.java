package com.ngautocode.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngautocode.model.App;
import com.ngautocode.model.Table;
import com.ngautocode.model.Variables;

@Service
public class AppService {
	
	@Autowired FileReadWrite fileReadWrite;
	@Autowired CounterService counterServ;
	@Autowired FolderZipper folderZipper;
	
	public void setAllModelValues(App app){		
		System.out.println("App creation started"+ counterServ.getIndex());
		// Seed folder copy and rename
		String appPath = copyDirectory(app, counterServ.getIndex());
		// Change theme file
		getThemeCss(app, appPath);
		// Components folders
		String srcComFormPath = appPath + Variables.componentSrcFormPath;
		String srcComListPath = appPath + Variables.componentSrcListPath;
		String srcModelPath = appPath + Variables.modelSrcPath;
		// Create components folder
		app.getTables().stream().forEach(table -> {
			// Copy folders for each table
			String tarComFormPath = appPath + Variables.compPath + table.getNameLowercase();
			String tarComListPath = appPath + Variables.compPath + table.getNameLowercase() + "s";
			String tarModelPath = appPath + Variables.modelPath + table.getNameLowercase();
			fileReadWrite.directoryCopyAndRename(srcComFormPath, tarComFormPath);
			fileReadWrite.directoryCopyAndRename(srcComListPath, tarComListPath);
			fileReadWrite.directoryCopyAndRename(srcModelPath, tarModelPath);
			// Change route file
			changeRouteFile(appPath, table);			
			// Add Component and service to app.module.ts
			changeModuleFile(appPath, table);
			// Change component files
			// First rename		
			String pathOfTargetComp = tarComFormPath+ "/"+ table.getNameLowercase();
			String pathOfTargetCompTs = pathOfTargetComp + Variables.comFile;
			new File(tarComFormPath+ Variables.comTs).renameTo(new File(pathOfTargetCompTs));
			String pathOfTargetCompHtml= pathOfTargetComp + Variables.htmlFile;
			new File(tarComFormPath+ Variables.comHtml).renameTo(new File(pathOfTargetCompHtml));
			String pathOfTargetCompScss= pathOfTargetComp + Variables.scssFile;
			new File(tarComFormPath+ Variables.comScss).renameTo(new File(pathOfTargetCompScss));
			String pathOfTargetListComp = tarComListPath+ "/"+ table.getNameLowercase();
			String pathOfTargetListCompTs = pathOfTargetListComp + Variables.comListFile;
			new File(tarComListPath+ Variables.listTs).renameTo(new File(pathOfTargetListCompTs));
			String pathOfTargetListCompHtml = pathOfTargetListComp + Variables.listHtmlFile;
			new File(tarComListPath+ Variables.listHtml).renameTo(new File(pathOfTargetListCompHtml));
			String pathOfTargetListCompScss = pathOfTargetListComp + Variables.listScssFile;
			new File(tarComListPath+ Variables.listScss).renameTo(new File(pathOfTargetListCompScss));
			String pathOfTargetModel = tarModelPath+ "/"+ table.getNameLowercase();
			String pathOfTargetModelTs = pathOfTargetModel + Variables.modelFile;
			new File(tarModelPath+ Variables.modelTs).renameTo(new File(pathOfTargetModelTs));
			String pathOfTargetServiceTs = pathOfTargetModel + Variables.serviceFile;
			new File(tarModelPath+ Variables.modelServiceTs).renameTo(new File(pathOfTargetServiceTs));
			// Change file contents
			//System.out.println("cc "+ pathOfTargetCompTs + " "+ Variables.modelName+ " "+ table.getName());
			fileReadWrite.replaceWordInAFile(pathOfTargetCompTs, Variables.modelName, table.getName());
			fileReadWrite.replaceWordInAFile(pathOfTargetCompTs, Variables.modelNameLowercase, table.getNameLowercase());
			fileReadWrite.replaceWordInAFile(pathOfTargetCompHtml, Variables.modelName, table.getName());
			fileReadWrite.replaceWordInAFile(pathOfTargetCompHtml, Variables.modelNameLowercase, table.getNameLowercase());
			fileReadWrite.replaceWordInAFile(pathOfTargetListCompTs, Variables.modelName, table.getName());
			fileReadWrite.replaceWordInAFile(pathOfTargetListCompTs, Variables.modelNameLowercase, table.getNameLowercase());
			fileReadWrite.replaceWordInAFile(pathOfTargetListCompHtml, Variables.modelName, table.getName());
			fileReadWrite.replaceWordInAFile(pathOfTargetListCompHtml, Variables.modelNameLowercase, table.getNameLowercase());
			fileReadWrite.replaceWordInAFile(pathOfTargetModelTs, Variables.modelName, table.getName());
			fileReadWrite.replaceWordInAFile(pathOfTargetModelTs, Variables.modelNameLowercase, table.getNameLowercase());
			table.getFields().stream().forEach(field -> {
				fileReadWrite.writeAfterLine(Paths.get(pathOfTargetModelTs), Variables.modelProperty, field.getFieldName() + "?: " + field.getType());
			});
			fileReadWrite.replaceWordInAFile(pathOfTargetServiceTs, Variables.modelName, table.getName());
			fileReadWrite.replaceWordInAFile(pathOfTargetServiceTs, Variables.modelNameLowercase, table.getNameLowercase());
			
		});
		// Delete source folders
		fileReadWrite.deleteDirectoryRecursively(srcComFormPath);
		fileReadWrite.deleteDirectoryRecursively(srcComListPath);
		fileReadWrite.deleteDirectoryRecursively(srcModelPath);
		folderZipper.pack(Paths.get(appPath), Paths.get(appPath+ ".zip"));
	}
	
	private void getThemeCss(App app,String appPath){
		switch (app.getCssFramework()) {
		case "material":
			changeTheme(app, appPath);
			break;
		case "primeng-seed":
			break;
		default:
			break;
		}
	}
	
	private String copyDirectory(App app, int folderIndex){
		String source = Variables.pathSrc + app.getType() + "/"+ folderIndex+ "/" +app.getCssFramework()+ "-seed";
		String target = Variables.pathTarget + app.getId();
		if(Files.exists(Paths.get(target))){
			fileReadWrite.deleteDirectoryRecursively(target);
			fileReadWrite.createEmptyDirectory(target);
		}
		else{
			fileReadWrite.createEmptyDirectory(target);
		}
		String appPath = target + "/" + app.getAppName();
		fileReadWrite.directoryCopyAndRename(source, appPath);
		return appPath;
	}
	
	private void changeTheme(App app,String appPath){
		String themeFilePath = appPath + Variables.themeFilePath;
		System.out.println("Theme path "+ themeFilePath);
		fileReadWrite.replaceWordInAFile(themeFilePath, Variables.primaryColor, app.getPrimaryColor());
		fileReadWrite.replaceWordInAFile(themeFilePath, Variables.primaryColorhue, app.getPrimaryhue());
		fileReadWrite.replaceWordInAFile(themeFilePath, Variables.accentColor, app.getSecondaryColor());
		fileReadWrite.replaceWordInAFile(themeFilePath, Variables.accentColorhue, app.getSecondaryhue());
	}
	
	private void changeRouteFile(String appPath, Table table){
		Path routeFile = Paths.get(appPath + Variables.routePath);
		fileReadWrite.writeAfterLine(routeFile, Variables.routeRoute, "{path: '" +table.getNameLowercase()+ "s', component: "+table.getName()+"sComponent},");
		fileReadWrite.writeAfterLine(routeFile, Variables.routeImport, "import { "+ table.getName()+"sComponent } from './components/"+ table.getNameLowercase() +"s/"+ table.getNameLowercase()+"s.component'");
		Path mainFile = Paths.get(appPath + Variables.mainRouteEntry);
		fileReadWrite.writeAfterLine(mainFile, Variables.mainRouteAdd, "{title: '"+ table.getName()+ "s', route: '/"+ table.getNameLowercase() +"s', icon: 'dashboard'},");

	}
	
	private void changeModuleFile(String appPath, Table table){
		Path appFile = Paths.get(appPath + Variables.modulePath);
		fileReadWrite.writeLinesAfterLine(appFile, Variables.moduleImport, new ArrayList<String>
			(Arrays.asList("import { "+ table.getName()+"Component } from './components/"+ table.getNameLowercase() +"/"+ table.getNameLowercase()+".component'",
						   "import { "+ table.getName()+"sComponent } from './components/"+ table.getNameLowercase() +"s/"+ table.getNameLowercase()+"s.component'",
						   "import { "+ table.getName()+ "Service } from './shared/"+ table.getNameLowercase() + "/" + table.getNameLowercase() + ".service'"
					))
		);
		fileReadWrite.writeLinesAfterLine(appFile, Variables.moduleDeclaration, new ArrayList<String>
		(Arrays.asList(table.getName()+"Component", table.getName()+"sComponent,")));
		fileReadWrite.writeAfterLine(appFile, Variables.moduleProvider, table.getName()+"Service,");
	}
}
