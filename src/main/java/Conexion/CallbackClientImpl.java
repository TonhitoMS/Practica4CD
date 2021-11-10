package Conexion;

import java.rmi.*;
import java.rmi.server.*;
import GUI.*;

/**
 * This class implements the remote interface 
 * CallbackClientInterface.
 * @author M. L. Liu
 */

public class CallbackClientImpl extends UnicastRemoteObject
     implements CallbackClientInterface {
    private VentaCliente vc;
  
   public CallbackClientImpl(VentaCliente vc) throws RemoteException {
      super( );
      this.vc = vc;
   }
   
   public CallbackClientImpl() throws RemoteException {
      super( );
      this.vc = null;
   }

   public String notifyMe(String message){
      String returnMessage = "Call back received: " + message;
      System.out.println(returnMessage);
      vc.getTxtAlerta().append(message + "\n");
      return returnMessage;
   }      

}// end CallbackClientImpl class   
