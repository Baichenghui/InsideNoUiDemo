package cn.idaddy.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.idaddy.android.opensdk.device.IDYSdkApi
import cn.idaddy.android.opensdk.lib.IDYCommon
import cn.idaddy.android.opensdk.lib.order.OnCreateOrderCallback
import cn.idaddy.android.opensdk.lib.utils.ToastUtils
import com.appshare.test.adapters.ListAdapter
import com.appshare.test.R
import kotlinx.android.synthetic.main.activity_audio_rank_list_layout.*

class OrderPayParamActivity : AppCompatActivity() {
    lateinit var LIstAdapter: ListAdapter

    var list: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_rank_list_layout)
        LIstAdapter = ListAdapter(layoutInflater)
        audio_rank_list.adapter = LIstAdapter

        intent?.let {
            var goodsId = it.getStringExtra("goodsId")

            if (goodsId.isNullOrBlank()) {
                goodsId = "2938";
            }
            IDYSdkApi.createOrderByGoodsId(goodsId.toInt(),object : OnCreateOrderCallback {
                override fun onGetOrderSuccess(successRet: String?) {
                    successRet?.let {
                        list.add(it)
                        LIstAdapter.refresh(list)
                    }

                    if (list.count() > 0) {
                        ToastUtils.showShort(IDYCommon.application,"获取支付参数成功")
                    } else {
                        ToastUtils.showShort(IDYCommon.application,"获取支付参数失败")
                    }
                }

                override fun onGetOrderFailure(failureRet: String?) {
                    ToastUtils.showShort(IDYCommon.application,failureRet)
                }
            })
        }


    }
}