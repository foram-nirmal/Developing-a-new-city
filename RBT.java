/*References for algorithms taken from Introduction to Algorithms by Cormen, Leiserson, Rivest and Stein*/
//package buildings;

public class RBT {
    //creating a node of type RBT with the following fields
    public class Node {
        //false denotes a black node and true denotes a red node
        boolean color = false; //initializing default color to black
        public RBT_node node; //having a field of type RBT_node which contains all the properties of a building
        Node parent = NIL, left = NIL, right = NIL;

        public Node(RBT_node node){ //constructor
            this.node = node;
        }
    }

    public final Node NIL = new Node(new RBT_node(-1,0)); //NIL is an external node
    public Node root = NIL; //initializing root to NIL

    //rotate left is same as LL rotation in AVL trees
    private void rotateLeft(Node node) {

        if(node.parent != NIL) {
            if(node == node.parent.left) {
                node.parent.left = node.right; //put the node on parent's left
            }
            else {
                node.parent.right = node.right; //put the node on parent's right
            }
            node.right.parent = node.parent;
            node.parent = node.right;
            if(node.right.left != NIL) {
                node.right.left.parent = node;
            }
            node.right = node.right.left;
            node.parent.left = node;
        }
        else {
            Node temp1 = root.right;
            root.right = temp1.left;
            temp1.left.parent = root;
            root.parent = temp1;
            temp1.left = root;
            temp1.parent = NIL;
            root = temp1;
        }
    }

    private void rotateRight(Node node) {
        if(node.parent != NIL) {
            if(node == node.parent.left) {
                node.parent.left = node.left; // put node on parent's left
            }
            else {
                node.parent.right = node.left; //put node on parent's right
            }
            node.left.parent = node.parent;
            node.parent = node.left;
            if(node.left.right != NIL) {
                node.left.right.parent = node;
            }
            node.left = node.left.right;
            node.parent.right = node;
        }
        else {
            Node temp2 = root.left;
            root.left = root.left.right;
            temp2.right.parent = root;
            root.parent = temp2;
            temp2.right = root;
            temp2.parent = NIL;
            root = temp2;
        }
    }
    // cases for insertion into RBT
    /*Case 1: node's uncle is red
      Case 2: node's uncle is black and node is a right child
      Case 3: node's uncle is black and node is a left child
    */
    public void RBT_insert(Node node) {
        Node x = root;
        if(root == NIL) { //first node being inserted
            root = node;
            node.color=false; //root is always colored black
            node.parent = NIL;
        }
        else {
            node.color = true;
            //inserting the new node as it is inserted in a BST, coloring the new node RED
            while(true) {
                if(node.node.buildingNum < x.node.buildingNum) {
                    if(x.left == NIL) {
                        x.left = node;
                        node.parent = x;
                        break;
                    }
                    else
                        x = x.left;
                }
                else if(node.node.buildingNum > x.node.buildingNum) {
                    if(x.right == NIL) {
                        x.right = node;
                        node.parent = x;
                        break;
                    }
                    else
                        x = x.right;
                }
            }
            fixInsert(node);
        }
    }

