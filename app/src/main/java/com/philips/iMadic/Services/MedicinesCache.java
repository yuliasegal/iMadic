package com.philips.iMadic.Services;

import android.content.Context;

import com.philips.iMadic.Models.Medicine;
import com.philips.iMadic.iMadicApp;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.util.HashMap;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

/**
 * Created by 310176672 on 27/07/2015.
 */
public class MedicinesCache {

    private  static MedicinesCache instance = null;

    private MedicinesCache(){
    }

    public static MedicinesCache getInstance(){
        if (instance == null) {
            instance = new MedicinesCache();
            instance.LoadMedicines(iMadicApp.getAppContext());
        }

        return instance;
    }

    // File
    //private String _fileName = "MedicineDB";
    private String _fileName = "Drugs.xml";

    // Key - code . Value - Medicine obj
    HashMap<String,Medicine> MedicinesMap = new HashMap<String,Medicine>();

    public boolean IsMedicineExists(String code){
        return (this.MedicinesMap.containsKey(code));
    }
    public Medicine GetByCode(String code){
        return (this.MedicinesMap.get(code));
    }

    public void LoadMedicines(Context context) {

        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            String expression = "//Row";
            InputSource inputSource = new InputSource(context.getAssets().open(_fileName));

            // All rows
            NodeList nodes = (NodeList) xpath.evaluate(expression, inputSource, XPathConstants.NODESET);

            // For every row
            for (int i = 0; i < nodes.getLength(); i++) {
                Node sura = nodes.item(i);

                NamedNodeMap n =  sura.getAttributes();
                n.getNamedItem("");

                Medicine m = new Medicine(n.getNamedItem("Barcode").getTextContent(),
                                          n.getNamedItem("Name").getTextContent(),
                                          n.getNamedItem("INGREDIENT").getTextContent(),
                                          n.getNamedItem("RXCUI").getTextContent());


                this.MedicinesMap.put(n.getNamedItem("Barcode").getTextContent(), m);
            }
        } catch (Exception e) {

            String m = e.getMessage();
        }
    }
}
