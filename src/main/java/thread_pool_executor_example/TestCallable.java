package thread_pool_executor_example;

import java.util.concurrent.Callable;

public class TestCallable implements Callable<Long> {
    @Override
    public Long call() throws Exception {
        try {
            System.out.println("Thread started" + Thread.currentThread().getId());
            Thread.sleep(1000);
            System.out.println("Thread finished" + Thread.currentThread().getId());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Thread.currentThread().getId();
    }
}
