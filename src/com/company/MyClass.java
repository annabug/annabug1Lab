package com.company;
import java.util.Arrays;
public class MyClass {



    public long[] ZerosCutting(long[] arrey){ // зрізає старші нулі.
        boolean bool = true;
        int m = arrey.length - 1;
        while (m > 0 || arrey[m] != 0){
            if( arrey[m] != 0){
                break;
            }
            m--;
        }
        long[] narrey = new long[m+1];
        for(int f = 0; f < m+1; f++){
            narrey[f] = arrey[f];
        }
        return narrey;
    }

    public int LenShort(long base, long a){ //  к-ть біт в числі
        if(a == 0 ) {
            return 1;
        }
        int rez;
        rez = (int) (Math.log(a) / Math.log(base)) + 1;
        return rez;
    }

    public long compare( long[] g, long [] h){ // порівнює 2 числа
        long[] g1 = ZerosCutting(g);
        long[] h1 = ZerosCutting(h);
        int k = g1.length;
        int m = h1.length;
        long com = 0;
        if( m != k ){
            return (k - m);
        }
        for( int i = k - 1; i > -1; i--){
            if(g[i] != h[i]){
                com = g[i] - h[i]; // результат зрівняння. і виходимо з масиву
                break;
            }
        }
        return Long.signum(com); // повертає -1 , якщо com < 0;
        // якщо ж жодного разу в циклі не увійшов if ( тобто com зберігає значення нуль )
        // , то повертає 0
    }

    public long[] funczero(int m, long[] rr){ // додає старші нулі
        int rezl = m + rr.length;
        int al = rr.length;
        long[] arrees = new long[rezl];
        if(m==0){
            return rr;
        }
        for( int k = 0; k < al; k ++){
            arrees[k] = rr[k];
        }
        for(int t = al; t < rezl; t ++){
            arrees[t] = 0;
        }
        return arrees;
    }

    public long[] multiplyNum(long b, long[] a){ //множення масиву на якесь число
        long temporis;
        long carry = 0;
        long[] c = new long[a.length + 2];
        for( int k = 0 ; k < a.length; k++){
            temporis = a[k]*b + carry;
            c[k] = temporis&(65535);
            carry = temporis>>16;
        }
        for(int k = a.length; k < a.length + 2; k++){
            temporis = c[k]*b + carry;
            c[k] = temporis&(65535);
            carry = temporis>>16;
        }
        return c;
    }

    public long[] shiftArrayElem(long[] arr,int i){ //  збільшує алемент масивц, при цьому перші і будуть 0
        if( i == 0){
            return arr;
        }
        int len = arr.length;
        long[] rs = new long[arr.length + i];
        for(int k = 0; k < len; k++){
            rs[ k + i ] = arr[k];
        }
        return rs;
    }



    public String arrToBinStr(long[] dec_arr){ // з масиву у бінарну
        int base = 16;
        dec_arr = ZerosCutting(dec_arr);
        int len_arr = dec_arr.length;
        StringBuffer str = new StringBuffer("");
        str.append(Long.toBinaryString(dec_arr[len_arr-1]));
        for(int i = len_arr-2; i>-1; i--){
            int nums_number  = LenShort(2,dec_arr[i]);
            int zeros_number = base - nums_number;
            String str_0 = new String(new char[zeros_number]).replace("\0", "0");
            str.append(str_0);
            str.append(Long.toBinaryString(dec_arr[i]));
        }
        String ret_str = str.toString();
        return ret_str;
    }


    public int longBitLength(long[] arr,int bzs){ //рахує к-ть біт у всьому масиві
        int i = arr.length - 1;
        while (arr[i] == 0 & i > 0){
            i--; // переходимо до першого ненульово елементу: наприклад 00000AAAA32743582 , в данному випадку перше А зліва
        }
        int rrs = 0;
        int maxchislosymbols = LenShort(bzs,65535);
        rrs = LenShort(bzs,arr[i]) + maxchislosymbols*i;
        return rrs;
    }


    private void bitShiftRight(long[] arr){ // бітовий зсув вправо.
        short base = 16;
        long rest = 65536;
        int length = arr.length;
        long carry = 1&(arr[length-1]);
        arr[length-1] = arr[length-1]>>1;
        for(int i = length - 2 ; i > -1 ; i--){
            arr[i] = carry*rest + arr[i];
            carry = 1&arr[i];
            arr[i] = arr[i]>>1;
        }
    }

    private boolean isEven(long[] a){
        return (a[0]&1)==0;
    }

    private long[] retMin(long[] a, long[] b){ //повертає мінімум
        if(compare(a,b) > 0){
            return b;
        }
        else {
            return a;
        }
    }



