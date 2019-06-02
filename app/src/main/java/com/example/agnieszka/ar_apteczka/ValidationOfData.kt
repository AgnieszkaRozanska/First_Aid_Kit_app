package com.example.agnieszka.ar_apteczka

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView

fun validationDataSoThatTheAreNotZero(textedit: EditText, warm_informations: TextView)
{
    textedit.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (textedit.length()==0){
                warm_informations.text = "To pole nie może być puste!"
                warm_informations.visibility= TextView.VISIBLE
            }
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (textedit.length()<1){
                warm_informations.text = "To pole nie może być puste!"
                warm_informations.visibility= TextView.VISIBLE            }
            else{
                warm_informations.visibility= TextView.INVISIBLE
            }
        }

    })
}