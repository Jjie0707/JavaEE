package jjie.pracpart.entity;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@Component
//@ConfigurationProperties(prefix = "demo1")
public class Demo0 {

//    @Value("${demo2.num}")
//    int num;

//    @Autowired
//    public Person person;

    public List<Integer> list;

    @Autowired
    private Book book;

    private Book book1;

    private Book book2;

    @Autowired
    public Demo0(Book book){
        this .book1=book;
    }

    @Autowired
    public void setBook2(Book book){
        this.book2=book;
    }


    @Bean
    public Person person1(){
        Person person=new Person();
        person.setAge(19);
        person.setName("JJ");
        return person;
    }

    @Bean
    public Person person2(){
        Person person=new Person();
        person.setAge(19);
        person.setName("Lu");
        return person;
    }

    @PostConstruct
    public void test(){
//        System.out.println(num);
//        System.out.println(person);
//        for(int num:list) System.out.println(num);
    }
}
