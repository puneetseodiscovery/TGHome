package com.mandy.tencent.kotlin

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import com.mandy.tencent.R
import com.mandy.tencent.controller.Controller
import com.mandy.tencent.kotlin.pojo.GetMessageListApi
import com.mandy.tencent.kotlin.pojo.SendMesage
import com.mandy.tencent.message.adapter.MessageAdapter
import com.mandy.tencent.nointernet.NoInternetActivity
import com.mandy.tencent.retrofit.ApiInterface
import com.mandy.tencent.retrofit.ServiceGenerator
import com.mandy.tencent.util.CheckInternet
import com.mandy.tencent.util.ProgressBarClass
import com.mandy.tencent.util.SharedToken
import com.pusher.client.Pusher

import com.pusher.client.PusherOptions
import com.pusher.client.channel.*
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionStateChange
import com.pusher.client.util.HttpAuthorizer

import kotlinx.android.synthetic.main.activity_main2.*
import com.pusher.util.Result
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class Main2Activity : AppCompatActivity(), Controller.SendMessage, Controller.GetMessageList {


    lateinit var channel: Channel
    lateinit var privateChannel: PrivateChannel

    var toolbar: Toolbar? = null
    val user: String? = null
    var sharedToken: SharedToken? = null
    lateinit var controller: Controller
    lateinit var dialog: Dialog
    lateinit var arrayList: ArrayList<GetMessageListApi.Datum>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        sharedToken = SharedToken(this);
        controller = Controller(this, this)
        dialog = ProgressBarClass.showProgressDialog(this)

        toolbar = tooolbar as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar!!.setTitle("")



        setupPusher()

        imgSend.setOnClickListener {
            if (edtText.text.toString().isEmpty()) {
                edtText.setError("Enter the message")
            } else {
                if (CheckInternet.isInternetAvailable(this)) {
                    dialog.show()
                    controller.setSendMessage(sharedToken!!.userId, edtText.text.toString())
                } else {
                    startActivity(Intent(this, NoInternetActivity::class.java));
                }
            }
        }

    }


    override fun onSucessSend(response: Response<SendMesage>?) {
        dialog.dismiss()
        if (response!!.isSuccessful) {
            if (response.body()!!.status == 200) {
                edtText.setText("")
            } else {
                Toast.makeText(this, "" + response.body()!!.message, Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "" + response.message(), Toast.LENGTH_SHORT).show()

        }
    }

    override fun onSucessList(response: Response<GetMessageListApi>?) {
        dialog.dismiss()
        if (response!!.isSuccessful) {
            if (response.body()!!.status == 200) {

                for (i in response.body()!!.data.indices) {

                }

            } else {
                Toast.makeText(this, "" + response.body()!!.message, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "" + response.message(), Toast.LENGTH_SHORT).show()

        }

    }

    override fun error(error: String?) {
        dialog.dismiss()
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show()
    }

//    private fun setMessage() {
//        val adapter = MessageAdapter(this, )
//        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        recyclerView.setLayoutManager(linearLayoutManager)
//        recyclerView.setAdapter(adapter)
//        adapter.notifyDataSetChanged()
//
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupPusher() {
        val http = HttpAuthorizer("http://tghome.amrdev.site/api/pusher/auth")
        val options = PusherOptions()
                .setCluster("eu")
                .setAuthorizer(http)
        val pusher = Pusher("a1a0b819ec1e1c2a9926", options)

        pusher.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(p0: ConnectionStateChange?) {
                Log.d("+++++++++++", "connected " + p0)
            }

            override fun onError(p0: String?, p1: String?, p2: Exception?) {
                Log.d("+++++++++++", "error " + p2)


            }

        })

        channel = pusher.subscribe("chat", object : ChannelEventListener {
            override fun onEvent(p0: String?, p1: String?, p2: String?) {
                Log.d("++++++++++", "sub" + p0 + p1 + p2)
            }

            override fun onSubscriptionSucceeded(p0: String?) {
                Log.d("+++++++++++", "subscrp " + p0)

            }

        })

        channel.bind("send", object : SubscriptionEventListener {
            override fun onEvent(p0: String?, p1: String?, p2: String?) {
                Log.d("+++++++++++", "subscrp " + p2)

            }

        })

        pusher.connect()


    }

}