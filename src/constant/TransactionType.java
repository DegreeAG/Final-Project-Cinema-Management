package constant;

public enum TransactionType {
    DEPOSIT ("Nạp tiền tài khoản"),
    WITHDRAW("Rút tiền tài khoản");

    private final String transactionType;

    private String getTransactionType() {
        return transactionType;
    }

    TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }


}
