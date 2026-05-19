package jjie._03_30;

import ch.qos.logback.core.util.StringUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("message")
public class MessageController {
    static ArrayList<MessageInfo> list=new ArrayList<>();

    @RequestMapping("publish")
    public boolean publish(@RequestBody MessageInfo messageInfo){
        if(!StringUtils.hasLength(messageInfo.getTo()) ||
            !StringUtils.hasLength(messageInfo.getFrom()) ||
            !StringUtils.hasLength(messageInfo.getMessage())){
            return false;
        }else{
            list.add(messageInfo);
        }
        return true;
    }

    @RequestMapping("getList")
    public ArrayList<MessageInfo> getList(){
        return list;
    }
}
