import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TreeClass {
    private List<String> method(String url){
        List<String> lines = new ArrayList<>();

        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("a");
            for (Element element : elements) {
                lines.add(element.attr("href"));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lines;
    }

    public List<String> start(String startUrl){
        List<String> lines = new ArrayList<>();
        lines.add(startUrl);
        List<String> allLines = new ArrayList<>();

        while(lines.size()!=0) {
            List<String> linesBuffer = lines;
            lines = new ArrayList<>();
            for (String line : linesBuffer) {
                for (String s : method(line)) {
                    lines.add(s);
                    allLines.add(s);
                }
            }
        }

        return allLines;
    }
}
