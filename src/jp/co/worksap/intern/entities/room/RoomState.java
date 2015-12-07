package jp.co.worksap.intern.entities.room;

public enum RoomState {
	IDLE,RESERVED,OCCUPIED,NOT_AVAILABLE;
	
	private static final String[] desc = {"IDLE","RESERVED","OCCUPIED","NOT AVAILABLE"};
	
	public String toString() {
		return desc[this.ordinal()];
	}

	public static RoomState valueOfString(String src) {
		String raw = src.toUpperCase();
		if (raw.equals(desc[0])) {
			return IDLE;
		}

		if (raw.equals(desc[1])) {
			return RESERVED;
		}
		if (raw.equals(desc[2])) {
			return OCCUPIED;
		}
		if (raw.equals(desc[3])) {
			return NOT_AVAILABLE;
		}

		throw new IllegalArgumentException("Unknown Room Type : " + raw);
	}
}
