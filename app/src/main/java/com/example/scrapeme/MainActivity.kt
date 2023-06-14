package com.example.scrapeme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.scrapeme.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Connection
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentprogress = 0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intcame :Intent = intent
        val user = intcame.getStringExtra("user").toString()
        val password = intcame.getStringExtra("password").toString()
        binding.lottieAnimationViewid.playAnimation()
        GlobalScope.launch {
            try {
                val cookies = Jsoup.connect("\n" +
                        "http://103.167.126.190/LoginValid.php")
                    .data("user", user, "password", password, "type", "student")
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .followRedirects(true)
                    .execute()
                    .cookies()



                val html =
                    Jsoup.connect("http://103.167.126.190/student/?value=attendance_datereporttest&left=attendance&tab=attendance")
                        .cookies(cookies)
                        .timeout(80000000)
                        .followRedirects(true)
                        .get()


                val atttable = html.select("center > table:nth-of-type(2) > tbody")


                var present :Float = 0.0F
                var absent = 0.0F
                for (row in atttable.select("tr"))
                    for (col in row.select("td")){
                        if (col.text() == "P"){
                            present++
                        }else if (col.text() == "A"){
                            absent++
                        }
                    }

                val studentname = html.select("tr:nth-of-type(1) > td:nth-of-type(2) > font > b")
                val studentrollno = html.select("tr:nth-of-type(1) > td:nth-of-type(4) > font > b")
                val year = html.select("center > table:nth-of-type(1) > tbody > tr:nth-of-type(2) > td:nth-of-type(2)")
                val branch = html.select("table:nth-of-type(1) > tbody > tr:nth-of-type(3) > td:nth-of-type(2) > font > b")
                val sem = html.select("tr:nth-of-type(2) > td:nth-of-type(4) > font > b")
                val total = absent + present
                val progresspercent = ((present / total)*100).toInt()
                val percentage = ((present / total)*100).toInt().toString()+"%"


            runOnUiThread {
                val name: String = studentname.text().substring(2, studentname.text().toString().length)
                val year: String = year.text().substring(2)
                val branch: String = branch.text().substring(2)
                val id: String = studentrollno.text().substring(2)
                val semester = sem.text().substring(2)
                binding.stdname.text = name
                binding.year.text = year+"-"+semester
                binding.rollno.text = id
                binding.branch.text = branch
                binding.present.text = present.toInt().toString()
                binding.absent.text = absent.toInt().toString()
                binding.total.text = total.toInt().toString()
                binding.percentage.text = percentage
                binding.progressbar.setProgress(progresspercent)
                binding.lottieAnimationViewid.cancelAnimation()
                binding.lottieAnimationViewid.visibility = View.GONE
                binding.table1.isVisible = true
                binding.progressbar.isVisible = true
                binding.percentage.isVisible = true
                binding.table2.isVisible = true
            }

            }catch (e:Exception){
                runOnUiThread {
                    binding.catcherror.text = e.localizedMessage!!.toString()
                    Toast.makeText(this@MainActivity,"Check Internet Connection",Toast.LENGTH_LONG).show()
                    binding.lottieAnimationViewid.cancelAnimation()
                }

            }
        }
    }
}