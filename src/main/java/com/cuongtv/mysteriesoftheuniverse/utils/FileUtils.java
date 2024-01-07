package com.cuongtv.mysteriesoftheuniverse.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class FileUtils {
    // Set a maximum file size limit (e.g., 5MB)
    private static long maxSizePost = 5 * 1024 * 1024;
    private static long maxSizeAvatar = 10 * 1024 * 1024;




    public static String downloadUploadFile(HttpServletRequest req){
        String fileName = null;
        try{
            Part part = req.getPart("image");
            long fileSize = part.getSize(); // Get the size of the uploaded file in bytes

            if (fileSize > maxSizePost) {
               return "File size error!";
            } else {
                String realPath = req.getServletContext().getRealPath("\\");
                String submitFileName = Path.of(part.getSubmittedFileName()).getFileName().toString();
                String []split = submitFileName.split("\\.");

                fileName = "\\images\\post\\" + LocalDateTime.now();
                fileName = fileName.replace(":","_");
                fileName = fileName.replace(".","_");

                if ("".equals(submitFileName)){
                    return null;
                }

                if (!"jpg".equals(split[split.length-1]) && !"png".equals(split[split.length-1])){
                    fileName += "."+split[split.length-1];

                    return "File image error!";
                }
                fileName += "."+split[split.length-1];

                if (!Files.exists(Path.of(realPath))){
                    Files.createDirectories(Path.of(realPath));
                }

                part.write(realPath+fileName);
            }

        }
        catch (Exception e){
            System.out.println("Cannot download file!");
            System.out.println(" -- "+ e);
        }
        return fileName;
    }

    public static String downloadAvatar(HttpServletRequest req,String avatarName){
        String fileName = null;
        try{
            Part part = req.getPart("image");
            long fileSize = part.getSize(); // Get the size of the uploaded file in bytes

            if (fileSize > maxSizePost) {
                return "File size error!";
            } else {
                String realPath = req.getServletContext().getRealPath("\\");
                String submitFileName = Path.of(part.getSubmittedFileName()).getFileName().toString();
                String []split = submitFileName.split("\\.");

                fileName = "\\images\\avatar\\" + LocalDateTime.now();
                fileName = fileName.replace(":","_");
                fileName = fileName.replace(".","_");

                if ("".equals(submitFileName)){
                    return null;
                }
                if (!"jpg".equals(split[split.length-1]) && !"png".equals(split[split.length-1])){
                    fileName += "."+split[split.length-1];
                    return "File image error!";
                }
                fileName += "."+split[split.length-1];


                if (!Files.exists(Path.of(realPath))){
                    Files.createDirectories(Path.of(realPath));
                }

                if(!avatarName.equals("default.png")){
                    try{
                        Path path = Paths.get(avatarName);
                        Files.delete(path);
                        System.out.println("File deleted successfully.");
                    }catch (IOException e){
                        System.out.println("Failed to delete the file: " + e.getMessage());
                    }
                }
                part.write(realPath+fileName);
                return fileName;

            }

        }
        catch (Exception e){
            System.out.println("Cannot download file!");
            System.out.println(" -- "+ e);
        }
        return fileName;
    }
}
