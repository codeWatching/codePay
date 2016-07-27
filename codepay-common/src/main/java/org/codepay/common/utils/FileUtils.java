package org.codepay.common.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.lang3.StringUtils;
import org.codepay.common.CommonConstant;
import org.codepay.common.utils.http.HttpClientUtil;

public class FileUtils {

    static final int BUFFER_SIZE = 16 * 1024;

    public static void copy(File src, File dst) throws IOException {
        if (!dst.exists()) {
            dst.createNewFile();
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            while (in.read(buffer) > 0) {
                out.write(buffer);
            }
        } finally {
            if (null != in) {
                in.close();
            }
            if (null != out) {
                out.close();
            }
        }
    }

    public static String getFileContent(String filePath) throws Exception {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(filePath));
            StringBuffer sb = new StringBuffer();
            String temp = null;
            while ((temp = in.readLine()) != null) {
                if (temp.trim().length() > 0) {
                    sb.append(temp).append("\n");
                }
            }
            if (sb.length() != 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            return sb.toString();
        } finally {
            if (in != null) in.close();
        }

    }

    public static void writeContent(String dir, String fileName, String value, String charString) {
        writeContent(dir, fileName, value, charString, false);
    }

    public static void writeContent(String dir, String fileName, String value, String charString, boolean append) {
        File dirFile = null;
        File file = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        DataOutputStream dos = null;
        String context = CommonConstant.ROOTPATH;
        try {
            dirFile = new File(combinePath(context, dir));
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            file = new File(combinePath(context, dir + fileName));
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file, append);
            bos = new BufferedOutputStream(fos);
            dos = new DataOutputStream(bos);
            dos.write(value.getBytes(charString));
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != dos) dos.flush();
                if (null != fos) fos.close();
                if (null != bos) bos.close();
                if (null != dos) dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Boolean checkFileExists(String path) throws IOException {
        String context = CommonConstant.ROOTPATH;
        File file = new File(combinePath(context, path));
        return file.exists();

    }

    public static final String combinePath(String base, String child) {
        base = base.trim();
        child = child.trim();
        if (base.endsWith("/") || base.endsWith("//")) {
            if (child.startsWith("/") || child.startsWith("//")) {
                return base + child.substring(1);
            } else {
                return base + child;
            }
        } else {
            if (child.startsWith("/") || child.startsWith("//")) {
                return base + child;
            } else {
                return base + '/' + child;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(FileUtils.combinePath(CommonConstant.ROOTPATH, "/tae/eae/"));
    }

    public static boolean isImage(File file) throws IOException {
        boolean flag = false;
        ImageInputStream is = ImageIO.createImageInputStream(file);
        if (null == is) {
            return flag;
        }
        Iterator<ImageReader> iter = ImageIO.getImageReaders(is);
        if (!iter.hasNext()) {
            return flag;
        }
        ImageReader reader = iter.next();
        flag = isImage(reader.getFormatName());
        is.close();
        flag = true;
        return flag;
    }

    public static int getImageLength(File picture) throws IOException {
        BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
        return sourceImg.getHeight();
    }

    public static int getImageWidth(File picture) throws IOException {
        BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
        return sourceImg.getWidth();
    }

    /**
     * 判断文件是否为图片文件(GIF,PNG,JPG)
     *
     * @param srcFileName
     * @return
     */
    public static boolean isImage(String srcFileName) {
        FileInputStream imgFile = null;
        byte[] b = new byte[10];
        int l = -1;
        try {
            imgFile = new FileInputStream(srcFileName);
            l = imgFile.read(b);
            imgFile.close();
        } catch (Exception e) {
            return false;
        }
        if (l == 10) {
            byte b0 = b[0];
            byte b1 = b[1];
            byte b2 = b[2];
            byte b3 = b[3];
            byte b6 = b[6];
            byte b7 = b[7];
            byte b8 = b[8];
            byte b9 = b[9];
            if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F') {
                return true;
            } else if (b1 == (byte) 'P' && b2 == (byte) 'N' && b3 == (byte) 'G') {
                return true;
            } else if (b6 == (byte) 'J' && b7 == (byte) 'F' && b8 == (byte) 'I' && b9 == (byte) 'F') {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 删除指定文件
     *
     * @param path
     * @throws IOException
     */
    public static void deleteFile(String path) throws IOException {
        File file = new File(path);
        deleteFile(file);
    }

    public static void deleteFile(File file) throws IOException {
        if (file.isDirectory()) {
            cleanDirectory(file);
        }
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

    /**
     * 清空目录中的文件 不删除空文件夹
     *
     * @param directory
     * @throws IOException
     */
    public static void cleanDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }
        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }
        File[] files = directory.listFiles();
        if (null == files || files.length == 0) {
            directory.delete();
            return;
        }
        for (File file : files) {
            deleteFile(file);
        }
    }

    /**
     * 删除目录
     *
     * @param directory
     * @throws IOException
     */
    public static void deleteDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }
        cleanDirectory(directory);

        if (!directory.delete()) {
            String message = "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }

    /**
     * @param file     文件
     * @param fileName 文件名
     * @param filePath 文件路径
     * @return String 上传后文件路径
     * @throws
     * @Title: upload
     * @Description: 文件上传
     */
    public static String upload(File file, String fileName, String filePath) {
        String path = null;
        if (null == file) {
            return path;
        }
        String context = CommonConstant.ROOTPATH;
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            File pluginPathFile = new File(combinePath(context, filePath));
            if (!pluginPathFile.exists()) {
                pluginPathFile.mkdirs();
            }
            path = filePath + fileName;
            File localFile = new File(combinePath(context, path));
            fos = new FileOutputStream(localFile);
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return path;
    }

    /**
     * 获取文件
     *
     * @param relativePath 文件相对路径
     */
    public static File getFile(String relativePath) {
        String context = CommonConstant.ROOTPATH;
        File localFile = new File(combinePath(context, relativePath));
        if (null == localFile || !localFile.isFile()) {
            return null;
        }
        return localFile;
    }

    /**
     * @param filePath 文件完整路径
     * @param fos      输出流
     * @return void 返回类型
     * @throws
     * @Title: download
     * @Description: 文件下载
     */
    public static void download(String filePath, OutputStream fos) {
        String context = CommonConstant.ROOTPATH;
        FileInputStream fis = null;
        try {
            File localFile = new File(combinePath(context, filePath));
            if (!localFile.isFile()) {
                return;
            }
            fis = new FileInputStream(localFile);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * http下载网络文件，fastdfs文件等
     *
     * @param remoteUrl 网络地址
     * @param descPath  目标文件路径
     * @throws IOException
     */
    public static void copyURLToFile(String remoteUrl, String descPath, boolean isProxy) throws IOException {
        if (StringUtils.isEmpty(remoteUrl) || StringUtils.isEmpty(descPath)) {
            throw new IllegalArgumentException("文件路径不能为空！");
        }
        FileOutputStream out = null;

        File destFile = new File(descPath);
        if (!destFile.exists()) {
            destFile.delete();
        }
        out = new FileOutputStream(destFile);
        byte[] buffer = HttpClientUtil.sendGetBytes(remoteUrl, isProxy);
        out.write(buffer);
        if (null != out) {
            out.close();
        }
    }

    public static byte[] getBytesByFile(File file) {
        FileInputStream fin = null;
        byte[] bs = new byte[new Long(file.length()).intValue()];
        try {
            fin = new FileInputStream(file);
            int readBytesLength = 0;
            int i;
            while ((i = fin.available()) > 0) {
                fin.read(bs, readBytesLength, i);
                readBytesLength += i;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bs;
    }

    /**
     * 获取文件扩展名
     *
     * @param file
     * @return
     */
    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }
}
