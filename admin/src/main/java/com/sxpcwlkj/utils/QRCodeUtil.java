package com.sxpcwlkj.utils;

import com.google.zxing.*;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Random;

/**
 *  二维码生成工具类
 */
public class QRCodeUtil {
    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    private static final int QRCODE_SIZE = 300; // 二维码尺寸，宽度和高度均是300
    private static final int WIDTH = 80;  //LOGO宽度
    private static final int HEIGHT = 80; //LOGO高度
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    /**
     * 生成二维码的方法
     * @param content 目标url
     * @param imgPath logo图片地址
     * @param needCompress 是否压缩logo
     * @return 二维码图片
     * @throws Exception
     */
    private static BufferedImage createImage(String content, String imgPath,boolean needCompress) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//指定二维码的纠错等级为高级
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);//指定字符编码为“utf-8”
        hints.put(EncodeHintType.MARGIN, 1); //设置图片的边距
        //参数1：内容，目标url,参数2：固定写法，参数3：二维码的宽度，参数4：二维码的高度，参数5：二维码属性设置
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();//二维码的宽度
        int height = bitMatrix.getHeight();//二维码的高度
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);//生成的二维码image
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000: 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {//logo图片地址为null或空时
            return image; //只返回二维码图片，无中间的logo
        }
        // 插入logo图片  参数1：二维码图片，参数2：logo图片地址，参数3：压缩图片
        QRCodeUtil.insertImage(image, imgPath, needCompress);
        return image;
    }

    /**
     * 插入logo图片
     * @param source 二维码图片
     * @param imgPath LOGO图片地址
     * @param needCompress 是否压缩
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String imgPath,boolean needCompress) throws Exception {
        File file = new File(imgPath);//logo图片地址放入文件
        if (!file.exists()) {
            System.err.println("" + imgPath + " 该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) { //如果上传logo宽 >60
                width = WIDTH;
            }
            if (height > HEIGHT) { //如果上传logo高>60
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码(内嵌LOGO)
     * @param content  内容
     * @param imgPath  logo地址
     * @param destPath 存放目录
     * @param needCompress 是否压缩logo
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath,needCompress); //生成二维码
        mkdirs(destPath);
        String file = new Random().nextInt(99999) + ".jpg";
        ImageIO.write(image, FORMAT_NAME, new File(destPath + "/" + file));
    }
    /**
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     * @param destPath 存放目录
     */
    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }
    /**
     * 生成二维码(内嵌LOGO),没有压缩
     *
     * @param content  内容
     * @param imgPath  LOGO地址
     * @param destPath 存储地址
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath)throws Exception {
        QRCodeUtil.encode(content, imgPath, destPath, false);
    }

    /**
     * 生成二维码，无内嵌logo
     *
     * @param content      内容
     * @param destPath     存储地址
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String destPath,boolean needCompress) throws Exception {
        QRCodeUtil.encode(content, null, destPath, needCompress);
    }
    /**
     * 生成二维码
     *
     * @param content  内容
     * @param destPath 存储地址
     * @throws Exception
     */
    public static void encode(String content, String destPath) throws Exception {
        QRCodeUtil.encode(content, null, destPath, false);
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param imgPath      LOGO地址
     * @param output       输出流
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String imgPath,OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath,needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }
    /**
     * 生成二维码
     *
     * @param content 内容
     * @param output  输出流
     * @throws Exception
     */
    public static void encode(String content, OutputStream output)throws Exception {
        QRCodeUtil.encode(content, null, output, false);
    }

    /**
     * 解析二维码
     * 按文件参数解析
     * @param file 二维码图片
     * @return
     * @throws Exception
     */
    public static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);//指定字符编码为“utf-8”
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();//二维码文本内容
        return resultStr;
    }

    /**
     * 解析二维码
     * 按二维码图片地址解析
     * @param path 二维码图片地址
     * @return  不是二维码的内容返回null,是二维码直接返回识别的结果
     * @throws Exception
     */
    public static String decode(String path) throws Exception {
        /**
         * http://www.test.com/page/weizhi.html?devicesName=消防栓_WRbb&devicesNumber=2018092512094659f6aee7&hydrantId=d88219b0-c07e-11e8-87a3-2302a122c883
         * &equipment=触发器01,撞到触发器;触发器02,开盖触发器;触发器03,漏水触发器;
         */
        return QRCodeUtil.decode(new File(path));
    }



    /**
     * 此方法  生成的图片文件名是一样的，如果之前有将直接覆盖
     * @param request
     * @param matrix
     * @param filename
     * @return
     * @throws Exception
     */
    public static String uploadQRcode(HttpServletRequest request, BitMatrix matrix, String filename)throws Exception {
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
        path=path+"/Qrcode";
        File Qrcode = new File(path);
        if (!Qrcode.exists()) {
            // 经过此方法后,文件夹一定是存在的
            Qrcode.mkdir();
        }

        String filePath = path+"/" + filename ;
        filePath= filePath.replaceAll( "\\\\","/");
        File outputFile = new File(filePath+".png");
        QRCodeUtil.writeToFile(matrix, "png", outputFile);
        //return "/upload/"+fileName+"/"+filename+".png";
        return  filePath+".png";
    }

    /**
     * 图片写入
     * @param matrix
     * @param format
     * @param file
     * @throws IOException
     */
    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        //写图片  图片   格式  file 路径
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    /**
     * 生成二维码图片
     * @param matrix
     * @return
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static void main(String[] args) {

        String path = null;
        try {
            path = ResourceUtils.getURL("classpath:").getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //if(!upload.exists()) upload.mkdirs();
        path=path+"upload\\";
        File fileUploadName = new File(path);
        if (!fileUploadName.exists()) {
            // 经过此方法后,文件夹一定是存在的
            fileUploadName.mkdir();
        }


        //生成二维码
//        String content= "http://www.test.com/page/weizhi.html?devicesName=消防栓_WRbb&devicesNumber=2018092512094659f6aee7&hydrantId=d88219b0-c07e-11e8-87a3-2302a122c883&equipment=触发器01,撞到触发器;触发器02,开盖触发器;触发器03,漏水触发器;";
//        String imgPath="D:/upload/logo/logo.png";
//        String destPath ="D:/upload/zxing/";
//        System.out.println("logo图片地址imgPath=="+imgPath);
//        System.out.println("存放二维码图片的地址destPath=="+destPath);
//        try {
//            QRCodeUtil.encode(content,imgPath,destPath, true);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
        //解析二维码
        /*String destPath1 = "D:/upload/zxing/15456.jpg";
        try {
            String result = QRCodeUtil.decode(destPath1);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(destPath1+"不是二维码");
        }*/
    }

}
