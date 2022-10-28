/*
 * Copyright 2022, telechow(laughho@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    /**
     * 将 短整型 转换成 字节数组<br>
     * 默认以小端序转换<br>
     *
     * @param shortValue 短整型数据
     * @return byte[] 字节数组
     * @author Telechow
     * @since 2022/10/28 11:29
     */
    public static byte[] shortToBytes(short shortValue) {
        return shortToBytes(shortValue, ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * 将 短整型 转换成 字节数组<br>
     *
     * @param shortValue 短整型数据
     * @param byteOrder  端序
     * @return byte[] 字节数组
     * @author Telechow
     * @since 2022/10/28 11:30
     */
    public static byte[] shortToBytes(short shortValue, ByteOrder byteOrder) {
        byte[] b = new byte[Short.BYTES];
        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            b[0] = (byte) (shortValue & 0xff);
            b[1] = (byte) ((shortValue >> Byte.SIZE) & 0xff);
        } else {
            b[1] = (byte) (shortValue & 0xff);
            b[0] = (byte) ((shortValue >> Byte.SIZE) & 0xff);
        }
        return b;
    }

    /**
     * 将 字节数组 转换成 短整型<br>
     * 默认以小端序转换<br>
     *
     * @param bytes 字节数组
     * @return short 短整型数据
     * @author Telechow
     * @since 2022/10/28 14:33
     */
    public static short bytesToShort(byte[] bytes) {
        return bytesToShort(bytes, ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * 将 字节数组 转换成 短整型<br>
     *
     * @param bytes     字节数组
     * @param byteOrder 端序
     * @return short 短整型数据
     * @author Telechow
     * @since 2022/10/28 14:34
     */
    public static short bytesToShort(byte[] bytes, ByteOrder byteOrder) {
        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            return (short) (bytes[0] & 0xff | (bytes[1] & 0xff) << Byte.SIZE);
        } else {
            return (short) (bytes[1] & 0xff | (bytes[0] & 0xff) << Byte.SIZE);
        }
    }
}