    private void fixInsert(Node node) {
        while(node.parent.color == true) { //iterate till the node's parent is red, since the node itself is red
            Node uncle = NIL;
            if(node.parent == node.parent.parent.left) {
                uncle = node.parent.parent.right;
                if(uncle.color == true && uncle != NIL) { //Case 1
                    node.parent.color = false; //Case 1
                    uncle.color = false; // Case 1
                    node.parent.parent.color = true; // Case 1
                    node = node.parent.parent;  // case 1
                    continue;
                }
                if(node == node.parent.right) {
                    node = node.parent;     //case 2
                    rotateLeft(node); // case 2
                }
                node.parent.color = false;  // case 3
                node.parent.parent.color = true; //case 3
                rotateRight(node.parent.parent); // case 3
            }
            else { //similar to the above cases, just interchanging left with right and vice versa
                uncle = node.parent.parent.left;
                if(uncle.color ==true && uncle != NIL) {
                    node.parent.color = false;
                    uncle.color = false;
                    node.parent.parent.color = true;
                    node = node.parent.parent;
                    continue;
                }
                if(node == node.parent.left) {
                    node = node.parent;
                    rotateRight(node);
                }
                node.parent.color = false;
                node.parent.parent.color = true;
                rotateLeft(node.parent.parent);
            }
        }
        root.color = false;
    }
    // Cases for deletion
    /* Case 1: Deleted node's successor's sibling is red
       Case 2: Deleted node's sibling w is black and both of w's children are black
       Case 3: Deleted node's sibling w is black and w's left child is red, w's right child is black
       Case 4: Deleted node's sibling w is black and w's right child is red
    */
    public void RBT_remove(int element) {
        Node p = nodeExists(root, element);
        if(p == NIL)
            return;
        Node x;
        Node y = p;
        boolean z = y.color;
        if(p.left == NIL) {
            x = p.right;
            findMin(p,p.right);
        }
        else if(p.right == NIL) {
            x = p.left;
            findMin(p,p.left);
        }
        else {
            y = findSuccessor(p.right);
            z = y.color;
            x = y.right;
            if(y.parent == p)
                x.parent = y;
            else {
                findMin(y,y.right);
                y.right = p.right;
                y.right.parent = y;
            }
            findMin(p,y);
            y.left = p.left;
            y.left.parent = y;
            y.color = p.color;
        }
        if(z == false)
            delete_fix(x);

    }


    private void delete_fix(Node node) {
        while(node != root && node.color == false) {
            if(node == node.parent.left) {
                Node w = node.parent.right;
                if(w.color == true) {
                    w.color = false; //case 1
                    node.parent.color = true; //case 1
                    rotateLeft(node.parent); // case 1
                    w = node.parent.right; // case 1
                }
                if(w.left.color==false && w.right.color==false) {
                    w.color = true; //case 2
                    node = node.parent; //case 2
                    continue;
                }
                else if(w.right.color == false) {
                    w.left.color = false; //case 3
                    w.color = true; //case 3
                    rotateRight(w); //case 3
                    w = node.parent.right; //case 3
                }
                if(w.right.color == true) { //case 4
                    w.color = node.parent.color; //case 4
                    node.parent.color = false; //case 4
                    w.right.color = false; //case 4
                    rotateLeft(node.parent); //case 4
                    node = root; //case 4
                }
            }
            else { //similar to the above conditions, just replacing left with right and vice versa
                Node w= node.parent.left;
                if(w.color == true) {
                    w.color = false;
                    node.parent.color = true;
                    rotateRight(node.parent);
                    w = node.parent.left;

                }
                if(w.right.color == false && w.left.color == false) {
                    w.color=true;
                    node = node.parent;
                    continue;
                }
                else if(w.left.color == false) {
                    w.right.color = false;
                    w.color = true;
                    rotateLeft(w);
                    w = node.parent.left;
                }
                if(w.left.color == true) {
                    w.color = node.parent.color;
                    node.parent.color = false;
                    w.left.color = false;
                    rotateRight(node.parent);
                    node = root;
                }
            }
        }
        node.color = false;
    }
    //check if a node exists
    private Node nodeExists(Node node, int n1) {
        if(root==NIL)
            return NIL;

        if(node.node.buildingNum > n1) {
            if(node.left != NIL) { //traverse left if given node is greater than the required element
                return nodeExists(node.left, n1);
            }
        }
        else if(node.node.buildingNum < n1) {
            if(node.right != NIL) { //traverse right if given node is less than the required element
                return nodeExists(node.right, n1);
            }
        }
        else if(node.node.buildingNum == n1) { //return the node once condition matches
            return node;
        }
        return NIL;
    }
    //searching for the node
    public Node search(Node node, int n1){
        if (root.node.buildingNum == n1 || node == NIL)
            return node;
        if (node.node.buildingNum > n1)
            return search(root.left, n1); //traverse left if given node is greater than the required element
        return search(root.right, n1); //traverse right if given node is less than the required element
    }
    //finding the minimum of two given RBT nodes
    private void findMin(Node node1, Node node2) {
        if(node1.parent == NIL)
            root = node2;
        else if(node1 == node1.parent.left)
            node1.parent.left = node2;
        else
            node1.parent.right = node2;
        node2.parent = node1.parent;
    }
    // find the successor for deleted node
    private Node findSuccessor(Node node) {
        while(node.left != NIL) {
            node = node.left; //leftmost child of the left subtree
        }
        return node;
    }
}
