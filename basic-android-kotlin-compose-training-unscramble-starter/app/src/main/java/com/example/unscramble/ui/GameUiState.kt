package com.example.unscramble.ui

data class GameUiState(
    // 글자가 섞인 현재 단어를 저장하기 위한 변수
    val currentScrambleWord: String = "",
    val currentWordCount: Int = 1,
    val score: Int = 0,
    val isGuessedWordWrong: Boolean = false,
    val isGameOver: Boolean = false
)
