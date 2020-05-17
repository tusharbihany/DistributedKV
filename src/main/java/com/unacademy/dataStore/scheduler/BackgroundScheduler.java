package com.unacademy.dataStore.scheduler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

public class BackgroundScheduler {

    // Schedule at every 3 mins
    private static final long SCHEDULER_FREQUENCY = 180000;

    /*
       Schedules a callable every SCHEDULER_FREQUENCY milliseconds
     */
    public static void scheduleTTLCleanUp(Timer timer, final Callable<Void> callable) {
        timer.scheduleAtFixedRate(new TimerTask() {

                                      @Override
                                      public void run() {
                                          try {
                                              callable.call();
                                          } catch (Exception e) {
                                              //log the error
                                          }
                                      }
                                  },
                //Delay
                0,
                //Time between each execution
                SCHEDULER_FREQUENCY);
    }
}
