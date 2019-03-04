//
//  ChaosControl.java
//  ChaosControl
//
//  Created by Carlos Castellanos on 1/18/05.
//  Copyright (c) 2005. All rights reserved.
//
// Introduces "disturbances" into a Lorenz attractor

import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;

public class ChaosControl extends Frame {

    public ChaosControl() {
        setSize(800, 600);
        addWindowListener(new WindowAdapter()
            { public void windowClosing(WindowEvent we)
                { System.exit(0); } });
    }
    
    public void fSystem(double h, double t, double u[], double hf[]) {
        double sigma = 10.0;
        double r = 80.0;
        double b = 0.4;
        double Omega = 6.28318;
        double k = 2.0;
        hf[0] = h*sigma*(u[1]-u[0]);
        hf[1] = h*(-u[0]*u[2] + (r + k*Math.cos(Omega*t)) * u[0] - u[1]);
        hf[2] = h*(u[0]*u[1] - b*u[2]);
    }

    public void map(double u[], int steps, double h, double t, int num) {
        double uk[] = new double[num];
        double tk;
        double a[] = {0.0, 1.0/4.0, 3.0/8.0, 12.0/13.0, 1.0, 1.0/2.0};
        double c[] = {16.0/135.0, 0.0, 6656.0/12825.0, 28561.0/56430.0, -9.0/50.0, 2.0/55.0};
        double b[][] = new double[6][5];
        b[0][0] = b[0][1] = b[0][2] = b[0][3] = b[0][4] = 0.0;
        b[1][0] = 1.0/4.0;
        b[1][1] = b[1][2] = b[1][3] = b[1][4] = 0.0;
        b[2][0] = 3.0/32.0;
        b[2][1] = 9.0/32.0;
        b[2][2] = b[2][3] = b[2][4] = 0.0;
        b[3][0] = 1932.0/2197.0;
        b[3][1] = -7200.0/2197.0;
        b[3][2] = 7296.0/2197.0;
        b[3][3] = b[3][4] = 0.0;
        b[4][0] = 439.0/216.0;
        b[4][1] = -8.0;
        b[4][2] = 3680.0/513.0;
        b[4][3] = -845.0/4104.0;
        b[4][4] = 0.0;
        b[5][0] = -8.0/27.0;
        b[5][1] = 2.0;
        b[5][2] = -3544.0/2565.0;
        b[5][3] = 1859.0/4104.0;
        b[5][4] = -11.0/40.0;
        double f[][] = new double[6][num];
        
        for(int i = 0; i < steps; i++) {
            fSystem(h,t,u,f[0]);
            for(int k = 1; k <= 5; k++) {
                tk = t + a[k] * h;
                for(int l = 0; l < num; l++) {
                    uk[l] = u[l];
                    for(int j = 0; j <= k-1; j++)
                        uk[l] += b[k][j] * f[j][l];
                }
                fSystem(h,tk,uk,f[k]);
            }
            for(int l = 0; l < num; l++)
                for(int k = 0; k < 6; k++)
                    u[l] += c[k] * f[k][l];
        }
    }
    
    public void paint(Graphics g) {
        g.drawRect(40,40,600,400);
        int steps = 1;
        int num = 3;
        double h = 0.005;
        double t = 0.0;
        double u[] = {0.8, 0.8, 0.8}; // initial conditions
        // wait for transients to decay
        for(int i = 0; i < 4000; i++) {
            t += h;
            map(u, steps, h, t, num);
            int m = (int)(10.0*u[2] - 400);
            int n = (int)(10.0*u[0] + 200);
            g.drawLine(m,n,m,n);
        }
    }
    
    public static void main (String args[]) {
        Frame frame = new ChaosControl();
        frame.setVisible(true);
    }
}
