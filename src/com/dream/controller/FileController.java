package com.dream.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
		String fileId = UuidUtils.base58Uuid();	
		
		return upload(fileId, request, response);
	}
	
	@RequestMapping(value = "/upload/{fileId}", method = RequestMethod.POST)
	public @ResponseBody String uploadFile(@PathVariable String fileId, HttpServletRequest request, HttpServletResponse response ) {
		if (fileId.length() <= 0) {
			fileId = UuidUtils.base58Uuid();	
		}
		return upload(fileId, request, response);
	}
	
	/**
	 * 
	 * @param fileId 文件ID
	 * @param request 请求
	 * @param response 返回
	 * @return
	 */
	private String upload(String fileId, HttpServletRequest request, HttpServletResponse response) {
		
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
		
        String rtnStr = "";
        Iterator<FileItem> iter = items.iterator();
        while (iter.hasNext()) {
            FileItem item = iter.next();
            InputStream is = null;
            try {
                String fileName = item.getName();
                
//                String extname = FilenameUtils.getExtension(fileName);
//                fileId = fileId + "." + extname;
                
                String filePath = FileMgr.getFilePath("article", fileId);
                
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
                
                rtnStr += fileId + ",";
            } catch (IOException ioe) {
                log.error(" file upload error.", ioe);
            } finally {
                IOUtils.closeQuietly(is);
                item.delete();
            }
        }
        if (rtnStr.length() > 0) {
        	rtnStr = rtnStr.substring(0, rtnStr.length() - 1);
        }
        
		return rtnStr;
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
	
	
    @RequestMapping(value="/{fileid}", method = RequestMethod.GET)
	public void download(@PathVariable String fileid, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Server", "server");
        response.setHeader("Cache-Control", "max-age=" + (3600 * 2 * 12));
        
        FileBean fileBean = fileService.findFile(fileid);
        
        setDownFileName(request, response, fileBean.getName());
        
        String filePath = fileBean.getPath();
        
        File file = new File(filePath);
        
        
        try {
			InputStream is = FileUtils.openInputStream(file);
			
            OutputStream out = response.getOutputStream();
            IOUtils.copyLarge(is, out);
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(out);
            out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}
    
    
    
    private void setDownFileName(HttpServletRequest request, HttpServletResponse response, String fileName) {
        String userbrowser = "unknow";
        try {
            userbrowser = request.getHeader("User-Agent");
            if (-1 < userbrowser.indexOf("MSIE 6.0") || -1 < userbrowser.indexOf("MSIE 7.0")) {
                // IE6、7
                response.addHeader("content-disposition", "attachment;filename="
                        + new String(fileName.getBytes(), "ISO8859-1"));
            } else if (-1 < userbrowser.indexOf("MSIE 8.0")) {
                // IE8
                response.addHeader("content-disposition", "attachment;filename="
                        + URLEncoder.encode(fileName, "UTF-8"));
            } else if (-1 < userbrowser.indexOf("MSIE 9.0")) {
                // IE9
                response.addHeader("content-disposition", "attachment;filename="
                        + URLEncoder.encode(fileName, "UTF-8"));
            } else if (-1 < userbrowser.indexOf("Chrome")) {
                // chrome
                response.addHeader("content-disposition",
                        "attachment;filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));
            } else if (-1 < userbrowser.indexOf("Safari")) {
                // safari
                response.addHeader("content-disposition", "attachment;filename="
                        + new String(fileName.getBytes(), "ISO8859-1"));
            } else {
                // other brower
                response.addHeader("content-disposition",
                        "attachment;filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));
            }
        } catch (Exception e) {
            log.error("get user browser error. agent info:" + userbrowser, e);
        }
    }
}
