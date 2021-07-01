@file:Suppress("FunctionName")

package crux.visualisation.components.history

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.Dp
import crux.visualisation.domain.TemporalData

@Composable
fun HistoryPanel(
    width: Dp,
    temporalData: MutableState<TemporalData>
) {
    Column {
        temporalData
            .value
            .history
            .forEach { historyItem ->
                HistoryItemPanel(
                    width,
                    historyItem
                )
            }
    }
}