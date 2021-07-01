package crux.visualisation.domain.input

import crux.visualisation.MutableDerivative
import crux.visualisation.domain.VisualisationColor.Color1
import crux.visualisation.domain.input.InputData.Color.*
import crux.visualisation.domain.input.InputData.Link.DeleteLink
import crux.visualisation.domain.input.InputData.Link.PutLink
import crux.visualisation.domain.input.InputData.None.DeleteSimpleColor
import crux.visualisation.domain.input.Mode.*

enum class Mode {
    SimpleColor,
    Color,
    Link
}

fun InputData.changeMode(mode: Mode): InputData =
    when (mode) {
        SimpleColor -> when (this) {
            is DeleteColor, is DeleteLink, is DeleteSimpleColor -> DeleteSimpleColor
            is PutSimpleColor -> this
            is PutColor -> PutSimpleColor(color)
            is PutLink -> PutSimpleColor(from)
        }
        Color -> when (this) {
            is DeleteColor -> this
            is PutColor -> this
            is PutSimpleColor -> PutColor(color)
            is DeleteLink -> DeleteColor(from)
            is PutLink -> PutColor(from)
            DeleteSimpleColor -> DeleteColor(Color1)
        }
        Link -> when (this) {
            is DeleteColor -> DeleteLink(Color1, Color1)
            is PutColor -> PutLink(color, Color1)
            is PutSimpleColor -> PutLink(color, Color1)
            is DeleteLink -> this
            is PutLink -> this
            DeleteSimpleColor -> DeleteLink(Color1, Color1)
        }
    }

val InputData.mode get() =
    when (this) {
        is PutSimpleColor, DeleteSimpleColor -> SimpleColor
        is PutColor, is DeleteColor -> Color
        is PutLink, is DeleteLink -> Link
    }

fun MutableDerivative<InputData>.toMode() =
    MutableDerivative(value.mode) { set(value.changeMode(it)) }