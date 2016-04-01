import RegFunctions.RegressionFunctions;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LinesComponent  extends JComponent {

    private static class Line{
        final int x1;
        final int y1;
        final int x2;
        final int y2;
        final Color color;

        public Line(int x1, int y1, int x2, int y2, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
        }
    }

    private final ArrayList<Line> lines = new ArrayList<>();

    public void addLine(int x1, int y1, int x2, int y2) {
        addLine(x1, y1, x2, y2, new Color(204, 204, 0));
    }

    public void addLine(int x1, int y1, int x2, int y2, Color color) {
        lines.add(new Line(x1, y1, x2, y2, color));
        repaint();
    }

    public void clearLines() {
        lines.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < lines.size(); ++i) {
            Line line = lines.get(i);
            if (line != null) {
                ((Graphics2D) g).setStroke(new BasicStroke(5));
                g.setColor(line.color);
                g.drawLine(line.x1, line.y1, line.x2, line.y2);
            }
        }
    }

    public static void main(String... args) {
        JFrame testFrame = new JFrame();
        testFrame.add(new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setFont(g.getFont().deriveFont(20f));
                for (int i = 1; i < 10; ++i) {
                    g.drawString(Double.toString(i / 10.0), 50 + i * 100, 320);
                }
                for (int i = -10; i < 30; i += 2) {
                    if (i < 0) {
                        g.drawString(Double.toString(i / 100.0), 13, (int) (300 - (i / 10.0) * 100));
                    } else {
                        g.drawString(Double.toString(i / 100.0), 20, (int) (300 - (i / 10.0) * 100));
                    }
                }
            }
        });
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        testFrame.setSize(1000, 500);
        testFrame.setVisible(true);
        final LinesComponent comp = new LinesComponent();
        comp.setPreferredSize(new Dimension(1000, 500));
        testFrame.getContentPane().add(comp, BorderLayout.CENTER);

        RegressionTask1 r = (new RegressionTask1(0, 1, 40) {
            @Override
            public double fun(double x) {
                return RegressionFunctions.xSin15X(x);
            }
        });
        r.trainNetwork(5079525, comp);

        //comp.clearLines();
        testFrame.pack();
        testFrame.setVisible(true);
    }

}
