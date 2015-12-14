package com.jianweicui.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import java.util.Collections;
import java.util.Vector;

public class TimePlot extends JPanel {
	Memristor mr;
	PowerSource ps;
	int current_w_point;
	double voltage_scale_factor = 100;
	double current_scale_factor = 100000;
	double r_scale_factor = 0.5;
	Vector<Integer> ts = new Vector<Integer>();
	Vector<Integer> voltages = new Vector<Integer>();
	Vector<Integer> currents = new Vector<Integer>();
	Vector<Integer> rs = new Vector<Integer>();
	int prefered_w = 1000;
	int prefered_h = 500;
	
	//double history_max_voltage;
	//double history_max_current;
	//double history_max_r;
	
	TimePlot(){
		this.setPreferredSize(new Dimension(prefered_w,prefered_h));
		current_w_point = 1;
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	void addComponent(Memristor mr, PowerSource ps){
		this.mr = mr;
		this.ps = ps;
		r_scale_factor = 0.25 * prefered_h / mr.getR();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.blue);
		
		Dimension size = this.getPreferredSize();
		int w = size.width;
		int h = size.height;

		//int w_mid = w / 2;
		int h_mid = h / 2;

		// draw some axis and ticks
		g2d.drawLine(0, h_mid,  w, h_mid);
		for(int width_pix = 100; width_pix < prefered_w; width_pix += 100){
			g2d.fillOval(width_pix, h_mid, 5, 5);
			g2d.drawString(String.format("%f s", ps.deltatime * width_pix), width_pix, h_mid + 20);
		}
		
		double r = mr.getR() * r_scale_factor;
		double voltage = mr.getVoltage() * voltage_scale_factor;
		double current = mr.getCurrent() * current_scale_factor;
		
		int r_int = (int) Math.round(r);
		int voltage_int = (int) Math.round(voltage);
		int current_int = (int) Math.round(current);
		
		ts.add(current_w_point);
		voltages.add(voltage_int);
		currents.add(current_int);
		rs.add(r_int);
		
		for (int i = 0; i < voltages.size() ; i++){
			g2d.setColor(Color.blue);
			g2d.fillRect(ts.get(i), - voltages.get(i) + h_mid, 3, 3);
			g2d.setColor(Color.red);
			g2d.fillRect(ts.get(i), - currents.get(i) + h_mid, 3, 3);
			g2d.setColor(Color.green);
			g2d.fillRect(ts.get(i), - rs.get(i) + h_mid, 3, 3);
		}
		current_w_point += 1;
		
		if(current_w_point >= prefered_w){
			
			int history_max_voltage = Math.max(Math.abs(Collections.max(voltages)), Math.abs(Collections.min(voltages)));
			int history_max_current = Math.max(Math.abs(Collections.max(currents)), Math.abs(Collections.min(currents)));
			int history_max_r = Math.abs(Collections.max(rs));
			ts.clear();
			voltages.clear();
			currents.clear();
			rs.clear();
			
			
			
			if(history_max_voltage >= prefered_h / 2 ){
				voltage_scale_factor /= 4;
			}
			else{
				if(history_max_voltage < prefered_h / 8){
					voltage_scale_factor *= 2;
				}		
			}
			
			if(history_max_current >= prefered_h / 2 ){
				current_scale_factor /= 4;
			}
			else{
				if(history_max_current < prefered_h / 8){
					current_scale_factor *= 2;
				}		
			}
			
			if(history_max_r >= prefered_h / 2 ){
				r_scale_factor /= 4;
			}
			else{
				if(history_max_r < prefered_h / 8){
					r_scale_factor *= 2;
				}		
			}
			current_w_point = 1;
		}
		
		StringBuilder str = new StringBuilder();
		str.append(String.format("Time = %f s\n", ps.getTime()));
		str.append(String.format("Memristor R = %1.1f Ohms Voltage = %f V Current = %f A\n", mr.getR(), mr.getVoltage(), mr.getCurrent()));
		str.append(String.format("Voltage full scale = %1f V\n", 0.5 * prefered_h / voltage_scale_factor));
		str.append(String.format("Current full scale = %1f A\n", 0.5 * prefered_h / current_scale_factor));
		str.append(String.format("Resistance full scale = %1f Ohms\n", 0.5 * prefered_h / r_scale_factor));
		g2d.setColor(Color.blue);
		this.drawString(g2d, str.toString(), 10, 10);

	}
	
	void drawString(Graphics2D g, String text, int x, int y) {
		
	    for (String line : text.split("\n"))
	        g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}
}
