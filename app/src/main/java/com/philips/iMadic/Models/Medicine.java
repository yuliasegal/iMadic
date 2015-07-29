package com.philips.iMadic.Models;

import java.io.Serializable;


public class Medicine implements Serializable {

    public String _medicineCode;
    public String _INGREDIENT;
    public String _name;
    public String _RXCUI;

    public Medicine(String medicineCode,String name, String INGREDIENT, String RXCUI){
        this._INGREDIENT = INGREDIENT;
        this._name =name;
        this._RXCUI = RXCUI;
        this._medicineCode = medicineCode;
    }
}
