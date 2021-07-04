package crux.visualisation.domain.history

import crux.api.TransactionInstant
import crux.api.tx.Transaction

data class TransactionHistoryItem(
    val transaction: Transaction,
    val transactionInstant: TransactionInstant
)