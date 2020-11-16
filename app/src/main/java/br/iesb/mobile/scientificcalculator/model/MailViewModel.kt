package br.iesb.mobile.scientificcalculator.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.iesb.mobile.scientificcalculator.constant.*
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.*

class MainViewModel : ViewModel() {

    private val visor = CalcVisor()
    private var equalPress = false
    private var hasError = false
    private val calcList: MutableList<String> = mutableListOf()

    private val calcVisor: MutableLiveData<CalcVisor> by lazy {
        MutableLiveData<CalcVisor>()
    }

    // Envia a variável calcVisor
    fun getNumbers(): LiveData<CalcVisor> {
        return calcVisor
    }

    // Recebe o click da tela
    fun setClick(c: Any?) {
        if (hasError) {
            if (c == Reset) processClick(Reset)
        } else processClick(c.toString())
    }

    private fun buildString(arrayToConvert: MutableList<String>):String {
        val builder = StringBuilder()
        for (value in calcList) {
            builder.append(value)
        }
        return builder.toString()
    }

    private fun insertNewCalc(newValue: String) {

        if (calcList.size > 9) {
            calcList.removeAt(0)
        }
        calcList.add(newValue)
        visor.number = buildString(calcList)
    }

    // Processa informação do click
    private fun processClick(c: String) {
        if (equalPress == true) { resetCalc() }
        equalPress = false

        when (c) {
            Reset -> resetCalc()

            Back -> {
                if (visor.calc.length > 1) {
                    visor.calc = visor.calc.substring(0, visor.calc.length - 1)
                } else {
                    visor.calc = Zero
                }
            }

            Coma -> {
                if (!visor.calc.contains(Coma)) {
                    if (visor.calc == "") visor.calc = "$Zero." else visor.calc += "$c"
                }
            }

            ChangeSinal -> {
                visor.calc =
                        if (visor.calc.isNotEmpty() && visor.calc.first() == Subtraction.first()) {
                            visor.calc.substring(1, visor.calc.length)
                        } else {
                            "$Subtraction${visor.calc}"
                        }
            }

            Inverse -> {
                visor.calc =
                        if (visor.calc.isNotEmpty()) {
                            "1/(${visor.calc})"
                        } else {
                            "$Zero${visor.calc}"
                        }
            }

            Addition -> setCalc(Addition)
            Subtraction -> setCalc(Subtraction)
            Multiply -> setCalc(Multiply)
            Division -> setCalc(Division)

            Equals -> {
                //visor.calc += visor.number
                makeCalc(visor.calc)
            }

            Factorial -> {
                if (visor.calc != "") {
                    visor.calc = factorial(visor.calc)
                } else {
                    visor.calc = ""
                }
            }

            Pi -> {
                if (visor.calc == "") {
                    visor.calc = PI.toString()
                } else {
                    visor.calc = ""
                }
            }

            SquareRoot -> {
                val num: Float
                if (visor.calc != "") {
                    num = visor.calc.toFloat()
                    visor.calc = sqrt(num).toString()
                } else {
                    visor.calc = ""
                }
            }

            Square -> {
                val num: Double
                if (visor.calc != "") {
                    num = visor.calc.toDouble()
                    visor.calc = num.pow(2).toString()
                } else {
                    visor.calc = ""
                }
            }

            Module -> {
                val num: Float
                if (visor.calc != "") {
                    num = visor.calc.toFloat()
                    visor.calc = abs(num).toString()
                } else {
                    visor.calc = ""
                }
            }

            Sin -> {
                val num: Float
                if (visor.calc != "") {
                    num = ((visor.calc.toFloat())* (PI/180)).toFloat()
                    visor.calc = sin(num).toString()
                } else {
                    visor.calc = ""
                }
            }

            Cos -> {
                val num: Float
                if (visor.calc != "") {
                    num = ((visor.calc.toFloat())* (PI/180)).toFloat()
                    visor.calc = cos(num).toString()
                } else {
                    visor.calc = ""
                }
            }

            Tan -> {
                val num: Float
                if (visor.calc != "") {
                    num = ((visor.calc.toFloat())* (PI/180)).toFloat()
                    visor.calc = tan(num).toString()
                } else {
                    visor.calc = ""
                }
            }

            aSin -> {
                val num: Float
                if (visor.calc != "") {
                    num = visor.calc.toFloat()
                    visor.calc = asin(num).toString()
                } else {
                    visor.calc = ""
                }
            }

            aCos -> {
                val num: Float
                if (visor.calc != "") {
                    num = visor.calc.toFloat()
                    visor.calc = acos(num).toString()
                } else {
                    visor.calc = ""
                }
            }

            aTan -> {
                val num: Float
                if (visor.calc != "") {
                    num = visor.calc.toFloat()
                    visor.calc = atan(num).toString()
                } else {
                    visor.calc = ""
                }
            }

            Ln -> {
                val num: Float
                if (visor.calc != "") {
                    num = visor.calc.toFloat()
                    visor.calc = ln(num).toString()
                } else {
                    visor.calc = ""
                }
            }

            Exponent -> setCalc(Exponent)
            Percent -> setCalc(Percent)
            Log -> setCalc(Log)

            else -> {
                try {
                    c.toInt()
                    when(visor.number){
                        Zero -> visor.number = ""
                        "$Subtraction" -> visor.number = "$Subtraction"
                    }
                    visor.calc += "$c"
                } catch (e: Exception) {
                    hasError = true
                    visor.number = ERROR
                }
            }
        }
        calcVisor.value = visor
    }

    // Função AC da calculadora
    private fun resetCalc() {
        hasError = false
        equalPress = false
        visor.calc = ""
    }

    fun factorial(calcValue:String):String {

        val num = calcValue.toInt()
        var factorial: Long = 1
        for (i in 1..num) {
            factorial *= i.toLong()
        }
        return factorial.toString()
    }

    // Adiciona o cálculo a ser feito no visor.calc
    private fun setCalc(str: String) {
        if (equalPress) {
            visor.calc = ""
            equalPress = false
        }
        if (visor.calc == "") { visor.calc = "0" }
        visor.calc = "${visor.calc}${str}"
        //visor.number = Zero
    }

    // Mostra o resultado apertando " = "
    private fun makeCalc(str: String) {
        var result: String
        try {
            val replaceStr = str.replace(Addition, "+")
                    .replace(Subtraction, "-")
                    .replace(Division, "/")
                    .replace(Multiply, "*")
                    .replace(Factorial, "!")

            //visor.number += (visor.calc + Equals) + "\n"
            if  (Exponent in str) {
                val strExponent = replaceStr.split("xʸ").toTypedArray()
                result = strExponent.first().toDouble().pow(strExponent.last().toDouble()).toString()
            } else if (Percent in str) {
                val strPercent = replaceStr.split("%").toTypedArray()
                result = ((strPercent.first().toFloat()/100)*strPercent.last().toFloat()).toString()
            } else if (Log in str) {
                val strLog = replaceStr.split("Log").toTypedArray()
                result = log(strLog.first().toFloat(), strLog.last().toFloat()).toString()
            } else {
                val calc = ExpressionBuilder(replaceStr).build()
                result = calc.evaluate().toString()
            }
            val newCalc = (visor.calc + Equals + result) + "\n"
            insertNewCalc(newCalc)
            equalPress = true
        }
        catch (e: Exception) {
            hasError = true
            result = ERROR
        }
    }
}