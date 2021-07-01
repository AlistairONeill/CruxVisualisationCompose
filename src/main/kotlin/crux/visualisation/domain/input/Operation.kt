package crux.visualisation.domain.input

import crux.visualisation.MutableDerivative
import crux.visualisation.domain.VisualisationColor.Color1
import crux.visualisation.domain.input.InputData.Color.*
import crux.visualisation.domain.input.InputData.Link.DeleteLink
import crux.visualisation.domain.input.InputData.Link.PutLink
import crux.visualisation.domain.input.InputData.None.DeleteSimpleColor
import crux.visualisation.domain.input.Operation.DELETE
import crux.visualisation.domain.input.Operation.PUT

enum class Operation {
    PUT, DELETE
}

val InputData.operation get() = when (this) {
    is DeleteColor, is DeleteLink, DeleteSimpleColor -> DELETE
    is PutColor, is PutLink, is PutSimpleColor -> PUT
}

fun InputData.changeOperation(operation: Operation) =
    when (operation) {
        PUT -> when (this) {
            is PutSimpleColor, is PutColor, is PutLink -> this
            DeleteSimpleColor -> PutSimpleColor(Color1)
            is DeleteColor -> PutColor(color)
            is DeleteLink -> PutLink(from, to)
        }
        DELETE -> when (this) {
            DeleteSimpleColor, is DeleteColor, is DeleteLink -> this
            is PutSimpleColor -> DeleteSimpleColor
            is PutColor -> DeleteColor(color)
            is PutLink -> DeleteLink(from, to)
        }
    }

fun MutableDerivative<InputData>.toOperation() =
    MutableDerivative(value.operation) { set(value.changeOperation(it)) }
