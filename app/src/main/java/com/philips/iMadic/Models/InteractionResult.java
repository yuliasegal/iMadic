package com.philips.iMadic.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310176672 on 29/07/2015.
 */
public class InteractionResult {

    private List<Interaction> _interactions  = new ArrayList<>();

    public InteractionResult(){

    }

    public List<Interaction> GetInteractions(){
        return this._interactions;
    }

    public void AddInteraction(Interaction interaction){
        this._interactions.add(interaction);
    }

    public void RemoveInteraction(Interaction interaction){
        this._interactions.remove(interaction);
    }

    public void IsInteractionExists(Interaction interaction){
        this._interactions.contains(interaction);
    }

    public void SetResults(List<Interaction> list)
    {
        this._interactions = list;
    }

    public List<Interaction> GetRelevantInteractions(String code){
        List<Interaction> relevantInteractions = new ArrayList<>();

// Check for relevant data
        for (Interaction interaction : relevantInteractions){

            if(interaction.MedicineBCode.equals(code) || interaction.MedicineACode.equals(code)){
                relevantInteractions.add(interaction);
            }
        }

        return relevantInteractions;
    }

}
