package generics;

import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class BiMap<K, V> {

	private final TreeMap<K, V> keyToValue;//  = new TreeMap<>();
	private final TreeMap<V, K> valueToKey;// = new TreeMap<>();
	
	private BiMap() {
		this(new TreeMap<>(), new TreeMap<>());
	}
	
	private BiMap(TreeMap<K, V> k, TreeMap<V, K> v) {
		this.keyToValue = k;
		this.valueToKey = v;
	}
	
	public static <K1 extends Comparable<K1>, V1 extends Comparable<V1>> BiMap<K1, V1> create(Class<K1> k, Class<V1> v) {
		return new BiMap<>();
	}
	
	public static <K, V> BiMap<K, V> create(Comparator<K> kComp, Comparator<V> vComp) {
		return new BiMap<>(new TreeMap<>(kComp), new TreeMap<>(vComp));
	}
	
	public void put(K k, V v) {
		keyToValue.put(k, v);
		valueToKey.put(v, k);
	}
	
	public K findKey(V v) {
		return valueToKey.get(v);
	}
	
	public V findValue(K k) {
		return keyToValue.get(k);
	}
	
	public void populate(List<? extends K> keys, List<? extends V> values) {
		if(keys.size() != values.size()) {
			throw new IllegalArgumentException();
		}
		var kIter = keys.iterator();
		var vIter = values.iterator();
		while(kIter.hasNext() && vIter.hasNext()) {
			put(kIter.next(), vIter.next());
		}
	}
	
	@Override
	public String toString() {
		return keyToValue.toString() +"\n" +
		valueToKey.toString();
	}
	
	public static void main(String[] args) {
		var v = BiMap.create(Integer.class, Long.class);
		v.populate(List.of(1, 2, 3), List.of(4L, 5L, 6L));
		
		System.out.println(v);
	}

}
