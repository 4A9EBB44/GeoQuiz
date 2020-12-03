package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var previousButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX,0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        previousButton = findViewById(R.id.previous_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener{ view: View->
            checkAnswer(true)
            trueButton.isEnabled=false
            falseButton.isEnabled=false
        }

        falseButton.setOnClickListener{view: View->
            checkAnswer(false)
            falseButton.isEnabled=false
            trueButton.isEnabled=false
        }
        /**Bug on the state of T&F buttons:
         *after answer one question (pressed T or F button), the two buttons are disabled (grey out),
         *then rotate the device, the T&F buttons come back alive again...
         *While answer question in landscape mode and rotate back to vertical mode,
         * the T&F buttons become alive again...
         * */

        previousButton.setOnClickListener{
            trueButton.isEnabled=true
            falseButton.isEnabled=true
            quizViewModel.moveToPrev()
            updateQuestion()

        }

        nextButton.setOnClickListener{
            trueButton.isEnabled=true
            falseButton.isEnabled=true
            quizViewModel.moveToNext()
            updateQuestion()
        }

        questionTextView.setOnClickListener{
            trueButton.isEnabled=true
            falseButton.isEnabled=true
            quizViewModel.moveToNext()
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy() called")
    }

    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if(userAnswer==correctAnswer){
            R.string.correct_toast
        }else{
            R.string.incorrect_toast
        }
        //set the toast on TOP with setGravity(Gravity.TOP) fun,
        //somehow, it doesn't work on emulator but work on phones
        val myToast = Toast.makeText(this,messageResId,Toast.LENGTH_SHORT)
        myToast.setGravity(Gravity.TOP,0,200)
        myToast.show()
    }
    //Ch3 Challenge 2 pending complete, hint:
    //https://www.chegg.com/homework-help/questions-and-answers/challenge-graded-quiz-user-provides-answers-quiz-questions-display-toast-percentage-score--q61363648
    //https://forums.bignerdranch.com/t/graded-quiz-challenge/13243
    /**private fun displayScore(){
    var score = 0
    var scoreMessage = "Quiz Complete! Score:$score"
    var scoreToast = Toast.makeText(this,scoreMessage,Toast.LENGTH_SHORT)
    scoreToast.setGravity(Gravity.TOP,0,200)
    scoreToast.show()
    }*/
}