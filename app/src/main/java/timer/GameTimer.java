package timer;

import android.os.Handler;

/**
 * Created by Daniel_m on 10/12/2016.
 */

public class GameTimer extends Thread implements Runnable {

    private Handler handler;
    private boolean active;

    public GameTimer(Handler handler){
        this.handler = handler;
        this.active = true;
    }
    @Override
    public void run() {
        while(active){
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopTimer(){
        this.active = false;
    }

}
