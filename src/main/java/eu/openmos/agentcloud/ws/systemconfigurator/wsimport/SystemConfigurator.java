
package eu.openmos.agentcloud.ws.systemconfigurator.wsimport;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import eu.openmos.agentcloud.cloudinterface.AgentStatus;
import eu.openmos.agentcloud.cloudinterface.OrderStatus;
import eu.openmos.agentcloud.data.CyberPhysicalAgentDescription;
import eu.openmos.agentcloud.data.Order;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SystemConfigurator", targetNamespace = "http://cloudinterface.agentcloud.openmos.eu/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SystemConfigurator {


    /**
     * 
     * @param newOrder
     * @return
     *     returns eu.openmos.agentcloud.cloudinterface.OrderStatus
     */
    @WebMethod
    @WebResult(name = "orderStatus", targetNamespace = "")
    @RequestWrapper(localName = "acceptNewOrder", targetNamespace = "http://cloudinterface.agentcloud.openmos.eu/", className = "eu.openmos.agentcloud.ws.systemconfigurator.wsimport.AcceptNewOrder")
    @ResponseWrapper(localName = "acceptNewOrderResponse", targetNamespace = "http://cloudinterface.agentcloud.openmos.eu/", className = "eu.openmos.agentcloud.ws.systemconfigurator.wsimport.AcceptNewOrderResponse")
    public OrderStatus acceptNewOrder(
        @WebParam(name = "newOrder", targetNamespace = "")
        Order newOrder);

    /**
     * 
     * @param cyberPhysicalAgentDescription
     * @return
     *     returns eu.openmos.agentcloud.cloudinterface.AgentStatus
     */
    @WebMethod
    @WebResult(name = "agentStatus", targetNamespace = "")
    @RequestWrapper(localName = "createNewAgent", targetNamespace = "http://cloudinterface.agentcloud.openmos.eu/", className = "eu.openmos.agentcloud.ws.systemconfigurator.wsimport.CreateNewAgent")
    @ResponseWrapper(localName = "createNewAgentResponse", targetNamespace = "http://cloudinterface.agentcloud.openmos.eu/", className = "eu.openmos.agentcloud.ws.systemconfigurator.wsimport.CreateNewAgentResponse")
    public AgentStatus createNewAgent(
        @WebParam(name = "cyberPhysicalAgentDescription", targetNamespace = "")
        CyberPhysicalAgentDescription cyberPhysicalAgentDescription);

    /**
     * 
     * @param agentUniqueName
     * @return
     *     returns eu.openmos.agentcloud.cloudinterface.AgentStatus
     */
    @WebMethod
    @WebResult(name = "agentStatus", targetNamespace = "")
    @RequestWrapper(localName = "removeAgent", targetNamespace = "http://cloudinterface.agentcloud.openmos.eu/", className = "eu.openmos.agentcloud.ws.systemconfigurator.wsimport.RemoveAgent")
    @ResponseWrapper(localName = "removeAgentResponse", targetNamespace = "http://cloudinterface.agentcloud.openmos.eu/", className = "eu.openmos.agentcloud.ws.systemconfigurator.wsimport.RemoveAgentResponse")
    public AgentStatus removeAgent(
        @WebParam(name = "agentUniqueName", targetNamespace = "")
        String agentUniqueName);

}
