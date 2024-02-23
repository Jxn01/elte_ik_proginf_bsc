package bead4;

public class Television {
    enum TelevisionShop{
        SAMSUNG(10,7,80), LG(11,8,81), SKYWORTH(12,9,82), SONY(13,10,83), SHARP(14,11,84);
        final private int pcsInStock;
        final private int minTVSize;
        final private int maxTVSize;

        TelevisionShop(int pcsInStock, int minTVSize, int maxTVSize){
            this.pcsInStock = pcsInStock;
            this.minTVSize = minTVSize;
            this.maxTVSize = maxTVSize;
        }

        final static void minsAndMaxes(){
            System.out.println("Available min and max sizes are:");
            for(TelevisionShop elem: TelevisionShop.values()){
                System.out.println("Min: "+elem.minTVSize+" Max: "+elem.maxTVSize);
            }
        }

        final static void availableSizes(TelevisionShop TV){
            System.out.println("TVs from the "+TV.name()+" brand are available from "+TV.minTVSize+" inches to "+TV.maxTVSize+" inches.");
        }

        final static void stock(){
            System.out.println("The available number of TVs on stock, the smallest and largest TVs, grouped by brand:");
            for(TelevisionShop elem: TelevisionShop.values()){
                System.out.println("Brand: "+elem.name()+", Number of TVs in stock: "+elem.pcsInStock+", The smallest TV: "+elem.minTVSize+", The largest TV: "+elem.maxTVSize);
            }
        }
    }
}
