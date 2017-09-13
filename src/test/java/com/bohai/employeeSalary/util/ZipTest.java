package com.bohai.employeeSalary.util;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class ZipTest {
    
    public static void main(String[] args) {
        
        File f = new File("C:/Users/cxy/Desktop/曹星昱.docx");
        
        File f1 = new File("C:/Users/cxy/Desktop/工资表.xlsx");
        
        List<File> files= Arrays.asList(f,f1);
        
        File zipFile = new File("C:/Users/cxy/Desktop/test.zip");
        ZipUtil.zipFiles(files, zipFile);
    }

}
