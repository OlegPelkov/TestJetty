package messages;

public class OperationResponce {

    private OperationStatus operationStatus;
    private String msg;

    public OperationResponce(OperationStatus operationStatus, String msg) {
        this.operationStatus = operationStatus;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "OperationResponce{" +
                "operationStatus=" + operationStatus +
                ", msg='" + msg + '\'' +
                '}';
    }
}
