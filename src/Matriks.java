public class Matriks {
    private int[][] data;
    
    public Matriks() {
        data = new int[2][2];
    }
    
    public int getElement(int baris, int kolom) {
        return data[baris][kolom];
    }
    
    public void setElement(int baris, int kolom, int nilai) {
        data[baris][kolom] = nilai;
    }
    
    // Operasi perkalian matriks
    public static Matriks perkalian(Matriks A, Matriks B) {
        Matriks hasil = new Matriks();
        
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                hasil.data[i][j] = 0;
                for(int k = 0; k < 2; k++) {
                    hasil.data[i][j] += A.data[i][k] * B.data[k][j];
                }
            }
        }
        return hasil;
    }
    
    // Operasi pengurangan matriks
    public static Matriks pengurangan(Matriks A, Matriks B) {
        Matriks hasil = new Matriks();
        
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                hasil.data[i][j] = A.data[i][j] - B.data[i][j];
            }
        }
        return hasil;
    }
    
    // Operasi penjumlahan matriks
    public static Matriks penjumlahan(Matriks A, Matriks B) {
        Matriks hasil = new Matriks();
        
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                hasil.data[i][j] = A.data[i][j] + B.data[i][j];
            }
        }
        return hasil;
    }
    
    public String toFormattedString() {
        return "[" + data[0][0] + " " + data[0][1] + "]\n" +
               "[" + data[1][0] + " " + data[1][1] + "]";
    }
}