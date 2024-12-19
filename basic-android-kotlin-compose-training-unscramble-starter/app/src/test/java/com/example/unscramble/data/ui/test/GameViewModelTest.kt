package com.example.unscramble.data.ui.test

import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.getUnscrambledWord
import com.example.unscramble.ui.GameViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Assert
import org.junit.Test

class GameViewModelTest {

    private val viewModel = GameViewModel()

    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        // 사용자가 올바른 단어를 입력했을 때 정상적으로
        // 점수, 단어 개수, 글자가 뒤섞인 단어가 업데이트 되는지 테스트

        // 현재 화면의 상태를 ViewModel을 통해 받음.
        var currentGameUiState = viewModel.uiState.value
        // WordsData.kt 파일에 이미 정의되어 있는 현재 사용자가 보고 있는 문제의
        // 정답을 받아옴.
        var correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambleWord)

        // 화면에 정답(단어)를 사용자가 입력했다고 알림(상태를 업데이트)
        viewModel.updateUserGuess(correctPlayerWord)
        // 정답이 맞는지 체크
        // 체크함과 동시에 정상적이라면 상태 및 UI가 업데이트가 되어야 함.
        viewModel.checkUserGuess()

        // 작업을 모두 마친 현재 상태를 받아옴.
        currentGameUiState = viewModel.uiState.value

        // 테스트 결과를 알림 - 1  
        // 맞는 테스트 케이스(즉 isGuessedWordWrong = False)인지를 체크하고 성공/실패 여부를 알림
        // isGuessedWordWrong 이 True이면 False
        // assertFalse는 인자 값이 False 일 때만 Pass를 알림
        assertFalse(currentGameUiState.isGuessedWordWrong)
        
        // 테스트 결과를 알림 - 2
        // 점수가 일치하는지 체크
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
    }

    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }

    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        // 일부러 오류 케이스를 만듬. 사용자가 and 라고 입력한 예시
        val incorrectPlayerWord = "and"

        viewModel.updateUserGuess(incorrectPlayerWord)
        viewModel.checkUserGuess()

        val currentGameUiState = viewModel.uiState.value

        // 잘못된 정답이기에 0과 같아야 함.
        assertEquals(0, currentGameUiState.score)
        // 잘못된 정답이기에 isGuessedWordWrong은 True가 되어야 함.
        // True 일 때만 Pass
        assertTrue(currentGameUiState.isGuessedWordWrong)
    }

    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {

        val gameUiState = viewModel.uiState.value
        val unScrambledWord = getUnscrambledWord(gameUiState.currentScrambleWord)

        // 앱 초기 상태에 표시된 것이 뒤섞인 단어인지 테스트
        // 둘이 같지 않을 때에만 Pass
        Assert.assertNotEquals(unScrambledWord, gameUiState.currentScrambleWord)
        // 앱 초기 상태에 wordCount가 1인지 체크하여 테스트
        // assertTrue는 인자 값이 True 일 때만 Pass
        assertTrue(gameUiState.currentWordCount == 1)
        // 앱 초기 상태에 score 값이 0인지 테스트
        // assertTrue는 인자 값이 True 일 때만 Pass
        assertTrue(gameUiState.score == 0)
        // 앱 초기 상태에 사용자 입력이 틀렸다고 나오는지 체크 (False여야 함)
        // assertFalse는 인자 값이 False 일 때만 패스
        assertFalse(gameUiState.isGuessedWordWrong)
        // 앱 초기 상태가 게임 종료 상태인지 테스트
        // assertFalse는 인자 값이 False 일 때만 패스
        assertFalse(gameUiState.isGameOver)
    }

    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdateCorrectly() {

        var expectedScore = 0
        var currentGameUiState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambleWord)

        // 전체 단어 개수 10개 만큼 반복
        repeat(MAX_NO_OF_WORDS) {
            // 모두 맞췄을 경우를 가정하고 테스트 하기에
            // 그에 맞게 점수 증가
            expectedScore += SCORE_INCREASE
            // 실제로 사용자가 올바른 답을 입력했다고 업데이트를 하게끔 하고
            viewModel.updateUserGuess(correctPlayerWord)
            // viewModel도 상태를 업데이트하여 점수가 반영되게 체크
            viewModel.checkUserGuess()
            // 현재 상태를 다시 받아오고, 그 이후에 비교를 수행
            currentGameUiState = viewModel.uiState.value
            correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambleWord)
            // 정상적으로 점수가 올라갔는지 테스트
            assertEquals(expectedScore, currentGameUiState.score)
        }

        // 모든 반복을 수행한 후에 상태가 올바른지 테스트
        // 표시되는 단어 개수가 10개로 표시가 되는지 테스트
        assertEquals(MAX_NO_OF_WORDS, currentGameUiState.currentWordCount)
        // 게임 종료 상태가 됐는지 테스트
        assertTrue(currentGameUiState.isGameOver)

    }

    @Test
    fun gameViewModel_WordSkipped_ScoreUnchangedAndWordCountIncreased() {
        // 사용자가 Skip 버튼을 눌렀을 때의 상태를 테스트 하는 함수
        // Skip 버튼을 누르면 Score는 변하지 않고 WordCount는 증가되어야 함.

        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambleWord)

        // 점수 체크를 위해 일단 1개는 맞춘 상태로 둠
        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value
        val lastWordCount = currentGameUiState.currentWordCount
        // Skip 버튼 클릭
        viewModel.skipWord()
        currentGameUiState = viewModel.uiState.value
        // Skip 버튼 클릭한 이후 상태 체크
        // 첫 문제만 맞았을 때 점수랑 같은지 체크
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
        // word count가 증가가 됐는지 체크
        assertEquals(lastWordCount + 1, currentGameUiState.currentWordCount)

    }
}