package searchResult;

import util.ByteArrayUtil;

public class FoundNonce extends SearchResult {
	private byte[] nonceResult;

	public FoundNonce(byte[] nonceResult) {
		this.nonceResult = nonceResult;
	}

	@Override
	public void printResult() {
		System.out.println("Nonce: " + ByteArrayUtil.byteArrayAsString(nonceResult));
		System.out.println("Hashed nonce: " + ByteArrayUtil.byteArrayAsString(
				ByteArrayUtil.hash(nonceResult)));
	}
}
