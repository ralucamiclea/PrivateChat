package internalstorage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

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

        Gson gson = new Gson();
        String json = gson.toJson(user);
        Log.i("GSON", "Logged user converted to JSON : " + json);
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            Toast.makeText(context, "Cannot access local data to store logged user.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /*Delete msg from binary files in INTERNAL STORAGE*/
    public static void deleteUserFile(Context context) {
        for (File file : context.getFilesDir().listFiles()) {
            if (file.getName().startsWith("loggeduser")) {
                boolean deleted = file.delete();
            }
        }
    }

    /*Load contacts from binary files in INTERNAL STORAGE*/
    public static User loadUserFromFile(Context context) {
        Gson gson = new Gson();
        try {
            User user = null;

            for (File file : context.getFilesDir().listFiles()) {
                if(file.getName().startsWith("loggeduser")) {
                    FileInputStream fis = context.openFileInput(file.getName());
                    byte buffer[] = new byte[fis.available()];
                    fis.read(buffer);
                    String m = new String(buffer);
                    Log.i("GSON", "READ Logged user : " + m);
                    user = gson.fromJson(m, User.class);
                    fis.close();
                }
            }

            return user;
        } catch (IOException e) {
            Toast.makeText(context, "Cannot access local data to load logged user.", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
