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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uiadapters.Message;

/**
 * Created by raluca.miclea on 12/18/2017.
 */

public class MessagesInternalStorage implements Serializable {

    public MessagesInternalStorage(){
        //
    }

    /*Save msgs in binary files in INTERNAL STORAGE*/
    public static void saveMsgToFile(Context context, Message msg) {
        String fileName = "msg" + msg.getMsgId();

        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(msg);
            os.close();
            fos.close();
        } catch (IOException e) {
            Toast.makeText(context, "Cannot access local data to store msg.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /*Delete msg from binary files in INTERNAL STORAGE*/
    public static void deleteMsgFromFile(Context context, Message msg) {
        try {
            List<Message> msgs = new ArrayList<>();
            boolean flag = false;

            for (File file : context.getFilesDir().listFiles()) {
                if(file.getName().startsWith("msg")) {
                    FileInputStream fis = context.openFileInput(file.getName());
                    ObjectInputStream is = new ObjectInputStream(fis);
                    Message m = (Message) is.readObject();
                    is.close();
                    fis.close();
                    if (m.equals(msg)) {
                        Log.v("DeleteStorage", "Try to delete msg.");
                        flag = file.delete();
                        break;
                    }
                }
            }
            if(flag == false) {
                Log.v("DeleteStorage", "Cannot delete msg.");
                Toast.makeText(context, "Cannot delete msg.", Toast.LENGTH_SHORT).show();
            }
            Log.v("DeleteStorage", "Deleted msg.");
        } catch (IOException e) {
            Toast.makeText(context, "Cannot access local data to load msg.", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*Load msgs from binary files in INTERNAL STORAGE*/
    public static List<Message> loadMsgFromFile(Context context) {
        try {
            List<Message> msgs = new ArrayList<>();

            for (File file : context.getFilesDir().listFiles()) {
                if(file.getName().startsWith("msg")) {
                    FileInputStream fis = context.openFileInput(file.getName());
                    ObjectInputStream is = new ObjectInputStream(fis);
                    Message msg = (Message) is.readObject();
                    msgs.add(msg);
                    is.close();
                    fis.close();
                }
            }

            return msgs;
        } catch (IOException e) {
            Toast.makeText(context, "Cannot access local data to load msgs.", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
