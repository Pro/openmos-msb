/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.openmos.msb.opcua.utils;
//import agentWebService.AgentStatus;
//import agentWebService.CyberPhysicalAgentDescription;
//import agentWebService.Recipe;
/*import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;*/

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import com.ximpleware.*;
//import com.ximpleware.xpath.*;
import eu.openmos.agentcloud.cloudinterface.AgentStatus;
import eu.openmos.agentcloud.config.ConfigurationLoader;
import eu.openmos.agentcloud.data.CyberPhysicalAgentDescription;
import eu.openmos.agentcloud.data.LogicalLocation;
import eu.openmos.agentcloud.data.PhysicalLocation;
import eu.openmos.agentcloud.data.recipe.KPI;
import eu.openmos.agentcloud.data.recipe.KPISetting;
import eu.openmos.agentcloud.data.recipe.Parameter;
import eu.openmos.agentcloud.data.recipe.ParameterSetting;
import eu.openmos.agentcloud.data.recipe.Recipe;
import eu.openmos.agentcloud.data.recipe.Skill;
import eu.openmos.agentcloud.data.recipe.SkillRequirement;
import eu.openmos.agentcloud.utilities.Constants;
import eu.openmos.agentcloud.ws.systemconfigurator.wsimport.SystemConfigurator;
import eu.openmos.agentcloud.ws.systemconfigurator.wsimport.SystemConfigurator_Service;
import eu.openmos.msb.cloudinterface.WebSocketsReceiver;
import eu.openmos.msb.cloudinterface.WebSocketsSender;
import eu.openmos.msb.cloudinterface.WebSocketsSenderDraft;
import java.io.*;
import eu.openmos.msb.database.stateless.DeviceRegistryBean;
import eu.openmos.msb.dummyclasses.ChangedState;
import eu.openmos.msb.dummyclasses.ExecuteData;
import eu.openmos.msb.dummyclasses.RegFile;
import eu.openmos.msb.dummyclasses.ServerStatus;
import eu.openmos.msb.starter.DeviceInterface;
import eu.openmos.msb.starter.MSB_MiloClientSubscription;
import eu.openmos.msb.starter.MSB_gui;
import eu.openmos.msb.starter.MyHashMaps;
import io.vertx.core.Vertx;
//import fabio_xml.Device;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.BindingProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 *
 * @author fabio.miranda
 */
public class OPCDeviceItf implements DeviceInterface{

    
    
     Vertx vertx = Vertx.vertx(); 
     Boolean withAGENTCloud=false;
     
    /*final private String function;
    final private String arguments;
    
    public OPCDeviceItf(String func, String args) {
		//super();
		this.function = func;
                this.arguments = args;
	}*/
    
    DeviceRegistryBean dbMSB = new DeviceRegistryBean();
    
