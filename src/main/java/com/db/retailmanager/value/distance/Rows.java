package com.db.retailmanager.value.distance;

/**
 * Created by Rajesh on 16-May-17.
 */
public class Rows
{
    private Elements[] elements;

    public Elements[] getElements ()
    {
        return elements;
    }

    public void setElements (Elements[] elements)
    {
        this.elements = elements;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [elements = "+elements+"]";
    }
}

