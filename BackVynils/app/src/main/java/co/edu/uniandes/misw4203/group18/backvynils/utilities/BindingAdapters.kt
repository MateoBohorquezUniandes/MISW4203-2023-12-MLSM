package co.edu.uniandes.misw4203.group18.backvynils.utilities

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import co.edu.uniandes.misw4203.group18.backvynils.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(imageUrl)
        .apply(
            RequestOptions()
            .placeholder(R.drawable.loading_animation)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.ic_broken_image))
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