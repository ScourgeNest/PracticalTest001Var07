package ro.pub.cs.systems.eim.practicaltest001var07

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest001Var07MainActivity : AppCompatActivity() {

    inner class ButtonClickListener : android.view.View.OnClickListener {
        override fun onClick(v: View?) {
            if(v == null) return

            if(v.id == R.id.set) {

            } else if (v.id == R.id.random){
                var edit1 = findViewById<EditText>(R.id.edit_text_1)
                var edit2 = findViewById<EditText>(R.id.edit_text_2)
                var edit3 = findViewById<EditText>(R.id.edit_text_3)
                var edit4 = findViewById<EditText>(R.id.edit_text_4)

                if (!edit1.text.isDigitsOnly() || edit1.text.toString() == "") {
                    val rand = (0..10).random()
                    edit1.text.clear()
                    edit1.append(rand.toDouble().toString())
                }
                if (!edit2.text.isDigitsOnly() || edit2.text.toString() == "") {
                    val rand = (0..10).random()
                    edit2.text.clear()
                    edit2.append(rand.toDouble().toString())
                }
                if (!edit3.text.isDigitsOnly() || edit3.text.toString() == "") {
                    val rand = (0..10).random()
                    edit3.text.clear()
                    edit3.append(rand.toDouble().toString())
                }
                if (!edit4.text.isDigitsOnly() || edit4.text.toString() == "") {
                    val rand = (0..10).random()
                    edit4.text.clear()
                    edit4.append(rand.toDouble().toString())
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test001_var07_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toggleButton = ButtonClickListener()

        val buttons = listOf(
            R.id.set, R.id.random
        )

        for (id in buttons) {
            findViewById<View>(id).setOnClickListener(toggleButton)
        }

    }
}