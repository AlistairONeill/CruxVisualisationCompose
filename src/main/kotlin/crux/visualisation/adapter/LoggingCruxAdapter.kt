package crux.visualisation.adapter

import crux.api.ICruxAPI
import crux.api.TransactionInstant
import crux.api.tx.Transaction
import crux.visualisation.domain.TransactionHistoryItem

class LoggingCruxAdapter(
    private val delegate: ICruxAPI
) : ICruxAPI by delegate {
    lateinit var lastSubmitted: TransactionHistoryItem

    override fun submitTx(transaction: Transaction): TransactionInstant =
        delegate.submitTx(transaction)
            .also { transactionInstant ->
                lastSubmitted =
                    TransactionHistoryItem(
                        transaction,
                        transactionInstant
                    )
            }
}

fun ICruxAPI.withLog() = LoggingCruxAdapter(this)