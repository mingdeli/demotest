package com.ldm.demoes;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        int i=1;
        i = i ++;
        int j = i++;
        int k =  ++i * i++ +i * i++ - i;

        System.out.println(i+"==="+j + "=="+k);

    }
}
