package study.fisco.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BaseService<T> {

    Logger logger = LoggerFactory.getLogger(BaseService.class);

    protected String contractAddress;

}
