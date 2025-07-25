package com.console;

import java.util.Scanner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityTransaction;



public class App 
{
    public static void main( String[] args )
    {   int a=1;
        Scanner sc=new Scanner(System.in);
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("my-persistence-unit");
        student std=new student();


        while(a==1){
            System.out.println("Welcome to my conlsole JPA application..");
            System.out.println("1.Create a record");
            System.out.println("2.Delete record");
            System.out.println("3.Show record");
            System.out.println("4.Update record");
            System.out.println("5.Exit\n");

            System.out.print("Choose your option : ");
            int opt=sc.nextInt();

            switch (opt) {
                case 1:
                    createRecord(emf,std);
                    break;
                case 2:
                     deleteRecord(emf);
                     break;
                case 3:
                    showRecord(emf);
                    break;
                case 4:
                    updateRecord(emf);
                    break;
                case 5:
                    a=0;
                    break;
                default:
                    System.out.println("Enter valid option....");
                    break;
            }

            

        }
        sc.close();
    }

    static void createRecord(EntityManagerFactory emf,student std){
        Scanner sc=new Scanner(System.in);
        int id,mark;
        String name;
        System.out.print("Enter your id : ");
        id = sc.nextInt();

        sc.nextLine(); // Add this line to consume the leftover newline

        System.out.print("Enter your name : ");
        name = sc.nextLine(); // This will now correctly read the full name

        System.out.print("Enter your mark : ");
        mark = sc.nextInt();
        //sc.next();


        //set the user data into the calss
        std.setId(id);
        std.setName(name);
        std.setMark(mark);

        //create a transaction
        EntityManager manager=emf.createEntityManager();
        EntityTransaction tx=null;
        
        try{
            System.out.println("saving...");
            tx=manager.getTransaction();
            tx.begin();
            manager.persist(std);
            tx.commit();
            System.out.println("Details saved sussfully....!");
        }
        catch(Exception e){
            if(tx!=null && tx.isActive()){
                System.out.println("Transaction Not completed");
                e.printStackTrace();
                tx.rollback();

            }
        }
        
        manager.close();
    }


    static void showRecord(EntityManagerFactory emf){
        Scanner sc=new Scanner(System.in);

        System.out.print("Enter your id : ");
        int id=sc.nextInt();

        EntityManager manager=emf.createEntityManager();
        EntityTransaction tx=manager.getTransaction();

        try{
            tx.begin();
            student std=manager.find(student.class, id);
            System.out.println("your data is fetched ...");
            System.out.println(std);
            tx.commit();

        }
        catch(Exception e){
            if(tx.isActive()){
                tx.rollback();
            }
            System.out.println("Sorry, we cannot fetch your data...");

        }

        manager.close();
    }


    static void deleteRecord(EntityManagerFactory emf){
        EntityManager manager=emf.createEntityManager();
        EntityTransaction tx=manager.getTransaction();
        Scanner sc=new Scanner(System.in);

        System.out.print("Enter your id : ");
        int id=sc.nextInt();
        
        try{
            tx.begin();
            student std=manager.find(student.class,id );
            
            if(std!=null){
                manager.remove(std);
                tx.commit();
                System.out.println("your data is successfully removed...");
            }
            else{
                System.out.println("Sorry, i could not fetch yoyr data...");
            }
        }
        catch(Exception e){
            System.out.println("Transaction is not committed...");
        }
    }


    static void updateRecord(EntityManagerFactory emf){
        EntityManager manager=emf.createEntityManager();
        Scanner sc=new Scanner(System.in);
        EntityTransaction tx=manager.getTransaction();
        student std=null;
        System.out.print("Enter your id to update the record : ");
        int id=sc.nextInt();
        try{
            tx.begin();
            std=manager.find(student.class, id);
            if(std!=null){
                System.out.println("Update your data..");
                
                System.out.print("Enter your name : ");
                String name=sc.nextLine();
                sc.nextLine();
                System.out.print("Enter your mark : ");
                int mark=sc.nextInt();

                System.out.println("updating your data...");
                std.setName(name);
                std.setMark(mark);

                manager.merge(std);
                tx.commit();
                System.out.println("your updated detail ...");
                System.out.println(std);


            }
            else
            {
                System.out.println("Sorry, we could not fetch your data...");
            }
            manager.close();

        }
        catch(Exception e){
            System.out.println("It throws an error..");
            System.out.println(e);
        }
    }

}
