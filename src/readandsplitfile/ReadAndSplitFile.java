/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package readandsplitfile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Collator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author miracle
 */
public class ReadAndSplitFile {

    /**
     * @param args the command line arguments here
     */
    public static void main(String[] args) throws IOException {
        List result1 = ReadAndSplitFile.splitMethod("C:\\Users\\miracle\\Desktop\\PbDocuments\\SampleSplitDoc.txt", " ");
        List result2 = ReadAndSplitFile.splitMethod("C:\\Users\\miracle\\Desktop\\PbDocuments\\SplitPipeSymbol.txt", "|");
        for (int i = 0; i < result2.size(); i++) {
            result1.add(result2.get(i));
        }
        List result3 = ReadAndSplitFile.splitMethod("C:\\Users\\miracle\\Desktop\\PbDocuments\\SplitCommaSymbol.txt", ",");
        for (int i = 0; i < result3.size(); i++) {
            result1.add(result3.get(i));
        }
        output1(result1);
       
        output3(result1);
         output2(result1);

    }

    public static List splitMethod(String Path, String regs) {
        List result = null;
        BufferedReader bf = null;
        try {
         
            File f = new File(Path);
            bf = new BufferedReader(new FileReader(f));
            String s = "";
            result = new ArrayList();
            String fName = "";
            String lName = "";
            String gender = "";
            String dob = "";
            String color = "";
            while ((s = bf.readLine()) != null) {
                //System.out.println("ssssssssssss"+s);
                StringTokenizer ss = new StringTokenizer(s, regs);
                //   while (ss.hasMoreTokens()) {
                // System.out.println("--------->" + ss.nextToken());
                lName = (ss.nextToken());
                fName = (ss.nextToken());
                if (regs.equals(" ") || regs.equals("|")) {
                    ss.nextToken();
                }
                String genderC = ss.nextToken().trim();
                //    System.out.println("---gender----"+genderC);
                if (genderC.equals("F")) {
                    gender = ("Female");
                } else if (genderC.equalsIgnoreCase("Female")) {
                    gender = genderC;
                } else {
                    gender = ("Male");
                }
                if (regs.equals(" ")) {
                    dob = (ss.nextToken().replace("-", "/"));
                    color = (ss.nextToken());
                } else if (regs.equals("|") || regs.equals(",")) {
                    color = (ss.nextToken());
                    if (regs.equals("|")) {
                        dob = (ss.nextToken().replace("-", "/"));
                    } else {
                        dob = (ss.nextToken());
                    }
                }
                result.add(lName.trim() + " " + fName.trim() + " " + gender.trim() + " " + dob.trim() + " " + color.trim());
                //}
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadAndSplitFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadAndSplitFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bf.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadAndSplitFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    public static void output1(List result1) throws IOException {
        File file = new File("C:\\Users\\miracle\\Desktop\\PbDocuments\\Output1.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        List females = new ArrayList();
        List males = new ArrayList();
        for (int i = 0; i < result1.size(); i++) {
            if (result1.get(i).toString().contains("Female")) {
                females.add(result1.get(i).toString());
            } else {
                males.add(result1.get(i).toString());
            }
        }
        java.util.Collections.sort(females, Collator.getInstance());
        for (int i = 0; i < females.size(); i++) {
            bw.write(females.get(i).toString().trim() + "\r\n");
        }
        java.util.Collections.sort(males, Collator.getInstance());
        for (int i = 0; i < males.size(); i++) {
            bw.write(males.get(i).toString().trim() + "\r\n");
        }
        bw.close();
    }

    public static void output2(List result1) throws IOException {
        
        File file = new File("C:\\Users\\miracle\\Desktop\\PbDocuments\\Output2.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        List<Date> listDates = new ArrayList<Date>();
        DateFormat dateFormatter = new SimpleDateFormat("mm/dd/yyyy");
        java.util.Collections.sort(result1, Collator.getInstance());
        try {
            for (int i = 0; i < result1.size(); i++) {
                //  System.out.println("date before=========>"+result1.get(i).toString().split(" ")[3]);
                listDates.add(dateFormatter.parse(result1.get(i).toString().split(" ")[3]));
            }

        } catch (ParseException ex) {
            System.err.print(ex);
        }

        // System.out.println("Before sorting: " + listDates);

        Collections.sort(listDates);

        //System.out.println("After sorting: " + listDates);
        DateFormat dateFormatter1 = new SimpleDateFormat("mm/dd/yyyy");

        List dates = new ArrayList();
        for (int i = 0; i < listDates.size(); i++) {
            String fromatDate[] = dateFormatter1.format(listDates.get(i)).split("/");
            String dayff = fromatDate[1].charAt(0) == '0' ? fromatDate[1].substring(1) : fromatDate[1];
           
            String monff = fromatDate[0].charAt(0) == '0' ? fromatDate[0].substring(1) : fromatDate[0];
            dates.add(monff + "/" + dayff + "/" + fromatDate[2]);
          //   System.out.println("date--->" + dates.toString());
        }
        
        for (int i = 0; i < dates.size(); i++) {
            for (int j = 0; j < result1.size(); j++) {
                System.out.println("------"+result1.get(j).toString()+"-------------"+dates.get(i).toString());
                if (result1.get(j).toString().contains(dates.get(i).toString())) {
                    bw.write(result1.get(j).toString() + "\r\n");
                    result1.remove(j);
                    break;
                }
            }
        }

        bw.close();
    }

    public static void output3(List result1) throws IOException {
        File file = new File("C:\\Users\\miracle\\Desktop\\PbDocuments\\Output3.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        java.util.Collections.sort(result1, Collator.getInstance());
        for (int i = result1.size() - 1; i >= 0; i--) {
            bw.write(result1.get(i).toString().trim() + "\r\n");
        }
        bw.close();
    }
}
