package me.haibin.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;

/**
 * Created by ParkZhao on 16/12/9.
 */

public class FileUtil {


    /**
     * 删除文件夹下所有文件
     *
     * @param path
     * @return
     */
    public static boolean delAllFile(String path) {
        // 删除指定文件夹下所有文件
        // param path 文件夹完整绝对路径
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }


    /**
     * 删除指定文件夹
     *
     * @param folderPath
     */
    public static void delFolder(String folderPath) {
        // 删除文件夹
        // param folderPath 文件夹完整绝对路径
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {
        }
    }


    /**
     * 获取文件基本信息
     */
    public static BasicFileAttributes getFileInfo(Path path) throws IOException {
        return Files.readAttributes(path, BasicFileAttributes.class);
    }

    /**
     * 获取文件基本信息
     */
    public static BasicFileAttributes getFileInfo(String path) throws IOException {
        return getFileInfo(Paths.get(path));
    }

    /**
     * 需要系统支持POSIX文件系统
     * 一般只在类Unix系统下有效
     */
    public static PosixFileAttributes getPosixFileInfo(Path path) throws IOException {
        return Files.readAttributes(path, PosixFileAttributes.class);
    }


    //---------------中等大小文件save、delete、move、copy操作--------------------------------------------------------------//


    public static FileUtil.Save save(String str) {
        return save(str.getBytes());
    }

    public static FileUtil.Save save(byte[] bytes) {
        return new FileUtil.Save(bytes);
    }

    public static FileUtil.Append append(String str) {
        return append(str.getBytes());
    }

    public static FileUtil.Append append(byte[] bytes) {
        return new FileUtil.Append(bytes);
    }

    public static FileUtil.Move move(File file) {
        return move(file.toPath());
    }

    public static FileUtil.Move move(String path) {
        return move(Paths.get(path));
    }

    public static FileUtil.Move move(Path path) {
        return new FileUtil.Move(path);
    }

    public static FileUtil.Copy copy(Path path) {
        return new FileUtil.Copy(path);
    }

    public static FileUtil.Copy copy(File file) {
        return copy(file.toPath());
    }

    public static FileUtil.Copy copy(String path) {
        return copy(Paths.get(path));
    }

    public static boolean delete(File file) {
        return delete(file.toPath());
    }

    public static boolean delete(String path) {
        Path p = Paths.get(path);
        return delete(p);
    }

    public static boolean delete(Path path) {
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static class Append {
        byte[] bytes;

        Append(byte[] bytes) {
            this.bytes = bytes;
        }

        public boolean to(String path) {
            return to(Paths.get(path));
        }

        public boolean to(File file) {
            return to(file.getPath());
        }

        public boolean to(Path path) {
            if (path == null || bytes == null) {
                return false;
            }

            try {
                Files.write(path, bytes, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static class Save {
        byte[] bytes;

        Save(byte[] bytes) {
            this.bytes = bytes;
        }

        public boolean replaceTo(String path) {
            return replaceTo(Paths.get(path));
        }

        public boolean replaceTo(File file) {
            return replaceTo(file.toPath());
        }

        public boolean replaceTo(Path path) {

            return saveTo(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        }

        public boolean to(String path) {
            return to(Paths.get(path));
        }

        public boolean to(File file) {
            return to(file.getPath());
        }

        public boolean to(Path path) {
            return saveTo(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
        }

        public boolean saveTo(Path path, OpenOption... openOptions) {
            if (bytes == null || path == null) {
                return false;
            }

            try {
                Files.write(path, bytes, openOptions);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

    }

    public static class Copy extends FileUtil.I {

        public Copy(Path from) {
            super(from);
        }

        @Override
        public boolean replaceTo(Path path) {
            return copyTo(path, REPLACE);
        }

        @Override
        public boolean to(Path path) {
            return copyTo(path, NO_REPLACE);
        }

        public boolean to(OutputStream outputStream) {
            if (from == null || outputStream == null) {
                return false;
            }

            try {
                Files.copy(from, outputStream);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        private boolean copyTo(Path path, CopyOption[] copyOptions) {
            if (path == null || from == null) {
                return false;
            }

            try {
                Files.copy(from, path, copyOptions);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static class Move extends FileUtil.I {
        public Move(Path from) {
            super(from);
        }

        @Override
        public boolean replaceTo(Path path) {
            return moveTo(path, REPLACE);
        }

        @Override
        public boolean to(Path path) {
            return moveTo(path, NO_REPLACE);
        }

        protected boolean moveTo(Path path, CopyOption[] copyOptions) {
            if (path == null || from == null) {
                return false;
            }

            try {
                Files.move(from, path, copyOptions);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    static abstract class I {
        Path from;

        final CopyOption[] NO_REPLACE =
                {StandardCopyOption.COPY_ATTRIBUTES};

        final CopyOption[] REPLACE =
                {StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING};

        I(Path from) {
            this.from = from;
        }

        public boolean replaceTo(String path) {
            return replaceTo(Paths.get(path));
        }

        public boolean replaceTo(File file) {
            return replaceTo(file.toPath());
        }

        abstract boolean replaceTo(Path path);

        public boolean to(String path) {
            return to(Paths.get(path));
        }

        public boolean to(File file) {
            return to(file.toPath());
        }

        abstract boolean to(Path path);


    }


}
