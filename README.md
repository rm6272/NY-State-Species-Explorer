# NY State Species Explorer (Java)

## Overview

This project is a console-based Java application that processes a New York State biodiversity dataset and allows users to search for species by keyword. It demonstrates custom data structure implementation and efficient data handling.

## Features

* Parses a CSV dataset of species information
* Case-insensitive search by common or scientific name
* Displays species details including taxonomy and conservation status
* Tracks number of counties where each species is present
* Handles invalid data and input errors gracefully

## Data Structure

* Custom **sorted doubly linked list** (`SpeciesList`)
* Manual node management with `prev` and `next` pointers
* Custom **Iterator** and **ListIterator** implementations for traversal

## Core Classes

* `Species`: stores species data and county information
* `SpeciesList`: manages sorted storage of species
* `NYSpecies`: main program (file parsing + user interaction)

## How to Run

```
javac *.java
java NYSpecies <input_file>
```

Example:

```
java NYSpecies biodiversity.csv
```

## Usage

* Enter a keyword to search for species
* Matching results are printed with formatted details
* Enter `quit` to exit

## Example Output

```
Gray Treefrog (Hyla versicolor)
Amphibians, Frogs and Toads
Game with open season
present in 10 / 62 counties
```

## Technologies

* Java
* File I/O and CSV parsing
* Custom data structures and iterators

## Notes

* Data is read once and stored in memory for efficient querying
* Does not use built-in linked list implementations
* Focuses on data structures, parsing, and object-oriented design
