package crux.visualisation.domain.history

import crux.api.TransactionInstant
import crux.api.tx.Transaction
import crux.visualisation.domain.input.InputData
import crux.visualisation.domain.input.ValidTimeData
import java.time.LocalTime

data class HistoryItem(
    val inputData: InputData,
    val validTimeData: ValidTimeData,
    val transactionTime: LocalTime,
    val transaction: Transaction,
    val transactionInstant: TransactionInstant
)