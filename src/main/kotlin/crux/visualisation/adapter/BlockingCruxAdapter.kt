package crux.visualisation.adapter

import crux.api.ICruxAPI
import crux.api.TransactionInstant
import crux.api.tx.Transaction
import java.time.Duration

class BlockingCruxAdapter(
    private val delegate: ICruxAPI,
    private val timeout: Duration
): ICruxAPI by delegate {
    override fun submitTx(transaction: Transaction): TransactionInstant =
        delegate
            .submitTx(transaction)
            .also { delegate.awaitTx(it, timeout)}
}

fun ICruxAPI.blocking(timeout: Duration) = BlockingCruxAdapter(this, timeout)