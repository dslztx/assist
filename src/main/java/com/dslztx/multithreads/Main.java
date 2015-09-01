package com.dslztx.multithreads;


import com.dslztx.number.RadixConversionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 测试HTML语句<br/>
 * <ol>
 * <li>Item One
 * <li>Item two
 * <li>Item three
 * </ol>
 */
public class Main {
    public static void main(String[] args) {
        // System.out.println(21000000000);
        // System.out.println(0x7fffffffff);
        // System.out.println(07777777777777);

        System.out.println(21000000000L);
        System.out.println(0x7fffffffffL);
        System.out.println(07777777777777L);

        // System.out.println(-1.7E308);
        // System.out.println(1.7E308F);
        System.out.println(0xffffffff);
        System.out.println(0x1ffff);
        char c = 0xffff;
        System.out.println((int) c);

        byte b=127;
        byte bb=(byte)(b & 0xffff);
    }
}
