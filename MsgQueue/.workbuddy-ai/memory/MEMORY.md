# 项目长期记忆 — MsgQueue

## 协作方式（欧阳明确要求，2026-11-14）

**不要直接改他的源码。** 只做两件事：指出错在哪、教他怎么改（给代码片段 + 为什么）。
原因：这是他的学习项目，改了他就学不到了。要动手前先问，得到明确同意才改。

## 技术约定

- Spring Boot 4 + Jackson 3（`tools.jackson`）+ MyBatis-Plus 3.5.15 + SQLite。
- MyBatis-Plus 里凡是"纯内存态"字段，必须标 `@TableField(exist = false)`，否则会被拼进 SQL 列。
- 消息 id 由 `BasicProperties` 的字段初始化器生成，**共用对象 = 共用 id**，多队列副本必须各自独立。
- 测试工程放在项目外（`/tmp/mq-verify2`），靠工作目录让硬编码的 `./data` 落到 /tmp，项目目录零污染。
- 工具链：`JAVA_HOME=$(/usr/libexec/java_home -v 24)` + `/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3/bin/mvn`（系统无 mvn）。
