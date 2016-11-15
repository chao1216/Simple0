import java.io.*;
import java.util.*;
import java.util.Scanner;

public class fileReader{
  public static void main(String[] args){
		
  simple0 simp = new simple0();
  ArrayList<String> list = new ArrayList<String>();
		
  try {
	  
    File f = new File(args[0]);
    Scanner reader = new Scanner(f);
	  
    while(reader.hasNext()){
      list.add(reader.nextLine());
    }
	  
  } catch (FileNotFoundException ex) {
	  
      System.out.println("cannot locate the file.");
	  
  } catch (NullPointerException ex) {
	  
      System.out.println("failed to connect Scanner to the file");
	  
  }
      String result = simp.instrController(list);
      System.out.println(result);
  }
	
}
