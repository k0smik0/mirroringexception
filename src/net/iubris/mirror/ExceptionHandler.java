/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * ExceptionHandler.java is part of 'MirroringException'.
 * 
 * 'MirroringException' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'MirroringException' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'MirroringException' ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.mirror;

import java.lang.reflect.Method;

public class ExceptionHandler {

	public static Method findBestMatchException(Exception throwable, OnExceptionProvided self) {
    	Class<?> handlerClass = self.getClass();
    	Class<?> founderClass = OnExceptionProvided.class;
	    Class<?> throwableClass = throwable.getClass();
//System.out.println("throwable is: "+throwableClass.getSimpleName());
	    do {
	    	try {
//System.out.println("trying on: "+handlerClass.getName());
	    		Method method = handlerClass.getDeclaredMethod("onException", throwableClass);
//System.out.println("found: "+method+" !");
	    		return method;
	    	} catch (NoSuchMethodException e) {
	    		handlerClass = handlerClass.getSuperclass();
//System.out.println("nothing found, trying on superclass: "+handlerClass.getSimpleName()); 	    		
    			if (handlerClass == founderClass) {
					try {
						Method method = founderClass.getDeclaredMethod("onException", Exception.class);
//System.out.println("sorry, calling: "+selfClass.getSimpleName()+".onException(Exception e)");
//System.out.println("sorry, calling: "+method.getName()+"(Exception e)");
						return method;
					} catch (NoSuchMethodException e1) {
    	    			// this should never happen because onException(Exception) must be present for an instance
    	    			// of ExceptionHandler	    				
    	    			throw new UnknownError("Expected method onException("+throwable.getClass()+") not found");
					}
	    		}
	    	}
	    } while (true);
    }
}
