package searchResult;

public class Failure extends SearchResult {

	@Override
	public void printResult() {
		System.out.println("The search failed to deliver a result");
	}
}
