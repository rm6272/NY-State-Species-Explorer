/**
* This class is responsible for opening and readingthe csv file of species and allows users to 
* get information about different species through entering keywords of the species name.
*
* @author Merry Ma
* @version 03/09/2024
*/

package project3;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
public class NYSpecies {
    static SpeciesList myList = new SpeciesList();

    /**
     * This main method checks if file can be opened from the filepath given from the command line.
     * Invokes read_data method to generate a list of species from the csv file into myList
     * Finally, gets input from user for keyword that they want to search for, 
     * and prints out the results of the search. 
     * The method will continue asking the user for inputs until the user enters quit.
     */
    public static void main(String [] args){ 
        if (args.length == 0) {
            System.err.println("Error: the program expects file name as an argument.");
            // If no command line argument specified, terminate the program with an error status
            System.exit(1); 
        }
        //get the filepath from the command line
        String fileName = args[0]; 
        
        try(Scanner input = new Scanner (System.in)){
            //calls read_data method to read data from the file
            read_data(fileName);

            boolean continueSearching = true;

            while (continueSearching) {
                System.out.print("Enter the keyword to search through species names, " +
                                "or \"quit\" to stop: ");
                String keyword = input.next();//get user input for the keyword or quit
            
                if (keyword.equalsIgnoreCase("quit")) {
                    continueSearching = false; // ends while loop. 
                } else {
                    //Gets the list of species that matches the keyword
                    SpeciesList names = myList.getByName(keyword); 
                    if (names == null) {
                        System.out.println("\nNo matching species found.\n");
                        //Prints this if nothing is found
                    } else {
                        System.out.println();
                        //loop through the speciesList and print each species
                        for (Species species : names) {
                            System.out.println(species);
                        }
                    }
                }
            }
        }catch (Exception e){
            System.err.println(e);//catches any exception that may be thrown
        }
    }

    /**
     * reads the data in the given file using a scanner. 
     * reads the file line by line, splitting by comma that is not with quotes according 
     * to regex expression, and extract the necessary information from the information given. 
     * Creates a species object for each line and add it to the list of species.
     * Ignores any line that has null value(s) for the information that the program is looking for. 
     * 
     * @param filename the path of the csv file that the program is trying to read. 
     * @catches FileNotFoundException if the file cannot be found based on the file path. 
     */
    protected static void read_data(String filename) {
        // regex expression for looking for a comma that has 
        //either zero or even number of double quotes.
        final Pattern csvPattern = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        
        try (Scanner doc = new Scanner(new java.io.File(filename))) {
            while (doc.hasNext()) {
                String line = doc.nextLine();
                // Split the line by commas not within quotes
                String[] lineArray = csvPattern.split(line, -1);
    
                // Process values to remove surrounding quotes
                for (int i = 0; i < lineArray.length; i++) {
                    lineArray[i] = lineArray[i].replaceAll("^\"|\"$", "");
                }
                //reading values of each species
                String county = lineArray[0];
                String category = lineArray[1];
                String taxonomicGroup = lineArray[2];
                String taxonomicSubGroup = lineArray[3];
                String scientificName = lineArray[4];
                String commonName = lineArray[5];
                String NYListingStatus = lineArray[7];
    
                // Check for empty values
                if (!county.isEmpty() && !category.isEmpty() && !taxonomicGroup.isEmpty() && 
                        !taxonomicSubGroup.isEmpty() && !scientificName.isEmpty()
                        && !commonName.isEmpty() && !NYListingStatus.isEmpty()) {
                    Species tis = new Species(category, taxonomicGroup, taxonomicSubGroup, 
                                            scientificName, commonName, NYListingStatus);
                    myList.add(tis, county); //adds to the list of species. 
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            System.exit(1);//exit if no file is found. 
        }
    }
}
