class Solution {
  public List<String> binaryTreePaths(TreeNode root) {
      List<String> paths = new ArrayList<String>();
      if(root == null) {
          return paths;
      }
      
      if(root.left == null && root.right == null) {
          paths.add("" + root.val);
          return paths;
      }
      
      List<String> leftPaths = binaryTreePaths(root.left); // 2 -> 5
      List<String> rightPaths = binaryTreePaths(root.right); // 3


      
      for(String path : leftPaths) {
          paths.add(root.val + "->" + path);
      }
      
      for(String path : rightPaths) {
          paths.add(root.val + "->" + path);
      }
      return paths;
  }
}