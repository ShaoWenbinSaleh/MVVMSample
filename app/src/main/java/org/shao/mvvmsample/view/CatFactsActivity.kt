package org.shao.mvvmsample.view

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.shao.mvvmsample.R
import org.shao.mvvmsample.databinding.CatFactsActivityBinding
import org.shao.mvvmsample.model.RandomCatFactService
import org.shao.mvvmsample.utility.HOST_API
import org.shao.mvvmsample.viewmodel.CatFactsViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CatFactsActivity : RxAppCompatActivity() {

    private lateinit var mViewModel : CatFactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: CatFactsActivityBinding = DataBindingUtil.setContentView(this, R.layout.cat_facts_activity)

        val remote = Retrofit.Builder()
            .baseUrl(HOST_API)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RandomCatFactService::class.java)

        mViewModel = CatFactsViewModel(remote)

        binding.button.setOnClickListener {
            mViewModel.loadRandomCatFact().compose(bindToLifecycle())
                .subscribe ({},{ it -> dispatchError(it) })
        }

        binding.vm = mViewModel

        //set the lifeCycleOwner, otherwise it will the UI will not be synchronized
        binding.lifecycleOwner = this
    }

    //error handler
    private fun dispatchError(error:Throwable?, length:Int= Toast.LENGTH_LONG){
        error?.let {
            Toast.makeText(this, "error" + it.message, length).show()
        }
    }
}
