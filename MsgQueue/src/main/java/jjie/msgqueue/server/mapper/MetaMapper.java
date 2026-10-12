package jjie.msgqueue.server.mapper;


import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class MetaMapper {

    private final DataSource dataSource;

    public MetaMapper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void deleteDB() {
        File file = new File("./data/msgqueue.db");
        boolean ret = file.delete();
        if (ret) {
            System.out.println("[DataBaseManager] 删除数据库文件成功!");
        } else {
            System.out.println("[DataBaseManager] 删除数据库文件失败!");
        }

    }
    public int init(){
        if(!check()) {
            createTable(dataSource);
            System.out.println("数据库初始化成功!!");
            return 1;
        }else {
            System.out.println("数据库初始化成功!!");
            return 0;
        }
    }

    public boolean check(){
        String name="./data/msgqueue.db";
        File file=new File(name);
        return file.exists();
    }
    public void createTable(DataSource dataSource){
        String createExchangeTable = """
            CREATE TABLE IF NOT EXISTS exchange (
                name TEXT PRIMARY KEY,
                exchangetype INTEGER NOT NULL DEFAULT 0,
                durable INTEGER NOT NULL DEFAULT 0,
                autodelete INTEGER NOT NULL DEFAULT 1,
                arguments TEXT
            )
            """;
        String createMsgQueueTable = """
            CREATE TABLE IF NOT EXISTS msgqueue (
                name TEXT PRIMARY KEY,
                exclusive INTEGER,
                durable INTEGER NOT NULL DEFAULT 0,
                autodelete INTEGER NOT NULL DEFAULT 1,
                arguments TEXT
            )
            """;
        String createBindingTable = """
            CREATE TABLE IF NOT EXISTS binding (
                exchangename TEXT NOT NULL,
                queuename TEXT NOT NULL,
                bindingkey TEXT NOT NULL,
                PRIMARY KEY (exchangename, queuename, bindingkey)
            )
            """;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.addBatch(createExchangeTable);
            statement.addBatch(createMsgQueueTable);
            statement.addBatch(createBindingTable);
            statement.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException("初始化数据库表失败", e);
        }
    }
    
}
