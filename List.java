public class List {
    //CLASS NODE
    private class Node{
        //Fields
        Object data;
        Node next;
        Node prev;
        //Constructor
        //Create a new Node
        Node(Object data){
            this.data = data; next = null;prev = null;
        }
        //Functions
            public boolean equals(Object x){
            boolean eq = false;
            Node that;
            if(x instanceof Node){
                that = (Node) x;
                eq = (this.data == that.data);
            }
            return eq;
        }
        public String toString(){
            String out="";
            out += this.data.toString();
            return out;
        }
    }
    //Fields
    Node front;
    Node back;
    int length;
    Node cursor;
    //Constructor
    List(){
        front = back = null;
        length = 0;
    }
    /*equals()
      return true if 2 list are equal*/
    boolean equals(List L) {
        Node A = L.front;
        Node B = this.front;
        if(this.length != L.length) return false;
        while (A != null) {
            if (A.data != B.data)
                return false;
            A = A.next;
            B = B.next;
        }
        return true;
    }
    /*isEmpty()
      Return true if list is empty
     */
    boolean isEmpty(){
        return length==0;
    }
    /*length()
      Return the length of list
     */
    int length(){
        return length;
    }
    /*front()
    Return the data of the first node in list
     */
    Object front(){
        if(this.isEmpty()){
            throw new RuntimeException("List Error:No stuff in List");
        }
        return front.data;
    }
    /*index()
    Return the position of cursor
     */
    int index(){
        if(this.isEmpty()){
            return -1;
        }
        Node N = front;
        int count=0;
        if(cursor == null){
            return -1;
        }
        while (N != null) {
            if(N == cursor){
                return count;
            }
            N=N.next;count++;
        }
        return -1;
    }
    /*back()
    Return the data of the last node in list
     */
    Object back(){
        if(this.isEmpty()){
            throw new RuntimeException("List Error:No stuff in List");
        }
        return back.data;
    }
    /*get()
    Return the data of the cursor
     */
    Object get(){
        if(length>=1&&index()>=0){
            return cursor.data;
        }
        else {return -1;}
    }
    /*clear()
    Wipe out all the elements in List
     */
    void clear(){
        this.front=this.back=null;
        length = 0;
    }
    /*append()
    Add item from back
     */
    void append(Object data){
        Node N = new Node(data);
        if(this.isEmpty()){
            front=back=N;
        }else{
            back.next = N;
            N.prev = back;
            back = N;
        }
        length++;
    }
    /*prepend()
    Add item from front
     */
    void prepend(Object data){
        Node N = new Node(data);
        if (this.isEmpty()) {
            front=back=N;
        }else{
            front.prev=N;
            N.next=front;
            front=N;
        }
        length++;
    }
    /*insertBefore()
   Add item before the cursor
    */
    void insertBefore(Object data){
        Node N = new Node(data);
        if(cursor.prev ==null){
            N.next=front;
            front.prev = N;
            front = N;
            length++;
        }else{
            N.prev=cursor.prev;
            cursor.prev=N;
            N.next=cursor;
            N.prev.next=N;
            length++;
        }
    }
    /*insertAfter()
   Add item After the cursor
    */
    void insertAfter(Object data){
        Node N = new Node(data);
        if(cursor.next==null){
            back.next=N;
            back = N;
            back.prev = cursor;
            length++;
        }else{
            N.next=cursor.next;
            cursor.next=N;
            N.prev=cursor;
            N.next.prev=N;
            length++;
        }
    }
    /*deleteFront()
    Delete the front node of list
     */
    void deleteFront() {
        if (this.isEmpty()) {
            throw new RuntimeException("List Error:No stuff in List");
        }
        if(this.length>1){
            front=front.next;
            front.prev = null;
        }else{
            front=back=null;
        }
        length--;
    }
    /*deleteBack()
    Delete the back node of list
     */
    void deleteBack(){
        if(this.isEmpty()){
            throw new RuntimeException("List Error:No stuff in List");
        }
        if(this.length>1){
            back=back.prev;
            back.next = null;
        }else{
            front=back=null;
        }
        length--;
    }
    /*delete()
    Delete the node with cursor's position of list
     */
    void delete(){
        if(this.cursor != null && length > 1&& index()>= 0){
            if (cursor.prev == null) {
                deleteFront();
            }else if(cursor.next == null){
                deleteBack();
            }else {
                this.cursor.prev.next = this.cursor.next;
                this.cursor.next.prev = this.cursor.prev;
                cursor.prev = cursor.next =null;
                length--;
            }
            this.cursor=null;
        }else{front = back = cursor = null;length--;}
    }
    /*moveFront()
    Move cursor to the front node
     */
    void moveFront(){
        if(!isEmpty()){
            if(index() == -1) {
                cursor = front;
            }else{
                while(this.cursor.prev != null){
                    this.cursor = this.cursor.prev;
                }
            }
        }
    }
    /*moveBack()
    Move cursor to the back node
     */
    void moveBack(){
        if(!isEmpty()){
            if(index() == -1) {
                cursor = back;
            }else{
                while(this.cursor.next != null){
                    this.cursor = this.cursor.next;
                }
            }
        }
    }
    /*moveNext()
    Move cursor to the next node
     */
    void moveNext(){
        if(index() >=0 ) {
            if (cursor != back) {
                cursor = cursor.next;
            } else if (cursor == back) {
                cursor = null;
            }
        }
    }
    /*movePrev()
    Move cursor to the previous node
     */
    void movePrev(){
        if(cursor!=front){
            cursor=cursor.prev;
        }else if(cursor == front){
            cursor=null;
        }
    }
    /*toString()
    Return the whole list and print it out
     */
    public String toString(){
        String out="";
        for (Node N = front; N != null; N = N.next){
            out +=N.toString()+ " ";
        }
        return out;
    }
    /*copy()
    Return a List which has exactly same content of the current list
     */
    List copy(){
        List newList = new List();
        Node current = front;
        while(current!=null){
            newList.append(current.data);
            current=current.next;
        }
        newList.cursor=null;
        return newList;
    }
}
