package com.db.retailmanager.value.geocode;

public class GeoCodeResponse
{
    private Results[] results;

    private String status;

    public Results[] getResults ()
    {
        return results;
    }

    public void setResults (Results[] results)
    {
        this.results = results;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        StringBuilder resultBuilder = new StringBuilder();
        for (int i=0;i<results.length;i++)
            resultBuilder.append(results[i].toString());
        return "ClassPojo [results = "+resultBuilder.toString()+", status = "+status+"]";
    }
}
