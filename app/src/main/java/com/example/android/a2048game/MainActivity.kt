package com.example.android.a2048game

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var gameScoreTextView : TextView
    private lateinit var swipeViewHolder : FrameLayout
    private lateinit var resetButton : Button
    private lateinit var resultTextView : TextView
    private lateinit var recordTextView : TextView
    private lateinit var startGameButton : Button

    private var emptyFields : Int = 16
    private var score : Int = 0

    private lateinit var gameField : Array<Array<Button>>
    private lateinit var gameFieldArray : Array<IntArray>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeViewHolder = findViewById(R.id.transparent_view)
        resultTextView = findViewById(R.id.result_tv)
        gameScoreTextView = findViewById(R.id.game_score_tv)
        recordTextView = findViewById(R.id.record_tv)

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
                        if(gameFieldArray[row][col] != 0) {
                            if (lastFoundFieldIndex == 4) {
                                lastFoundFieldIndex--
                                if (row != 3) {
                                    switchFieldsInColumn(col, row, lastFoundFieldIndex)
                                }
                            } else {
                                if (gameFieldArray[row][col] == gameFieldArray[lastFoundFieldIndex][col]) {
                                    if (!foundPair) {
                                        foundPair = true
                                        gameFieldArray[lastFoundFieldIndex][col] = gameFieldArray[lastFoundFieldIndex][col] * 2
                                        newField(gameFieldArray[lastFoundFieldIndex][col])
                                        gameFieldArray[row][col] = 0
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
                refreshField()
            }

            override fun onSwipeUp() {
                super.onSwipeUp()

                for (col in 0..3){
                    var lastFoundFieldIndex : Int = -1
                    var foundPair : Boolean = false
                    for (row in 0..3) {
                        if(gameFieldArray[row][col] != 0) {
                            if (lastFoundFieldIndex == -1) {
                                lastFoundFieldIndex++
                                if (row != 0) {
                                    switchFieldsInColumn(col, row, lastFoundFieldIndex)
                                }
                            } else {
                                if (gameFieldArray[row][col] == gameFieldArray[lastFoundFieldIndex][col]) {
                                    if (!foundPair) {
                                        foundPair = true
                                        gameFieldArray[lastFoundFieldIndex][col] = gameFieldArray[lastFoundFieldIndex][col] * 2
                                        newField(gameFieldArray[lastFoundFieldIndex][col])
                                        gameFieldArray[row][col] = 0
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
                refreshField()
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()

                for (row in gameFieldArray){
                    var lastFoundFieldIndex : Int = -1
                    var foundPair : Boolean = false
                    for (i in 0..3) {
                        if(row[i] != 0) {
                            if (lastFoundFieldIndex == -1) {
                                lastFoundFieldIndex++
                                if (i != 0) {
                                    switchFieldsInRow(gameFieldArray.indexOf(row), i, lastFoundFieldIndex)
                                }
                            } else {
                                if (row[i] == row[lastFoundFieldIndex]) {
                                    if (!foundPair) {
                                        foundPair = true
                                        row[lastFoundFieldIndex] = row[lastFoundFieldIndex] * 2
                                        newField(row[lastFoundFieldIndex])
                                        row[i] = 0
                                    } else {
                                        foundPair = false
                                        lastFoundFieldIndex++
                                        switchFieldsInRow(gameFieldArray.indexOf(row), i, lastFoundFieldIndex)
                                    }
                                } else if(i - lastFoundFieldIndex > 1) {
                                    lastFoundFieldIndex++
                                    switchFieldsInRow(gameFieldArray.indexOf(row), i, lastFoundFieldIndex)
                                } else {
                                    lastFoundFieldIndex++
                                }
                            }
                        }
                    }
                }
                newFieldWithTwo()
                refreshField()
            }

            override fun onSwipeRight() {
                super.onSwipeRight()

                for (row in gameFieldArray){
                    var lastFoundFieldIndex : Int = 4
                    var foundPair : Boolean = false
                    for (i in 3 downTo 0) {
                        if(row[i] != 0) {
                            if (lastFoundFieldIndex == 4) {
                                lastFoundFieldIndex--
                                if (i != 3) {
                                    switchFieldsInRow(gameFieldArray.indexOf(row), i, lastFoundFieldIndex)
                                }
                            } else {
                                if (row[i] == row[lastFoundFieldIndex]) {
                                    if (!foundPair) {
                                        foundPair = true
                                        row[lastFoundFieldIndex] = row[lastFoundFieldIndex] * 2
                                        newField(row[lastFoundFieldIndex])
                                        row[i] = 0
                                    } else {
                                        foundPair = false
                                        lastFoundFieldIndex--
                                        switchFieldsInRow(gameFieldArray.indexOf(row), i, lastFoundFieldIndex)
                                    }
                                } else if(lastFoundFieldIndex - i > 1) {
                                    lastFoundFieldIndex--
                                    switchFieldsInRow(gameFieldArray.indexOf(row), i, lastFoundFieldIndex)
                                } else {
                                    lastFoundFieldIndex--
                                }
                            }
                        }
                    }
                }
                newFieldWithTwo()
                refreshField()
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
        gameFieldArray[row][toField] = gameFieldArray[row][fromField]
        gameFieldArray[row][fromField] = 0
    }

    fun switchFieldsInColumn(col: Int, fromField: Int, toField: Int) {
        gameFieldArray[toField][col] = gameFieldArray[fromField][col]
        gameFieldArray[fromField][col] = 0
    }

    fun randomIndex() : Int = Random.nextInt(0, 4)

    fun newFieldWithTwo() {
        if(emptyFields <= 0) {
            gameOver(false)
            return
        }
        val randomRow = randomIndex()
        val randomCol = randomIndex()
        val randomField = gameFieldArray[randomRow][randomCol]
        if(randomField != 0) {
            newFieldWithTwo()
        } else {
            gameFieldArray[randomRow][randomCol] = 2
            emptyFields--
            gameField[randomRow][randomCol].text = "2"
            gameField[randomRow][randomCol].setBackgroundResource(R.drawable.button_2)
            Log.d("___", "empty fields: " + emptyFields)
        }
    }

    fun newField(newVal: Int) {
        emptyFields++
        score += newVal
        gameScoreTextView.text = getString(R.string.game_score, score)
        if (newVal == 2048) gameOver(true)
    }

    fun refreshField() {
        for (row in gameField) {
            for (col in row) {
                val value = gameFieldArray[gameField.indexOf(row)][row.indexOf(col)]
                when(value) {
                    0 -> {
                        col.text = ""
                        col.setBackgroundResource(R.drawable.button_empty)
                    }
                    2 -> {
                        col.text = "2"
                        col.setBackgroundResource(R.drawable.button_2)
                    }
                    4 -> {
                        col.text = "4"
                        col.setBackgroundResource(R.drawable.button_4)
                    }
                    8 -> {
                        col.text = "8"
                        col.setBackgroundResource(R.drawable.button_8)
                    }
                    16 -> {
                        col.text = "16"
                        col.setBackgroundResource(R.drawable.button_16)
                    }
                    32 -> {
                        col.text = "32"
                        col.setBackgroundResource(R.drawable.button_32)
                    }
                    64 -> {
                        col.text = "64"
                        col.setBackgroundResource(R.drawable.button_64)
                    }
                    128 -> {
                        col.text = "128"
                        col.setBackgroundResource(R.drawable.button_128)
                    }
                    256 -> {
                        col.text = "256"
                        col.setBackgroundResource(R.drawable.button_256)
                    }
                    512 -> {
                        col.text = "512"
                        col.setBackgroundResource(R.drawable.button_512)
                    }
                    1024 -> {
                        col.text = "1024"
                        col.setBackgroundResource(R.drawable.button_1024)
                    }
                    2048 -> {
                        col.text = "2048"
                        col.setBackgroundResource(R.drawable.button_2048)
                    }
                    else -> {
                        Log.d("___", "error!! newValue: " + value)
                    }
                }
            }
        }
    }

    fun gameStart() {
        emptyFields = 16
        score = 0

        gameFieldArray = arrayOf(intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0))

        swipeViewHolder.visibility = View.VISIBLE
        resultTextView.visibility = View.INVISIBLE

        setInitialScore()

        newFieldWithTwo()
        newFieldWithTwo()

        refreshField()
    }

    fun setInitialScore() {
        val initialScore = getString(R.string.game_score, score)
        gameScoreTextView.text = initialScore

        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        val highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), 0)
        recordTextView.text = getString(R.string.record_score, highScore)
    }

    fun gameOver(result: Boolean) {
        if (result) {
            resultTextView.text = getString(R.string.game_win)
        } else {
            resultTextView.text = getString(R.string.game_lose)
        }
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        val currentRecordScore = sharedPref.getInt(getString(R.string.saved_high_score_key), 0)
        if (score > currentRecordScore) {
            with (sharedPref.edit()) {
                putInt(getString(R.string.saved_high_score_key), score)
                commit()
            }
            recordTextView.text = getString(R.string.record_score, score)
        }
        swipeViewHolder.visibility = View.INVISIBLE
        resultTextView.visibility = View.VISIBLE
    }
}