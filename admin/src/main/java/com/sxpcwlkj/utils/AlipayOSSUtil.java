package com.sxpcwlkj.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * @author https://www.sxpcwlkj.com
 * @version 1.0
 * @date 2020/4/25 22:49
 * @Description : 阿里云OOS 存储
 */
public class AlipayOSSUtil {

    //阿里云API的内或外网域名
    private static String ENDPOINT = "http://oss-cn-zhangjiakou.aliyuncs.com";
    //阿里云API的密钥Access Key ID
    private static String ACCESS_KEY_ID = "LTAI4GGZtoTG67RULaepGq3H";
    //阿里云API的密钥Access Key Secret
    private static String ACCESS_KEY_SECRET = "qMGiTRbUYUkYtvpvQHhSWMRIF7eS3s";
    //阿里云API的bucket名称
    private static String BACKET_NAME = "jifugou";
    //oss域名访问
    private static String MY_URL="https://jifugou.oss-cn-zhangjiakou.aliyuncs.com/";    //带 斜杠  /


    public static String IMG_TYPE_PNG = "PNG";
    public static String IMG_TYPE_JPG = "JPG";
    public static String IMG_TYPE_JPEG = "JPEG";
    public static String IMG_TYPE_DMG = "BMP";
    public static String IMG_TYPE_GIF = "GIF";
    public static String IMG_TYPE_SVG = "SVG";
    public static String IMG_TYPE_APK = "APK";
    public static String IMG_TYPE_IOS= "IOS";
    public static String IMG_TYPE_MP3= "MP3";


    //上传图片文件，返回地址
    public static String uploadFile(byte[] content, String filename) throws IOException {
        OSSClient client = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        MultipartFile file = new MockMultipartFile(filename, content);
        String imgUrl;

        //创建上传object的metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getInputStream().available());

        metadata.setContentEncoding("utf-8");
        metadata.setContentType(file.getContentType());
        metadata.setContentDisposition("inline");
        //设置上传文件的类型
        metadata.setContentType("image/jpeg");

        //上传文件

        PutObjectResult putresult = client.putObject(BACKET_NAME, filename, file.getInputStream(), metadata);

        Date expiration = new Date(new Date().getTime() + 3600 * 1000);

        URL url = client.generatePresignedUrl(BACKET_NAME, filename, expiration);

        imgUrl = url.toString();

        client.shutdown();

        return imgUrl;
    }

    //上传图片文件，返回地址
    public static String uploadFile(MultipartFile file) throws IOException {
        OSSClient client = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        String imgUrl="";
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if((IMG_TYPE_MP3.equals(suffix.toUpperCase())) ||(IMG_TYPE_APK.equals(suffix.toUpperCase()) ||IMG_TYPE_IOS.equals(suffix.toUpperCase()) ||IMG_TYPE_DMG.equals(suffix.toUpperCase()) || IMG_TYPE_GIF.equals(suffix.toUpperCase()) || IMG_TYPE_JPEG.equals(suffix.toUpperCase()) || IMG_TYPE_JPG.equals(suffix.toUpperCase()) || IMG_TYPE_PNG.equals(suffix.toUpperCase()) || IMG_TYPE_SVG.equals(suffix.toUpperCase()))){
            //创建上传object的metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getInputStream().available());

            metadata.setContentEncoding("utf-8");
            metadata.setContentType(file.getContentType());
            metadata.setContentDisposition("inline");
            //设置上传文件的类型

            //application/vnd.android.package-archive
            metadata.setContentType(contentType(suffix));

            //上传文件
            String filename=DataUtil.getOrderIdByUUId();
            filename=filename+"."+suffix;
            PutObjectResult putresult = client.putObject(BACKET_NAME,filename , file.getInputStream(), metadata);

            Date expiration = new Date(new Date().getTime() + 3600 * 1000);

            URL url = client.generatePresignedUrl(BACKET_NAME, filename, expiration);

            imgUrl = url.toString();

            imgUrl=MY_URL+filename;
            client.shutdown();
        }

        return imgUrl;
    }

    //更具图片名，获取图片路径
    public static String getImageURL(String key) {
        OSSClient client = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        OSSObject ossObj = client.getObject(BACKET_NAME, key);
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        URL url = client.generatePresignedUrl(BACKET_NAME, key, expiration);
        return url.toString();
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType @Version1.0
     * https://blog.csdn.net/wangh92/article/details/86535707
     * @param FilenameExtension
     *            文件后缀
     * @return String
     */
    public static String contentType(String FilenameExtension) {
        if (FilenameExtension.equals("BMP") || FilenameExtension.equals("bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equals("GIF") || FilenameExtension.equals("gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equals("JPEG") || FilenameExtension.equals("jpeg") || FilenameExtension.equals("JPG")
                || FilenameExtension.equals("jpg") || FilenameExtension.equals("PNG")
                || FilenameExtension.equals(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equals("HTML") || FilenameExtension.equals("html")) {
            return "text/html";
        }
        if (FilenameExtension.equals("TXT") || FilenameExtension.equals("txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equals("VSD") || FilenameExtension.equals("vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equals("PPTX") || FilenameExtension.equals("pptx") || FilenameExtension.equals("PPT")
                || FilenameExtension.equals("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equals("DOCX") || FilenameExtension.equals("docx") || FilenameExtension.equals("DOC")
                || FilenameExtension.equals("doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equals("XML") || FilenameExtension.equals("xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equals("apk") || FilenameExtension.equals("APK")) {
            return "application/octet-stream";
        }

        if (FilenameExtension.equalsIgnoreCase("pdf")) {
            return "application/pdf";
        }
        if (FilenameExtension.equalsIgnoreCase("zip")) {
            return "application/zip";
        }
        if (FilenameExtension.equalsIgnoreCase("tar")) {
            return "application/x-tar";
        }
        if (FilenameExtension.equalsIgnoreCase("avi")) {
            return "video/avi";
        }
        if (FilenameExtension.equalsIgnoreCase("mp4")) {
            return "video/mpeg4";
        }
        if (FilenameExtension.equalsIgnoreCase("mp3")) {
            return "audio/mp3";
        }
        if (FilenameExtension.equalsIgnoreCase("mp2")) {
            return "audio/mp2";
        }
        return "text/html";
    }




    /**
     * 功能描述:
     *
     * @param:[objectKey, multipartFile 文件的新名称]
     * @return:java.lang.String
     * @date:2018/10/14 15:46
     **/
    public static String uploadFile(String objectKey, MultipartFile multipartFile)
            throws OSSException, ClientException, FileNotFoundException {
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        String filename=DataUtil.getOrderIdByUUId();
        objectKey=filename+"."+suffix;
        // 创建OSSClient的实例
        OSSClient ossClient  = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        StringBuffer sb = new StringBuffer();
        // 上传的文件不是空，并且文件的名字不是空字符串
        if (multipartFile.getSize() != 0 && !"".equals(multipartFile.getName())) {
            ObjectMetadata om = new ObjectMetadata();
            om.setContentLength(multipartFile.getSize());
            // 设置文件上传到服务器的名称
            om.addUserMetadata("filename", objectKey);
            try {
                ossClient.putObject(BACKET_NAME, objectKey, new ByteArrayInputStream(multipartFile.getBytes()), om);
            } catch (IOException e) {

            }
        }
        // 设置这个文件地址的有效时间
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        String url = ossClient.generatePresignedUrl(BACKET_NAME, objectKey, expiration).toString();
        String FileUrl=MY_URL+objectKey;
        return FileUrl;
    }



    public static void  main(String[] res){

        SysouUtil.sysou(getImageURL("1000000043195041"));

        ;
    }

}
