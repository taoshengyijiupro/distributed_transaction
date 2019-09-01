package com.xlinclass.interceptor;

import com.xlinclass.transaction.TransactionUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Description: </p>
 * <p>blog: http://www.xlinclass.com</p>
 *
 * @author 涛声依旧
 * @date 2019/8/24 16:50
 */

@Component
public class RequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String groupId = request.getHeader("groupId");
        String transactionCount = request.getHeader("transactionCount");
        TransactionUtil.setTransactionCount(Integer.valueOf(transactionCount==null?"0":transactionCount));
        TransactionUtil.setCurrent(groupId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
