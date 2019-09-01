package com.xlinclass.aspect;

import com.xlinclass.connection.XlConnection;
import com.xlinclass.transaction.TransactionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * <p>Description: </p>
 * <p>blog: http://www.xlinclass.com</p>
 *
 * @author 涛声依旧
 * @date 2019/8/24 16:07
 */

@Aspect
@Component
public class ConnectionAspect {

    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Connection arount(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            return new XlConnection((Connection) proceedingJoinPoint.proceed(), TransactionUtil.getCurrentTransaction());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
