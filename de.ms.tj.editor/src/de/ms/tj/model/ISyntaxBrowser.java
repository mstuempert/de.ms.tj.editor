package de.ms.tj.model;

public interface ISyntaxBrowser {
	
	ICommand getCommand(String name);
	
	ICommand[] getCommands();
	
	ISyntaxContainer getSyntaxRoot();
	
	ISyntaxElement getElementById(String id);

}
