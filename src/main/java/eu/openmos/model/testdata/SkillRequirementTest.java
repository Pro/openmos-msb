/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.openmos.model.testdata;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import eu.openmos.model.SkillRequirement;

/**
 *
 * @author valerio.gentile
 */
public class SkillRequirementTest {
    
    public static SkillRequirement getTestObject()
    {
        Date registeredTimestamp = new Date();
                
        /*
     * @param description - Skill Requirement description.
     * @param uniqueId - Skill Requirement unique ID.
     * @param name - Skill Requirement name.
     * @param type - Skill Requirement type.
     * @param classificationType - Skill Requirement classification type.
     * @param registeredTimestamp - Skill Requirement registration timestamp.        
        */
        
        String description = "skillRequirementDescription";
        String uniqueId = "skillRequirementUniqueId";
        String name = "skillRequirementName";
        String type = "weld";
        List<String> precedenceIds = new LinkedList<>();
        String classificationType = "skillRequirementClassificationType";
        
        SkillRequirement skillRequirement = new SkillRequirement(
                description, 
//                uniqueId, 
                name, 
                type, 
                precedenceIds, 
                registeredTimestamp
        );
        
        return skillRequirement;
    }
    
    public static List<SkillRequirement> getTestList()
    {
        return new LinkedList<>(Arrays.asList(getTestObject()));        
    }
}
