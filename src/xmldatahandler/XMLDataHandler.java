/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package xmldatahandler;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author wiggerpa
 */
public class XMLDataHandler {
    
    public String walkNode(Node n) {
        String resultString = "";
        for(int i=0; i<n.nodes.size();i++)
        {
            
            resultString += "<" + n.nodes.get(i).GetName()+">\n";
            resultString += n.nodes.get(i).GetValue() + "\n";

            resultString += walkNode(n.nodes.get(i));
            resultString += "</" + n.nodes.get(i).GetName()+">\n";
        }
        
        return resultString;
    }
    
    public String walkNodeJSON(Node n) {
        
        String resStr = "";
        for(int i=0; i<n.nodes.size();i++)
        {
            
            resStr += '"' + n.nodes.get(i).GetName() +'"' + ":"; 
            if(n.nodes.get(i).GetValue().length() > 0)
                resStr += '"' + n.nodes.get(i).GetValue().replace("\n","") + '"';
            if(n.nodes.get(i).nodes.size() > 0)
            {
                resStr += "{\n";
                resStr += walkNodeJSON(n.nodes.get(i));
                resStr += "}\n";
            }
            if(i != n.nodes.size()-1)
                resStr += ",\n";
        }
        return resStr;
    }
    
    public ArrayList<Node> findRows(Node n, String rowKey)
    {
        ArrayList<Node> entries = new ArrayList<Node>();
        
        for(int i =0; i < n.nodes.size();i++)
        {
            
        }
        
        return entries;
    }
    
    public ArrayList<Node> findEntry(Node n, String type, String value)
    {
        ArrayList<Node> returnValues = new ArrayList<Node>();
        for(int k=0; k< n.nodes.size();k++)
        {
            Node node = n.nodes.get(k);
        for(int i=0; i< node.nodes.size();i++)
        {
            String cmpString = node.nodes.get(i).GetName();
            String valueString = node.nodes.get(i).GetValue();
            
            if(cmpString.contains(type) && valueString.contains(value) )
            {
                returnValues.add(n.nodes.get(k));
            }
        }
        }
        return returnValues;
    }
    
    
    public Node readExternal(String path) throws IOException
    {
        Node motherNode = new Node("Mothernode",null);
        Node leadNode = new Node("LeadNode",motherNode);
        motherNode.addNode(leadNode);
        System.out.println(motherNode.GetName());
        String fileData = "";
        try (FileInputStream in = new FileInputStream(path)) {
         
         int c;
         while((c = in.read()) != -1)
         {
             fileData += (char)c;
         }
         
        }
        
        boolean waitForClose = false;
        String currentNodeName = "";
        String currentValue = "";
        for (int i =0; i < fileData.length(); i++)
        {
            if(fileData.charAt(i) == '<')
            {
                waitForClose = true;
                continue;
            }
            if(waitForClose)
            {
                char currentChar = fileData.charAt(i);
                if(currentChar == '>')
                {
                    Node currentNode = new Node(currentNodeName,leadNode);
                    leadNode.addNode(currentNode);
                    leadNode = currentNode;
                    waitForClose = false;
                    currentNodeName = "";
                    System.out.println(leadNode.GetName());
                }
                else if(currentChar == '/')
                {
                    leadNode.setValue(currentValue);
                    System.out.println(leadNode.GetValue());
                    currentValue = "";
                    if(leadNode.getMother() != null)
                        leadNode = leadNode.getMother();
                    waitForClose = false; 
                    i++;
                    while(fileData.charAt(i) != '>')
                    {
                        i++;
                    }
                }
                else
                {
                    currentNodeName += currentChar;
                }
            }
            else 
            {
                currentValue += fileData.charAt(i);
            }
        }
       
       System.out.println("Finished"); 
       System.out.println(motherNode.GetName());
       return motherNode;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException  {
        // TODO code application logic here
        XMLDataHandler xml = new XMLDataHandler();
        Node n = xml.readExternal("/home/wiggerpa/media/wiggerpa_auf_server/Dokumente/NetBeansProjects/XMLDataHandler/src/xmldatahandler/test.xml");
        for(int i=0; i < 30;i++)
            System.out.print("*************");
        
        ArrayList<Node> nodes = xml.findEntry(n.nodes.get(0),"First Name", "Indiana");
        nodes.get(0).nodes.get(0).setValue("Jack");
        
        
        
        try {
      FileWriter myWriter = new FileWriter("./test.xml");
      myWriter.write(xml.walkNode(n.nodes.get(0)));
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  
    try {
      FileWriter myWriter = new FileWriter("./test.json");
      myWriter.write("{" + xml.walkNodeJSON(n.nodes.get(0)) + "}");
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
        //xml.walkNodeJSON(n);
        
    }
    
    
    

