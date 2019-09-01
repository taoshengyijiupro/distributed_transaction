package com.xlinclass.utils;

import com.xlinclass.transaction.TransactionUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * <p>Description: </p>
 * <p>blog: http://www.xlinclass.com</p>
 *
 * @author 涛声依旧
 * @date 2019/8/24 16:15
 */

@Component
public class HttpUtil {

    private static RestTemplate restTemplate = new RestTemplate();


    public  static Object post(String url){
        HttpHeaders header = new HttpHeaders();
        header.set("groupId", TransactionUtil.getCurrent());
        header.set("transactionCount", String.valueOf(TransactionUtil.getCount()));
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(null, header);
        return  restTemplate.postForObject(url,httpEntity,Object.class);

    }
}
