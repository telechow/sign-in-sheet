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
     * @param monthOfYear 月份，1-12
     * @return int 指定月份的签到次数
     * @author Telechow
     * @since 2022/10/28 10:43
     */
    int signInCountInWholeMonth(int monthOfYear);

    /**
     * 统计指定月份的未签到次数
     *
     * @param monthOfYear 月份，1-12
     * @return int 指定月份的未签到次数
     * @author Telechow
     * @since 2022/10/28 10:44
     */
    int notSignInCountInWholeMonth(int monthOfYear);
}
