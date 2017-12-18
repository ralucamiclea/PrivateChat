package internalstorage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import uiadapters.Message;

/**
 * Created by raluca.miclea on 12/18/2017.
 */

public class MessagesInternalStorage {

    public MessagesInternalStorage(){
        //
    }

    /*Save msgs in binary files in INTERNAL STORAGE*/
    public static void saveMsgToFile(Context context, Message msg) {

        String fileName = "jmsg";

        Gson gson = new Gson();
        List<Message> messages = MessagesInternalStorage.loadMsgFromFile(context);
        if (messages != null) {
            messages.add(msg);

            String json = gson.toJson(messages);
            Log.i("GSON", "Msg converted to JSON : " + json);

            try {
                FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                fos.write(json.getBytes());
                fos.close();
            } catch (IOException e) {
                Toast.makeText(context, "Cannot access local data to store msg.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*Delete msg from binary files in INTERNAL STORAGE*/
    public static void deleteMsgFromFile(Context context, Message msg) {
        Gson gson = new Gson();
        try {
            List<Message> msgs = new ArrayList<>();

            for (File file : context.getFilesDir().listFiles()) {
                if (file.getName().equals("jmsg")) {
                    FileInputStream fis = context.openFileInput(file.getName());
                    byte buffer[] = new byte[fis.available()];
                    fis.read(buffer);
                    String m = new String(buffer);
                    Log.i("GSON", "READ MSG : " + m);
                    msgs = gson.fromJson(m, new TypeToken<List<Message>>() {
                    }.getType());
                    fis.close();
                }
            }
            if(msgs != null){
                if(msgs.contains(msg)){
                    msgs.remove(msg);
                    Log.v("Delete Message", "Deleted message.");
                }
            }
        } catch (IOException e) {
            Toast.makeText(context, "Cannot access local data to load msgs.", Toast.LENGTH_SHORT).show();
        }
    }

    /*Load msgs from binary files in INTERNAL STORAGE*/
    public static List<Message> loadMsgFromFile(Context context) {
        Gson gson = new Gson();
        try {
            List<Message> msgs = new ArrayList<>();

            for (File file : context.getFilesDir().listFiles()) {
                if (file.getName().equals("jmsg")) {
                    FileInputStream fis = context.openFileInput(file.getName());
                    byte buffer[] = new byte[fis.available()];
                    fis.read(buffer);
                    String m = new String(buffer);
                    Log.i("GSON", "READ MSG : " + m);
                    msgs = gson.fromJson(m, new TypeToken<List<Message>>() {
                    }.getType());
                    fis.close();
                }
            }
            return msgs;
        } catch (IOException e) {
            Toast.makeText(context, "Cannot access local data to load msgs.", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
