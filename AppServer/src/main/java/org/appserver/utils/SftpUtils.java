package org.appserver.utils;

import com.jcraft.jsch.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

public class SftpUtils {
    private static final Logger logger = Logger.getLogger(SftpUtils.class);
    public static final String host = "115.159.98.166";
    public static final int port = 22;
    public static final String username = "root";
    public static final String password = "1QAZ2WSX.";
    private static ChannelSftp sftp;
    private static SftpUtils instance = null;
    public static SftpUtils getInstance() {
        if (instance == null) {
            instance = new SftpUtils();
        }
        sftp = instance.connect(host, port, username, password); //获取连接
        return instance;
    }

    /**
     * 连接sftp服务器
     * @param host     主机
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public ChannelSftp connect(String host, int port, String username, String password) {
        ChannelSftp sftp = null;
        try {
            // 创建JSch对象
            JSch jsch = new JSch();

            // 根据用户名、主机ip、端口号获取一个Session对象
            Session sshSession = jsch.getSession(username, host, port);

            // 设置密码
            sshSession.setPassword(password);

            // 为Session对象设置properties
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);

            // 设置超时
            sshSession.setTimeout(1000 * 30);

            // 通过Session建立连接
            sshSession.connect();

            logger.info("SFTP Session connected.");
            // 打开SFTP通道
            Channel channel = sshSession.openChannel("sftp");

            // 建立SFTP通道的连接
            channel.connect();
            sftp = (ChannelSftp) channel;
            logger.info("Connected successfully to ftpHost = " + host);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return sftp;
    }

    /**
     * 上传文件
     *
     * @param directory   上传的目录
     * @param inputStream 要上传的文件
     * @param inputName   文件名称
     */
    public static void upload(String directory, InputStream inputStream, String inputName) {
        getInstance();
        try {
            sftp.cd("/home/nginx/html");
            // 进入指定目录
            createDir(directory);
            // 上传文件
            sftp.put(inputStream, inputName);
            // 关闭连接
            inputStream.close();
        } catch (Exception e) {
            logger.error(e.getMessage());

        } finally {
            disconnect();
        }
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     */
    public File download(String directory, String downloadFile, String saveFile) {
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            sftp.get(downloadFile, fileOutputStream);
            fileOutputStream.close();
            return file;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * 下载文件
     *
     * @param downloadFilePath 下载的文件完整目录
     * @param saveFile         存在本地的路径
     */
    public File download(String downloadFilePath, String saveFile) {
        try {
            int i = downloadFilePath.lastIndexOf('/');
            if (i == -1) {
                return null;
            }
            sftp.cd(downloadFilePath.substring(0, i));
            File file = new File(saveFile);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            sftp.get(downloadFilePath.substring(i + 1), fileOutputStream);
            fileOutputStream.close();
            logger.info("下载文件：" + saveFile + "成功");
            return file;
        } catch (Exception e) {
            logger.error("下载文件：" + saveFile + "失败" + e.getMessage());
            return null;
        }
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     */
    public void delete(String directory, String deleteFile) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 断开服务器连接
     */
    public static void disconnect() {
        try {
            sftp.getSession().disconnect();
        } catch (JSchException e) {
            logger.error(e.getMessage());
        }
        sftp.quit();
        sftp.disconnect();
    }

    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @throws SftpException
     */
    @SuppressWarnings("unchecked")
    public Vector<ChannelSftp.LsEntry> listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }


    /**
     * 判断目录是否存在
     *
     * @param directory 文件夹路径，如：/root/test/saveFile/
     */
    public static boolean isDirExist(String directory) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

    public static void createDir(String createpath) throws SftpException {

        if (isDirExist(createpath)) {

        } else {
            String pathArry[] = createpath.split("/");

            for (String path : pathArry) {
                if (path.equals("")) {
                    continue;
                }
                if (isDirExist(path)) {
                    try {
                        sftp.cd(path);
                    } catch (SftpException e) {
                        e.printStackTrace();
                    }
                    if (isDirExist(createpath)) {
                    }
                } else {
                    // 建立目录
                    sftp.mkdir(path);
                    // 进入并设置为当前目录
                    sftp.cd(path);
                }
            }
        }
    }
}

