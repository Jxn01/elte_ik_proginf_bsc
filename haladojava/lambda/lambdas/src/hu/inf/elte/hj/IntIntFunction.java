package hu.inf.elte.hj;

@FunctionalInterface
public interface IntIntFunction {

	int apply(int i);
    default int plus2(int i) {
    	return apply(i)+2;
    } 
    
    default IntIntFunction plus3(IntIntFunction before) {
    	return i-> before.apply(i)+3;
    }
	
}
