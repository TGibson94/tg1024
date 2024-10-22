package service;

import models.Tool;
import models.ToolType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ToolService {
    List<ToolType> TOOL_TYPE = new ArrayList<>();
    List<Tool> TOOL_LIST = new ArrayList<>();
    {
        createToolTypes();
        createToolList();
    }

    //these being stored in lists means we have to traverse the list and check each tool code, limitation of this design
    public List<String> getAllToolCodes(){
        List<String> toolCodes = new ArrayList<>();
        for(Tool t : TOOL_LIST){
            toolCodes.add(t.toolCode());
        }
        return toolCodes;
    }
    public Tool getToolByCode(String toolCode){
        for (Tool t : TOOL_LIST){
            if(t.toolCode().equals(toolCode))
                return t;
        }
        return new Tool("", TOOL_TYPE.get(1), "");
    }

    //This plus the static initialization block is what I'm using to replicate what would in a production system come from
    //a database, in said system I would be calling a repository but to keep it simple we're keeping the data here
    private void createToolTypes(){
        ToolType ladder = new ToolType("Ladder", new BigDecimal("1.99"),true,false);
        ToolType chainsaw = new ToolType("Chainsaw", new BigDecimal("1.49"),false,true);
        ToolType jackhammer = new ToolType("Jackhammer",new BigDecimal("2.99"),false,false);
        this.TOOL_TYPE.add(ladder);
        this.TOOL_TYPE.add(chainsaw);
        this.TOOL_TYPE.add(jackhammer);
    }
    private void createToolList(){
        Tool chns = new Tool("CHNS",TOOL_TYPE.get(1),"Stihl");
        Tool ladw = new Tool("LADW",TOOL_TYPE.get(0),"Werner");
        Tool jakd = new Tool("JAKD",TOOL_TYPE.get(2),"DeWalt");
        Tool jakr = new Tool("JAKR",TOOL_TYPE.get(2),"Ridgid");
        this.TOOL_LIST.add(chns);
        this.TOOL_LIST.add(ladw);
        this.TOOL_LIST.add(jakd);
        this.TOOL_LIST.add(jakr);
    }

}
