package com.example.calculatormvp.presenter

interface CalculationResult {
    fun addition(number1: Int, number2: Int)
    fun subtraction(number1: Int, number2: Int)
    fun multiplication(number1: Int, number2: Int)
    fun deviation(number1: Int, number2: Int)
}