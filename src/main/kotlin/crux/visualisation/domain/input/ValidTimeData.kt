package crux.visualisation.domain.input

import androidx.compose.runtime.MutableState
import crux.visualisation.MutableDerivative
import crux.visualisation.domain.input.ValidTimeData.HasNoTimes
import crux.visualisation.domain.input.ValidTimeData.HasValidTime
import crux.visualisation.domain.input.ValidTimeData.HasValidTime.HasEndValidTime
import crux.visualisation.domain.input.ValidTimeData.HasValidTime.OnlyValidTime
import java.time.LocalTime

sealed class ValidTimeData {
    object HasNoTimes : ValidTimeData()
    sealed class HasValidTime : ValidTimeData() {
        abstract val validTime: LocalTime

        data class OnlyValidTime(override val validTime: LocalTime) : HasValidTime()
        data class HasEndValidTime(override val validTime: LocalTime, val endValidTime: LocalTime) : HasValidTime()
    }
}

fun MutableState<InputDataWithTimes>.toValidTimeData() =
    MutableDerivative(value.validTimeData) { value = value.copy(validTimeData = it) }

fun MutableDerivative<ValidTimeData>.toSetValidTime(): (LocalTime) -> Unit = { validTime ->
    set(
        when (value) {
            HasNoTimes, is OnlyValidTime -> {
                OnlyValidTime(validTime)
            }
            is HasEndValidTime -> {
                HasEndValidTime(validTime, value.endValidTime)
            }
        }
    )
}

fun MutableDerivative<ValidTimeData>.toSetEndValidTime(): (LocalTime) -> Unit = { endValidTime ->
    set(
        when (value) {
            HasNoTimes -> throw IllegalStateException()
            is HasValidTime -> HasEndValidTime(value.validTime, endValidTime)
        }
    )
}