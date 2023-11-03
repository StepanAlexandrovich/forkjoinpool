import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import thread_pool_executor_example.TestReject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TreeClassThreadPoolExecutor {
    private List<String> lines = new ArrayList<>();
    private List<String> allLines = new ArrayList<>();
    public List<String> start(String startUrl){

        lines.add(startUrl);

        while(lines.size()!=0) {
            ThreadPoolExecutor es = new ThreadPoolExecutor(5,8,1, TimeUnit.MILLISECONDS,
                    //new LinkedBlockingQueue<>(10),
                    new SynchronousQueue<>(),
                    new TestReject()
            );

            CountDownLatch count = new CountDownLatch(lines.size());

            List<String> linesBuffer = lines;
            lines = new ArrayList<>();

            for (String line : linesBuffer) {
                es.submit( new Task(line,count) );
            }

            es.shutdown();

            try {
                count.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return allLines;
    }

    class Task implements Callable{
        private String url;
        private CountDownLatch count;

        public Task(String url, CountDownLatch count) {
            this.url = url;
            this.count = count;
        }

        @Override
        public Object call() throws Exception {
            try {
                Document document = Jsoup.connect(url).get();
                Elements elements = document.select("a");
                for (Element element : elements) {
                    String url = element.attr("href");

                    lines.add(url);
                    allLines.add(url);
                }

                count.countDown();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return null;
        }

    }

}
