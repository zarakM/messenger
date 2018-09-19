package pk.zarsh.messenger

import android.os.Bundle
import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class ChatActivity : AppCompatActivity() {
    lateinit var child :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val bundle = intent.extras
        if(bundle != null) {
            child = bundle.getString("username")
        }

        val uri = "https://childbook.000webhostapp.com/name.php?name="+child
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, uri, null, Response.Listener { response ->
            try {
                val jsonArray = response.getJSONArray("querify")
                var list = ArrayList<feed>()
                var len = jsonArray.length()-1
                for (i in 0..len) {
                    val json = jsonArray.getJSONObject(i)
                    val heads = json.getString("friend_name")
                    val descs = json.getString("child_name")
                    Log.d("heads",heads)
                    if (heads==child) {
                        list.add(feed(descs,child))
                    }
                    if (descs==child) {
                        list.add(feed(heads,child))
                    }
                }

                val adapter = feedNameChat(applicationContext, list)
                val recyclerview = findViewById<RecyclerView>(R.id.recylcerViewChat)
                recyclerview.setHasFixedSize(true)
                recyclerview.setLayoutManager(LinearLayoutManager(this))
                recyclerview.adapter=adapter
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
