package memristor;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainClass {
	public static void main(String[] args) throws InterruptedException {
		double deltatime = 0.000001;
		IVTrace ivtrace = new IVTrace();
		TimePlot timeplot = new TimePlot();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		


		PowerSource ps = new PowerSource(deltatime);
		Memristor mr = new Memristor();
		ivtrace.addComponent(mr);
		timeplot.addComponent(mr, ps);

		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		container.add(ivtrace);
		container.add(timeplot);
		frame.add(container);
		frame.pack();
		
		double V_1 = 0.0;
		double V_2 = 0.0;
		
		while (true) {
			ps.run(deltatime);
			V_1 = ps.getVoltageV1();
			V_2 = 0.0;
			mr.run(deltatime, V_1, V_2);
			ivtrace.repaint();
			timeplot.repaint();
			//System.out.format("Time: %f -> %f %f\n", ps.getTime(), V_1, mr.getCurrent());
			Thread.sleep(1);

		}
	}
}
