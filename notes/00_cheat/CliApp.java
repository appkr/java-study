import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CliApp {

    private BufferedReader reader;

    public static void main(String[] args) throws IOException {
        CliApp app = new CliApp();
        app.setReader(new BufferedReader(new InputStreamReader(System.in)));
        app.run();
    }

    private void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    private void run() {
        while (true) {
            String command = readCommand();
            handleCommand(command);
        }
    }

    private String readCommand() {
        String command;
        try {
            System.out.println("명령어를 입력하세요:");
            command = reader.readLine();
        } catch (IOException ex) {
            throw new RuntimeException("입출력 오류가 발생했습니다");
        }

        return command.trim();
    }

    private void handleCommand(String command) {
        if (command.equals("exit")) {
            System.out.println("앱을 종료합니다.");
            System.exit(0);
        }

        String[] part = command.split(" ");
        if (part.length < 2) {
            throw new RuntimeException("...");
        }

        // Do something with command

        System.out.println("something");
    }
}
