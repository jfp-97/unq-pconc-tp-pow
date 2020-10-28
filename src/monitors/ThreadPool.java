package monitors;


import searchResult.FailedSearch;
import searchResult.SearchResult;
import threads.PowWorker;

public class ThreadPool {
	private Buffer buffer;
	private int threadAmount;
	private int nonceSize;
	private byte[] prefix;
	private int difficulty;
	private PowWorker[] powWorkers;
	private boolean hasResult;
	private SearchResult searchResult;
	private int failedThreads;

	public ThreadPool(Buffer buffer, int threadAmount, int nonceSize, byte[] prefix, int difficulty) {
		this.buffer = buffer;
		this.threadAmount = threadAmount;
		this.nonceSize = nonceSize;
		this.prefix = prefix;
		this.difficulty = difficulty;
		this.hasResult = false;
	}

	private int getThreadAmount() {
		return this.threadAmount;
	}

	private SearchResult getSearchResult() {
		return this.searchResult;
	}

	private void setSearchResult(SearchResult searchResult) {
		this.searchResult = searchResult;
	}

	private PowWorker[] getPowWorkers() {
		return this.powWorkers;
	}

	private void setPowWorkers(PowWorker[] powWorkers) {
		this.powWorkers = powWorkers;
	}

	private Buffer getBuffer() {
		return this.buffer;
	}

	private int getNonceSize() {
		return this.nonceSize;
	}

	private byte[] getPrefix() {
		return this.prefix;
	}

	private int getDifficulty() {
		return this.difficulty;
	}

	private boolean getHasResult() {
		return this.hasResult;
	}

	private void setHasResult(boolean hasResult) {
		this.hasResult = hasResult;
	}

	private int getFailedThreads() {
		return this.failedThreads;
	}

	private void setFailedThreads(int failedThreads) {
		this.failedThreads = failedThreads;
	}

	public void searchNonce() {
		this.setPowWorkers(new PowWorker[this.getThreadAmount()]);

		for (int i = 0; i < this.getThreadAmount(); i++) {
			this.getPowWorkers()[i] = new PowWorker(
					this.getBuffer(), this.getNonceSize(), this.getPrefix(), this.getDifficulty(), this);
			this.getPowWorkers()[i].start();
		}
	}

	public synchronized void incFailedThreads() {
		this.setFailedThreads(this.getFailedThreads() + 1);

		if (this.getFailedThreads() == this.getThreadAmount())
			this.write(new FailedSearch());
	}

	public synchronized void write(SearchResult searchResult) {
		if (this.getHasResult()) return;

		for (PowWorker powWorker : this.getPowWorkers()) {
			powWorker.stopWorking();
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
		notifyAll();
		return this.getSearchResult();
	}
}
