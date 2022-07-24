package com.lihd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * 为一个模块生成 目录
 * @author ：葬花吟留别1851053336@qq.com
 * @description：TODO
 * @date ：2021/11/9 21:54
 */
public class Utils {
    public static void main(String[] args) throws Exception{

        String path = "jdbc_3/src/";
        File f = new File(path + "contents.txt");
        if(f.exists()) {
            System.out.println("已经有文件了");
            return;
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path+"contents.txt")));
        File file = new File(path + "com");
        String name = "";

        A a = new A();
        getMaxLen(file,name,a);
        System.out.println(a.len);

        bw.write("这里是  内容 \n");
        run(file,name,bw,a.len);

        bw.write("\n--------------------------------------------------------------------\n\n");
        bw.flush();

    }

    public static void getMaxLen(File file,String name,A a){
        if(file.isDirectory()){
            String n = file.getName();
            File[] files = file.listFiles();

            for (File file1 : files) {
                if(name.length()==0){
                    getMaxLen(file1, n,a);
                }else {
                    getMaxLen(file1,name + "." + n,a);
                }
            }

        }else{
            int len =  (name + "." + file.getName()).length();
            if(len > a.len){
                a.len = len;
            }
        }

    }

    public static void run(File file, String name, BufferedWriter bw, int len) throws Exception{
        if(file.isDirectory()){
            //是目录
            String n = file.getName();
            File[] files = file.listFiles();

            bw.write("\n--------------------------------------------------------------------\n\n");

            bw.flush();
            for (File file1 : files) {
                if(name.length()==0){
                    run(file1, n, bw,len);
                }else {

                    run(file1,name + "." + n,bw,len);
                }
            }

        }else {
            //是文件
            String nn = name + "." + file.getName();
            int x = 0;
            if((len - nn.length())%4 != 0){
                x = 1;
            }

            int y = (len - nn.length())/4 + x;

            bw.write(nn+"\t");
            for (int i = 0; i < y; i++) {
                bw.write("\t");
            }
            bw.write(": \n");
        }
    }
}

class A{
    int len = 0;
}
