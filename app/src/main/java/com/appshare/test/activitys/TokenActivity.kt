package cn.idaddy.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.idaddy.android.opensdk.device.IDYSdkApi
import cn.idaddy.android.opensdk.lib.IDYCommon
import cn.idaddy.android.opensdk.lib.OutBean.OutAudioRankBean
import cn.idaddy.android.opensdk.lib.audioinfo.rank.OnGetAudioRankListCallback
import cn.idaddy.android.opensdk.lib.utils.ToastUtils
import com.appshare.test.R
import com.appshare.test.adapters.ListAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_audio_rank_list_layout.*

class TokenActivity : AppCompatActivity(){
    lateinit var  LIstAdapter: ListAdapter

    var list: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_rank_list_layout)
        LIstAdapter = ListAdapter(layoutInflater)
        audio_rank_list.adapter = LIstAdapter

        intent?.let {
            var token = it.getStringExtra("token")
            list.add(token)

            LIstAdapter.refresh(list)
        }

    }
}
