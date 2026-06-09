package kr.ondoc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessStream implements Runnable{
    
    private static final String EUC_KR = "euc-kr";
    private String name;
    private InputStream inputStream;
    private Thread thread;
    
    public ProcessStream(String name, InputStream inputStream) {
        this.name = name;
        this.inputStream = inputStream;
    }
    
    public void start() {
        this.thread = new Thread(this);
        thread.start();
    }
 
    @Override
    public void run() {
        InputStreamReader isr = null;
        BufferedReader br = null;
        String lines = "";
        
        try {
            isr = new InputStreamReader(inputStream, EUC_KR);
            br = new BufferedReader(isr);
            
            while(true) {
                String line = br.readLine();
                
                if(line == null)
                    break;
                
                lines += line;
                lines += "\n";
            }
            
            if(!lines.equals("")) {
                System.out.println("[" + name + "]");
                System.out.println(lines);
            }
        }// try
        
        catch(IOException e) {
            e.printStackTrace();
        }// catch
        
        finally{
            try {
                if(br != null)
                    br.close();
                
                if(inputStream != null)
                    inputStream.close();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }// finally
        
    }
}

