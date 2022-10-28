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
package io.github.telechow.sign.in.sheet.impl;

import io.github.telechow.sign.in.sheet.SignInSheetSerializable;
import io.github.telechow.sign.in.sheet.YearlySignInSheet;
import io.github.telechow.sign.in.sheet.exception.ByteArrayDataLengthException;
import io.github.telechow.sign.in.sheet.exception.SignInYearNotMatchException;
import io.github.telechow.sign.in.sheet.util.ByteConvertUtils;
import lombok.Data;

import java.time.*;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 年度签到表（不记录每次签到的时间，只记录日期）
 * <li color=red>如果你只需要记录一年内哪一天签到了，哪一天没有签到，而不需要记录每天什么时间签到；那么使用此类将可以完全满足要求，且节约存储空间</li>
 * 此签到表只记录日期，不记录时间，我们可以使用bitset来最小化存储；即每人每年的签到数据最多366bit（最多需46Byte存储），再用一个short型存储年份，总共最多占48字节，最少占2字节<br>
 * 按照上述理论，此类初始化一个对象时，字节数组只占2字节，但是我们依旧使用48字节去存储，防止数组扩容对性能带来影响<br>
 * 为了签到册序列化与反序列化时保证自描述性，年份也将被序列化至字节数组中；如果是存储在数据库中，可以自行将年份再存储到一个字段中，查询时方便走索引<br>
 *
 * @author Telechow
 * @since 2022/10/28 10:53
 */
@Data
public class YearlySignInSheetWithoutTime implements YearlySignInSheet, SignInSheetSerializable {

    private static final long serialVersionUID = 3693492867497168042L;

    /**
     * 签到册的年份
     */
    private short year;

    /**
     * 签到册存储每天是否签到的BitSet，如果签到则为1，未签到则为0
     */
    private BitSet sheetBitSet;

    /**
     * short型占用的字节数
     */
    private static final int SHORT_BYTE_LENGTH = 2;

    /**
     * bitset的字节数组最大长度，365或366天对应365或366bit，对应46Byte
     */
    private static final int BITSET_BYTE_ARRAY_MAX_LENGTH = 46;

    /**
     * 构造方法
     *
     * @param year        年份
     * @param sheetBitSet 签到册bitset
     * @author Telechow
     * @since 2022/10/28 14:42
     */
    public YearlySignInSheetWithoutTime(short year, BitSet sheetBitSet) {
        this.year = year;
        this.sheetBitSet = sheetBitSet;
    }

    /**
     * 使用 字节数组 得到一个 年度签到表（不记录每次签到的时间，只记录日期）对象
     *
     * @param bytes 字节数组
     * @return io.github.telechow.sign.in.sheet.impl.YearlySignInSheetWithoutTime 年度签到表（不记录每次签到的时间，只记录日期）对象
     * @author Telechow
     * @since 2022/10/28 14:44
     */
    public static YearlySignInSheetWithoutTime valueOf(byte[] bytes) {
        //1.如果字节数组的长度不是46+2，则抛出异常
        final boolean error = bytes.length != (SHORT_BYTE_LENGTH + BITSET_BYTE_ARRAY_MAX_LENGTH);
        if (error) {
            throw new ByteArrayDataLengthException();
        }

        //2.加载数据
        byte[] yearByteArray = Arrays.copyOf(bytes, SHORT_BYTE_LENGTH);
        short year = ByteConvertUtils.bytesToShort(yearByteArray);
        byte[] bitSetByteArray = Arrays.copyOfRange(bytes, SHORT_BYTE_LENGTH, bytes.length);
        BitSet bitSet = BitSet.valueOf(bitSetByteArray);
        return new YearlySignInSheetWithoutTime(year, bitSet);
    }

    /**
     * 根据 年份 获取年度签到表（不记录每次签到的时间，只记录日期）对象实例
     *
     * @param year 年份
     * @return io.github.telechow.sign.in.sheet.impl.YearlySignInSheetWithoutTime 年度签到表（不记录每次签到的时间，只记录日期）对象
     * @author Telechow
     * @since 2022/10/28 14:56
     */
    public static YearlySignInSheetWithoutTime getInstance(int year) {
        //1.求得此年份的天数
        Year yearObj = Year.of(year);
        final int yearLength = yearObj.length();

        //2.返回对象
        return new YearlySignInSheetWithoutTime((short) year, new BitSet(yearLength));
    }

    @Override
    public void signIn() {
        //1.判断当前日期的年份和签到册的年份是否一致，如果不一致抛出异常
        LocalDate today = LocalDate.now();
        final boolean yearNotMatch = !Objects.equals(today.getYear(), (int) this.year);
        if (yearNotMatch) {
            throw new SignInYearNotMatchException();
        }

        //2.签到
        this.sheetBitSet.set(today.getDayOfYear() - 1, true);
    }

