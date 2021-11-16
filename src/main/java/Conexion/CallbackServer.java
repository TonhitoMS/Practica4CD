package Conexion;

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.SortedSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 Falta: 
 * varios clientes definindo múltiples alertas
 * interfaz gráfica
 * cambiar timertask por thread.sleep
 * *********    comprobar paralelismo
 * 
 */

public class CallbackServer  {
  public static void main(String args[]) throws IOException, ParseException {
    InputStreamReader is = 
      new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(is);
    String portNum, registryURL;
    try{     
      System.out.println(
        "Enter the RMIregistry port number:");
      portNum = (br.readLine()).trim();
      int RMIPortNum = Integer.parseInt(portNum);
      startRegistry(RMIPortNum);
      String ip;
    try(final DatagramSocket socket = new DatagramSocket()){
        socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
        ip = socket.getLocalAddress().getHostAddress();
    }

      CallbackServerImpl exportedObj = 
        new CallbackServerImpl();
        Process proc = Runtime.getRuntime().exec("hostname"); 
        is = new InputStreamReader( proc.getInputStream() );
        br = new BufferedReader(is);
        String host = (br.readLine()).trim();
      registryURL = 
        "rmi://" + ip + ":" + portNum + "/callback";
      Naming.rebind(registryURL, exportedObj);
      System.out.println("Callback Server ready. registry URL: " + registryURL);
      
      Timer timer = new Timer();
//        
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                
                try {
                    exportedObj.consultarTaboa();
                } catch (RemoteException ex) {
                    Logger.getLogger(CallbackServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }, 0, 20*1000);
    }// end try
    catch (Exception re) {
      System.out.println(
        "Exception in HelloServer.main: " + re);
    } // end catch
        
        System.out.println("ola");
        
  }
        

    private static void log(String msg, String... vals) {
        System.out.println(String.format(msg, vals));
    }
  //This method starts a RMI registry on the local host, if
  //it does not already exists at the specified port number.
  private static void startRegistry(int RMIPortNum)
    throws RemoteException{
    try {
      Registry registry = 
        LocateRegistry.getRegistry(RMIPortNum);
      registry.list( );  
        // This call will throw an exception
        // if the registry does not already exist
    }
    catch (RemoteException e) { 
      // No valid registry at that port.
      Registry registry = 
        LocateRegistry.createRegistry(RMIPortNum);
    }
  } // end startRegistry

} // end class
