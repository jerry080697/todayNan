import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.PostWrite
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.post.Post
import com.example.todaynan.data.remote.post.PostInterface
import com.example.todaynan.data.remote.post.PostResponse
import com.example.todaynan.databinding.FragmentEmployRegisterBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.main.board.DetailFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployRegisterFragment : BaseFragment<FragmentEmployRegisterBinding>(FragmentEmployRegisterBinding::inflate) {

    private val postService = getRetrofit().create(PostInterface::class.java)

    private var title: String = ""
    private var content: String = ""
    private val category: String = "EMPLOY"

    override fun initAfterBinding() {

        binding.employRegisterBackIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.employRegisterTitleEt.setOnEditorActionListener { v, actionId, event ->
            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard()
                true
            } else {
                false
            }
        }

        binding.employRegisterBtn.setOnClickListener {
            if(binding.employRegisterTitleEt.text.isEmpty()){
                Toast.makeText(requireContext(),"제목을 입력하세요", Toast.LENGTH_SHORT).show()
            }
            else if(binding.employRegisterContentEt.text.isEmpty()){
                Toast.makeText(requireContext(),"내용을 입력하세요", Toast.LENGTH_SHORT).show()
            }
            else{
                title = binding.employRegisterTitleEt.text.toString()
                content = binding.employRegisterContentEt.text.toString()
                postWrite(title, content, category)
            }
        }
    }

    private fun postWrite(title: String, content: String, category: String) {
        val postWrite = PostWrite(content, title, category)
        val request = "Bearer "+ AppData.appToken

        postService.post(request, postWrite).enqueue(object :
            Callback<PostResponse<Post>> {
            override fun onResponse(
                call: Call<PostResponse<Post>>,
                response: Response<PostResponse<Post>>
            ) {
                Log.d("SERVER/SUCCESS", response.toString())
                val resp = response.body()
                Log.d("SERVER/SUCCESS", resp.toString())
            }

            override fun onFailure(call: Call<PostResponse<Post>>, t: Throwable) {
                Log.d("SERVER/FAILURE", t.message.toString())
            }
        })
    }
}
