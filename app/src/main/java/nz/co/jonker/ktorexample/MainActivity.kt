package nz.co.jonker.ktorexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.serialization.json.Json
import nz.co.jonker.ktorexample.posts.Post
import okhttp3.MediaType
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()

        val viewModel: PostsViewModel = ViewModelProvider(
            this,
            PostsViewModelFactory(retrofit)
        ).get(PostsViewModel::class.java)

        posts_recycler.layoutManager = LinearLayoutManager(this)
        posts_recycler.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        viewModel.posts.observe(this, {
            it.onSuccess { posts ->
                posts_recycler.visibility = View.VISIBLE
                error_screen.visibility = View.GONE

                posts_recycler.adapter = PostsAdapter(posts)
            }

            it.onFailure { t ->
                posts_recycler.visibility = View.GONE
                error_screen.visibility = View.VISIBLE

                Log.e("tag", "error:", t)
                error_screen.text = t.message
            }
        })
    }
}

class PostsAdapter(private val posts: List<Post>) :
    RecyclerView.Adapter<PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(parent.inflate(R.layout.item_post))

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int = posts.size
}

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val postIdTextView = itemView.findViewById<TextView>(R.id.post_id)
    private val userIdTextView = itemView.findViewById<TextView>(R.id.user_id)
    private val postTitleTextView = itemView.findViewById<TextView>(R.id.title)

    @SuppressLint("SetTextI18n")
    fun bind(post: Post) {
        postIdTextView.text = "ID: ${post.id}"
        userIdTextView.text = "User ID: ${post.userId}"
        postTitleTextView.text = post.title
    }
}

fun ViewGroup.inflate(@LayoutRes layoutId: Int): View =
    LayoutInflater.from(context).inflate(layoutId, this, false)
