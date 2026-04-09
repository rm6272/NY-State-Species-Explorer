/**
* This species class represents a particular species with information about its category,
* taaxonomic group, taxonomic subgroup, scientific name, common name, NY Listing status, 
* and a list of counties that the species is present in. 
* This class overrides the equals and toString methods. 
* @author Merry Ma
* @version 03/09/2024
*/

package project3;

import java.util.ArrayList;

public class Species implements Comparable<Species>{
    private String category;
    private String taxonomicGroup;
    private String taxonomicSubGroup;
    private String scientificName;
    private String commonName;
    private String NYListingStatus;
    private ArrayList <String> counties = new ArrayList<>();

    public Species (
        String category, String taxonomicGroup, String taxonomicSubGroup, 
        String scientificName, String commonName, String NYListingStatus){

        //if any of the data fields entered is null, throws exception
        if (category == null || taxonomicGroup == null || taxonomicSubGroup == null ||
        scientificName == null || commonName == null || NYListingStatus == null) {
            throw new IllegalArgumentException("Field(s) cannot be null");}
        
        //set the data fields accordingly
        this.category = category;
        this.taxonomicGroup = taxonomicGroup;
        this.taxonomicSubGroup = taxonomicSubGroup;
        this.scientificName = scientificName;
        this.commonName = commonName;
        this.NYListingStatus = NYListingStatus;
    }

    //getter methods for getting each data fields of this species
    
    /**
     * Gets the value of the common name of the species.
     * 
     * @return The common name of the species.
     */
    public String getCommonName(){
        return this.commonName;
    }

    /**
     * Gets the value of the scientific name of the species.
     * 
     * @return The scientific name of the species.
     */
    public String getScientificName(){
        return this.scientificName;
    }

    /**
     * Gets the value of the taxonomic group of the species.
     * 
     * @return The taxonomic group of the species.
     */
    public String getTaxonomicGroup(){
        return this.taxonomicGroup;
    }
    
    /**
     * Gets the value of the taxonomic subgroup of the species.
     * 
     * @return The taxonomic subgroup of the species.
     */
    public String getTaxonomicSubGroup(){
        return this.taxonomicSubGroup;
    }

    /**
     * Gets the value of the NY listing status of the species.
     * 
     * @return The NY listing status of the species.
     */
    public String getNYListingStatus(){
        return this.NYListingStatus;
    }

    /**
     * Gets the value of the category of the species.
     * 
     * @return The category of the species.
     */
    public String getCategory(){
        return this.category;
    }

    /**
     * Gets the list of values of the counties that the species is in.
     * 
     * @return The list of values of the counties that the species is in.
     */
    public ArrayList<String> getCounties() {
        return this.counties;
    }

    /**
     * checks if county is already in the list of counties that this species is present in. 
     * 
     * @param county the name of the county that we want to check. 
     * @return true if the list already contains this county, false otherwise. 
     * @throws IllegalArgumentException if county is null or empty. 
     */
    protected boolean isPresentIn(String county){

        //if county is null or empty, throws an exception if so
        if (county == null|| county == ""){
            throw new IllegalArgumentException("county cannot be null");
        }

        //check if the list of counties of the species already contains the county specified. 
        //if yes, return true, else return false.
        if (this.counties.contains (county))
            return true;
        else 
            return false;
    }

    /**
     * adds county to the list of county that this specific species is present in. 
     * Will only add if the county is not already in the list of counties. 
     * 
     * @param county the name of the county that we want to add
     * @return false if the county is already in the list and we did not add, true if we successfully 
     * added the county to the list. 
     * @throws IllegalArgumentException if county is null or empty. 
     */
    protected boolean addCounty (String county){

        //check if county is null or empty, throws an exception if so
        if (county == null|| county == ""){
            throw new IllegalArgumentException("county cannot be null");
        }
        //If county is already present, do nothing and return false. else, add county to the 
        //counties list and return true.
        if (isPresentIn(county)){
            return false;
        }else{
            this.counties.add(county);
            return true;
        }
    }


    /**
     * overrides the equals method in the object class. 
     * determines equality based on whether each string fields of the species are equal. 
     * 
     * @param obj the Species object that we want to compare with
     * @return true if all of the string fields of the species and the 
     * species being compared to are the same. False if the obejct being passed is not
     * an instance of the Species class, or if any of the string data fields is not equal. 
     * 
     */
    public boolean equals(Object obj){

        //check if the object passed is an instance of species. If not, return false.
        if (!(obj instanceof Species))
            return false;
    
        //cast the object to Species to perform further operations.
        Species obj2 =(Species) obj;

        //checks if common name, scientific name, taxonomic group and subgroup and category 
        //are the same (case-insensitive)
        if (this.getCommonName().equalsIgnoreCase(obj2.getCommonName())
        && this.getScientificName().equalsIgnoreCase(obj2.getScientificName())
        &&this.getTaxonomicGroup().equalsIgnoreCase(obj2.getTaxonomicGroup())
        &&this.getTaxonomicSubGroup().equalsIgnoreCase(obj2.getTaxonomicSubGroup())
        &&this.getCategory().equalsIgnoreCase(obj2.getCategory())){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Overrides the toString method from the object class. 
     * describes the species including its common name, scientific name, taxonomic group, 
     * taxonomic subgroup, NY listing status, and number of counties that it is present in. 
     * 
     * @return a string describing the species. 
     */
    public String toString(){
        //get the number of counties in the list of counties corresponding with this species
        int num = this.counties.size();

        //if there is no counties in which it is present, return null, else, return the string 
        //describing different information. 
        if (num == 0)
            return null;
        return getCommonName() + " (" + getScientificName() + ")\n" + getTaxonomicGroup() + ", " 
            + getTaxonomicSubGroup() + "\n" + getNYListingStatus() + "\nPresent in " + num + 
            " / 62 countries\n";
    }

    /**
     * implements the compareTo method
     * compares the strings of common name in the species. if the common names are equal, which
     * is unlikely, it will compare the scientific names. 
     * 
     * @param species the species that we want to compare with. 
     * @return a negative integer, zero, or a positive integer as this Species object
     * is less than, equal to, or greater than the specified Species object.
     */
    public int compareTo(Species species){

        //compare the strings of common name of the species, case-insensitive
        int commonNameComparison = this.commonName.compareToIgnoreCase(species.getCommonName());
        
        //if common names are the same, compare the scientific names, case-insensitive
        if (commonNameComparison == 0) {
            return this.scientificName.compareToIgnoreCase(species.getScientificName());
        }
        
        return commonNameComparison;
    }
}
