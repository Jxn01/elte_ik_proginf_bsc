package elte;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class TempWritable implements WritableComparable<Object> {
	
	private Text time;
	private DoubleWritable temp;
	
	public TempWritable() {
		this.time = new Text("");
		this.temp = new DoubleWritable(Double.MIN_VALUE);
	}
	
	public String toString() {
		return this.time + ": " + this.temp + "C";
	}
	
	public TempWritable(String _time, double _temp) {
		this.time = new Text(_time);
		this.temp = new DoubleWritable(_temp);
	}
	
	public void setTime(Text time) {
		this.time = time;
	}
	
	public void setTemp(DoubleWritable temp) {
		this.temp = temp;
	}

	public Text getTime() {
		return this.time;
	}
	
	public DoubleWritable getTemp() {
		return this.temp;
	}
	
	public void set(String _time, double _temp) {
		this.time = new Text(_time);
		this.temp = new DoubleWritable(_temp);
	}
	

	public void readFields(DataInput in) throws IOException {
		this.time.readFields(in);
		this.temp.readFields(in);
	}
	
	public void write(DataOutput out) throws IOException {
		this.time.write(out);
		this.temp.write(out);
	}

	@Override
	public int compareTo(Object o) {
		return this.temp.compareTo(((TempWritable)o).temp);
	}
}
