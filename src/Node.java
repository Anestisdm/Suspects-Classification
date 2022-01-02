public class Node {
        protected Suspect Item;
        protected Node next = null;

        Node(Suspect item) {
            this.Item = item;
        }

        public Suspect getItem() {
            return Item;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
}

