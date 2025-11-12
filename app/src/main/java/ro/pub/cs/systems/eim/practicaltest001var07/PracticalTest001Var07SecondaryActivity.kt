package ro.pub.cs.systems.eim.practicaltest001var07

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ro.pub.cs.systems.eim.practicaltest001var07.R

class PracticalTest001Var07SecondaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test001_var07_secondary)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val text00 = intent.getStringExtra("Data00")
        val text01 = intent.getStringExtra("Data01")
        val text10 = intent.getStringExtra("Data10")
        val text11 = intent.getStringExtra("Data11")

        var btn_text00 = findViewById<TextView>(R.id.text_view_s_s)
        var btn_text01 = findViewById<TextView>(R.id.text_view_s_d)
        var btn_text10 = findViewById<TextView>(R.id.text_view_s_j)
        var btn_text11 = findViewById<TextView>(R.id.text_view_d_j)

        btn_text00.hint = text00
        btn_text01.hint = text01
        btn_text10.hint = text10
        btn_text11.hint = text11

        var sum_btn = findViewById<Button>(R.id.sum).setOnClickListener { v ->
            var sum = (text00?.toDouble() ?: 0.0) + (text01?.toDouble() ?: 0.0) + (text10?.toDouble() ?: 0.0) + (text11?.toDouble() ?: 0.0)
            Toast.makeText(v.context ,"Suma este: " + sum.toString(), Toast.LENGTH_SHORT).show()
            finish()
        }

        var prod_btn = findViewById<Button>(R.id.prod).setOnClickListener { v ->
            var prod = (text00?.toDouble() ?: 1.0) * (text01?.toDouble() ?: 1.0) * (text10?.toDouble() ?: 1.0) * (text11?.toDouble() ?: 1.0)
            Toast.makeText(v.context ,"Produsul este: " + prod.toString(), Toast.LENGTH_LONG).show()
            finish()
        }

    }
}