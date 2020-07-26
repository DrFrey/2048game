package com.example.android.a2048game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var gameScoreTextView : TextView
    private lateinit var swipeViewHolder : FrameLayout
    private lateinit var resetButton: Button

    private var gameOver : Boolean = false
    private var emptyFields : Int = 16

    private lateinit var gameField : Array<Array<Button>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipeViewHolder = findViewById(R.id.transparent_view)

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
                Toast
                    .makeText(this@MainActivity, "Swiped down", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onSwipeUp() {
                super.onSwipeUp()
                Toast
                    .makeText(this@MainActivity, "Swiped up", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                Toast
                    .makeText(this@MainActivity, "Swiped left", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                Toast
                    .makeText(this@MainActivity, "Swiped right", Toast.LENGTH_SHORT)
                    .show()
                for (row in gameField){
                    for (i in 3 downTo 0) {
                        // если клетка не пустая
                        if(row[i].text != "") {
                            // если это не последний в ряду элемент, сравниваем значения с предыдущим
                            if (i < 3) {
                                //если значения равны, создаем новую клетку с новым значением, а старую обнуляем
                                if (row[i].text == row[i + 1].text) {
                                    var oldValue = row[i].text.toString().toInt()
                                    var newValue = oldValue * 2
                                    var newFieldButton = newField(newValue)
                                    if (newFieldButton != null) {
                                        row[i + 1] = newFieldButton
                                        row[i].text = ""
                                        row[i].setBackgroundResource(R.drawable.button_empty)
                                    } else {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "error in creating new value",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                // если предыдущая клетка пустая, идем вперед, пока не доходим до непустой
                                } else if (row[i + 1].text == "") {

                                }
                            }



                            if(lastEmpty == 3) {
                                row[lastEmpty].text = row[i].text
                                row[lastEmpty].background = row[i].background
                                row[i].text = ""
                                row[i].setBackgroundResource(R.drawable.button_empty)
                                lastEmpty = i
                            // если уже есть заполненные клетки за текущей, сравниваем значения
                            } else if(i < 3 && lastEmpty < 3) {
                                //если значения равны, создае новую клетку с новым значением, а старую обнуляем
                                if (row[i].text == row[lastEmpty + 1].text) {
                                    var oldValue = row[i].text.toString().toInt()
                                    var newValue = oldValue * 2
                                    var newFieldButton = newField(newValue)
                                    if (newFieldButton != null) {
                                        row[lastEmpty + 1] = newFieldButton
                                        row[i].text = ""
                                        row[i].setBackgroundResource(R.drawable.button_empty)
                                        lastEmpty = i
                                    } else {
                                        Toast.makeText(this@MainActivity, "error in creating new value", Toast.LENGTH_LONG).show()
                                    }
                                // если нет, сдвигаем текущую клетку на свободное место
                                } else {
                                    row[lastEmpty].text = row[i].text
                                    row[lastEmpty].background = row[i].background
                                    row[i].text = ""
                                    row[i].setBackgroundResource(R.drawable.button_empty)
                                    lastEmpty = i
                                }
                            }
                        }
                    }

                }
            }
        })
        resetButton = findViewById(R.id.reset_button)
        resetButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                gameStart()
            }
        })

        gameStart()
    }

    fun newField(newValue: Int): Button? {
        val newButton = Button(this)
        when(newValue) {
            4 -> {
                newButton.text = "4"
                newButton.setBackgroundResource(R.drawable.button_4)
                return newButton
            }
            8 -> {
                newButton.text = "8"
                newButton.setBackgroundResource(R.drawable.button_8)
                return newButton
            }
            16 -> {
                newButton.text = "16"
                newButton.setBackgroundResource(R.drawable.button_16)
                return newButton
            }
            32 -> {
                newButton.text = "32"
                newButton.setBackgroundResource(R.drawable.button_32)
                return newButton
            }
            64 -> {
                newButton.text = "64"
                newButton.setBackgroundResource(R.drawable.button_64)
                return newButton
            }
            128 -> {
                newButton.text = "128"
                newButton.setBackgroundResource(R.drawable.button_128)
                return newButton
            }
            256 -> {
                newButton.text = "256"
                newButton.setBackgroundResource(R.drawable.button_256)
                return newButton
            }
            512 -> {
                newButton.text = "512"
                newButton.setBackgroundResource(R.drawable.button_512)
                return newButton
            }
            1024 -> {
                newButton.text = "1024"
                newButton.setBackgroundResource(R.drawable.button_1024)
                return newButton
            }
            2048 -> {
                newButton.text = "2048"
                newButton.setBackgroundResource(R.drawable.button_2048)
                return newButton
            }
            else -> Log.d("___", "error!! newValue: " + newValue)
        }
        return null
    }


    fun gameStart() {
        emptyFields = 16
        gameScoreTextView = findViewById(R.id.game_score_tv)
        val initialScore = getString(R.string.game_score, 0)
        gameScoreTextView.text = initialScore

        for (row in gameField) {
            for (col in row) {
                col.text = ""
                col.setBackgroundResource(R.drawable.button_empty)
            }
        }
        newFieldWithTwo()
        newFieldWithTwo()
    }


    fun randomIndex() : Int = Random.nextInt(0, 4)

    fun newFieldWithTwo() : Button? {
        if(emptyFields == 0) {
            gameOver = true
            return null
        }
        val newField = gameField[randomIndex()][randomIndex()]
        if(newField.text != "") {
            newFieldWithTwo()
        } else {
            newField.text = "2"
            newField.setBackgroundResource (R.drawable.button_2)
            emptyFields -= 1
            Log.d("___", "empty fields: " + emptyFields)
        }
        return newField
    }
}