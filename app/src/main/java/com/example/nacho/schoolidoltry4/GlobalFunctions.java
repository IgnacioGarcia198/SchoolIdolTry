package com.example.nacho.schoolidoltry4;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by nacho on 20/03/2015.
 */
public class GlobalFunctions {
    private static final String PARENT_DIR = "..";
    public static boolean isExternalStorage(File file) {
        String route = file.toString();
        String[] folders = route.split("/");
        for(int i = 0; i < folders.length; i ++) {
            if(folders[i].equals("sdcard")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isExternalStorage(String route) {
        String[] folders = route.split("/");
        for(int i = 0; i < folders.length; i ++) {
            if(folders[i].equals("sdcard")) {
                return true;
            }
        }
        return false;
    }

    /**
     * This function as it is is only meant to write files in the private directory of the application (by the moment)
     * @param text
     * @param file
     */
    public static void writeToFile(String text, File file) {
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.write(text);
            myOutWriter.close();
            fOut.close();
/*            Toast.makeText(getBaseContext(),
                    "Done writing SD 'mysdfile.txt'",
                    Toast.LENGTH_SHORT).show();*/
        }
        catch (Exception e) {
            /*Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();*/
        }
    }

    public static String readFromFile(File file) {
        // write on SD card file data in the text box
        StringBuilder aBuffer = new StringBuilder();
        try {
            FileInputStream fIn = new FileInputStream(file);
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(fIn));
            String aDataRow = "";
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer.append(aDataRow);
                aBuffer.append("\n");
            }
            myReader.close();
            fIn.close();

        }
        catch (Exception e) {

        }
        return aBuffer.toString();
    }

    /**
     * Returns a line from a file, and if the file does not exists, then returns the default text given
     * @param file The file from which we want to read the line
     * @param defaultText The text we want the function to return in case of the file not exists
     * @return
     */
    public static String readLineValuesWithDefault(File file, String defaultText) {
        String ret = "";

        try {
            FileInputStream fIn = new FileInputStream(file);

            if ( fIn != null ) {
                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(fIn));
                ret = bufferedReader.readLine();
                bufferedReader.close();
                fIn.close();
            }
        }
        catch (FileNotFoundException e) {
            //Log.e("login activity", "File not found: " + e.toString());
            writeToFile(defaultText, file);
            ret = defaultText;
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }

    public static String pullString(String key, Context context) {
        // ARCHIVO DE CLAVES: ACCEDER
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // LEER

        return sharedPref.getString(key, "");
    }

