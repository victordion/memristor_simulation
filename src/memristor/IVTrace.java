package memristor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Vector;

public class IVTrace extends JPanel {

	public double x, y;
	Memristor mr;

	IVTrace(){
		this.setPreferredSize(new Dimension(500, 500));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	void addComponent(Memristor mr) {
		this.mr = mr;
	}

	Vector<Integer> xs = new Vector<Integer>();
	Vector<Integer> ys = new Vector<Integer>();

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.blue);
		
		//Dimension size = getSize();
		Dimension size = this.getPreferredSize();
		int w = size.width;
		int h = size.height;

		int w_mid = w / 2;
		int h_mid = h / 2;

		g2d.drawLine(0, h_mid,  w, h_mid);

		//g2d.drawLine(h_mid, 0, h_mid, w);
		g2d.drawLine(w_mid, 0, w_mid, h);

		x = mr.getVoltage() * 100;
		y = -mr.getCurrent() * 100000;
		int x_int = (int) x;
		int y_int = (int) y;
		
		xs.add(x_int);
		ys.add(y_int);
		for (int i = xs.size() - 1; i >= xs.size() - 500 && i >= 0; i--){
			//g2d.fillRect(x_int + h_mid, y_int + w_mid, 5, 5);
			g2d.fillRect(xs.get(i) + h_mid, ys.get(i) + w_mid, 3, 3);

		}
		String state = String.format("Memristor R = %f", mr.getR());
		g2d.drawString(state, 10, 10);


	}

	
}