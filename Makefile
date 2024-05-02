runFDTest: FrontendDeveloperTests.class
	java -jar ../junit5.jar -cp . -c FrontendDeveloperTests

FrontendDeveloperTests.class: FrontendDeveloperTests.java Frontend.class TextUITester.class
	javac -cp .:../junit5.jar FrontendDeveloperTests.java

Frontend.class: Frontend.java FrontendInterface.java FRBackendPlaceholder.class FrShortestPathPlaceholder.class
	javac FrontendInterface.java Frontend.java 

FRBackendPlaceholder.class: FRBackendPlaceholder.java BackendInterface.java GraphADT.java 
	javac FRBackendPlaceholder.java BackendInterface.java GraphADT.java

FrShortestPathPlaceholder.class: FrShortestPathPlaceholder.java ShortestPathSearchInterface.java 
	javac FrShortestPathPlaceholder.java ShortestPathSearchInterface.java
	
BaseGraph.class: BaseGraph.java MapADT.java 
	javac BaseGraph.java MapADT.java 

PlaceholderMap.class: PlaceholderMap.java MapADT.java
	javac PlaceholderMap.java MapADT.java 

TextUITester.class: TextUITester.java
	javac TextUITester.java

runBDTests: BackendDeveloperTests.java
	javac -cp .:../junit5.jar BackendDeveloperTests.java
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests

BackendFiles:
	javac Backend.java
	javac -cp .:../junit5.jar DijkstraGraph.java
	javac ShortestPathSearch.java

runBackend: BackendFiles
	java Backend

clean: 
	rm -f *.class
	rm -f *~
