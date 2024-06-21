package com.example.dictionary.presenter.screen.speak

class SpeakPresenter(private val view: SpeakController.View):SpeakController.Presenter {

    private val model:SpeakController.Model=SpeakModel()
    override fun loadText(boll: Boolean,text:String) {
        if (boll){
//            view.showText(model.getUzbText(text = text))
        }else{
//            view.showText(model.getEngText(text = text))
        }
    }
}