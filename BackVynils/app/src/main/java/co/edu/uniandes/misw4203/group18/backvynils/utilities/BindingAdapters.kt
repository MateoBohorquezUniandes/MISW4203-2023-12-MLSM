package co.edu.uniandes.misw4203.group18.backvynils.utilities

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(imageUrl)
        .into(view)
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("formattedDate")
fun formattedDate(view: TextView, value: String) {
    val zonedFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val desiredFormat = SimpleDateFormat("yyyy-MM-dd")
    val date = zonedFormat.parse(value)?.let { desiredFormat.format(it) }
    view.text = date
}