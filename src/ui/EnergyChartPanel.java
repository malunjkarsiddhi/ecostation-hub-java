package ui;

import javax.swing.*;
import java.awt.*;


public class EnergyChartPanel extends JPanel {

    private final int[] hours = {8, 10, 12, 14, 16, 18, 20, 22};
    private final int[] values = {150, 450, 380, 600, 800, 950, 600, 250};

    public EnergyChartPanel() {
        setPreferredSize(new Dimension(600, 260));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        int w = getWidth();
        int h = getHeight();
        int left = 60;
        int right = 20;
        int top = 30;
        int bottom = 40;

        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        
        g2.setColor(new Color(250, 252, 255));
        g2.fillRoundRect(0, 0, w - 1, h - 1, 18, 18);

        
        g2.setColor(new Color(24, 28, 32));
        g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        g2.drawString("Energy Consumption Flow (Pune Hub)", left, 22);

        
        int x0 = left;
        int y0 = h - bottom;
        int x1 = w - right;
        int y1 = top;

        
        g2.setColor(new Color(220, 225, 230));
        g2.drawLine(x0, y0, x0, y1);
        g2.drawLine(x0, y0, x1, y0);

        
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        int[] yMarks = {0, 250, 500, 750, 1000};
        for (int mark : yMarks) {
            int yy = y0 - (y0 - y1) * mark / 1000;
            g2.setColor(new Color(235, 238, 242));
            g2.drawLine(x0, yy, x1, yy);
            g2.setColor(new Color(140, 150, 160));
            String label = String.valueOf(mark);
            g2.drawString(label, x0 - 30, yy + 4);
        }

        
        int count = hours.length;
        int plotWidth = x1 - x0;
        int stepX = plotWidth / (count - 1);
        g2.setColor(new Color(140, 150, 160));
        for (int i = 0; i < count; i++) {
            int xx = x0 + i * stepX;
            String label = String.format("%02d:00", hours[i]);
            g2.drawString(label, xx - 18, y0 + 18);
        }

        
        int[] xs = new int[count];
        int[] ys = new int[count];
        for (int i = 0; i < count; i++) {
            xs[i] = x0 + i * stepX;
            ys[i] = y0 - (y0 - y1) * values[i] / 1000;
        }

        
        Polygon area = new Polygon();
        area.addPoint(xs[0], y0);
        for (int i = 0; i < count; i++) {
            area.addPoint(xs[i], ys[i]);
        }
        area.addPoint(xs[count - 1], y0);
        g2.setColor(new Color(24, 175, 96, 40));
        g2.fill(area);

        
        g2.setColor(new Color(24, 175, 96));
        g2.setStroke(new BasicStroke(2.5f));
        for (int i = 0; i < count - 1; i++) {
            g2.drawLine(xs[i], ys[i], xs[i + 1], ys[i + 1]);
        }

       
        g2.setColor(new Color(24, 175, 96));
        for (int i = 0; i < count; i++) {
            g2.fillOval(xs[i] - 3, ys[i] - 3, 6, 6);
        }

        g2.dispose();
    }
}
