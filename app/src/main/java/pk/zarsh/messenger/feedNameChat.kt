package pk.zarsh.messenger


import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

/**
 * Created by HP on 1/9/2018.
 */
internal class feedNameChat(private val mCtx: Context, private val productList: List<feed>) : RecyclerView.Adapter<feedNameChat.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.cont_name_chat, parent,false)
        view.getLayoutParams().width = parent.getWidth();


        return ProductViewHolder(view)
    }
    private var currentPosition = 100

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.textName.setText(product.head)


        if (currentPosition == position) {
        }

        holder.textName.setOnClickListener(View.OnClickListener {
            //getting the position of the item to expand it
            currentPosition = position
            val ss= holder.textName.text.toString()
            Log.d("ye",ss)


            val intent = Intent(mCtx,Message::class.java)
            intent.putExtra("child",product.head)
            intent.putExtra("username",product.ead)
            mCtx.startActivity(intent)

            //reloding the list
            notifyDataSetChanged()
        })
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    internal inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textName: TextView
        init {

            textName = itemView.findViewById(R.id.chat_name)
        }
    }
}