    private boolean nulArray(long[] arr){
        ZerosCutting(arr);
        boolean bool = true;
        for(int i = 0; i < arr.length; i++){
            if(!(arr[i] == 0)){
                return false;
            }
        }
        return true;
    }



    public long[] hexToArray(String str){ // перевод з 16-чної строки в масив
        int k = 4;      // задає розбиття, для 4:  FFFFFFFF -> FFFF0000 + FFFF
        int str_len = str.length();
        int arr_len = str_len/k + ( str_len%k>0 ? 1:0 ) ;// задає кількість комірок для розбиття
        StringBuffer str_buf = new StringBuffer(str);
        long[] arr = new long [arr_len];
        for(int i = k, j = 0; i<=str_len && j <arr_len; i+=k, j++){ //функція переводу
            arr[j] = Long.parseLong(str_buf.substring(str_len-i,str_len-i+k),16);
        }
        if (str_len%k!=0) {
            arr[arr_len - 1] = Long.parseLong(str_buf.substring(0, str_len % k), 16);
        }
        return arr;
    }

    public String arrayToHex(long[] arr){ // функція переводу з масиву в 16 строку
        int k = 16;
        arr = ZerosCutting(arr); // Відрізаю старші нулі
        int len_arr = arr.length;
        StringBuffer str = new StringBuffer("");
        for(int i = len_arr-1; i>-1; i--){
            int nums_number  = LenShort(16,arr[i]); // к-ть цифр в хексовому представленні числа
            int zeros_number = k/4 - nums_number;
            String str_0 = new String(new char[zeros_number]).replace("\0", "0");
            str.append(str_0);
            str.append(Long.toHexString(arr[i]));
        }
        String ret_str = str.toString();
        return ret_str;
    }


    public long[] add(long[] a, long[] b,long[] mod){ // додавання
        a = ZerosCutting(a);
        b = ZerosCutting(b);
        int al = a.length;
        int bl = b.length;
        if(a.length != b.length) {
            if (b.length > a.length) {
                a = funczero(bl - al, a);
            } else {
                b = funczero(al - bl, b);
            }
        }
        al = a.length;
        long carry = 0;
        int i = 0;
        long[] rez = new long[a.length + 1];
        for(i = i; i < al; i++){
            long temp = ( a[i] + b[i] + carry );
            carry = temp >> 16;  ;
            rez[i] = temp&((65535));
        }
        rez[i] = rez[i] + carry;
        return rez;
    }
    public long[] sub(long[] a, long[] b, long[] mod){
        if(compare(a,b) == 0){
            return new long[1]; // якщо рівні, то результат А - Б = 0 -> повертаємо нульовий масив
        }
        long[] ref;
        if(compare(a,b) < 0){ //  введення додаткової змінної для того щоб поміняти місцями а і б, якщо А < B: фактично ця функція віднімання по модулю
            ref = a;
            a = b;
            b = ref;
        }
        int al = a.length;
        int bl = b.length;
        if(a.length != b.length) {
            if (b.length > a.length) {
                a = funczero(bl - al, a);
            } else {
                b = funczero(al - bl, b);
            }
        }
        long carry = 0;
        long[] rr = new long[b.length];
        for (int i = 0; i < b.length; i++) {
            long temp = (a[i] - b[i] - carry);
            carry = temp >>> 63;                   // беззнаковий зсув
            rr[i] = carry * 65536 + temp;
        }
        return rr;
    }

    private long[] bitshiftleftArray(long[] a){ // побітовий зсув вліво на 1
        long temp = 0;
        long[] res = new long[a.length + 1];
        for (int i = 0; i < a.length; i++) {
            res[i] = a[i] + temp;
            res[i] = res[i] << 1;
            temp = res[i] >> 16;
            res[i] = (res[i]) & (65535);
        }
        res[a.length] = res[a.length] + temp;
        return res;
    }

    public long[] bitshiftleftArray(long[] a, int m){ // побітовий зсув на м бітів
        for(int i = 0; i < m; i++){
            a = bitshiftleftArray(a);
            a = ZerosCutting(a);
        }
        return a;
    }

    public long[] mul(long[] a,long[] b, long[] mod){ // множення
        long[] temp;
        a = ZerosCutting(a);
        b = ZerosCutting(b);
        if(a.length != b.length) {
            if (b.length > a.length) {
                a = funczero(b.length - a.length, a);
            } else {
                b = funczero(a.length - b.length, b);
            }
        }
        long[] c = new long[2*a.length];
        for(int i = 0; i < a.length; i++){
            temp = multiplyNum(b[i],a); // множення на число
            temp = shiftArrayElem(temp,i); // зсув при множенні
            c = add(c,temp,module); // додаємо
        }
        return c;
    }

