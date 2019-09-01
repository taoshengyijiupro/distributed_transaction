package com.xlinclass.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: </p>
 * <p>blog: http://www.xlinclass.com</p>
 *
 * @author 涛声依旧
 * @date 2019/8/24 15:26
 */
public class NettyServerHandler  extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(NettyServerHandler.class);

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 事务组中的事务状态列表
     */
    private static Map<String, List<String>> tsTypeMap = new HashMap<>();

    /**
     * 事务组是否已经接受到结束的标记
     */
    private static Map<String,Boolean> isEndMap = new HashMap<>();

    // 事务组中应该有的事务个数
    private static Map<String, Integer> transactionCountMap = new HashMap<String, Integer>();


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channelGroup.add(ctx.channel());
    }

    /**
     * 创建事务组  添加保存事务
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("接受到的数据:" + msg.toString());

        JSONObject jsonObject = JSON.parseObject(msg.toString());

        //create-创建事务组    add-添加事务
        String command = jsonObject.getString("command");
        //事务组ID
        String groupId = jsonObject.getString("groupId");
        //子事务类型   commit：待提交  rollback:待回滚
        String transactionType = jsonObject.getString("transactionType");
       // 事务数量
        Integer transactionCount = jsonObject.getInteger("transactionCount");
        // 是否是结束事务
        Boolean isEnd = jsonObject.getBoolean("isEnd");
        //事物ID
        String transactionId = jsonObject.getString("transactionId");

        if ("create".equals(command)){
            //创建事务组
            tsTypeMap.put(groupId,new ArrayList<>());
        }else if("add".equals(command)){
            //加入事务组
            tsTypeMap.get(groupId).add(transactionType);

            if (isEnd){
                isEndMap.put(groupId,true);
                transactionCountMap.put(groupId, transactionCount);
            }
        }

        JSONObject result = new JSONObject();
        result.put("groupId", groupId);
        result.put("transactionId",transactionId);

        if (isEndMap.get(groupId) !=null && isEndMap.get(groupId) && transactionCountMap.get(groupId).equals(tsTypeMap.get(groupId).size()) ) {
            if (tsTypeMap.get(groupId).contains("ROLLBACK")){
                result.put("command", "rollback");
                sendResult(result);
            } else {
                result.put("command", "commit");
                sendResult(result);
            }
        }
    }

    private void sendResult(JSONObject result) {
        for (Channel channel : channelGroup) {
            log.info("发送数据:" + result.toJSONString());
            channel.writeAndFlush(result.toJSONString());
        }
    }
}
