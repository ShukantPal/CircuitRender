/*
 * Copyright (C) 2016 Sukant Pal
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.scilca.circuit.sparks;

import java.util.Stack;
import com.scilca.circuit.io.*;

/**
 * The ShortCircuit class handles the errors (or the short circuit(s)) that 
 * occur while the engine is in running process. This class is a singleton class 
 * and its only instance can be accessed through the getInstance object.
 * @author Sukant Pal
 */
public final class ShortCircuit {
    
    private ShortCircuit() {
    } // Singleton Class
    
    private static class ShortCircuitInstance{
        public static ShortCircuit INSTANCE = new ShortCircuit();
    }
    
    public static ShortCircuit getInstance() {
       return ShortCircuitInstance.INSTANCE;
    }
    
    public static class LoggerEntry{
        public final String Msg;
        public final ERR_SEVERITY errLevel;
        public final RuntimeException initExc;
        
        LoggerEntry(String Msg, ERR_SEVERITY errLevel, RuntimeException initExc){
            this.Msg = Msg;
            this.errLevel = errLevel;
            this.initExc = initExc;
        }
        
        @Override
        public String toString(){
            return "Log Msg=\"" + Msg + "\" errLevel=\""
                    + errLevel + "\" initExc=\"" + initExc + "\"";
        }
    }
    
    /**
     * The ERR_SEVERITY enumeration is used by the ShortCircuit 
     * to log the severity of the error that happens in the CircuitHTML 
     * Engine 
     */
    public static enum ERR_SEVERITY{
        /**
         * This error state means that the error's severity was 
         * low and was handled automatically.
         */
        LOW, 
        
        /**
         * This error severity means that the error's severity 
         * consumed CPU time and delayed the parsing process 
         * visibly.
         */
        INTERMEDIATE, 
        
        /**
         * This error severity means that the parser may have 
         * had to go back and look up for stuff before moving 
         * on because of a bad formatting of a input.
         */
        HIGH, 
        
        /**
         * This error severity means that a process may have 
         * been killed because of a un-handled exception.
         */
        SEVERE, 
        
        /**
         * This error severity means that the main-thread of 
         * the engine was disturbed due to a chain of delays.
         */
        CRITICAL, 
        
        /**
         * This means the application may have been terminated due 
         * to crashing
         */
        CRASH;
        
        public static ERR_SEVERITY getEnum(String enumName){
            switch(enumName.toUpperCase()){
                case "LOW": return LOW;
                case "INTERMEDIATE": return INTERMEDIATE;
                case "HIGH": return HIGH;
                case "SEVERE": return SEVERE;
                case "CRITICAL": return CRITICAL;
                case "CRASH": return CRASH;
                default: return null;
            }
        }
    }
    
    Stack<LoggerEntry> allLogs = new Stack<>();
    
    /**
     * The Log( , , ) method adds a log to the list of logging messages. This 
     * method takes a String message, Error Level, and RuntimeException to 
     * save to the list.
     * @param msg - The message about the error that happened
     * @param errLevel - The error level of the exception
     * @param e - The RuntimeException (as the error is Runtime Exception only) 
     * that has happened, if it exists (may be null also).
     */
    public void Log(String msg, ERR_SEVERITY errLevel
                        ,RuntimeException e){
        allLogs.add(new LoggerEntry(msg, errLevel, e));
    }
    
    /**
     * This writes all the logs to the output source
     * @param outputAccess 
     */
    public void Log(StringWriter outputAccess){
        for(LoggerEntry loggerEntry : allLogs){
            outputAccess.write(loggerEntry);
            outputAccess.newLine();
        }
    }
    
    /**
     * This method writes all the logs in the buffer. Afterwards, 
     * they are removed from the buffer.
     * @param outputAccess 
     */
    public void Flush(StringWriter outputAccess){
        Log(outputAccess);
        allLogs = new Stack<>();
    }
    
    
    public Object getLogs(ERR_SEVERITY errLevel){
        return null;
    }
    
}
