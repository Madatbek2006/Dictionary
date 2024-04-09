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
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.room.util.getColumnIndex
import com.example.dictionary.R
import com.example.dictionary.data.model.Dictionary
import com.example.dictionary.data.sourse.MyDatabase
import com.example.dictionary.databinding.ItemWordBinding
import java.util.Locale


class CursorAdapter:Adapter<CursorAdapter.Holder>(){
    private var myCursor:Cursor?=null
    private var isDataValid=true
    private var isUzb=false
    val dao=MyDatabase.getInstance().dictionaryDao()
    lateinit var textSpeech:TextToSpeech
    lateinit var onClickItem:(id:Long,pos:Int)->Unit

    var search=""

    inner class Holder(private val binding: ItemWordBinding):ViewHolder(binding.root) {


        init {
            textSpeech= TextToSpeech(binding.root.context,TextToSpeech.OnInitListener {
                if (it==TextToSpeech.SUCCESS){
                    textSpeech.setLanguage(Locale.ENGLISH)
                }
            })
            binding.root.setOnClickListener {
                myCursor?.moveToPosition(adapterPosition)
                onClickItem.invoke(myCursor!!.getLong(myCursor!!.getColumnIndexOrThrow("id")),adapterPosition)
            }

        }
        @SuppressLint("SetTextI18n")
        fun bind(dictionary:Dictionary){
            if (isUzb){
                val text =SpannableString(dictionary.uzbek)
                text.setSpan(
                    ForegroundColorSpan(Color.BLUE),
                    0,
                    search.length,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
                binding.eng.text=text
                binding.uzb.text=dictionary.english
                binding.speak.visibility=View.GONE
            }else{
                val text =SpannableString(dictionary.english)
                text.setSpan(
                    ForegroundColorSpan(Color.BLUE),
                    0,
                    search.length,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
                binding.eng.text=text
                binding.uzb.text=dictionary.uzbek
                binding.speak.visibility=View.VISIBLE
            }
            binding.type.text="[${dictionary.type}]"
            if (dictionary.is_favourite==1){
                binding.save.setBackgroundResource(R.drawable.ic_baseline_chek)
            }else{
                binding.save.setBackgroundResource(R.drawable.ic_bookmark_unchek)
            }

            binding.save.setOnClickListener {
                if (dictionary.is_favourite==1){
                    dictionary.is_favourite=0
                    binding.save.setBackgroundResource(R.drawable.ic_bookmark_unchek)
                    saveDictionary(dictionary)
                }else{
                    dictionary.is_favourite=1
                    binding.save.setBackgroundResource(R.drawable.ic_baseline_chek)
                    saveDictionary(dictionary)
                }
            }
            binding.speak.setOnClickListener {
                if (!isUzb){
                    speakOut(dictionary.english)
                    textSpeech.setLanguage(Locale.ENGLISH)
                }else{
                    textSpeech.setLanguage(Locale.KOREA)
                    speakOut(dictionary.uzbek)
                }

            }

        }
        private fun speakOut(text:String){
            textSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null,null)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =Holder(
        ItemWordBinding.inflate(LayoutInflater.from(parent.context),parent,false))

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
                val dictionary=Dictionary(id,eng,type,transcript,uz,countable,isFavourite)
                holder.bind(dictionary)
            }
        }
    }
    fun setIsUzb(bool:Boolean){
        isUzb=bool
    }
    fun saveDictionary(dictionary: Dictionary){
        dao.updateDictionary(dictionary)
    }
    fun setCursor(cursor: Cursor,search:String){
        this.search=search
        myCursor=cursor
        notifyDataSetChanged()
    }
}