package thread_pool_executor_example;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Map {
    public void start(){

        ThreadPoolExecutor es = new ThreadPoolExecutor(2,4,1, TimeUnit.MILLISECONDS,
                //new LinkedBlockingQueue<>(2),
                new SynchronousQueue<>(),
                new TestReject()
        );



        for (int i = 0; i < 7; i++) {
            TestCallable tc = new TestCallable();
            es.submit(tc);
        }


    }
}
