package com.fsc.generate.utils;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class StringUtils {

    private static Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");

    public static String fillTemplate(String template, List<Object> params) {
        for (int i = 0; i < params.size(); i++) {
            template = template.replaceFirst("@@@", String.valueOf(params.get(i)));
        }
        return template;
    }

    public static boolean isInteger(String str) {
        return pattern.matcher(str).matches();
    }

    public static String nullToEmpty(String str) {
        return Objects.isNull(str) ? "" : str;
    }
}
