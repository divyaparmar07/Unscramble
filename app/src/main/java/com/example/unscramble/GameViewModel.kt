package com.example.unscramble

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DataViewState())
    val uiState = _uiState.asStateFlow()

    var wordCount : Int by mutableStateOf(1)
        private set

    var score : Int by mutableStateOf(0)

    private var currentWord : String by mutableStateOf("")

    var editTextWord1 by mutableStateOf("")

    fun textChanged(text : String) {
        editTextWord1 = text
    }

    init {
        game()
    }

    fun game()
    {
        score=0
        wordCount=1
        _uiState.value = DataViewState(scrambleWord = takeStringRandomly())
    }

    private fun takeStringRandomly() : String
    {
        currentWord = allWords.random()
        return takeStringRandomly1(currentWord)
    }

    private fun takeStringRandomly1(word : String) : String
    {
        val charArray = word.toCharArray()
        charArray.sortDescending()
        val resultingString = charArray.joinToString(separator = "")
        _uiState.value = _uiState.value.copy(scrambleWord = resultingString)
        return resultingString
    }

    private fun updateUserGuess(word: String){
        editTextWord1 = word
    }

    fun submitButtonClicked(){

        if (currentWord == editTextWord1){

            if (wordCount < 11) {
                wordCount += 1
                score += 20
                _uiState.update { currentState->
                    currentState.copy(userGuessWrong = false)
                }
                takeStringRandomly()
            }

        }
        else if(currentWord != editTextWord1){

            _uiState.update { currentState->
                currentState.copy(userGuessWrong = true)
            }

        }
        updateUserGuess("")

    }

    fun skipButtonClicked() {
        if(wordCount < 11)
        {
            wordCount+=1
            _uiState.update {currentState->
                currentState.copy(userGuessWrong = false)
            }
            takeStringRandomly()
        }
        updateUserGuess("")
    }

}