package jjie.msgqueue.client;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.fail;

final class ClientTestSupport {
    private ClientTestSupport() {}

    static <T> Future<T> start(Callable<T> action) {
        FutureTask<T> task = new FutureTask<>(action);
        Thread.startVirtualThread(task);
        return task;
    }

    static <T> T await(String operation, Future<T> task) throws Exception {
        try {
            return task.get(3, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            return fail(operation + " 在 3 秒内没有返回", e);
        }
    }
}
