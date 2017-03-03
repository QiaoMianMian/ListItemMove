package com.list.move;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class StringUtils {

    //obj2String
    public static String obj2String(Object obj) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            String str = bos.toString("ISO-8859-1");
            str = java.net.URLEncoder.encode(str, "UTF-8");
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //str2Object
    public static Object str2Object(String str) {
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            String redStr = java.net.URLDecoder.decode(str, "UTF-8");
            bis = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
