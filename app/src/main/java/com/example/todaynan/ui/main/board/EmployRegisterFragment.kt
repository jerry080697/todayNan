import android.util.Log
import android.view.KeyEvent
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.PostWrite
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.user.PostResponse
import com.example.todaynan.data.remote.user.UserInterface
import com.example.todaynan.data.remote.user.UserResponse
import com.example.todaynan.databinding.FragmentEmployRegisterBinding
import com.example.todaynan.ui.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployRegisterFragment : BaseFragment<FragmentEmployRegisterBinding>(FragmentEmployRegisterBinding::inflate) {

    private val userService = getRetrofit().create(UserInterface::class.java)

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
            title = binding.employRegisterTitleEt.text.toString()
            content = binding.employRegisterContentEt.text.toString()
            postWrite(title, content, category)
        }
    }

    private fun postWrite(title: String, content: String, category: String) {
        val postWrite = PostWrite(content, title, category)
        val request = "Bearer "+ AppData.appToken

        userService.post(request, postWrite).enqueue(object :
            Callback<UserResponse<PostResponse>> {
            override fun onResponse(
                call: Call<UserResponse<PostResponse>>,
                response: Response<UserResponse<PostResponse>>
            ) {
                Log.d("SERVER/SUCCESS", response.toString())
                val resp = response.body()
                Log.d("SERVER/SUCCESS", resp.toString())
            }

            override fun onFailure(call: Call<UserResponse<PostResponse>>, t: Throwable) {
                Log.d("SERVER/FAILURE", t.message.toString())
            }
        })
    }
}
