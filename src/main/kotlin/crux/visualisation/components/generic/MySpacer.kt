@file:Suppress("FunctionName")

package crux.visualisation.components.generic

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MySpacer() {
    Spacer(Modifier.size(16.dp))
}

fun getComponentWidth(
    totalWidth: Dp,
    count: Int
) = (totalWidth - (16.dp * (count + 1))) / count