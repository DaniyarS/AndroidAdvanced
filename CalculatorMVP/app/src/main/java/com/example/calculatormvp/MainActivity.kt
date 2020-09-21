package com.example.calculatormvp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.calculatormvp.presenter.CalculationResult
import com.example.calculatormvp.presenter.CalculationResultPresenter
import com.example.calculatormvp.view.CalculationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CalculationView, View.OnClickListener {

    private lateinit var calculationResultPresenter: CalculationResultPresenter
    var operationType = ""

    var number1 = 0
    private var number2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calculationResultPresenter = CalculationResultPresenter(this)

        bt1.setOnClickListener(this)
        bt2.setOnClickListener(this)
        bt3.setOnClickListener(this)
        bt4.setOnClickListener(this)
        bt5.setOnClickListener(this)
        bt6.setOnClickListener(this)
        bt7.setOnClickListener(this)
        bt8.setOnClickListener(this)
        bt9.setOnClickListener(this)
        bt0.setOnClickListener(this)

        btPlus.setOnClickListener(this)
        btMinus.setOnClickListener(this)
        btDivision.setOnClickListener(this)
        btMultiplication.setOnClickListener(this)
        btEqual.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        val value = tvMainText.text.toString()


        when (view?.id) {
            R.id.bt0 -> {
                if (value == "0") {
                    tvMainText.text = "0"
                } else {
                    tvMainText.append("0")
                }
                tvSecondaryText.text = ""
            }
            R.id.bt1 -> {
                if (value == "0") {
                    tvMainText.text = "1"
                } else {
                    tvMainText.append("1")
                }
                tvSecondaryText.text = ""
            }
            R.id.bt2 -> {
                if (value == "0") {
                    tvMainText.text = "2"
                } else {
                    tvMainText.append("2")
                }
                tvSecondaryText.text = ""
            }
            R.id.bt3 -> {
                if (value == "0") {
                    tvMainText.text = "3"
                } else {
                    tvMainText.append("3")
                }
                tvSecondaryText.text = ""
            }
            R.id.bt4 -> {
                if (value == "0") {
                    tvMainText.text = "4"
                } else {
                    tvMainText.append("4")
                }
                tvSecondaryText.text = ""
            }
            R.id.bt5 -> {
                if (value == "0") {
                    tvMainText.text = "5"
                } else {
                    tvMainText.append("5")
                }
                tvSecondaryText.text = ""
            }
            R.id.bt6 -> {
                if (value == "0") {
                    tvMainText.text = "7"
                } else {
                    tvMainText.append("7")
                }
                tvSecondaryText.text = ""
            }
            R.id.bt7 -> {
                if (value == "0") {
                    tvMainText.text = "7"
                } else {
                    tvMainText.append("7")
                }
                tvSecondaryText.text = ""
            }
            R.id.bt8 -> {
                if (value == "0") {
                    tvMainText.text = "8"
                } else {
                    tvMainText.append("8")
                }
                tvSecondaryText.text = ""
            }
            R.id.bt9 -> {
                if (value == "0") {
                    tvMainText.text = "9"
                } else {
                    tvMainText.append("9")
                }
                tvSecondaryText.text = ""
            }
            R.id.btPlus -> {
                if (value != "") {
                    number1 = tvMainText.text.toString().toInt()
                    tvMainText.text = ""
                    operationType = "+"
                }
            }
            R.id.btMinus -> {
                if (value != "") {
                    number1 = tvMainText.text.toString().toInt()
                    tvMainText.text = ""
                    operationType = "-"
                }
            }
            R.id.btMultiplication -> {
                if (value != "") {
                    number1 = tvMainText.text.toString().toInt()
                    tvMainText.text = ""
                    operationType = "+"
                }
            }
            R.id.btDivision -> {
                if (value != "") {
                    number1 = tvMainText.text.toString().toInt()
                    tvMainText.text = ""
                    operationType = "/"
                }
            }
            R.id.btEqual -> {
                when(operationType) {
                    "+" -> {
                        number2 = tvMainText.text.toString().toInt()
                        calculationResultPresenter.addition(number1, number2)
                        tvMainText.text = ""
                    }
                    "-" -> {
                        number2 = tvMainText.text.toString().toInt()
                        calculationResultPresenter.subtraction(number1, number2)
                        tvMainText.text = ""
                    }
                    "*" -> {
                        number2 = tvMainText.text.toString().toInt()
                        calculationResultPresenter.multiplication(number1, number2)
                        tvMainText.text = ""
                    }
                    "/" -> {
                        number2 = tvMainText.text.toString().toInt()
                        calculationResultPresenter.deviation(number1, number2)
                        tvMainText.text = ""
                    }
                }
            }
        }
    }

    override fun showResult(result: Int) {
        tvSecondaryText.text = result.toString()
        tvMainText.text = ""
    }
}