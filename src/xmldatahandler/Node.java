/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package xmldatahandler;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author wiggerpa
 */
public class Node {
    
    ArrayList<Node> nodes = new ArrayList<Node>();
    String name;
    String value;
    Node motherNode;
    public Node(String name, Node mother)
    {
        this.name = name;
        this.motherNode = mother;
    }
    
    public String GetName() {
        return name;
    }
    
    public Node(String name)
    {
        this.name = value;
        this.value = "";
    }
    
    public void addNode(Node n)
    {
        nodes.add(n);
    }
    
    public List<Node> returnConnected()
    {
        return nodes;
    }
    
    public void setValue(String val)
    {
        this.value = val;
    }
    
    public String GetValue()
    {
        return value;
    }
    
    public Node getMother()
    {
        return motherNode;
    }
    
}
