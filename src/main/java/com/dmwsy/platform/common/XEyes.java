package com.dmwsy.platform.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class XEyes {
    protected ACAutomaton ac = new ACAutomaton();

    void fresh() {
        this.ac = new ACAutomaton();
    }

    public void indexSensitiveFromFile(File paramFile) {
        try {
            FileReader localFileReader = new FileReader(paramFile);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader);
            int i = 0;
            String str;
            while ((str = localBufferedReader.readLine()) != null)
                if (str.length() != 0) {
                    SensitiveInfo localSensitiveInfo = new SensitiveInfo(i, str);
                    i++;
                    this.ac.addBranch(localSensitiveInfo);
                }
            this.ac.addBud();
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
    }

    public List findSensitive(String paramString) {
        String str = paramString.toLowerCase();
        str = StringMachine.insertBlank(str);
        return this.ac.findBranch(str);
    }

    public void saveIndex(String paramString) {
        try {
            FileOutputStream localFileOutputStream = new FileOutputStream(new File(paramString));
            ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localFileOutputStream);
            localObjectOutputStream.writeObject(this.ac);
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
    }

    public void loadIndex(String paramString) {
        try {
            FileInputStream localFileInputStream = new FileInputStream(new File(paramString));
            ObjectInputStream localObjectInputStream = new ObjectInputStream(localFileInputStream);
            this.ac = ((ACAutomaton) localObjectInputStream.readObject());
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        } catch (ClassNotFoundException localClassNotFoundException) {
            localClassNotFoundException.printStackTrace();
        }
    }
}