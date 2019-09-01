package com.xlinclass.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xlinclass.enums.TransactionType;
import com.xlinclass.transaction.Transaction;
import com.xlinclass.transaction.TransactionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <p>Description: </p>
 * <p>blog: http://www.xlinclass.com</p>
 *
 * @author 涛声依旧
 * @date 2019/8/24 16:01
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext context;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接受数据:" + msg.toString());
        JSONObject jsonObject = JSON.parseObject((String) msg);

        String groupId = jsonObject.getString("groupId");
        String command = jsonObject.getString("command");
        String transactionId = jsonObject.getString("transactionId");
        //拿到通知的事物对象
        Transaction transaction = TransactionUtil.getTransactionById(groupId, transactionId);
        if (transaction!=null){
            if (command.equals("rollback")){
                    transaction.setTransactionType(TransactionType.ROLLBACK);
            }else{
                transaction.setTransactionType(TransactionType.COMMIT);
            }
            transaction.getTask().signalTask();
        }
    }

    public synchronized Object call(JSONObject data) throws Exception {
        context.writeAndFlush(data.toJSONString()).channel().newPromise();
        return null;
    }
}
