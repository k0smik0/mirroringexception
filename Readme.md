#MirroringException

This is a polymorphic exception dispatcher.  

The scenario:  
* you are using some framework providing "onException(Exception e) method (like roboguice for roboasynctask) [or similar name]  
* the method throwing not Exception but any of subclasses (CustomException1, CustomException2, etc)  
  
So, you want framework calls "onException(CustomException1 ce1)" or "onException(CustomException2 ce2)", not "onException(Exception e)", but it does not.  
  
Then, you can write some logic within "onException(Exception e)" in order to handle righter exceptions (a kind of "if e instanceof BlablaException),   
or:
  
you can use this dispatcher:  

<pre>
void onException(Exception e) { // framework method
	ExceptionHandler.findBestMatchException(e, this, "onException");
}

void onException(CustomException1 ce1) {
	// your code
}
void onException(CustomException2 ce2) {
	// your code
}
</pre>

where "this" is the calling object itself, and "onException" is polymorphic method you want framework invokes.  
If you don't override it, default method "onException(Exception e)" will be called.  
Obviously, because the third parameter, you could have different name for method you're searching for.
  
  
enjoy it ;D
