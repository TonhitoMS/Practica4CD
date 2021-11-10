import java.io.*;
import java.rmi.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents the object client for a
 * distributed object of class CallbackServerImpl, 
 * which implements the remote interface 
 * CallbackServerInterface.  It also accepts callback
 * from the server.
 * 
 * 
 * 
 * @author M. L. Liu
 */

public class CallbackClient {

  public static void main(String args[]) throws Exception {
      int i = 0;
    try {
      int RMIPort;         
      String hostName;
      InputStreamReader is = 
        new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(is);
      System.out.println(
        "Enter the RMIRegistry host namer:");
      hostName = br.readLine();
      System.out.println(
        "Enter the RMIregistry port number:");
      String portNum = br.readLine();
      RMIPort = Integer.parseInt(portNum); 
      System.out.println(
        "Enter how many seconds to stay registered:");
      String timeDuration = br.readLine();
      int time = Integer.parseInt(timeDuration);
      String registryURL = 
        "rmi://localhost:" + portNum + "/callback";  
      // find the remote object and cast it to an 
      //   interface object
      CallbackServerInterface h =
        (CallbackServerInterface)Naming.lookup(registryURL);
      System.out.println("Lookup completed " );
      System.out.println("Server said " + h.sayHello());
      HashMap<String, Double> taboa = h.getTaboa();
      System.out.println("*****************IBEX35*****************\n" + h.getTaboa());
      CallbackClientInterface callbackObj = 
        new CallbackClientImpl();
      // register for callback
      h.registerForCallback(callbackObj);
      System.out.println("Registered for callback.");
      System.out.println(taboa.get("IBERDROLA"));
      h.alertaVenta("IBERDROLA", taboa.get("IBERDROLA") - 1.0, callbackObj);
      h.alertaCompra("VISCOFAN", taboa.get("VISCOFAN") + 1.0, callbackObj);
      h.alertaVenta("NATURGY", taboa.get("NATURGY") - 1.0, callbackObj);
      try {
        Thread.sleep(time * 1000);
      }
      catch (InterruptedException ex){ // sleep over
      }
      System.out.println("while");
      //locawhile(i == 0){}
      h.unregisterForCallback(callbackObj);
      System.out.println("Unregistered for callback.");
    } // end try 
    catch (Exception e) {
      System.out.println(
        "Exception in CallbackClient: " + e);
    } // end catch

//        Document doc = Jsoup.connect("https://www.bolsamadrid.es/esp/aspx/Mercados/Precios.aspx?indice=ESI100000000&punto=indice").get();
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
//        
//        Timer timer = new Timer();
//        
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("ola que tal");
//            }
//        }, 2*1000, 2*1000);
        
  }
        

    private static void log(String msg, String... vals) {
        System.out.println(String.format(msg, vals));
    }
}







































