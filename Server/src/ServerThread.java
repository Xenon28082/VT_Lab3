import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private static StudentDAO dao = new StudentDAO();

    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;
        // если потоку ввода/вывода приведут к генерированию исключения, оно проброситься дальше
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start(); // вызываем run()
    }

    @Override
    public void run() {
        try {
            String word = "";
            while (!word.equals("stop")) {
                word = in.readLine();
                System.out.println(word);
                switch (word) {
                    case "read": {
                        String students = dao.getStudents().toString();
                        out.write(students + "\n");
                        out.flush();
                        break;
                    }
                    case "write": {
                        Student student = new Student(in.readLine(), in.readLine(), Integer.parseInt(in.readLine()), in.readLine());
                        dao.addNewStudent(student);
                        System.out.println(student);
                        break;
                    }
                    default:
                        continue;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
