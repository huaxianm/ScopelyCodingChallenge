import java.util.ArrayList;

public class TreeNode {
	public String pathName;
	//all children
	public ArrayList<TreeNode> children;
	//all children's pathname, this is for faster searching.
	public ArrayList<String> folder;
	//constructor.
	public TreeNode(){
		this.children=new ArrayList<TreeNode>();
		this.pathName="";
		this.folder=new ArrayList<String>();
	}
	public TreeNode(String str, ArrayList<TreeNode> child,ArrayList<String> fold){
		this.children=child;
		this.pathName=str;
		this.folder=fold;
	}
	public TreeNode(String str){
		this.children=new ArrayList<TreeNode>();
		this.pathName=str;
		this.folder=new ArrayList<String>();
	}
}
