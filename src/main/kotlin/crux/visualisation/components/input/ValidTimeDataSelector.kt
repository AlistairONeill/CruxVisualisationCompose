@file:Suppress("FunctionName")

package crux.visualisation.components.input

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.Dp
import crux.visualisation.MutableDerivative
import crux.visualisation.components.generic.ButtonStyle
import crux.visualisation.components.generic.MyButton
import crux.visualisation.components.generic.MySpacer
import crux.visualisation.components.generic.RemovableTimePanel
import crux.visualisation.domain.input.ValidTimeData
import crux.visualisation.domain.input.toSetEndValidTime
import crux.visualisation.domain.input.toSetValidTime
import crux.visualisation.exhaustive
import java.time.LocalTime

private val defaultTime = LocalTime.of(10, 0, 0)

@Composable
fun ValidTimeDataPanel(
    width: Dp,
    mutableValidTimeData: MutableDerivative<ValidTimeData>,
    mutableInvalidValidTime: MutableState<Boolean>,
    mutableInvalidEndValidTime: MutableState<Boolean>
) {
    val (invalidValidTime, setInvalidValidTime) = mutableInvalidValidTime
    val (invalidEndValidTime, setInvalidEndValidTime) = mutableInvalidEndValidTime
    val setValidTime = mutableValidTimeData.toSetValidTime()
    val setEndValidTime = mutableValidTimeData.toSetEndValidTime()
    mutableValidTimeData.value.run {
        when (this) {
            ValidTimeData.HasNoTimes -> {
                MyButton(
                    ButtonStyle.Outlined,
                    null,
                    Icons.Default.AddCircle,
                    "Valid Time"
                ) { setValidTime(defaultTime) }
            }
            is ValidTimeData.HasValidTime -> {
                RemovableTimePanel(
                    width,
                    "Valid Time",
                    validTime,
                    !invalidValidTime,
                    createSetValidTime(setValidTime, setInvalidValidTime)
                ) {
                    mutableValidTimeData.set(ValidTimeData.HasNoTimes)
                    setInvalidValidTime(false)
                    setInvalidEndValidTime(false)
                }

                MySpacer()

                when (this) {
                    is ValidTimeData.HasValidTime.OnlyValidTime -> {
                        MyButton(
                            ButtonStyle.Outlined,
                            null,
                            Icons.Default.AddCircle,
                            "End Valid Time"
                        ) { setEndValidTime(defaultTime) }
                    }

                    is ValidTimeData.HasValidTime.HasEndValidTime -> {
                        RemovableTimePanel(
                            width,
                            "End Valid Time",
                            endValidTime,
                            !invalidEndValidTime,
                            createSetEndValidTime(setEndValidTime, setInvalidEndValidTime)
                        ) {
                            mutableValidTimeData.set(ValidTimeData.HasValidTime.OnlyValidTime(validTime))
                            setInvalidEndValidTime(false)
                        }
                    }
                }
            }
        }.exhaustive
    }
}

private fun createSetValidTime(
    setValidTime: (LocalTime) -> Unit,
    setInvalidValidTime: (Boolean) -> Unit
): (LocalTime?) -> Unit = { validTime ->
    if (validTime == null) {
        setInvalidValidTime(true)
    }
    else {
        setValidTime(validTime)
        setInvalidValidTime(false)
    }
}

private fun createSetEndValidTime(
    setEndValidTime: (LocalTime) -> Unit,
    setInvalidEndValidTime: (Boolean) -> Unit
): (LocalTime?) -> Unit = { endValidTime ->
    if (endValidTime == null) {
        setInvalidEndValidTime(true)
    }
    else {
        setEndValidTime(endValidTime)
        setInvalidEndValidTime(false)
    }
}