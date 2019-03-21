package com.sandu.web;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @ClassName: test
 * @Auther: gaoj
 * @Date: 2019/2/25 16:57
 * @Description:
 * @Version 1.0
 */
public class test {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(100, 100);
        frame.setLocation(600,0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addPanel(frame);
        frame.setVisible(true);
    }

    private static void addPanel(JFrame frame) {
        JPanel jPanel = new JPanel();
        JButton jButton = new JButton("click");

        jButton.addActionListener(e -> {
            frame.remove(jPanel);
            frame.add(new PP());
            frame.setSize(1000,1000);
            frame.repaint();
        });

        jPanel.add(jButton);
        frame.add(jPanel);
    }
}

class PP extends JPanel {
    private Image img;
    public PP() {
        super();
        try {
            URL url = new URL("http://p7.xyzs.com/app/e7/49/511667389/ed30d26d4c8e3bc3384964ea0dec04d1_i3.jpg");
            img = new ImageIcon(url).getImage();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }

}