    @Override
    public boolean isSignIn(LocalDate localDate) {
        //1.判断年份是否和签到册的年份匹配，如果不匹配直接返回false
        final boolean yearNotMatch = !Objects.equals(localDate.getYear(), (int) this.year);
        if (yearNotMatch) {
            return false;
        }

        //2.从bitset中判断是否签到
        return this.sheetBitSet.get(localDate.getDayOfYear() - 1);
    }

    @Override
    public int signInCountInWholeYear() {
        return this.sheetBitSet.cardinality();
    }

    @Override
    public int notSignInCountInWholeYear() {
        //当年未签到次数 = 当年天数 - 当年已签到次数
        return Year.of(this.year).length() - this.sheetBitSet.cardinality();
    }

    @Override
    public int signInCountInWholeMonth(int monthOfYear) {
        //1.截取该月的bitset
        BitSet monthBitSet = this.interceptBitSetByMonth(monthOfYear);

        //2.获取该月签到次数
        return monthBitSet.cardinality();
    }

    @Override
    public int notSignInCountInWholeMonth(int monthOfYear) {
        //当月未签到次数 = 当月天数 - 当月已签到次数
        return YearMonth.of(this.year, monthOfYear).lengthOfMonth() - this.signInCountInWholeMonth(monthOfYear);
    }

    @Override
    public List<LocalDateTime> listSignInDateTimeInWholeYear() {
        return this.sheetBitSet.stream()
                .boxed()
                .map(i -> LocalDateTime.of(LocalDate.ofYearDay(this.year, i + 1), LocalTime.MIN))
                .collect(Collectors.toList());
    }

    @Override
    public List<LocalDateTime> listNotSignInDateTimeInWholeYear() {
        //1.克隆bitset并翻转
        BitSet clone = this.cloneAndFlip(this.sheetBitSet);

        //2.返回未签到的日期列表
        return clone.stream()
                .boxed()
                .map(i -> LocalDateTime.of(LocalDate.ofYearDay(this.year, i + 1), LocalTime.MIN))
                .collect(Collectors.toList());
    }

    @Override
    public List<LocalDateTime> listSignInDateTimeInWholeMonth(int monthOfYear) {
        //1.截取该月的bitset
        BitSet monthBitSet = this.interceptBitSetByMonth(monthOfYear);

        //2.过滤出结果
        int offset = LocalDate.of(this.year, monthOfYear, 1).getDayOfYear();
        return monthBitSet.stream()
                .boxed()
                .map(i -> LocalDateTime.of(LocalDate.ofYearDay(this.year, offset + i + 1), LocalTime.MIN))
                .collect(Collectors.toList());
    }

    @Override
    public List<LocalDateTime> listNotSignInDateTimeInWholeMonth(int monthOfYear) {
        //1.截取该月的bitset
        BitSet monthBitSet = this.interceptBitSetByMonth(monthOfYear);

        //2.克隆bitset并翻转
        BitSet clone = this.cloneAndFlip(monthBitSet);

        //3.过滤出结果
        int offset = LocalDate.of(this.year, monthOfYear, 1).getDayOfYear();
        return clone.stream()
                .boxed()
                .map(i -> LocalDateTime.of(LocalDate.ofYearDay(this.year, offset + i + 1), LocalTime.MIN))
                .collect(Collectors.toList());
    }

    @Override
    public byte[] toByteArray() {
        byte[] bytes = new byte[SHORT_BYTE_LENGTH + BITSET_BYTE_ARRAY_MAX_LENGTH];
        //1.前两个字节为年份
        System.arraycopy(ByteConvertUtils.shortToBytes(this.year), 0, bytes, 0, SHORT_BYTE_LENGTH);

        //2.后面的字节为每天的签到数据
        System.arraycopy(this.sheetBitSet.toByteArray(), 0
                , bytes, SHORT_BYTE_LENGTH, this.sheetBitSet.toByteArray().length);
        return bytes;
    }

    /// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ private method ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    /**
     * 截取指定月份的BitSet
     *
     * @param monthOfYear 月份，1-12
     * @return java.util.BitSet 指定月份你的BitSet
     * @author Telechow
     * @since 2022/10/28 17:41
     */
    private BitSet interceptBitSetByMonth(int monthOfYear) {
        YearMonth yearMonth = YearMonth.of(this.year, monthOfYear);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
        return this.sheetBitSet.get(firstDayOfMonth.getDayOfYear() - 1, lastDayOfMonth.getDayOfYear());
    }

    /**
     * 克隆并翻转一个BitSet<br>
     * 例如：BitSet(0100100000)，翻转后得到BitSet(1011011111)
     *
     * @param bitSet 被克隆和翻转的BitSet
     * @return java.util.BitSet 克隆并翻转后的BitSet
     * @author Telechow
     * @since 2022/10/28 17:56
     */
    private BitSet cloneAndFlip(BitSet bitSet) {
        BitSet clone = (BitSet) bitSet.clone();
        clone.flip(0, clone.length());
        return clone;
    }
}
