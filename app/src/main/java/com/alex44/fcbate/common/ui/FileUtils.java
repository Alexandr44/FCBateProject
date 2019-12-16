package com.alex44.fcbate.common.ui;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

public class FileUtils {

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            final String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public static String getHumanableSize(long size, List<String> labels) {
        if (size <= 0) return "0 " + (labels.size()>=1?labels.get(0):"");
        final int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " +
                (labels.size()>digitGroups?labels.get(digitGroups):"");
    }

    public static long getDirSize(File dir){
        long size = 0;
        for (File file : dir.listFiles()) {
            if (file != null && file.isDirectory()) {
                size += getDirSize(file);
            } else if (file != null && file.isFile()) {
                size += file.length();
            }
        }
        return size;
    }

}
