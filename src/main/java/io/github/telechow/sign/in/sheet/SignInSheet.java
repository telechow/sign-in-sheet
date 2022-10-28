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

/**
 * 签到册接口，定义了签到册的一些行为
 *
 * @author Telechow
 * @since 2022/10/27 16:37
 */
public interface SignInSheet {

    /**
     * 在当前系统时间（系统日期）进行一次签到<br>
     * 如果年份和签到册的年份不一致，将抛出异常<br>
     *
     * @author Telechow
     * @since 2022/10/27 16:41
     */
    void signIn();

    /**
     * 判断某年某月某日是否签到
     *
     * @param year        年
     * @param monthOfYear 月，1-12
     * @param dayOfMonth  日，1-31
     * @return boolean false：当日未签到；true：当日已签到
     * @author Telechow
     * @since 2022/10/27 16:43
     */
    boolean isSignIn(int year, int monthOfYear, int dayOfMonth);

    /**
     * 判断指定的日期是否签到
     *
     * @param localDate 指定日期，时区为当地时区
     * @return boolean false：当日未签到；true：当日已签到
     * @author Telechow
     * @since 2022/10/27 16:45
     */
    boolean isSignIn(LocalDate localDate);

    /**
     * 判断指定的日期是否签到
     *
     * @param localDateTime 指定日期时间，时区为当地时区
     * @return boolean false：当日未签到；true：当日已签到
     * @author Telechow
     * @since 2022/10/27 16:46
     */
    boolean isSignIn(LocalDateTime localDateTime);
}
