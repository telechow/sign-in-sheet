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

import java.io.Serializable;

/**
 * 签到册序列化接口，提供签到册的序列化与反序列化功能
 *
 * @author Telechow
 * @since 2022/10/28 10:45
 */
public interface SignInSheetSerializable extends Serializable {

    /**
     * 将 签到册对象 转换成 字节数组<br>
     * 字节数组中包含了年份和当年每一天是否签到的数据<br>
     * 得到字节数组后，可以将其存储在sql、noSql中，实现序列化<br>
     * 反序列化时使用此接口的实现类的{@code valueOf(byte[] bytes)}方法构造签到册对象实例<br>
     *
     * @return byte[] 签到册字节数组
     * @author Telechow
     * @since 2022/10/28 15:38
     */
    byte[] toByteArray();

}
