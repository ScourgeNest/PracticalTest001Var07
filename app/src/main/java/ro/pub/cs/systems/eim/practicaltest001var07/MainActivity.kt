package ro.pub.cs.systems.eim.practicaltest001var07

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ro.pub.cs.systems.eim.practicaltest001var07.PracticalTest001Var07SecondaryActivity

class PracticalTest001Var07MainActivity : AppCompatActivity() {

    var data00 = ""
    var data01 = ""
    var data10 = ""
    var data11 = ""

    var suma = 0.0

    var prod = 0.0

    private lateinit var startedServiceBroadcastReceiver: StartedServiceBroadcastReceiver
    private lateinit var startedServiceIntentFilter: IntentFilter

    inner class StartedServiceBroadcastReceiver() : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            var nr1 = intent?.getIntExtra("nr1",0) ?: 0
            var nr2 = intent?.getIntExtra("nr2",0) ?: 0
            var nr3 = intent?.getIntExtra("nr3",0) ?: 0
            var nr4 = intent?.getIntExtra("nr4",0) ?: 0
            Log.v("Am primit Broadcast", "${intent?.action} to package: with: ${nr1},${nr2},${nr3},${nr4}")

            var edit00 = findViewById<EditText>(R.id.edit_text_1)
            var edit01 = findViewById<EditText>(R.id.edit_text_2)
            var edit10 = findViewById<EditText>(R.id.edit_text_3)
            var edit11 = findViewById<EditText>(R.id.edit_text_4)

            edit00.text.clear()
            edit00.append(nr1.toString())
            edit01.text.clear()
            edit01.append(nr2.toString())
            edit10.text.clear()
            edit10.append(nr3.toString())
            edit11.text.clear()
            edit11.append(nr4.toString())
        }
    }

    inner class ButtonClickListener : android.view.View.OnClickListener {
        override fun onClick(v: View?) {
            if (v == null) return

            if (v.id == R.id.set) {
                val intent = Intent(
                    this@PracticalTest001Var07MainActivity,
                    PracticalTest001Var07SecondaryActivity::class.java
                )

                intent.putExtra("Data00", data00)
                intent.putExtra("Data01", data01)
                intent.putExtra("Data10", data10)
                intent.putExtra("Data11", data11)

                startActivity(intent)
            } else if (v.id == R.id.random) {
                var edit1 = findViewById<EditText>(R.id.edit_text_1)
                var edit2 = findViewById<EditText>(R.id.edit_text_2)
                var edit3 = findViewById<EditText>(R.id.edit_text_3)
                var edit4 = findViewById<EditText>(R.id.edit_text_4)

                if (edit1.text.toString() == "") {
                    val rand = (0..10).random()
                    edit1.text.clear()
                    edit1.append(rand.toDouble().toString())
                    data00 = edit1.text.toString()
                }
                if (edit2.text.toString() == "") {
                    val rand = (0..10).random()
                    edit2.text.clear()
                    edit2.append(rand.toDouble().toString())
                    data01 = edit2.text.toString()
                }
                if (edit3.text.toString() == "") {
                    val rand = (0..10).random()
                    edit3.text.clear()
                    edit3.append(rand.toDouble().toString())
                    data10 = edit3.text.toString()
                }
                if (edit4.text.toString() == "") {
                    val rand = (0..10).random()
                    edit4.text.clear()
                    edit4.append(rand.toDouble().toString())
                    data11 = edit4.text.toString()
                }
                suma = data00.toDouble() + data01.toDouble() + data10.toDouble() + data11.toDouble()
                prod = data00.toDouble() * data01.toDouble() * data10.toDouble() * data11.toDouble()
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

        startedServiceBroadcastReceiver = StartedServiceBroadcastReceiver()

        startedServiceIntentFilter = IntentFilter().apply {
            addAction("Update")
        }

        val intent = Intent().apply {
            component = ComponentName(
                "ro.pub.cs.systems.eim.practicaltest001var07",
                "ro.pub.cs.systems.eim.practicaltest001var07.PracticalTest01Var07Service"
            )
        }

        // De la Android Oreo (Android 8) în sus se folosește startForegroundService
        startForegroundService(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("suma", suma.toString())
        outState.putString("prod", prod.toString())

    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        suma = savedInstanceState.getString("suma", "0").toDouble()
        prod = savedInstanceState.getString("prod", "0").toDouble()

        Log.v("Suma!!!", "Suma este: " + suma.toString())
        Log.v("Prod!!!", "Produsul este: " + prod.toString())
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(startedServiceBroadcastReceiver, startedServiceIntentFilter, Context.RECEIVER_EXPORTED)
        } else {
            @Suppress("DEPRECATION")
            registerReceiver(startedServiceBroadcastReceiver, startedServiceIntentFilter)
        }
    }

    override fun onPause() {
        if (::startedServiceBroadcastReceiver.isInitialized) {
            unregisterReceiver(startedServiceBroadcastReceiver)
        }

        super.onPause()
    }
}