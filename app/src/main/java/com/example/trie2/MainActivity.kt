package com.example.trie2

import android.os.Bundle
import android.text.Spanned
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trie2.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    lateinit var  binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val searchQuery = "Ak"
        binding.apply {
            button.setOnClickListener {
                val text = etNumber.text.toString().toIntOrNull()
                text?.let {size->
                    CoroutineScope(Dispatchers.Default).launch {
                        val list = getBuiltString(size)
                        val time = measureTimeMillis {
                            list.forEach { str ->
//                                str.filterHtmlTags()
                            }
                        }
                        Log.d("AKSHAT", "onCreate: ${time} $list")
                    }
                }
            }
        }
    }
    private suspend fun getBuiltString(size: Int): List<String> = withContext(Dispatchers.Default) {
        return@withContext List<String>(size) {
            "<p><font color = \"aqua\">Hello There How are You doing!!</font><h1>This is html</h1></p>"
        }
    }

    private suspend fun generateRandomString(size: Int): String = withContext(Dispatchers.Default){
        val charset = "abcdefghijklmn"
        return@withContext "Akshat" + (1..size).joinToString("") {
            "${charset[Random.nextInt(charset.length)]}"
        }
    }
    private fun String.fromHtml(): Spanned {
        return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}