    public static void pushString (String key, String value, Context context) {
        // ARCHIVO DE CLAVES: ACCEDER

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // EDITAR
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static boolean pullBoolean(String key, Context context) {
        // ARCHIVO DE CLAVES: ACCEDER

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // LEER

        return sharedPref.getBoolean(key, false);
    }

    public static void pushBoolean (String key, Boolean value, Context context) {
        // ARCHIVO DE CLAVES: ACCEDER

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // EDITAR
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }

    public static int pullInt(String key, Context context) {
        // ARCHIVO DE CLAVES: ACCEDER

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // LEER

        return sharedPref.getInt(key, 0);
    }

    public static void pushInt (String key, int value, Context context) {
        // ARCHIVO DE CLAVES: ACCEDER

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // EDITAR
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    /**
     * Gets a list of files and directories inside the directory given as parameter, showing only the files with the given extension.
     * It is meant for any directory inside our phone, but IT DOES NOT WORK PROPERLY so far.
     * @param directory The directory of which we want to show the list
     * @param extension The extension of the files we want to show in the list
     * @return An arrayList of File objects whith subdirectories and files of the given extension
     */
    public static ArrayList<File> getFiles(String directory, String extension, Context context) {
        ArrayList<File> fileList = new ArrayList<File>();
        File dir;
        if((directory != null) || !directory.equals("")) {
            dir = new File(directory);
            if(!dir.exists()) {
                dir = context.getFilesDir();
            }
        }
        else {
            dir = context.getFilesDir();
        }
        if (dir.getParentFile() != null) fileList.add(new File(PARENT_DIR));
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    fileList.add(listFile[i]);
                }
                else {
                    if (listFile[i].getName().endsWith(extension)) {
                        fileList.add(listFile[i]);
                        //String s = listFile[i].toString();
                        //fileList.add(s.substring(0, s.lastIndexOf("\\.")));
                    }
                }
            }
        }
        return fileList;
    }

    /**
     * Gets a list of files and directories inside the directory given as parameter, showing only the files with the given extension.
     * It is meant for any directory inside our phone, but IT DOES NOT WORK PROPERLY so far.
     * @param dir The directory of which we want to show the list
     * @param extension The extension of the files we want to show in the list
     * @return An arrayList of File objects whith subdirectories and files of the given extension
     */
    public static ArrayList<File> getFiles(File dir, String extension, Context context) {
        //Toast.makeText(context, "raiz: " + dir, Toast.LENGTH_SHORT).show();
        ArrayList<File> fileList = new ArrayList<File>();
        if(dir == null) {
            dir = context.getFilesDir();
        }
        if (dir.getParentFile() != null) fileList.add(new File(PARENT_DIR));
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    fileList.add(listFile[i]);
                }
                else {
                    if (listFile[i].getName().endsWith(extension)) {
                        //Toast.makeText(context, "archivo: " + listFile[i], Toast.LENGTH_SHORT).show();
                        fileList.add(listFile[i]);
                        //String s = listFile[i].toString();
                        //fileList.add(s.substring(0, s.lastIndexOf("\\.")));
                    }
                }
            }
        }
        return fileList;
    }

    /**
     * A local version of getFiles, gets only files inside the given directory and nothing else, but IT DOES NOT WORK PROPERLY so far.
     * @param directory The directory of which we want to show the list
     * @param extension The extension of the files we want to show in the list
     * @return An arrayList of File objects whith subdirectories and files of the given extension
     */
    public static ArrayList<File> getLocalFiles(String directory, String extension, Context context) {
        ArrayList<File> fileList = new ArrayList<File>();
        File dir;
        if((directory != null) || !directory.equals("")) {
            dir = new File(directory);
            if(!dir.exists()) {
                dir = context.getFilesDir();
            }
            else {
                if (!dir.equals(context.getFilesDir()) && (dir.getParentFile() != null)) fileList.add(new File(PARENT_DIR));
            }
        }
        else {
            dir = context.getFilesDir();
        }
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    fileList.add(listFile[i]);
                }
                else {
                    if (listFile[i].getName().endsWith(extension)) {
                        fileList.add(listFile[i]);
                        //String s = listFile[i].toString();
                        //fileList.add(s.substring(0, s.lastIndexOf("\\.")));
                    }
                }
            }
        }
        return fileList;
    }

    /**
     * A version of getFiles, which only retrieve their names, but IT DOES NOT WORK PROPERLY so far.
     * @param directory The directory of which we want to show the list
     * @param extension The extension of the files we want to show in the list
     * @return An arrayList of File objects whith subdirectories and files of the given extension
     */
    public static ArrayList<String> getFileNames(String directory, String extension, Context context) {
        ArrayList<String> fileList = new ArrayList<String>();
        File dir;
        if((directory != null) || !directory.equals("")) {
            dir = new File(directory);
            if(!dir.exists()) {
                dir = context.getFilesDir();
            }
        }
        else {
            dir = context.getFilesDir();
        }
        if (dir.getParentFile() != null) fileList.add(PARENT_DIR);
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    fileList.add(listFile[i].getName());
                }
                else {
                    if (listFile[i].getName().endsWith(extension)) {
                        fileList.add(listFile[i].getName());
                        //String s = listFile[i].toString();
                        //fileList.add(s.substring(0, s.lastIndexOf("\\.")));
                    }
                }
            }
        }
        return fileList;
    }

    /*private void loadFileList(File path) {
        this.currentPath = path;
        List<String> r = new ArrayList<String>();
        if (path.exists()) {
            if (path.getParentFile() != null) r.add(PARENT_DIR);
            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    File sel = new File(dir, filename);
                    if (!sel.canRead()) return false;
                    if (selectDirectoryOption) return sel.isDirectory();
                    else {
                        boolean endsWith = fileEndsWith != null ? filename.toLowerCase().endsWith(fileEndsWith) : true;
                        return endsWith || sel.isDirectory();
                    }
                }
            };
            String[] fileList1 = path.list(filter);
            for (String file : fileList1) {
                r.add(file);
            }
        }
        fileList = (String[]) r.toArray(new String[]{});
    }*/

    /*private File getChosenFile(String fileChosen) {
        if (fileChosen.equals(PARENT_DIR)) return currentPath.getParentFile();
        else return new File(currentPath, fileChosen);
    }*/

}
