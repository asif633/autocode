package com.ngautocode.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class FileReadWrite {

	public void folderCopyAndRename(Path srcfolder, Path destfolder) {
		try (Stream<Path> stream = Files.walk(srcfolder)) {
			stream.forEach(path -> {

				try {
					Files.copy(path, Paths.get(path.toString().replace(srcfolder.toString(), destfolder.toString())));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void deleteDirectoryRecursively(Path start) {

		try {
			Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void replaceWordInAFile(Path filepath, String sourceWord, String replaceWord) {
		Charset charset = StandardCharsets.UTF_8;
		String content = "";
		try {
			content = new String(Files.readAllBytes(filepath), charset);
			content = content.replaceAll(sourceWord, replaceWord);
			Files.write(filepath, content.getBytes(charset));
		} catch (IOException e) {
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

		}catch(IOException e){
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeLine(BufferedWriter bufferedWriter, String line){
		try {
			bufferedWriter.write("\n"+line+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
