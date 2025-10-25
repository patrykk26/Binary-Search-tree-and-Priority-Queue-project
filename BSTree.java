public class BSTree {
    
    //Each node stores: key (int), left and right children, parent pointer
    public static class Node {
        int key;
        Node left, right, parent;

        Node(int key) {
            this.key = key;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    // The BSTree class holds a reference to the root
    public Node root;

    //constructor, starts with an empty tree.
    public BSTree() {
        this.root = null;
    }

    // add(T, key) inserts a new node with the given key.
    public void add(BSTree S, int key) {
        Node z = new Node(key);
        Node y = null;   // tracks the parent where z is attached.
        Node x = S.root; // Start from the root.

        while (x != null) {
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else if (z.key > x.key) {
                x = x.right;
            } else {
                // if key is there, do not insert duplicates.
                return;
            }
        }

        // y is parent node where z should be attached.
        z.parent = y;
        if (y == null) {

            S.root = z;
        } else if (z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }
    }

    // TRANSPLANT(S, u, v) replace subtree rooted at u with the subtree rooted at v
    public void TRANSPLANT(BSTree S, Node u, Node v) {
        if (u.parent == null) {
            S.root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        if (v != null) {
            v.parent = u.parent;
        }
    }

    // REMOVE(S, z) removes the node z from tree S.
    public void remove(BSTree S, Node x) {
        if (x.left == null) {
            // Case 1: z has no left child
            TRANSPLANT(S, x, x.right);
        } else if (x.right == null) {
            // Case 2: z has no right child.
            TRANSPLANT(S, x, x.left);
        } else {
            // Case 3: z has two children
            // find z in-order successor (minimum in z.right)
            Node y = MINIMUM(x.right);
            if (y.parent != x) {
                // Move y right child up
                TRANSPLANT(S, y, y.right);
                // y takes z.right as its right subtree.
                y.right = x.right;
                y.right.parent = y;
            }
            // replace z with y
            TRANSPLANT(S, x, y);
            // y takes z.left as its left subtree
            y.left = x.left;
            y.left.parent = y;
        }
    }

    // MINIMUM(x): Return the minimum node under x
    public Node MINIMUM(Node x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    // IS-ELEMENT(S, x) check whether element x is in set S
    public boolean IS_ELEMENT(BSTree S, int x) {
        Node node = S.root;
        while (node != null && x != node.key) {
            if (x < node.key) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node != null;
    }

    // SET-EMPTY(S check whether set S has no elements
    public boolean SET_EMPTY(BSTree S) {
        return S.root == null;
    }
    
    // INORDER(x): Print the BST in ascending order
    public void INORDER(Node x) {
        if (x != null) {
            INORDER(x.left);
            System.out.print(x.key + " ");
            INORDER(x.right);
        }
    }

    
    

    

    // SET-SIZE(S) Return the number of elements of set S.recursively counts the nodes
    private int SET_SIZE(Node S) {
        if (S == null)
            return 0;
        return 1 + SET_SIZE(S.left) + SET_SIZE(S.right);
    }

    public int SET_SIZE(BSTree S) {
        return SET_SIZE(S.root);
    }

    // UNION(S, T): Return the union of sets S and T.creates a new BSTree that contains every element in S or T.
    public static BSTree UNION(BSTree S, BSTree T) {
        BSTree U = new BSTree();
        //Insert every element from S into U
        unionInsert(S.root, U);
        // insert every element from T into U
        unionInsert(T.root, U);
        return U;
    }

    // Helper method for in-order traversal and insertion, for union
    private static void unionInsert(Node node, BSTree U) {
        if (node == null) {
            return;
        }
        unionInsert(node.left, U);
        U.add(U, node.key);
        unionInsert(node.right, U);
    }
    
    // INTERSECTION(S, T) Return the intersection of sets S and T Creates a new BSTree that contains only elements present in both S and T

    public static BSTree INTERSECTION(BSTree S, BSTree T) {
        BSTree U = new BSTree();
        intersectionInsert(S.root, T, U);
        return U;
    }
    
    // Helper method for intersetion in-order traversal and insertion
    private static void intersectionInsert(Node node, BSTree T, BSTree U) {
        if (node == null) {
            return;
        }
        intersectionInsert(node.left, T, U);
        //if the element exists in T, insert it into U
        if (T.IS_ELEMENT(T, node.key)) {
            U.add(U, node.key);
        }
        intersectionInsert(node.right, T, U);
    }
    
    // DIFFERENCE(S, T) return the difference of sets S and T Creates a new BSTree that contains elements in S but not in T
        public static BSTree DIFFERENCE(BSTree S, BSTree T) {
        BSTree U = new BSTree();
        differenceInsert(S.root, T, U);
        return U;
    }
    
    // Helper method for difference in-order traversal and insertion (for difference). Inserts into U each element from S that is NOT in T
    private static void differenceInsert(Node node, BSTree T, BSTree U) {
        if (node == null) {
            return;
        }
        differenceInsert(node.left, T, U);
        if (!T.IS_ELEMENT(T, node.key)) {
            U.add(U, node.key);
        }
        differenceInsert(node.right, T, U);
    }
    
    // SUBSET(S, T) Check whether set S is a subset of set T  Returns true if every element in S is also present in T.
    public static boolean SUBSET(BSTree S, BSTree T) {
        return subsetHelper(S.root, T);
    }
    
    //helper method for subset in-order traversal for subset checking
    private static boolean subsetHelper(Node node, BSTree T) {
        if (node == null) {
            return true;
        }
        // If current element is not in T, S is not a subset of T
        if (!T.IS_ELEMENT(T, node.key)) {
            return false;
        }
        return subsetHelper(node.left, T) && subsetHelper(node.right, T);
    }
    
    // Main method for testing the BST operations.
    public static void main(String[] args) {

        //exmample of using the BSTree class

        //set S
        BSTree S = new BSTree();
        S.add(S, 50);
        S.add(S, 30);
        S.add(S, 70);
        S.add(S, 20);
        S.add(S, 40);

        //set T
        BSTree T = new BSTree();
        T.add(T, 70);
        T.add(T, 60);
        T.add(T, 80);
        T.add(T, 90);

        System.out.print("Set S: ");
        S.INORDER(S.root);
        System.out.println();

        System.out.print("Set T: ");
        T.INORDER(T.root);
        System.out.println();

        //  union of S and T.
        BSTree unionSet = BSTree.UNION(S, T);
        System.out.print("Union of S and T: ");
        unionSet.INORDER(unionSet.root);
        System.out.println();

        // Perform intersection of S and T.
        BSTree intersectSet = BSTree.INTERSECTION(S, T);
        System.out.print("Intersection of S and T: ");
        intersectSet.INORDER(intersectSet.root);
        System.out.println();
        
        // Perform difference of S and T (S - T)
        BSTree diffSet = BSTree.DIFFERENCE(S, T);
        System.out.print("Difference of S and T (S - T): ");
        diffSet.INORDER(diffSet.root);
        System.out.println();
        
        // Test subset: check if S is a subset of unionSet. (should be true)
        System.out.println("Is S a subset of the union of S and T? " + BSTree.SUBSET(S, unionSet));
        // Check if T is a subset of S (should be false)
        System.out.println("Is T a subset of S? " + BSTree.SUBSET(T, S));
        
    
    }
}
