/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.openmos.agentcloud.data;

import eu.openmos.agentcloud.data.utilities.SerializationConstants;
import eu.openmos.agentcloud.data.recipe.Equipment;
import eu.openmos.agentcloud.data.recipe.ExecutionTable;
import eu.openmos.agentcloud.data.recipe.Recipe;
import eu.openmos.agentcloud.data.recipe.Skill;
import eu.openmos.agentcloud.data.utilities.ListsToString;
import eu.openmos.agentcloud.data.utilities.StringToLists;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import org.bson.Document;

/**
 * Describes the agent that arrives to the cloud from the Manufacturing
 * Service Bus to be created.
 * 
 * @author Pedro Lima Monteiro <pedro.monteiro@uninova.pt>
 * @author Valerio Gentile <valerio.gentile@we-plus.eu>
 *      
 */
public class CyberPhysicalAgentDescription {    
    /**
     * WP3 semantic model alignment
     * Equipment id, it's the agent unique identifier 
     */
    private String equipmentId;
    /**
     * WP3 semantic model alignment.
     * Agent execution table.
    */
    private ExecutionTable executionTable;
    /**
     * The skills the agent is capable of performing (Resource/Transport Agent).
     */
    private List<Skill> skills;
    /**
     * The recipes the agent can apply (Resource/Transport Agent).
     */
    private List<Recipe> recipes;
    /**
     * WP3 semantic model alignment
     * List of inner equipments.
     */
    private List<Equipment> equipments;
    /**
     * The Physical Location of the device abstracted by this agent.
     */
    private PhysicalLocation physicalLocation;
    /**
     * The Logical location of the agent.
     * TBV if we need it or not
     */
    private LogicalLocation logicalLocation;
//    /**
//     * Agent's Java class.
//     * TBV if we need it or not
//     */
//    private String agentClass;
    /**
     * Agent's type.
     * TBV if we need it or not
     */
    private String type;
    /**
     * WP3 semantic model alignment.
     * Agent timestamp.
    */
    private Date registeredTimestamp;    
    
//    private static final int FIELDS_COUNT = 10;
    private static final int FIELDS_COUNT = 9;

    /**
     * Default constructor, for reflection purpose.
     */
    public CyberPhysicalAgentDescription() {}

    /**
     * Parameterized constructor.
     * 
     * @param equipmentId - the equipment id, aka the agent unique identifier
     * @param skills - The skills the agent is capable of performing (Resource/
     * Transport Agent).
     * @param recipes - The recipes the agent can apply (Resource/Transport Agent).
     * @param equipments - The inner equipment in case of workstation.
     * @param physicalLocation - The Physical Location of the device abstracted 
     * by this agent.
     * @param logicalLocation - The skill requirements of the agent (Product Agent).
     * @param agentClass - Agent's class - TBV if necessary.
     * @param type - Agent's type - TBV if necessary.
     * @param registeredTimestamp - the agent creation time
     */
    public CyberPhysicalAgentDescription(String equipmentId, 
            ExecutionTable executionTable, 
            List<Skill> skills, 
            List<Recipe> recipes, 
            List<Equipment> equipments, 
            PhysicalLocation physicalLocation, 
            LogicalLocation logicalLocation, 
//            String agentClass, 
            String type,
            Date registeredTimestamp
            ) {
        this.equipmentId = equipmentId;
        this.registeredTimestamp = registeredTimestamp;
        this.executionTable = executionTable;
        this.skills = skills;
        this.recipes = recipes;
        this.equipments = equipments;
        this.physicalLocation = physicalLocation;
        this.logicalLocation = logicalLocation;
//        this.agentClass = agentClass;
        this.type = type;
    }
    
    public PhysicalLocation getPhysicalLocation() {
        return physicalLocation;
    }

    public void setPhysicalLocation(PhysicalLocation physicalLocation) {
        this.physicalLocation = physicalLocation;
    }

    public LogicalLocation getLogicalLocation() {
        return logicalLocation;
    }

    public void setLogicalLocation(LogicalLocation logicalLocation) {
        this.logicalLocation = logicalLocation;
    }

//    public String getAgentClass() {
//        return agentClass;
//    }
//
//    public void setAgentClass(String agentClass) {
//        this.agentClass = agentClass;
//    }
//
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public ExecutionTable getExecutionTable() {
        return executionTable;
    }

