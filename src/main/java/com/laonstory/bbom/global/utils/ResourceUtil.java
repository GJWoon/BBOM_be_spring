package com.laonstory.bbom.global.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;


@Slf4j
@Component
public class ResourceUtil {


    private static String PATH;

    private static final String LIVE_PHOTO_EXE = "HEIC";

    @Value("${app.filePath}")
    public void setUploadPath(String path) {
        PATH = path;
    }

    public static String saveFile(MultipartFile file, String location, String folderPath) throws IOException {



        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        String loc = PATH + location;

        File targetFile = new File(Objects.equals(extension, LIVE_PHOTO_EXE) ? loc.replace(LIVE_PHOTO_EXE, "jpg") : loc);

        File folder = new File(PATH + folderPath);

        log.info("check extension ---> {} / {}", extension, LIVE_PHOTO_EXE);


        log.info(loc);

        if (!folder.exists()) {
            log.info(folder.getAbsolutePath());
            folder.mkdirs();
        }

        file.transferTo(targetFile);

        return loc;
    }

    public static void deleteFile(String filePath) {

        File file = new File(PATH + filePath);

        if (file.exists()) {
            file.delete();
        }
    }




    public static Map<String,String> saveImageListMap(Map<String,MultipartFile> detailImages, Long id, String type) {


        Set<String> imageKey = detailImages.keySet();

        Map<String,String> imagePath = new HashMap<>();

            imageKey.forEach(e -> {



                if (detailImages.get(e) != null) {

                    String location = "/images/" + type + "/" + id + "/" + detailImages.get(e).getOriginalFilename();

                    imagePath.put(e,location);

                    String folderPath = "/images/" + type + "/" + id + "/";

                    try {

                        ResourceUtil.saveFile(detailImages.get(e), location, folderPath);

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }else imagePath.put(e,null);


            });

        return imagePath;

    }








    public static List<String> saveImageList (List<MultipartFile> images, Long id, String type) {


        List<String> imageListPath = new ArrayList<>();

        if (images != null) {

            images.forEach(e -> {

                if (e != null) {
                    String location = "/images/" + type + "/" + id + "/" + e.getOriginalFilename();

                    String folderPath = "/images/" + type + "/" + id + "/";

                    try {

                        ResourceUtil.saveFile(e, location, folderPath);

                        imageListPath.add(location);

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }

            });
        }
        return imageListPath;

    }

    public static String createCode(){

        Random rnd =new Random();

        StringBuffer buf =new StringBuffer();

        for(int i=0;i<8;i++){

            if(rnd.nextBoolean()){

                buf.append((char)((int)(rnd.nextInt(26))+97));

            }else{

                buf.append((rnd.nextInt(10)));

            }

        }

        return buf.toString();
    }

    public static byte[] convertFileContentToBlob (String filePath) {

        byte[] result = null;

        try {
            result = FileUtils.readFileToByteArray( new File(filePath));

        } catch (IOException ie) {
            log.error("file convert Error");

        }
        return result;
    }

    public static String convertBlobToBase64 (byte[] blob) {

        return new String(Base64.getEncoder().encode(blob));

    }
    public static String getFileContent (String filePath) {

        byte[] filebyte = convertFileContentToBlob(filePath);

        return convertBlobToBase64(filebyte); }

}
