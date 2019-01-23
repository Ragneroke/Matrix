public class Matrix {
    private class Entry {
        //Fields
        int col;
        double val;

        //Constructor
        Entry(int col, double val) {
            this.col = col;
            this.val = val;
        }

        /*equals()
        Overrides Object's equals() method
         */
        public boolean equals(Object x) {
            boolean eq = false;
            Entry that;
            if (x instanceof Entry) {
                that = (Entry) x;
                eq = (this.col == that.col && this.val == that.val);
            }
            return eq;
        }

        /*toString()
        Overrides Object's toString() method
         */
        public String toString() {
            String out = "(";
            out += String.valueOf(this.col) + ", ";
            out += String.valueOf(this.val) + ")";
            return out;
        }
    }

    //Fields
    List[] row;
    int size;
    int NNZ;

    //Constructor
    Matrix(int n) {
        if (n <= 0) {
            throw new RuntimeException("Matrix's size must greater than zero!");
        }
        size = n;
        row = new List[size];
        NNZ = 0;
        for (int i = 0; i < size; i++) {
            row[i] = new List();
        }
    }

    /*getSize()
    Return the size of Matrix
     */
    int getSize() {
        return size;
    }

    /*getNNZ()
    Return the number of non-zero entries of Matrix
     */
    int getNNZ() {
        return NNZ;
    }

    /*equals(Object x)
    Overrides Object's toString method
     */
    public boolean equals(Object x) {
        boolean eq = false;
        Matrix that;
        if (x instanceof Matrix) {
            that = (Matrix) x;
            List a;
            List b;
            if (this.size == that.size && this.NNZ == that.NNZ) {
                int i = 0;
                a = this.row[i];
                while (a != null && i < size) {
                    a = this.row[i];
                    b = that.row[i];
                    if (!a.equals(b)) {
                        return eq;
                    }
                    i++;
                }
                eq = true;
            }
        }
        return eq;
    }

    /*makeZero()
    Set the matrix to empty
     */
    void makeZero() {
        for (int i = 0; i < size; i++) {
            row[i] = null;
        }
        size = 0;
        NNZ = 0;
    }

    /*copy()
    Return the same matrix
     */
    Matrix copy() {
        Matrix x = new Matrix(size);
        for (int i = 0; i < size; i++) {
            if (row[i] != null) {
                List a = new List();
                List b = row[i];
                Entry temp;
                for (b.moveFront(); b.index() >= 0; b.moveNext()) {
                    if (b.get() != null) {
                        temp = (Entry) b.get();
                        a.append(new Entry(temp.col, temp.val));
                        x.NNZ++;
                    }
                }
                x.row[i] = a;
            }
        }
        return x;
    }

    /*changeEntry(int i, int j, double x)
    Changes the element in ith row, jth column to x
     */
    void changeEntry(int i, int j, double x) {
        if (i <= size || i > 0 || j > 0 || j <= size) {
            Entry temp;
            if (row[i - 1] == null) {
                if (x != 0) {
                    row[i - 1] = new List();
                    row[i - 1].append(new Entry(j, x));
                    NNZ++;
                    return;
                }
            } else {
                row[i - 1].moveFront();
                while (row[i - 1].index() >= 0) {
                    temp = (Entry) row[i - 1].get();
                    if (temp.col == j) {
                        if (x == 0 && temp.val != 0) {
                            row[i - 1].delete();
                            NNZ--;
                            return;
                        } else {
                            temp.col = j;
                            temp.val = x;
                            return;
                        }
                    }
                    if (temp.col > j) {
                        if (x != 0) {
                            row[i - 1].insertBefore(new Entry(j, x));
                            NNZ++;
                            return;
                        }
                    }
                    row[i - 1].moveNext();
                }
                if (x != 0) {
                    row[i - 1].append(new Entry(j, x));
                    NNZ++;
                }
            }
        }
    }

    /*scalarMult(double x)
    Multiple every elements in the Matrix by x
     */
    Matrix scalarMult(double x) {
        Matrix temp = copy();
        Matrix sol = new Matrix(this.size);
        if (x == 0) {
            return sol;
        } else {
            for (int i = 0; i < size; i++) {
                if (temp.row[i] != null) {
                    List cal = new List();
                    Entry newElement;
                    for (temp.row[i].moveFront(); temp.row[i].index() >= 0; temp.row[i].moveNext()) {
                        newElement = (Entry) temp.row[i].get();
                        cal.append(new Entry(newElement.col, newElement.val * x));
                        sol.NNZ++;
                    }
                    sol.row[i] = cal;
                }
            }
        }
        return sol;

    }

    /*add(Matrix M)
    Return the sum of current Matrix and Matrix M
     */
    Matrix add(Matrix M) {
        if (this.getSize() != M.getSize()) {
            throw new RuntimeException("Matrix with different size cannot add up!");
        }
        Matrix temp = new Matrix(size);
        if (this.equals(M)) {
            return M.scalarMult(2);
        }
        for (int i = 0; i < size; i++) {
            List sum = new List();
            List add1 = this.row[i];
            List add2 = M.row[i];
            Entry a;
            Entry b;
            if (this.row[i] == null && M.row[i] != null) {
                temp.row[i] = add2;
            } else if (this.row[i] != null && M.row[i] == null) {
                temp.row[i] = add1;
            } else if (this.row[i] != null && M.row[i] != null) {
                row[i].moveFront();
                M.row[i].moveFront();
                while (add1.index() >= 0 && add2.index() >= 0) {
                    a = (Entry) add1.get();
                    b = (Entry) add2.get();
                    if (a.col == b.col) {
                        if (a.val + b.val == 0) {
                            temp.NNZ--;
                        } else {
                            sum.append(new Entry(a.col, a.val + b.val));
                            temp.NNZ++;
                            add1.moveNext();
                            add2.moveNext();
                        }
                    } else if (a.col > b.col) {
                        sum.append(new Entry(b.col, b.val));
                        temp.NNZ++;
                        add2.moveNext();
                    } else if (a.col < b.col) {
                        sum.append(new Entry(a.col, a.val));
                        temp.NNZ++;
                        add1.moveNext();
                    }
                }
                while (add1.index() >= 0) {
                    a = (Entry) add1.get();
                    sum.append(new Entry(a.col, a.val));
                    temp.NNZ++;
                    add1.moveNext();
                }
                while (add2.index() >= 0) {
                    b = (Entry) add2.get();
                    sum.append(new Entry(b.col, b.val));
                    temp.NNZ++;
                    add2.moveNext();
                }
            }
            temp.row[i] = sum;
        }
        return temp;

    }

    /*sub(Matrix M)
    Return the difference of current Matrix and Matrix M
     */
    Matrix sub(Matrix M) {
        if (this.getSize() != M.getSize()) {
            throw new RuntimeException("Matrix with different size cannot sub!");
        }
        Matrix temp = new Matrix(size);
        if (this.equals(M)) {
            Matrix non = new Matrix(size);
            return non;
        }
        for (int i = 0; i < size; i++) {
            List sum = new List();
            List add1 = this.row[i];
            List add2 = M.row[i];
            Entry a;
            Entry b;
            if (this.row[i] == null && M.row[i] != null) {
                temp.row[i] = M.row[i];
            } else if (this.row[i] != null && M.row[i] == null) {
                temp.row[i] = this.row[i];
            } else if (this.row[i] != null && M.row[i] != null) {
                row[i].moveFront();
                M.row[i].moveFront();
                while (add1.index() >= 0 && add2.index() >= 0) {
                    a = (Entry) add1.get();
                    b = (Entry) add2.get();
                    if (a.col == b.col) {
                        if (a.val - b.val == 0) {
                            add1.moveNext();
                            add2.moveNext();
                        } else {
                            sum.append(new Entry(a.col, a.val - b.val));
                            temp.NNZ++;
                            add1.moveNext();
                            add2.moveNext();
                        }
                    } else if (a.col > b.col) {
                        sum.append(new Entry(b.col, -b.val));
                        temp.NNZ++;
                        add2.moveNext();
                    } else if (a.col < b.col) {
                        sum.append(new Entry(a.col, a.val));
                        temp.NNZ++;
                        add1.moveNext();
                    }
                }
                while (add1.index() >= 0) {
                    a = (Entry) add1.get();
                    sum.append(new Entry(a.col, a.val));
                    temp.NNZ++;
                    add1.moveNext();
                }
                while (add2.index() >= 0) {
                    b = (Entry) add2.get();
                    sum.append(new Entry(b.col, -b.val));
                    temp.NNZ++;
                    add2.moveNext();
                }
            }
            temp.row[i] = sum;
        }
        return temp;

    }

    /*transpose()
    Return the transpose matrix of current matrix
     */
    Matrix transpose() {
        Matrix tran = new Matrix(size);
        Entry temp;
        for (int i = 0; i < size; i++) {
            if (row[i] != null) {
                row[i].moveFront();
                while (row[i].index() >= 0) {
                    temp = (Entry) row[i].get();
                    tran.changeEntry(temp.col, i + 1, temp.val);
                    row[i].moveNext();
                }
            }
        }
        return tran;
    }

    /*mult(Matrix M)
    Return the product of current matrix and Matrix M
     */
    Matrix mult(Matrix M) {
        if (this.getSize() != M.getSize()) {
            throw new RuntimeException("Matrix with different size cannot multiple!");
        }
        Matrix A = new Matrix(size);
        Matrix B = M.transpose();
        for (int i = 0; i < size; i++) {

            if (row[i] != null) {
                for (int j = 0; j < B.size; j++) {
                    if (B.row[i] != null) {
                        if (dot(row[i], B.row[j]) != 0) {
                            double sol = dot(row[i], B.row[j]);
                            A.changeEntry(i + 1, j + 1, sol);
                        }

                    }
                }
            }
        }
        return A;
    }

    /*dot(List A, List B)
    A help method to return the product of elements in the
    list A and B
     */
    double dot(List A, List B) {
        Entry a;
        Entry b;
        double sol = 0;
        if (B == null || A == null) return sol;
        A.moveFront();
        B.moveFront();
        while (A.index() >= 0 && B.index() >= 0) {
            a = (Entry) A.get();
            b = (Entry) B.get();
            if (a.col > b.col) {
                B.moveNext();
            } else if (a.col < b.col) {
                A.moveNext();
            } else {
                sol += a.val * b.val;
                A.moveNext();
                B.moveNext();
            }
        }
        return sol;
    }

    public String toString() {
        String out = "";
        for (int i = 0; i < this.size; i++) {
            if (!row[i].isEmpty()) {
                out += (i + 1) + ": ";
                out += row[i].toString();
                out += "\n";
            }
        }
        return out;
    }
}