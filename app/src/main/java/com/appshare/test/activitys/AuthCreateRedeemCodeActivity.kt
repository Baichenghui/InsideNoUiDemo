package cn.idaddy.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.idaddy.android.opensdk.device.IDYSdkApi
import cn.idaddy.android.opensdk.lib.IDYCommon
import cn.idaddy.android.opensdk.lib.audioinfo.works.OnGetAudioWorksCallback
import cn.idaddy.android.opensdk.lib.auth.OnPostAuthCreateCallBack
import cn.idaddy.android.opensdk.lib.utils.ToastUtils
import com.appshare.test.R
import com.appshare.test.adapters.ListAdapter
import kotlinx.android.synthetic.main.activity_audio_rank_list_layout.*

class AuthCreateRedeemCodeActivity : AppCompatActivity(){

    lateinit var LIstAdapter: ListAdapter

    var list: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_rank_list_layout)
        LIstAdapter = ListAdapter(layoutInflater)
        audio_rank_list.adapter = LIstAdapter

        intent?.let {
            var redeemCode = it.getStringExtra("redeemCode")
            if (redeemCode.isNullOrEmpty()){
                redeemCode = "1721488406649604"
            }
            IDYSdkApi.postAuthCreateByRedeemCode(redeemCode,object :OnPostAuthCreateCallBack{
                override fun onPostAuthCreateFailure(failureRet: String?) {
                    ToastUtils.showShort(IDYCommon.application,failureRet)
                }

                override fun onPostAuthCreateSuccess(successRet: String?) {
                    successRet?.let {
                        list.add(it)
                        LIstAdapter.refresh(list)
                    }

                    if (list.count() > 0) {
                        ToastUtils.showShort(IDYCommon.application,"兑换成功")
                    } else {
                        ToastUtils.showShort(IDYCommon.application,"兑换失败")
                    }
                }
            })

        }


    }
}