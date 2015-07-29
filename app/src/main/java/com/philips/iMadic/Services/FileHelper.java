package com.philips.iMadic.Services;

import android.content.Context;

import com.philips.iMadic.Models.Medicine;
import com.philips.iMadic.iMadicApp;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by 310210574 on 2015-07-27.
 */
public class FileHelper {

    public static ArrayList<Medicine> ReadFile(String fileName) {
        ArrayList<Medicine> obj = null;
        try {
        FileInputStream fis = iMadicApp.getAppContext().openFileInput(fileName);
        ObjectInputStream is = new ObjectInputStream(fis);
            try {
                obj =  ( ArrayList<Medicine>)is.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            is.close();

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public static void PersistToFile (String fileName, ArrayList<Medicine> data)
    {
        try {
            FileOutputStream fos = iMadicApp.getAppContext().openFileOutput(fileName, Context.MODE_PRIVATE);

            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(data);
            os.close();
            //fos.write(data.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
