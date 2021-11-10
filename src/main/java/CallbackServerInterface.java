import java.rmi.*;
import java.util.HashMap;

/**
 * This is a remote interface for illustrating RMI 
 * client callback.
 * @author M. L. Liu
 */

public interface CallbackServerInterface extends Remote {

  public String sayHello( )   
    throws java.rmi.RemoteException;

// This remote method allows an object client to 
// register for callback
// @param callbackClientObject is a reference to the
//        object of the client; to be used by the server
//        to make its callbacks.

  public void registerForCallback(
    CallbackClientInterface callbackClientObject
    ) throws java.rmi.RemoteException;

// This remote method allows an object client to 
// cancel its registration for callback

  public void unregisterForCallback(
    CallbackClientInterface callbackClientObject)
    throws java.rmi.RemoteException;
  
  public HashMap getTaboa() throws java.rmi.RemoteException;
  
  public void alertaCompra(String empresa, double umbral, CallbackClientInterface cliente) throws java.rmi.RemoteException;
  
  public void alertaVenta(String empresa, double umbral, CallbackClientInterface cliente) throws java.rmi.RemoteException;
  
}
