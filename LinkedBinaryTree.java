public class LinkedBinaryTree<E> {
    protected Node<E> root = null;


    public Node<E> root() {
        return root;
    }


    public int size() {
        return size(root);
    }

    // Exercise 1: calculate the size of the tree recursively
    private int size(Node<E> node) {
        if (node == null)
            return 0;
        else
            return 1 + size(node.getLeft()) + size(node.getRight());
    }

    // Exercise 1: Return true if the tree is empty, otherwise return false
    public boolean isEmpty() {
        return size() == 0;
    }

    // Exercise 1: Check if the node has both left and right children
    public boolean isFull(Node<E> p) {
        return p.getLeft() != null && p.getRight() != null;
    }


    public boolean isLeaf(Node<E> p) {
        return p.getLeft() == null && p.getRight() == null;
    }

    // Add a node to the root. This should be called only once.
    // Throw an exception if the root exists
    public Node<E> addRoot(E e) throws IllegalStateException {
        if (!isEmpty()) throw new IllegalStateException("Root exists (tree is not empty)");
        root = new Node<E>(e); // create a new node and add an element to the root
        return root;
    }

    // Add a node to the left child. Throw an exception if the left child exists
    public Node<E> addLeft(Node<E> parent, E e) throws IllegalArgumentException {
        if (parent.getLeft() != null) {
            throw new IllegalArgumentException("parent already has a left child");
        }
        // create a new node for the left child and connect linked list to the parent
        Node<E> child = new Node<E>(e, parent, null, null);
        parent.setLeft(child);
        return child;
    }

    // Add a node to the right child. Throw an exception if the right child exists
    public Node<E> addRight(Node<E> parent, E e) throws IllegalArgumentException {
        if (parent.getRight() != null) {
            throw new IllegalArgumentException("parent already has a right child");
        }
        // create a new node for the right child and connect linked list to the parent
        Node<E> child = new Node<E>(e, parent, null, null);
        parent.setRight(child);
        return child;
    }

    // Remove a node
    public E remove(Node<E> node) {
        E result = node.getElement();

        // Consider two cases: is this node leaf node or internal node?
        if (isLeaf(node)) {
            if (node.equals(root)) {    // special case - only one node: root
                root = null;    // remove root
            } else {
                removeLeaf(node);    // easy to remove leaf node
            }
        } else {
            // You cannot just remove internal node because the node will have children.
            // Since we will remove an internal node, need replacement
            Node<E> replacement = findLeaf(node);
            removeLeaf(replacement);

            replacement.setParent(node.getParent());
            replacement.setLeft(node.getLeft());
            replacement.setRight(node.getRight());

            // Internal node, happens to be a root after removal
            if (node.equals(root)) {
                root = replacement;
            }
        }
        return result;
    }

    private void removeLeaf(Node<E> node) {
        if (node.getParent().getLeft().equals(node)) {
            node.getParent().setLeft(null);
        } else if (node.getParent().getRight().equals(node)) {
            node.getParent().setRight(null);
        }
    }

    // Recursively find a leaf node to replace
    private Node<E> findLeaf(Node<E> node) {
        if (isLeaf(node)) {
            return node;
        } else if (node.getLeft() != null) {
            return findLeaf(node.getLeft());
        } else {
            return findLeaf(node.getRight());
        }
    }

    // Override toString to display tree in a structured format
    @Override
    public String toString() {
        return toString(root);
    }

    // Print better formats
    private String toString(Node<E> node) {
        if (node == null) {
            return "null";
        } else if (isLeaf(node)) {
            return "" + node.getElement();    // e.g., number 1 becomes String "1"
        }
        return "" + node.getElement() + "(" + toString(node.getLeft()) + "," + toString(node.getRight()) + ")";
    }

    // Exercise2: Just to print the tree from Tester class
    public String inFix() {
        return inFix(root);
    }

    private String inFix(Node<E> node) {
        if (node == null) {
            return "null";
        } else if (isLeaf(node)) {
            return "" + node.getElement();
        }
        return "" + inFix(node.getLeft()) + "(" + node.getElement() + "," + inFix(node.getRight()) + ")";
    }

    // Exercise2: Check if the target node exists
    // Return true if the node exists, otherwise return false
    public boolean contains(E target) {
        return contains(root, target);
    }

    private boolean contains(Node<E> node, E target) {
        // If the node is null, it means we've reached the end of a branch without finding the target
        if (node == null) {
            return false;
        }
        // If the current node's element is equal to the target, return true
        if (node.getElement().equals(target)) {
            return true;
        }
        // Otherwise, recursively check the left and right subtrees
        return contains(node.getLeft(), target) || contains(node.getRight(), target);
    }
}

