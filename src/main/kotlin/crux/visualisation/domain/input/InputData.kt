package crux.visualisation.domain.input

import androidx.compose.runtime.MutableState
import crux.visualisation.MutableDerivative
import crux.visualisation.domain.VisualisationColor
import crux.visualisation.domain.input.InputData.Color
import crux.visualisation.domain.input.InputData.Color.*
import crux.visualisation.domain.input.InputData.Link
import crux.visualisation.domain.input.InputData.Link.DeleteLink
import crux.visualisation.domain.input.InputData.Link.PutLink


sealed class InputData {
    sealed class None: InputData() {
        object DeleteSimpleColor: None()
    }
    sealed class Color: InputData() {
        abstract val color: VisualisationColor
        data class PutSimpleColor(override val color: VisualisationColor): Color()
        data class PutColor(override val color: VisualisationColor): Color()
        data class DeleteColor(override val color: VisualisationColor): Color()
    }
    sealed class Link: InputData() {
        abstract val from: VisualisationColor
        abstract val to: VisualisationColor
        data class PutLink(override val from: VisualisationColor, override val to: VisualisationColor): Link()
        data class DeleteLink(override val from: VisualisationColor, override val to: VisualisationColor): Link()
    }
}

fun MutableState<InputDataWithTimes>.toInputData() =
    MutableDerivative(value.inputData) { value = value.copy(inputData = it) }

fun Color.withColor(color: VisualisationColor) =
    when (this) {
        is DeleteColor -> DeleteColor(color)
        is PutColor -> PutColor(color)
        is PutSimpleColor -> PutSimpleColor(color)
    }

fun Link.withFrom(color: VisualisationColor) =
    when (this) {
        is DeleteLink -> copy(from = color)
        is PutLink -> copy(from = color)
    }

fun Link.withTo(color: VisualisationColor) =
    when (this) {
        is DeleteLink -> copy(to = color)
        is PutLink -> copy(to = color)
    }