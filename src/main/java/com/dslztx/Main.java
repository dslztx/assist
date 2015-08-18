package com.dslztx;

import com.dslztx.character.CodeUtils;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @date 2015年5月21日
 * @author dslztx
 */
public class Main {
    public static void main(String[] args) throws IOException {
        // System.out.println(~1);
        // System.out.println(~((byte) -1));
        // System.out.println((byte) -0);
        // System.out.println("\n");
        // System.out.println("hello");
        Pattern hexPattern = Pattern.compile("^0x[0-9a-fA-F]+$");
        System.out.println(hexPattern.matcher("0xfda3fdafda0").find());
        Pattern octPattern = Pattern.compile("0[0-7]+");
        System.out.println(octPattern.matcher("04324632").find());

        String a = "0xffff";
        String b = "0xaaa";
        String c = "0777";
        System.out.println(Integer.parseInt(a.substring(2), 16));
        System.out.println(Integer.parseInt(b.substring(2), 16));
        System.out.println(Integer.parseInt(c, 8));
        String hello = "hello";
        System.out.println(hello.indexOf('l', 4));
        System.out.println("".substring(0, 0));
        byte[] bb = CodeUtils.encode("称", CodeUtils.CodeMethod.UTF8);
        for (int i = 0; i < bb.length; i++)
            System.out.println(Integer.toHexString(bb[i]));
    }
}
