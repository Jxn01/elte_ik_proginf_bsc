package second_2;

import java.util.List;
import java.util.Locale;

public enum WeekDay {
	MONDAY(1, "Hetfo", "Montag"), TUESDAY(2, "Kedd", null, "Martedi"), WEDNESDAY(3, "Szerda", "Mitwoch", "Mercoledi"), THURSDAY(4), FRIDAY(5), SATURDAY(6), SUNDAY(7);
	
	public static final List<String> SUPPORTED_LANGS = List.of("en", "hu", "de", "it");
	
	private final int numInWeek;
	private final String[] names;
	
	private WeekDay(int numInWeek, String... names) {
		this.numInWeek = numInWeek;
		this.names = names;
	}
	/**
	 * Returns the number of the day.
	 * @return the number of the day [1-7]
	 */
	public int getNumber() throws IllegalStateException {
		return numInWeek;
	}
	
	public WeekDay next() {
		return WeekDay.values()[numInWeek%7];
	}
	
	public String getName(String lang) {
		int idx = SUPPORTED_LANGS.indexOf(lang);
		if(idx == -1) {			
			return "?";
		}
		if(idx == 0) {
			return this.toString().toLowerCase(Locale.ROOT);
		}
		String result = null;
		if(idx-1<names.length) {
			result = names[idx-1];
		}
		if(result == null) {
			return "?";
		}
		return result;
	}
}
