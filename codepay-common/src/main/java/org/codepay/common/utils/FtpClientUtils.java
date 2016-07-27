package org.codepay.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * FTP操作工具
 * @author lisai
 * @date 2016-04-21
 */
public class FtpClientUtils {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(FtpClientUtils.class);
	/**
	 * 客户端字符编码
	 */
	public final  static String  FTP_CLIENT_ENCODING     = System.getProperty("file.encoding");
	/**
	 * 服务端字符编码
	 */
	public final  static String  FTP_SERVER_ENCODING    = "ISO-8859-1";
	/**
	 * 服务端缓冲区大小(KB)
	 */
	public final static Integer FTP_SERVER_BUFFER_SIZE_VALUE   = 8092;
	/**
	 * 服务端数据传输TimeOut(毫秒)
	 */
	public final static Integer FTP_SERVER_DATA_TIME_OUT       = 30000;
	/**
	 * 服务端连接TimeOut(毫秒)
	 */
	public final static Integer FTP_SERVER_SO_TIME_OUT         = 30000;
	
	/**
	 *  客户端连接对象
	 */
	private  static volatile FTPClient ftpClient ;

    /**
     * 连接FTP服务器
     * @param host FTP服务器地址
     * @param port FTP服务器端口号
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public  static boolean connect(String host, int port, String username, String password) {
        try{
        	 ftpClient  = new FTPClient();
            ftpClient.connect(host, port);
            // 登录
            ftpClient.login(username, password);
            ftpClient.setBufferSize(FTP_SERVER_BUFFER_SIZE_VALUE);  
    		ftpClient.setControlEncoding(FTP_CLIENT_ENCODING);
    		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    	   	ftpClient.setDataTimeout(FTP_SERVER_DATA_TIME_OUT);
        	ftpClient.setSoTimeout(FTP_SERVER_SO_TIME_OUT);
        	// 检验是否连接成功
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
            	LOGGER.error("连接FTP服务器失败，响应码："+reply);
            	disConnectionServer();
                return false;
            }
        }catch(IOException e){
        	LOGGER.error("连接FTP服务器失败",e);
            return false;
        }
        return true;
    }
    /**
     * 断开FTP连接
     */
    public static  void disConnectionServer() {
    	try{
    		if(ftpClient != null){
    			// 退出登录
				ftpClient.logout();
				// 断开连接
				ftpClient.disconnect();
				LOGGER.info("成功退出以及断开FTP连接");
    		}
    	}catch (Exception e){
    		LOGGER.error("退出或者断开FTP服务器出现异常",e);
    	}finally {
    		if(ftpClient != null){
    			if (ftpClient.isConnected()) {
					try {
						ftpClient.disconnect();
					} catch (IOException ioe) {
						ftpClient = null;
					}
				}
    		}
    	}
	}
    /**
     * 上传文件到服务器上的特定路径
     * @param file 上传的文件或文件夹
     * @param path 上传到ftp服务器哪个路径下
     * @return 
     */
    public static  boolean uploadTo(File file, String path){
        //切换到服务器上面的合适目录
        //如果对应的目录不存在，则创建
    	LOGGER.info("上传文件 "+file.getAbsolutePath()+" 到服务器路径 "+path);
        try{
            String[] segs = path.split("/");
            for(String seg : segs){
                if(!ftpClient.changeWorkingDirectory(seg)){
                    ftpClient.makeDirectory(seg);
                    if(!ftpClient.changeWorkingDirectory(seg)){
                    	LOGGER.error("服务器目录切换错误:"+seg);
                        return false;
                    }
                }
            }
        }catch(IOException e){
        	LOGGER.error("服务器目录切换错误",e);
            return false;
        }
        return upload(file);
    }
    /**
     * 上传文件到服务器上的特定路径
     * @param inputStream 文件输入流
     * @param path 上传到ftp服务器哪个路径下
     * @param fileName 上传文件名
     * @return 
     */
    public static  boolean uploadTo(InputStream inputStream, String path,String fileName){
        //切换到服务器上面的合适目录
        //如果对应的目录不存在，则创建
    	LOGGER.info("上传文件到服务器路径 "+path);
        try{
            String[] segs = path.split("/");
            for(String seg : segs){
                if(!ftpClient.changeWorkingDirectory(seg)){
                    ftpClient.makeDirectory(seg);
                    if(!ftpClient.changeWorkingDirectory(seg)){
                    	LOGGER.error("服务器目录切换错误:"+seg);
                        return false;
                    }
                }
            }
        	ftpClient.storeFile(new String(fileName.getBytes(FTP_CLIENT_ENCODING),FTP_SERVER_ENCODING), inputStream);
        }catch(IOException e){
        	LOGGER.error("服务器目录切换错误",e);
            return false;
        }
        return true;
    }
    /**
     * 上传文件或者文件夹
     * @param file 上传的文件或文件夹
     * @return 是否上次成功
     */
    private static boolean upload(File file) {
        try{
            if (file.isDirectory()) {
                ftpClient.makeDirectory(file.getName());
                ftpClient.changeWorkingDirectory(file.getName());
                File[] subFiles = file.listFiles();
                for (File subFile : subFiles) {
                    if (subFile.isDirectory()) {
                        upload(subFile);
                        ftpClient.changeToParentDirectory();
                    } else {
                    	doUpload(subFile);
                    	
                    }
                }
            } else {
            		doUpload(file);
            }
        }catch(IOException e){
        	LOGGER.error("上传文件失败",e);
            return false;
        }
        return true;
    }
    /**
     * 执行上传操作
     * @param file
     */
	private static void doUpload(File file) {
		FileInputStream input = null;
		try{
			input = new FileInputStream(file.getName());
        	ftpClient.storeFile(new String(file.getName().getBytes(FTP_CLIENT_ENCODING),FTP_SERVER_ENCODING), input);
		}catch(Exception e){
			LOGGER.error("上传文件失败",e);
		}finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 下载文件从服务器上
     * @param file 现在的文件或文件夹
     * @param path 下载文件在ftp服务器哪个路径下
     * @param out 将下载数据包装在输出流中
	 */
	public static boolean downFrom(File file, String path,OutputStream out){
		LOGGER.info(String.format("下载文件名称为:%s ,文件所在路径为:%s", file.getName(),path));
		try{
			ftpClient.changeWorkingDirectory(new String(path.getBytes(FTP_CLIENT_ENCODING), FTP_SERVER_ENCODING));
			if (!ftpClient.retrieveFile(file.getName(), out)) {
				LOGGER.error("下载文件失败,文件目录:{},文件名称:{}" , path , file.getName());
				return false;
			} 
		}catch(Exception e){
			LOGGER.error("下载文件失败",e);
            return false;
		}
		return true;
	}
}
