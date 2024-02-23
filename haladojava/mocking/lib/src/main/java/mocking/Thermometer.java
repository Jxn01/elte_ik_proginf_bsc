package mocking;

public class Thermometer implements GeneralThermometer {

	@Override
	public native int measure();
	@Override
	public native void restart();
	@Override
	public native void calibrate(int x);
	@Override
	public native boolean isOn();
	
}
