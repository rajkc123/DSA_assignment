// q no 4b

import java.util.LinkedList;
import java.util.List;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }




public List<Integer> closestKValues(TreeNode root, double target, int k) {
    LinkedList<Integer> closestValues = new LinkedList<>();
    inOrderTraversal(root, target, k, closestValues);
    return closestValues;
}

private void inOrderTraversal(TreeNode node, double target, int k, LinkedList<Integer> closestValues) {
    if (node == null) return;

    // Traverse left subtree
    inOrderTraversal(node.left, target, k, closestValues);

    // Process current node
    if (closestValues.size() < k) {
        closestValues.add(node.val);
    } else {
        if (Math.abs(node.val - target) < Math.abs(closestValues.peekFirst() - target)) {
            closestValues.removeFirst();
            closestValues.add(node.val);
        } else {
            return; // The rest of the tree will not have closer values
        }
    }

    // Traverse right subtree
    inOrderTraversal(node.right, target, k, closestValues);
}

}