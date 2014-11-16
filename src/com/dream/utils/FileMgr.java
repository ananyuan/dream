package com.dream.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dream.base.Constant;

public class FileMgr {
	
	private static Log log = LogFactory.getLog(FileMgr.class);

	
	public static String getFilePath(String cat, String fileId) {
		
		String rootpath = ConfigUtils.getConf(Constant.FILE_PATH);
		
		if (!rootpath.endsWith("/") && !rootpath.endsWith("\\")) {
			rootpath = rootpath + File.separator;
		}
		
		String filePath = rootpath + cat + File.separator + fileId;
		
		return filePath;
	}
	
	
	/**
	 * 
	 * @param filePath 文件路径
	 * @param input InputStream
	 */
	public static void upload(String filePath, InputStream input) {
        try {
        	StopWatch sw = new StopWatch();
            sw.start();
        	
            File targetFile = new File(filePath);
            
            FileUtils.copyInputStreamToFile(input, targetFile);
            
            log.debug(" save file to storage qtime:" + sw.getTime());

        } catch (IOException ioe) {
            log.error(" file upload error.", ioe);
            throw new RuntimeException("file upload error.", ioe);
        } 
	}
}
