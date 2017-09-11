
package eu.openmos.agentcloud.ws.systemconfigurator.wsimport;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "SystemConfigurator", targetNamespace = "http://cloudinterface.agentcloud.openmos.eu/", wsdlLocation = "/wsdl/wsSystemConfigurator.wsdl")
public class SystemConfigurator_Service
    extends Service
{

    private final static URL SYSTEMCONFIGURATOR_WSDL_LOCATION;
    private final static WebServiceException SYSTEMCONFIGURATOR_EXCEPTION;
    private final static QName SYSTEMCONFIGURATOR_QNAME = new QName("http://cloudinterface.agentcloud.openmos.eu/", "SystemConfigurator");

    static {
        SYSTEMCONFIGURATOR_WSDL_LOCATION = eu.openmos.agentcloud.ws.systemconfigurator.wsimport.SystemConfigurator_Service.class.getResource("/wsdl/wsSystemConfigurator.wsdl");
        WebServiceException e = null;
        if (SYSTEMCONFIGURATOR_WSDL_LOCATION == null) {
            e = new WebServiceException("Cannot find '/wsdl/wsSystemConfigurator.wsdl' wsdl. Place the resource correctly in the classpath.");
        }
        SYSTEMCONFIGURATOR_EXCEPTION = e;
    }

    public SystemConfigurator_Service() {
        super(__getWsdlLocation(), SYSTEMCONFIGURATOR_QNAME);
    }

    public SystemConfigurator_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), SYSTEMCONFIGURATOR_QNAME, features);
    }

    public SystemConfigurator_Service(URL wsdlLocation) {
        super(wsdlLocation, SYSTEMCONFIGURATOR_QNAME);
    }

    public SystemConfigurator_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SYSTEMCONFIGURATOR_QNAME, features);
    }

    public SystemConfigurator_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SystemConfigurator_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns SystemConfigurator
     */
    @WebEndpoint(name = "SystemConfiguratorImplPort")
    public SystemConfigurator getSystemConfiguratorImplPort() {
        return super.getPort(new QName("http://cloudinterface.agentcloud.openmos.eu/", "SystemConfiguratorImplPort"), SystemConfigurator.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SystemConfigurator
     */
    @WebEndpoint(name = "SystemConfiguratorImplPort")
    public SystemConfigurator getSystemConfiguratorImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://cloudinterface.agentcloud.openmos.eu/", "SystemConfiguratorImplPort"), SystemConfigurator.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SYSTEMCONFIGURATOR_EXCEPTION!= null) {
            throw SYSTEMCONFIGURATOR_EXCEPTION;
        }
        return SYSTEMCONFIGURATOR_WSDL_LOCATION;
    }

}
