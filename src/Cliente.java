import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class Cliente {
    public static void main(String[] args) throws IOException {
        List<String> mensajes = Collections.synchronizedList(new ArrayList());

        Scanner leer = new Scanner(System.in);
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        Socket socket = new Socket("127.0.0.1", 8080);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());

        try {
            /* Enviar usuario */
            System.out.println("Introduce tu nombre: ");
            String nombreUsuario = leer.nextLine();
            oos.writeObject(nombreUsuario);
            /* Obtener el saludo */
            String returnValue = (String) ois.readObject();
            System.out.println(returnValue);

            /* Solicito la lista de mensajes*/
            String textorecogido = (String) ois.readObject();
            System.out.println("Mensajes recogidos ....");
            StringTokenizer nuevosMensajes = new StringTokenizer(textorecogido, "***");

            while (nuevosMensajes.hasMoreTokens()){
                System.out.println(nuevosMensajes.nextToken());
            }


            String texto = "";
            while ( !texto.equals("Good bye") ){

                System.out.println("Introduce una interacción: ");
                texto = leer.nextLine();

                if (texto.equals("bye")) {
                    oos.writeObject(texto);
                    texto = (String) ois.readObject();
                }else if (texto.contains("message:")) {

                    oos.writeObject(texto);

                    String Textonuevo = (String) ois.readObject();
                    StringTokenizer primerosMensajes = new StringTokenizer(Textonuevo, "***");

                    while (primerosMensajes.hasMoreTokens()){
                        System.out.println(primerosMensajes.nextToken());
                    }

                }  else {
                    System.out.println("Error en los datos intorducidos");
                }
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