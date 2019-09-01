package com.xlinclass.service;

import com.xlinclass.annotation.XlTransactional;
import com.xlinclass.mapper.TestMapper;
import com.xlinclass.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Description: </p>
 * <p>blog: http://www.xlinclass.com</p>
 *
 * @author 涛声依旧
 * @date 2019/8/26 14:09
 */

@Service
public class TestService {


    @Autowired
    private TestMapper testMapper;


    @XlTransactional(isStart = true)
    @Transactional
    public void test() {
        testMapper.insertTest("server1");
        HttpUtil.post("http://localhost:8088/server2/test");
        //int i = 1/0;
    }
}
