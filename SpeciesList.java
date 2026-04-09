/**
 * This species list class keeps a sorted doubly linked list for Species.
 * It also has implementations for Iterator and ListIterator.
 * 
 * @author Merry Ma
 * @version 03/09/2024
 */

package project3;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class SpeciesList implements Iterable<Species> {
    private Node<Species> head = null;//initialize head reference
    private Node<Species> tail = null;//initialize tail reference
    private int size = 0; //initialize size of the list

    public SpeciesList() {
        // Initializes an empty list
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Gets the size of this Species List.
     * @return size.
     */
    public int size() {
        return size;
    }

    private static class Node<T> {
        T data;//node's data
        Node<T> prev;//previous reference
        Node<T> next;//next reference

        public Node(T data) {
            this.data = data;//assign data
            this.prev = null;
            this.next = null;
        }
    }

    /**
     * This methods adds the specified species with its county to the Specieslist. 
     * @param element the element added
     * @param county the county of the element
     * @return true if a new species object was added to this list, false otherwise.
     * @throws IllegalArgumentException of county is empty or null. 
     */
    protected boolean add(Species element, String county) {
        boolean success;
        if (county==""|| county == null)
            throw new IllegalArgumentException("County can't be null");

        success = this.add(element);

        for (Species i: this){
            if (i.equals(element)){
                i.addCounty(county);
                return success;
            }
        }
        return false;
    }
    /**
     * This method adds the specified species to the species list. 
     * if the species is already in the list, it checks for the counties of both and merges. 
     * 
     * @param element Species to be added
     * @return true if a new species object was added to this list, false otherwise.
     */
    protected boolean add(Species element) {
        if (element == null)
            return false;
    
        // Check if the new element is equal to any existing element in the list
        for (Species i : this) {
            if (i.equals(element)) {
                for (String county : element.getCounties()) {
                    //loop through the counties
                    if (!i.getCounties().contains(county)) { 
                        i.getCounties().add(county); // Add the county if it's not already exists.
                    }
                }
                return false; // no new species is added. 
            }
        }
        Node<Species> current = head;
        // Create a new node for the species
        Node<Species> newNode = new Node<>(element);
    
        if (head == null) {
            //if the list is empty
            head = tail = newNode;
        } else {
            //list is not empty
            current = head;
            Node<Species> prev = null;
    
            // Find the correct position to insert the new species
            while (current != null && element.compareTo(current.data) > 0) {
                prev = current;
                current = current.next;
            }
    
            if (prev == null) {
                // Insert at the beginning
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            } else if (current == null) {
                // Insert at the end
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            } else {
                // Insert in the middle
                newNode.next = current;
                newNode.prev = current.prev;
                current.prev.next = newNode;
                current.prev = newNode;
            }
        }
    
        size++;//increase size counter of the list.
        return true;//species successfully added.
    }
    
    /**
     * This method removes a specified element from the specieslist. 
     * it will not remove if the element is no a species or the list is empty or cannot find.
     * @param o the element to remove
     * @return true if successfully removed. false other wise.
     */
    protected boolean remove(Object o){
        if (head == null) {
            return false; // List is empty, nothing to remove
        }
        if (!(o instanceof Species)){
            return false;//Object is not a species
        }

        Node<Species> current = head;
        while (current != null) {
            if (current.data.equals(o)) {
                // Adjusting pointer for previous node
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }
                //Adjusting pointer for next node
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev;
                }
                //the remove process
                current.prev = null;
                current.next = null;
                size--;
                return true; // Element removed successfully
            }
            current = current.next;//advance current if this is not equal
        }

        return false; //cannot find match
    }

    /**
     * Checks if the lists are equal in terms of containing the same element in same order.
     * 
     * @param o object to be compared with
     * @return true if the specified object is equal to this list, false otherwise
     */
    public boolean equals(Object o){
        if (!(o instanceof SpeciesList)){
            return false;//not an instance of species list
        }
        
        if (this == o) {
            return true; //same reference, are equal
        }
    
        // Cast the object to SpeciesList
        SpeciesList otherList = (SpeciesList) o;
    
        // Check if the sizes of the lists are equal
        if (size != otherList.size) {
            return false;
        }
    
        // Iterate over both lists simultaneously and compare each element
        Node<Species> currentThis = head;
        Node<Species> currentOther = otherList.head;
        while (currentThis != null) {
            // If elements are not equal, return false
            if (!currentThis.data.equals(currentOther.data)) {
                return false;
            }
            currentThis = currentThis.next;
            currentOther = currentOther.next;//advances both
        }
    
        return true;//all elements are equal
    }

    /**
     * Checks if the specieslist contains the specified object by using the equals method.
     * @param o object to be checked
     * @return true if the list contains object, false otherwise. 
     */
    protected boolean contains(Object o){
        if (!(o instanceof Species))
            return false;//Not a species
        for (Species i : this){
            if (o.equals(i)){
                return true;//Species found, return true
            }
        }
        return false;//Cannot find the species
    }

    /**
     * Clear method that empties the entire list by directly setting nulls.
     */
    protected void clear(){
        while (head != null) {//while list is not empty
            Node<Species> next = head.next;
            head.prev = null;
            head.next = null;//set previous and next pointers to null
            head = next;
        }
        tail = null;//tail pointer also points to null
        size = 0;//updates size to 0
    }

    /**
     * Gets the index of the object that we want to look for. 
     * @param o object to get the index of.
     * @return the index of the first occurrence of the element, or `-1` if 
     * the element is not in the list.
     */
    protected int indexOf(Object o){
        int index = 0;//sets initial index
        Node<Species> current = head;

        if (!(o instanceof Species))
            return -1;//element is not in list because it is not a species
        
        while (current != null) {
            if (current.data.equals(o)) {
                return index; // Return the index if the element is found
            }
            current = current.next;//advances because we did not find it yet
            index++;
        }
        // Element not found, return -1
        return -1;
    }

    /**
     * Gets the species object at the particular index of this specieslist.
     * 
     * @param index the index of the element that we are looking for.
     * @return the Species at the specified index.
     * @throws IndexOutOfBoundsException if index is smaller than 0 or larger than size of 
     * the list.
     */
    protected Species get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            //index is not valid
        }

        Node<Species> current;
        current = head;
        for (int i = 0; i < index; i++) {
            current = current.next; //goes through the list until the designated index.
        }
        return current.data; //return the species at the index that we stopped at.
    }

    /**
     * String representation of the list according to the formatting guidelines. 
     * overrides the toString method.
     * 
     * @returns a String representation of the specieslist.
     */
    public String toString() {
        String result = "[";
    
        if (size == 0) {
            return "[]";//returns this if list is empty.
        }
    
        Node<Species> current = head;
        while (current != null) {
            //goes through the list and gets the common name and scientific names and 
            //appends to the string. 
            result += current.data.getCommonName() + " ("+ current.data.getScientificName() + ")";
    
            if (current.next != null) {
                result += ", ";
            }
            current = current.next;//Advances to the next species
        }
        result += "]";
        return result; //returns the string representation
    }


    /**
     * Returns a specieslist of species that matches the keyword from common name
     * or scientific name.
     * 
     * @param keyword the keyword to search for
     * @return a list of Species objects that contain the specified keyword in
     * either the common name or the scientific name.
     * @throws IllegalArgumentException if keyword is null or empty.
     */
    protected SpeciesList getByName(String keyword){
        //creates new specieslist for storing matching species
        SpeciesList name_match = new SpeciesList();
        
        //throws exception if keyword is null or empty
        if (keyword == null|| keyword == ""){
            throw new IllegalArgumentException("keyword cannot be null");
        }

        //loop through the species in this list and check if the common name contains the keyword
        for (Species i: this){
            if (i.getCommonName().toLowerCase().contains(keyword.toLowerCase())){
                name_match.add(i);
            //Switches to scientific name if common names are the same
            }else if(i.getScientificName().toLowerCase().contains(keyword.toLowerCase())){
                name_match.add(i);
            }
        }
        
        //if there is nothing in the list, return null. otherwise return the SpeciesList. 
        if (name_match.size() == 0)
            return null;
        else
            return name_match;

    }
    /**
     * An iterator for iterating over the elements in the species list.
     *
     * @return An instance of species iterator which can be used to iterate 
     * through the species in the list.
     */
    public Iterator<Species> iterator() {
        return new SpeciesIterator();
    }

    /**
     * listIterator method for SpeciesListIterator.
     * 
     * @return a new instance of SpeciesListIterator, initialized with the size of the list,
     * to iterate over Species objects.
     */
    public ListIterator<Species> listIterator() {
        return new SpeciesListIterator(size);
    }

    private class SpeciesIterator implements Iterator<Species> {
        private Node<Species> current = head;

        /**
         * Overrides the hasNext method from iterator
         * @returns true if the iteration has more species
         */
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Overrides the next method.
         * @returns The next species in the iteration
         */
        public Species next() {
            if (!hasNext()) {
                throw new NoSuchElementException();//no more elements
            }
            Species species = current.data;//gets the species
            current = current.next;//advances the current
            return species;
        }
    }

    private class SpeciesListIterator implements ListIterator<Species> {
        private Node<Species> current = head;
        private Species lastReturned = null;//helpful variable for storing species element
        private int cursor;
        private int size;
        
        public SpeciesListIterator(int size) {
            this.size = size;//sets the size
        }
        
        /**
         * Overrides the hasNext method from iterator
         * @returns true if the iteration has more species
         */
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Overrides the next method.
         * @returns The next species in the iteration
         */
        public Species next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = current.data; //gets the species
            current = current.next;
            cursor ++; //advances cursor palcement
            return lastReturned; //returns "next" species
        }

        /**
         * Overrides hasprevious method
         * @returns True if cursor is not at first position, meaning that there is a 
         * previous species.
         */
        public boolean hasPrevious() {
            return cursor != 0;
        }
        /**
         * Overrides the previous method.
         * Returns the previous species in the list and moves the cursor position backwards. 
         * @returns the previous species in the list.
         * @throws NoSuchElementException if the iteration has no previous species
         */
        public Species previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException("No previous element in the list");
            }
        
            // check if current is at the back of the list and moves it back to tail if so.
            if (current == null) {
                current = tail;
            } else {
                current = current.prev;//moving normally if it is not at the back.
            }
            
            lastReturned = current.data; //gets the species at current location
            cursor--; // Decrementing the cursor to reflect the move to the previous element
            return lastReturned; //return the previous species.
        }

        /**
         * Gets the nextindex of the current species or the size. 
         * 
         * @return the the index of the element that would be returned by a subsequent call 
         * to next, or list size if the list iterator is at the end of the list
         */
        public int nextIndex() {
            if (!hasNext()) {
                return size; //return list size
            }
            return cursor; 
            // Return the index of the element that would be returned by a subsequent call to next()
        }

        /**
         * Gets the previous index of the current species or -1 if there is nothing.
         * 
         * @return the index of the species that would be returned by a subsequent call 
         * to previous, or -1 if the list iterator is at the beginning of the list.
         */
        public int previousIndex() {
            if (!hasPrevious()) {
                return -1; // Iterator is at the beginning of the list
            }
            return cursor - 1; 
            //The index of the element that would be returned by a subsequent call to previous()
        }

        //Below are methods that do not need to be implemented by the requirements
        //they just simply throws a unsupported operation exception. 
        public void set(Species species) {
            throw new UnsupportedOperationException();
        }

        public void add(Species species) {
            throw new UnsupportedOperationException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}