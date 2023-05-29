package dev.leeshuyun.Lifeguild.exceptions;

import dev.leeshuyun.Lifeguild.models.TransactionPetAdoptionDetails;

public class TradePetFailedException extends Exception {
    private TransactionPetAdoptionDetails txnDetails;

    public TradePetFailedException() {
        super();
    }

    public TradePetFailedException(String msg) {
        super(msg);
    }

    public TransactionPetAdoptionDetails getTxnDetails() {
        return txnDetails;
    }

    public void setTxnDetails(TransactionPetAdoptionDetails txnDetails) {
        this.txnDetails = txnDetails;
    }

}
