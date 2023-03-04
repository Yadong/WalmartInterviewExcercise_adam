package com.walmartinterviewexercise.utils

import android.util.Log

private const val LOG_PREFIX = "COUNTRY_LIST_"

class AppLog {

    companion object {
        fun d(tag: String, message: String?) {
            Log.d(LOG_PREFIX + tag, message.orEmpty())
        }

        fun e(tag: String, message: String?, throwable: Throwable?) {
            Log.e(LOG_PREFIX + tag, message, throwable)
        }
    }

}
