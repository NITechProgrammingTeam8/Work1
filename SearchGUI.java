import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SearchGUI extends JFrame implements ActionListener {
    JRadioButton[] radio;
    int which;
    Search search;
    Map<Node, NodePanel> map;
    Node[] node;
    ArrayList<PathPanel> paths;

    {
        which = 0;
        search = new Search(which);
        map = new HashMap<>();
        node = search.getNode();
        paths = new ArrayList<>();
    }

    public static void main(String args[]) {
        SearchGUI frame = new SearchGUI("探索");
        frame.setVisible(true);
    }

    SearchGUI(String title) {
        setTitle(title);
        int appWidth = 1200;
        int appHeight = 700;
        setBounds(100, 100, appWidth, appHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SpringLayout layout = new SpringLayout();
        JPanel mainPanel = new JPanel();
        int mainWidth = 1000;
        mainPanel.setPreferredSize(new Dimension(mainWidth, appHeight));
        mainPanel.setLayout(layout);

        for (int i = 0; i < 10; i++) {
            map.put(node[i], new NodePanel(node[i]));
        }
        mainPanel.setBorder(new BevelBorder(BevelBorder.RAISED));

        layout.putConstraint(SpringLayout.NORTH, map.get(node[0]), 300, SpringLayout.NORTH, mainPanel);
        layout.putConstraint(SpringLayout.WEST, map.get(node[0]), 25, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.SOUTH, map.get(node[1]), -50, SpringLayout.NORTH, map.get(node[0]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[1]), 100, SpringLayout.EAST, map.get(node[0]));
        layout.putConstraint(SpringLayout.NORTH, map.get(node[2]), 0, SpringLayout.NORTH, map.get(node[1]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[2]), 100, SpringLayout.EAST, map.get(node[1]));
        layout.putConstraint(SpringLayout.SOUTH, map.get(node[7]), 0, SpringLayout.NORTH, map.get(node[2]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[7]), 100, SpringLayout.EAST, map.get(node[2]));
        layout.putConstraint(SpringLayout.SOUTH, map.get(node[9]), -50, SpringLayout.NORTH, map.get(node[7]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[9]), 100, SpringLayout.EAST, map.get(node[7]));
        layout.putConstraint(SpringLayout.NORTH, map.get(node[4]), 0, SpringLayout.NORTH, map.get(node[7]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[4]), 100, SpringLayout.EAST, map.get(node[9]));
        layout.putConstraint(SpringLayout.NORTH, map.get(node[5]), 200, SpringLayout.SOUTH, map.get(node[0]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[5]), 150, SpringLayout.WEST, map.get(node[0]));
        layout.putConstraint(SpringLayout.SOUTH, map.get(node[6]), -100, SpringLayout.NORTH, map.get(node[5]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[6]), 100, SpringLayout.EAST, map.get(node[5]));
        layout.putConstraint(SpringLayout.NORTH, map.get(node[3]), 50, SpringLayout.SOUTH, map.get(node[6]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[3]), 100, SpringLayout.EAST, map.get(node[6]));
        layout.putConstraint(SpringLayout.NORTH, map.get(node[8]), 0, SpringLayout.NORTH, map.get(node[3]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[8]), 100, SpringLayout.EAST, map.get(node[3]));

        for (int i = 0; i < 10; i++) {
            mainPanel.add(map.get(node[i]));
        }

        JPanel sidePanel = searchButtons();
        sidePanel.setPreferredSize(new Dimension(appWidth - mainWidth, appHeight));

        Container contentPane = getContentPane();
        contentPane.add(mainPanel, BorderLayout.CENTER);
        contentPane.add(sidePanel, BorderLayout.LINE_END);

        pack();
        for (Map.Entry<Node, NodePanel> entry : map.entrySet()) {
            NodePanel panel = entry.getValue();
            for (Node child : panel.getNode().getChildren()) {
                NodePanel childPanel = map.get(child);

                PathPanel path = new PathPanel(panel, childPanel);
                layout.putConstraint(SpringLayout.WEST, path, path.getLeft(), SpringLayout.WEST, mainPanel);
                layout.putConstraint(SpringLayout.EAST, path, path.getRight(), SpringLayout.WEST, mainPanel);
                layout.putConstraint(SpringLayout.NORTH, path, path.getTop(), SpringLayout.NORTH, mainPanel);
                layout.putConstraint(SpringLayout.SOUTH, path, path.getBtm(), SpringLayout.NORTH, mainPanel);
                mainPanel.add(path);
                paths.add(path);
            }
        }
    }

    JPanel searchButtons() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
    
        radio = new JRadioButton[6];
        radio[0] = new JRadioButton("幅優先探索");
        radio[1] = new JRadioButton("深さ優先探索");
        radio[2] = new JRadioButton("分岐限定法");
        radio[3] = new JRadioButton("山登り法");
        radio[4] = new JRadioButton("最良優先探索");
        radio[5] = new JRadioButton("A*アルゴリズム");
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < radio.length; i++) {
            group.add(radio[i]);
            p.add(radio[i]);
        }
        
        JButton button = new JButton("実行");
        button.addActionListener(this);
        p.add(button);

        return p;
    }

    public void actionPerformed(ActionEvent e) {
        for (NodePanel p : map.values()) {
            p.getNode().reset();
            p.update((Integer)p.getModel().getValue()); // ヒューリスティック値の変更を反映
        }

        for (int i = 0 ; i < radio.length; i++){ // 探索の選択
            if (radio[i].isSelected()){
              which = i + 1;
            }
        }

        for(int i = 0; i < paths.size(); i++) {
            PathPanel p = paths.get(i);
            p.update((Integer)p.getModel().getValue()); // コストの変更を反映
        }
        
        ArrayList<Node> route = search.exec(which); // 再実行
        
        for(int i = 0; i < paths.size(); i++) {
            PathPanel p = paths.get(i);
            for(int j = 0; j < route.size() - 1; j++) {
                if(p.forRepaint(route.get(j), route.get(j + 1))) {
                    break;
                }
            }
            p.repaint();
        }
    }
}

