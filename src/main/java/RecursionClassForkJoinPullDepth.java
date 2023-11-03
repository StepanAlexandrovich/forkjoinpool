import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveAction;

public class RecursionClassForkJoinPullDepth extends RecursiveAction {

    private String startUrl = "https://7745.by/";
//    private String startUrl = "https://stepanalexandrovich.github.io/";

    private String pageUrl;
    private int depth;
    public static volatile Set<String> setUrls = new HashSet<>();
    public static volatile List<String> urls = new ArrayList<>();
    public RecursionClassForkJoinPullDepth(String pageUrl, int depth) {
        this.pageUrl = pageUrl;
        this.depth = depth;
    }

    @Override
    protected void compute() {
        List<RecursionClassForkJoinPullDepth> tasks = new ArrayList<>();

        System.out.println("--------------");

        try {
            Document document = Jsoup.connect(pageUrl).get();
            Elements elements = document.select("a");
            for (Element element : elements) {
                String newUrl = element.attr("href");

//                if(newUrl.startsWith("h") && depth > 0) {
//                    if(setUrls.add(newUrl)){
//                        urls.add(newUrl);
//                        tasks.add( new RecursionClassForkJoinPull(newUrl, depth - 1) );
//                        System.out.println(newUrl);
//                    }
//                }



                if(newUrl.contains(startUrl) && depth > 0) {
                    if(setUrls.add(newUrl)){
                        urls.add(newUrl);
                        tasks.add( new RecursionClassForkJoinPullDepth(newUrl, depth - 1) );
                        System.out.println(newUrl);
                    }
                }

            }

        } catch (IOException e) {
            //throw new RuntimeException(e);
            System.out.println("ошибка");
        }

        invokeAll(tasks);
    }

}
