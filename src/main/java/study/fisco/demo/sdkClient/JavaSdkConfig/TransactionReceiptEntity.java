package study.fisco.demo.sdkClient.JavaSdkConfig;

import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;

import java.util.List;
import java.util.Map;

public class TransactionReceiptEntity {

    private TransactionResponse  response;
    private TransactionResponse status;
    private Map<String, List<List<Object>>> logs;


    public TransactionResponse getResponse() {
        return response;
    }

    public void setResponse(TransactionResponse response) {
        this.response = response;
    }

    public TransactionResponse getStatus() {
        return status;
    }

    public void setStatus(TransactionResponse status) {
        this.status = status;
    }

    public Map<String, List<List<Object>>> getLogs() {
        return logs;
    }

    public void setLogs(Map<String, List<List<Object>>> logs) {
        this.logs = logs;
    }
}
