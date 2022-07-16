package com.sxpcwlkj.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlToImg {
    //获取网页上所有的图片路径
    public static List<String> getImgSrc(String htmlStr) {

        if( htmlStr == null ){

            return null;
        }

        String img = "";
        Pattern p_image;
        Matcher m_image;
        List<String> pics = new ArrayList<String>();

        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            img = img + "," + m_image.group();
            // Matcher m =
            // Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //匹配src
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);

            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

    /**
     * 获取网页上所有的图片路径
     * @param htmlCode
     * @return
     */
    public static List<String> getImageSrc(String htmlCode) {
        List<String> imageSrcList = new ArrayList<String>();
        Pattern p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);
            imageSrcList.add(src);

        }
        return imageSrcList;
    }

    	public static void main(String[] args) {

		String str = "<div><img src=\"http://47.95.209.196/easyteaching-core/mobile/teachingrecords/getUrl?path=teachrecord/2018-08-10/20180810161528fce8f644e8224841a291ea7633efb395.jpeg\" alt=\"\"></div><br>";
         str="\"<p><br></p><p style=\"text-align: center;\"><img src=\"http://image.sxpcwlkj.cn/%E9%82%80%E8%AF%B7%E6%96%B0%E4%BA%BA.jpg\" width=\"300\" style=\"max-width:100%;\"></p><p><br></p>\"";
            str=str+str;
         System.out.println( getImgSrc(str) );

            List<String> imgs=getImageSrc(str);

            for (String s:imgs
                 ) {
                System.out.println(s);
            }
	}

}
