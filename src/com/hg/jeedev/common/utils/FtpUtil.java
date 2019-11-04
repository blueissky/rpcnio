package com.hg.jeedev.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Properties;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.*;

public class FtpUtil {
	private final static Log logger = LogFactory.getLog(FtpUtil.class);  

	/** 
	 * 获取FTPClient对象 
	 * 
	 * @param ftpHost 
	 *            FTP主机服务器 
	 * @param ftpPassword 
	 *            FTP 登录密码 
	 * @param ftpUserName 
	 *            FTP登录用户名 
	 * @param ftpPort 
	 *            FTP端口 默认为21 
	 * @return 
	 */  
	public static FTPClient getFTPClient(String ftpHost, String ftpUserName,  
	        String ftpPassword, int ftpPort) {  
	    FTPClient ftpClient = new FTPClient();  
	    try {  
	        ftpClient = new FTPClient();  
	        ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器  
	        ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器  
	        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {  
	            logger.info("未连接到FTP，用户名或密码错误。");  
	            ftpClient.disconnect();  
	        } else {  
	            logger.info("FTP连接成功。");  
	        }  
	    } catch (SocketException e) {  
	        e.printStackTrace();  
	        logger.info("FTP的IP地址可能错误，请正确配置。");  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	        logger.info("FTP的端口错误,请正确配置。");  
	    }  
	    return ftpClient;  
	}  

	/* 
	 * 从FTP服务器下载文件 
	 *  
	 * @param ftpHost FTP IP地址 
	 *  
	 * @param ftpUserName FTP 用户名 
	 *  
	 * @param ftpPassword FTP用户名密码 
	 *  
	 * @param ftpPort FTP端口 
	 *  
	 * @param ftpPath FTP服务器中文件所在路径 格式： ftptest/aa 
	 *  
	 * @param localPath 下载到本地的位置 格式：H:/download 
	 *  
	 * @param fileName 文件名称 
	 */  
	public String downloadFtpFile(String ftpHost, String ftpUserName,  
	        String ftpPassword, String ftpPort, String ftpPath, /*String localPath,  */
	        String fileName) {  

	    FTPClient ftpClient = null;  

	    try {  
	        ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, Integer.parseInt(ftpPort));  
	        ftpClient.setControlEncoding("UTF-8"); // 中文支持  
	        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
	        ftpClient.enterLocalPassiveMode();  
	        ftpClient.changeWorkingDirectory(ftpPath);  

	        String path="e://temp//"+UUID.randomUUID().toString()+ fileName;
	        File localFile = new File(path);  
	        OutputStream os = new FileOutputStream(localFile);  
	        ftpClient.retrieveFile(fileName, os);  
	        os.close();  
	        ftpClient.logout();  
	        return path;
	    } catch (Exception e) { 
	        logger.error("没有找到" + ftpPath + "文件");  
	        e.printStackTrace();
	        return null;
	    } 
	}  
	public String[] splitUrl(String url) {
//		String url="ftp://administrator:aaa.123@192.168.10.223:21/1.png";
		String[] fa = url.split("@");
		//left
		String[] fa_1 = fa[0].split(":");
		fa_1[1]=fa_1[1].substring(2, fa_1[1].length());
		//right
		
		int pp1 = fa[1].indexOf("/");
		int pp2=fa[1].lastIndexOf("/");
		System.out.println(fa[1]);
		String directory=fa[1].substring(pp1,pp2);
		
		int point = fa[1].indexOf("/");
		fa[1]=fa[1].substring(0, point);
		String[] fa_2 = fa[1].split(":");
		
		int point2 = url.lastIndexOf("/");
		String filename = url.substring(point2+1);
		
		
		System.out.println("########参数#######");		
		System.out.println(fa_1[0]);
		System.out.println(fa_1[1]);
		System.out.println(fa_1[2]);
		System.out.println(fa_2[0]);
		System.out.println(fa_2[1]);
		System.out.println(filename);
		System.out.println(directory);
		System.out.println("########参数#######");
		
		String []temp= {fa_1[0],fa_1[1],fa_1[2],fa_2[0],fa_2[1],filename,directory};
					   //ftp,user,pw,ip,port,file,dir
		return temp;
	}
	
	
}
