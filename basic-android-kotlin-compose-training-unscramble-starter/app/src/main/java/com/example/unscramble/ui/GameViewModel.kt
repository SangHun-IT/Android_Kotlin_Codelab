package com.example.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
    // ViewMoodel 클래스 내부에서만 UIState에 접근 가능하고 편집 가능하게 선언
    private val _uiState = MutableStateFlow(GameUiState())
    // 글자가 섞인 현재 단어를 저장
    private lateinit var currentWord: String
    // 사용된 단어를 저장하는 변경 가능한 집합 변수
    private var usedWords: MutableSet<String> = mutableSetOf()
    // 클래스 외부에서는 아래 포인트로만 접근 가능 (읽기 전용)
    val uiState : StateFlow<GameUiState> = _uiState.asStateFlow()

    // 사용자가 추측한 단어를 저장하기 위한 변수
    var userGuess by mutableStateOf("")
        private set

    init {
        resetGame()
    }

    //WordsData.kt에 저장된 단어들 중 임의의 단어를 선택하여 글자를 섞는 도우미 메서드
    private fun pickRandomWordAndShuffle(): String {
        currentWord = allWords.random()

        // 이미 존재하는 경우 재귀로 반복
        if(usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }

    // String을 받아 순서가 섞인 String을 반환
    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()

        tempWord.shuffle()
        // 기존 단어랑 같을 수 있으므로 같지 않을 때까지 반복
        while (String(tempWord).equals(word)) {
            tempWord.shuffle()
        }

        return String(tempWord)
    }

    // 게임을 초기화하는 함수
    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambleWord = pickRandomWordAndShuffle())
    }

    // 사용자가 UI에 입력을 하면 발생하는 이벤트 처리 함수를 정의
    fun updateUserGuess(guessWord: String) {
        userGuess = guessWord
    }

    // 사용자가 추측한 값이 currentWord와 맞는지 비교하는 함수를 정의
    fun checkUserGuess() {

        if(userGuess.equals(currentWord, ignoreCase = true)) {
            // 사용자 입력이 맞은 경우
            // 현재 상태의 값을 받아와서 SCORE_INCREASE 만큼 증가
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            // 증가된 값으로 GameState를 변경
            updateGameState(updatedScore)
        } else {
            // 사용자 입력이 틀림
            _uiState.update { currentState ->
                // copy 함수를 사용하여 객체를 복사하면
                // 일부 속성만 변경하고 나머지는 그대로 유지가 가능하다.
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        // 사용자 입력란을 Reset
        updateUserGuess("")
    }

    // 사용자가 추측한 단어가 맞으면 점수를 올리고, 다음 단어를 선택하여 게임 상태를 변경
    private fun updateGameState(updatedScore: Int) {
        // 사용자가 스킵을 맥스로 하여 더 이상 문제가 출제될 수 없는 경우에는 게임을 강제 종료
        if(usedWords.size == MAX_NO_OF_WORDS) {
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambleWord = pickRandomWordAndShuffle(),
                    currentWordCount = currentState.currentWordCount.inc(),
                    score = updatedScore
                )
            }
        }
    }

    // 사용자가 Skip 버튼을 클릭했을 때 이벤트를 처리하는 함수를 정의
    fun skipWord() {
        updateGameState(_uiState.value.score)
        updateUserGuess("")
    }
}