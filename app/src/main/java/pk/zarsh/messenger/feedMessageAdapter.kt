package pk.zarsh.messenger
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import com.bumptech.glide.Glide

/**
 * Created by HP on 1/9/2018.
 */
internal class feedMessageAdapter(private val mCtx: Context, private val productList: List<messageC>) : RecyclerView.Adapter<feedMessageAdapter.ProductViewHolder>() {

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.textName.setText(product.sender)
        holder.textDesignation.setText(product.message)

        if (currentPosition == position) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.cont_message, parent,false)
        view.getLayoutParams().width = parent.getWidth()

        return ProductViewHolder(view)
    }
    private var currentPosition = 100

    override fun getItemCount(): Int {
        return productList.size
    }

    internal inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textName: TextView
        var textDesignation: TextView
        init {
            textName = itemView.findViewById(R.id.c_name)
            textDesignation = itemView.findViewById(R.id.c_message)
        }
    }
}
