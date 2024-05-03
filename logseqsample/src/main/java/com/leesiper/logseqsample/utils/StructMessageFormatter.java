package com.leesiper.logseqsample.utils;

import java.util.Map;

public class StructMessageFormatter {

    private static final char[] Delimiter = {',',' ','.','。'};
    public static String formatAssign(Map<String, Object> dist, String format, final Object[] argArray) {
        if(argArray==null || argArray.length==0)
            return format;

        int scanIdx = 0;
        int endIdx = format.length();
        int arrIndex = 0;
        StringBuilder sb = new StringBuilder(endIdx + 50);
        while (scanIdx < endIdx) {
            if (format.charAt(scanIdx) == '{' && format.charAt(scanIdx + 1) == '}') {
                var result = findArgumentKey(format, scanIdx);
                if (!result.Key.isEmpty()) {
                    dist.put(result.Key, argArray[arrIndex]);
                    sb.delete(sb.length() - result.Length, sb.length());
                    sb.append(argArray[arrIndex]);
                }
                arrIndex++;
                scanIdx++;
            } else {
                sb.append(format.charAt(scanIdx));
            }
            scanIdx++;
        }
        return sb.toString();
    }

    private static result findArgumentKey(String s, int scanIdx) {
        if (scanIdx == 0)
            return new result("", 0);
        StringBuilder keySb = new StringBuilder(10);
        var idx = scanIdx - 1;
        if (s.charAt(idx) == '=') {
            while (idx >= 0) {
                idx--;
                if (isBorder(s.charAt(idx))) {
                    break;
                }

                keySb.insert(0, s.charAt(idx));
            }
            return new result(keySb.toString(), keySb.length() + 1);
        }
        else{
            return new result("prop_"+scanIdx,0);
        }
    }
    private static boolean isBorder(char ch){
        for(var c : Delimiter){
            if(c==ch)
                return true;
        }
        return false;
    }

    private record result(String Key, int Length) {
    }
}

