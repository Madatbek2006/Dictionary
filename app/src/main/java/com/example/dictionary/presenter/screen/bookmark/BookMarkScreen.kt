package com.example.dictionary.presenter.screen.bookmark

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.presenter.adapper.CursorAdapter
import com.example.dictionary.databinding.ScreenBookmarkBinding
import com.example.dictionary.presenter.dialog.BottomSheetDialog
import com.example.dictionary.presenter.dialog.DictionaryDialog
import com.example.dictionary.utils.popBackStack

class BookMarkScreen:Fragment(),BookMarkController.View {
    private var _binding:ScreenBookmarkBinding?=null
    private val binding by lazy { _binding!! }
    private val adapter by lazy { CursorAdapter() }
    private val presenter:BookMarkController.Presenter by lazy { BookMarkPresenter(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding=ScreenBookmarkBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }
    private fun init(){
        binding.apply {
            recyclerView.adapter=adapter
            recyclerView.layoutManager=LinearLayoutManager(requireActivity())
            back.setOnClickListener {
                presenter.onClickBack()
            }
        }
        presenter.loadDictionary()
        adapter.onClickItem={it,pos->
            val bottom= BottomSheetDialog().apply {
                arguments= bundleOf(Pair("pos",it))
            }
            bottom.show(requireActivity().supportFragmentManager,"")
            bottom.onDestroyListener={

            }
        }
    }



    override fun showDictionary(cursor: Cursor) {
        if (cursor.count==0){
            binding.notFound.visibility=View.VISIBLE
        }else{
            binding.notFound.visibility=View.INVISIBLE
        }
        adapter.setCursor(cursor,"")
    }

    override fun back() {
        popBackStack()
    }


}