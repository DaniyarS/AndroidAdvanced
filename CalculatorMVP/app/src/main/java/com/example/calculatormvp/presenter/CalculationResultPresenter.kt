package com.example.calculatormvp.presenter

import com.example.calculatormvp.view.CalculationView

class CalculationResultPresenter (private var view: CalculationView) : CalculationResult {
    override fun addition(number1: Int, number2: Int) {
        view.showResult(number1 + number2)
    }

    override fun subtraction(number1: Int, number2: Int) {
        view.showResult(number1 - number2)
    }

    override fun multiplication(number1: Int, number2: Int) {
        view.showResult(number1 * number2)
    }

    override fun deviation(number1: Int, number2: Int) {
        view.showResult(number1 / number2)
    }
}