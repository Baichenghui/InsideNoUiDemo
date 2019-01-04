package com.appshare.test

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import cn.idaddy.android.opensdk.device.IDYSdkApi
import cn.idaddy.android.opensdk.lib.IDYCommon
import cn.idaddy.android.opensdk.lib.IDYConfig
import cn.idaddy.android.opensdk.lib.phone.OnGetVerifyCodeCallback
import cn.idaddy.android.opensdk.lib.user.IDYLoginIdaddyCallback
import cn.idaddy.android.opensdk.lib.utils.StringUtils
import cn.idaddy.android.opensdk.lib.utils.ToastUtils
import cn.idaddy.test.*
import kotlinx.android.synthetic.main.activity_layout.*

class MainActivity : AppCompatActivity() {
    var heh:String = "heh"
    var pushText = ""  //推送的文案

    fun isLogin():Boolean {
        // 验证用户是否登录
        IDYConfig.userInfoBean.data?.let {
            if (it.is_guest) {
                return false
            } else {
                return true
            }
        }

        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)

        findViewById<TextView>(R.id.prd_textView).text = BuildConfig.outputFileName

        //发送验证码
        sendValidatecode.setOnClickListener {
            if (StringUtils.isValidOfPhoneNumber( edit_phoneNumber.text.toString())){
                IDYSdkApi.sendMobileVerifyCodeWithMobile( edit_phoneNumber.text.toString(),object :OnGetVerifyCodeCallback{
                    override fun onVerifyCodeFailed(failureRet: String?) {
                        Toast.makeText(this@MainActivity,failureRet,Toast.LENGTH_SHORT).show()
                    }

                    override fun onVerifyCodeSuccess(successRet: String?) {
                        Toast.makeText(this@MainActivity,"验证码获取成功",Toast.LENGTH_SHORT).show()
                    }
                })
            }else{
                Toast.makeText(this,"手机号码不正确",Toast.LENGTH_SHORT).show()
            }
        }

        //手机号 + 验证码登录
        login_with_mobile.setOnClickListener {
            if (!StringUtils.isValidOfPhoneNumber( edit_phoneNumber.text.toString())){
                Toast.makeText(this,"手机号码不正确",Toast.LENGTH_SHORT).show()
            }else if (StringUtils.isEmpty(edit_Validatecode.text.toString())){
                Toast.makeText(this,"请输入验证码",Toast.LENGTH_SHORT).show()
            }else{
                IDYSdkApi.loginWithMobileVerifyCode(edit_phoneNumber.text.toString(),edit_Validatecode.text.toString(),object : IDYLoginIdaddyCallback {
                    override fun beforeLogin() {
                        //登录前，可自行显示loading...
                    }

                    override fun onLoginFailure(failureRet: Any?) {
                        Toast.makeText(this@MainActivity,"登录失败：+ ${failureRet}",Toast.LENGTH_SHORT).show()
                    }

                    override fun onLoginSuccess() {
                        Toast.makeText(this@MainActivity,"登录成功",Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        findViewById<View>(R.id.main_user_logout_button).setOnClickListener {
            IDYSdkApi.logout()
        }

        findViewById<Button>(R.id.getOrderlistBtn).setOnClickListener{
            startActivity(Intent(this@MainActivity, OrderListActivity::class.java))
        }

        findViewById<Button>(R.id.getAudioRanklistBtn).setOnClickListener{
            startActivity(Intent(this@MainActivity, AudioRankTypeActivity::class.java))
        }

        findViewById<Button>(R.id.getPayParam_btn).setOnClickListener{
            startActivity(Intent(this@MainActivity, OrderPayParamActivity::class.java).putExtra("goodsId",editText_goodsId.text.toString()))
        }

        getTopicList_btn.setOnClickListener {
            startActivity(Intent(this@MainActivity,TopicAudioListActivity::class.java).putExtra("topicId",editText_topicId.text.toString()))
        }

        getAudioInfo_btn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AudioInfoActivity::class.java).putExtra("audioId",editText_audioId.text.toString()).putExtra("chapterId",editText_chapterId.text.toString()))
        }

        getCategorylistBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, CategoryListActivity::class.java))
        }

        getCategoryAudiolistBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AudioListByCategoryIdActivity::class.java)
                    .putExtra("categoryid",editText_audioId.text.toString()))
        }

        getSearchHotKeyBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchHotKeyActivity::class.java))
        }

        getAudioList_btn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AudioListBySortKeyActivity::class.java).putExtra("sortKey",editText_sortKey.text.toString()))
        }

        getAudioList_hotkey_btn.setOnClickListener {

            val islogin = isLogin()

            startActivity(Intent(this@MainActivity, AudioListByHotKeyActivity::class.java).putExtra("hotKey",editText_hotKey.text.toString()))
        }

        getCode_btn.setOnClickListener {


            startActivity(Intent(this@MainActivity, AuthCreateRedeemCodeActivity::class.java).putExtra("redeemCode",editText_code.text.toString()))
        }

        getAccessToken.setOnClickListener {
            val token = IDYSdkApi.getAccessToken()
            if (token.isNullOrEmpty()) {
                ToastUtils.showShort(this,"token 为空")
            } else {
                startActivity(Intent(this, TokenActivity::class.java).putExtra("token", token))
            }
        }

//        //工爸账号验证----------
//        useIdaddyAccountValidate_box.isChecked = false
//        edit_phoneNumber_btn.isEnabled = false
//
//        edit_phoneNumber_btn.setTextColor(Color.GRAY)
//        useIdaddyAccountValidate_box.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
//            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
//                if (isChecked){
//                    IDYSdkApi.setUseIdaddyAccountValidate(true)
//                    edit_phoneNumber_btn.isEnabled = true
//                    edit_phoneNumber_btn.setTextColor(Color.BLACK)
//                }else{
//                    IDYSdkApi.setUseIdaddyAccountValidate(false)
//                    edit_phoneNumber_btn.isEnabled = false
//                    edit_phoneNumber_btn.setTextColor(Color.GRAY)
//                }
//            }
//        })
//
//        edit_phoneNumber_btn.setOnClickListener{
//            phoneNumber = edit_phoneNumber.text.toString()
//            if (StringUtils.isValidOfPhoneNumber(phoneNumber)){
//                IDYSdkApi.setUseIdaddyAccountValidate(true)
//                IDYSdkApi.setIdaddyAccountPhoneNumber(phoneNumber)
//            }else{
//                ToastUtils.showShort(this,"请输入正确的手机号")
//            }
//        }
        //工爸账号验证----------
    }
}
