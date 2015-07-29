package com.philips.iMadic.Services;

import com.philips.iMadic.Models.Medicine;

import java.util.ArrayList;

/**
 * Created by 310210574 on 2015-07-27.
 */
public class MyMedicationsService {

    private String MyMedicationsDB = "MyMedicationsDB.txt";
    ArrayList<Medicine> _allMyList;

    public MyMedicationsService()
    {
        ReadData();
        if (_allMyList == null)
            _allMyList = new ArrayList<Medicine>();
    }

    public ArrayList<Medicine> GetAllMyMedicines()
    {
        return _allMyList;
    }

    public void AddMedication(Medicine m)
    {
        _allMyList.add(m);
        PersistData();
    }

    public void DeleteMedication (Medicine m)
    {
        _allMyList.remove(m);
        PersistData();
    }


    private void ReadData(){
      _allMyList = FileHelper.ReadFile(MyMedicationsDB);

    }

    private void PersistData()
    {
        FileHelper.PersistToFile(MyMedicationsDB, _allMyList);
    }




}
