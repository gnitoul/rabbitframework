package com.rabbitframework.commons.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author justin.liang
 */
public class CodecUtils extends DigestUtils {
    public static String md5ToStr(String data) {
        return md5Hex(data.getBytes());
    }
}
