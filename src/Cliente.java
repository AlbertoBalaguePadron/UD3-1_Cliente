import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException {
        Scanner leer = new Scanner(System.in);
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        Socket socket = new Socket("127.0.0.1", 8080);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());

        try {
            System.out.println("Introduce tu nombre: ");
            oos.writeObject(leer.nextLine());

            String returnValue = (String) ois.readObject();
            System.out.println(returnValue);

            String texto = "";
            while ( !texto.equals("Good bye") ){
                System.out.println("Introduce una interacción: ");
                oos.writeObject(leer.nextLine());
                texto = (String) ois.readObject();
                System.out.println("El servidor responde => " + texto);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(oos != null) oos.close();
            if (ois != null) ois.close();
            if (socket != null) socket.close();
            System.out.println("Conexion cerrada");
        }
    }
}