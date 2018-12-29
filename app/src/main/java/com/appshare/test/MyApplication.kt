package com.appshare.test

import android.app.Application
import android.os.AsyncTask
import cn.idaddy.android.opensdk.device.IDYSdkApi
import cn.idaddy.android.opensdk.lib.IDYConfig
import cn.idaddy.android.opensdk.lib.api.IDYTokenInterface
import cn.idaddy.android.opensdk.lib.api.OnTaskCallback
import cn.idaddy.android.opensdk.lib.user.IdaddyTicketBean
import cn.idaddy.android.opensdk.lib.user.PostIdaddyTicketTask
import com.google.gson.Gson

//以下为使用手机号+验证码登录的方式，无需实现IDYTokenInterface接口
//class MyApplication : Application() {
//    override fun onCreate() {
//        super.onCreate()
//        IDYSdkApi.init(this, true,true)
//    }
//}

/**
 * 以下为使用静默登录的方式，必须要需要实现IDYTokenInterface 接口
 *
 *
 */
class MyApplication : Application(), IDYTokenInterface {
    override fun onCreate() {
        super.onCreate()
//        /**
//         * 使用静默登录
//         */
//        IDYSdkApi.init(this, true,false)

        //Demo为了简单，默认集成了该接口
        IDYSdkApi.init(this, true)
    }


    override fun idyNeedAccessToken() {
        //静默绑定
        PostIdaddyTicketTask(IDYConfig.IDADDY_APP_ID, TestConfig.uniquedId,
                "", (System.currentTimeMillis() / 1000).toString(), null, object : OnTaskCallback {
            override fun onStart() {

            }

            override fun onSuccess(successRet: Any?) {
                val idaddyTicketBean = Gson().fromJson(successRet as String, IdaddyTicketBean::class.java)
                idaddyTicketBean?.data?.let {
                    IDYSdkApi.genIDYAccessToken(it.ticket)
                }
            }

            override fun onError(failureRet: Any?) {

            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

        //可直接调用loginIdaddySilent 实现静默绑定登录
        //IDYSdkApi.loginIdaddySilent("uniqueId", null)
    }
}
