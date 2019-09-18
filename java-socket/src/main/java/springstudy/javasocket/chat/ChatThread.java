package springstudy.javasocket.chat;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class ChatThread extends Thread {

    private Socket socket;
    private Map<String, Object> entry;

    private String userName;
    private boolean initialized = false;

    public ChatThread(Socket socket, Map<String, Object> entry) {
        this.socket = socket;
        this.entry = entry;
    }

    public void run() {
        try (
            PrintWriter w = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            if (!initialized) {
                userName = r.readLine();
                synchronized (entry) {
                    entry.put(this.userName, w);
                }
                broadcast("\033[0;32msystem : User '" + userName + "' entered\033[0m");
                initialized = true;
            }

            String line = null;
            while ((line = r.readLine()) != null) {
                if (line.equals("/quit")) {
                    break;
                }

                if (line.indexOf("/to") == 0) {
                    sendPrivateMessage(line);
                } else {
                    broadcast(userName + " : " + line);
                }
            }
        } catch (IOException e) {
            //
        } finally {
            synchronized (entry) {
                entry.remove(userName);
            }

            broadcast("\033[0;32msystem : User '" + userName + "' left the session\033[0m");
        }
    }

    public void sendPrivateMessage(String message) {
        int start = message.indexOf(" ") + 1;
        int end = message.indexOf(" ", start);
        if (end != -1) {
            String to = message.substring(start, end);
            String privateMessage = message.substring(end + 1);

            Object obj = entry.get(to);
            if (obj != null) {
                PrintWriter pw = (PrintWriter) obj;
                pw.println("\033[0;33mPrivate message from '" + userName + "':\033[0m");
                pw.println(privateMessage);
                pw.flush();
            }
        }
    }

    public void broadcast(String message) {
        synchronized (entry) {
            Collection<Object> collection = entry.values();
            Iterator<?> iter = collection.iterator();
            while(iter.hasNext()) {
                PrintWriter pw = (PrintWriter) iter.next();
                pw.println(message);
                pw.flush();
            }
        }
    }
}
