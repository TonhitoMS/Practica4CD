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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 Falta: 
 * varios clientes definindo múltiples alertas
 * interfaz gráfica
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
      CallbackServerImpl exportedObj = 
        new CallbackServerImpl();
      registryURL = 
        "rmi://localhost:" + portNum + "/callback";
      Naming.rebind(registryURL, exportedObj);
      System.out.println("Callback Server ready.");
    }// end try
    catch (Exception re) {
      System.out.println(
        "Exception in HelloServer.main: " + re);
    } // end catch
//          Document doc = Jsoup.connect("https://www.bolsamadrid.es/esp/aspx/Mercados/Precios.aspx?indice=ESI100000000&punto=indice").get();
//        //log(doc.title());
//
//        Element taboa = doc.select("table[id*=Acciones]").get(0);
////        for (Element headline : newsHeadlines) {
////            log("%s\n", headline.attr("tr"));
////        }
//
//        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
//        Number number;
//        double d;
//
//        HashMap<String, Double> valores = new HashMap();
//        Elements filas = taboa.select("tr");
//        Elements cols;
//        for (int i = 1; i < filas.size(); i++) { //first row is the col names so skip it.
//            Element fila = filas.get(i);
//            cols = fila.select("td");
//            number = format.parse(cols.get(1).text());
//            d = number.doubleValue();
//            valores.put(cols.get(0).text(), d);
//            log("%s\n\t%s", cols.get(0).text(), cols.get(1).text());
//        }
//        
//        SortedSet<String> keys = new TreeSet<>(valores.keySet());
//        for(String s: keys){
//            System.out.println(s);
//        }
//        System.out.println("PHARMA MAR:\t" + valores.get("PHARMA MAR"));
        
//        Timer timer = new Timer();
//        
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("ola que tal");
//            }
//        }, 2*1000, 2*1000);
        
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
