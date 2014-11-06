package com.dream.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dream.model.FileBean;
import com.dream.service.FileService;
import com.dream.utils.DateUtils;
import com.dream.utils.FileMgr;
import com.dream.utils.UuidUtils;

@Controller
@RequestMapping("/file")
public class FileController {
	
	private static Log log = LogFactory.getLog(FileController.class);
	

	@Autowired
	private FileService fileService;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String uploadFile(HttpServletRequest request, HttpServletResponse response ) {
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        List<FileItem> items = new ArrayList<FileItem>();
        if (isMultipart) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                List<FileItem> allItems = upload.parseRequest(request);
                for (FileItem item : allItems) {
                    if (item.isFormField()) {
                        continue;
                    }

                    items.add(item);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
		
        Iterator<FileItem> iter = items.iterator();
        while (iter.hasNext()) {
            FileItem item = iter.next();
            InputStream is = null;
            try {
            	String fileId = UuidUtils.base58Uuid();
            	String filePath = FileMgr.getFilePath("article", fileId);
            	
                String fileName = item.getName();

                String mType = getMimeType(item, request);

                // 上传文件
                InputStream input = item.getInputStream();
                
                FileMgr.upload(filePath, input);
                IOUtils.closeQuietly(input);
                
                FileBean fileBean = new FileBean();
                fileBean.setAtime(DateUtils.getDatetime());
                fileBean.setName(fileName);
                fileBean.setDisname(fileName);
                fileBean.setMtype(mType);
                fileBean.setFsize(String.valueOf(item.getSize()));
                fileBean.setPath(filePath);
                fileBean.setId(fileId);
                
                fileService.insert(fileBean);
            } catch (IOException ioe) {
                log.error(" file upload error.", ioe);
            } finally {
                IOUtils.closeQuietly(is);
                item.delete();
            }
        }
        
        
		return "";
	}

	/**
	 * 
	 * @param item FileItem
	 * @param request 
	 * @return mimeType
	 */
	private String getMimeType(FileItem item, HttpServletRequest request) {
		String contentType = item.getContentType();
		
		
		return contentType;
	}
}
