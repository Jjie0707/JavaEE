package jjie.pracpart;

import jjie.pracpart.entity.Demo0;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PracPartApplication {



    public static void main(String[] args) {
        ApplicationContext applicationContext=
                SpringApplication.run(PracPartApplication.class, args);
//        Demo0 demo=applicationContext.getBean(Demo0.class);
//        if(demo.getBook()==null) System.out.println("null");else System.out.println(demo.getBook());
//        if(demo.getBook1()==null) System.out.println("null");else System.out.println(demo.getBook1());
//        if(demo.getBook2()==null) System.out.println("null");else System.out.println(demo.getBook2());
//        System.out.println(demo);
//        Person p1=(Person)applicationContext.getBean("person1", Person.class);
//        Person p2=(Person)applicationContext.getBean("person2", Person.class);
//        System.out.println(p1);
//        System.out.println(p2);
//        Person bean = applicationContext.getBean(Person.class);
//        System.out.println(bean);
    }

}
