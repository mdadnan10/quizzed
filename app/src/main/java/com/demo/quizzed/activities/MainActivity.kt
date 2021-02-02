package com.demo.quizzed.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.demo.quizzed.R
import com.demo.quizzed.activities.adapters.QuizAdapter
import com.demo.quizzed.activities.models.Quiz
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: QuizAdapter

    private var quizList = mutableListOf<Quiz>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        setUpViews()

    }


    private fun setUpViews() {
        setUpFireStore()
        setUpDrawerLayout()
        setUpRecyclerView()
        setUpDatePicker()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setUpDatePicker() {
        btnDatePicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
                val dateFormatter = SimpleDateFormat("dd-mm-yyyy")
                val date = dateFormatter.format(Date(it))
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("DATE", date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER", "Date Picker Cancelled")
            }
        }
    }

    private fun setUpFireStore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("quizzes")
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Log.d("error", "value is: $value, Error is $error")
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            } else {
                Log.d("DATA", value.toObjects(Quiz::class.java).toString())
                quizList.clear()
                quizList.addAll(value.toObjects(Quiz::class.java))
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = QuizAdapter(this, quizList)
        quizRecyclerView.layoutManager = GridLayoutManager(this, 2)
        quizRecyclerView.adapter = adapter
    }

    private fun setUpDrawerLayout() {
        setSupportActionBar(appBar)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this, mainDrawer,
            R.string.app_name,
            R.string.app_name
        )
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.btnProfile -> {
                    Toast.makeText(this, "Profile Button Clicked", Toast.LENGTH_SHORT).show()
                    mainDrawer.closeDrawers()
                }

                R.id.btnFollowUs -> {
                    Toast.makeText(this, "Follow Us Button Clicked", Toast.LENGTH_SHORT).show()
                    mainDrawer.closeDrawers()
                }

                R.id.btnRateUs -> {
                    Toast.makeText(this, "Rate Us Button Clicked", Toast.LENGTH_SHORT).show()
                    mainDrawer.closeDrawers()
                }

                R.id.btnLogout -> {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Confirmation")
                    builder.setMessage("Are you sure you want to Logout?")
                    builder.setPositiveButton("YES") { _, _ ->
                        FirebaseAuth.getInstance().signOut()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    builder.setNegativeButton("NO") { response, _ ->
                        response.dismiss()
                    }
                    builder.create()
                    builder.setCancelable(false)
                    builder.show()

                    mainDrawer.closeDrawers()
                }
            }

            return@setNavigationItemSelectedListener true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}