package crux.visualisation.domain

import crux.api.TransactionInstant
import crux.api.tx.Transaction

data class TransactionHistoryItem(
    val transaction: Transaction,
    val transactionInstant: TransactionInstant
)