    public long[] square(long[] a){ // до квадрату
        long[] res = mul(a,a,this.module);
        return res;
    }

    public long[] getPower(long[] a,long[] b){ // іднесення до степеня за Горнером
        String bin_string_b = new String(arrToBinStr(b));
        int string_len = bin_string_b.length();
        long[] arr = new long[]{1};
        char chr;
        for(int i = string_len - 1; i > -1; i--){
            chr = bin_string_b.charAt(i);
            if(chr == '1'){
                arr = mul(a,arr,this.module);
            }
            a = mul(a,a,this.module);
        }
        return arr;
    }



    public long[] getGCD(long[] a, long[] b){ // нсд
        long[] d = new long[1];
        d[0] = 1;
        while((isEven(a))&&(isEven(b))){ // робить хоча б одне число не парним
            bitShiftRight(a);
            bitShiftRight(b);
            d = bitshiftleftArray(d);
            d = ZerosCutting(d);
        }
        while(isEven(a)){ //  прибирає можливу зайву 2
            bitShiftRight(a);
        }
        long[] temp;
        while(!nulArray(b)){
            while(isEven(b) && (!nulArray(b))){
                bitShiftRight(b);
                b = ZerosCutting(b);
            }
            temp = retMin(a,b);
            b = sub(a,b,module);
            a = temp;
            a = ZerosCutting(a);
            b = ZerosCutting(b);

        }
        d = mul(d,a,this.module);
        return d;
    }

    public long[][] div(long[] a, long[] b){ //
        long compare = compare(a,b);
        if( compare < 0 ){
            return new long[][]{new long[1],a.clone()};
        }
        int base = 16;
        int k = longBitLength(b,2);
        int t;
        int alen = a.length;
        int blen = b.length;
        long[] q = new long[alen];
        long[] oneLong = new long[1];
        oneLong[0] = 1;
        long[] r = a.clone();
        long[] temp;
        while (compare(r,b) >= 0){
            t = longBitLength(r,2);
            temp = bitshiftleftArray(b, t - k);
            while (compare(r,temp) < 0){
                t = t - 1;
                temp = bitshiftleftArray(b, t - k);
            }
            r = sub(r,temp,this.module);
            long[] b2 = bitshiftleftArray(oneLong,t - k);
            q = add(q,b2,this.module);
            r = ZerosCutting(r);
        }
        return new long[][]{q,r};
    }

/*
    private long[] KillLastDigits(long[] x, int k) { // відкидання к цифр
        int len = x.length;
        boolean bool = len < k;
        if(bool){
            return new long[1];
        }
        long[] rez_arr = new long[len - k + 1];
        int j = 0;
        for(int i = k - 1; i < len; i++ ){
            rez_arr[j] = x[i];
            j++;
        }
        return rez_arr;
    }

    private long[] preCalc(long[] n, int xlen){ // вираховує мю
        long[] b = new long[xlen + 1];
        b[xlen] = 1;
        long[] rs = div(b,n)[0];
        return rs;
    }

    public long[] BarretRedution(long[] arr, long[] n){
        int arlen = arr.length; int nlen = n.length;
        if(arlen < nlen){
            return arr;
        }
        if(arlen != 2*nlen){
            if(arlen < 2*nlen){
                arr = funczero(2*nlen - arlen,arr);
            }
            else{
                n = funczero(arlen-2*nlen,n);
            }
        }
        long[] m = preCalc(n,arr.length);
        int k = arr.length/2;
        long[] q = KillLastDigits(arr,k-1);
        q = mul(m,q, this.module);
        q = KillLastDigits(q,k+1);
        long[] x = mul(q,n, this.module);
        long[] res = sub(arr,x, this.module);
        while (compare(res,n)>=0){
            sub(res,n, this.module);
        }
        return arr;
    }


    public static long[] Long_ModBarrett_Power(long[] a,long[] b,long[] m,long[] n){
        int base = 16;
        String bin_string_b = new String(ArrToBinStr(b,base));
        int string_len = bin_string_b.length();
        long[] arr = new long[]{1};
        char chr;
        for(int i = string_len - 1; i > -1; i--){
            chr = bin_string_b.charAt(i);
            if(chr == '1'){
                arr = LongMul(a,arr,base);
                arr = BarretRedution(arr,m,n);
            }
            a = LongMul(a,a,base);
            a = BarretRedution(a,m,n);
            System.out.println("ARR = " + Arrays.toString(arr));
        }
        return arr;
    }*/

    long[] module; // ми створюємо клас MyClass і робимо обчислення по модулю module.

}
