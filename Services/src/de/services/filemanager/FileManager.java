package de.services.filemanager;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import javax.xml.crypto.Data;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class FileManager {
    public boolean saveData(String filePath) {
        try {
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filePath)));
            out.writeObject("hi some data");
            out.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
    }

    public Object loadData(String filePath) {
        Object data;
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(filePath)));
            data = (Object) in.readObject();
            in.close();

            return data;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();

            return null;
        }
    }
}
