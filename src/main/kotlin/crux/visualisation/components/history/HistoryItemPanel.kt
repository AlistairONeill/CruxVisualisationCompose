package crux.visualisation.components.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import crux.visualisation.components.generic.MySpacer
import crux.visualisation.domain.history.HistoryItem
import crux.visualisation.domain.input.InputData.Link.DeleteLink
import crux.visualisation.domain.input.InputData.Link.PutLink
import crux.visualisation.domain.input.InputData.None.DeleteSimpleColor
import crux.visualisation.domain.input.ValidTimeData.HasNoTimes
import crux.visualisation.domain.input.ValidTimeData.HasValidTime.HasEndValidTime
import crux.visualisation.domain.input.ValidTimeData.HasValidTime.OnlyValidTime
import crux.visualisation.domain.input.operation
import crux.visualisation.domain.renderColor
import androidx.compose.ui.graphics.Color
import crux.visualisation.domain.input.InputData.Color.*
import crux.visualisation.exhaustive
import java.time.LocalTime

@Suppress("FunctionName")
@Composable
fun HistoryItemPanel(
    width: Dp,
    historyItem: HistoryItem
) {
    Card(
        modifier = Modifier.width(width).wrapContentHeight().padding(16.dp),
        elevation = 8.dp
    ) {
        Row(
            verticalAlignment = CenterVertically
        ) {
            Column(
                Modifier.fillMaxHeight().width(100.dp),
                horizontalAlignment = CenterHorizontally
            ) {
                when (val inputData = historyItem.inputData) {
                    is PutSimpleColor -> {
                        Icon(
                            Icons.Filled.AddCircle,
                            null,
                            modifier = Modifier.size(72.dp),
                            tint = inputData.color.renderColor.let(::Color)
                        )
                    }
                    DeleteSimpleColor -> {
                        Icon(
                            Icons.Default.Delete,
                            null,
                            modifier = Modifier.size(72.dp)
                        )
                    }
                    is PutColor -> Icon(
                        Icons.Filled.AddCircle,
                        null,
                        modifier = Modifier.size(72.dp),
                        tint = inputData.color.renderColor.let(::Color)
                    )
                    is DeleteColor -> Icon(
                        Icons.Default.Delete,
                        null,
                        modifier = Modifier.size(72.dp),
                        tint = inputData.color.renderColor.let(::Color)
                    )
                    is PutLink ->
                        Icon(
                            Icons.Filled.AddCircle,
                            null,
                            modifier = Modifier.size(72.dp)
                        )
                    is DeleteLink ->
                        Icon(
                            Icons.Default.Delete,
                            null,
                            modifier = Modifier.size(72.dp)
                        )
                }.exhaustive
            }

            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = SpaceEvenly
            ) {
                MySpacer()
                Text(historyItem.inputData.operation.name)
                MySpacer()
                Row {
                    Column {
                        Text("Valid Time")
                        Text("End Valid Time")
                        Text("Transaction Time")
                    }
                    MySpacer()
                    Column {
                        Text(historyItem.validTime())
                        Text(historyItem.endValidTime())
                        Text(historyItem.transactionTime())
                    }
                }
                MySpacer()
            }
        }
    }
}


private fun HistoryItem.validTime() =
    validTimeData.run {
        when (this) {
            HasNoTimes -> null
            is OnlyValidTime -> validTime
            is HasEndValidTime -> validTime
        }.render()
    }

private fun HistoryItem.endValidTime() =
    validTimeData.run {
        when (this) {
            HasNoTimes, is OnlyValidTime -> null
            is HasEndValidTime -> endValidTime
        }.render()
    }

private fun HistoryItem.transactionTime() = transactionTime.render()

private fun LocalTime?.render(): String = this?.toString() ?: " - "