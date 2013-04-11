#MirroringException

This is a polymorphic exception dispatcher/delegate.  

The scenario:  
* you are using some framework which provides "onException(Exception e) method (like roboguice for roboasynctask),  
* the method throwing not Exception but any of subclasses (CustomException1, CustomException2, etc)  
  
So, you want framework calls "onException(CustomException1 ce1)" or "onException(CustomException2 ce2)", not "onException(Exception e)", but it does not.  
  
Then, you can write some logic within "onException(Exception e)" in order to handle righter exceptions (a kind of "if e instanceof BlablaException),   
or:
  
you can use this dispatcher:  

<pre>
void onException(Exception e) { // framework method
	ExceptionHandler.findBestMatchException(e, this);
}

void onException(CustomException1 ce1) {
	// your code
}
void onException(CustomException2 ce2) {
	// your code
}
</pre>

where "this" is the calling object itself.  
Be careful that the method searched is "onException", so you have to overload method!
    
Honoring OOP, the second parameter must be an istance of "OnExceptionProvided",   
just an super-interface containing "onException(Exception e)":  
so, your class must be implement this interface, or you have to cast when invoke the method, like 
<pre>
ExceptionHandler.findBestMatchException(e, (OnExceptionProvided)this);
</pre>
(if you can, let's your class implements the interface   
but if you can't/don't want, i.e. if you don't want make as public the protected "onException", you have to cast)  

