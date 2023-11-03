import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class RecursionClassForkJoinPull {
    private String startUrl;
    private volatile Set<String> setUrls = new HashSet<>();
    private volatile List<String> urls = new ArrayList<>();
    public RecursionClassForkJoinPull(String startUrl) {
        this.startUrl = startUrl;
    }

    public List<String> start(){
        ForkJoinPool pool = new ForkJoinPool(12);
        pool.invoke(new Recursion(startUrl));
        return urls;
    }

    private class Recursion extends RecursiveAction {
        private String pageUrl;

        public String ref = "http://www.google.com/";

        public Recursion(String pageUrl) {
            this.pageUrl = pageUrl;
        }

        @Override
        protected void compute() {
            List<Recursion> tasks = new ArrayList<>();

            System.out.println("--------------");

            try {
                Document document = Jsoup.connect(pageUrl).timeout(0).referrer(ref).get();
                Elements elements = document.select("a");
                for (Element element : elements) {
                    String newUrl = element.attr("href");



                    if(newUrl.contains("https://")) {
                        if(setUrls.add(newUrl)){
                            urls.add(newUrl);
                            tasks.add( new Recursion(newUrl) );
                            System.out.println(newUrl);
                        }
                    }

//                    if(newUrl.contains(startUrl)) {
//                        if(setUrls.add(newUrl)){
//                            urls.add(newUrl);
//                            tasks.add( new Recursion(newUrl) );
//                            System.out.println(newUrl);
//                        }
//                    }

//                if(newUrl.length()>=startUrl.length() && newUrl.substring(0, startUrl.length()).equals(startUrl)) {
//                    if(setUrls.add(newUrl)){
//                        urls.add(newUrl);
//                        tasks.add( new RecursionClassForkJoinPull(newUrl) );
//                        System.out.println(newUrl);
//                    }
//                }

                }

            } catch (IOException e) {
                //throw new RuntimeException(e);
                System.out.println("ошибка");
            }

            invokeAll(tasks);
        }

    }
}
