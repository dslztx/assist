package me.dslztx.assist.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

/**
 * @author dslztx
 */
public class CharCodingAssist {

    private static final Pattern pattern = Pattern.compile("<meta charset=\"?([a-zA-Z0-9-]+?)\"?>");

    /**
     * 以特定编码方案编码字符串，返回编码得到的字节流
     */
    public static byte[] encode(String str, Charset charset) {
        if (StringAssist.isBlank(str)) {
            return null;
        }

        if (charset == null) {
            throw new RuntimeException("charset is null");
        }

        return str.getBytes(charset);
    }

    public static Charset detectHTMLEncoding(byte[] htmlData) {

        if (ArrayAssist.isEmpty(htmlData)) {
            return null;
        }

        try {
            int length = 50;
            if (htmlData.length < 50) {
                length = htmlData.length;
            }

            String headChars = new String(htmlData, 0, length, "ASCII");

            Matcher matcher = pattern.matcher(headChars);

            if (matcher.find()) {
                String name = matcher.group(1);

                return Charset.forName(name);
            }
        } catch (Exception ignore) {
        }

        return null;
    }

    /**
     * 以特定编码方案解码字节流，返回解码得到的字符串
     */
    public static String decode(byte[] bytes, Charset charset) {
        if (ArrayAssist.isEmpty(bytes)) {
            return null;
        }

        if (charset == null) {
            throw new RuntimeException("charset is null");
        }

        return new String(bytes, charset);
    }

    public static String unescapedHTMLEscapeSequence(String s) {
        if (StringAssist.isBlank(s)) {
            return s;
        }

        StringBuilder sb = new StringBuilder();
        StringBuilder buffer = new StringBuilder();

        char c;

        for (int index = 0; index < s.length(); index++) {
            c = s.charAt(index);
            if (c == '&') {
                if (buffer.length() == 0) {
                    buffer.append(c);
                } else {
                    sb.append(buffer.toString());

                    buffer.setLength(0);
                    buffer.append(c);
                }
            } else if (c == '#') {
                if (buffer.length() == 1) {
                    buffer.append(c);
                } else {
                    if (buffer.length() > 0) {
                        sb.append(buffer.toString());
                        buffer.setLength(0);
                    }

                    sb.append(c);
                }
            } else if (c == 'x' || c == 'X') {
                if (buffer.length() == 2) {
                    buffer.append(c);
                } else {
                    if (buffer.length() > 0) {
                        sb.append(buffer.toString());
                        buffer.setLength(0);
                    }

                    sb.append(c);
                }
            } else if (CharAssist.isDigitChar(c) || CharAssist.isHexChar(c)) {
                if (CharAssist.isDigitChar(c)) {
                    if (buffer.length() >= 2) {
                        buffer.append(c);
                    } else {
                        if (buffer.length() > 0) {
                            sb.append(buffer.toString());
                            buffer.setLength(0);
                        }

                        sb.append(c);
                    }
                } else {
                    if (buffer.length() > 2 && (buffer.charAt(2) == 'X' || buffer.charAt(2) == 'x')) {
                        buffer.append(c);
                    } else {
                        if (buffer.length() > 0) {
                            sb.append(buffer.toString());
                            buffer.setLength(0);
                        }

                        sb.append(c);
                    }
                }
            } else if (c == ';') {
                if (buffer.length() > 2) {
                    if (buffer.length() == 3 && (buffer.charAt(2) == 'x' || buffer.charAt(2) == 'X')) {
                        sb.append(buffer.toString());
                        buffer.setLength(0);

                        sb.append(c);
                    } else {
                        try {
                            buffer.append(c);
                            sb.append(charFromHTMLEscapeSequence(buffer.toString()));
                        } catch (NumberFormatException e) {
                            sb.append(buffer.toString());
                        }

                        buffer.setLength(0);
                    }
                } else {
                    if (buffer.length() > 0) {
                        sb.append(buffer.toString());
                        buffer.setLength(0);
                    }

                    sb.append(c);
                }
            } else {
                if (buffer.length() > 0) {
                    sb.append(buffer.toString());
                    buffer.setLength(0);
                }
                sb.append(c);
            }
        }

        if (buffer.length() > 0) {
            sb.append(buffer.toString());
            buffer.setLength(0);
        }

        return sb.toString();
    }

    public static String escapeHTMLEscapeSequence(String s) {
        if (StringAssist.isEmpty(s)) {
            return s;
        }

        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < s.length(); index++) {
            sb.append(charToHTMLEscapeSequence(s.charAt(index)));
        }

        return sb.toString();
    }

    /**
     * 扩展的，不局限于ASCII编码集
     */
    public static char charFromHTMLEscapeSequence(String s) throws NumberFormatException {
        if (StringAssist.isEmpty(s) || s.length() < 4) {
            throw new NumberFormatException("illegal html escape sequence");
        }

        if (s.charAt(0) != '&' || s.charAt(1) != '#' || s.charAt(s.length() - 1) != ';') {
            throw new NumberFormatException("illegal html escape sequence");
        }

        int start = 2;
        int end = s.length() - 2;

        if (s.charAt(start) == 'x' || s.charAt(start) == 'X') {
            if (start + 1 == end) {
                throw new NumberFormatException("illegal html escape sequence");
            }

            int value = NumberAssist.hexStrToDec(s, start + 1, end);
            return (char)value;
        } else {
            int value = NumberAssist.decStrToDec(s, start, end);
            return (char)value;
        }
    }

    /**
     * 扩展的，不局限于ASCII编码集
     */
    public static String charToHTMLEscapeSequence(char c) {
        return "&#" + (int)c + ";";
    }

    public static boolean isFileNotEncodedWith(File file, Charset charset) throws IOException {
        if (file == null || !file.exists()) {
            throw new RuntimeException("file not exists");
        }

        if (charset == null) {
            throw new RuntimeException("charset not specify");
        }

        byte[] direct = IOUtils.toByteArray(new FileInputStream(file));

        String ss = new String(direct, charset);

        byte[] indirect = ss.getBytes(charset);

        return !Arrays.equals(direct, indirect);
    }

    public static boolean isGBKCompatible(byte[] bb) {
        if (ArrayAssist.isEmpty(bb)) {
            return true;
        }

        try {
            return Arrays.equals(bb, new String(bb, "GBK").getBytes("GBK"));
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isUTF8Compatible(byte[] bb) {
        if (ArrayAssist.isEmpty(bb)) {
            return true;
        }

        try {
            return Arrays.equals(bb, new String(bb, "UTF-8").getBytes("UTF-8"));
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        String a = "�";
        String b =
            CharCodingAssist.decode(CharCodingAssist.encode("�", Charset.forName("GBK")), Charset.forName("GBK"));
        System.out.println(a.equals(b));

        char c = '好';
        System.out.println((int)c);

        String content = "<meta charset=UTF-8>";

        Matcher matcher = pattern.matcher(content);

        System.out.println(matcher.find());
        System.out.println(matcher.group(0));
        System.out.println(matcher.group(1));
    }
}
