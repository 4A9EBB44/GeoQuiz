package com.bignerdranch.android.geoquiz

import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel: ViewModel() {
    //Question objects.
    var currentIndex = 0
    private val questionBank = listOf(
        Question(R.string.question_australia,true),
        Question(R.string.question_oceans,true),
        Question(R.string.question_mideast,false),
        Question(R.string.question_africa,false),
        Question(R.string.question_americas,true),
        Question(R.string.question_asia,true))

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    /**Bug on the state of T&F buttons:
     *after answer one question (pressed T or F button), the two buttons are disabled (grey out),
     *then rotate the device, the T&F buttons come back alive again...
     *While answer question in landscape mode and rotate back to vertical mode,
     * the T&F buttons become alive again...
     * */

    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun moveToPrev(){
        if(currentIndex==0){
            currentIndex = questionBank.size - 1
        }else{
            currentIndex = (currentIndex - 1) % questionBank.size
        }
    }
}