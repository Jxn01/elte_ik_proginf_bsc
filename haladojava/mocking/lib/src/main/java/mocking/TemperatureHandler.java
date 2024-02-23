package mocking;

public class TemperatureHandler {

	private final GeneralThermometer thermometer;
	
	public TemperatureHandler(GeneralThermometer th) {
		this.thermometer = th;
	}

	public double measure() {
		return thermometer.measure()*(9.0/5.0)+32.0;
	}

	public void restart() {
		thermometer.restart();
		while(!thermometer.isOn()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	public void calibrate(int x) {
		thermometer.calibrate(x);
	}

	public boolean isOn() {
		return thermometer.isOn();
	}
	
	
	
}
