package com.example.android.iak3_bproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by adesm on 09/12/17.
 */

public class Patungan implements Serializable {
    private String name;
    private int numParticipants;
//    private int price;
    private int total;
    private int remain;
    private int modulo;
    private int onePersonPay;
    private boolean isFinished = false;
    private Calendar dueDate;
    private ArrayList<Person> persons;

    public Patungan(String name, int numParticipants, int price) {
        this.name = name;
        this.numParticipants = numParticipants;
//        this.price = price;
        this.onePersonPay = price;
        persons = new ArrayList<Person>();
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void addPerson(String name, int payment){
        Person person = new Person(name,payment);
        persons.add(person);

    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOnePersonPay() {
        return onePersonPay;
    }

    public void setOnePersonPay(int onePersonPay) {
        this.onePersonPay = onePersonPay;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public int getModulo() {
        return modulo;
    }

    public void setModulo(int modulo) {
        this.modulo = modulo;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumParticipants() {
        return numParticipants;
    }

    public void setNumParticipants(int numParticipants) {
        this.numParticipants = numParticipants;
    }

//    public int getPrice() {
//        return price;
//    }

//    public void setPrice(int price) {
//        this.price = price;
//    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }
}
