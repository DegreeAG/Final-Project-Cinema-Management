package constant;

public enum TransactionType {
    DEPOSIT ("Nạp tiền tài khoản"),
    WITHDRAW("Rút tiền tài khoản"),
    PUNISH ("Tiền phạt đổi vé muộn");

    private final String transactionType;

    private String getTransactionType() {
        return transactionType;
    }

    TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }


}
