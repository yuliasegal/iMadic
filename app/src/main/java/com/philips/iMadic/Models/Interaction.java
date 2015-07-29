package com.philips.iMadic.Models;

/**
 * Created by 310176672 on 29/07/2015.
 */
public class Interaction {

    public String MedicineAName;
    public String MedicineBName;
    public String MedicineACode;
    public String MedicineBCode;
    public String InteractionConfilct;

    public Interaction(){

    }

    public Interaction(String medicineACode,String medicineAName,String medicineBCode,String medicineBName,String interactionConfilct){
        this.InteractionConfilct = interactionConfilct;
        this.MedicineACode = medicineACode;
        this.MedicineAName = medicineAName;
        this.MedicineBName = medicineBName;
        this.MedicineBCode = medicineBCode;
    }
}
