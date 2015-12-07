package jp.co.worksap.intern.entities.room;

public enum RoomType {
	SINGLE, SEMIDOUBLE, DOUBLE;

	public String toString() {
		switch (this) {
		case SINGLE:
			return "SINGLE";
		case SEMIDOUBLE:
			return "SEMIDOUBLE";
		case DOUBLE:
			return "DOUBLE";
		default:
			return ""; 
		}
	}

	public static RoomType valueOfString(String src) {
		String raw = src.toLowerCase();
		if (raw.equals("double")) {
			return DOUBLE;
		}

		if (raw.equals("semidouble")) {
			return SEMIDOUBLE;
		}

		if (raw.equals("single")) {
			return SINGLE;
		}

		throw new IllegalArgumentException("Unknown Room Type : " + raw);
	}
}
