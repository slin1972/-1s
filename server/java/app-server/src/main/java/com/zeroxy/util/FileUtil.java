package com.zeroxy.util;

import java.io.File;

public class FileUtil {
	public static void createDirectoryWhenNotExists(String directoryPath){
		  File file = new File(directoryPath);
		  if (!file.exists()) {
			  file.mkdirs();
		  }
	}
}
