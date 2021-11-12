package Conexion;

import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class implements the remote interface 
 * CallbackServerInterface.
 * @author M. L. Liu
 */

public class CallbackServerImpl extends UnicastRemoteObject
     implements CallbackServerInterface {
    
    private HashMap<String, Double> taboa;
    private ArrayList<Cliente> clientList;


   public CallbackServerImpl() throws RemoteException {
      super( );
     clientList = new ArrayList();
     taboa = new HashMap();
     this.consultarTaboa();
   }

  public String sayHello( )   
    throws java.rmi.RemoteException {
      return("hello");
  }

  public synchronized void registerForCallback(
    CallbackClientInterface callbackClientObject)
    throws java.rmi.RemoteException{
      // store the callback object into the vector
      Cliente c = new Cliente(callbackClientObject);
      if (!(clientList.contains(c))) {
         clientList.add(c);
      System.out.println("Registered new client ");
      doCallbacks();
    } // end if
  }  

// This remote method allows an object client to 
// cancel its registration for callback
// @param id is an ID for the client; to be used by
// the server to uniquely identify the registered client.
  public synchronized void unregisterForCallback(
    CallbackClientInterface callbackClientObject) 
    throws java.rmi.RemoteException{
      Cliente c = new Cliente(callbackClientObject);
//      for(Cliente cl: this.clientList){
//          if(c.equals(cl)){
//              c = cl;
//              break;
//          }
//      }
    if (clientList.remove(c)) {
      System.out.println("Unregistered client ");
    } else {
       System.out.println(
         "unregister: client wasn't registered.");
    }
  } 

  private synchronized void doCallbacks( ) throws java.rmi.RemoteException{
    // make callback to each registered client
    System.out.println(
       "**************************************\n"
        + "Callbacks initiated ---");
    for (int i = 0; i < clientList.size(); i++){
      System.out.println("doing "+ i +"-th callback\n");    
      // convert the vector object to a callback object
      CallbackClientInterface nextClient = 
        (CallbackClientInterface)clientList.get(i).getCallbackClientObject();
      // invoke the callback method
        nextClient.notifyMe("Number of registered clients="
           +  clientList.size());
    }// end for
    System.out.println("********************************\n" +
                       "Server completed callbacks ---");
  } // doCallbacks
  
  public synchronized void consultarTaboa()  throws RemoteException{
      HashMap<String, Double> result = new HashMap();
      CallbackServerImpl csi = this;
//        
//        Timer timer = new Timer();
//        
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
                Document doc = null;
                try{
                    doc = Jsoup.connect("https://www.bolsamadrid.es/esp/aspx/Mercados/Precios.aspx?indice=ESI100000000&punto=indice").get();
                  }catch(IOException e){System.out.println("Excepción: " + e);}

                Element taboa = doc.select("table[id*=Acciones]").get(0);

                    
                Elements filas = taboa.select("tr");
                Elements cols;
                NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
                Number number = 0;
                double d;
                for (int i = 1; i < filas.size(); i++) { 
                    Element fila = filas.get(i);
                    cols = fila.select("td");
                    try{
                        number = format.parse(cols.get(1).text());
                    }catch(ParseException e){System.out.println("Excepción: " + e);}
                    d = number.doubleValue();
                    result.put(cols.get(0).text(), d);
                    //log("%s\n\t%s", cols.get(0).text(), cols.get(1).text());
                }

                SortedSet<String> keys = new TreeSet<>(result.keySet());
                for(String s: keys){
                    //System.out.println(s);
                }
                try {
                    csi.notificarAlertas();
                } catch (RemoteException ex) {
                    Logger.getLogger(CallbackServerImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                csi.taboa=result;
//            }
//        }, 0, 20*1000);
        
        this.taboa = result;
   }
  
  private static void log(String msg, String... vals) {
        System.out.println(String.format(msg, vals));
    }

    @Override
    public HashMap getTaboa() throws RemoteException {
        return this.taboa;
    }

    @Override
    public synchronized void alertaCompra(String empresa, double umbral, CallbackClientInterface cliente) throws RemoteException {
        System.out.println("AlertaCompra");
        Cliente c = new Cliente(cliente);
        for(Cliente cl: this.clientList){
            if(cl.equals(c)){
                c = cl;
                System.out.println("Alerta de compra para a empresa " + empresa + " e o límite " + umbral + " realizada");
                break;
            }
        }
        c.getBaixa().put(empresa, umbral);
    }

    @Override
    public synchronized void alertaVenta(String empresa, double umbral, CallbackClientInterface cliente) throws RemoteException {
        Cliente c = new Cliente(cliente);
        for(Cliente cl: this.clientList){
            if(cl.equals(c)){
                c = cl;
                System.out.println("Alerta de venta para a empresa " + empresa + " e o límite " + umbral + " realizada");
                break;
            }
        }
        c.getSube().put(empresa, umbral);
    }

    
    private synchronized void notificarAlertas() throws RemoteException{
        //alerta de compra
        System.out.println("notificarAlertas");
        for(Cliente c: this.clientList){
//            for(Map.Entry<String, Double> m: c.getBaixa().entrySet()){
//                String empresa = m.getKey();
//                double umbral = m.getValue();
//                if(taboa.get(empresa) < umbral){
//                    CallbackClientInterface nextClient = 
//                        (CallbackClientInterface)c.getCallbackClientObject();
//                    nextClient.notifyMe("Alerta de compra. O valor da empresa " + empresa + " baixou do " + umbral);
//                    c.getBaixa().remove(empresa);
//                }
//            }
                Iterator<Map.Entry<String, Double>> it = c.getBaixa().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Double> m = it.next();
                    String empresa = m.getKey();
                    double umbral = m.getValue();
                    if(taboa.get(empresa) < umbral){
                        CallbackClientInterface nextClient = 
                            (CallbackClientInterface)c.getCallbackClientObject();
                    System.out.println(nextClient.notifyMe("Alerta de compra. O valor da empresa " + empresa + " baixou do " + umbral));
                        it.remove();
                    }
                }
        }
        
        //alerta de venta
        for(Cliente c: this.clientList){
//            for(Map.Entry<String, Double> m: c.getSube().entrySet()){
//                String empresa = m.getKey();
//                double umbral = m.getValue();
//                if(taboa.get(empresa) > umbral){
//                    CallbackClientInterface nextClient = 
//                        (CallbackClientInterface)c.getCallbackClientObject();
//                    nextClient.notifyMe("Alerta de venta. O valor da empresa " + empresa + " subiu do " + umbral);
//                    c.getSube().remove(empresa);
//                }
//            }
                Iterator<Map.Entry<String, Double>> it = c.getSube().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Double> m = it.next();
                    String empresa = m.getKey();
                    double umbral = m.getValue();
                    if(taboa.get(empresa) > umbral){
                        CallbackClientInterface nextClient = 
                            (CallbackClientInterface)c.getCallbackClientObject();
                        System.out.println(nextClient.notifyMe("Alerta de venta. O valor da empresa " + empresa + " subiu do " + umbral));
                        it.remove();
                    }
                }
        }
    }
}// end CallbackServerImpl class   
