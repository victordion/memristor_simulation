package com.jianweicui.app;

public class PowerSource {
	double outvoltage;
	double time ;
	double frequency ;
	double amplitude ;
	double phase ;
	double dc;
	double deltatime;
	PowerSource(double deltatime){
		time = 0;
		frequency = 1000;
		amplitude = 1;
		phase = 0;
		dc = 0;
		this.deltatime = deltatime;
	}
	
	double getVoltageV1(){
		outvoltage = amplitude * Math.sin(2* Math.PI * frequency * time + phase) + dc ;
		//System.out.format("V1: %f\n", outvoltage);
		return outvoltage;
	}
	
	double getTime(){
		return time;
	}
	void run(double deltatime){
		time += deltatime;
	}
	
	
}
