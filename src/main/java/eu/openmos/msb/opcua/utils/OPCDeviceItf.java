/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.openmos.msb.opcua.utils;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import eu.openmos.agentcloud.cloudinterface.AgentStatus;
import eu.openmos.agentcloud.config.ConfigurationLoader;
import eu.openmos.model.LogicalLocation;
import eu.openmos.model.PhysicalLocation;
import eu.openmos.model.KPI;
import eu.openmos.model.KPISetting;
import eu.openmos.model.Parameter;
import eu.openmos.model.ParameterSetting;
import eu.openmos.model.Recipe;
import eu.openmos.model.Skill;
import eu.openmos.model.SkillRequirement;
import eu.openmos.agentcloud.utilities.Constants;
import eu.openmos.agentcloud.ws.systemconfigurator.wsimport.SystemConfigurator;
import eu.openmos.agentcloud.ws.systemconfigurator.wsimport.SystemConfigurator_Service;
import eu.openmos.model.Equipment;
import eu.openmos.model.SubSystem;
import eu.openmos.msb.cloudinterface.WebSocketsReceiver;
import eu.openmos.msb.cloudinterface.WebSocketsSender;
import eu.openmos.msb.cloudinterface.WebSocketsSenderDraft;
import eu.openmos.msb.messages.ChangedState;
import eu.openmos.msb.messages.RegFile;
import eu.openmos.msb.opcua.milo.client.MSB_MiloClientSubscription;
import eu.openmos.msb.starter.MSB_gui;
import eu.openmos.msb.datastructures.DACManager;
import eu.openmos.msb.datastructures.DeviceAdapter;
import eu.openmos.msb.messages.DaRecipe;
import eu.openmos.msb.messages.DaSkill;
import io.vertx.core.Vertx;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Observable;
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
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author fabio.miranda
 */
public class OPCDeviceItf extends Observable implements DeviceInterface
{

    Vertx vertx = Vertx.vertx();
    Boolean withAGENTCloud = false;
    Boolean fromFile = true;

