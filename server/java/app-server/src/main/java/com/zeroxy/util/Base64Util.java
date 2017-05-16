package com.zeroxy.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * base64 与 file 之间的相互转化
 * 实现形式, 懒汉式的单例模式
 */
public class Base64Util {
    // 将 file 转化为 Base64
    public static String fileToBase64(String path) {
        File file = new File(path);
        FileInputStream inputFile;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return new BASE64Encoder().encode(buffer);
        } catch (Exception e) {
            throw new RuntimeException("文件路径无效\n" + e.getMessage());
        }
    }

    // 将 base64 转化为 file
    public static boolean base64ToFile(String base64, String path) {
        // 文件字节数组字符串数据为空
        if (base64 == null)
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(base64);
            for (int i = 0; i < b.length; ++i) {
                {// 调整异常数据
                    if (b[i] < 0)
                        b[i] += 256;
                }
            }
            // 生成文件
            // String sangImageStr = "D:/My Documents/ip.jpg" ;  // 要生成文件的路径.
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}