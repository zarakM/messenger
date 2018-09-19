package pk.zarsh.messenger
import android.Manifest
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.EditText
import org.json.JSONException
import android.util.Base64
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.provider.MediaStore
import com.android.volley.toolbox.*
import java.io.*
import java.util.*
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ColorSpace
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.ButtonBarLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.*
import java.net.Socket


class Message : Activity() {
    lateinit var sender:String
    lateinit var receive:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        val btnmessage = findViewById<Button>(R.id.sendMessage)
        val bundle = intent.extras
        sender = bundle.getString("username")
        receive = bundle.getString("child")

        val query = findViewById<EditText>(R.id.message)
        query.text = null

        loadMessage(sender)

        btnmessage.setOnClickListener({
            val uri = "http://childbook.000webhostapp.com/insertMessage.php"
            val query = findViewById<EditText>(R.id.message)
            val querys = query.text.toString()

            val request = object : StringRequest(Request.Method.POST, uri, Response.Listener { response ->
                Log.d("writeRexponse",response)
                query.text = null
                loadMessage(sender)
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError) {
                    Log.d("writeError"," "+error.message)
                }
            }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val param = HashMap<String, String>()
                    param.put("sender", sender)
                    param.put("receive",receive)
                    param.put("message", querys)
                    return param
                }}
            Volley.newRequestQueue(this).add(request)
        })
    }

    private fun loadMessage(ss:String) {
        val uri = "https://childbook.000webhostapp.com/chat.php"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, uri, null, Response.Listener { response ->
            try {
                val jsonArray = response.getJSONArray("querify")
                var list = ArrayList<messageC>()
                var len = jsonArray.length()-1
                for (i in 0..len) {
                    var json = jsonArray.getJSONObject(i)
                    var senders = json.getString("username")
                    var receiver = json.getString("receiver")
                    var message = json.getString("message")
                    var image = json.getString("image")
                    when {
                        (senders ==ss || receiver == ss)-> {
                            list.add(messageC(senders, receiver, message))
                        }}}
                val adapter = feedMessageAdapter(applicationContext, list)
                val recyclerview = findViewById<RecyclerView>(R.id.recylcerViewMessage)
                recyclerview.setHasFixedSize(true)
                val l = LinearLayoutManager(this)
                recyclerview.layoutManager =l
                recyclerview.adapter = adapter
            } catch (e: JSONException) {
                Log.d("error", " " + e.message)
            }
        }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError) {
                Log.d("error", " " + error.message)
            }
        })
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }
}
