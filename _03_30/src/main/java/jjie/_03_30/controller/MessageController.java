package jjie._03_30.controller;

import jjie._03_30.entity.MessageInfo;
import jjie._03_30.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("publish")
    public boolean publish(@RequestBody MessageInfo messageInfo){
        if(!StringUtils.hasLength(messageInfo.getTo()) ||
            !StringUtils.hasLength(messageInfo.getFrom()) ||
            !StringUtils.hasLength(messageInfo.getMessage())){
            return false;
        }else{
            messageService.serviceInsertMessage(messageInfo);
        }
        return true;
    }

    @RequestMapping("getList")
    public List<MessageInfo> getList(){
        return messageService.serviceGetList();
    }
}
