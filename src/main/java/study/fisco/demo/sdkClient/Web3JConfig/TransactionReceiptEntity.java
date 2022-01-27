package study.fisco.demo.sdkClient.Web3JConfig;

import org.fisco.bcos.web3j.tx.txdecode.EventResultEntity;
import org.fisco.bcos.web3j.tx.txdecode.InputAndOutputResult;

import java.util.List;
import java.util.Map;

public class TransactionReceiptEntity {

    private InputAndOutputResult input;
    private InputAndOutputResult out;
    private Map<String,List<List<EventResultEntity>>> logs;

    public InputAndOutputResult getInput() {
        return input;
    }

    public void setInput(InputAndOutputResult input) {
        this.input = input;
    }

    public InputAndOutputResult getOut() {
        return out;
    }

    public void setOut(InputAndOutputResult out) {
        this.out = out;
    }

    public Map<String, List<List<EventResultEntity>>> getLogs() {
        return logs;
    }

    public void setLogs(Map<String, List<List<EventResultEntity>>> logs) {
        this.logs = logs;
    }
}
