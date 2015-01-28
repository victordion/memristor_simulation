package memristor;

public class Memristor {
	private double R;
	private double R_max;
	private double R_min;
	private double V_1;
	private double V_2;
	private double RQ_ratio;
	private double current;

	//double R, double R_max, double R_min, double RQ_ratio
	Memristor(){
		R = 100;
		R_min = 10;
		R_max = 10000;
		RQ_ratio = 1000000000;
	}
	
	double getCurrent(){
		return current;
	}
	
	double getVoltage(){
		return V_1 - V_2;
	}
	
	double getR(){
		return R;
	}
	
	double run(double deltatime, double V_1, double V_2){
		
		
		this.V_1 = V_1;
		this.V_2 = V_2;
		
		current = (V_1 - V_2) / R;
		
		R = R - current * RQ_ratio * deltatime;
		R = R > R_max ? R_max : ( R < R_min ? R_min : R);
		
		return current;
	}

}
