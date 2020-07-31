package com.example.android.a2048game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var gameScoreTextView : TextView
    private lateinit var swipeViewHolder : FrameLayout
    private lateinit var resetButton : Button
    private lateinit var resultTextView : TextView
    private lateinit var startGameButton : Button

    private var emptyFields : Int = 16
    private var score : Int = 0

    private lateinit var gameField : Array<Array<Button>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeViewHolder = findViewById(R.id.transparent_view)
        resultTextView = findViewById(R.id.result_tv)
        gameScoreTextView = findViewById(R.id.game_score_tv)

        gameField = Array(4) {row ->
            Array(4) {col ->
                Button(this)
            }
        }

        gameField[0][0] = findViewById(R.id.button_1_1)
        gameField[0][1] = findViewById(R.id.button_1_2)
        gameField[0][2] = findViewById(R.id.button_1_3)
        gameField[0][3] = findViewById(R.id.button_1_4)
        gameField[1][0] = findViewById(R.id.button_2_1)
        gameField[1][1] = findViewById(R.id.button_2_2)
        gameField[1][2] = findViewById(R.id.button_2_3)
        gameField[1][3] = findViewById(R.id.button_2_4)
        gameField[2][0] = findViewById(R.id.button_3_1)
        gameField[2][1] = findViewById(R.id.button_3_2)
        gameField[2][2] = findViewById(R.id.button_3_3)
        gameField[2][3] = findViewById(R.id.button_3_4)
        gameField[3][0] = findViewById(R.id.button_4_1)
        gameField[3][1] = findViewById(R.id.button_4_2)
        gameField[3][2] = findViewById(R.id.button_4_3)
        gameField[3][3] = findViewById(R.id.button_4_4)


        swipeViewHolder.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeDown() {
                super.onSwipeDown()

                for (col in 0..3){
                    var lastFoundFieldIndex : Int = 4
                    var foundPair : Boolean = false
                    for (row in 3 downTo 0) {
                        if(gameField[row][col].text != "") {
                            if (lastFoundFieldIndex == 4) {
                                lastFoundFieldIndex--
                                if (row != 3) {
                                    switchFieldsInColumn(col, row, lastFoundFieldIndex)
                                }
                            } else {
                                if (gameField[row][col].text == gameField[lastFoundFieldIndex][col].text) {
                                    if (!foundPair) {
                                        foundPair = true
                                        val newFieldButton = newField(gameField[row][col].text.toString().toInt())
                                        if (newFieldButton != null) {
                                            gameField[lastFoundFieldIndex][col].text = newFieldButton.text
                                            gameField[lastFoundFieldIndex][col].background = newFieldButton.background
                                            gameField[row][col].text = ""
                                            gameField[row][col].setBackgroundResource(R.drawable.button_empty)
                                        } else {
                                            Toast.makeText(
                                                this@MainActivity,
                                                "error creating new value",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    } else {
                                        foundPair = false
                                        lastFoundFieldIndex--
                                        switchFieldsInColumn(col, row, lastFoundFieldIndex)
                                    }
                                } else if(lastFoundFieldIndex - row > 1) {
                                    lastFoundFieldIndex--
                                    switchFieldsInColumn(col, row, lastFoundFieldIndex)
                                } else {
                                    lastFoundFieldIndex--
                                }
                            }
                        }
                    }
                }
                newFieldWithTwo()
            }

            override fun onSwipeUp() {
                super.onSwipeUp()

                for (col in 0..3){
                    var lastFoundFieldIndex : Int = -1
                    var foundPair : Boolean = false
                    for (row in 0..3) {
                        if(gameField[row][col].text != "") {
                            if (lastFoundFieldIndex == -1) {
                                lastFoundFieldIndex++
                                if (row != 0) {
                                    switchFieldsInColumn(col, row, lastFoundFieldIndex)
                                }
                            } else {
                                if (gameField[row][col].text == gameField[lastFoundFieldIndex][col].text) {
                                    if (!foundPair) {
                                        foundPair = true
                                        val newFieldButton = newField(gameField[row][col].text.toString().toInt())
                                        if (newFieldButton != null) {
                                            gameField[lastFoundFieldIndex][col].text = newFieldButton.text
                                            gameField[lastFoundFieldIndex][col].background = newFieldButton.background
                                            gameField[row][col].text = ""
                                            gameField[row][col].setBackgroundResource(R.drawable.button_empty)
                                        } else {
                                            Toast.makeText(
                                                this@MainActivity,
                                                "error creating new value",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    } else {
                                        foundPair = false
                                        lastFoundFieldIndex++
                                        switchFieldsInColumn(col, row, lastFoundFieldIndex)
                                    }
                                } else if(row - lastFoundFieldIndex > 1) {
                                    lastFoundFieldIndex++
                                    switchFieldsInColumn(col, row, lastFoundFieldIndex)
                                } else {
                                    lastFoundFieldIndex++
                                }
                            }
                        }
                    }
                }
                newFieldWithTwo()
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()

                for (row in gameField){
                    var lastFoundFieldIndex : Int = -1
                    var foundPair : Boolean = false
                    for (i in 0..3) {
                        if(row[i].text != "") {
                            if (lastFoundFieldIndex == -1) {
                                lastFoundFieldIndex++
                                if (i != 0) {
                                    switchFieldsInRow(gameField.indexOf(row), i, lastFoundFieldIndex)
                                }
                            } else {
                                if (row[i].text == row[lastFoundFieldIndex].text) {
                                    if (!foundPair) {
                                        foundPair = true
                                        val newFieldButton = newField(row[i].text.toString().toInt())
                                        if (newFieldButton != null) {
                                            row[lastFoundFieldIndex].text = newFieldButton.text
                                            row[lastFoundFieldIndex].background = newFieldButton.background
                                            row[i].text = ""
                                            row[i].setBackgroundResource(R.drawable.button_empty)
                                        } else {
                                            Toast.makeText(
                                                this@MainActivity,
                                                "error creating new value",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    } else {
                                        foundPair = false
                                        lastFoundFieldIndex++
                                        switchFieldsInRow(gameField.indexOf(row), i, lastFoundFieldIndex)
                                    }
                                } else if(i - lastFoundFieldIndex > 1) {
                                    lastFoundFieldIndex++
                                    switchFieldsInRow(gameField.indexOf(row), i, lastFoundFieldIndex)
                                } else {
                                    lastFoundFieldIndex++
                                }
                            }
                        }
                    }
                }
                newFieldWithTwo()
            }

            override fun onSwipeRight() {
                super.onSwipeRight()

                for (row in gameField){
                    var lastFoundFieldIndex : Int = 4
                    var foundPair : Boolean = false
                    for (i in 3 downTo 0) {
                        if(row[i].text != "") {
                            if (lastFoundFieldIndex == 4) {
                                lastFoundFieldIndex--
                                if (i != 3) {
                                    switchFieldsInRow(gameField.indexOf(row), i, lastFoundFieldIndex)
                                }
                            } else {
                                if (row[i].text == row[lastFoundFieldIndex].text) {
                                    if (!foundPair) {
                                        foundPair = true
                                        val newFieldButton = newField(row[i].text.toString().toInt())
                                        if (newFieldButton != null) {
                                            row[lastFoundFieldIndex].text = newFieldButton.text
                                            row[lastFoundFieldIndex].background = newFieldButton.background
                                            row[i].text = ""
                                            row[i].setBackgroundResource(R.drawable.button_empty)
                                        } else {
                                            Toast.makeText(
                                                this@MainActivity,
                                                "error creating new value",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    } else {
                                        foundPair = false
                                        lastFoundFieldIndex--
                                        switchFieldsInRow(gameField.indexOf(row), i, lastFoundFieldIndex)
                                    }
                                } else if(lastFoundFieldIndex - i > 1) {
                                    lastFoundFieldIndex--
                                    switchFieldsInRow(gameField.indexOf(row), i, lastFoundFieldIndex)
                                } else {
                                    lastFoundFieldIndex--
                                }
                            }
                        }
                    }
                }
                newFieldWithTwo()
            }
        })

        resetButton = findViewById(R.id.reset_button)
        resetButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                gameStart()
            }
        })

        startGameButton = findViewById(R.id.start_game_button)
        startGameButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                gameStart()
            }

        })

        setInitialScore()
    }

    fun switchFieldsInRow(row: Int, fromField: Int, toField: Int) {
        gameField[row][toField].text = gameField[row][fromField].text
        gameField[row][toField].background = gameField[row][fromField].background
        gameField[row][fromField].text = ""
        gameField[row][fromField].setBackgroundResource(R.drawable.button_empty)
    }

    fun switchFieldsInColumn(col: Int, fromField: Int, toField: Int) {
        gameField[toField][col].text = gameField[fromField][col].text
        gameField[toField][col].background = gameField[fromField][col].background
        gameField[fromField][col].text = ""
        gameField[fromField][col].setBackgroundResource(R.drawable.button_empty)
    }

    fun randomIndex() : Int = Random.nextInt(0, 4)

    fun newFieldWithTwo() : Button? {
        if(emptyFields <= 0) {
            gameOver(false)
            return null
        }
        val newFieldWithTwo = gameField[randomIndex()][randomIndex()]
        if(newFieldWithTwo.text != "") {
            newFieldWithTwo()
        } else {
            newFieldWithTwo.text = "2"
            newFieldWithTwo.setBackgroundResource (R.drawable.button_2)
            emptyFields--
            Log.d("___", "empty fields: " + emptyFields)
        }
        return newFieldWithTwo
    }

    fun newField(oldValue: Int): Button? {
        val newButton = Button(this)
        val newValue = oldValue * 2
        emptyFields++
        when(newValue) {
            4 -> {
                newButton.text = "4"
                newButton.setBackgroundResource(R.drawable.button_4)
                score += 4
                gameScoreTextView.text = getString(R.string.game_score, score)
                return newButton
            }
            8 -> {
                newButton.text = "8"
                newButton.setBackgroundResource(R.drawable.button_8)
                score += 8
                gameScoreTextView.text = getString(R.string.game_score, score)
                return newButton
            }
            16 -> {
                newButton.text = "16"
                newButton.setBackgroundResource(R.drawable.button_16)
                score += 16
                gameScoreTextView.text = getString(R.string.game_score, score)
                return newButton
            }
            32 -> {
                newButton.text = "32"
                newButton.setBackgroundResource(R.drawable.button_32)
                score += 32
                gameScoreTextView.text = getString(R.string.game_score, score)
                return newButton
            }
            64 -> {
                newButton.text = "64"
                newButton.setBackgroundResource(R.drawable.button_64)
                score += 64
                gameScoreTextView.text = getString(R.string.game_score, score)
                return newButton
            }
            128 -> {
                newButton.text = "128"
                newButton.setBackgroundResource(R.drawable.button_128)
                score += 128
                gameScoreTextView.text = getString(R.string.game_score, score)
                return newButton
            }
            256 -> {
                newButton.text = "256"
                newButton.setBackgroundResource(R.drawable.button_256)
                score += 256
                gameScoreTextView.text = getString(R.string.game_score, score)
                return newButton
            }
            512 -> {
                newButton.text = "512"
                newButton.setBackgroundResource(R.drawable.button_512)
                score += 512
                gameScoreTextView.text = getString(R.string.game_score, score)
                return newButton
            }
            1024 -> {
                newButton.text = "1024"
                newButton.setBackgroundResource(R.drawable.button_1024)
                score += 1024
                gameScoreTextView.text = getString(R.string.game_score, score)
                return newButton
            }
            2048 -> {
                newButton.text = "2048"
                newButton.setBackgroundResource(R.drawable.button_2048)
                score += 2048
                gameScoreTextView.text = getString(R.string.game_score, score)
                gameOver(true)
                return newButton
            }
            else -> {
                emptyFields--
                Log.d("___", "error!! newValue: " + newValue)
            }
        }
        return null
    }


    fun gameStart() {
        emptyFields = 16
        score = 0
        swipeViewHolder.visibility = View.VISIBLE
        resultTextView.visibility = View.INVISIBLE

        setInitialScore()

        for (row in gameField) {
            for (col in row) {
                col.text = ""
                col.setBackgroundResource(R.drawable.button_empty)
            }
        }
        newFieldWithTwo()
        newFieldWithTwo()
    }
    fun setInitialScore() {
        val initialScore = getString(R.string.game_score, score)
        gameScoreTextView.text = initialScore
    }
    fun gameOver(result: Boolean) {
        if (result) {
            resultTextView.text = getString(R.string.game_win)
        } else {
            resultTextView.text = getString(R.string.game_lose)
        }
        swipeViewHolder.visibility = View.INVISIBLE
        resultTextView.visibility = View.VISIBLE
    }
}