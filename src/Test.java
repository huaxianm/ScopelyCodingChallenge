import java.util.Scanner;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tree tree1=new Tree(new TreeNode("home"));
		Tree tree2=new Tree(new TreeNode("home"));
		Tree tree3=new Tree(new TreeNode("home"));
		Tree tree4=new Tree(new TreeNode("home"));
		//System.out.println("type in path to insert");
		//Scanner sc=new Scanner(System.in);
		System.out.println("*****************************part one*****************************");
		if(tree1.insertNormal("/home/sport/basketball"))
			tree1.LevelOrderPrint();
		System.out.println("*****************************part two*****************************");
		if(tree2.insertDualLeafNodes("/home/music/jazz|classic|poping"))
			tree2.LevelOrderPrint();
		System.out.println("*****************************part three*****************************");
		if(tree3.combinationalLeafNodeInsert("/home/music/jazz|classic|poping"))
			tree3.LevelOrderPrint();
		System.out.println("*****************************part four*****************************");
		if(tree4.combinatorialNodesAnyLevel("/home/sports|music|entertainment/misc|favorites/james|Man"))
			tree4.LevelOrderPrint();
		System.out.println("*****************************part five*****************************");
	    System.out.println("Base on combinatiorial tree given in part four, the path is "+tree4.CollapseTree(tree4));
	    System.out.println("*****************************part six*****************************");
	    System.out.println("synonym path of the input '/home/sports' is "+tree1.Synonym("/home/sports"));
	}

}
