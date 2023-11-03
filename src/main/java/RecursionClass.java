import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecursionClass {
    private List<String> urls = new ArrayList<>();
    public List<String> recursion(String url,int depth){
        try {

            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("a");
            for (Element element : elements) {
                String newUrl = element.attr("href");
                if(newUrl.startsWith("h")&&depth>0){
                    urls.add(newUrl);
                    recursion(newUrl,depth - 1);
                }
            }

        } catch (IOException e) {
            System.out.println("ошибка");
        }

        return urls;
    }
}
