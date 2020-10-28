package searchResult;

public class FailedSearch extends SearchResult {

	@Override
	public void printResult() {
		System.out.println("There doesn't exist a nonce that satisfies the specified parameters");
	}
}
