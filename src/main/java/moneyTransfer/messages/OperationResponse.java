package moneyTransfer.messages;

public class OperationResponse {

    private OperationStatus operationStatus;
    private String msg;

    public OperationResponse(OperationStatus operationStatus, String msg) {
        this.operationStatus = operationStatus;
        this.msg = msg;
    }

    public OperationStatus getOperationStatus() {
        return operationStatus;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "OperationResponse{" +
                "operationStatus=" + operationStatus +
                ", msg='" + msg + '\'' +
                '}';
    }
}
