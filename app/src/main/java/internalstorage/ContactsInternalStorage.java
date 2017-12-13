package internalstorage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raluca.miclea on 12/13/2017.
 */

public class ContactsInternalStorage {

    public ContactsInternalStorage(){
        //
    }

    /*Save contacts in binary files in INTERNAL STORAGE*/
    public static void saveContactsToFile(Context context, String contactUsername) {
        String fileName = contactUsername;

        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(contactUsername);
            os.close();
            fos.close();
        } catch (IOException e) {
            Toast.makeText(context, "Cannot access local data to store contact.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /*Delete contact from binary files in INTERNAL STORAGE*/
    public static void deleteContactFromFile(Context context, String contactUsername) {
        try {
            List<String> contacts = new ArrayList<>();
            boolean flag = false;

            for (File file : context.getFilesDir().listFiles()) {
                FileInputStream fis = context.openFileInput(file.getName());
                ObjectInputStream is = new ObjectInputStream(fis);
                String contact = (String) is.readObject();
                is.close();
                fis.close();
                if(contactUsername.equals(contact)) {
                    Log.v("DeleteStorage", "Try to delete contact.");
                    flag = file.delete();
                    break;
                }
            }
            if(flag == false) {
                Log.v("DeleteStorage", "Cannot delete contact.");
                Toast.makeText(context, "Cannot delete contact.", Toast.LENGTH_SHORT).show();
            }
            Log.v("DeleteStorage", "Deleted contact.");
        } catch (IOException e) {
            Toast.makeText(context, "Cannot access local data to load contact.", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*Load contacts from binary files in INTERNAL STORAGE*/
    public static List<String> loadContactsFromFile(Context context) {
        try {
            List<String> contacts = new ArrayList<>();

            for (File file : context.getFilesDir().listFiles()) {
                FileInputStream fis = context.openFileInput(file.getName());
                ObjectInputStream is = new ObjectInputStream(fis);
                String contact = (String) is.readObject();
                contacts.add(contact);
                is.close();
                fis.close();
            }

            return contacts;
        } catch (IOException e) {
            Toast.makeText(context, "Cannot access local data to load contact.", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
