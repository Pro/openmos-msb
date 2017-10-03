package eu.openmos.model;

import eu.openmos.model.utilities.SerializationConstants;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.bson.Document;

/**
 * Object used to describe a Product Instance.
 * 
 * @author Pedro Lima Monteiro <pedro.monteiro@uninova.pt>
 * @author Valerio Gentile <valerio.gentile@we-plus.eu>
 * 
 */
public class ProductInstance extends Base implements Serializable {
    private static final Logger logger = Logger.getLogger(ProductInstance.class.getName());
    private static final long serialVersionUID = 6529685098267757024L;

    /**
     * Id of the product instance.
     * It could be the agent name, so that is unique into the system.
     */
    private String uniqueId;    
    /**
     * Product instance name.
     */
    private String name;
    /**
     * Product instance description.
     */
    private String description;
    /**
     * Id of the product family, of the model of the product.
     */
    private String productId;    
    /**
     * Id of the order.
     */
    private String orderId;    
    /**
     * List of parts.
     * MSB and WP4 Bari decision: we will not use parts for any demonstrator so far.
    */
    private List<PartInstance> partInstances;
    /**
     * List of skill requirements that need to be executed.
     */
//    private List<SkillRequirement> skillRequirements;
    /**
     * Whether the product instance is finished or not.
    */
    private boolean finished = false;
    /**
     * Timestamp of product finish.
    */
    private Date finishedTime;
    
//    private static final int FIELDS_COUNT = 10;
    
    /**
     * Default constructor, for reflection purpose.
     */
    public ProductInstance() {super();} 
    
    /**
     * Parameterized constructor.
     * 
     * @param uniqueId - product instance unique id, could be the agent unique identifier
     * @param modelId - product model id
     * @param name - product instance name
     * @param description - product instance description
     * @param orderId - id of the order
     * @param components - list of parts
     * @param skillRequirements - list of skills requirements
     * @param finished - finished status
     * @param finishedTimestamp - timestamp of object finish
     * @param registeredTimestamp - timestamp of object creation
     */
    public ProductInstance(String uniqueId, String productId, String name, 
            String description, String orderId, 
            List<PartInstance> partInstances, 
//            List<SkillRequirement> skillRequirements, 
            Date registeredTimestamp) {
        super(registeredTimestamp);

        this.uniqueId = uniqueId;
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.orderId = orderId;
        this.partInstances = partInstances;
//        this.skillRequirements = skillRequirements;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<SkillRequirement> getSkillRequirements() {
//        return skillRequirements;
//    }
//
//    public void setSkillRequirements(List<SkillRequirement> skillRequirements) {
//        this.skillRequirements = skillRequirements;
//    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getModelId() {
        return productId;
    }

    public void setModelId(String modelId) {
        this.productId = modelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PartInstance> getPartInstances() {
        return partInstances;
    }

    public void setParts(List<PartInstance> partInstances) {
        this.partInstances = partInstances;
    }
    
    /**
     * Method that serializes the object into a BSON document.
     * 
     * @return BSON form of the object. 
     */
    public Document toBSON() {
        Document doc = new Document();
        
        List<String> partInstanceIds = null;
        if (partInstances != null)
            partInstanceIds = partInstances.stream().map(partInstance -> partInstance.getUniqueId()).collect(Collectors.toList());
//        List<String> skillRequirementIds = skillRequirements.stream().map(skillRequirement -> skillRequirement.getName()).collect(Collectors.toList());  
        
        doc.append("uniqueId", uniqueId);
        doc.append("productId", productId);
        doc.append("name", name);
        doc.append("description", description);
        doc.append("orderId", orderId);
        doc.append("partInstanceIds", partInstanceIds);
//        doc.append("skillRequirements", skillRequirementIds);      
        doc.append("finished", finished);         
        doc.append("finishedTimestamp", finishedTime == null ? null : new SimpleDateFormat(SerializationConstants.DATE_REPRESENTATION).format(this.finishedTime));  
        doc.append("registered", new SimpleDateFormat(SerializationConstants.DATE_REPRESENTATION).format(registered));
        
        return doc;
    }
}