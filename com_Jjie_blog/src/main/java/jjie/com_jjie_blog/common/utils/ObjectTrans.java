package jjie.com_jjie_blog.common.utils;

import org.springframework.beans.BeanUtils;


public class ObjectTrans {
    public static void blogTrans(Object sou, Object tar){
        if(sou==null || tar==null) return;
        BeanUtils.copyProperties(sou,tar);
    }
    public static void userTrans(Object sou,Object tar){
        if(sou==null || tar==null) return;
        BeanUtils.copyProperties(sou,tar);
    }
}
