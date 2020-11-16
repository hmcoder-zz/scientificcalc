package br.iesb.mobile.scientificcalculator

//import br.iesb.mobile.scientificcalculator.model.CalcVisor
//import br.iesb.mobile.scientificcalculator.model.MainViewModel
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.iesb.mobile.scientificcalculator.constant.*
import br.iesb.mobile.scientificcalculator.model.CalcVisor
import br.iesb.mobile.scientificcalculator.model.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Lista de botões da calculadora
        val listButton = listOf<View>(
                btOne, btTwo, btThree, btFour, btFive, btSix,
                btSeven, btEight, btNine, btZero, btComa, btAddition,
                btSubtraction, btMultiply, btDivision, btEquals,
                btChangeSinal, btInverse, btFactorial, btSquareRoot,
                btPi, btBack, btReset, btSquare, btExponent, btModule,
                btPercent, btLog, btNatLog, btSin, btCos, btTan,
                btInvSin, btInvCos, btInvTan,
        )

        // define o nome dos botões de acordo com as constantes pela função
        for (x in listButton) {
            if(x is TextView){
                x.text = getBtnClicked(x).toString()
            }
        }

        // Setando função de clique para todos os botões
        for (x in listButton) {
            x.click()
        }

//         Setando viewmodel para receber as Strings
//         de cálculo e números
        model.getNumbers().observe(this, Observer<CalcVisor> {
            tvNumbers.text = it.number
            tvCalc.text = it.calc
        })

//        tvNumbers.text = ""
    }

    private fun getBtnClicked(any: Any): Any? {
        return when(any) {
            btOne -> One
            btTwo -> Two
            btThree -> Three
            btFour -> Four
            btFive -> Five
            btSix -> Six
            btSeven -> Seven
            btEight -> Eight
            btNine -> Nine
            btZero -> Zero
            btComa -> Coma
            btAddition -> Addition
            btSubtraction -> Subtraction
            btMultiply -> Multiply
            btDivision -> Division
            btEquals -> Equals
            btChangeSinal -> ChangeSinal
            btInverse -> Inverse
            btFactorial -> Factorial
            btSquareRoot -> SquareRoot
            btPi -> Pi
            btBack -> Back
            btReset -> Reset
            btSquare -> Square
            btExponent -> Exponent
            btModule -> Module
            btPercent -> Percent
            btLog -> Log
            btNatLog -> Ln
            btSin -> Sin
            btCos -> Cos
            btTan -> Tan
            btInvSin -> aSin
            btInvCos -> aCos
            btInvTan -> aTan
            else -> null
        }
    }

    // define comportamento onClickListener (atualiza visor)
    private fun View.click() {
        this.setOnClickListener {
            send(it)
        }
    }

    // atualiza o painel;
    private fun send(v: View) {
        // 'c' recebe o valor da tecla clicada
        val c = getBtnClicked(v)
        if (c != null) {
            model.setClick(c)
        }
    }
}