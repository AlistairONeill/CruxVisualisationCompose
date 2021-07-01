@file:Suppress("FunctionName")

package crux.visualisation.components.input

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.unit.Dp
import crux.visualisation.components.generic.MySpacer
import crux.visualisation.domain.VisualisationColor.Color1
import crux.visualisation.domain.input.*
import crux.visualisation.domain.input.InputData.Color.PutSimpleColor
import crux.visualisation.domain.input.ValidTimeData.HasNoTimes

@Composable
fun TimedInputPanel(
    width: Dp,
    submit: (InputDataWithTimes) -> Unit
) {
    val mutableInputDataWithTimes =
        remember {
            mutableStateOf(
                InputDataWithTimes(
                    PutSimpleColor(Color1),
                    HasNoTimes
                )
            )
        }

    val mutableInvalidValidTime = remember { mutableStateOf(false) }
    val mutableInvalidEndValidTime = remember { mutableStateOf(false) }

    val mutableInputData = mutableInputDataWithTimes.toInputData()
    val mutableValidTimeData = mutableInputDataWithTimes.toValidTimeData()


    Column(horizontalAlignment = CenterHorizontally) {
        MySpacer()

        OperationSelector(
            width,
            mutableInputData.toOperation()
        )

        MySpacer()

        ModeSelector(
            width,
            mutableInputData.toMode()
        )

        MySpacer()

        InputDataPanel(
            width,
            mutableInputData
        )

        ValidTimeDataPanel(
            width,
            mutableValidTimeData,
            mutableInvalidValidTime,
            mutableInvalidEndValidTime
        )

        MySpacer()

        Button(
            onClick = { submit(mutableInputDataWithTimes.value) },
            enabled = !mutableInvalidValidTime.value && !mutableInvalidEndValidTime.value
        ) {
            Text("Submit")
        }

        MySpacer()
    }
}




