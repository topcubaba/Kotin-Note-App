import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.task.noteapp.R

fun ImageView.loadImage(url: String) {
    Glide.with(context).load(url).listener(object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            this@loadImage.visibility = View.GONE
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            //change card view visibility
            this@loadImage.visibility = View.VISIBLE
            this@loadImage.setImageDrawable(resource)
            return true
        }

    }).into(this@loadImage)
}

fun chooseColor(position: Int): Int {
    if (position % 2 == 0) {
        return (R.color.purple)
    } else {
        return (R.color.teal)
    }
}
