package org.arquillian.example;

import java.io.PrintStream;

import javax.inject.Named;

/**
 * Un composant pour créer des salutations personnalisées.
 */
//@Named
public class Greeter {
	
    public void greet(PrintStream to, String name) {
        to.println(createGreeting(name));
    }

    
    public String createGreeting(String name) {
        return "Hello, " + name + "!";
    }
}