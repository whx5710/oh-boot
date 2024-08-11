package com.iris.system.base.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import com.iris.framework.common.utils.JsonUtils;
import com.iris.framework.security.user.UserDetail;
import com.iris.system.base.vo.SysMessageVO;
import com.iris.system.base.entity.SysMessageEntity;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * websocket 消息相关处理
 * @author 王小费 whx5710@qq.com
 */
@Component
@ServerEndpoint("/websocket/{userId}")  // 接口路径 ws://localhost:8080/webSocket/10001;
public class WebSocketHandler {

    private final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);

    private static SysMessageService sysMessageService;

    // @Component注解，通过该方法注入service
    @Autowired
    public void setSysMessageService(SysMessageService sysMessageService){
        WebSocketHandler.sysMessageService = sysMessageService;
    }

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    /**
     * 用户ID
     */
    private String userId;

    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    // 虽然@Component默认是单例模式的，但spring boot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
    // 注：底下WebSocketHandler是当前类名
    private static final CopyOnWriteArraySet<WebSocketHandler> webSockets =new CopyOnWriteArraySet<>();
    // 用来存在线连接用户信息
    private static final ConcurrentHashMap<String,Session> sessionPool = new ConcurrentHashMap<String,Session>();

    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value="userId")String userId) {
        try {
            this.session = session;
            this.userId = userId;
            webSockets.add(this);
            sessionPool.put(userId, session);
            // 查询是否有未发送的消息
            List<SysMessageVO> list = sysMessageService.unSendMsg(Long.valueOf(userId));
            if(!ObjectUtils.isEmpty(list)){
                for(SysMessageVO sysMessageVO: list){
                    int i = this.sendMessage(userId, sysMessageVO.toJson());
                    if(i == 1){
                        SysMessageEntity sysMessageEntity = new SysMessageEntity();
                        BeanUtil.copyProperties(sysMessageVO, sysMessageEntity);
                        sysMessageEntity.setState("1");
                        sysMessageService.updateById(sysMessageEntity);
                    }
                }
            }
            // 查询收件箱是否有待阅读消息，有则提示查看
            list = sysMessageService.unReadMsg(Long.valueOf(userId));
            if(!ObjectUtils.isEmpty(list)){
                String msg = "您有多条未读消息，请查看消息中心！";
                if(list.size() < 10){
                    msg = "您有" + list.size() + "条未读消息，请在消息中心查看！";
                }
                SysMessageVO sysMessageVO = new SysMessageVO();
                sysMessageVO.setContent(msg);
                sysMessageVO.setTitle("消息提醒");
                sysMessageVO.setType("success");
                int i = this.sendMessage(userId, sysMessageVO.toJson());
            }
            log.info("【websocket消息】有新的连接，总数为:{}", webSockets.size());
        } catch (Exception e) {
            log.error("websocket打开连接异常！{}", e.getMessage());
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            sessionPool.remove(this.userId);
            log.info("【websocket消息】连接断开，总数为:{}", webSockets.size());
        } catch (Exception e) {
            log.error("断开连接异常！{}", e.getMessage());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        SysMessageVO sysMessageVO = JsonUtils.parseObject(message, SysMessageVO.class);
        Principal principal = session.getUserPrincipal();
        UserDetail user = (UserDetail) ReflectUtil.getFieldValue(principal, "principal");
        if(sysMessageVO.getFromId() == null){
            sysMessageVO.setFromId(user.getId());
        }
        if(sysMessageVO.getFromName() == null){
            sysMessageVO.setFromName(user.getRealName());
        }
        if(ObjectUtils.isEmpty(sysMessageVO.getType())){
            sysMessageVO.setType("success");
        }else if(sysMessageVO.getType().equals("heartBeat")){ // 心跳
            log.debug("【websocket消息】收到客户【{}-{}】端消息:{}",user.getId(), user.getRealName(), message);
            return;
        }
        log.info("【websocket消息】收到客户端【{}-{}】消息:{}",user.getId(), user.getRealName(), message);
        sysMessageVO.setState("0");
        int i = 0; // 0未发生成功1发送成功-1发送异常
        if(!ObjectUtils.isEmpty(sysMessageVO.getToId())){
            i = sendMessage(String.valueOf(sysMessageVO.getToId()), sysMessageVO.toJson());
        }
        if(i == 1){
            sysMessageVO.setState("1"); // 状态0未发送1未读2已读
        }
        sysMessageService.save(sysMessageVO);
    }

    /** 发送错误时的处理
     * @param session s
     * @param error e
     */
    @OnError
    public void onError(Session session, Throwable error) {
//        error.printStackTrace();
        log.error("发生异常！{}", error.getMessage());
    }


    // 此为广播消息
    public void sendAllMessage(String message) {
        log.info("【websocket消息】广播消息:{}", message);
        for(WebSocketHandler webSocket : webSockets) {
            try {
                if(webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                log.error("发送广播消息异常！{}", e);
            }
        }
    }

    /**
     * 此为单点消息
     * @param userId 接收人ID
     * @param message 消息
     * @return 0未发生成功1发送成功-1发送一场
     */
    public int sendMessage(String userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null && session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:{}", message);
                session.getAsyncRemote().sendText(message);
                return 1;
            } catch (Exception e) {
                log.error("发送消息异常！{}", e);
                return -1;
            }
        } else{
            log.warn("还未与客户端建立连接！【{}】", userId);
            return 0;
        }
    }

    // 此为单点消息(多人)
    public void sendMoreMessage(String[] userIds, String message) {
        for(String userId:userIds) {
            Session session = sessionPool.get(userId);
            if (session != null&&session.isOpen()) {
                try {
                    log.info("【websocket消息】 单点消息:{}", message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    log.error("发送消息异常（多人）！", e);
                }
            }
        }
    }

}
