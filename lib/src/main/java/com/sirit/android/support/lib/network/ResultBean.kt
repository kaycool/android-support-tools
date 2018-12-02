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

        /**
         * 接口正常
         */
        val RESULT_CODE_SUCCESS = 0
        /**
         * 网络异常
         */
        val RESULT_CODE_NET_ERROR = 111
        /**
         * 服务器错误
         */
        val RESULT_CODE_SERVER_ERROR = -1
        /**
         * 用户被挤掉
         */
        val RESULT_CODE_OTHER_LOGIN = 402
        /**
         * token过期状态
         */
        val RESULT_CODE_NO_LOGIN = 403
        /**
         * 资源没有发现
         */
        val RESULT_CODE_NO_FOUND = 404
        /**
         * 礼包过期
         */
        val RESULT_CODE_GIFT_TIME_OUT = 1024
        /**
         * 礼包重复领取
         */
        val RESULT_CODE_REPEAT_DATA = 1022
        /**
         * 礼包领取失败
         */
        val RESULT_CODE_GIFT_RECEIVER_ERROR = 1025
        /**
         * 礼包领取等级不够
         */
        val RESULT_CODE_NOT_PERMISSION = 1023
        /**
         * 晒单审核通过
         */
        val RESULT_CODE_SHOW_ORDER_SUCCEED = 1019
    }
}
