import java.util.*;

public class Node {
	//FIELDS
	private String value;
	private List<Node> children = null;
	
	//CONSTRUCTOR
	public Node(String val){
		value = val;
		children = new ArrayList<>();
	}
	
	//PUBLIC METHODS: GETTER & SETTER
	public void addChild(Node child){
		children.add(child);
	}
	
	public String getValue(){
		return value;
	}
	
	public List<Node> getChildren(){
		return children;
	}
	
	public ArrayList<String> getChildrenValues(){
		ArrayList<String> childrenValues = new ArrayList<String>();
		
		for(int i = 0 ; i < children.size() ; i++){
			childrenValues.add(children.get(i).getValue());
		}
		
		return childrenValues;
	}
	
	public boolean hasChildren(){
		return children.size() > 0;
	}
	
	public int getNumChildren(){
		return children.size();
	}
	
	public String printTree(){
		String stringTree="";
		
		if(children.size()>0){
			stringTree = stringTree+value+"(";
			
			stringTree = stringTree + printChildren(children);
			
			stringTree = stringTree+")";
		}else{
			stringTree=stringTree+value;
		}
		
		
		//System.out.print(")");
		
		return stringTree;
	}
	
	public String printChildren(List<Node> childrenList){
		String childrenString="";
		
		for(int i = 0 ; i < childrenList.size() ; i++){
			
			Node currNode = childrenList.get(i);
			
			childrenString = childrenString + currNode.getValue();
			//System.out.print(currNode.getValue());
			
			if(currNode.hasChildren()){
				childrenString = childrenString + "(";
				//System.out.print("(");
				childrenString = childrenString + printChildren(currNode.getChildren());
				
				childrenString = childrenString + ")";
				//System.out.print(")");
			}
			
			if(i != childrenList.size()-1){
				childrenString = childrenString + ",";
			}
		}
		
		return childrenString;
	}
	
	public void addToTree(String[] paramParts){
		
		for(int i = 0 ; i < paramParts.length ; i++){
			String currParam = paramParts[i];
			
			//first check it isn't equal to root
			if(value.equals(currParam)){
				continue;
			}
			
			//then check if it is in the list of children
			Node tmpNode = new Node(currParam);
			if(children.contains(tmpNode)){
				continue;
			}
			
			//otherwise the parameter before the current one
			//is the parent so we can add it on
			int indexOfParent = children.indexOf(paramParts[i-1]);
			Node tmpParent = children.get(indexOfParent);
			tmpParent.addChild(new Node(currParam));
		}
		
	}
	
}
