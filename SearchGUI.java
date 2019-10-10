import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

class SearchGUI extends JFrame{
    public static void main(String args[]){
        SearchGUI frame = new SearchGUI("探索");
        frame.setVisible(true);
    }

    SearchGUI(String title){
        setTitle(title);
        int appWidth = 300;
		int appHeight = 250;
        setBounds(100, 100, appWidth, appHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        SpringLayout layout = new SpringLayout();
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(appWidth, appHeight));
        mainPanel.setLayout(layout);

        JPanel p = new JPanel(new GridLayout(2, 1));
        p.setBackground(Color.ORANGE);
        BevelBorder border = new BevelBorder(BevelBorder.RAISED);
        p.setBorder(border);

        JLabel label = new JLabel("Place");
        SpinnerNumberModel model = new SpinnerNumberModel(100, 0, 9999, 10);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(50, 25));

        p.add(label);
        p.add(spinner);

        layout.putConstraint(SpringLayout.NORTH, p, 50, SpringLayout.NORTH, mainPanel);
        layout.putConstraint(SpringLayout.WEST, p, 50, SpringLayout.WEST, mainPanel);

        Container contentPane = getContentPane();
        mainPanel.add(p);
        contentPane.add(mainPanel);
        

        pack();
        System.out.println(mainPanel.getComponent(0));
    }

    // @Override
	// public void paint(Graphics g) {
	// 	super.paint(g);
	// 	paintLine(g);
	// }

	// void paintLine(Graphics g) {
	// 	int startX = 50;
	// 	int startY = 100;
	// 	int endX = startX + 200;
	// 	int endY = startY + 0;
	// 	g.drawLine(startX, startY, endX, endY);
	// }
}

// public class LineDrawer {

//     public static void draw( Graphics g, Rectangle fromRect, Rectangle toRect ) {

//         // 最短の距離を求める
//         int[] position = ShortestDistanceObtainer.getShortest( fromRect, toRect );
//         int fromX = position[ 0 ];
//         int fromY = position[ 1 ];
//         int toX = position[ 2 ];
//         int toY = position[ 3 ];

//         g.drawLine( fromX, fromY, toX, toY );

//         // 線分の傾きを求める
//         double[] fromDouble = new double[] {fromX, fromY};
//         double[] toDouble = new double[] {toX, toY};

//         double angle = StepUtils.getAngle( fromDouble, toDouble );
//         double oneAngle = angle + 150.0;
//         double anotherAngle = angle - 150.0;

//         System.out.printf( "%3.0f,%3.0f,%3.0f\n",
//                            angle, oneAngle, anotherAngle );

//         // 矢印の部分を表示する
//         double pitch = 15.0;
//         g.drawLine( ( int ) ( toX + pitch * Math.cos( StepUtils.degreeToRadian( oneAngle ) ) ),
//                     ( int ) ( toY + pitch * Math.sin( StepUtils.degreeToRadian( oneAngle ) ) ),
//                     toX, toY );
//         g.drawLine( ( int ) ( toX + pitch * Math.cos( StepUtils.degreeToRadian( anotherAngle ) ) ),
//                     ( int ) ( toY + pitch * Math.sin( StepUtils.degreeToRadian( anotherAngle ) ) ),
//                     toX, toY );


//     }

// }