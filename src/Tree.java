import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class Tree {
	TreeNode root;
	public Tree(TreeNode node){
		root=node;
	}
	//insert a normal node
	public boolean insertNormal(String str){
		String[] folder=str.toLowerCase().split("/");
		
		//return false if invalid path (without "/")
		if(folder.length<=1||!folder[0].equals("")||!folder[1].equals("home")){
			return false;
		}
		TreeNode curNode=root;
		//starting from index two, we restrict that the root folder will always be home
		for(int i=2;i<folder.length;i++){
			String cur=folder[i];
			//if the current folder has been created, we pinpoint to that treenode
			if(curNode.folder.contains(cur)){
				for(TreeNode node:curNode.children){
					if(node.pathName.equals(cur))
						curNode=node;
				}
			}else{
			//if the current folder hasn't been created, we create one and pinpoint the that
				TreeNode temp=new TreeNode(cur,new ArrayList<TreeNode>(),new ArrayList<String>());
				curNode.children.add(temp);
				curNode.folder.add(cur);
				curNode=temp;
			}
		}
		return true;
	}
	//insert dual leaf nodes
	public boolean insertDualLeafNodes(String path){
		String[] folder=path.toLowerCase().split("/");
		//return false if invalid path (without "/")
		if(folder.length<=1||!folder[0].equals("")||!folder[1].equals("home")){
			return false;
		}
		TreeNode curNode=root;
		for(int i=2;i<folder.length;i++){
			String cur=folder[i];
			//make sure it's leaf node
			if(i==folder.length-1){
				//split the words then insert the leaves
					String[] twonodes=cur.split("\\|");
					for(int j=0;j<twonodes.length;j++){
						if(!curNode.folder.contains(twonodes[j]))
							curNode.children.add(new TreeNode(twonodes[j],new ArrayList<TreeNode>(),new ArrayList<String>()));
					}
				break;
			}else if(cur.contains("|")){
				return false;
			}
			//same ideas here
			if(curNode.folder.contains(cur)){
				for(TreeNode node:curNode.children){
					if(node.pathName.equals(cur))
						curNode=node;
				}
			}else{
				TreeNode temp=new TreeNode(cur,new ArrayList<TreeNode>(),new ArrayList<String>());
				curNode.children.add(temp);
				curNode.folder.add(cur);
				curNode=temp;
			}
		}
		return true;
	}
	//support a combinational leaf-node insert
	public boolean combinationalLeafNodeInsert(String str){
		String[] folder=str.toLowerCase().split("/");
		//return false if invalid path (without "/")
		if(folder.length<=1||!folder[0].equals("")||!folder[1].equals("home")){
			return false;
		}
		TreeNode curNode=root;
		for(int i=2;i<folder.length;i++){
			String cur=folder[i];
			if(i==folder.length-1){
					String[] twonodes=cur.split("\\|");
					//get all combination with getCombination method
					ArrayList<String> combination=getCombination(twonodes);
					for(String string:combination){
						if(!curNode.folder.contains(string))
							curNode.children.add(new TreeNode(string,new ArrayList<TreeNode>(),new ArrayList<String>()));
					}
				break;
			}else if(cur.contains("|")){
				return false;
			}
			if(curNode.folder.contains(cur)){
				for(TreeNode node:curNode.children){
					if(node.pathName.equals(cur))
						curNode=node;
				}
			}else{
				TreeNode temp=new TreeNode(cur,new ArrayList<TreeNode>(),new ArrayList<String>());
				curNode.children.add(temp);
				curNode.folder.add(cur);
				curNode=temp;
			}
		}
		return true;
	}
	//support combinatorial nodes at any level
	public boolean combinatorialNodesAnyLevel(String path){
		String[] folder=path.toLowerCase().split("/");
		//return false if invalid path (without "/")
		if(folder.length<=1||!folder[0].equals("")||!folder[1].equals("home")){
			return false;
		}
		TreeNode curNode=root;
		//we need to store the parent nodes to know what nodes should be inserted out current combinations
		ArrayList<TreeNode> parentNode=new ArrayList<TreeNode>();
		for(int i=2;i<folder.length;i++){
			String cur=folder[i];
			String[] splitString=cur.split("\\|");
			ArrayList<String> getCombination=getCombination(splitString);
			if(parentNode.isEmpty()){
			for(String str:getCombination){
				if(!curNode.folder.contains(str)){
					TreeNode temp=new TreeNode(str,new ArrayList<TreeNode>(),new ArrayList<String>());
					parentNode.add(temp);
					curNode.children.add(temp);
					curNode.folder.add(cur);
				}
			}
			}else{
				ArrayList<TreeNode> newParent=new ArrayList<TreeNode>();
				for(TreeNode node:parentNode){
					for(String str:getCombination){
						if(!node.folder.contains(str)){
							TreeNode temp=new TreeNode(str,new ArrayList<TreeNode>(),new ArrayList<String>());
							newParent.add(temp);
							node.children.add(temp);
							node.folder.add(cur);
						}
					}
				}
			parentNode.clear();
			parentNode.addAll(newParent);
			}
		}
		return true;
	}
	//get all combinations
	public ArrayList<String> getCombination(String[] array){
		ArrayList<String> result=new ArrayList<String>();
		int length=array.length;
		//get the number of possibility, totally 2^length combination
		int max=1<<length;
        java.util.Arrays.sort(array);
        //see if the bit is 1, if true, add the current string in the array to result
        //for example, for array {1,2,3}, when we come to 101, which is 5 in decimal, we add array[0] and array[2] to result.
        //then we can get all combinations.
        for(int i=1;i<max;i++){
        	StringBuffer sb=new StringBuffer();
        	for(int n=i,index=0;n>0;n>>=1){
	            if((n&1)==1){
	                if(sb.length()==0)
	                	sb.append(array[index]);
	                else
	                	sb.append("-"+array[index]);
	            }
	                index++;
	        }
        	result.add(sb.toString());
        }
        return result;
	}
	//part five, collapseTree,current tree should be combinatorial tree,otherwise the path with "|" will be confusing
	public String CollapseTree(Tree tree){
		StringBuffer output=new StringBuffer("/home");
		ArrayList<TreeNode> list=new ArrayList<TreeNode>();
		list.add(this.root);
		while(!list.isEmpty()){
			ArrayList<TreeNode> arr=new ArrayList<TreeNode>();
			String str="";
			for(TreeNode temp:list){
				for(TreeNode folder:temp.children){
					arr.add(folder);
					if(!folder.pathName.contains("-")&&!str.contains(folder.pathName))
						str=str==""?folder.pathName:str+"|"+folder.pathName;
				}
			}
			output.append("/");
			output.append(str);
			list=arr;
		}
		return output.toString();
	}
	//part six, output synonym path
	public String Synonym(String input){
		//first see which level we should look at. 
		//for example, "/home/sport" indicates that the "synonym" should go to the same depth as "sport"
		//we only need to look at those folders which are at the same level as sport folder.
		StringBuffer output=new StringBuffer("/home");
		String[] folder=input.toLowerCase().split("/");
		int levelToCheck=folder.length-1;
		for(int i=2;i<levelToCheck;i++)
			output.append("/"+folder[i]);
		//input's folder name
		String folderNameToCheck=folder[folder.length-1];
		//input's adjacent childrens
		ArrayList<String> listToCheck=new ArrayList<String>();
		ArrayList<TreeNode> list=new ArrayList<TreeNode>();
		list.add(this.root);
		int level=1;
		while(!list.isEmpty()){
			if(level==levelToCheck){
				//get input's children list first
				for(TreeNode node:list){
					if(node.pathName.equals(folderNameToCheck)){
						listToCheck=node.folder;
						break;
					}
				}
				//if current node's children list is equal to the input's, add the folder name to output,then break
				for(TreeNode node:list){
					if((node.folder.isEmpty()&&listToCheck.isEmpty()||node.folder.equals(listToCheck))&&!node.pathName.equals(folderNameToCheck)){
						output.append("/"+node.pathName);
						break;
					}
				}
			}
			ArrayList<TreeNode> arr=new ArrayList<TreeNode>();
			for(TreeNode temp:list){
				for(TreeNode node:temp.children)
					arr.add(node);
			}
			list=arr;
			level++;
		}
		return output.toString();
	}
	//print all tree nodes in level order.
	public void LevelOrderPrint(){
		ArrayList<TreeNode> list=new ArrayList<TreeNode>();
		list.add(this.root);
		while(!list.isEmpty()){
			ArrayList<TreeNode> arr=new ArrayList<TreeNode>();
			for(TreeNode temp:list){
				System.out.print(temp.pathName+" ");
				for(TreeNode node:temp.children)
					arr.add(node);
			}
			System.out.println();
			list=arr;
		}
		
	}

}
