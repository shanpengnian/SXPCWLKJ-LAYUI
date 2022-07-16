package com.sxpcwlkj.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

/**
 * 图片压缩
 */
public class ImageZipUtil {

    public static void main(String[] args) {
         String ss= zipWidthHeightImageFile(new File("D:\\1.jpg"), new File("D:\\2.jpg"), 425, 638, 0.7f);
         System.out.println(ss);
        //zipImageFile(new File("C:\\spider\\2.JPG"),new File("C:\\spider\\2-2.JPG"),425,638,0.7f);

        //zipImageFile(new File("C:\\spider\\3.jpg"),new File("C:\\spider\\3-3.jpg"),425,638,0.7f);

        System.out.println("ok");
    }

    /**
     * 根据设置的宽高等比例压缩图片文件<br> 先保存原文件，再压缩、上传
     *
     * @param oldFile 要进行压缩的文件
     * @param newFile 新文件
     * @param width   宽度 //设置宽度时（高度传入0，等比例缩放）
     * @param height  高度 //设置高度时（宽度传入0，等比例缩放）
     * @param quality 质量
     * @return 返回压缩后的文件的全路径
     */
    public static String zipImageFile(File oldFile, File newFile, int width, int height, float quality) {
        if (oldFile == null) {
            return null;
        }
        try {
            /** 对服务器上的临时文件进行处理 */
            Image srcFile = ImageIO.read(oldFile);
            int w = srcFile.getWidth(null);
            int h = srcFile.getHeight(null);
            double bili;
            if (width > 0) {
                bili = width / (double) w;
                height = (int) (h * bili);
            } else {
                if (height > 0) {
                    bili = height / (double) h;
                    width = (int) (w * bili);
                }
            }

            String srcImgPath = newFile.getAbsoluteFile().toString();
            System.out.println(srcImgPath);
            String subfix = "jpg";
            subfix = srcImgPath.substring(srcImgPath.lastIndexOf(".") + 1, srcImgPath.length());

            BufferedImage buffImg = null;
            if (subfix.equals("png")) {
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            } else {
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            }

            Graphics2D graphics = buffImg.createGraphics();
            graphics.setBackground(new Color(255, 255, 255));
            graphics.setColor(new Color(255, 255, 255));
            graphics.fillRect(0, 0, width, height);
            graphics.drawImage(srcFile.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

            ImageIO.write(buffImg, subfix, new File(srcImgPath));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile.getAbsolutePath();
    }

    /**
     * 按设置的宽度高度压缩图片文件<br> 先保存原文件，再压缩、上传
     *
     * @param oldFile 要进行压缩的文件全路径
     * @param newFile 新文件
     * @param width   宽度
     * @param height  高度
     * @param quality 质量
     * @return 返回压缩后的文件的全路径
     */
    public static String zipWidthHeightImageFile(File oldFile, File newFile, int width, int height, float quality) {
        if (oldFile == null) {
            return null;
        }
        String newImage = null;
        try {
            /** 对服务器上的临时文件进行处理 */
            Image srcFile = ImageIO.read(oldFile);

            String srcImgPath = newFile.getAbsoluteFile().toString();
            //System.out.println(srcImgPath);
            String subfix = "jpg";
            subfix = srcImgPath.substring(srcImgPath.lastIndexOf(".") + 1, srcImgPath.length());

            BufferedImage buffImg = null;
            if (subfix.equals("png")) {
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            } else {
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            }

            Graphics2D graphics = buffImg.createGraphics();
            graphics.setBackground(new Color(255, 255, 255));
            graphics.setColor(new Color(255, 255, 255));
            graphics.fillRect(0, 0, width, height);
            graphics.drawImage(srcFile.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

            ImageIO.write(buffImg, subfix, new File(srcImgPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newImage;
    }

    /**
     *
     * @param req      request
     * @param oldFile  要压缩的图片
     * @param width    指定宽度
     * @param height   指定高度
     * @param quality  精度  1 最大
     *         imgName  图片名称
     * @return
     * File file=  new File("D:\\1.jpg");
     * Map<String, String> map=ImageZipUtil.zipImg("aaa",req,file,200,200,0.6f);
     */
    public static Map<String, String> zipImg(String imgName,HttpServletRequest req,File oldFile, int width, int height, float quality){
        String  path=null;
        Map<String, String> uploadImageMap  =  new HashMap<String, String>();
        uploadImageMap.put("state", -1+"");                                                            //默认状态-1  失败
        try {
            //获取项目下的upload文件夹路径
            String objectPath = req.getSession().getServletContext().getRealPath("/") + "upload";
            //指定文件夹
            String filemane="zipImg";
            //创建文件夹
            File fileUserUpload = new File(objectPath + "/" + filemane);
            if (!fileUserUpload.exists()) {
                // 经过此方法后,用户指定的fileUserPath文件夹一定是存在的 ==》  ps：  /upload/zipImg
                fileUserUpload.mkdir();
            }
            //将文件上传到哪里？
            String uuid=System.currentTimeMillis()+"";
            uuid=imgName;
            String sqlPate="upload\\"+filemane+"\\"+uuid+".jpg";
            path=objectPath+"\\"+filemane+"\\"+uuid+".jpg";
            File newfile=  new File(path);
            ImageZipUtil.zipWidthHeightImageFile(oldFile, newfile, width, height, quality);
            path= path.replaceAll( "\\\\","/");
            sqlPate= sqlPate.replaceAll( "\\\\","/");
            uploadImageMap.put("state", 1+"");                                                            //默认状态 1  成功
            uploadImageMap.put("sqlPate",sqlPate);
            uploadImageMap.put("path",path);
            return uploadImageMap;
        }catch (Exception e){
            e.printStackTrace();
        }
        return uploadImageMap;
    }
}
