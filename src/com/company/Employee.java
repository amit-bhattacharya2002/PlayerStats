package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class Employee {

    static HashMap<String, List<Double>> employee = new HashMap<>();

    public static void main(String[] args) throws IOException {

        double empNum;
        double annSalary;
        String name="";
        boolean flag=true;
        Scanner sc = new Scanner(System.in);

        transferData();

        while(!name.equals("ex")){

            System.out.println("Enter the name of the employee, Type ex to exit .. ");
            name=sc.nextLine();

            if(name.equals("ex")){
                break;
            }

            System.out.println("Enter the employee number..");
            empNum=sc.nextDouble();
            sc.nextLine();
            while(flag){
                try{
                    System.out.println("Enter annual salary..");
                    annSalary=sc.nextDouble();
                    flag=false;
                    employee.put(name, new ArrayList<>(Arrays.asList(empNum, annSalary)));
                    sc.nextLine();
                } catch (InputMismatchException e){
                    System.out.println("Please enter a numeric value!!");
                    sc.nextLine();
                }
            }
        }

        display();
        saveFile();
        System.out.println("The data is saved in the text file...");

    }

    public static void transferData(){

        File tempfile = new File("MyEmployeeList.txt");
        boolean exists=tempfile.exists();

        String input[];

        try{
            if(exists){
                Scanner sc1 = new Scanner(tempfile);
                while(sc1.hasNextLine()){
                    sc1.nextLine();
                    String data = sc1.nextLine();
                    input = data.split("\\s");
                    double empnum = Double.parseDouble(input[1]);
                    double empsalary = Double.parseDouble(input[2]);
                    employee.put(input[0], new ArrayList<>(Arrays.asList(empnum, empsalary)));

                }
            }
        } catch (FileNotFoundException e){
            System.out.println("The file is not found");
        }

    }

    public static void display(){
        int count= employee.size();
        System.out.println("There are currently "+count+" employees in your records.");

        for(Map.Entry<String, List<Double>> entry : Employee.employee.entrySet()) {
            
            System.out.println("Employee name : "+entry.getKey());
            System.out.println("Employee number : "+employee.get(entry.getKey()).get(0));
            System.out.println("Employee salary : "+employee.get(entry.getKey()).get(1));

        }
    }

    public static void saveFile() throws IOException{

        Path file = Paths.get("MyEmployeeList.txt");
        OutputStream output=new BufferedOutputStream(Files.newOutputStream(file,APPEND, CREATE));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
        String data;
        try{
            for(Map.Entry<String, List<Double>> entry : Employee.employee.entrySet()){
                data = entry.getKey()+" "+employee.get(entry.getKey()).get(0)+" "+employee.get(entry.getKey()).get(1)+"\n";
                writer.write(data);
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        writer.close();
    }
}
