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
package io.github.telechow.sign.in.sheet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 年度签到册接口
 *
 * @author Telechow
 * @since 2022/10/28 10:40
 */
public interface YearlySignInSheet extends SignInSheet {

    /**
     * 统计当前年度签到册中签到的次数
     *
     * @return int 当前年度签到册中签到的次数
     * @author Telechow
     * @since 2022/10/28 10:41
     */
    int signInCountInWholeYear();

    /**
     * 统计当前年度签到册中的未签到的次数
     *
     * @return int 当前年度签到册中的未签到的次数
     * @author Telechow
     * @since 2022/10/28 10:42
     */
    int notSignInCountInWholeYear();

    /**
     * 统计指定月份的签到次数
     *
     * @param month 月份，1-12
     * @return int 指定月份的签到次数
     * @author Telechow
     * @since 2022/10/28 10:43
     */
    int signInCountInWholeMonth(int month);

    /**
     * 统计指定月份的未签到次数
     *
     * @param month 月份，1-12
     * @return int 指定月份的未签到次数
     * @author Telechow
     * @since 2022/10/28 10:44
     */
    int notSignInCountInWholeMonth(int month);

    /**
     * 获取当前年度签到册中已签到的日期时间列表<br>
     * 如果签到册不存储时间，只存储日期，则返回日期的00:00:00<br>
     *
     * @return java.util.List<java.time.LocalDateTime> 当前年度签到册中已签到的日期时间列表
     * @author Telechow
     * @since 2022/10/28 16:36
     */
    List<LocalDateTime> listSignInDateTimeInWholeYear();

    /**
     * 获取当前年度签到册中已签到的日期列表
     *
     * @return java.util.List<java.time.LocalDate> 当前年度签到册中已签到的日期列表
     * @author Telechow
     * @since 2022/10/28 16:07
     */
    default List<LocalDate> listSignInDateInWholeYear() {
        return this.listSignInDateTimeInWholeYear().stream()
                .map(LocalDateTime::toLocalDate)
                .collect(Collectors.toList());
    }

    /**
     * 获取当前年度签到册中未签到的日期时间列表<br>
     * 如果签到册不存储时间，只存储日期，则返回日期的00:00:00<br>
     *
     * @return java.util.List<java.time.LocalDateTime> 当前年度签到册中未签到的日期时间列表
     * @author Telechow
     * @since 2022/10/28 16:38
     */
    List<LocalDateTime> listNotSignInDateTimeInWholeYear();

    /**
     * 获取当前年度签到册中未签到的日期列表
     *
     * @return java.util.List<java.time.LocalDate> 当前年度签到册中未签到的日期列表
     * @author Telechow
     * @since 2022/10/28 16:12
     */
    default List<LocalDate> listNotSignInDateInWholeYear() {
        return this.listNotSignInDateTimeInWholeYear().stream()
                .map(LocalDateTime::toLocalDate)
                .collect(Collectors.toList());
    }

    /**
     * 获取签到册中指定月份已签到的日期时间列表<br>
     * 如果签到册不存储时间，只存储日期，则返回日期的00:00:00<br>
     *
     * @param month 月份，1-12
     * @return java.util.List<java.time.LocalDateTime> 签到册中指定月份已签到的日期时间列表
     * @author Telechow
     * @since 2022/10/28 17:26
     */
    List<LocalDateTime> listSignInDateTimeInWholeMonth(int month);

    /**
     * 获取签到册中指定月份已签到的日期列表
     *
     * @param month 月份，1-12
     * @return java.util.List<java.time.LocalDate> 签到册中指定月份已签到的日期列表
     * @author Telechow
     * @since 2022/10/28 17:21
     */
    default List<LocalDate> listSignInDateInWholeMonth(int month) {
        return this.listSignInDateTimeInWholeMonth(month).stream()
                .map(LocalDateTime::toLocalDate)
                .collect(Collectors.toList());
    }

    /**
     * 获取签到册中指定月份未签到的日期时间列表
     *
     * @param month 月份，1-12
     * @return java.util.List<java.time.LocalDateTime> 签到册中指定月份未签到的日期时间列表
     * @author Telechow
     * @since 2022/10/28 17:30
     */
    List<LocalDateTime> listNotSignInDateTimeInWholeMonth(int month);

    /**
     * 获取签到册中指定月份未签到的日期列表
     *
     * @param month 月份，1-12
     * @return java.util.List<java.time.LocalDate> 签到册中指定月份未签到的日期列表
     * @author Telechow
     * @since 2022/10/28 17:28
     */
    default List<LocalDate> listNotSignInDateInWholeMonth(int month) {
        return this.listNotSignInDateTimeInWholeMonth(month).stream()
                .map(LocalDateTime::toLocalDate)
                .collect(Collectors.toList());
    }
}
