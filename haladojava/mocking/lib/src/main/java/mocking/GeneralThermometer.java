package mocking;

public interface GeneralThermometer {

	int measure();

	void restart();

	void calibrate(int x);

	boolean isOn();

}