class NodePanel extends JPanel {
    private static int counter = 0;
    private int id;
    private SpinnerNumberModel model;
    private Node node;
    private ArrayList<NodePanel> children;

    NodePanel(Node node) {
        id = counter++;
        this.node = node;
        setLayout(new GridLayout(2, 1));
        setBackground(Color.ORANGE);
        setBorder(new BevelBorder(BevelBorder.RAISED));

        JLabel label = new JLabel(id + ": " + node.getName());
        model = new SpinnerNumberModel(node.getHValue(), 0, 9999, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(50, 25));

        add(label);
        add(spinner);
    }

    int getId() {
        return id;
    }

    Node getNode() {
        return node;
    }

    SpinnerNumberModel getModel() {
        return model;
    }

    void update(int value) {
        node.setHValue(value);
    }
}

class PathPanel extends JPanel {
    private NodePanel par;
    private NodePanel child;
    private Point start;
    private Point end;
    private SpinnerNumberModel model;
    int grace; // パネルの幅の猶予
    boolean pass;

    PathPanel(NodePanel par, NodePanel child) {
        this.par = par;
        this.child = child;
        grace = 50;
        pass = false;

        setOpaque(false);
        Rectangle source = par.getBounds();
        Rectangle distance = child.getBounds();
        setShortestDistance(source, distance);

        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        
        JPanel p = new JPanel(new GridLayout(2, 1));
        p.setAlignmentY(Component.CENTER_ALIGNMENT);
        p.setBackground(new Color(0,128,128));
        p.setBorder(new BevelBorder(BevelBorder.LOWERED));
        JLabel label = new JLabel(par.getId() + " to " + child.getId());
        model = new SpinnerNumberModel(par.getNode().getCost(child.getNode()), 0, 9999, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(40, 25));  // graceは各座標の２倍以上にすること
        
        p.add(label);
        p.add(spinner);

        layout.putConstraint(SpringLayout.WEST, p, (getRight() - getLeft()) / 2 - 20, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, p, (getBtm() - getTop()) / 2 - 20, SpringLayout.NORTH, this);
        add(p);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintArrows(g);
    }

    void paintArrows(Graphics g) {
        int fromX = start.x;
        int fromY = start.y;
        int toX = end.x;
        int toY = end.y;

        int constX = getLeft();
        int constY = getTop();
        if(pass) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLUE);
        }
        g.drawLine(fromX - constX, fromY - constY, toX - constX, toY - constY);
    }

    void setShortestDistance(Rectangle source, Rectangle distance) {
        Point[] fromMidPoints = getMidPoints(source);
        Point[] toMidPoints = getMidPoints(distance);

        double min = Double.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            Point from = fromMidPoints[i].getLocation();
            for (int j = 0; j < 4; j++) {
                Point to = toMidPoints[j].getLocation();
                double value = (from.getX() - to.getX()) * (from.getX() - to.getX())
                        + (from.getY() - to.getY()) * (from.getY() - to.getY());
                if (value < min) {
                    min = value;
                    start = from;
                    end = to;
                }
            }
        }
    }

    Point[] getMidPoints(Rectangle r) {
        Point[] midPoints = new Point[4];
        for (int i = 0; i < midPoints.length; i++) {
            midPoints[i] = new Point();
        }
        midPoints[0].setLocation(r.x + r.width / 2.0, r.y); // 上の中点
        midPoints[1].setLocation(r.x + r.width, r.y + r.height / 2.0); // 右の中点
        midPoints[2].setLocation(r.x + r.width / 2.0, r.y + r.height / 2.0); // 下の中点
        midPoints[3].setLocation(r.x, r.y + r.height / 2.0); // 左の中点
        return midPoints;
    }

    Point getStart() {
        return start;
    }

    Point getEnd() {
        return end;
    }

    int getLeft() {
        return execHor(true) - grace;
    }

    int getRight() {
        return execHor(false) + grace;
    }

    int getTop() {
        return execVer(true) - grace;
    }

    int getBtm() {
        return execVer(false) + grace;
    }

    int execHor(boolean b) {
        int left = start.x;
        int right = end.x;
        if (left > right) {
            int tmp = left;
            left = right;
            right = tmp;
        }
        if(b) {
            return left;
        } else {
            return right;
        }
    }

    int execVer(boolean b) {
        int top = start.y;
        int btm = end.y;
        if (top > btm) {
            int tmp = top;
            top = btm;
            btm = tmp;
        }
        if(b) {
            return top;
        } else {
            return btm;
        }
    }

    SpinnerNumberModel getModel() {
        return model;
    }

    void update(int value) {
        par.getNode().remakeChild(child.getNode(), value);
    }

    NodePanel par() {
        return par;
    }
    
    boolean forRepaint(Node to, Node from) {
        pass = (child.getNode() == to && par.getNode() == from);
        return pass;
    }
}