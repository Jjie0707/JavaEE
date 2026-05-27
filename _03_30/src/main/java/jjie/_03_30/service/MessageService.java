package jjie._03_30.service;

import jjie._03_30.entity.MessageInfo;
import jjie._03_30.mapper.MessageInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageInfoMapper messageInfoMapper;

    public Integer serviceInsertMessage(MessageInfo messageInfo){
        return messageInfoMapper.insertMessage(messageInfo);
    }

    public List<MessageInfo> serviceGetList(){
        return messageInfoMapper.getList();
    }
}
