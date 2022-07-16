package com.sxpcwlkj.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author http://www.sxpcwlkj.com
 * @version 1.0
 * @description com.sxpcwlkj.utils
 * @date 2018/12/25 0025
 */
public class FileUtil {
    public static void main(String[] args) {
        //单图 Bates64位      filepath 自定义路   oldFileName 图片名称带后缀
        //uploadImage(HttpServletRequest req, String imgBase64, String filepath, String oldFileName)
        //uploadImage(HttpServletRequest req, File file, String filepath)
        String dir = "D:\\sgtsc_files\\393\\";
        String filename = "test1.txt";
        String subdir = "subdir";
        //创建文件夹(支持多路径)
        createDir(dir);
        //创建文件
        createFile(dir + filename);
        //删除文件,文件夹（支持深度删除）
        //DeleteFolder(dir + filename);
        try {
            WriteStringToFile(dir+filename);           //写入txt
            File file = new File(dir+filename);        //读取txt
            // copyFile(dir+filename,dir+"new.txt");     //复制
            // renameFile(dir+filename,"AAA.txt");      //重命名
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定文件，写入内容
     * @param filePath
     */
    public static void WriteStringToFile(String filePath) {
        try {
            File file = new File(filePath);
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println("我在第一行");// 往文件里写入字符串
            ps.append("我在第一行的下面");// 在已有的基础上添加字符串
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 读取txt文件的内容
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String txtToString(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
         * 检查文件是否存在，存在返回true
         * @param destFileName
         * @return
         */
        public static boolean checkFileIsExists(String destFileName){
            File file = new File(destFileName);
            if (file.exists()) {
                return true;
            }else{
                return false;
            }
        }
        /**
         * 复制文件
         * @param source
         * @param dest
         * @throws IOException
         */
        public static void copyFile(File source, File dest){
            InputStream input = null;
            OutputStream output = null;
            try {
                input = new FileInputStream(source);
                output = new FileOutputStream(dest);
                byte[] buf = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buf))>-1) {
                    output.write(buf, 0, bytesRead);
                }
                output.close();
                input.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        /**
         * 把输入流保存到指定文件
         * @param source
         * @param dest
         * @throws IOException
         */
        public static void saveFile(InputStream source, File dest){
            InputStream input = null;
            OutputStream output = null;
            try {
                input =source;
                output = new FileOutputStream(dest);
                byte[] buf = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buf))>-1) {
                    output.write(buf, 0, bytesRead);
                }
                output.close();
                input.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        /**
         * 创建文件
         */
        public static boolean createFile(String destFileName) {
            File file = new File(destFileName);
            if (file.exists()) {
                return false;
            }
            if (destFileName.endsWith(File.separator)) {
                return false;
            }
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    return false;
                }
            }
            try {
                if (file.createNewFile()) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 创建目录
         */
        public static boolean createDir(String destDirName) {
            File dir = new File(destDirName);
            if (dir.exists()) {
                return false;
            }
            if (!destDirName.endsWith(File.separator))
                destDirName = destDirName + File.separator;
            if (dir.mkdirs()) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * 根据路径删除指定的目录或文件，无论存在与否
         */
        public static boolean DeleteFolder(String sPath) {
            boolean flag = false;
            File file = new File(sPath);
            if (!file.exists()) {
                return flag;
            } else {
                if (file.isFile()) {
                    return deleteFile(sPath);
                } else {
                    return deleteDirectory(sPath);
                }
            }
        }

        /**
         * 删除单个文件
         */
        public static boolean deleteFile(String sPath) {
            boolean flag = false;
            File file = new File(sPath);
            if (file.isFile() && file.exists()) {
                file.delete();
                flag = true;
            }
            return flag;
        }

        /**
         * 删除目录（文件夹）以及目录下的文件
         */
        public static boolean deleteDirectory(String sPath) {
            if (!sPath.endsWith(File.separator)) {
                sPath = sPath + File.separator;
            }
            File dirFile = new File(sPath);
            if (!dirFile.exists() || !dirFile.isDirectory()) {
                return false;
            }
            boolean flag = true;
            File[] files = dirFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    flag = deleteFile(files[i].getAbsolutePath());
                    if (!flag)
                        break;
                } else {
                    flag = deleteDirectory(files[i].getAbsolutePath());
                    if (!flag)
                        break;
                }
            }
            if (!flag)
                return false;
            if (dirFile.delete()) {
                return true;
            } else {
                return false;
            }
        }
    // 文件复制
    public static boolean copyFile(String source, String copy) throws Exception {
        source = source.replace("\\", "/");
        copy = copy.replace("\\", "/");

        File source_file = new File(source);
        File copy_file = new File(copy);

        // BufferedStream缓冲字节流

        if (!source_file.exists()) {
            throw new IOException("文件复制失败：源文件（" + source_file + "） 不存在");
        }
        if (copy_file.isDirectory()) {
            throw new IOException("文件复制失败：复制路径（" + copy_file + "） 错误");
        }
        File parent = copy_file.getParentFile();
        // 创建复制路径
        if (!parent.exists()) {
            parent.mkdirs();
        }
        // 创建复制文件
        if (!copy_file.exists()) {
            copy_file.createNewFile();
        }

        FileInputStream fis = new FileInputStream(source_file);
        FileOutputStream fos = new FileOutputStream(copy_file);

        BufferedInputStream bis = new BufferedInputStream(fis);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        byte[] KB = new byte[1024];
        int index;
        while ((index = bis.read(KB)) != -1) {
            bos.write(KB, 0, index);
        }

        bos.close();
        bis.close();
        fos.close();
        fis.close();

        if (!copy_file.exists()) {
            return false;
        } else if (source_file.length() != copy_file.length()) {
            return false;
        } else {
            return true;
        }

    }

    // 文件重命名
    public static boolean renameFile(String url, String new_name) throws Exception {
        String old_url = url;
        old_url = old_url.replace("\\", "/");
        File old_file = new File(old_url);
        if (!old_file.exists()) {
            throw new IOException("文件重命名失败，文件（"+old_file+"）不存在");
        }
       // System.out.println(old_file.exists());

        String old_name = old_file.getName();
        // 获得父路径
        String parent = old_file.getParent();
        // 重命名
        String new_url = parent + "/" + new_name;
        File new_file = new File(new_url);
        old_file.renameTo(new_file);

        //System.out.println("原文件：" + old_file.getName());
        //System.out.println("新文件：" + new_file.getName());
        new_name = new_file.getName();
        old_name = old_file.getName();
        if (new_name.equals(old_name)) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * 图片上传
     * 默认传到 tomcate下的update的路径下
     *
     * 需要传入 req，Base64 字节 string，用户自定义路径，图片名称
     * @param req
     * @param imgBase64         字节码文件:Base64
     * @param filepath          自定义文件夹
     * @param oldFileName       文件名 ps：abc.jpg
     * @return                  返回 map
     * 
     * Map map= new HashMap();
     * 
     * map=ImageUtil.uploadImage(rep,fil,"logo")
     * 
     * String url=  map.get("sqlPate")
     */
    public  static Map<String,String>   uploadImage(HttpServletRequest req, String imgBase64, String filepath, String oldFileName){
        String TrueDirectory = "upload"+ File.separator+filepath;
        String inventedDirectory = "upload/"+filepath;
        String fileurl = TrueDirectory+"-"+inventedDirectory;
        return privateBase64(req,imgBase64,fileurl,oldFileName);
    }

    /**
     * 图片上传
     * 默认传到 tomcate下的update的路径下
     * @param req
     * @param file
     * @param fileUserPath
     *
     *  此方法只需要传入  req  和   file ，fileUserPath：指定一级文件名，可以为空 fileUserPath=""
     *  传入项目根路径 /upload
     *  返回map   旧图片名，新图片名，Sql路径，状态，提示文本
     * @return
     */
    public  static Map<String,String>  uploadImage(HttpServletRequest req, MultipartFile file, String fileUserPath){
        return privateFile(req,file,fileUserPath);
    }

    /**
     *
     * @param req
     * @param file
     * @param fileUserPath   用户指定路径  只支持一级
     * @return
     */
    public static Map<String, String> privateFile(HttpServletRequest req, MultipartFile file, String fileUserPath)  {
        Map<String, String> uploadImageMap  =  new HashMap<String, String>();
        // 获取上传文件名字
        String oldFileName1 = file.getOriginalFilename();
        uploadImageMap.put("oldFileName", oldFileName1); //旧文件名
        String oldFileName2 = "";
        String lastFileName = "";
        String newfileName = "";
        if(oldFileName1.lastIndexOf(".") != -1){
        	 oldFileName2 = oldFileName1.substring(0,oldFileName1.lastIndexOf("."));
             lastFileName = oldFileName1.substring(oldFileName1.lastIndexOf("."),oldFileName1.length());
             newfileName =  oldFileName2+System.currentTimeMillis()+lastFileName;// 重命名上传后的文件名
        }else{
        	 newfileName =  oldFileName2+System.currentTimeMillis()+lastFileName+".jpg";// 重命名上传后的文件名
        }
       
        uploadImageMap.put("newfileName", newfileName);                                                       //新文件名
        if (file.isEmpty()) {
            uploadImageMap.put("data","上传资源为空");
            uploadImageMap.put("state",-1+"");
            return uploadImageMap;
        }
         //当 用户传入 制定路径不为Null时
        if(!fileUserPath.equals("")){
            //获取项目根路径下的upload绝对路径  ===> ps: /upload
            String objectPath = req.getSession().getServletContext().getRealPath("/") + "upload";
            objectPath= objectPath.replaceAll( "\\\\","/");
            //指定路径+新文件名.jpg
            String sqlPate= "upload\\"+fileUserPath +"\\"+ newfileName;
            File fileUpload = new File(objectPath);
            if (!fileUpload.exists()) {
                // 经过此方法后,项目根路径下的upload文件夹一定是存在的
                fileUpload.mkdir();
            }
            //指定用户的二级文件夹创建
            File fileUserUpload = new File(objectPath + "/" + fileUserPath);
            if (!fileUserUpload.exists()) {
                // 经过此方法后,用户指定的fileUserPath文件夹一定是存在的 ==》  ps：  /upload/img
                fileUserUpload.mkdir();
            }
            try{
                //上传本地tomcat路径+指定路径+新文件名.jpg
                String path = req.getSession().getServletContext().getRealPath("/") + sqlPate;
                path= path.replaceAll( "\\\\","/");
                file.transferTo(new File(path));                                                                 //上传图片

                uploadImageMap.put("data", "上传文件成功！");                                                   //文本提示
                sqlPate= sqlPate.replaceAll( "\\\\","/");
                uploadImageMap.put("sqlPate","/"+sqlPate);                                                          //数据库保存路径
                uploadImageMap.put("state",1+"");                                                               //状态   1  成功   -1 失败
                return uploadImageMap;
            }catch (Exception e){
                uploadImageMap.put("data", "上传文件失败！");                                                   //文本提示
                uploadImageMap.put("state",-1+"");                                                               //状态   1  成功   -1 失败
                e.printStackTrace();
            }

        }else {
            //获取项目根路径下的upload绝对路径  ===> ps: /upload
            String objectPath = req.getSession().getServletContext().getRealPath("/") + "upload";
            //指定路径+新文件名.jpg
            String sqlPate= "/upload/"+ newfileName;
            File fileUpload = new File(objectPath);
            if (!fileUpload.exists()) {
                // 经过此方法后,项目根路径下的upload文件夹一定是存在的
                fileUpload.mkdir();
            }
            try{
                //上传本地tomcat路径+指定路径+新文件名.jpg
                String path = req.getSession().getServletContext().getRealPath("/") + sqlPate;
                file.transferTo(new File(path));                                                                 //上传图片

                uploadImageMap.put("data", "上传文件 成功！");                                                   //文本提示
                uploadImageMap.put("sqlPate","/"+sqlPate);                                                          //数据库保存路径
                uploadImageMap.put("state",1+"");                                                               //状态   1  成功   -1 失败
                return uploadImageMap;
            }catch (Exception e){
                uploadImageMap.put("data", "上传文件失败！");                                                   //文本提示
                uploadImageMap.put("state",-1+"");                                                               //状态   1  成功   -1 失败
                e.printStackTrace();
            }
        }

        return uploadImageMap;
    }

    /**
     * 上传base64 单个图片
     * @param imgBase64 图片base64
     * @param  oldFileName 图片名称
     * @return
     */
    public static Map<String, String> privateBase64(HttpServletRequest request, String imgBase64, String Directory, String oldFileName){
        Map<String, String> uploadImageMap  =  new HashMap<String, String>();
        String oldFileName2 = oldFileName.substring(0,oldFileName.lastIndexOf("."));
        String lastFileName = oldFileName.substring(oldFileName.lastIndexOf("."),oldFileName.length());
        String fileName =  oldFileName2+System.currentTimeMillis()+lastFileName;// 重命名上传后的文件名
        uploadImageMap.put("oldFileName", oldFileName);
        //上传本地tomcat路径+指定路径+新文件名.jpg
        String path = request.getSession().getServletContext().getRealPath("/") + Directory.split("-")[0] + File.separator+ fileName;
        //上传本地tomcat路径+指定路径
        String filePath = request.getSession().getServletContext().getRealPath("/") + Directory.split("-")[0];
        //指定路径+新文件名.jpg
        String phoUrl = "/" + Directory.split("-")[1]+"/" + fileName;

        String header ="data:image";
        String[] imageArr=imgBase64.split(",");
        if(imageArr[0].contains(header)){//是img的
            // 去掉头部
            imgBase64=imageArr[1];
            //image = image.substring(header.length());
            // 写入磁盘
            BASE64Decoder decoder = new BASE64Decoder();
            try{
                byte[] decodedBytes = decoder.decodeBuffer(imgBase64);        //将字符串格式的image转为二进制流（biye[])的decodedBytes
                File targetFile = new File(filePath);
                if(!targetFile.exists()){
                    targetFile.mkdirs();
                }

                FileOutputStream out = new FileOutputStream(path);        //新建一个文件输出器，并为它指定输出位置imgFilePath
                out.write(decodedBytes); //利用文件输出器将二进制格式decodedBytes输出
                out.flush();
                out.close();                        //关闭文件输出器
                uploadImageMap.put("data", "上传文件成功！");
                uploadImageMap.put("path", path);
                uploadImageMap.put("sqlUrl", phoUrl);
                uploadImageMap.put("state",1+"");
                return uploadImageMap;
            }catch(Exception e){
                uploadImageMap.put("data", "上传文件失败！");
                uploadImageMap.put("state",-1+"");
                return uploadImageMap;
            }
        }else{
            uploadImageMap.put("data", "上传文件失败！");
            uploadImageMap.put("state",-1+"");
            return uploadImageMap;
        }
    }


    /**
     * MultipartFile 转 File
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile( @RequestParam MultipartFile file ) throws Exception {

        File toFile = null;
        if(file.equals("")||file.getSize()<=0){
            file = null;
        }else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    /**
     * File 转 MultipartFile
     * @param file
     * @throws Exception
     */
    public static void fileToMultipartFile( File file ) throws Exception {

        FileInputStream fileInput = new FileInputStream(file);
        MultipartFile toMultipartFile = new MockMultipartFile("file",file.getName(),"text/plain", IOUtils.toByteArray(fileInput));
        toMultipartFile.getInputStream();

    }

    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
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
     * File 转  byte
     * @param file
     * @return
     */
    public static byte[] getBytesByFile(File file) {
        try {

            //获取输入流
            FileInputStream fis = new FileInputStream(file);

            //新的 byte 数组输出流，缓冲区容量1024byte
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            //缓存
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            //改变为byte[]
            byte[] data = bos.toByteArray();
            //
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片路劲转bate
     * @param filePath
     * @return
     */
    public static byte[] getBytesByFile(String filePath) {
        try {
            File file=new File(filePath);
            //获取输入流
            FileInputStream fis = new FileInputStream(file);

            //新的 byte 数组输出流，缓冲区容量1024byte
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            //缓存
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            //改变为byte[]
            byte[] data = bos.toByteArray();
            //
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * byte[]转换为File，输入参数：文件byte[]，文件转换后的路径（不含文件名.格式），文件名称（包括格式）
     * @param bytes
     * @param filePath
     * @param fileName
     */
    public static void getFileByBytes(byte[] bytes, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            // 判断文件目录是否存在
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);

            //输出流
            fos = new FileOutputStream(file);

            //缓冲流
            bos = new BufferedOutputStream(fos);

            //将字节数组写出
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}