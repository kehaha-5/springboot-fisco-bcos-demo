package study.fisco.demo.contract.web3Sdk;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

public class AssetContract {

    private static final String abi;
    private static final String bin;

    static {
        try{
            abi = IOUtils.toString(Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("web3j/solidity/Asset/Asset.abi"));
            bin = IOUtils.toString(Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("web3j/solidity/Asset/Asset.bin"));
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String getAbi() {
        return abi;
    }

    public static String getBin() {
        return bin;
    }
}
