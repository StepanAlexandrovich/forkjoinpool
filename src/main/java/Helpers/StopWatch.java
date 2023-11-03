package Helpers;

import java.util.Date;

public class StopWatch {
    private Long startTime;
    private Long finishTime;
    private Long currentTime(){
        return new Date().getTime();
    }

    public void start(){ startTime = currentTime(); }

    public void finish(){ finishTime = currentTime(); }

    public Long difference(){
        return finishTime - startTime;
    }
}
