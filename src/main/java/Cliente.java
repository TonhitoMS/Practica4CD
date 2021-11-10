
import java.util.HashMap;
import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author antonio
 */
public class Cliente {
    private CallbackClientInterface callbackClientObject;
    private HashMap<String, Double> sube;
    private HashMap<String, Double> baixa;

    public Cliente(CallbackClientInterface callbackClientObject, HashMap<String, Double> sube, HashMap<String, Double> baixa) {
        this.callbackClientObject = callbackClientObject;
        this.sube = sube;
        this.baixa = baixa;
    }

    public Cliente(CallbackClientInterface callbackClientObject) {
        this.callbackClientObject = callbackClientObject;
        this.sube = new HashMap();
        this.baixa = new HashMap();
    }
    
    
    

    public CallbackClientInterface getCallbackClientObject() {
        return callbackClientObject;
    }

    public void setCallbackClientObject(CallbackClientInterface callbackClientObject) {
        this.callbackClientObject = callbackClientObject;
    }

    public HashMap<String, Double> getSube() {
        return sube;
    }

    public void setSube(HashMap<String, Double> sube) {
        this.sube = sube;
    }

    public HashMap<String, Double> getBaixa() {
        return baixa;
    }

    public void setBaixa(HashMap<String, Double> baixa) {
        this.baixa = baixa;
    }

    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.callbackClientObject, other.callbackClientObject)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
