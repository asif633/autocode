package com.ngautocode.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngautocode.model.App;

@Service
public class AppService {
	
	@Autowired FileReadWrite fileReadWrite;
	
	public void setAllModelValues(App app){
		System.out.println("App creation started");
		// Seed folder copy and rename
		Path source = Paths.get("A:/dev/autocode/covalent-seed");
		Path target = Paths.get("A:/dev/generatedApps/"+ app.getAppName());
		if(Files.exists(target)){
			fileReadWrite.deleteDirectoryRecursively(target);
			target = Paths.get("A:/dev/generatedApps/"+ app.getAppName());
		}
		fileReadWrite.folderCopyAndRename(source, target);
		// Change theme file
		Path themeFile = Paths.get("A:/dev/generatedApps/"+ app.getAppName()+ "/src/theme.scss");
		fileReadWrite.replaceWordInAFile(themeFile, "teal", app.getPrimaryColor());
		fileReadWrite.replaceWordInAFile(themeFile, "700", app.getPrimaryhue());
		fileReadWrite.replaceWordInAFile(themeFile, "deep-orange", app.getSecondaryColor());
		fileReadWrite.replaceWordInAFile(themeFile, "800", app.getSecondaryhue());
		// Components folders
		Path comFormSrcPath = Paths.get("A:/dev/generatedApps/"+ app.getAppName()+ "/src/app/components/demo");
		Path comListSrcPath = Paths.get("A:/dev/generatedApps/"+ app.getAppName()+ "/src/app/components/demos");
		Path modelSrcPath = Paths.get("A:/dev/generatedApps/"+ app.getAppName()+ "/src/app/shared/demo");
		// Create components folder
		app.getTables().stream().forEach(table -> {
			// Copy folders for each table
			Path comFormTarPath = Paths.get("A:/dev/generatedApps/"+ app.getAppName()+ "/src/app/components/"+ table.getNameLowercase());
			Path comListTarPath = Paths.get("A:/dev/generatedApps/"+ app.getAppName()+ "/src/app/components/"+ table.getNameLowercase() + "s");
			fileReadWrite.folderCopyAndRename(comFormSrcPath, comFormTarPath);
			fileReadWrite.folderCopyAndRename(comListSrcPath, comListTarPath);
			Path modelTarPath = Paths.get("A:/dev/generatedApps/"+ app.getAppName()+ "/src/app/shared/"+ table.getNameLowercase());
			fileReadWrite.folderCopyAndRename(modelSrcPath, modelTarPath);
			// Change component files
			// First rename
			String comts = "A:/dev/generatedApps/"+ app.getAppName()+ "/src/app/components/"+ table.getNameLowercase();
			new File(comts+ "/demo.component.ts").renameTo(new File(comts+ "/"+ table.getNameLowercase() + ".component.ts"));
			new File(comts+ "/demo.component.html").renameTo(new File(comts+ "/"+table.getNameLowercase() + ".component.html"));
			new File(comts+ "/demo.component.scss").renameTo(new File(comts+ "/"+table.getNameLowercase() + ".component.scss"));
			String comsts = "A:/dev/generatedApps/"+ app.getAppName()+ "/src/app/components/"+ table.getNameLowercase()+ "s";
			new File(comsts+ "/demos.component.ts").renameTo(new File(comsts+ "/"+table.getNameLowercase() + "s.component.ts"));
			new File(comsts+ "/demos.component.html").renameTo(new File(comsts+ "/"+table.getNameLowercase() + "s.component.html"));
			new File(comsts+ "/demos.component.scss").renameTo(new File(comsts+ "/"+table.getNameLowercase() + "s.component.scss"));
			String modelts = "A:/dev/generatedApps/"+ app.getAppName()+ "/src/app/shared/"+ table.getNameLowercase();
			new File(modelts+ "/demo.model.ts").renameTo(new File(modelts+ "/"+table.getNameLowercase() + ".model.ts"));
			new File(modelts+ "/demo.service.ts").renameTo(new File(modelts+ "/"+table.getNameLowercase() + ".service.ts"));
			// Change file contents
			Path formtsPath = Paths.get(comts + "/"+ table.getNameLowercase() + ".component.ts");
			Path formhtmlPath = Paths.get(comts + "/"+ table.getNameLowercase() + ".component.html");
			fileReadWrite.replaceWordInAFile(formtsPath, "Demo", table.getName());
			fileReadWrite.replaceWordInAFile(formtsPath, "demo", table.getNameLowercase());
			fileReadWrite.replaceWordInAFile(formhtmlPath, "Demo", table.getName());
			fileReadWrite.replaceWordInAFile(formhtmlPath, "demo", table.getNameLowercase());
			Path listtsPath = Paths.get(comsts + "/"+ table.getNameLowercase() + "s.component.ts");
			Path listhtmlPath = Paths.get(comsts + "/"+ table.getNameLowercase() + "s.component.html");
			fileReadWrite.replaceWordInAFile(listtsPath, "Demo", table.getName());
			fileReadWrite.replaceWordInAFile(listtsPath, "demo", table.getNameLowercase());
			fileReadWrite.replaceWordInAFile(listhtmlPath, "Demo", table.getName());
			fileReadWrite.replaceWordInAFile(listhtmlPath, "demo", table.getNameLowercase());
			Path modeltsPath = Paths.get(modelts + "/"+ table.getNameLowercase() + ".model.ts");
			Path servicetsPath = Paths.get(modelts + "/"+ table.getNameLowercase() + ".service.ts");
			fileReadWrite.replaceWordInAFile(modeltsPath, "Demo", table.getName());
			fileReadWrite.replaceWordInAFile(modeltsPath, "demo", table.getNameLowercase());
			fileReadWrite.replaceWordInAFile(servicetsPath, "Demo", table.getName());
			fileReadWrite.replaceWordInAFile(servicetsPath, "demo", table.getNameLowercase());
			// Change route file
			Path routeFile = Paths.get("A:/dev/generatedApps/"+ app.getAppName()+ "/src/app/app.routes.ts");
			//fileReadWrite.addLinesAfterSearchedLine(routeFile, "{path: 'demos', component: DemosComponent},", "{path: '" +table.getNameLowercase()+ "s', component: "+table.getName()+"sComponent},");
			Path mainFile = Paths.get("A:/dev/generatedApps/"+ app.getAppName()+ "/src/app/main/main.component.ts");
			//fileReadWrite.addLinesAfterSearchedLine(mainFile, "{ title: 'Demos', route: '/demos', icon: 'dashboard'}," ,"{ title: '"+ table.getName()+ "s', route: '/"+ table.getNameLowercase() +"', icon: 'dashboard'},");
			// Add Component to app.module.ts
			Path appFile = Paths.get("A:/dev/generatedApps/"+ app.getAppName()+ "/src/app/app.module.ts");
			
		});
		// Delete source folders
		fileReadWrite.deleteDirectoryRecursively(comFormSrcPath);
		fileReadWrite.deleteDirectoryRecursively(comListSrcPath);
	}
}
