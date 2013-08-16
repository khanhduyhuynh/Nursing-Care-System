

/*
 * This class is used to store information about Patients
 *
 *@author Ken Fakamuria
 * Date: 22/08/2011
 * Version 1.0
 */
public class Patient {
    public Patient(String name, String id,int nutrition,int hygiene, int activity, int category, int cHours)
    {
        patientName = name;
        patientId = id;
        nutritionIndex = nutrition;
        hygieneIndex = hygiene;
        activityIndex = activity;
        patientCategory = category;
        careHours = cHours;

    }

    public void setPatientCategory(int cat)
    {
        patientCategory = cat;
    }

    public String getPatientName()
    {
        return patientName;
    }
    public String getPatientId()
    {
        return patientId;
    }

    public int getNutritionIndex()
    {
        return nutritionIndex;
    }
    public int getHygieneIndex()
    {
        return hygieneIndex;
    }
    public int getActivityIndex()
    {
        return activityIndex;
    }
    public int getPatientCategory()
    {
        return patientCategory;
    }
    public int getCareHours()
    {
        return careHours;
    }
    private String patientName;
    private String patientId;
    private int nutritionIndex;
    private int hygieneIndex;
    private int activityIndex;
    private int patientCategory;
    private int careHours;
}
