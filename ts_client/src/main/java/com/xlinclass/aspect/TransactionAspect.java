package com.xlinclass.aspect;

import com.xlinclass.annotation.XlTransactional;
import com.xlinclass.enums.TransactionType;
import com.xlinclass.transaction.Transaction;
import com.xlinclass.transaction.TransactionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <p>Description: </p>
 * <p>blog: http://www.xlinclass.com</p>
 *
 * @author 涛声依旧
 * @date 2019/8/24 16:33
 */

@Order(10000)
@Aspect
@Component
public class TransactionAspect {

    @Around("@annotation(com.xlinclass.annotation.XlTransactional)")
    public void invoke(ProceedingJoinPoint proceedingJoinPoint){
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        XlTransactional annotation = signature.getMethod().getAnnotation(XlTransactional.class);
        String group = "";
        if (annotation.isStart()) {
            //创建事务组
            group  = TransactionUtil.createGroup();
        }else{
            //拿到当前事务组的ID
            group = TransactionUtil.getCurrent();
        }
        //他肯定存在分布式事物里面


        //创建事物对象
        Transaction transaction = TransactionUtil.createTransaction(group);

        //执行本地逻辑
        try {
            //Spring 会帮我们执行mysql的事物  一直等待
            proceedingJoinPoint.proceed();
            //提交本地事物状态  ---commit
            TransactionUtil.commitTransaction(transaction,annotation.isEnd(), TransactionType.COMMIT);
        } catch (Throwable throwable) {
            // 回滚
            TransactionUtil.commitTransaction(transaction,annotation.isEnd(), TransactionType.ROLLBACK);
            throwable.printStackTrace();
        }

    }
}
