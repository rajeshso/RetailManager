package com.db.retailmanager.value.distance;

/**
 * Created by Rajesh on 16-May-17.
 */
public class Distance
{
    private String text;

    private int value;

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }

    public int getValue ()
    {
        return value;
    }

    public void setValue (int value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [text = "+text+", value = "+value+"]";
    }
}
