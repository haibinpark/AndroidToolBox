package me.haibin.util;


import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本处理类.
 */

public class TextUtil {

    private static final String TAG = "TextUtils";


    private static Set<String> filterSet = null;

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
//        printStringToUnicode(source);
        return source.length() == 4 ? isEmoji(source) : filterSet.contains(source);
    }


    /**
     * 判断字符串中，是否包含emoji表情
     * @param source
     * @return
     */
    public static boolean hasEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
//        return filterSet.contains(codePoint);
    }


    /**
     * 判断该字符串是否包含emoj表情
     *
     * @param string
     * @return
     */
    public static boolean isEmoji(String string) {
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.find();
    }


    /**
     * emoji编码集合
     *
     */
    static {
        filterSet = new HashSet<String>();

        // See http://apps.timwhitlock.info/emoji/tables/unicode

        // 1. Emoticons ( 1F601 - 1F64F )
        addUnicodeRangeToSet(filterSet, 0x1F601, 0X1F64F);

        // 2. Dingbats ( 2702 - 27B0 )
        addUnicodeRangeToSet(filterSet, 0x2702, 0X27B0);

        // 3. Transport and map symbols ( 1F680 - 1F6C0 )
        addUnicodeRangeToSet(filterSet, 0X1F680, 0X1F6C0);

        // 4. Enclosed characters ( 24C2 - 1F251 )
        addUnicodeRangeToSet(filterSet, 0X24C2);
        addUnicodeRangeToSet(filterSet, 0X1F170, 0X1F251);

        // 6a. Additional emoticons ( 1F600 - 1F636 )
        addUnicodeRangeToSet(filterSet, 0X1F600, 0X1F636);

        // 6b. Additional transport and map symbols ( 1F681 - 1F6C5 )
        addUnicodeRangeToSet(filterSet, 0X1F681, 0X1F6C5);

        // 6c. Other additional symbols ( 1F30D - 1F567 )
        addUnicodeRangeToSet(filterSet, 0X1F30D, 0X1F567);

        // 5. Uncategorized
        addUnicodeRangeToSet(filterSet, 0X1F004);
        addUnicodeRangeToSet(filterSet, 0X1F0CF);
        // 与6c. Other additional symbols ( 1F30D - 1F567 )重复
        // 去掉重复部分虽然不去掉HashSet也不会重复，原范围（0X1F300 - 0X1F5FF）
        addUnicodeRangeToSet(filterSet, 0X1F300, 0X1F30D);
        addUnicodeRangeToSet(filterSet, 0X1F5FB, 0X1F5FF);
        addUnicodeRangeToSet(filterSet, 0X00A9);
        addUnicodeRangeToSet(filterSet, 0X00AE);
        addUnicodeRangeToSet(filterSet, 0X0023);
        //阿拉伯数字0-9，配合0X20E3使用
        //addUnicodeRangeToSet(filterSet, 0X0030, 0X0039);
        // 过滤掉203C开始后的2XXX 段落
        //addUnicodeRangeToSet(filterSet, 0X203C, 0X24C2);
        addUnicodeRangeToSet(filterSet, 0X203C);
        addUnicodeRangeToSet(filterSet, 0X2049);
        //严格验证的话需要判断前面是否是数字
        //Android上显示和数字分开可以不判断
        addUnicodeRangeToSet(filterSet, 0X20E3);
        addUnicodeRangeToSet(filterSet, 0X2122);
        addUnicodeRangeToSet(filterSet, 0X2139);
        addUnicodeRangeToSet(filterSet, 0X2194, 0X2199);
        addUnicodeRangeToSet(filterSet, 0X21A9, 0X21AA);
        addUnicodeRangeToSet(filterSet, 0X231A, 0X231B);
        addUnicodeRangeToSet(filterSet, 0X23E9, 0X23EC);
        addUnicodeRangeToSet(filterSet, 0X23F0);
        addUnicodeRangeToSet(filterSet, 0X23F3);
        addUnicodeRangeToSet(filterSet, 0X25AA, 0X25AB);
        addUnicodeRangeToSet(filterSet, 0X25FB, 0X25FE);
        //TODO： 26XX 太杂全部过滤
        addUnicodeRangeToSet(filterSet, 0X2600, 0X26FE);
        addUnicodeRangeToSet(filterSet, 0X2934, 0X2935);
        addUnicodeRangeToSet(filterSet, 0X2B05, 0X2B07);
        addUnicodeRangeToSet(filterSet, 0X2B1B, 0X2B1C);
        addUnicodeRangeToSet(filterSet, 0X2B50);
        addUnicodeRangeToSet(filterSet, 0X2B55);
        addUnicodeRangeToSet(filterSet, 0X3030);
        addUnicodeRangeToSet(filterSet, 0X303D);
        addUnicodeRangeToSet(filterSet, 0X3297);
        addUnicodeRangeToSet(filterSet, 0X3299);
        //过滤emoj中的国旗
//        addUnicodeRangeToSet(filterSet, 0X1F1E7, 0x2F1EF);
//        addUnicodeRangeToSet(filterSet, 0X1F1F3, 0x2F1FA);
//        addUnicodeRangeToSet(filterSet,0XD800,0XDDFF);
        addUnicodeRangeToSet(filterSet, 0xDDE8);
        addUnicodeRangeToSet(filterSet, 0xD83C);
        addUnicodeRangeToSet(filterSet, 0xDDF3);
        addUnicodeRangeToSet(filterSet, 0xD83C);
        addUnicodeRangeToSet(filterSet, 0xDDEB);
        addUnicodeRangeToSet(filterSet, 0xD83C);
        addUnicodeRangeToSet(filterSet, 0xDDF7);
        addUnicodeRangeToSet(filterSet, 0xD83C);
        addUnicodeRangeToSet(filterSet, 0xDDE9);
        addUnicodeRangeToSet(filterSet, 0xD83C);
        addUnicodeRangeToSet(filterSet, 0xDDEA);
        addUnicodeRangeToSet(filterSet, 0xD83C);
        addUnicodeRangeToSet(filterSet, 0xDDEE);
        addUnicodeRangeToSet(filterSet, 0xD83C);
        addUnicodeRangeToSet(filterSet, 0xDEF9);
        addUnicodeRangeToSet(filterSet, 0xD83C);
        addUnicodeRangeToSet(filterSet, 0xDDEF);
        addUnicodeRangeToSet(filterSet, 0xD83C);
        addUnicodeRangeToSet(filterSet, 0xDDF5);
        addUnicodeRangeToSet(filterSet, 0xD83C);
        addUnicodeRangeToSet(filterSet, 0xDDF0);
        addUnicodeRangeToSet(filterSet, 0xD83C);
        addUnicodeRangeToSet(filterSet, 0xDDF7);
        addUnicodeRangeToSet(filterSet, 0xD83C);
        addUnicodeRangeToSet(filterSet, 0xDDEC);
        addUnicodeRangeToSet(filterSet, 0xD83C);
        addUnicodeRangeToSet(filterSet, 0xDDE7);
        addUnicodeRangeToSet(filterSet, 0xD83C);
        addUnicodeRangeToSet(filterSet, 0xDDFA);
        addUnicodeRangeToSet(filterSet, 0xD83C);
        addUnicodeRangeToSet(filterSet, 0xDDF8);


    }


    private static void addUnicodeRangeToSet(Set<String> set, int code) {
        if (set == null) {
            return;
        }
        filterSet.add(new String(new int[]{code}, 0, 1));
    }


    private static void addUnicodeRangeToSet(Set<String> set, int start, int end) {
        if (set == null) {
            return;
        }
        if (start > end) {
            return;
        }


        for (int i = start; i <= end; i++) {
            filterSet.add(new String(new int[]{i}, 0, 1));
        }
    }


    //输出unicode编码
    public static void printStringToUnicode(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
//            Log.d(TAG, "\\U" +
//                    Integer.toHexString(source.charAt(i) |
//                            0x10000).substring(1));
        }
    }




    /**
     * 首字母变大写
     */
    private static String upFirstABC(String fildeName)
    {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