    public void setExecutionTable(ExecutionTable executionTable) {
        this.executionTable = executionTable;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public Date getRegisteredTimestamp() {
        return registeredTimestamp;
    }

    public void setRegisteredTimestamp(Date registeredTimestamp) {
        this.registeredTimestamp = registeredTimestamp;
    }

    /**
     * Method that serializes the object.
     * The returned string has the following format:
     * 
     * equipment id,
     * execution table,
     * skills,
     * recipes,
     * equipments,
     * physical location,
     * logical location,
     * agent java class,
     * agent type,
     * registeredTimestamp ("yyyy-MM-dd HH:mm:ss.SSS")
     * 
     * @return Serialized form of the object. 
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(equipmentId);
        
        builder.append(SerializationConstants.TOKEN_CYBER_PHYSICAL_AGENT_DESCRIPTION);
        builder.append(executionTable.toString());
        
        builder.append(SerializationConstants.TOKEN_CYBER_PHYSICAL_AGENT_DESCRIPTION);
        builder.append(ListsToString.writeSkills(skills));
        
        builder.append(SerializationConstants.TOKEN_CYBER_PHYSICAL_AGENT_DESCRIPTION);
        builder.append(ListsToString.writeRecipes(recipes));

        builder.append(SerializationConstants.TOKEN_CYBER_PHYSICAL_AGENT_DESCRIPTION);
        builder.append(ListsToString.writeEquipments(equipments));

        builder.append(SerializationConstants.TOKEN_CYBER_PHYSICAL_AGENT_DESCRIPTION);
        builder.append(physicalLocation.toString());
        
        builder.append(SerializationConstants.TOKEN_CYBER_PHYSICAL_AGENT_DESCRIPTION);
        builder.append(logicalLocation.toString());
        
//        builder.append(Constants.TOKEN_CYBER_PHYSICAL_AGENT_DESCRIPTION);
//        builder.append(agentClass);
//        
        builder.append(SerializationConstants.TOKEN_CYBER_PHYSICAL_AGENT_DESCRIPTION);
        builder.append(type);
        
        SimpleDateFormat sdf = new SimpleDateFormat(SerializationConstants.DATE_REPRESENTATION);
        String stringRegisteredTimestamp = sdf.format(this.registeredTimestamp);
        builder.append(SerializationConstants.TOKEN_CYBER_PHYSICAL_AGENT_DESCRIPTION);
        builder.append(stringRegisteredTimestamp);
        
        return builder.toString();
    }
    
    /**
    * Method that deserializes a String object.
     * The input string needs to have the following format:
     * 
     * equipment id,
     * execution table,
     * skills,
     * recipes,
     * equipments,
     * physical location,
     * logical location,
     * agent java class,
     * agent type,
     * registeredTimestamp ("yyyy-MM-dd HH:mm:ss.SSS")
    * 
    * @param object - String to be deserialized.
    * @return Deserialized object.
     * @throws java.text.ParseException
    */
    public static CyberPhysicalAgentDescription fromString(String object) throws ParseException {        
        StringTokenizer tokenizer = new StringTokenizer(object, SerializationConstants.TOKEN_CYBER_PHYSICAL_AGENT_DESCRIPTION);

        if (tokenizer.countTokens() != FIELDS_COUNT)
            throw new ParseException("CyberPhysicalAgentDescription - " + SerializationConstants.INVALID_FORMAT_FIELD_COUNT_ERROR + FIELDS_COUNT, 0);
        
        SimpleDateFormat sdf = new SimpleDateFormat(SerializationConstants.DATE_REPRESENTATION);
        
        return new CyberPhysicalAgentDescription(
                tokenizer.nextToken(),                                          // equipment id
                ExecutionTable.fromString(tokenizer.nextToken()),              // execution table
                StringToLists.readSkills(tokenizer.nextToken()),                // skills
                StringToLists.readRecipes(tokenizer.nextToken()),                // recipes
                StringToLists.readEquipments(tokenizer.nextToken()),                // equipments
                PhysicalLocation.fromString(tokenizer.nextToken()),              // physical location
                LogicalLocation.fromString(tokenizer.nextToken()),              // logical location
//                tokenizer.nextToken(),                                          // agent class
                tokenizer.nextToken(),                                          // agent type
                sdf.parse(tokenizer.nextToken())                                // registeredTimestamp
        );
    }
    
    /**
     * To check if the object is valid.... for now it returns always "true"
     * Used into the optimizeragent.
     * 
     * @return always true
     */
    public boolean isValid() {
        return true;
    }

    /**
     * Method that serializes the object into a BSON document.
     * The returned BSON document has the following format:
     * 
     * equipment id,
     * execution table,
     * skills,
     * recipes,
     * equipments,
     * physical location,
     * logical location,
     * agent java class,
     * agent type,
     * registeredTimestamp ("yyyy-MM-dd HH:mm:ss.SSS")
     * 
     * @return BSON form of the object. 
     */
    public Document toBSON() {
        Document doc = new Document();

        List<String> skillIds = skills.stream().map(skill -> skill.getUniqueId()).collect(Collectors.toList());        
        List<String> recipeIds = recipes.stream().map(recipe -> recipe.getUniqueId()).collect(Collectors.toList());
        List<String> equipmentIds = equipments.stream().map(equipment -> equipment.getUniqueId()).collect(Collectors.toList());
        
        doc.append("id", equipmentId);
        doc.append("executionTableId", executionTable.getUniqueId());
        doc.append("skillIds", skillIds);
        doc.append("recipeIds", recipeIds);
        doc.append("equipmentIds", equipmentIds);        
        doc.append("physicalLocation", physicalLocation.toBSON());
        doc.append("logicalLocation", logicalLocation.toBSON());
        doc.append("type", type);
        doc.append("registered", new SimpleDateFormat(SerializationConstants.DATE_REPRESENTATION).format(this.registeredTimestamp));
                
        return doc;
    }
}
