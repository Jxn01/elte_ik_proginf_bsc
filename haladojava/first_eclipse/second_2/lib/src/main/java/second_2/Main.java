package second_2;

public class Main {
	public static void main(String[] args) {
		for(var wd: WeekDay.values()) {
			System.out.println(wd.getNumber()+"\t"+wd+"\t"+wd.next()+"\t"+wd.getName("en")+"\t"+wd.getName("de")+"\t"+wd.getName("it")+"\t"+wd.getName("es")+"\t"+wd.getName("hu"));
		}
	}
}
