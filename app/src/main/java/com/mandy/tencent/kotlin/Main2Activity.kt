package com.mandy.tencent.kotlin

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import com.mandy.tencent.R
import com.mandy.tencent.message.adapter.MessageAdapter
import com.mandy.tencent.retrofit.ServiceGenerator
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
import kotlin.collections.HashMap


class Main2Activity : AppCompatActivity() {

    lateinit var channel: Channel
    lateinit var privateChannel: PrivateChannel

    var toolbar: Toolbar? = null
    val user: String? = null
    var sharedToken: SharedToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        sharedToken = SharedToken(this);
        toolbar = tooolbar as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar!!.setTitle("")

        setMessage()

        setupPusher()


    }

    private fun setMessage() {
        val arrayList = ArrayList<String>()
        arrayList.add("0")
        arrayList.add("1")
        arrayList.add("0")

        val adapter = MessageAdapter(this, arrayList)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(linearLayoutManager)
        recyclerView.setAdapter(adapter)
        adapter.notifyDataSetChanged()

    }

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


//        privateChannel = pusher.subscribePrivate("private-chat", object : PrivateChannelEventListener {
//            override fun onAuthenticationFailure(p0: String?, p1: Exception?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onSubscriptionSucceeded(p0: String?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onEvent(p0: String?, p1: String?, p2: String?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//        })
//
//
//        privateChannel.bind("send", object : SubscriptionEventListener {
//            override fun onEvent(p0: String?, p1: String?, p2: String?) {
//                Log.d("+++++++++++", "subscrp " + p0)
//            }
//
//        })


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

        imgSend.setOnClickListener {
            sendData()
        }


    }

    fun sendData() {
        val hashMap: HashMap<String, String> = HashMap<String, String>() //define empty hashmap
        hashMap.put("from_user", sharedToken!!.userId)
        hashMap.put("content", edtText.text.toString())
        hashMap.put("to_user", "1")

        val jsonObject: JSONObject = JSONObject(hashMap)


//        imgSend.setOnClickListener {
//            if (edtText.text.isNotEmpty()) {
//                val message = Message(
//                        sharedToken!!.userId,
//                        edtText.text.toString(),
//                        Calendar.getInstance().timeInMillis
//                )
//
//                val call = ChatService.create().postMessage(message)
//
//                call.enqueue(object : Callback<Void> {
//                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                        resetInput()
//                        if (!response.isSuccessful) {
//                            Log.e("++++++++++", response.code().toString());
//                            Toast.makeText(
//                                    applicationContext,
//                                    "Response was not successful",
//                                    Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//
//                    override fun onFailure(call: Call<Void>, t: Throwable) {
//                        resetInput()
//                        Log.e("+++++++++++++", t.toString());
//                        Toast.makeText(
//                                applicationContext,
//                                "Error when calling the service",
//                                Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                })
//            } else {
//                Toast.makeText(
//                        applicationContext,
//                        "Message should not be empty",
//                        Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
    }


    fun resetInput() {
        // Clean text box
        edtText.text.clear()

        // Hide keyboard
        val inputManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
                currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}