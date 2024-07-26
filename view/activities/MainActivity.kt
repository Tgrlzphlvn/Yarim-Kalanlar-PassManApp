package com.ozpehlivantugrul.passmanapp.view.activities

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.ozpehlivantugrul.passmanapp.ui.theme.PassmanAppTheme
import com.ozpehlivantugrul.passmanapp.view.navigations.LoginNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        enableEdgeToEdge()
        setContent {
            PassmanAppTheme {
                LoginNavigation()
            }
        }
    }


    private fun scrapSite() {

        CoroutineScope(Dispatchers.IO).launch {
            val url = "https://www.petrolofisi.com.tr/akaryakit-fiyatlari"
            val document: Document = Jsoup.connect(url).get()

            val div: Element? = document.select("div.fuel-items").first()
            val table: Element? = div?.select("table")?.first()

            val rows: Elements? = table?.select("tr")

            if (rows != null)
                for (row in rows) {
                    val cols: Elements = row.select("td")
                    for (col in cols) {
                        println(col.text())
                    }
                }
        }
    }
}

