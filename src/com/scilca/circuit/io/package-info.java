/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * This package contains the API structure for the token input to the 
 * CircuitParser. The TokenIterator class is a significant portion of this 
 * package. It reads over a HTML source from a CharacterReader object accessing 
 * its data indirectly through this. A custom CharacterReader can be built 
 * by extending it, allowing portability of the TokenIterator for multiple 
 * sources.
 */
package com.scilca.circuit.io;
