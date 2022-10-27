package io.github.telechow.sign.in.sheet.util;

import java.nio.ByteOrder;

/**
 * 字节数据转换工具类
 *
 * @author Telechow
 * @since 2022/10/21 17:40
 */
public class ByteConvertUtils {

    /**
     * 将 长整型 转换成 字节数组<br>
     * 默认以小端序转换<br>
     *
     * @param longValue 长整型数据
     * @return byte[] 字节数组
     * @author Telechow
     * @since 2022/10/21 17:43
     */
    public static byte[] longToBytes(long longValue) {
        return longToBytes(longValue, ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * 将 长整型 转换成 字节数组<br>
     *
     * @param longValue 长征型数据
     * @param byteOrder 端序
     * @return byte[] 字节数组
     * @author Telechow
     * @since 2022/10/21 17:44
     */
    public static byte[] longToBytes(long longValue, ByteOrder byteOrder) {
        byte[] result = new byte[Long.BYTES];
        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            for (int i = 0; i < result.length; i++) {
                result[i] = (byte) (longValue & 0xFF);
                longValue >>= Byte.SIZE;
            }
        } else {
            for (int i = (result.length - 1); i >= 0; i--) {
                result[i] = (byte) (longValue & 0xFF);
                longValue >>= Byte.SIZE;
            }
        }
        return result;
    }

    /**
     * 将 字节数组 转换成 长整型<br>
     * 默认以小端序转换<br>
     * 默认从第0个字节开始转换，且超过8字节的部分将会被忽略<br>
     *
     * @param bytes 字节数组
     * @return long 长征型数字
     * @author Telechow
     * @since 2022/10/21 18:19
     */
    public static long bytesToLong(byte[] bytes) {
        return bytesToLong(bytes, 0, ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * 将 字节数组 转换成 长整型<br>
     *
     * @param bytes     字节数组
     * @param start     开始字节索引
     * @param byteOrder 端序
     * @return long 长整型数组
     * @author Telechow
     * @since 2022/10/21 18:22
     */
    public static long bytesToLong(byte[] bytes, int start, ByteOrder byteOrder) {
        long values = 0;
        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            for (int i = (Long.BYTES - 1); i >= 0; i--) {
                values <<= Byte.SIZE;
                values |= (bytes[i + start] & 0xff);
            }
        } else {
            for (int i = 0; i < Long.BYTES; i++) {
                values <<= Byte.SIZE;
                values |= (bytes[i + start] & 0xff);
            }
        }
        return values;
    }
}
