/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PV112;

/**
 *
 * @author MiškoHu
 */
public class AnimationThread implements Runnable {

    private float teapotY = 0;

    @Override
    public void run() {
        while (teapotY < Math.PI * 4) {
            teapotY += 0.01f;
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                return;
            }
        }
    }

    public double getTeapotY() {
        return Math.sin(teapotY);
    }
}

