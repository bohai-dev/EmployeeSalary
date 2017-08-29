package com.bohai.employeeSalary.util;

import java.io.File;

public class ZipTest {
    
    public static void main(String[] args) {
        
        File f = new File("C:/Users/BHQH-CXYWB/Desktop/期货合约代码对应改.xlsx");
        
        File f1 = new File("C:/Users/BHQH-CXYWB/Desktop/渤海期货股份有限公司OA系统评估报告.doc");
        
        File[] files = {f,f1};
        
        File zipFile = new File("C:/123.zip");
        ZipUtil.zipFiles(files, zipFile);
    }

}
