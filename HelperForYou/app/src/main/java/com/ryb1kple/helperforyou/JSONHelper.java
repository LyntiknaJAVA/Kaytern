package com.ryb1kple.helperforyou;

import android.content.Context;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

class JSONHelper {

    private static final String FILE_NAME = "d.json";

    static boolean exportToJSON(Context context, List<Deal> dataList) {

        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setDeal(dataList);
        String jsonString = gson.toJson(dataItems);

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    static List<Deal> importFromJSON(Context context) {

        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = context.openFileInput(FILE_NAME);
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            return  dataItems.getDeal();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private static class DataItems {
        private List<Deal> deal;

        List<Deal> getDeal() {
            return deal;
        }
        void setDeal(List<Deal> deal) {
            this.deal = deal;
        }
    }

}