package ui;

import dao.StationDAO;
import model.Station;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DashboardWindow extends JFrame {

    private final StationDAO dao;

    private JLabel totalStationsLabel;
    private JLabel totalEnergyLabel;
    private JLabel totalRevenueLabel;
    private DefaultTableModel tableModel;

    private JPanel centerCards;
    private JPanel dashboardPanel;
    private StationsPanel stationsPanel;
    private AnalyticsPanel analyticsPanel;

    public DashboardWindow(StationDAO dao) {
        this.dao = dao;
        initUI();
        loadMetrics();
        loadTable();
    }

    private void initUI() {
        setTitle("Eco-Station Management Hub");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);

        Color bg = new Color(246, 248, 252);
        Color sidebarBg = new Color(241, 248, 243);
        Color cardBg = Color.WHITE;
        Color accent = new Color(24, 175, 96);
        Color textDark = new Color(24, 28, 32);
        Color textMuted = new Color(120, 130, 140);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(bg);
        setContentPane(root);

        
        JPanel sidebar = new JPanel();
        sidebar.setBackground(sidebarBg);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel logoTitle = new JLabel("Eco-Station");
        logoTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logoTitle.setForeground(accent);
        JLabel logoSubtitle = new JLabel("MANAGEMENT HUB");
        logoSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        logoSubtitle.setForeground(textMuted);

        sidebar.add(logoTitle);
        sidebar.add(Box.createVerticalStrut(2));
        sidebar.add(logoSubtitle);
        sidebar.add(Box.createVerticalStrut(24));

        
        sidebar.add(navItem("Dashboard", true));
        sidebar.add(navItem("Stations", false));
        sidebar.add(navItem("Analytics", false));
        sidebar.add(Box.createVerticalGlue());

        JPanel tipCard = new JPanel();
        tipCard.setBackground(new Color(0, 120, 80));
        tipCard.setBorder(new EmptyBorder(12, 12, 12, 12));
        tipCard.setLayout(new BoxLayout(tipCard, BoxLayout.Y_AXIS));
        JLabel tipTitle = new JLabel("Eco Tip");
        tipTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tipTitle.setForeground(Color.WHITE);
        JLabel tipText = new JLabel("Shift heavy charging loads to off-peak hours to maximize renewable usage.");
        tipText.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tipText.setForeground(Color.WHITE);
        tipCard.add(tipTitle);
        tipCard.add(Box.createVerticalStrut(4));
        tipCard.add(tipText);
        sidebar.add(tipCard);

        root.add(sidebar, BorderLayout.WEST);

        
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(bg);
        main.setBorder(new EmptyBorder(16, 16, 16, 16));
        root.add(main, BorderLayout.CENTER);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        JLabel pageTitle = new JLabel("Dashboard Management");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pageTitle.setForeground(textDark);
        JLabel userLabel = new JLabel("Admin User  |  System Administrator");
        userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        topBar.add(pageTitle, BorderLayout.WEST);
        topBar.add(userLabel, BorderLayout.EAST);
        main.add(topBar, BorderLayout.NORTH);

        
        centerCards = new JPanel(new CardLayout());
        centerCards.setOpaque(false);
        main.add(centerCards, BorderLayout.CENTER);

        dashboardPanel = buildDashboardPanel(cardBg, textMuted, textDark);
        centerCards.add(dashboardPanel, "DASHBOARD");

        stationsPanel = new StationsPanel(dao);
        centerCards.add(stationsPanel, "STATIONS");

        analyticsPanel = new AnalyticsPanel(dao);
        centerCards.add(analyticsPanel, "ANALYTICS");
    }

    private JPanel buildDashboardPanel(Color cardBg, Color textMuted, Color textDark) {
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        
        JPanel cardsRow = new JPanel(new GridLayout(1, 3, 12, 0));
        cardsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        cardsRow.setOpaque(false);
        totalStationsLabel = new JLabel("0");
        totalEnergyLabel = new JLabel("0 kWh");
        totalRevenueLabel = new JLabel("₹0");
        cardsRow.add(metricCard("Total Stations", totalStationsLabel, cardBg, textMuted, textDark));
        cardsRow.add(metricCard("Total Energy (kWh)", totalEnergyLabel, cardBg, textMuted, textDark));
        cardsRow.add(metricCard("Total Revenue (INR)", totalRevenueLabel, cardBg, textMuted, textDark));
        center.add(cardsRow);
        center.add(Box.createVerticalStrut(16));

        
        JPanel chartHolder = new JPanel(new BorderLayout());
        chartHolder.setOpaque(false);
        chartHolder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));

        JPanel chartPanel = new JPanel() {
            private final int[] hours = {8, 10, 12, 14, 16, 18, 20, 22};
            private final int[] values = {150, 450, 380, 600, 800, 950, 600, 250};

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();
                int left = 60;
                int right = 20;
                int top = 30;
                int bottom = 40;

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
                    g2.drawString(String.valueOf(mark), x0 - 30, yy + 4);
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
        };
        chartPanel.setOpaque(false);
        chartHolder.add(chartPanel, BorderLayout.CENTER);
        center.add(chartHolder);

        return center;
    }

    private JPanel navItem(String text, boolean active) {
        JPanel p = new JPanel(new BorderLayout());
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        p.setBorder(new EmptyBorder(8, 12, 8, 8));
        p.setBackground(active ? new Color(222, 244, 231) : new Color(241, 248, 243));

        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        l.setForeground(active ? new Color(24, 175, 96) : new Color(90, 96, 100));
        p.add(l, BorderLayout.WEST);

        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        p.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                showSection(text);
            }
        });

        return p;
    }

    private void showSection(String name) {
        CardLayout cl = (CardLayout) centerCards.getLayout();
        if ("Dashboard".equals(name)) {
            loadMetrics();
            loadTable();
            cl.show(centerCards, "DASHBOARD");
        } else if ("Stations".equals(name)) {
            stationsPanel.reload();
            cl.show(centerCards, "STATIONS");
        } else if ("Analytics".equals(name)) {
            analyticsPanel.loadData();
            cl.show(centerCards, "ANALYTICS");
        } else {
            cl.show(centerCards, "DASHBOARD");
        }
    }

    private JPanel metricCard(String title, JLabel valueLabel,
                              Color cardBg, Color textMuted, Color textDark) {
        JPanel card = new JPanel();
        card.setBackground(cardBg);
        card.setBorder(new EmptyBorder(12, 14, 12, 14));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(textMuted);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valueLabel.setForeground(textDark);
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(6));
        card.add(valueLabel);
        return card;
    }

    private void loadMetrics() {
        totalStationsLabel.setText(String.valueOf(dao.getTotalStations()));
        totalEnergyLabel.setText(dao.getTotalEnergy() + " kWh");
        totalRevenueLabel.setText("₹" + dao.getTotalRevenue());
    }

    private void loadTable() {
        if (tableModel == null) {
            tableModel = new DefaultTableModel(
                    new Object[]{"ID", "Code", "Name", "Location", "Status", "Energy", "Revenue"}, 0
            );
        }
        tableModel.setRowCount(0);
        for (Station s : dao.getAllStations()) {
            tableModel.addRow(new Object[]{
                    s.getId(),
                    s.getCode(),
                    s.getName(),
                    s.getLocation(),
                    s.getStatus(),
                    s.getEnergyKwh(),
                    s.getRevenue()
            });
        }
    }
}
