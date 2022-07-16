package com.sxpcwlkj.utils;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MultipartFileToFileUtils {

    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        String path = ResourceUtils.getURL("classpath:").getPath();
        path=path+"upload/"+file.getOriginalFilename();
        return new File(path);
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            //获取根目录
            String path = ResourceUtils.getURL("classpath:").getPath();
            //if(!upload.exists()) upload.mkdirs();
            path=path+"upload";
            path= path.replaceAll( "\\\\","/");
            File fileUploadName = new File(path);
            if (!fileUploadName.exists()) {
                // 经过此方法后,文件夹一定是存在的
                fileUploadName.mkdir();
            }
            String filePath = path+"/" + file ;
            OutputStream os = new FileOutputStream(filePath);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     *
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }

}
