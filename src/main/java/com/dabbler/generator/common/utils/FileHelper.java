package com.dabbler.generator.common.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class FileHelper {
    private FileHelper() {
        throw new UnsupportedOperationException();
    }


    public static boolean createIfNotExists(String filePath)throws IOException{
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()){
            parentDir.mkdirs();
        }
        return file.createNewFile();
    }

    /**
     * 递归获取所有的文件
     * @param file
     * @return
     */
    public static List<File> listFiles(File file){
        Preconditions.checkArgument(file.exists(),"文件不存在");
        List<File> files = Lists.newArrayList();
        recursionFiles(file,files);
        return files;
    }

    /**
     * 递归获取文件
     * @param file
     * @param resultFile
     */
    private static void recursionFiles(File file,List<File>resultFile){
       if (file.isFile()){
           resultFile.add(file);
           return;
       }
       File[] files = file.listFiles();
       for(File tmpFile:files){
           recursionFiles(tmpFile,resultFile);
       }
    }


    public static Path getRelativePath(File srcFile,File descFile){
        Path srcPath = srcFile.toPath();
        Path descPath = descFile.toPath();
        return descPath.relativize(srcPath);
    }
}
