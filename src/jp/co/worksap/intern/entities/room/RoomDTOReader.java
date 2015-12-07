package jp.co.worksap.intern.entities.room;

import java.io.IOException;

import jp.co.worksap.intern.constants.Constants;
import jp.co.worksap.intern.entities.AbstractDTOReader;
import jp.co.worksap.intern.entities.region.RegionMstDTO;
import jp.co.worksap.intern.entities.region.RegionMstDTOReader;

public class RoomDTOReader extends AbstractDTOReader<RoomDTO>{

	private static final int COLUMN_INDEX_ROOM_ID = 0;
	private static final int COLUMN_INDEX_ROOM_NUMBER = 1;
	private static final int COLUMN_INDEX_ROOM_TYPE_ID = 2;
	private static final int COLUMN_INDEX_STATE = 3;
	private static final int COLUMN_INDEX_CUSTOMER_ID = 4;

	private String fileAddress = Constants.DEFAULT_CSV_FOLDER
			+ "ROOM_MST.csv";
	
	public RoomDTOReader()throws IOException {

		super(RoomDTOReader.class.getName());
		super.init();
	}

	@Override
	protected RoomDTO convertArrayToDTO(String[] value) throws IOException {
		// TODO Auto-generated method stub
		Integer roomId = Integer.valueOf(value[COLUMN_INDEX_ROOM_ID]);
		String roomNumber = value[COLUMN_INDEX_ROOM_NUMBER];
		Integer roomTypeId = Integer.valueOf(value[COLUMN_INDEX_ROOM_TYPE_ID]);
		Integer customerId = Integer.valueOf(value[COLUMN_INDEX_CUSTOMER_ID]);
		String state = value[COLUMN_INDEX_STATE];

		RoomDTO dto = new RoomDTO(roomNumber, roomTypeId, state, customerId);
		return dto;
	}

	@Override
	protected String getFileArress() {
		// TODO Auto-generated method stub
		return fileAddress;
	}
	
}
