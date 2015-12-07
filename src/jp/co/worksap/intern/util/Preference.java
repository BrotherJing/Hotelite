package jp.co.worksap.intern.util;

import jp.co.worksap.intern.constants.Constants;
import jp.co.worksap.intern.entities.AbstractDTOReader;
import jp.co.worksap.intern.entities.PreferenceItem;
import jp.co.worksap.intern.writer.ResultWriterImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Preference extends AbstractDTOReader<PreferenceItem> {

    public static final int KEY_INT_FIRST_USE = 0;

    private static final int COLUMN_INDEX_KEY = 0;
    private static final int COLUMN_INDEX_VALUE = 1;

    private String fileAddress = Constants.DEFAULT_CSV_FOLDER
            + "PREFERENCE.csv";

    private List<PreferenceItem> preferenceItemList = null;

    public Preference() throws IOException{
        super(Preference.class.getName());
        super.init();
        preferenceItemList = getValues();
    }

    public void set(int key,String value){
        preferenceItemList.get(key).setValue(value);
    }

    public PreferenceItem get(int key){
        return preferenceItemList.get(key);
    }

    public void save(){
        List<String[]> strings = new ArrayList<String[]>();
        strings.add(new String[]{"key","value"});
        for(PreferenceItem item:preferenceItemList){
            strings.add(new String[]{item.getKey(),item.getValue()});
        }
        new ResultWriterImpl(fileAddress).writeResult(strings);
    }

    @Override
    protected PreferenceItem convertArrayToDTO(String[] value) throws IOException {
        return new PreferenceItem(value[COLUMN_INDEX_KEY],value[COLUMN_INDEX_VALUE]);
    }

    @Override
    protected String getFileArress() {
        return fileAddress;
    }
}
