package cn.idaddy.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.idaddy.android.opensdk.device.IDYSdkApi
import cn.idaddy.android.opensdk.lib.IDYCommon
import cn.idaddy.android.opensdk.lib.audioinfo.search.OnSearchAudioCallback
import cn.idaddy.android.opensdk.lib.audioinfo.works.OnGetAudioWorksCallback
import cn.idaddy.android.opensdk.lib.utils.ToastUtils
import com.appshare.test.R
import com.appshare.test.adapters.ListAdapter
import kotlinx.android.synthetic.main.activity_audio_rank_list_layout.*

class AudioListByHotKeyActivity: AppCompatActivity(){

    lateinit var LIstAdapter: ListAdapter

    var list: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_rank_list_layout)
        LIstAdapter = ListAdapter(layoutInflater)
        audio_rank_list.adapter = LIstAdapter

        intent?.let {
            var hotKey = it.getStringExtra("hotKey")
            if (hotKey.isNullOrEmpty()){
                hotKey = "大耳朵图图"
            }

            IDYSdkApi.getSearchAudioList(hotKey,1,null,object :OnSearchAudioCallback{
                override fun onSearchAudioFailure(failureRet: String?) {
                    ToastUtils.showShort(IDYCommon.application,failureRet)
                }

                override fun onSearchAudioSuccess(successRet: String?) {
                    successRet?.let {
                        list.add(it)
                        LIstAdapter.refresh(list)
                    }

                    if (list.count() > 0) {
                        ToastUtils.showShort(IDYCommon.application,"关键字搜索作品列表成功")
                    } else {
                        ToastUtils.showShort(IDYCommon.application,"关键字搜索作品列表失败")
                    }
                }
            })
        }


    }
}