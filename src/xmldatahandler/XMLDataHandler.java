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
    
    public void walkNode(Node n) {
        for(int i=0; i<n.nodes.size();i++)
        {
            
            System.out.println("<" + n.nodes.get(i).GetName()+">");
            System.out.println(n.nodes.get(i).GetValue());

            walkNode(n.nodes.get(i));
            System.out.println("</" + n.nodes.get(i).GetName()+">");
        }
    }
    
    public void walkNodeJSON(Node n) {
        for(int i=0; i<n.nodes.size();i++)
        {
            
            System.out.println(n.nodes.get(i).GetName() + ": "); 
            System.out.println(n.nodes.get(i).GetValue());
            if(n.nodes.get(i).nodes.size() > 0)
            {
                System.out.println("{");
                walkNodeJSON(n.nodes.get(i));
                System.out.println("}");
            }
            if(i != n.nodes.size()-1)
                System.out.println(",");
        }
        
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
        xml.walkNode(n);
        xml.walkNodeJSON(n);
        
    }
    
    
    
}
