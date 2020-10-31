package monitors;

import searchResult.Failure;
import searchResult.SearchResult;

public class ResultBuffer {
	private boolean hasResult;
	private SearchResult searchResult;
	private int failures;
	private int maxFailures;

	public ResultBuffer(int maxFailures) {
		this.hasResult = false;
		this.failures = 0;
		this.maxFailures = maxFailures;
	}

	private boolean getHasResult() {
		return this.hasResult;
	}

	private void setHasResult(boolean hasResult) {
		this.hasResult = hasResult;
	}

	private SearchResult getSearchResult() {
		return this.searchResult;
	}

	private void setSearchResult(SearchResult searchResult) {
		this.searchResult = searchResult;
	}

	private int getFailures() {
		return this.failures;
	}

	private void setFailures(int failures) {
		this.failures = failures;
	}

	private int getMaxFailures() {
		return this.maxFailures;
	}

	public synchronized void incFailures() {
		this.setFailures(this.getFailures() + 1);

		if (this.getFailures() == this.getMaxFailures())
			this.write(new Failure());
	}

	public synchronized void write(SearchResult searchResult) {
		while (this.getHasResult()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		this.setSearchResult(searchResult);
		this.setHasResult(true);
		notifyAll();
	}

	public synchronized SearchResult read() {
		while (!(this.getHasResult())) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		SearchResult result = this.getSearchResult();

		this.setFailures(0);
		this.setHasResult(false);
		notifyAll();

		return result;
	}
}
