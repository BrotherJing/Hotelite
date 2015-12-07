package jp.co.worksap.intern.entities.room;

import java.io.IOException;

import jp.co.worksap.intern.constants.Constants;
import jp.co.worksap.intern.entities.AbstractDTOReader;
import jp.co.worksap.intern.entities.region.RegionMstDTOReader;

public class RoomTypeDTOReader extends AbstractDTOReader<RoomTypeDTO>{


	private static final int COLUMN_INDEX_ROOM_TYPE_ID = 0;
	private static final int COLUMN_INDEX_ROOM_TYPE = 1;
	private static final int COLUMN_INDEX_PRICE = 2;
	private static final int COLUMN_INDEX_PRICE_UNIT = 3;

	private String fileAddress = Constants.DEFAULT_CSV_FOLDER
			+ "ROOM_TYPE_MST.csv";
	
	public RoomTypeDTOReader() throws IOException{
		super(RoomTypeDTOReader.class.getName());
		super.init();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected RoomTypeDTO convertArrayToDTO(String[] value) throws IOException {
		// TODO Auto-generated method stub
		Integer roomTypeId = Integer.valueOf(value[COLUMN_INDEX_ROOM_TYPE_ID]);
		String roomType = value[COLUMN_INDEX_ROOM_TYPE];
		Double price = Double.valueOf(value[COLUMN_INDEX_PRICE]);
		String unit = value[COLUMN_INDEX_PRICE_UNIT];

		RoomTypeDTO dto = new RoomTypeDTO(roomType,price,unit);
		return dto;
	}

	@Override
	protected String getFileArress() {
		// TODO Auto-generated method stub
		return fileAddress;
	}

}
