import Helpers.StopWatch;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class TestSpring {
    private static StopWatch stopWatch = new StopWatch();

    public static void main(String[] args) {
//        String startUrl = "https://stepanalexandrovich.github.io/page_1/";

//        String startUrl = "https://7745.by/";
        String startUrl = "https://www.onliner.by/";
//        String startUrl = "https://vse-shutochki.ru/kartinki-prikolnye";

        //----------------------
        stopWatch.start();

        //List<String> urls = startTree(startUrl);
        //List<String> urls = startTreeThreadPoolExecutor(startUrl);
        //List<String> urls = startRecursion(startUrl);
        List<String> urls = new RecursionClassForkJoinPull(startUrl).start();

        stopWatch.finish();
        //---------------------------------------

        print(urls,stopWatch.difference());
    }

    private static List<String>  startTree(String startUrl){
        return new TreeClass().start(startUrl);
    }

    private static List<String>  startTreeThreadPoolExecutor(String startUrl){
        return new TreeClassThreadPoolExecutor().start(startUrl);
    }

    private static List<String> startRecursion(String startUrl){
        return new RecursionClass().recursion(startUrl,2);
    }

    private static List<String> startRecursionForkJoinPullDepth(String startUrl){
        ForkJoinPool pool = new ForkJoinPool(12);
        pool.invoke(new RecursionClassForkJoinPullDepth(startUrl,100));
        return RecursionClassForkJoinPullDepth.urls;
    }

    private static void print(List<String> lines,Long time){
        System.out.println("print---------------------");
        for (String s :  lines) {
            System.out.println(s);
        }
        System.out.println("-------------------------");
        System.out.println(lines.size());
        System.out.println("--------------------------");
        System.out.println("differenceTime -> " + time);
    }

}
