package com.example.smsreader

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import java.util.Random
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter(private val mList: List<ItemViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_recy, parent, false)

        return ViewHolder(view,parent.context)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageResource(ItemViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemViewModel.text
        holder.textView2.text = ItemViewModel.msg
        val str=ItemViewModel.msg
        if(str.contains("<#>")){
            val colourcode: Int = getRandomColor()

            // holder.iconimage.setText(note.getNote().substring(0,1));

            // holder.iconimage.setText(note.getNote().substring(0,1));
            holder.linlay.setBackgroundColor(holder.itemView.resources.getColor(colourcode, null))
        }
        holder.linlay.setOnClickListener(View.OnClickListener {
            val intent = Intent(holder.context, FullMessageScreen::class.java)
            intent.putExtra("name", ItemViewModel.text)
            intent.putExtra("msg", ItemViewModel.msg)
            holder.context.startActivity(intent)
        })

    }
    private fun getRandomColor(): Int {
        val colorcode: MutableList<Int> = ArrayList()
        colorcode.add(R.color.gray)
        colorcode.add(R.color.pink)
        colorcode.add(R.color.lightgreen)
        colorcode.add(R.color.skyblue)
        colorcode.add(R.color.color1)
        colorcode.add(R.color.color2)
        colorcode.add(R.color.color3)
        colorcode.add(R.color.color4)
        colorcode.add(R.color.color5)
        colorcode.add(R.color.green)
        val random = Random()
        val number: Int = random.nextInt(colorcode.size)
        return colorcode[number]
    }
    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View, context: Context) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val textView2: TextView= itemView.findViewById(R.id.textView2)
        val linlay: LinearLayout= itemView.findViewById(R.id.linlay)
        val context:Context=context

    }
}
