package com.sirit.android.support.lib.network

/**
 * @param <T> 数据实体对象
 * @author zou.sq
 * 结果处理类
</T> */
class ResultBean<T> {

    var code: Int = 0
    var data: T? = null

    companion object {

    }
}
