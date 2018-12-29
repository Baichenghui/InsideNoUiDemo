package cn.idaddy.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.idaddy.android.opensdk.device.IDYSdkApi
import cn.idaddy.android.opensdk.lib.IDYCommon

import cn.idaddy.android.opensdk.lib.order.OnGetOrderListCallback
import cn.idaddy.android.opensdk.lib.order.OrderListBean
import cn.idaddy.android.opensdk.lib.utils.ToastUtils
import com.appshare.test.adapters.OrderLIstAdapter
import com.appshare.test.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order_list_layout.*

class OrderListActivity :AppCompatActivity(){

   lateinit var  orderLIstAdapter: OrderLIstAdapter

    var list: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list_layout)

        orderLIstAdapter = OrderLIstAdapter(layoutInflater)
        order_list.adapter = orderLIstAdapter

        IDYSdkApi.getOrderList(10,"vip",object : OnGetOrderListCallback {
            override fun onGetOrderSuccess(successRet: String?) {
                val orderlist =  Gson().fromJson(successRet, OrderListBean::class.java)
                for (orderbean in orderlist.list?.let { it}
                        ?: ArrayList<OrderListBean.ListBean>()) {
                    orderbean?.let { list.add(Gson().toJson(orderbean)) }
                }
                orderLIstAdapter.refresh(list)
                if (list.isEmpty()){
                    ToastUtils.showShort(IDYCommon.application,"暂无订单")
                }
            }

            override fun onGetOrderFailure(failureRet: String?) {
                ToastUtils.showShort(IDYCommon.application,failureRet)
            }
        })
    }
}