    /**
     *
     * @param Function
     * @param args
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws JAXBException
     */
    public String AllCases(String Function, String args) throws ParserConfigurationException, SAXException, IOException, JAXBException, FileNotFoundException, TransformerException {
    String productID;
    String WorkstationID;
    String EntityID;
    String ProductID;
    String State="";
    String Recipe;
      switch (Function.toLowerCase()) {
               
            case "deployrecipe": //from resource agent to msb. from msb to workstation. 
                //param: workstation ID, recipe  
                WorkstationID=(String) args; //args will be a list of xml elements. use XML parser
                Recipe=(String) args; //args will be a list of xml elements. use XML parser
                DeployRecipe(WorkstationID, Recipe);
                
                 //do wtv
                break;
            case "updaterecipe": //from workstation to msb. from msb to datalistener
                //param: workstation ID, OldRecipeID, NewRecipe 
                
                
                EntityID = (String) args; //args will be a list of xml elements. use XML parser
                String OldRecipeID = (String) args; //args will be a list of xml elements. use XML parser
                String NewRecipe = (String) args; //args will be a list of xml elements. use XML parser
                //UpdateRecipe(WorkstationID,OldRecipeID,NewRecipe);
                //do wtv
                break;
            case "executereciperampupmode": //from workstation to msb. from msb to datalistener
                //param: workstation ID, OldRecipeID, NewRecipe 
                WorkstationID = (String) args; //args will be a list of xml elements. use XML parser
                String RecipeID = (String) args; //args will be a list of xml elements. use XML parser
                
                ExecuteRecipeRampUpMode(WorkstationID,RecipeID);
                //do wtv
                break;
             case "uploadexecutiondata": //from workstation to msb. from msb to datalistener
                //param: Module ID, Data 
                String ModuleID = args; //args will be a list of xml elements. use XML parser
                String Data = (String) args; //args will be a list of xml elements. use XML parser
                
                UploadExecutionData(ModuleID,Data);
                //do wtv
                break;
            case "statusupdate": //from workstation to msb. from msb to datalistener
                //param: State   
                State = (String) args; //args will be a list of xml elements. use XML parser
                
                return StatusUpdate(State);

            case "changerecipestate": //from workstation to msb. from msb to datalistener
                //param: WorkstationID,RecipeID, State   
                WorkstationID =  args;
                RecipeID = (String) args;
                State = (String) args; //args will be a list of xml elements. use XML parser
                
                ChangeRecipeState(WorkstationID, RecipeID, State);
                //do wtv
                break;
            case "requestproduct": //from workstation to msb. from msb to datalistener
                //param: ProductID  
                ProductID =  args;
                             
                RequestProduct(ProductID);
                //do wtv
                break;
            case "deploynewrecipe": //from workstation to msb. from msb to datalistener
                //param: EntityID, Recipe, ProductID  
                EntityID = args;
                Recipe = (String) args;
                ProductID =  args;
                             
                DeployNewRecipe(EntityID,Recipe, ProductID);
                //do wtv
                break;
                 case "requestmoduledetails": //from resource agents to msb. from msb to workstation. from datalistener to msb. from msb to workstation
                //params ?
                RequestModuleDetails();
                break;
            case "selfdescription": //from workstation to msb. from msb to datalistener
                productID=(String) args; //args will be a list of xml elements. use XML parser
                //selfdescriptioninformation
                //do wtv
                break;
            case "requestworkstationinformation": //from skill agreggator to msb. from msb to workstation
                WorkstationID =  args;
                RequestWorkstationInformation(WorkstationID);
                break;
            case "provideselfdescription": //from workstation to msb. from msb to skill agreggator
                //do wtv
               //enviar requestworkstationinformation para workstation e esperar ProvideSelfDescription
                break;
            
            case "executeskill":
                ProductID= args; //args will be a list of xml elements. use XML parser
                //do wtv
                break;
            case "changedstate":
                //params ExecutionState, productID, RecipeID, KPIs
                
                //active for the 1st demonstrator
                
               
                return ChangedState(args);
               
            case "recipeexecutiondone":
               //active for the 1st demonstrator
                return RecipeExecutionDone(args);
            case "executetransportskill":
                //do wtv
                break;
            case "productcreated":
               //do wtv
                break;
                case "resquestlocalModulesregistration": //from workstation to msb. from msb to equipment module
                //do wtv
                
                break;
            case "broadcastpresence": //from workstation to msb. from msb to equipment module, deployment agent and data listener
                //params: ID, EquipmentType, SelfDescriptionServiceCall
                String ID= args;
                String EquipmentType=(String) args;
                String SelfDescriptionServiceCall = (String) args;
                BroadcastPresence(ID,EquipmentType, SelfDescriptionServiceCall);
                
                 //do wtv
                break;
            case "sendRecipe" :
                //active for the 1st demonstrator
                return SendRecipe(args);
                 
            case "deviceregistration": //from workstation to msb. from msb to equipment module, deployment agent and data listener
                //params: ID, EquipmentType, SelfDescriptionServiceCall
                
                //active for the 1st demonstrator
                return WorkStationRegistration(args);
                
                 //do wtv
                //break;
            default: 
                
                return "Function not found!";
        }
        return null;
    }
    
    
    @Override
    public void ExecutionData(String Function, List args) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    String productID;
    int WorkstationID=0;
    String EntityID;
    int ProductID=0;
    String State="";
    String Recipe;
      switch (Function.toLowerCase()) {
               
            case "deployrecipe": //from resource agent to msb. from msb to workstation. 
                //param: workstation ID, recipe  
                WorkstationID=(int) args.get(0); //args will be a list of xml elements. use XML parser
                Recipe=(String) args.get(1); //args will be a list of xml elements. use XML parser
                //DeployRecipe(WorkstationID, Recipe);
                
                 //do wtv
                break;
            case "updaterecipe": //from workstation to msb. from msb to datalistener
                //param: workstation ID, OldRecipeID, NewRecipe 
                EntityID = (String) args.get(0); //args will be a list of xml elements. use XML parser
                String OldRecipeID = (String) args.get(1); //args will be a list of xml elements. use XML parser
                String NewRecipe = (String) args.get(2); //args will be a list of xml elements. use XML parser
                UpdateRecipe(EntityID,NewRecipe);
                //do wtv
                break;
            case "executereciperampupmode": //from workstation to msb. from msb to datalistener
                //param: workstation ID, OldRecipeID, NewRecipe 
                WorkstationID = (int) args.get(0); //args will be a list of xml elements. use XML parser
                String RecipeID = (String) args.get(1); //args will be a list of xml elements. use XML parser
                
                //ExecuteRecipeRampUpMode(WorkstationID,RecipeID);
                //do wtv
                break;
             case "uploadexecutiondata": //from workstation to msb. from msb to datalistener
                //param: Module ID, Data 
                int ModuleID = (int) args.get(0); //args will be a list of xml elements. use XML parser
                String Data = (String) args.get(1); //args will be a list of xml elements. use XML parser
                
                //UploadExecutionData(ModuleID,Data);
                //do wtv
                break;
            case "statusupdate": //from workstation to msb. from msb to datalistener
                //param: State   
                State = (String) args.get(0); //args will be a list of xml elements. use XML parser
                
                StatusUpdate(State);
                //do wtv
                break;
            case "changerecipestate": //from workstation to msb. from msb to datalistener
                //param: WorkstationID,RecipeID, State   
                WorkstationID = (int) args.get(0);
                RecipeID = (String) args.get(1);
                State = (String) args.get(2); //args will be a list of xml elements. use XML parser
                
                //ChangeRecipeState(WorkstationID, RecipeID, State);
                //do wtv
                break;
            case "requestproduct": //from workstation to msb. from msb to datalistener
                //param: ProductID  
                ProductID = (int) args.get(0);
                             
                //RequestProduct(ProductID);
                //do wtv
                break;
            case "deploynewrecipe": //from workstation to msb. from msb to datalistener
                //param: EntityID, Recipe, ProductID  
                EntityID = (String) args.get(0);
                Recipe = (String) args.get(1);
                ProductID = (int) args.get(2);
                             
                //DeployNewRecipe(EntityID,Recipe, ProductID);
                //do wtv
                break;
            default: 
                 //do wtv
                break;
        }
      
    }

    @Override
    public void EquipmentData(String Function, List args) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String productID;
        
        switch (Function.toLowerCase()) {
               
            case "requestmoduledetails": //from resource agents to msb. from msb to workstation. from datalistener to msb. from msb to workstation
                //params ?
                RequestModuleDetails();
                break;
            case "selfdescription": //from workstation to msb. from msb to datalistener
                productID=(String) args.get(0); //args will be a list of xml elements. use XML parser
                //selfdescriptioninformation
                //do wtv
                break;
            case "requestworkstationinformation": //from skill agreggator to msb. from msb to workstation
                int WorkstationID = (int) args.get(0);
                //RequestWorkstationInformation(WorkstationID);
                break;
                case "provideselfdescription": //from workstation to msb. from msb to skill agreggator
                //do wtv
               //enviar requestworkstationinformation para workstation e esperar ProvideSelfDescription
                break;
            default: 
                 //do wtv
                break;
        }
    
    }

    @Override
    public void EquipmentCommunication(String Function, List args) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    int productID;
    
    //teste parse xml file
    /*VTDGen vg = new VTDGen();
    AutoPilot ap = new AutoPilot();
    int i;
        try {
            ap.selectXPath("/catalog/book");
        } catch (XPathParseException ex) {
            Logger.getLogger(OPCDeviceItf.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    if (vg.parseFile("C:\\Users\\fabio.miranda\\Documents\\NetBeansProjects\\FabioMSB_Struct\\xml_sample.xml",true)){
    VTDNav vn = vg.getNav();
    ap.bind(vn); // apply XPath to the VTDNav instance
        try {
            // AutoPilot moves the cursor for you
            while((i=ap.evalXPath())!=-1){
                try {
                    System.out.println("the attribute index val is "+
                            i +" the attribute string ==>"+vn.toString(vn.getAttrVal("attribute")));
                } catch (NavException ex) {
                    Logger.getLogger(OPCDeviceItf.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   } catch (XPathEvalException | NavException ex) {
            Logger.getLogger(OPCDeviceItf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
    //teste2
   /* try {
	// open a file and read the content into a byte array
        File f = new File("C:\\Users\\fabio.miranda\\Documents\\NetBeansProjects\\FabioMSB_Struct\\xml_sample.xml");
	FileInputStream fis =  new FileInputStream(f);
        byte[] b = new byte[(int) f.length()];
	fis.read(b);
	//instantiate VTDGen
	//and call parse 
	VTDGen vg = new VTDGen();
	vg.setDoc(b);
	vg.parse(true);  // set namespace awareness to true
	VTDNav vn = vg.getNav();
	AutoPilot ap = new AutoPilot(vn);
	//ap.declareXPathNameSpace("ns1","http://purl.org/dc/elements/1.1/");
	ap.selectXPath("/catalog/book");
	int result = -1;
	int count = 0;
	while((result = ap.evalXPath())!=-1){
		System.out.print(""+result+"  ");     
		System.out.print("Element name ==> "+vn.toString(result));
		int t = vn.getText(); // get the index of the text (char data or CDATA)
		if (t!=-1)
		  System.out.println(" Text  ==> "+vn.toNormalizedString(t));
		System.out.println("\n ============================== ");
		count++;
	}
	System.out.println("Total # of element "+count);

     }
     catch (ParseException e){
	     System.out.println(" XML file parsing error \n"+e);
     }
     catch (NavException e){
	     System.out.println(" Exception during navigation "+e);
     }
     catch (XPathParseException e){
	     
     }
     catch (XPathEvalException e){
	    
     }
     catch (java.io.IOException e)
     {
	     System.out.println(" IO exception condition"+e);
     }*/
  
    
    
    
          switch (Function.toLowerCase()) {
            case "requestproduct": //from agents to msb. from msb to launch. from launch to msb
                //do wtv
                //productID= args; //args will be a list of xml elements. use XML parser
                break;
            case "executeskill":
                //productID= args; //args will be a list of xml elements. use XML parser
                //do wtv
                break;
            case "changedstate":
                //params ExecutionState, productID, RecipeID, KPIs
                /*String ExecutionState = (String) args; 
                productID = args; 
                String RecipeID = args; 
                String KPIs = (String) args;*/
                
                //ChangedState(ExecutionState, productID, RecipeID, KPIs );
               
                break;
            case "recipeexecutiondone":
               //do wtv
                break;
            case "executetransportskill":
                //do wtv
                break;
            case "productcreated":
               //do wtv
                break;
            
            default: 
                 //do wtv
                break;
        }
    
    }

    @Override
    public void EquipmentRegistration(String Function, List args) {
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       String productID;
       
        switch (Function.toLowerCase()) {
            case "resquestlocalModulesregistration": //from workstation to msb. from msb to equipment module
                //do wtv
                
                break;
            case "broadcastpresence": //from workstation to msb. from msb to equipment module, deployment agent and data listener
                //params: ID, EquipmentType, SelfDescriptionServiceCall
                int ID=(int) args.get(0);
                String EquipmentType=(String) args.get(1);
                String SelfDescriptionServiceCall = (String) args.get(2);
                //BroadcastPresence(ID,EquipmentType, SelfDescriptionServiceCall);
                
                 //do wtv
                break;
              
            default: 
                 //do wtv
                break;
        }
    
    }
    
    /**
     *
     * @param WorkstationID
     * @param Recipe
     */
    public void DeployRecipe(String WorkstationID, String Recipe){ //send to workstation
        //DeployRecipe(Recipe) //send to workstation opc
        
        //wait for ConfirmRecipeDeployment(Recipe)
        
        //return Recipe //send back to Resource Agent
        
    }
    
    /**
     *
     * @param WorkstationID
     */
    public void RequestWorkstationInformation(String WorkstationID){ //send to workstation
        //RequestWorkstationInformation() //send to workstation opc
        
        //wait for ProvideSelfDescription(SelfDescriptionInformation)
        
        //ProvideSelfDescription(SelfDescriptionInformation) //send to Skill Aggregator
        
    }

    private void UpdateRecipe(String EntityID, String NewRecipe) {
        //From SkillAggregator OR Resourceagent OR TransportAgent
        
        //1-ver para quem é o EntityID e enviar -> pode ser workstationID ou TransportID. É transparente para o MSB
        //2-retornar resposta
        
        //UpdateRecipe(String OldRecipeID, String NewRecipe) //enviar para o EntityID
        //wait for ConfirmRecipeDeployment(RecipeID)
        //return RecipeID para EntityID
        
        
        /*if(from SkillAggregator){
        }else if(From Resourceagent){
        }else if(From TransportAgent){
        } //indiferente*/
        
        
    }

    private void ExecuteRecipeRampUpMode(String WorkstationID, String RecipeID) {
        //from skill aggregator
        //call ExecuteRecipeRampUpMode(RecipeID) //to workstation
        //wait for ExecuteSkill(ModuleID,RecipeID)
        
        //change this??
        //call ExecuteSkill(RecipeID)
        //wait for CompletedSkillExecution(RecipeID)
        //call CompletedSkillExecution(RecipeID)
        
        
    
    }

    private void UploadExecutionData(String ModuleID, String Data) {
        //from EquipmentModule or Workstation
        //leitura de nós especificos, do cliente msb para o servidor do device
        //call UploadExecutionData(ModuleID, Data) //colocar na Base De Dados
    
    }

    private String StatusUpdate(String State) {
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //from workstation
        //call StatusUpdate(State,...) //send to ResourceAgent
        
        //what to send?
        
         return "Chegou";

    }

    private void ChangeRecipeState(String WorkstationID, String RecipeID, String State) {
        //from skill aggregator
        
        //call ChangeRecipeState(RecipeID, state) //send to workstation
        //wait for ConfirmRecipestate(RecipeID, State)
        
        //return ConfirmRecipestate(RecipeID, State) //to skillaggregator
    
    }

    private String RequestProduct(String ProductID) {
      
        //check the sender opcClient object from its Name
        MyHashMaps myMaps = MyHashMaps.getInstance(); //singleton to access hashmaps in other classes
        Map<String, MSB_MiloClientSubscription> ProductAdapterHashMap = myMaps.getProductIDAdapterMaps(); //get opcdevice name and object map
        MSB_MiloClientSubscription MiloClientID = ProductAdapterHashMap.get(ProductID);  //get the opcdevice objectID for the given ProductID
        CompletableFuture<String> RequestProductResponse = MiloClientID.RequestProduct(MiloClientID.milo_client_instanceMSB, ProductID); //call the method on the respective Device

        //if from production Optimizer Agent - webservice?
        //call RequestProduct(ProductID) //to lauch order service
        //wait for return RequestProduct(ProductID) //from launch order service
        //if launch order service - opcua
        //call ExecuteSkill(ProductID) //to Workstation
        //wait for return ExecuteSkill(Recipe,EquipmentID) //from Workstation
        //or wait for NoRecipe(Recipe,ProductID //from workstation
        //call ExecuteSkill(Recipe) //to equipment?? change this
        //wait for RecipeExecutionDone(RecipeID) //change this
        //call RecipeExecutionDone(RecipeID) //change this
        try {
            return RequestProductResponse.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(OPCDeviceItf.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }

    }

    /**
     * Send ChangedState to the AgentCloud
     * @param ExecutionState
     * @param productID
     * @param RecipeID
     * @param KPIs 
     */
    private String ChangedState(String args) {
        //from workstation

        //parse xml SelfDescriptionInformation
        String senderName = args.split("\\:")[0]; //get sender name
        String Alldata = args.substring(args.indexOf(':') + 1); //get XML data
        ChangedState parsedClass = null;

        DeviceRegistryBean dbMSB = new DeviceRegistryBean(); //TODO: save execution data on executionTable DB

        //STRING TO XML
        DocumentBuilderFactory dfctr = DocumentBuilderFactory.newInstance();
        DocumentBuilder bldr = null;
        try {
            bldr = dfctr.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(OPCDeviceItf.class.getName()).log(Level.SEVERE, null, ex);
        }
        InputSource insrc = new InputSource();
        insrc.setCharacterStream(new StringReader(Alldata));
        org.w3c.dom.Document docres = null;
        try {

            docres = bldr.parse(insrc);

            //stringToDom(args); //convert xml string to FILE
            System.out.println("\n\nThis is the XML received: " + docres.getDocumentElement().getTextContent() + "\n\n");
        } catch (SAXException ex) {
            Logger.getLogger(OPCDeviceItf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OPCDeviceItf.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ChangedState.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

//ou
            //StringReader boda = new StringReader(args);
            //RegFile dudie = JAXB.unmarshal(boda.toString(), RegFile.class);
            //File file = new File("museums.xml");
            //StringReader reader = new StringReader(args);
            parsedClass = (ChangedState) unmarshaller.unmarshal(docres);
        } catch (Exception ex) {
            System.out.println("\n Problem parsing class: " + ex.toString());
        }

        //check the sender opcClient object from its Name
        MyHashMaps myOpcUaClientsAgentsMap = MyHashMaps.getInstance(); //singleton to access hashmaps in other classes
        Map<String, MSB_MiloClientSubscription> OpcuaDeviceHashMap = myOpcUaClientsAgentsMap.getOPCclientIDMaps(); //get opcdevice name and object map
        MSB_MiloClientSubscription MiloClientID = OpcuaDeviceHashMap.get(senderName);  //get the opcdevice objectID for the given Server Name

        if (withAGENTCloud) {
            Map<MSB_MiloClientSubscription, CyberPhysicalAgentDescription> DevAgentMap = myOpcUaClientsAgentsMap.getAgentDeviceIDMaps();
            CyberPhysicalAgentDescription agentObj = DevAgentMap.get(MiloClientID); //get the  agentID for the respective sender client object

            //already deployed when agent is created ?
            Vertx.vertx().deployVerticle(new WebSocketsSender("5555")); //test! delete
            //vertx.deployVerticle(new WebSocketsSender(agentObj.getUniqueName()));
            List<String> ReplyMsg = new ArrayList<String>();

            //Send message on the respective AgentID topic/websocket
            String msgToSend = Constants.MSB_MESSAGE_TYPE_EXTRACTEDDATA  + ";" + parsedClass.recipeID + ";" + parsedClass.productID + ";";

            vertx.eventBus().send(agentObj.getUniqueName(), msgToSend, reply -> {
                        if (reply.succeeded()) {
                            System.out.println("vertX Received reply: " + reply.result().body());
                            ReplyMsg.add(reply.result().body().toString());
                        } else {
                            System.out.println("vertX No reply");
                            ReplyMsg.add("vertX No reply");
                        }
                    });
            return ReplyMsg.get(0); //ADD VERTX MESSAGE REPLY
        } else {
            return "OK - No AgentCloud";
        }

    }

    private void DeployNewRecipe(String EntityID, String Recipe, String ProductID) {
        //from resource Agent or transport  Agent
        
        // call DeployNewRecipe(Recipe,ProductID) //to EntityID - workstation or transport equipment
        //wait for return RecipeDeployed(Recipe,ProductID)
        
        //return RecipeDeployed(WorkstationID, Recipe,ProductID) //back to sender
        
        
    }

    private void BroadcastPresence(String ID, String EquipmentType, String SelfDescriptionServiceCall) {
        //from workstation to msb. from msb to equipment module, deployment agent and data listener
        
        //call BroadcastPresence(ID, EquipmentType, SelfDescriptionServiceCall) //to equipment module
        //call BroadcastPresence(ID, EquipmentType, SelfDescriptionServiceCall) //to deployment Agent
        
        //call BroadcastPresence(ID, EquipmentType, SelfDescriptionServiceCall) //to DataListener (DB)
        //dbMSB.register_device(DevName, ShortInfo, LongInfo, Ip_addr, Protocol);
        //ArrayList Arrays= dbMSB.get_device_address_protocol(ID); //get device address and protocol
        
        //System.out.println(Arrays.toString());

    }

    private void RequestModuleDetails() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
        //from Resource Agent or Data Listener (DB)
        //call RequestModuleDetails
    
    }
    
    private String WorkStationRegistration(String args) throws JAXBException, FileNotFoundException, ParserConfigurationException, TransformerException {
        
        //parse xml SelfDescriptionInformation
        String senderName = args.split("\\:")[0]; //get sender name
        String Alldata=args.substring(args.indexOf(':')+1); //get XML data
        RegFile parsedClass = null;
        
        DeviceRegistryBean dbMSB = new DeviceRegistryBean(); //TODO: save execution data on executionTable DB
/*
         try {
             FiletoXMLtoObject("RegFile.xml");
         } catch (SAXException | IOException ex) {
             Logger.getLogger(OPCDeviceItf.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
         
         
        //STRING TO XML
        DocumentBuilderFactory dfctr = DocumentBuilderFactory.newInstance();
        DocumentBuilder bldr=null;
        try {
            
            bldr = dfctr.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(OPCDeviceItf.class.getName()).log(Level.SEVERE, null, ex);
        }
        InputSource insrc = new InputSource();
        insrc.setCharacterStream(new StringReader(Alldata)); 
        org.w3c.dom.Document docres=null;
        try {
            
            docres = bldr.parse(insrc);
            
            //stringToDom(args); //convert xml string to FILE
            
            System.out.println("\n\nThis is the XML received: "+docres.getDocumentElement().getTextContent()+"\n\n");
        } catch (SAXException ex) {
            Logger.getLogger(OPCDeviceItf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OPCDeviceItf.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RegFile.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

//ou
            //StringReader boda = new StringReader(args);
            //RegFile dudie = JAXB.unmarshal(boda.toString(), RegFile.class);
            //File file = new File("museums.xml");
            //StringReader reader = new StringReader(args);
             parsedClass = (RegFile) unmarshaller.unmarshal(docres);
            
         //populate hashmaps
         MyHashMaps myOpcUaMap = MyHashMaps.getInstance(); //singleton to access client objects in other classes
         List<ExecuteData> ETD = new ArrayList<>();
         int index=0;
         MSB_MiloClientSubscription ThisOPCServerTemp= myOpcUaMap.OPCclientIDMaps.get(senderName);
            for (String key : parsedClass.ExecuteTable.keySet()) {
                System.out.println(key + " ->MAPA de execução deviceitf<- - " + parsedClass.ExecuteTable.get(key));
                ETD.add(parsedClass.ExecuteTable.get(key));

                //dbMSB.register_execution_info(senderName, ETD.recipeID, "NO AGENT", ETD.productID, ETD.methodID, ETD.objectID); //DO THIS AFTER AGENT CREATION

                myOpcUaMap.setProductIDAdapterMaps(ETD.get(index).productID, ThisOPCServerTemp); //put productID vs miloclient
                index++;
            }
            myOpcUaMap.setExecutionInfoMaps(senderName, ETD);
            
            
            for (String key : parsedClass.ServerTable.keySet()) {
                System.out.println(key + " ->MAPA de execução deviceitf<- - " + parsedClass.ServerTable.get(key));
                ServerStatus SStat = parsedClass.ServerTable.get(key);
                
                myOpcUaMap.setDevicesNameDataMaps(senderName, SStat);
                //call mainwindow filltables
                MSB_gui.FillDevicesTable();
            }
            

            System.out.println("\n\n\n Parsed class: " + parsedClass.Name + "\n\n\n");
        } catch (Exception ex) {
            System.out.println("\n Problem parsing class: " + ex.toString());
        }
        
        
        //unmarshall from a xmlfile++++++++++++++++++++++++++++++++++++
        /* InputStream is2 = new FileInputStream( "nosferatu.xml" );
         JAXBContext jc2 = JAXBContext.newInstance( "com.acme.foo" );
         Unmarshaller u = jc2.createUnmarshaller();
         RegFile o = (RegFile) u.unmarshal( is2 );*/
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        
        /*JAXBContext jc = JAXBContext.newInstance(Device.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Device root = (Device) unmarshaller.unmarshal(docres);

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(root, System.out);*/
        
        //String message = docres.getDocumentElement().getTagName();
        //String message2 = docres.getDocumentElement().getElementsByTagName("Session").item(0).getChildNodes().item(0).getTextContent();
        //System.out.println(message);
        System.out.println("\n\n WorkStationRegistration message has arrived \n\n");
        //System.out.println(message2);
        //CreateNewAgent(workstation)
        //from Resource Agent or Data Listener (DB)
        //call RequestModuleDetails

        //CyberPhysicalAgentDescription cpad=null;
        //cpad.getRecipes().add(root.getRecipes());
        //cpad.getRecipes().addAll((Collection<? extends Recipe>) root.getRecipes());
        //cenas.setRecipes();
        //AgentStatus AStatus=null;
        //AStatus=createNewAgent(cpad);
        long start = System.currentTimeMillis();
        
        //SACAR RecipeID, NodeID, ProductID, Workstation e fazer uma tabela de execução

           if (withAGENTCloud) {
            try {
                SystemConfigurator_Service systemConfiguratorService = new SystemConfigurator_Service();
                SystemConfigurator systemConfigurator = systemConfiguratorService.getSystemConfiguratorImplPort();

/////////////////////////////
                String CLOUDINTERFACE_WS_VALUE = ConfigurationLoader.getMandatoryProperty("openmos.agent.cloud.cloudinterface.ws.endpoint");

                BindingProvider bindingProvider = (BindingProvider) systemConfigurator;
                bindingProvider.getRequestContext().put(
                        // BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://192.168.15.5:9999/wsSystemConfigurator");
                        BindingProvider.ENDPOINT_ADDRESS_PROPERTY, CLOUDINTERFACE_WS_VALUE);
//////////////////////////////

                // CyberPhysicalAgentDescription cpad = new CyberPhysicalAgentDescription();
                //String myAgentId = "Resource_" + Calendar.getInstance().getTimeInMillis(); //or Transport_...
                //CyberPhysicalAgentDescription cpad = getTestObject(myAgentId);
                
                
                CyberPhysicalAgentDescription cpad = DummyCPADgeneration(parsedClass); // WorkStationDataToCPAD(parsedClass);  or DummyCPADgeneration(parsedClass); 
                //CyberPhysicalAgentDescription cpad = getTestObject("lolol");
                
                MyHashMaps myMaps = MyHashMaps.getInstance(); //singleton to access client objects in other classes
                List<ExecuteData> ETD = new ArrayList<>();
                //Update execute table data with agentID**************
                for (String key : parsedClass.ExecuteTable.keySet()) {
                    System.out.println(key + " ->MAPA de execução deviceitf<- - " + parsedClass.ExecuteTable.get(key));
                    ETD.add(parsedClass.ExecuteTable.get(key));
                }
                
                myMaps.setExecutionInfoMaps(senderName, ETD);
                //call mainwindow filltables
                MSB_gui.FillProductsTable();

                //VECTOR TODO: 10/04/17
                //dbMSB.edit_execute_info(senderName, ETD.recipeID, myAgentId, ETD.productID, "", ""); //DO THIS AFTER AGENT CREATION

                
                
                
                //end**************
                 
                
                //testObject e fazer um**********************************************************************05-04-17
              

                
                //fim do testObject**********************************************************************05-04-17
                
                

                AgentStatus agentStatus = systemConfigurator.createNewAgent(cpad);
                System.out.println("\n\n Creating Resource or Transport Agent... \n\n");

                //create websocket to the agentcloud
                //Vertx.vertx().deployVerticle(new WebSocketsSender(myAgentId)); //b4 24-3-17
                
                //vertx.deployVerticle(new WebSocketsSender(cpad.getUniqueName()));
                
                
                   //Send message on the respective AgentID topic/websocket
            String msgToSend = Constants.MSB_MESSAGE_TYPE_EXTRACTEDDATA+"anything";
            
            Vertx.vertx().deployVerticle(new WebSocketsSender(cpad.getUniqueName()));
        
            
            /*List<String> ReplyMsg = new ArrayList<String>();
             Vertx vertx2 = Vertx.vertx(); 
             vertx2.eventBus().send(cpad.getUniqueName(), msgToSend, reply -> {
                        if (reply.succeeded()) {
                            System.out.println("vertX Received reply: " + reply.result().body());
                            ReplyMsg.add(reply.result().body().toString());
                        } else {
                            System.out.println("vertX No reply");
                            ReplyMsg.add("vertX No reply");
                        }
                    });*/
             //ReplyMsg.get(0); //ADD VERTX MESSAGE REPLY
                
                

                //check the sender opcClient object from its Name
                MyHashMaps myOpcUaClientsAgentsMap = MyHashMaps.getInstance(); //singleton to access hashmaps in other classes
                Map<String, MSB_MiloClientSubscription> OpcuaDeviceHashMap = myOpcUaClientsAgentsMap.getOPCclientIDMaps();
                MSB_MiloClientSubscription MiloClientID = OpcuaDeviceHashMap.get(senderName);
                //add the sender client object to the respective agentID
                myOpcUaClientsAgentsMap.setAgentDeviceIDMaps(MiloClientID, cpad);

                return agentStatus.getCode(); //OK? ou KO?

            } catch (Exception e) {
                long elapsedTime = (long) ((System.currentTimeMillis() - start) / 1000F);
                System.out.println("\n\n ELAPSED TIME: " + elapsedTime + "s");
                System.out.println("\n\n Problem with Ws " + e.toString());
                return e.toString();
            }

        } else {
            //call mainwindow filltables
            MSB_gui.FillProductsTable();
            //draft 24-3-17 internal verteX check
            vertx.deployVerticle(new WebSocketsReceiver("R1"));
            vertx.deployVerticle(new WebSocketsReceiver("R2"));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(OPCDeviceItf.class.getName()).log(Level.SEVERE, null, ex);
            }
            vertx.deployVerticle(new WebSocketsSenderDraft());
            //*******end draft
            
            return "OK - No AgentPlatform";
        }
         
    }
    
    
    private CyberPhysicalAgentDescription DummyCPADgeneration(RegFile datafromWorkStation) {
               
        CyberPhysicalAgentDescription cpad = new CyberPhysicalAgentDescription();
        String myAgentId = "Resource_" + Calendar.getInstance().getTimeInMillis(); //or Transport_...
                
        String cpadUniqueName = myAgentId;
        String cpadAgentClass = "asdsa";
        String cpadType = "resource_df_service";  //or transport_df_service
        List<String> cpadParameters = new LinkedList<>(Arrays.asList("asdsad"));
            Skill s;
                String skillDescription = "SkillDescription";
                String skillUniqueId = "SkillUniqueId";
                    KPI kpi;
                    String kpiDescription = "SkillKpiDescription";
                    String kpiUniqueId = "SkillKpiUniqueId";
                    String kpiName = "SkillKpiName";
                    String kpiDefaultUpperBound = "SkillKpiDefaultUpperBound";
                    String kpiDefaultLowerBound = "SkillKpiDefaultLowerBound";
                    String kpiCurrentValue = "SkillKpiCurrentValue";
                    String kpiUnit = "SkillKpiUnit";
                    // kpi = new Kpi(kpiDescription, kpiUniqueId, kpiName, kpiDefaultUpperBound, kpiDefaultLowerBound, kpiCurrentValue, kpiUnit);
                    // kpi = new Kpi(kpiDescription, kpiUniqueId, kpiName, kpiDefaultUpperBound, kpiDefaultLowerBound, kpiCurrentValue, kpiUnit);
                    kpi = new KPI();
                    kpi.setDescription(kpiDescription);
                    kpi.setUniqueId(kpiUniqueId);
                    kpi.setName(kpiName);
                    kpi.setDefaultUpperBound(kpiDefaultUpperBound);
                    kpi.setDefaultLowerBound(kpiDefaultLowerBound);
                    kpi.setCurrentValue(kpiCurrentValue);
                    kpi.setUnit(kpiUnit);
                    
                    
                List<KPI> skillKpis = new LinkedList<>(Arrays.asList(kpi));
                String skillName = "SkillName";
                    Parameter p;
                    String parameterDefaultValue = "SkillParameterDefaultValue";
                    String parameterDescription = "SkillParameterDescription";
                    String parameterUniqueId = "SkillParameterUniqueId";
                    String parameterLowerBound = "SkillParameterLowerBound";
                    String parameterUpperBound = "SkillParameterUpperBound";
                    String parameterName = "SkillParameterName";
                    String parameterUnit = "SkillParameterUnit";
                    // p = new Parameter(parameterDefaultValue, parameterDescription, parameterUniqueId,                             parameterLowerBound, parameterUpperBound, parameterName, parameterUnit);
                    p = new Parameter();
                    p.setDefaultValue(parameterDefaultValue);
                    p.setDescription(parameterDescription);
                    p.setUniqueId(parameterUniqueId);
                    p.setLowerBound(parameterLowerBound);
                    p.setUpperBound(parameterUpperBound);
                    p.setName(parameterName);
                    p.setUnit(parameterUnit);
                    
                    
                    
                List<Parameter> skillParameters = new LinkedList<>(Arrays.asList(p));
                int skillType = 0;
            // s = new Skill(skillDescription, skillUniqueId, skillKpis, skillName, skillParameters, skillType);
            s = new Skill();
            s.setDescription(skillDescription);
            s.setUniqueId(skillUniqueId);
            // s.getKpis().addAll(skillKpis);
            s.setKpis(skillKpis);
            s.setName(skillName);
            // s.getParameters().addAll(skillParameters);
            s.setParameters(skillParameters);
            s.setType(skillType);
            
            
            
            
        List<Skill> cpadSkills = new LinkedList<>(Arrays.asList(s));
            Recipe r;
                String recipeDescription = "asdsad";
                String recipeUniqueId = "asda";
                    KPISetting recipeKpiSetting;
                    String kpiSettingDescription = "asdsad";
                    String kpiSettingId = "asda";
                    String kpiSettingName = "asdsa";
                    String kpiSettingValue = "asds";
                    // recipeKpiSetting = new KPISetting(kpiSettingDescription, 
                    // kpiSettingId, kpiSettingName, kpiSettingValue);
                    recipeKpiSetting = new KPISetting();
                    recipeKpiSetting.setDescription(kpiSettingDescription);
                    recipeKpiSetting.setId(kpiSettingId);
                    recipeKpiSetting.setName(kpiSettingName);
                    recipeKpiSetting.setValue(kpiSettingValue);
                    
                List<KPISetting> recipeKpiSettings = new LinkedList<>(Arrays.asList(recipeKpiSetting));
                String recipeName = "asda";
                    ParameterSetting recipePS;
                    String parameterSettingDescription = "asdsad";
                    String parameterSettingId = "asda";
                    String parameterSettingName = "asdsa";
                    String parameterSettingValue = "asds";
                    // recipePS = new ParameterSetting(parameterSettingDescription, parameterSettingId, parameterSettingName, parameterSettingValue);
                    recipePS = new ParameterSetting();
                    recipePS.setDescription(parameterSettingDescription);
                    recipePS.setId(parameterSettingId);
                    recipePS.setName(parameterSettingName);
                    recipePS.setValue(parameterSettingValue);
                    
                List<ParameterSetting> recipeParameterSettings = new LinkedList<>(Arrays.asList(recipePS));
                String recipeUniqueAgentName = "asdsad";
                    SkillRequirement recipeSR;
                    String skillRequirementDescription = "asdsad";
                    String skillRequirementUniqueId = "asda";
                    String skillRequirementName = "asdsa";
                    int skillRequirementType = 0;
                    // recipeSR = new SkillRequirement(skillRequirementDescription, skillRequirementUniqueId, skillRequirementName, skillRequirementType);
                    recipeSR = new SkillRequirement();
                    recipeSR.setDescription(skillRequirementDescription);
                    recipeSR.setUniqueId(skillRequirementUniqueId);
                    recipeSR.setName(skillRequirementName);
                    recipeSR.setType(skillRequirementType);
                    
                List<SkillRequirement> recipeSkillRequirements = new LinkedList<>(Arrays.asList(recipeSR));
            // r = new Recipe(recipeDescription, recipeUniqueId, recipeKpiSettings, recipeName, recipeParameterSettings, recipeUniqueAgentName, recipeSkillRequirements);
            r = new Recipe();
            r.setDescription(recipeDescription);
            r.setName(recipeName);
            r.setUniqueAgentName(recipeUniqueAgentName);
            r.setUniqueId(recipeUniqueId);
            // r.getKpisSetting().addAll(recipeKpiSettings);
            r.setKpisSetting(recipeKpiSettings);
            // r.getParametersSetting().addAll(recipeParameterSettings);
            r.setParametersSetting(recipeParameterSettings);
            // r.getSkillRequirements().addAll(recipeSkillRequirements);
            r.setSkillRequirements(recipeSkillRequirements);
            
        List<Recipe> cpadRecipes = new LinkedList<>(Arrays.asList(r));
        PhysicalLocation cpadPl;
           String physicalLocationReferenceFrameName = "asdas"; 
           long physicalLocationX = 0;
           long physicalLocationY = 0;
           long physicalLocationZ = 0;
           long physicalLocationAlpha = 0;
           long physicalLocationBeta = 0;
           long physicalLocationGamma = 0;
        // cpadPl = new PhysicalLocation(physicalLocationReferenceFrameName, physicalLocationX, physicalLocationY, physicalLocationZ, physicalLocationAlpha, physicalLocationBeta, physicalLocationGamma);
        cpadPl = new PhysicalLocation();
        cpadPl.setAlpha(physicalLocationAlpha);
        cpadPl.setBeta(physicalLocationBeta);
        cpadPl.setGamma(physicalLocationGamma);
        cpadPl.setReferenceFrameName(physicalLocationReferenceFrameName);
        cpadPl.setX(physicalLocationX);
        cpadPl.setY(physicalLocationY);
        cpadPl.setZ(physicalLocationZ);
        
        // LogicalLocation cpadLl = new LogicalLocation("asdad");
        LogicalLocation cpadLl = new LogicalLocation();
        cpadLl.setLocation("asdad");
        
            SkillRequirement cpadSR;
                String cpadSkillRequirementDescription = "asdsad";
                String cpadSkillRequirementUniqueId = "asda";
                String cpadSkillRequirementName = "asdsa";
                int cpadSkillRequirementType = 0;
            // cpadSR = new SkillRequirement(cpadSkillRequirementDescription, cpadSkillRequirementUniqueId, cpadSkillRequirementName, cpadSkillRequirementType);
            cpadSR = new SkillRequirement();
            cpadSR.setDescription(cpadSkillRequirementDescription);
            cpadSR.setName(cpadSkillRequirementName);
            cpadSR.setType(cpadSkillRequirementType);
            cpadSR.setUniqueId(cpadSkillRequirementUniqueId);
            
        List<SkillRequirement> cpadSkillRequirements = new LinkedList<>(Arrays.asList(recipeSR));
        // cpad = new CyberPhysicalAgentDescription(cpadUniqueName, cpadAgentClass,
        // cpadType, cpadParameters, cpadSkills, cpadRecipes, cpadPl, cpadLl, cpadSkillRequirements);

        cpad = new CyberPhysicalAgentDescription();
        cpad.setAgentClass(cpadAgentClass);
        cpad.setLogicalLocation(cpadLl);
        cpad.setPhysicalLocation(cpadPl);
        cpad.setType(cpadType);
        cpad.setUniqueName(cpadUniqueName);
        // cpad.getRecipes().addAll(cpadRecipes);
        cpad.setRecipes(cpadRecipes);
        // cpad.getSkillRequirements().addAll(cpadSkillRequirements);
        cpad.setSkillRequirements(cpadSkillRequirements);        
        // cpad.getSkills().addAll(cpadSkills);
        cpad.setSkills(cpadSkills);
        // cpad.getRecipes().addAll(cpadRecipes);
        cpad.setRecipes(cpadRecipes);
        // cpad.getAgentParameters().addAll(cpadParameters);
        cpad.setAgentParameters(cpadParameters);
        
        
        return cpad;        
        
        //        String cpadUniqueName = myAgentId;
//        String cpadAgentClass = "asdsa";
//        String cpadType = "resource_df_service";  //or transport_df_service
//        List<String> cpadParameters = new LinkedList<>(Arrays.asList("asdsad"));
//            Skill s;
//                String skillDescription = "SkillDescription";
//                String skillUniqueId = "SkillUniqueId";
//                    KPI kpi;
//                    String kpiDescription = "SkillKpiDescription";
//                    String kpiUniqueId = "SkillKpiUniqueId";
//                    String kpiName = "SkillKpiName";
//                    String kpiDefaultUpperBound = "SkillKpiDefaultUpperBound";
//                    String kpiDefaultLowerBound = "SkillKpiDefaultLowerBound";
//                    String kpiCurrentValue = "SkillKpiCurrentValue";
//                    String kpiUnit = "SkillKpiUnit";
//                    // kpi = new Kpi(kpiDescription, kpiUniqueId, kpiName, kpiDefaultUpperBound, kpiDefaultLowerBound, kpiCurrentValue, kpiUnit);
//                    // kpi = new Kpi(kpiDescription, kpiUniqueId, kpiName, kpiDefaultUpperBound, kpiDefaultLowerBound, kpiCurrentValue, kpiUnit);
//                    kpi = new KPI();
//                    kpi.setDescription(kpiDescription);
//                    kpi.setUniqueId(kpiUniqueId);
//                    kpi.setName(kpiName);
//                    kpi.setDefaultUpperBound(kpiDefaultUpperBound);
//                    kpi.setDefaultLowerBound(kpiDefaultLowerBound);
//                    kpi.setCurrentValue(kpiCurrentValue);
//                    kpi.setUnit(kpiUnit);
//                    
//                    
//                List<KPI> skillKpis = new LinkedList<>(Arrays.asList(kpi));
//                String skillName = "SkillName";
//                    Parameter p;
//                    String parameterDefaultValue = "SkillParameterDefaultValue";
//                    String parameterDescription = "SkillParameterDescription";
//                    String parameterUniqueId = "SkillParameterUniqueId";
//                    String parameterLowerBound = "SkillParameterLowerBound";
//                    String parameterUpperBound = "SkillParameterUpperBound";
//                    String parameterName = "SkillParameterName";
//                    String parameterUnit = "SkillParameterUnit";
//                    // p = new Parameter(parameterDefaultValue, parameterDescription, parameterUniqueId,                             parameterLowerBound, parameterUpperBound, parameterName, parameterUnit);
//                    p = new Parameter();
//                    p.setDefaultValue(parameterDefaultValue);
//                    p.setDescription(parameterDescription);
//                    p.setUniqueId(parameterUniqueId);
//                    p.setLowerBound(parameterLowerBound);
//                    p.setUpperBound(parameterUpperBound);
//                    p.setName(parameterName);
//                    p.setUnit(parameterUnit);
//                    
//                    
//                    
//                List<Parameter> skillParameters = new LinkedList<>(Arrays.asList(p));
//                int skillType = 0;
//            // s = new Skill(skillDescription, skillUniqueId, skillKpis, skillName, skillParameters, skillType);
//            s = new Skill();
//            s.setDescription(skillDescription);
//            s.setUniqueId(skillUniqueId);
//            // s.getKpis().addAll(skillKpis);
//            s.setKpis(skillKpis);
//            s.setName(skillName);
//            // s.getParameters().addAll(skillParameters);
//            s.setParameters(skillParameters);
//            s.setType(skillType);
//            
//            
//            
//            
//        List<Skill> cpadSkills = new LinkedList<>(Arrays.asList(s));
//            Recipe r;
//                String recipeDescription = "asdsad";
//                String recipeUniqueId = "asda";
//                    KPISetting recipeKpiSetting;
//                    String kpiSettingDescription = "asdsad";
//                    String kpiSettingId = "asda";
//                    String kpiSettingName = "asdsa";
//                    String kpiSettingValue = "asds";
//                    // recipeKpiSetting = new KPISetting(kpiSettingDescription, 
//                    // kpiSettingId, kpiSettingName, kpiSettingValue);
//                    recipeKpiSetting = new KPISetting();
//                    recipeKpiSetting.setDescription(kpiSettingDescription);
//                    recipeKpiSetting.setId(kpiSettingId);
//                    recipeKpiSetting.setName(kpiSettingName);
//                    recipeKpiSetting.setValue(kpiSettingValue);
//                    
//                List<KPISetting> recipeKpiSettings = new LinkedList<>(Arrays.asList(recipeKpiSetting));
//                String recipeName = "asda";
//                    ParameterSetting recipePS;
//                    String parameterSettingDescription = "asdsad";
//                    String parameterSettingId = "asda";
//                    String parameterSettingName = "asdsa";
//                    String parameterSettingValue = "asds";
//                    // recipePS = new ParameterSetting(parameterSettingDescription, parameterSettingId, parameterSettingName, parameterSettingValue);
//                    recipePS = new ParameterSetting();
//                    recipePS.setDescription(parameterSettingDescription);
//                    recipePS.setId(parameterSettingId);
//                    recipePS.setName(parameterSettingName);
//                    recipePS.setValue(parameterSettingValue);
//                    
//                List<ParameterSetting> recipeParameterSettings = new LinkedList<>(Arrays.asList(recipePS));
//                String recipeUniqueAgentName = "asdsad";
//                    SkillRequirement recipeSR;
//                    String skillRequirementDescription = "asdsad";
//                    String skillRequirementUniqueId = "asda";
//                    String skillRequirementName = "asdsa";
//                    int skillRequirementType = 0;
//                    // recipeSR = new SkillRequirement(skillRequirementDescription, skillRequirementUniqueId, skillRequirementName, skillRequirementType);
//                    recipeSR = new SkillRequirement();
//                    recipeSR.setDescription(skillRequirementDescription);
//                    recipeSR.setUniqueId(skillRequirementUniqueId);
//                    recipeSR.setName(skillRequirementName);
//                    recipeSR.setType(skillRequirementType);
//                    
//                List<SkillRequirement> recipeSkillRequirements = new LinkedList<>(Arrays.asList(recipeSR));
//            // r = new Recipe(recipeDescription, recipeUniqueId, recipeKpiSettings, recipeName, recipeParameterSettings, recipeUniqueAgentName, recipeSkillRequirements);
//            r = new Recipe();
//            r.setDescription(recipeDescription);
//            r.setName(recipeName);
//            r.setUniqueAgentName(recipeUniqueAgentName);
//            r.setUniqueId(recipeUniqueId);
//            // r.getKpisSetting().addAll(recipeKpiSettings);
//            r.setKpisSetting(recipeKpiSettings);
//            // r.getParametersSetting().addAll(recipeParameterSettings);
//            r.setParametersSetting(recipeParameterSettings);
//            // r.getSkillRequirements().addAll(recipeSkillRequirements);
//            r.setSkillRequirements(recipeSkillRequirements);
//            
//        List<Recipe> cpadRecipes = new LinkedList<>(Arrays.asList(r));
//        PhysicalLocation cpadPl;
//           String physicalLocationReferenceFrameName = "asdas"; 
//           long physicalLocationX = 0;
//           long physicalLocationY = 0;
//           long physicalLocationZ = 0;
//           long physicalLocationAlpha = 0;
//           long physicalLocationBeta = 0;
//           long physicalLocationGamma = 0;
//        // cpadPl = new PhysicalLocation(physicalLocationReferenceFrameName, physicalLocationX, physicalLocationY, physicalLocationZ, physicalLocationAlpha, physicalLocationBeta, physicalLocationGamma);
//        cpadPl = new PhysicalLocation();
//        cpadPl.setAlpha(physicalLocationAlpha);
//        cpadPl.setBeta(physicalLocationBeta);
//        cpadPl.setGamma(physicalLocationGamma);
//        cpadPl.setReferenceFrameName(physicalLocationReferenceFrameName);
//        cpadPl.setX(physicalLocationX);
//        cpadPl.setY(physicalLocationY);
//        cpadPl.setZ(physicalLocationZ);
//        
//        // LogicalLocation cpadLl = new LogicalLocation("asdad");
//        LogicalLocation cpadLl = new LogicalLocation();
//        cpadLl.setLocation("asdad");
//        
//            SkillRequirement cpadSR;
//                String cpadSkillRequirementDescription = "asdsad";
//                String cpadSkillRequirementUniqueId = "asda";
//                String cpadSkillRequirementName = "asdsa";
//                int cpadSkillRequirementType = 0;
//            // cpadSR = new SkillRequirement(cpadSkillRequirementDescription, cpadSkillRequirementUniqueId, cpadSkillRequirementName, cpadSkillRequirementType);
//            cpadSR = new SkillRequirement();
//            cpadSR.setDescription(cpadSkillRequirementDescription);
//            cpadSR.setName(cpadSkillRequirementName);
//            cpadSR.setType(cpadSkillRequirementType);
//            cpadSR.setUniqueId(cpadSkillRequirementUniqueId);
//            
//        List<SkillRequirement> cpadSkillRequirements = new LinkedList<>(Arrays.asList(recipeSR));
//        // cpad = new CyberPhysicalAgentDescription(cpadUniqueName, cpadAgentClass,
//        // cpadType, cpadParameters, cpadSkills, cpadRecipes, cpadPl, cpadLl, cpadSkillRequirements);
//
//        cpad = new CyberPhysicalAgentDescription();
//        cpad.setAgentClass(cpadAgentClass);
//        cpad.setLogicalLocation(cpadLl);
//        cpad.setPhysicalLocation(cpadPl);
//        cpad.setType(cpadType);
//        cpad.setUniqueName(cpadUniqueName);
//        // cpad.getRecipes().addAll(cpadRecipes);
//        cpad.setRecipes(cpadRecipes);
//        // cpad.getSkillRequirements().addAll(cpadSkillRequirements);
//        cpad.setSkillRequirements(cpadSkillRequirements);        
//        // cpad.getSkills().addAll(cpadSkills);
//        cpad.setSkills(cpadSkills);
//        // cpad.getRecipes().addAll(cpadRecipes);
//        cpad.setRecipes(cpadRecipes);
//        // cpad.getAgentParameters().addAll(cpadParameters);
//        cpad.setAgentParameters(cpadParameters);
// cpad.setType("resource_df_service"); //or transport_df_service
//                cpad.setUniqueName(myAgentId);

    }
    
    public AgentStatus RemoveAgent(String agentID){
        
        SystemConfigurator_Service systemConfiguratorService = new SystemConfigurator_Service();
        SystemConfigurator systemConfigurator = systemConfiguratorService.getSystemConfiguratorImplPort();

        String CLOUDINTERFACE_WS_VALUE = ConfigurationLoader.getMandatoryProperty("openmos.agent.cloud.cloudinterface.ws.endpoint");

        BindingProvider bindingProvider = (BindingProvider) systemConfigurator;
        bindingProvider.getRequestContext().put(
                // BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://192.168.15.5:9999/wsSystemConfigurator");
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY, CLOUDINTERFACE_WS_VALUE);

        AgentStatus agentStatus = systemConfigurator.removeAgent(agentID);

        return agentStatus;
        
    }
    
    private CyberPhysicalAgentDescription WorkStationDataToCPAD(RegFile datafromWorkStation) {
        
        String myAgentId = "Resource_" + Calendar.getInstance().getTimeInMillis(); //or Transport_...
        String cpadUniqueName = myAgentId;

        String cpadAgentClass = "asdsa"; //what is this?
        
        String cpadType=null; //select the agent type according to the workstation info
        if(datafromWorkStation.Type.equals("resource")){
             cpadType = "resource_df_service";  //or transport_df_service
        }else if(datafromWorkStation.Type.equals("transport")){
             cpadType = "transport_df_service";  //or transport_df_service
        }
        
        List<String> cpadParameters = new LinkedList<>(Arrays.asList("asdsad"));
        Skill s;
        String skillDescription = "SkillDescription";
        String skillUniqueId = "SkillUniqueId";
        KPI kpi;
        String kpiDescription = "SkillKpiDescription";
        String kpiUniqueId = "SkillKpiUniqueId";
        String kpiName = "SkillKpiName";
        String kpiDefaultUpperBound = "SkillKpiDefaultUpperBound";
        String kpiDefaultLowerBound = "SkillKpiDefaultLowerBound";
        String kpiCurrentValue = "SkillKpiCurrentValue";
        String kpiUnit = "SkillKpiUnit";
        // kpi = new Kpi(kpiDescription, kpiUniqueId, kpiName, kpiDefaultUpperBound, kpiDefaultLowerBound, kpiCurrentValue, kpiUnit);
        // kpi = new Kpi(kpiDescription, kpiUniqueId, kpiName, kpiDefaultUpperBound, kpiDefaultLowerBound, kpiCurrentValue, kpiUnit);
        kpi = new KPI();
        kpi.setDescription(kpiDescription);
        kpi.setUniqueId(kpiUniqueId);
        kpi.setName(kpiName);
        kpi.setDefaultUpperBound(kpiDefaultUpperBound);
        kpi.setDefaultLowerBound(kpiDefaultLowerBound);
        kpi.setCurrentValue(kpiCurrentValue);
        kpi.setUnit(kpiUnit);

        List<KPI> skillKpis = new LinkedList<>(Arrays.asList(kpi));
        String skillName = "SkillName";
        Parameter p;
        String parameterDefaultValue = "SkillParameterDefaultValue";
        String parameterDescription = "SkillParameterDescription";
        String parameterUniqueId = "SkillParameterUniqueId";
        String parameterLowerBound = "SkillParameterLowerBound";
        String parameterUpperBound = "SkillParameterUpperBound";
        String parameterName = "SkillParameterName";
        String parameterUnit = "SkillParameterUnit";
        // p = new Parameter(parameterDefaultValue, parameterDescription, parameterUniqueId,                             parameterLowerBound, parameterUpperBound, parameterName, parameterUnit);
        p = new Parameter();
        p.setDefaultValue(parameterDefaultValue);
        p.setDescription(parameterDescription);
        p.setUniqueId(parameterUniqueId);
        p.setLowerBound(parameterLowerBound);
        p.setUpperBound(parameterUpperBound);
        p.setName(parameterName);
        p.setUnit(parameterUnit);

        List<Parameter> skillParameters = new LinkedList<>(Arrays.asList(p));
        int skillType = 0;
        // s = new Skill(skillDescription, skillUniqueId, skillKpis, skillName, skillParameters, skillType);
        s = new Skill();
        s.setDescription(skillDescription);
        s.setUniqueId(skillUniqueId);
        // s.getKpis().addAll(skillKpis);
        s.setKpis(skillKpis);
        s.setName(skillName);
        // s.getParameters().addAll(skillParameters);
        s.setParameters(skillParameters);
        s.setType(skillType);

        //TODO! 12/04/17
        List<Skill> cpadSkills = new LinkedList<>(Arrays.asList(s));
        
        
        //Recipe data is already given by Workstation
        /*Recipe r;
        String recipeDescription = "asdsad";
        String recipeUniqueId = "asda";
        KPISetting recipeKpiSetting;
        String kpiSettingDescription = "asdsad";
        String kpiSettingId = "asda";
        String kpiSettingName = "asdsa";
        String kpiSettingValue = "asds";
        // recipeKpiSetting = new KPISetting(kpiSettingDescription, 
        // kpiSettingId, kpiSettingName, kpiSettingValue);
        recipeKpiSetting = new KPISetting();
        recipeKpiSetting.setDescription(kpiSettingDescription);
        recipeKpiSetting.setId(kpiSettingId);
        recipeKpiSetting.setName(kpiSettingName);
        recipeKpiSetting.setValue(kpiSettingValue);

        List<KPISetting> recipeKpiSettings = new LinkedList<>(Arrays.asList(recipeKpiSetting));
        String recipeName = "asda";
        ParameterSetting recipePS;
        String parameterSettingDescription = "asdsad";
        String parameterSettingId = "asda";
        String parameterSettingName = "asdsa";
        String parameterSettingValue = "asds";
        // recipePS = new ParameterSetting(parameterSettingDescription, parameterSettingId, parameterSettingName, parameterSettingValue);
        recipePS = new ParameterSetting();
        recipePS.setDescription(parameterSettingDescription);
        recipePS.setId(parameterSettingId);
        recipePS.setName(parameterSettingName);
        recipePS.setValue(parameterSettingValue);

        List<ParameterSetting> recipeParameterSettings = new LinkedList<>(Arrays.asList(recipePS));
        String recipeUniqueAgentName = "asdsad";
        SkillRequirement recipeSR;
        String skillRequirementDescription = "asdsad";
        String skillRequirementUniqueId = "asda";
        String skillRequirementName = "asdsa";
        int skillRequirementType = 0;
        // recipeSR = new SkillRequirement(skillRequirementDescription, skillRequirementUniqueId, skillRequirementName, skillRequirementType);
        recipeSR = new SkillRequirement();
        recipeSR.setDescription(skillRequirementDescription);
        recipeSR.setUniqueId(skillRequirementUniqueId);
        recipeSR.setName(skillRequirementName);
        recipeSR.setType(skillRequirementType);

        List<SkillRequirement> recipeSkillRequirements = new LinkedList<>(Arrays.asList(recipeSR));
        // r = new Recipe(recipeDescription, recipeUniqueId, recipeKpiSettings, recipeName, recipeParameterSettings, recipeUniqueAgentName, recipeSkillRequirements);
        r = new Recipe();
        r.setDescription(recipeDescription);
        r.setName(recipeName);
        r.setUniqueAgentName(recipeUniqueAgentName);
        r.setUniqueId(recipeUniqueId);
        // r.getKpisSetting().addAll(recipeKpiSettings);
        r.setKpisSetting(recipeKpiSettings);
        // r.getParametersSetting().addAll(recipeParameterSettings);
        r.setParametersSetting(recipeParameterSettings);
        // r.getSkillRequirements().addAll(recipeSkillRequirements);
        r.setSkillRequirements(recipeSkillRequirements);*/
        
        List<Recipe> cpadRecipes = new LinkedList<>();//(Arrays.asList(r));
        List<SkillRequirement> cpadSkillRequirements = new LinkedList<>();//(Arrays.asList(recipeSR));
        
        for (String key : datafromWorkStation.Recipes.keySet()) {
            Recipe recipe_inst = datafromWorkStation.Recipes.get(key);
            
            if (recipe_inst.getSkillRequirements() != null) {
                for (int i = 0; i < recipe_inst.getSkillRequirements().size(); i++) { //PROBLEM! TO SOLVE
                    cpadSkillRequirements.add(recipe_inst.getSkillRequirements().get(i));
                }
            }
            
        //populate KPIsettings as they aren't available yet
        KPISetting recipeKpiSetting;
        String kpiSettingDescription = "asdsad";
        String kpiSettingId = "asda";
        String kpiSettingName = "asdsa";
        String kpiSettingValue = "asds";
        // recipeKpiSetting = new KPISetting(kpiSettingDescription, 
        // kpiSettingId, kpiSettingName, kpiSettingValue);
        recipeKpiSetting = new KPISetting();
        recipeKpiSetting.setDescription(kpiSettingDescription);
        recipeKpiSetting.setId(kpiSettingId);
        recipeKpiSetting.setName(kpiSettingName);
        recipeKpiSetting.setValue(kpiSettingValue);

        List<KPISetting> recipeKpiSettings = new LinkedList<>(Arrays.asList(recipeKpiSetting));
        String recipeName = "asda";
        ParameterSetting recipePS;
        String parameterSettingDescription = "asdsad";
        String parameterSettingId = "asda";
        String parameterSettingName = "asdsa";
        String parameterSettingValue = "asds";
        // recipePS = new ParameterSetting(parameterSettingDescription, parameterSettingId, parameterSettingName, parameterSettingValue);
        recipePS = new ParameterSetting();
        recipePS.setDescription(parameterSettingDescription);
        recipePS.setId(parameterSettingId);
        recipePS.setName(parameterSettingName);
        recipePS.setValue(parameterSettingValue);
        List<ParameterSetting> recipeParameterSettings = new LinkedList<>(Arrays.asList(recipePS));
        
        recipe_inst.setKpisSetting(recipeKpiSettings);
        recipe_inst.setParametersSetting(recipeParameterSettings);
        /*
        String recipeUniqueAgentName = "asdsad";
        SkillRequirement recipeSR;
        String skillRequirementDescription = "asdsad";
        String skillRequirementUniqueId = "asda";
        String skillRequirementName = "asdsa";
        int skillRequirementType = 0;
        // recipeSR = new SkillRequirement(skillRequirementDescription, skillRequirementUniqueId, skillRequirementName, skillRequirementType);
        recipeSR = new SkillRequirement();
        recipeSR.setDescription(skillRequirementDescription);
        recipeSR.setUniqueId(skillRequirementUniqueId);
        recipeSR.setName(skillRequirementName);
        recipeSR.setType(skillRequirementType);*/
            
        cpadRecipes.add(recipe_inst); //add the received recipe to the list
            
        }

        //TODO when the workstation provide this info
        PhysicalLocation cpadPl;
        String physicalLocationReferenceFrameName = "Not yet implemented";
        long physicalLocationX = 0;
        long physicalLocationY = 0;
        long physicalLocationZ = 0;
        long physicalLocationAlpha = 0;
        long physicalLocationBeta = 0;
        long physicalLocationGamma = 0;
        // cpadPl = new PhysicalLocation(physicalLocationReferenceFrameName, physicalLocationX, physicalLocationY, physicalLocationZ, physicalLocationAlpha, physicalLocationBeta, physicalLocationGamma);
        cpadPl = new PhysicalLocation();
        cpadPl.setAlpha(physicalLocationAlpha);
        cpadPl.setBeta(physicalLocationBeta);
        cpadPl.setGamma(physicalLocationGamma);
        cpadPl.setReferenceFrameName(physicalLocationReferenceFrameName);
        cpadPl.setX(physicalLocationX);
        cpadPl.setY(physicalLocationY);
        cpadPl.setZ(physicalLocationZ);
        
        // LogicalLocation cpadLl = new LogicalLocation("asdad");
        LogicalLocation cpadLl = new LogicalLocation();
        cpadLl.setLocation(datafromWorkStation.LogicalLocation);
        

        SkillRequirement cpadSR;
        String cpadSkillRequirementDescription = "asdsad";
        String cpadSkillRequirementUniqueId = "asda";
        String cpadSkillRequirementName = "asdsa";
        int cpadSkillRequirementType = 0;
        // cpadSR = new SkillRequirement(cpadSkillRequirementDescription, cpadSkillRequirementUniqueId, cpadSkillRequirementName, cpadSkillRequirementType);
        cpadSR = new SkillRequirement();
        cpadSR.setDescription(cpadSkillRequirementDescription);
        cpadSR.setName(cpadSkillRequirementName);
        cpadSR.setType(cpadSkillRequirementType);
        cpadSR.setUniqueId(cpadSkillRequirementUniqueId);

        
        // cpad = new CyberPhysicalAgentDescription(cpadUniqueName, cpadAgentClass,
        // cpadType, cpadParameters, cpadSkills, cpadRecipes, cpadPl, cpadLl, cpadSkillRequirements);

        CyberPhysicalAgentDescription cpad = new CyberPhysicalAgentDescription();
        cpad.setAgentClass(cpadAgentClass);
        cpad.setLogicalLocation(cpadLl);
        cpad.setPhysicalLocation(cpadPl);
        cpad.setType(cpadType);
        cpad.setUniqueName(cpadUniqueName);
        // cpad.getRecipes().addAll(cpadRecipes);
        cpad.setRecipes(cpadRecipes);
        // cpad.getSkillRequirements().addAll(cpadSkillRequirements);
        cpad.setSkillRequirements(cpadSkillRequirements);
        // cpad.getSkills().addAll(cpadSkills);
        cpad.setSkills(cpadSkills);
        // cpad.getAgentParameters().addAll(cpadParameters);
        cpad.setAgentParameters(cpadParameters);

       
        
        //set received recipes on the cpad info
        /*List<Recipe> recipes = null;
                for (String key : parsedClass.Recipes.keySet()) {
                    recipes.add(parsedClass.Recipes.get(key));
                }
                cpad.setRecipes(recipes);*/

        return cpad;
    }

    /*private static AgentStatus createNewAgent(agentWebService.CyberPhysicalAgentDescription cyberPhysicalAgentDescription) {
        agentWebService.SystemConfigurator_Service service = new agentWebService.SystemConfigurator_Service();
        agentWebService.SystemConfigurator port = service.getSystemConfiguratorImplPort();
        return port.createNewAgent(cyberPhysicalAgentDescription);
    }

    private static AgentStatus removeAgent(java.lang.String agentUniqueName) {
        agentWebService.SystemConfigurator_Service service = new agentWebService.SystemConfigurator_Service();
        agentWebService.SystemConfigurator port = service.getSystemConfiguratorImplPort();
        return port.removeAgent(agentUniqueName);
    }

    private static AgentStatus removeAgent_1(java.lang.String agentUniqueName) {
        agentWebService.SystemConfigurator_Service service = new agentWebService.SystemConfigurator_Service();
        agentWebService.SystemConfigurator port = service.getSystemConfiguratorImplPort();
        return port.removeAgent(agentUniqueName);
    }*/

    /**
     * Send RecipeExecutionDone message with the ProductID to the AgentCloud using the respective VertX topic
     * @param args 
     */
    private String RecipeExecutionDone(String args) {
        //check ProductID
        String ProductID="1111"; //replace with productID in the received arguments
        //check which workstation sent
        String SenderName = "someAdaptor"; //replace with workstation name in the received arguments
 
        //check the sender opcClient object from its Name
        MyHashMaps myOpcUaClientsAgentsMap = MyHashMaps.getInstance(); //singleton to access hashmaps in other classes
        Map<String, MSB_MiloClientSubscription> OpcuaDeviceHashMap = myOpcUaClientsAgentsMap.getOPCclientIDMaps(); //get opcdevice name and object map
        MSB_MiloClientSubscription MiloClientID = OpcuaDeviceHashMap.get(SenderName);  //get the opcdevice objectID for the given Server Name

        Map<MSB_MiloClientSubscription, CyberPhysicalAgentDescription> DevAgentMap = myOpcUaClientsAgentsMap.getAgentDeviceIDMaps();
        CyberPhysicalAgentDescription agentObj = DevAgentMap.get(MiloClientID); //get the  agentID for the respective sender client object

        //already deployed when agent is created ?
        //Vertx.vertx().deployVerticle(new WebSocketsSender("5555")); //test! delete
        //vertx.deployVerticle(new WebSocketsSender(agentObj.getUniqueName()));
        
        
        if (withAGENTCloud) {
            //Send message on the respective AgentID topic/websocket
            String msgToSend = Constants.MSB_MESSAGE_TYPE_EXTRACTEDDATA + ProductID;
            List<String> ReplyMsg = new ArrayList<String>();

            vertx.eventBus().send(/*agentObj.getUniqueName()*/"5555", msgToSend, reply -> {
                        if (reply.succeeded()) {
                            System.out.println("vertX Received reply: " + reply.result().body());
                            String replyMessage = (String) reply.result().body().toString();
                            ReplyMsg.add(reply.result().body().toString());
                        } else {
                            System.out.println("vertX No reply");
                            ReplyMsg.add("vertX No reply");
                        }
                    });
            
            return ReplyMsg.get(0);
            
        } else {
            return "OK  - with no AgentCloud";
        }
         
                
    }

    public static String FiletoXMLtoObject(String filepath) throws ParserConfigurationException, SAXException, IOException, JAXBException
   {

       File fXmlFile = new File(filepath);
       DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
       DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
       Document doc = dBuilder.parse(fXmlFile);

       //optional, but recommended
       //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
       doc.getDocumentElement().normalize();
       String message = doc.getDocumentElement().getTextContent();

       System.out.println(message);
       
       JAXBContext jc = JAXBContext.newInstance(RegFile.class);
       Unmarshaller unmar = jc.createUnmarshaller();
       RegFile aux = (RegFile) unmar.unmarshal(doc);
        
        
       //not working renaxo
       /*DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
       DocumentBuilder db = null;

       try
       {
           db = dbf.newDocumentBuilder();
           InputSource is = new InputSource();
           is.setCharacterStream(new StringReader(filepath));
            //is.setCharacterStream(new StringReader(filepath));
           try
           {
               org.w3c.dom.Document doc = db.parse(is);
               String message = doc.getDocumentElement().getTextContent();
               System.out.println(message);
               JAXBContext jc = JAXBContext.newInstance(RegFile.class);
               Unmarshaller unmar = jc.createUnmarshaller();
               RegFile aux = (RegFile) unmar.unmarshal(doc);
               int i = 0;
           } catch (IOException | JAXBException | DOMException | SAXException e)
           {
            System.out.println("prob1: "+e);
           }
       } catch (ParserConfigurationException e1) 
       {
            System.out.println("prob2: "+e1);
       }*/

       return "";

   }
    
    
    public static void FileToStringToObject(String regFilexml) {
   /*    File fXmlFile = new File(regFilexml);
           DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder dBuilder = null;
           dBuilder = dbFactory.newDocumentBuilder();
           org.w3c.dom.Document doc = dBuilder.parse(regFilexml);            StringWriter sw = new StringWriter();
           TransformerFactory tf = TransformerFactory.newInstance();
           Transformer transformer = tf.newTransformer();
           transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
           transformer.setOutputProperty(OutputKeys.METHOD, "xml");
           transformer.setOutputProperty(OutputKeys.INDENT, "yes");
           transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
           transformer.transform(new DOMSource((Node) doc), new StreamResult(sw));
           return sw.toString();*/
    }
    
    
    public static String XMLtoString(String path)
   {
       try
       {
                     
           File fXmlFile = new File(path);
           DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
           Document doc = dBuilder.parse(fXmlFile);
           
           StringWriter sw = new StringWriter();
           TransformerFactory tf = TransformerFactory.newInstance();
           Transformer transformer = tf.newTransformer();
           transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
           transformer.setOutputProperty(OutputKeys.METHOD, "xml");
           transformer.setOutputProperty(OutputKeys.INDENT, "yes");
           transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
           transformer.transform(new DOMSource((Node) doc), new StreamResult(sw));
           return sw.toString();
       } catch (Exception ex)
       {
           throw new RuntimeException("Error converting to String", ex);
       }
   }    
    
    
    public static String StringtoXML(String stringFile)
   {
       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
       DocumentBuilder db = null;
       
       try
       {
           db = dbf.newDocumentBuilder();
           InputSource is = new InputSource();
           is.setCharacterStream(new StringReader(stringFile));
           try
           {
               org.w3c.dom.Document doc = db.parse(is);
               String message = doc.getDocumentElement().getTextContent();
               System.out.println(message);
               
               JAXBContext jc = JAXBContext.newInstance(RegFile.class);
               Unmarshaller unmar = jc.createUnmarshaller();
               
               RegFile aux = (RegFile) unmar.unmarshal(doc);
               int i = 0;
           } catch (Exception e)
           {
               // handle SAXException
           }
       } catch (Exception e1)
       {
           // handle ParserConfigurationException
       }
       return "";
   }
    
    
    public static void stringToDom(String xmlSource)
            throws SAXException, ParserConfigurationException, IOException, TransformerConfigurationException, TransformerException, JAXBException {
        // Parse the given input
        /*DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlSource)));

        // Write the parsed document to an xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(new File("my-file.xml"));
        transformer.transform(source, result);*/
        
        FiletoXMLtoObject("RegFile.xml");
        FileToStringToObject("RegFile.xml");
    }
    
    private String SendRecipe(String args) {

        //check which workstation sent
        String SenderName = "someAdaptor"; //replace with workstation name in the received arguments
 
        //check the sender opcClient object from its Name
        MyHashMaps myOpcUaClientsAgentsMap = MyHashMaps.getInstance(); //singleton to access hashmaps in other classes
        Map<String, MSB_MiloClientSubscription> OpcuaDeviceHashMap = myOpcUaClientsAgentsMap.getOPCclientIDMaps(); //get opcdevice name and object map
        MSB_MiloClientSubscription MiloClientID = OpcuaDeviceHashMap.get(SenderName);  //get the opcdevice objectID for the given Server Name

        Map<MSB_MiloClientSubscription, CyberPhysicalAgentDescription> DevAgentMap = myOpcUaClientsAgentsMap.getAgentDeviceIDMaps();
        CyberPhysicalAgentDescription agentObj = DevAgentMap.get(MiloClientID); //get the  agentID for the respective sender client object

        //already deployed when agent is created ?
        //Vertx.vertx().deployVerticle(new WebSocketsSender("5555")); //test! delete
        //vertx.deployVerticle(new WebSocketsSender(agentObj.getUniqueName()));
        
        //get agentID
        
        //get recipes sent by the workstation
        
        //send them to the respective agentID by publishing on its topic
        
         if (withAGENTCloud) {
            //Send message on the respective AgentID topic/websocket
            String msgToSend = Constants.MSB_MESSAGE_TYPE_APPLIEDRECIPES + args;
            List<String> ReplyMsg = new ArrayList<String>();

            vertx.eventBus().send(/*agentObj.getUniqueName()*/"5555", msgToSend, reply -> {
                        if (reply.succeeded()) {
                            System.out.println("vertX Received reply: " + reply.result().body());
                            String replyMessage = (String) reply.result().body().toString();
                            ReplyMsg.add(reply.result().body().toString());
                        } else {
                            System.out.println("vertX No reply");
                            ReplyMsg.add("vertX No reply");
                        }
                    });
            
            return ReplyMsg.get(0);
            
        } else {
            return "OK  - with no AgentCloud";
        }
         
    }
    
     

}
