package integerMatrix;

public class IntegerMatrix {
    private int rowNum;
    private int colNum;
    private int[] linearData;

    public IntegerMatrix(int rowNum, int colNum, int[] linearData) {
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.linearData = linearData;
    }

    public IntegerMatrix(){
    }

    @Override
    public String toString() {
        String result = "";

        for(int i=0; i < linearData.length; i++){
            if((i+1)%colNum==0){
                result+=linearData[i];

                if(i!=linearData.length-1){
                    result+=";";
                }

            }else{
                result+=linearData[i]+",";
            }
        }
        return result;
    }

    public static void main (String [] args){
        int[] linearData = {1,2,3,4,5,6};
        IntegerMatrix test = new IntegerMatrix(2,3,linearData);
        System.out.println(test.toString());
    }
}
