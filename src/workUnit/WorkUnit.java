package workUnit;

public class WorkUnit {

	private int id;
	private long totalCombinations;
	private long perUnitCombinations;
	private long remainingCombinations;

	public WorkUnit(int id, int threadAmount, int nonceSize) {
		this.id = id;
		this.totalCombinations = (long) Math.pow(256, nonceSize);
		this.perUnitCombinations = (long) Math.floor(this.getTotalCombinations() / threadAmount);
		this.remainingCombinations = this.getTotalCombinations() % threadAmount;
	}

	private int getId() {
		return this.id;
	}

	private long getTotalCombinations() {
		return this.totalCombinations;
	}

	private long getPerUnitCombinations() {
		return this.perUnitCombinations;
	}

	private long getRemainingCombinations() {
		return this.remainingCombinations;
	}

	public long minRange() {
		return this.shift(this.getId()) + this.getId() * this.getPerUnitCombinations();
	}

	public long maxRange() {
		return this.shift(this.getId() + 1) + (this.getId() + 1) * this.getPerUnitCombinations() - 1;
	}

	// El desplazamiento (shift) reparte las combinaciones que sobran entre los WorkUnit con ids mas bajos,
	// y asegura que los WorkUnit con id más alto tengan en cuenta el desplazamiento que esto genera.
	private long shift(int id) {
		return Math.min(id, this.getRemainingCombinations());
	}
}
