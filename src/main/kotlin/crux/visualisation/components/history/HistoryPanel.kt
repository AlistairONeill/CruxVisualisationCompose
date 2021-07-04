@file:Suppress("FunctionName")

package crux.visualisation.components.history

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import crux.visualisation.domain.history.HistoryItem

@Composable
fun HistoryPanel(
    width: Dp,
    history: List<HistoryItem>
) {
    Column {
        history.forEach { historyItem ->
            HistoryItemPanel(
                width,
                historyItem
            )
        }
    }
}