    /**
     *
     * @param Function
     * @param args
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws JAXBException
     * @throws java.io.FileNotFoundException
     * @throws javax.xml.transform.TransformerException
     */
    public String AllCases(String Function, String args) throws ParserConfigurationException, SAXException, IOException, JAXBException, FileNotFoundException, TransformerException
    {
        String productID;
        String WorkstationID;
        String EntityID;
        String ProductID;
        String State = "";
        String Recipe;
        switch (Function.toLowerCase())
        {

            case "deployrecipe": //from resource agent to msb. from msb to workstation. 
                //param: workstation ID, recipe  
                WorkstationID = (String) args; //args will be a list of xml elements. use XML parser
                Recipe = (String) args; //args will be a list of xml elements. use XML parser
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

                ExecuteRecipeRampUpMode(WorkstationID, RecipeID);
                //do wtv
                break;
            case "uploadexecutiondata": //from workstation to msb. from msb to datalistener
                //param: Module ID, Data 
                String ModuleID = args; //args will be a list of xml elements. use XML parser
                String Data = (String) args; //args will be a list of xml elements. use XML parser

                UploadExecutionData(ModuleID, Data);
                //do wtv
                break;
            case "statusupdate": //from workstation to msb. from msb to datalistener
                //param: State   
                State = (String) args; //args will be a list of xml elements. use XML parser

                return StatusUpdate(State);

            case "changerecipestate": //from workstation to msb. from msb to datalistener
                //param: WorkstationID,RecipeID, State   
                WorkstationID = args;
                RecipeID = (String) args;
                State = (String) args; //args will be a list of xml elements. use XML parser

                ChangeRecipeState(WorkstationID, RecipeID, State);
                //do wtv
                break;
            case "requestproduct": //from workstation to msb. from msb to datalistener
                //param: ProductID  
                ProductID = args;

                RequestProduct(ProductID);
                //do wtv
                break;
            case "deploynewrecipe": //from workstation to msb. from msb to datalistener
                //param: EntityID, Recipe, ProductID  
                EntityID = args;
                Recipe = (String) args;
                ProductID = args;

                DeployNewRecipe(EntityID, Recipe, ProductID);
                //do wtv
                break;
            case "requestmoduledetails": //from resource agents to msb. from msb to workstation. from datalistener to msb. from msb to workstation
                //params ?
                RequestModuleDetails();
                break;
            case "selfdescription": //from workstation to msb. from msb to datalistener
                productID = (String) args; //args will be a list of xml elements. use XML parser
                break;
            case "requestworkstationinformation": //from skill agreggator to msb. from msb to workstation
                WorkstationID = args;
                RequestWorkstationInformation(WorkstationID);
                break;
            case "provideselfdescription": //from workstation to msb. from msb to skill agreggator
                //do wtv
                //enviar requestworkstationinformation para workstation e esperar ProvideSelfDescription
                break;

            case "executeskill":
                ProductID = args; //args will be a list of xml elements. use XML parser
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
                String ID = args;
                String EquipmentType = (String) args;
                String SelfDescriptionServiceCall = (String) args;
                BroadcastPresence(ID, EquipmentType, SelfDescriptionServiceCall);
                break;
            case "sendRecipe":
                //active for the 1st demonstrator
                return SendRecipe(args);

            //from workstation to msb. from msb to equipment module, deployment agent and data listener
            case "deviceregistration":
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

    /**
     *
     * @param Function
     * @param args
     */
    @Override
    public void ExecutionData(String Function, List args)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String productID;
        int WorkstationID = 0;
        String EntityID;
        int ProductID = 0;
        String State = "";
        String Recipe;
        switch (Function.toLowerCase())
        {

            case "deployrecipe": //from resource agent to msb. from msb to workstation. 
                //param: workstation ID, recipe  
                WorkstationID = (int) args.get(0); //args will be a list of xml elements. use XML parser
                Recipe = (String) args.get(1); //args will be a list of xml elements. use XML parser
                //DeployRecipe(WorkstationID, Recipe);

                //do wtv
                break;
            case "updaterecipe": //from workstation to msb. from msb to datalistener
                //param: workstation ID, OldRecipeID, NewRecipe 
                EntityID = (String) args.get(0); //args will be a list of xml elements. use XML parser
                String OldRecipeID = (String) args.get(1); //args will be a list of xml elements. use XML parser
                String NewRecipe = (String) args.get(2); //args will be a list of xml elements. use XML parser
                UpdateRecipe(EntityID, NewRecipe);
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

    /**
     *
     * @param Function
     * @param args
     */
    @Override
    public void EquipmentData(String Function, List args)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String productID;

        switch (Function.toLowerCase())
        {

            case "requestmoduledetails": //from resource agents to msb. from msb to workstation. from datalistener to msb. from msb to workstation
                //params ?
                RequestModuleDetails();
                break;
            case "selfdescription": //from workstation to msb. from msb to datalistener
                productID = (String) args.get(0); //args will be a list of xml elements. use XML parser
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

    /**
     *
     * @param Function
     * @param args
     */
    @Override
    public void EquipmentCommunication(String Function, List args)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        int productID;
        switch (Function.toLowerCase())
        {
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
                /*
         * String ExecutionState = (String) args; productID = args; String RecipeID = args; String KPIs = (String) args;
                 */

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

    /**
     *
     * @param Function
     * @param args
     */
    @Override
    public void EquipmentRegistration(String Function, List args)
    {

        String productID;

        switch (Function.toLowerCase())
        {
            case "resquestlocalModulesregistration": //from workstation to msb. from msb to equipment module
                //do wtv

                break;

            //from workstation to msb. from msb to equipment module, deployment agent and data listener
            case "broadcastpresence":
                //params: ID, EquipmentType, SelfDescriptionServiceCall
                int ID = (int) args.get(0);
                String EquipmentType = (String) args.get(1);
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
    public void DeployRecipe(String WorkstationID, String Recipe)
    {
        //send to workstation
        //DeployRecipe(Recipe) //send to workstation opc
        //wait for ConfirmRecipeDeployment(Recipe)
        //return Recipe //send back to Resource Agent
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param WorkstationID
     */
    public void RequestWorkstationInformation(String WorkstationID)
    {
        //send to workstation
        //RequestWorkstationInformation() //send to workstation opc
        //wait for ProvideSelfDescription(SelfDescriptionInformation)
        //ProvideSelfDescription(SelfDescriptionInformation) //send to Skill Aggregator3.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param EntityID
     * @param NewRecipe
     */
    private void UpdateRecipe(String EntityID, String NewRecipe)
    {
        //From SkillAggregator OR Resourceagent OR TransportAgent
        //1-ver para quem é o EntityID e enviar -> pode ser workstationID ou TransportID. É transparente para o MSB
        //2-retornar resposta
        //UpdateRecipe(String OldRecipeID, String NewRecipe) //enviar para o EntityID
        //wait for ConfirmRecipeDeployment(RecipeID)
        //return RecipeID para EntityID

        //  if(from SkillAggregator){ }else if(From Resourceagent){ }else if(From TransportAgent){ } //indiferente
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param WorkstationID
     * @param RecipeID
     */
    private void ExecuteRecipeRampUpMode(String WorkstationID, String RecipeID)
    {
        //from skill aggregator
        //call ExecuteRecipeRampUpMode(RecipeID) //to workstation
        //wait for ExecuteSkill(ModuleID,RecipeID)
        //change this??
        //call ExecuteSkill(RecipeID)
        //wait for CompletedSkillExecution(RecipeID)
        //call CompletedSkillExecution(RecipeID)
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param ModuleID
     * @param Data
     */
    private void UploadExecutionData(String ModuleID, String Data)
    {
        //from EquipmentModule or Workstation
        //leitura de nós especificos, do cliente msb para o servidor do device
        //call UploadExecutionData(ModuleID, Data) //colocar na Base De Dados
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param State
     * @return
     */
    private String StatusUpdate(String State)
    {

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //from workstation
        //call StatusUpdate(State,...) //send to ResourceAgent
        //what to send?
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param WorkstationID
     * @param RecipeID
     * @param State
     */
    private void ChangeRecipeState(String WorkstationID, String RecipeID, String State)
    {
        //from skill aggregator
        //call ChangeRecipeState(RecipeID, state) //send to workstation
        //wait for ConfirmRecipestate(RecipeID, State)
        //return ConfirmRecipestate(RecipeID, State) //to skillaggregator
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param ProductID
     * @return [TODO] if from production Optimizer Agent - webservice? call
     * RequestProduct(ProductID) //to lauch order service wait for return
     * RequestProduct(ProductID) //from launch order service if launch order
     * service - opcua call ExecuteSkill(ProductID) //to Workstation wait for
     * return ExecuteSkill(Recipe,EquipmentID) //from Workstation or wait for
     * NoRecipe(Recipe,ProductID //from workstation call ExecuteSkill(Recipe)
     * //to equipment?? change this wait for RecipeExecutionDone(RecipeID)
     * //change this call RecipeExecutionDone(RecipeID) //change this
     */
    private String RequestProduct(String ProductID)
    {

        /*
     * //check the sender opcClient object from its Name DACManager myMaps = DACManager.getInstance(); //singleton to
     * access hashmaps in other classes Map<String, MSB_MiloClientSubscription> ProductAdapterHashMap =
     * myMaps.getProductIDAdapterMaps(); //get opcdevice name and object map MSB_MiloClientSubscription MiloClientID =
     * ProductAdapterHashMap.get(ProductID); //get the opcdevice objectID for the given ProductID
     * CompletableFuture<String> RequestProductResponse =
     * MiloClientID.RequestProduct(MiloClientID.milo_client_instanceMSB, ProductID); //call the method on the respective
     * Device
     *
     *
     * try { return RequestProductResponse.get(); } catch (InterruptedException | ExecutionException ex) {
     * Logger.getLogger(OPCDeviceItf.class.getName()).log(Level.SEVERE, null, ex); return ex.getMessage(); }
         */
        return null;
    }

    /**
     * Send ChangedState to the AgentCloud
     *
     * @param ExecutionState
     * @param productID
     * @param RecipeID
     * @param KPIs
     */
    private String ChangedState(String args)
    {
        //from workstation

        //parse xml SelfDescriptionInformation
        String senderName = args.split("\\:")[0]; //get sender name
        String Alldata = args.substring(args.indexOf(':') + 1); //get XML data
        ChangedState parsedClass = null;

        //STRING TO XML
        DocumentBuilderFactory dfctr = DocumentBuilderFactory.newInstance();
        DocumentBuilder bldr = null;
        try
        {
            bldr = dfctr.newDocumentBuilder();
            InputSource insrc = new InputSource();
            insrc.setCharacterStream(new StringReader(Alldata));
            org.w3c.dom.Document docres = null;
            docres = bldr.parse(insrc);
            System.out.println("\n\nThis is the XML received: " + docres.getDocumentElement().getTextContent() + "\n\n");
            JAXBContext jaxbContext = JAXBContext.newInstance(ChangedState.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            parsedClass = (ChangedState) unmarshaller.unmarshal(docres);
        } catch (ParserConfigurationException | SAXException | IOException ex)
        {
            Logger.getLogger(OPCDeviceItf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException | DOMException ex)
        {
            System.out.println("\n Problem parsing class: " + ex.toString());
        }

        DeviceAdapter da = DACManager.getInstance().getDeviceAdapter(senderName);
        //get the opcdevice objectID for the given Server Name
        MSB_MiloClientSubscription MiloClientID = (MSB_MiloClientSubscription) da.getClient();

        if (withAGENTCloud)
        {

            SubSystem agentObj = da.getSubSystem(); //get the  agentID for the respective sender client object

            //already deployed when agent is created ?
            Vertx.vertx().deployVerticle(new WebSocketsSender("5555")); //test! delete
            List<String> ReplyMsg = new ArrayList<String>();

            //Send message on the respective AgentID topic/websocket
            String msgToSend = Constants.MSB_MESSAGE_TYPE_EXTRACTEDDATA + ";" + parsedClass.recipeID + ";" + parsedClass.productID + ";";

            // TODO - talk to pedro aboud the naming of this parameter it sould be something like adapter_id or other thing
            // not Equipment
            // missing uniqueId in the SubsSystem Class defenition
            vertx.eventBus().send(agentObj.getName(), msgToSend, reply
                    ->
            {
                if (reply.succeeded())
                {
                    System.out.println("vertX Received reply: " + reply.result().body());
                    ReplyMsg.add(reply.result().body().toString());
                } else
                {
                    System.out.println("vertX No reply");
                    ReplyMsg.add("vertX No reply");
                }
            });
            return ReplyMsg.get(0); //ADD VERTX MESSAGE REPLY
        } else
        {
            return "OK - No AgentCloud";
        }

    }

    /**
     *
     * @param EntityID
     * @param Recipe
     * @param ProductID from resource Agent or transport Agent call
     * DeployNewRecipe(Recipe,ProductID) //to EntityID - workstation or
     * transport equipment wait for return RecipeDeployed(Recipe,ProductID)
     * return RecipeDeployed(WorkstationID, Recipe,ProductID) //back to sender.
     */
    private void DeployNewRecipe(String EntityID, String Recipe, String ProductID)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param ID
     * @param EquipmentType
     * @param SelfDescriptionServiceCall
     */
    private void BroadcastPresence(String ID, String EquipmentType, String SelfDescriptionServiceCall)
    {
        //from workstation to msb. from msb to equipment module, deployment agent and data listener

        //call BroadcastPresence(ID, EquipmentType, SelfDescriptionServiceCall) //to equipment module
        //call BroadcastPresence(ID, EquipmentType, SelfDescriptionServiceCall) //to deployment Agent
        //call BroadcastPresence(ID, EquipmentType, SelfDescriptionServiceCall) //to DataListener (DB)
        //dbMSB.register_device(DevName, ShortInfo, LongInfo, Ip_addr, Protocol);
        //ArrayList Arrays= dbMSB.get_device_address_protocol(ID); //get device address and protocol
        //System.out.println(Arrays.toString());
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     */
    private void RequestModuleDetails()
    {
        throw new UnsupportedOperationException("Not supported yet.");
        //from Resource Agent or Data Listener (DB)
        //call RequestModuleDetails
    }

    /**
     *
     * @param args
     * @return
     * @throws JAXBException
     * @throws FileNotFoundException
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    private String WorkStationRegistration(String args)
            throws JAXBException, FileNotFoundException, ParserConfigurationException, TransformerException
    {
        // global instance for managing device adapterss
        DACManager instance = DACManager.getInstance();

        // for performance monitoring
        System.out.println("\n\n WorkStationRegistration message has arrived \n\n");
        long start = System.currentTimeMillis();

        //parse xml SelfDescriptionInformation
        String senderName = args.split("\\:")[0]; //get sender name
        String Alldata = args.substring(args.indexOf(':') + 1); //get XML data
        RegFile parsedClass = null;

        try
        {
            if (fromFile)
            {
                // simulated that the device has sent the regfile
                // this overwrites the regfile send from th DA
                parsedClass = FiletoXMLtoObject("RegFile.xml");
            } else
            {
                // Pass the XML file (RegFile) to java classes
                DocumentBuilderFactory dfctr = DocumentBuilderFactory.newInstance();
                DocumentBuilder bldr = null;
                bldr = dfctr.newDocumentBuilder();
                InputSource insrc = new InputSource();
                insrc.setCharacterStream(new StringReader(Alldata));
                org.w3c.dom.Document docres = null;
                docres = bldr.parse(insrc);
                System.out.println("\n\nThis is the XML received: " + docres.getDocumentElement().getTextContent() + "\n\n");
                JAXBContext jaxbContext = JAXBContext.newInstance(RegFile.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                parsedClass = (RegFile) unmarshaller.unmarshal(docres);
            }

            // --------------------------------------------------------------------------------------------------------------
            DeviceAdapter da = instance.getDeviceAdapter(senderName);

            if (parsedClass.getDevicesTable().size() > 0)
            {
                for (String key : parsedClass.getDevicesTable().keySet())
                {
                    Equipment device = (Equipment) parsedClass.getDevicesTable().get(key);
                     da.addEquipmentModule(device);
                }
                MSB_gui.fillDevicesTable();
            }

            if (parsedClass.getSkillsTable().size() > 0)
            {
                for (String key : parsedClass.getSkillsTable().keySet())
                {
                    DaSkill skill = parsedClass.getSkillsTable().get(key);
                    instance.registerSkill(senderName, skill.getAmlId(), skill.getName(), skill.getDescription());
                }
            }

            if (parsedClass.getRecipesTable().size() > 0)
            {
                for (String key : parsedClass.getRecipesTable().keySet())
                {
                    DaRecipe r = parsedClass.getRecipesTable().get(key);
                    instance.registerRecipe(senderName, r.getAmlId(), r.getSkill(), r.getValid(), r.getName());
                }
                MSB_gui.fillRecipesTable();
            }

            // --------------------------------------------------------------------------------------------------------------
            System.out.println("\n\n\n Parsed class: " + parsedClass.getName() + "\n\n\n");

            if (withAGENTCloud)
            {
                // THIS CODE IS WORKING!! 
                SystemConfigurator_Service systemConfiguratorService = new SystemConfigurator_Service();
                SystemConfigurator systemConfigurator = systemConfiguratorService.getSystemConfiguratorImplPort();
                String CLOUDINTERFACE_WS_VALUE = ConfigurationLoader.getMandatoryProperty("openmos.agent.cloud.cloudinterface.ws.endpoint");
                BindingProvider bindingProvider = (BindingProvider) systemConfigurator;
                bindingProvider.getRequestContext().put(
                        BindingProvider.ENDPOINT_ADDRESS_PROPERTY, CLOUDINTERFACE_WS_VALUE);
                SubSystem cpad = dummySubSystemGeneration(parsedClass);

                AgentStatus agentStatus = systemConfigurator.createNewAgent(cpad);
                System.out.println("\n\n Creating Resource or Transport Agent... \n\n");
                String msgToSend = Constants.MSB_MESSAGE_TYPE_EXTRACTEDDATA + "anything";
                //Vertx.vertx().deployVerticle(new WebSocketsSender(cpad.getUniqueName())); // TODO - DELETE THIS

                //add the sender client object to the respective agentID
                da.setSubSystem(cpad);
                return agentStatus.getCode(); //OK? ou KO?

            } else
            {
                //call mainwindow filltables
                MSB_gui.fillRecipesTable();
                vertx.deployVerticle(new WebSocketsReceiver("R1"));
                vertx.deployVerticle(new WebSocketsReceiver("R2"));

                Thread.sleep(3000);

                vertx.deployVerticle(new WebSocketsSenderDraft());

                return "OK - No AgentPlatform";

            }
        } catch (SAXException | IOException | ParserConfigurationException | JAXBException | DOMException ex)
        {
            Logger.getLogger(OPCDeviceItf.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Problems parsing the RegFile");
        } catch (InterruptedException ex)
        {
            Logger.getLogger(OPCDeviceItf.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex)
        {
            System.out.println("[ERROR]   "  +  ex.getMessage());
        }
        finally
        {
            long elapsedTime = (long) ((System.currentTimeMillis() - start) / 1000F);
            System.out.println("\n\n ELAPSED TIME: " + elapsedTime + "s");
        }
        return null;
    }

    /**
     *
     * @param datafromWorkStation
     * @return
     */
    private SubSystem dummySubSystemGeneration(RegFile datafromWorkStation)
    {

        SubSystem cpad = new SubSystem();
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
        
        p = new Parameter();
        p.setDefaultValue(parameterDefaultValue);
        p.setDescription(parameterDescription);
        p.setUniqueId(parameterUniqueId);
        p.setLowerBound(parameterLowerBound);
        p.setUpperBound(parameterUpperBound);
        p.setName(parameterName);
        p.setUnit(parameterUnit);

        List<Parameter> skillParameters = new LinkedList<>(Arrays.asList(p));
        String skillType = "test";
        
        s = new Skill();
        s.setDescription(skillDescription);
        s.setUniqueId(skillUniqueId);
        s.setKpis(skillKpis);
        s.setName(skillName);
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
        
        recipePS = new ParameterSetting();
        recipePS.setDescription(parameterSettingDescription);
        recipePS.setUniqueId(parameterSettingId);
        recipePS.setName(parameterSettingName);
        recipePS.setValue(parameterSettingValue);

        List<ParameterSetting> recipeParameterSettings = new LinkedList<>(Arrays.asList(recipePS));
        String recipeUniqueAgentName = "asdsad";
        SkillRequirement recipeSR;
        String skillRequirementDescription = "asdsad";        
        String skillRequirementName = "asdsa";
        String skillRequirementType = "test";
        recipeSR = new SkillRequirement();
        recipeSR.setDescription(skillRequirementDescription);
        
        recipeSR.setName(skillRequirementName);
        recipeSR.setType(skillRequirementType);

        List<SkillRequirement> recipeSkillRequirements = new LinkedList<>(Arrays.asList(recipeSR));
        r = new Recipe();
        r.setDescription(recipeDescription);
        r.setName(recipeName);
        r.setUniqueAgentName(recipeUniqueAgentName);
        r.setUniqueId(recipeUniqueId);
        // r.getKpisSetting().addAll(recipeKpiSettings);
        r.setKpiSettings(recipeKpiSettings);
        // r.getParametersSetting().addAll(recipeParameterSettings);
        r.setParameterSettings(recipeParameterSettings);
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
        
        cpadPl = new PhysicalLocation();
        cpadPl.setAlpha(physicalLocationAlpha);
        cpadPl.setBeta(physicalLocationBeta);
        cpadPl.setGamma(physicalLocationGamma);
        cpadPl.setReferenceFrameName(physicalLocationReferenceFrameName);
        cpadPl.setX(physicalLocationX);
        cpadPl.setY(physicalLocationY);
        cpadPl.setZ(physicalLocationZ);

        LogicalLocation cpadLl = new LogicalLocation();
        cpadLl.setLocation("asdad");

        SkillRequirement cpadSR;
        String cpadSkillRequirementDescription = "asdsad";
        String cpadSkillRequirementUniqueId = "asda";
        String cpadSkillRequirementName = "asdsa";
        String cpadSkillRequirementType = "test";
        
        cpadSR = new SkillRequirement();
        cpadSR.setDescription(cpadSkillRequirementDescription);
        cpadSR.setName(cpadSkillRequirementName);
        cpadSR.setType(cpadSkillRequirementType);
        //cpadSR.setUniqueId(cpadSkillRequirementUniqueId); // its missing TOOD

        List<SkillRequirement> cpadSkillRequirements = new LinkedList<>(Arrays.asList(recipeSR));
        
        cpad = new SubSystem();
        cpad.setLogicalLocation(cpadLl);
        cpad.setPhysicalLocation(cpadPl);
        cpad.setType(cpadType);
        //cpad.setEquipmentId(cpadUniqueName);
        cpad.setRecipes(cpadRecipes);
        cpad.setSkills(cpadSkills);
        cpad.setRecipes(cpadRecipes);
        
        return cpad;
    }

    /**
     *
     * @param agentID
     * @return
     */
    public AgentStatus RemoveAgent(String agentID)
    {

        SystemConfigurator_Service systemConfiguratorService = new SystemConfigurator_Service();
        SystemConfigurator systemConfigurator = systemConfiguratorService.getSystemConfiguratorImplPort();

        String CLOUDINTERFACE_WS_VALUE = ConfigurationLoader.getMandatoryProperty("openmos.agent.cloud.cloudinterface.ws.endpoint");

        BindingProvider bindingProvider = (BindingProvider) systemConfigurator;
        bindingProvider.getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY, CLOUDINTERFACE_WS_VALUE);

        AgentStatus agentStatus = systemConfigurator.removeAgent(agentID);

        return agentStatus;

    }

    /**
     *
     * @param datafromWorkStation
     * @return
     */
    private SubSystem WorkStationDataToSubSystem(RegFile datafromWorkStation)
    {

        String myAgentId = "Resource_" + Calendar.getInstance().getTimeInMillis(); //or Transport_...
        String cpadUniqueName = myAgentId;

        String cpadAgentClass = "asdsa"; //what is this?

        String cpadType = null; //select the agent type according to the workstation info
        if (datafromWorkStation.getType().equals("resource"))
        {
            cpadType = "resource_df_service";  //or transport_df_service
        } else if (datafromWorkStation.getType().equals("transport"))
        {
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

        p = new Parameter();
        p.setDefaultValue(parameterDefaultValue);
        p.setDescription(parameterDescription);
        p.setUniqueId(parameterUniqueId);
        p.setLowerBound(parameterLowerBound);
        p.setUpperBound(parameterUpperBound);
        p.setName(parameterName);
        p.setUnit(parameterUnit);

        List<Parameter> skillParameters = new LinkedList<>(Arrays.asList(p));
        String skillType = "test";
        s = new Skill();
        s.setDescription(skillDescription);
        s.setUniqueId(skillUniqueId);
        s.setKpis(skillKpis);
        s.setName(skillName);
        s.setParameters(skillParameters);
        s.setType(skillType);

        List<Skill> cpadSkills = new LinkedList<>(Arrays.asList(s));
        List<Recipe> cpadRecipes = new LinkedList<>();
        List<SkillRequirement> cpadSkillRequirements = new LinkedList<>();

        // af-silva changes made here, this should not work
        for (String key : datafromWorkStation.getRecipesTable().keySet())
        {
            Recipe recipe_inst = new Recipe();
            DaRecipe temp = datafromWorkStation.getRecipesTable().get(key);

            if (temp.getSkillRequirements() != null)
            {
                for (int i = 0; i < temp.getSkillRequirements().size(); i++)
                {
                    // PROBLEM! TO SOLVE 
                    // The class contains different data from the agents representation
                    //cpadSkillRequirements.add(temp.getSkillRequirements().get(i));
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
            recipePS.setUniqueId(parameterSettingId);
            recipePS.setName(parameterSettingName);
            recipePS.setValue(parameterSettingValue);
            List<ParameterSetting> recipeParameterSettings = new LinkedList<>(Arrays.asList(recipePS));

            recipe_inst.setKpiSettings(recipeKpiSettings);
            recipe_inst.setParameterSettings(recipeParameterSettings);
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
        cpadLl.setLocation(datafromWorkStation.getLogicalLocation());

        SkillRequirement cpadSR;
        String cpadSkillRequirementDescription = "asdsad";
        String cpadSkillRequirementUniqueId = "asda";
        String cpadSkillRequirementName = "asdsa";
        String cpadSkillRequirementType = "test";
        // cpadSR = new SkillRequirement(cpadSkillRequirementDescription, cpadSkillRequirementUniqueId, cpadSkillRequirementName, cpadSkillRequirementType);
        cpadSR = new SkillRequirement();
        cpadSR.setDescription(cpadSkillRequirementDescription);
        cpadSR.setName(cpadSkillRequirementName);
        cpadSR.setType(cpadSkillRequirementType);
        //cpadSR.setUniqueId(cpadSkillRequirementUniqueId);

        // cpad = new CyberPhysicalAgentDescription(cpadUniqueName, cpadAgentClass,
        // cpadType, cpadParameters, cpadSkills, cpadRecipes, cpadPl, cpadLl, cpadSkillRequirements);
        SubSystem cpad = new SubSystem();
        
        cpad.setLogicalLocation(cpadLl);
        cpad.setPhysicalLocation(cpadPl);
        cpad.setType(cpadType);
        //cpad.setEquipmentId(cpadUniqueName);
        cpad.setRecipes(cpadRecipes);
        cpad.setSkills(cpadSkills);
        
       
        return cpad;
    }

    /**
     * Send RecipeExecutionDone message with the ProductID to the AgentCloud
     * using the respective VertX topic
     *
     * @param args
     */
    private String RecipeExecutionDone(String args)
    {
        //check ProductID
        String ProductID = "1111"; //replace with productID in the received arguments
        //check which workstation sent
        String SenderName = "someAdaptor"; //replace with workstation name in the received arguments

        DACManager instance = DACManager.getInstance();
        SubSystem agentObj = instance.getDeviceAdapter(SenderName).getSubSystem();

        if (withAGENTCloud)
        {
            //Send message on the respective AgentID topic/websocket
            String msgToSend = Constants.MSB_MESSAGE_TYPE_EXTRACTEDDATA + ProductID;
            List<String> ReplyMsg = new ArrayList<String>();

            vertx.eventBus().send(/*
         * agentObj.getUniqueName()
                     */"5555", msgToSend, reply
                    ->
            {
                if (reply.succeeded())
                {
                    System.out.println("vertX Received reply: " + reply.result().body());
                    String replyMessage = (String) reply.result().body().toString();
                    ReplyMsg.add(reply.result().body().toString());
                } else
                {
                    System.out.println("vertX No reply");
                    ReplyMsg.add("vertX No reply");
                }
            });

            return ReplyMsg.get(0);

        } else
        {
            return "OK  - with no AgentCloud";
        }

    }

    /**
     *
     * @param filepath
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws JAXBException
     */
    public RegFile FiletoXMLtoObject(String filepath) throws ParserConfigurationException, SAXException, IOException, JAXBException
    {

        File fXmlFile = new File(filepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        // http://howtodoinjava.com/jaxb/jaxb-example-marshalling-and-unmarshalling-hashmap-in-java/
        doc.getDocumentElement().normalize();
        String message = doc.getDocumentElement().getTextContent();

        System.out.println(message);

        JAXBContext jc = JAXBContext.newInstance(RegFile.class);
        Unmarshaller unmar = jc.createUnmarshaller();
        RegFile aux = (RegFile) unmar.unmarshal(doc);

        return aux;

    }

    /**
     *
     * @param regFilexml
     */
    public static void FileToStringToObject(String regFilexml)
    {
        /*
     * File fXmlFile = new File(regFilexml); DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
     * DocumentBuilder dBuilder = null; dBuilder = dbFactory.newDocumentBuilder(); org.w3c.dom.Document doc =
     * dBuilder.parse(regFilexml); StringWriter sw = new StringWriter(); TransformerFactory tf =
     * TransformerFactory.newInstance(); Transformer transformer = tf.newTransformer();
     * transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
     * transformer.setOutputProperty(OutputKeys.METHOD, "xml"); transformer.setOutputProperty(OutputKeys.INDENT, "yes");
     * transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); transformer.transform(new DOMSource((Node) doc), new
     * StreamResult(sw)); return sw.toString();
         */
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param path
     * @return
     */
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

    /**
     *
     * @param stringFile
     * @return
     */
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
            } catch (IOException | JAXBException | DOMException | SAXException ex)
            {
                // TODO handle SAXException
            }
        } catch (ParserConfigurationException ex)
        {
            // TODO handle ParserConfigurationException
        }
        return "";
    }

    /**
     *
     * @param xmlSource
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws TransformerConfigurationException
     * @throws TransformerException
     * @throws JAXBException
     */
    public void stringToDom(String xmlSource)
            throws SAXException, ParserConfigurationException, IOException, TransformerConfigurationException, TransformerException, JAXBException
    {
        FiletoXMLtoObject("RegFile.xml");
        FileToStringToObject("RegFile.xml");
    }

    /**
     *
     * @param args
     * @return
     */
    private String SendRecipe(String args)
    {

        //check which workstation sent
        String SenderName = "someAdaptor"; //replace with workstation name in the received arguments
        DACManager instance = DACManager.getInstance();

        SubSystem agentObj = instance.getDeviceAdapter(SenderName).getSubSystem();

        //already deployed when agent is created ?
        //Vertx.vertx().deployVerticle(new WebSocketsSender("5555")); //test! delete
        //vertx.deployVerticle(new WebSocketsSender(agentObj.getUniqueName()));
        //get agentID
        //get recipes sent by the workstation
        //send them to the respective agentID by publishing on its topic
        if (withAGENTCloud)
        {
            //Send message on the respective AgentID topic/websocket
            String msgToSend = Constants.MSB_MESSAGE_TYPE_APPLIEDRECIPES + args;
            List<String> ReplyMsg = new ArrayList<String>();

            vertx.eventBus().send(/*
         * agentObj.getUniqueName()
                     */"5555", msgToSend, reply
                    ->
            {
                if (reply.succeeded())
                {
                    System.out.println("vertX Received reply: " + reply.result().body());
                    String replyMessage = (String) reply.result().body().toString();
                    ReplyMsg.add(reply.result().body().toString());
                } else
                {
                    System.out.println("vertX No reply");
                    ReplyMsg.add("vertX No reply");
                }
            });

            return ReplyMsg.get(0);

        } else
        {
            return "OK  - with no AgentCloud";
        }

    }

}

//EOF
