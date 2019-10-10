import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

class SearchGUI extends JFrame {
    public static void main(String args[]) {
        SearchGUI frame = new SearchGUI("探索");
        frame.setVisible(true);
    }

    SearchGUI(String title) {
        setTitle(title);
        int appWidth = 300;
        int appHeight = 250;
        setBounds(100, 100, appWidth, appHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SpringLayout layout = new SpringLayout();
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(appWidth, appHeight));
        mainPanel.setLayout(layout);

        Search search = new Search();
        Node[] node = search.getNode();
        JPanel[] panels = new JPanel[10];
        BevelBorder border = new BevelBorder(BevelBorder.RAISED);
        for (int i = 0; i < 10; i++) {
            panels[i] = new JPanel(new GridLayout(2, 1));
            panels[i].setBackground(Color.ORANGE);
            panels[i].setBorder(border);

            JLabel label = new JLabel(node[i].getName());
            SpinnerNumberModel model = new SpinnerNumberModel(node[i].getHValue(), 0, 9999, 1);
            JSpinner spinner = new JSpinner(model);
            spinner.setPreferredSize(new Dimension(50, 25));

            panels[i].add(label);
            panels[i].add(spinner);
        }

        layout.putConstraint(SpringLayout.NORTH, panels[0], 50, SpringLayout.NORTH, mainPanel);
        layout.putConstraint(SpringLayout.WEST, panels[0], 50, SpringLayout.WEST, mainPanel);
        for (int i = 1; i < 10; i++) {
            layout.putConstraint(SpringLayout.NORTH, panels[i], 50, SpringLayout.SOUTH, panels[i - 1]);
            layout.putConstraint(SpringLayout.WEST, panels[i], 50, SpringLayout.EAST, panels[i - 1]);
        }

        Container contentPane = getContentPane();
        for (int i = 0; i < 10; i++) {
            mainPanel.add(panels[i]);
        }
        contentPane.add(mainPanel);

        pack();
        System.out.println(mainPanel.getComponent(0));
    }

    // @Override
    // public void paint(Graphics g) {
    // super.paint(g);
    // paintLine(g);
    // }

    // void paintLine(Graphics g) {
    // int startX = 50;
    // int startY = 100;
    // int endX = startX + 200;
    // int endY = startY + 0;
    // g.drawLine(startX, startY, endX, endY);
    // }
}

// public class LineDrawer {

// public static void draw( Graphics g, Rectangle fromRect, Rectangle toRect ) {

// // 最短の距離を求める
// int[] position = ShortestDistanceObtainer.getShortest( fromRect, toRect );
// int fromX = position[ 0 ];
// int fromY = position[ 1 ];
// int toX = position[ 2 ];
// int toY = position[ 3 ];

// g.drawLine( fromX, fromY, toX, toY );

// // 線分の傾きを求める
// double[] fromDouble = new double[] {fromX, fromY};
// double[] toDouble = new double[] {toX, toY};

// double angle = StepUtils.getAngle( fromDouble, toDouble );
// double oneAngle = angle + 150.0;
// double anotherAngle = angle - 150.0;

// System.out.printf( "%3.0f,%3.0f,%3.0f\n",
// angle, oneAngle, anotherAngle );

// // 矢印の部分を表示する
// double pitch = 15.0;
// g.drawLine( ( int ) ( toX + pitch * Math.cos( StepUtils.degreeToRadian(
// oneAngle ) ) ),
// ( int ) ( toY + pitch * Math.sin( StepUtils.degreeToRadian( oneAngle ) ) ),
// toX, toY );
// g.drawLine( ( int ) ( toX + pitch * Math.cos( StepUtils.degreeToRadian(
// anotherAngle ) ) ),
// ( int ) ( toY + pitch * Math.sin( StepUtils.degreeToRadian( anotherAngle ) )
// ),
// toX, toY );

// }

// }