package com.example.dictionary.presenter.adapper

import android.annotation.SuppressLint
import android.database.Cursor
import android.graphics.Color
import android.speech.tts.TextToSpeech
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
import com.example.dictionary.data.model.Dictionary
import com.example.dictionary.data.model.Stared
import com.example.dictionary.data.sourse.MyDatabase
import com.example.dictionary.data.sourse.MyDatabase2
import com.example.dictionary.databinding.ItemFavoritesBinding
import com.l4digital.fastscroll.FastScroller
import java.util.Locale

class FavoriteAdapter private constructor(): RecyclerView.Adapter<FavoriteAdapter.Holder>(){

    companion object{
        private val instance=FavoriteAdapter()


        fun getInstance():FavoriteAdapter{
            return instance
        }
    }
    private var myCursor: Cursor?=null
    private var isDataValid=true
    private var isUzb=false
    val dao= MyDatabase.getInstance().dictionaryDao()
    val stardDao= MyDatabase2.getInstance().dictionaryDao()
    lateinit var textSpeech: TextToSpeech
    lateinit var onClickItem:(id:Long,pos:Int)->Unit
    lateinit var onClickTrash:(pos:Int)->Unit

    var search=""

    inner class Holder(private val binding: ItemFavoritesBinding): RecyclerView.ViewHolder(binding.root) {


        init {
            binding.root.setOnClickListener {
                myCursor?.moveToPosition(adapterPosition)
                onClickItem.invoke(myCursor!!.getLong(myCursor!!.getColumnIndexOrThrow("id")),adapterPosition)
            }

        }
        @SuppressLint("SetTextI18n")
        fun bind(dictionary: Dictionary){
            binding.eng.text=dictionary.english
            binding.type.text="[${dictionary.type}]"

            binding.trash.setOnClickListener {
                dictionary.is_favourite=0
                saveDictionary(dictionary)
                stardDao.updateStared(Stared(dictionary.id,0))
                onClickTrash?.invoke(adapterPosition)
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =Holder(
        ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int =myCursor?.count ?:0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (!isDataValid)return
        myCursor?.let {
            if (it.moveToPosition(position)){
                val type=it.getString(it.getColumnIndexOrThrow("type"))
                val eng=it.getString(it.getColumnIndexOrThrow("english"))
                val uz=it.getString(it.getColumnIndexOrThrow("uzbek"))
                val isFavourite=it.getInt(it.getColumnIndexOrThrow("is_favourite"))
                val countable=it.getString(it.getColumnIndexOrThrow("countable"))
                val id=it.getLong(it.getColumnIndexOrThrow("id"))
                val transcript=it.getString(it.getColumnIndexOrThrow("transcript"))
                val dictionary= Dictionary(id,eng,type,transcript,uz,countable,isFavourite)
                holder.bind(dictionary)
            }
        }
    }
    fun saveDictionary(dictionary: Dictionary){
        dao.updateDictionary(dictionary)
    }
    fun setCursor(cursor: Cursor){
        myCursor=cursor
        notifyDataSetChanged()
    }
}