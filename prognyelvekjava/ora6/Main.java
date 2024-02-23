package ora6;


import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

// import java.io.*;


class Main
{
    public static void main(String[] args)
    {
        File input = new File(args[0]);
        File output = new File(args[1]);

        BufferedReader br = null;
        PrintWriter pw = null;

        try
        {
            br = new BufferedReader(new FileReader(input));
            pw = new PrintWriter(output);

            String line;
            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split(",");

                boolean hk = false;
                for (String num : parts)
                {
                    if(Integer.parseInt(num) < 0 && hk){
                        hk = false;
                        break;
                    }

                    if(Integer.parseInt(num) < 0 && !hk){
                        hk = true;
                    }
                }

                for(String elem : parts){
                    pw.print(elem+", ");
                }

                if(hk){
                    pw.print("HK\n");
                }else{
                    pw.print("NOT HK\n");
                }
            }

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Unable to access file " + args[0]);
        }
        catch (IOException e)
        {
            System.out.println("IO error");
        }finally{
            try{
                br.close();
            }catch(Exception exc){
                exc.printStackTrace();
            }
            pw.close();
        }
    }
}
