import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class Fourth extends JFrame {

    private final JPanel drawingPanel;
    private Color currentColor = Color.BLACK;
    private int currentThickness = 1;
    private List<Line> lines = new ArrayList<>(); //Список для хранения линий
    private Point startPoint = null;


    public Fourth() {
        setTitle("Drawing App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Сглаживание линий

                g2d.setStroke(new BasicStroke(currentThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); //Скругленные концы и соединения

                for (Line line : lines) {
                    g2d.setColor(line.color);
                    g2d.drawLine(line.x1, line.y1, line.x2, line.y2);
                }
            }
        };
        drawingPanel.setBackground(Color.WHITE);
        add(drawingPanel, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu colorMenu = new JMenu("Цвет");
        menuBar.add(colorMenu);

        colorMenu.add(createColorItem("Черный", Color.BLACK));
        colorMenu.add(createColorItem("Красный", Color.RED));
        colorMenu.add(createColorItem("Зеленый", Color.GREEN));
        colorMenu.add(createColorItem("Синий", Color.BLUE));

        JMenu thicknessMenu = new JMenu("Толщина");
        menuBar.add(thicknessMenu);

        thicknessMenu.add(createThicknessItem(1));
        thicknessMenu.add(createThicknessItem(3));
        thicknessMenu.add(createThicknessItem(5));

        //Обработка событий мыши
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (startPoint != null) {
                    lines.add(new Line(startPoint.x, startPoint.y, e.getX(), e.getY(), currentColor));
                    startPoint = null;
                    drawingPanel.repaint();
                }
            }
        });

        drawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (startPoint != null) {
                    drawingPanel.repaint();
                }
            }
        });

        setVisible(true);
    }

    private JMenuItem createColorItem(String name, Color color) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(e -> {
            currentColor = color;
        });
        return item;
    }

    private JMenuItem createThicknessItem(int thickness) {
        JMenuItem item = new JMenuItem(String.valueOf(thickness));
        item.addActionListener(e -> {
            currentThickness = thickness;
        });
        return item;
    }

    private static class Line {
        int x1, y1, x2, y2;
        Color color;

        Line(int x1, int y1, int x2, int y2, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Fourth());
    }
}