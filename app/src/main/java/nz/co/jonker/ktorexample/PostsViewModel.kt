package nz.co.jonker.ktorexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import nz.co.jonker.ktorexample.posts.Post
import nz.co.jonker.ktorexample.posts.PostsRepo
import nz.co.jonker.ktorexample.posts.RetrofitPostsRepo
import retrofit2.Retrofit

class PostsViewModel(private val postsRepo: PostsRepo) : ViewModel() {

    val posts = liveData<Result<List<Post>>>(Dispatchers.IO) {
        try {
            val posts = postsRepo.getPosts()
            emit(Result.success(posts))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}

class PostsViewModelFactory(private val postsRepo: PostsRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostsViewModel::class.java)) {
            return PostsViewModel(postsRepo) as T
        } else throw IllegalArgumentException("modelClass has to be of type: PostsViewModel, but was type ${modelClass.simpleName}")
    }
}
