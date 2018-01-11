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

import auth.User;

/**
 * Created by raluca.miclea on 1/11/2018.
 */

public class CurrentUserInternalStorage {

    public CurrentUserInternalStorage(){
        //
    }

    /*Save loggeduser in binary files in INTERNAL STORAGE*/
    public static void saveUserToFile(Context context, User user) {
        String fileName = "loggeduser";

        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(user);
            os.close();
            fos.close();
        } catch (IOException e) {
            Toast.makeText(context, "Cannot access local data to store logged user.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /*Delete logged user from binary files in INTERNAL STORAGE*/
    public static void deleteUserFromFile(Context context, User user) {
        try {
            boolean flag = false;
            String username = user.getUsername();
            for (File file : context.getFilesDir().listFiles()) {
                if(file.getName().startsWith("loggeduser")) {
                    FileInputStream fis = context.openFileInput(file.getName());
                    ObjectInputStream is = new ObjectInputStream(fis);
                    String contact = (String) is.readObject();
                    is.close();
                    fis.close();
                    if (username.equals(contact)) {
                        Log.v("DeleteStorage", "Try to delete logged user.");
                        flag = file.delete();
                        break;
                    }
                }
            }
            if(flag == false) {
                Log.v("DeleteStorage", "Cannot delete logged user.");
                Toast.makeText(context, "Cannot delete logged user.", Toast.LENGTH_SHORT).show();
            }
            Log.v("DeleteStorage", "Deleted logged user.");
        } catch (IOException e) {
            Toast.makeText(context, "Cannot access local data to delete logged user.", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*Load contacts from binary files in INTERNAL STORAGE*/
    public static User loadUserFromFile(Context context) {
        try {
            User user = null;

            for (File file : context.getFilesDir().listFiles()) {
                if(file.getName().startsWith("loggeduser")) {
                    FileInputStream fis = context.openFileInput(file.getName());
                    ObjectInputStream is = new ObjectInputStream(fis);
                    user = (User) is.readObject();
                    is.close();
                    fis.close();
                }
            }

            return user;
        } catch (IOException e) {
            Toast.makeText(context, "Cannot access local data to load logged user.", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
