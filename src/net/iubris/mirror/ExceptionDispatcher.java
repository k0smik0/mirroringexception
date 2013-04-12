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

public class ExceptionDispatcher {

//	public static Method findBestMatchException(Exception throwable, OnExceptionProvided self) {
	public static Method findBestMatchException(Exception throwable, Object self, String methodName) {
//		Class<?> selfClass = self.getClass();
		Class<?> handlerClass = self.getClass();
//    	Class<?> founderClass = Object.class;
	    Class<?> throwableClass = throwable.getClass();
//	    String methodName = "onException";
//System.out.println("throwable is: "+throwableClass.getSimpleName());
	    do {
	    	try {
//System.out.println("trying on: "+handlerClass.getName());
	    		Method method = handlerClass.getDeclaredMethod(methodName, throwableClass);
//System.out.println("found: "+method+" !");
	    		return method;
	    	} catch (NoSuchMethodException e) {
	    		handlerClass = handlerClass.getSuperclass();
//System.out.println("nothing found, trying on superclass: "+handlerClass.getSimpleName());
    			if (handlerClass == Object.class) {
//					try {
//						Method method = selfClass.getDeclaredMethod(methodName, Exception.class);
						Method method = ExceptionDispatcher.findBestMatchException(new Exception(), self, methodName);
//System.out.println("sorry, returning: "+selfClass.getSimpleName()+" "+method.getName());
						return method;
					/*} catch (NoSuchMethodException e1) {
    	    			// this should never happen because "methodName"(Exception) must be present on founder class
    	    			throw new UnknownError("Expected method "+methodName+"("+Exception.class+") not found in "+selfClass.getSimpleName());
					}*/
	    		}
	    	}
	    } while (true);
    }
}
