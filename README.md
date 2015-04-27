# Compile from command line:
As all java files are in a package edu.nyu.cs6015, first create a folder edu/nyu/cs6015 and execute the following command.

javac *.java

It will compile all the java files.

# Run from command line:

java edu.nyu.cs6015.main

# Compile TestCases:
javac –cp "path to junit-4.12.jar;path to hamcrest-core-1.3.jar;." edu/nyu/cs6015/test/*.java

[javac -cp "lib/junit-4.12.jar;lib/hamcrest-core-1.3.jar;." edu/nyu/cs6015/test/*.java]

# To run TestCases:
java –cp "path to junit-4.12.jar;path to hamcrest-core-1.3.jar;." org.junit.runner.JUnitCore edu.nyu.cs6015.test.PassengerTest 

[java -cp "lib/junit-4.12.jar;lib/hamcrest-core-1.3.jar;." org.junit.runner.JUnitCore edu.nyu.cs6015.test.PassengerTest]
