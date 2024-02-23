package wildAnimal;

public class WildAnimal {
    enum WildAnimalEnum{
        MONKEY ("bananas", 5),
        ELEPHANT ("raspberries", 30),
        GIRAFFE ("apples", 10),
        RACCOON ("walnuts", 20);

        private String gyumolcs;
        private int adag;

        WildAnimalEnum(String gyumolcs, int adag){
            this.gyumolcs = gyumolcs;
            this.adag = adag;
        }

        static String listAllAnimals(){
            String result = "";
            for(WildAnimalEnum elem : WildAnimalEnum.values()){
                result+=elem.ordinal()+":"+elem.name()+" szeretne enni "+elem.gyumolcs+"-t egy héten.";
            }
            return result;
        }

        @Override
        public String toString(){
            return this.name()+", "+this.adag+", "+this.gyumolcs;
        }

        public static void main (String [] args){
            System.out.println(WildAnimalEnum.ELEPHANT.toString());
            System.out.println(WildAnimalEnum.listAllAnimals());
        }
    }
}
