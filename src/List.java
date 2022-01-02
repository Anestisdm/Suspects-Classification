public class List {
        private Node head = null;
        private Node tail = null;
        private int count=0;

        public boolean isEmpty() {
            return head == null;
        }

        public void insertAtFront(Suspect item) {
            Node n = new Node(item);
            count++;
            if (isEmpty()) {
                head = n;
                tail = n;
            } else {
                n.setNext(head);
                head = n;
            }
        }
        public void printList() {
            if (!isEmpty()) {
                Node t = head;
                System.out.println(t.getItem().toString());
                while (t != tail) {
                    t = t.getNext();
                    System.out.println(t.getItem().toString());

                }
            }
        }

    public String returnList() {
        String returnstr = "";
        if (!isEmpty()) {
            Node t = head;
            returnstr +=t.getItem().toString()+"\n";
            while (t != tail) {
                t = t.getNext();
                returnstr+=t.getItem().toString()+"\n";

            }
        }
        return returnstr;
    }

        public int size() {
            return count;
        }
}
