package com.ngautocode.service;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class FileReadWrite {

	public void directoryCopyAndRename(String srcfolder, String destfolder) {
		try (Stream<Path> stream = Files.walk(Paths.get(srcfolder))) {
			stream.forEach(path -> {
				try {
					Files.copy(path, Paths.get(path.toString().replace(srcfolder, destfolder)));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});

		} 
		catch(FileNotFoundException fn){
			fn.printStackTrace();
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void createEmptyDirectory(String path){
		try {
			Files.createDirectory(Paths.get(path));
		}
		catch(FileNotFoundException fn){
			fn.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void deleteDirectoryRecursively(String start) {

		try {
			Files.walkFileTree(Paths.get(start), new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
					if (e == null) {
						Files.delete(dir);
						return FileVisitResult.CONTINUE;
					} else {
						// directory iteration failed
						throw e;
					}
				}
			});
		} 
		catch(FileNotFoundException fn){
			fn.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void replaceWordInAFile(String file, String sourceWord, String replaceWord) {
		Charset charset = StandardCharsets.UTF_8;
		String content = "";
		Path filepath = Paths.get(file);
		try {
			content = new String(Files.readAllBytes(filepath), charset);
			content = content.replaceAll(sourceWord, replaceWord);
			Files.write(filepath, content.getBytes(charset));
		}
		catch(FileNotFoundException fn){
			fn.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addLinesToAFile(Path file, String line){
		BufferedWriter bufferedWriter  = null;
		try{
			Charset charset = Charset.forName("UTF-8");

			bufferedWriter = Files.newBufferedWriter(file, charset);
			bufferedWriter.write("\n"+line+"\n");

		}
		catch(FileNotFoundException fn){
			fn.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				bufferedWriter.close();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
	}
	
	public void addLinesAfterSearchedLine(Path file, String searchLine, String addLine){
		//BufferedWriter bufferedWriter  = null;
		Charset charset = Charset.forName("UTF-8");
		try {
			final BufferedWriter bufferedWriter = Files.newBufferedWriter(file, charset);
			Stream<String> stream = Files.lines(file);
		    stream.forEach(line -> {
		    	if(line.contains(searchLine)){
		    		//addLinesToAFile(file, addLine);
		    		writeLine(bufferedWriter, addLine);
		    	}
		    	writeLine(bufferedWriter, line);
		    });
//		        .filter(line -> line.contains("print"))
//		        .map(String::trim)
//		        .forEach(System.out::println);
		}
		catch(FileNotFoundException fn){
			fn.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeLine(BufferedWriter bufferedWriter, String line){
		try {
			bufferedWriter.write("\n"+line+"\n");
		}
		catch(FileNotFoundException fn){
			fn.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeAfterLine(Path path, String needle, String extraLine){	
		List<String> lines;
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			int needlePosition =0;
			for(String line: lines){
				if(line.contains(needle)){
					needlePosition = lines.indexOf(line);
				}
			}
			lines.add(needlePosition, extraLine);
			Files.write(path, lines, StandardCharsets.UTF_8);
		} 
		catch(FileNotFoundException fn){
			fn.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeLinesAfterLine(Path path, String needle, List<String> extraLine){	
		List<String> lines;
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			int needlePosition =0;
			for(String line: lines){
				if(line.contains(needle)){
					needlePosition = lines.indexOf(line);
				}
			}
			for(String line: extraLine){
				lines.add(needlePosition, line);
			}
			Files.write(path, lines, StandardCharsets.UTF_8);
		}
		catch(FileNotFoundException fn){
			fn.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
