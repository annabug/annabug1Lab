package com.company;
import java.util.Arrays;
public class MyClass {

    private long[] module; // ми створюємо клас MyClass і робимо обчислення по модулю module.
    private long[] preCalc; // це наше мю , яке ми розраховуємо для редукції Барета
    private int modulen;

    public void setModule(long[] arr){
        arr = ZerosCutting(arr);
        int len = arr.length;
        this.module = arr;
        this.preCalc = preCalc(arr,len);
        this.preCalc = ZerosCutting(this.preCalc);
        this.modulen = len;
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
        str.append(Long.toHexString(arr[len_arr - 1]));
        for(int i = len_arr-2; i>-1; i--){
            int nums_number  = LenShort(16,arr[i]); // к-ть цифр в хексовому представленні числа
            int zeros_number = k/4 - nums_number;
            String str_0 = new String(new char[zeros_number]).replace("\0", "0");
            str.append(str_0);
            str.append(Long.toHexString(arr[i]));
        }
        String ret_str = str.toString();
        return ret_str;
    }

    public String arrayToBin(long[] arr){ // функція переводу з масиву в 16 строку
        int k = 16;
        arr = ZerosCutting(arr); // Відрізаю старші нулі
        int len_arr = arr.length;
        StringBuffer str = new StringBuffer("");
        str.append(Long.toBinaryString(arr[len_arr - 1]));
        for(int i = len_arr-2; i>-1; i--){
            int nums_number  = LenShort(2,arr[i]); // к-ть цифр в хексовому представленні числа
            int zeros_number = k - nums_number;
            String str_0 = new String(new char[zeros_number]).replace("\0", "0");
            str.append(str_0);
            str.append(Long.toBinaryString(arr[i]));
        }
        String ret_str = str.toString();
        return ret_str;
    }

    public long[] addM(long[] a, long[] b){
        long[] res = add(a,b);
        return div(res,this.module)[1];
    }
    public long[] subM(long[] a, long[] b){
        long[] res = sub(a,b);
        return div(res,this.module)[1];
    }
    public long[] mulM(long[] a, long[] b){
        long[] res = mul(a,b);
        return div(res,this.module)[1];
    }

    public long[] square(long[] a){ // до квадрату
        long[] res = mul(a,a);
        return res;
    }

    public long[] getPower(long[] a,long[] b){ // іднесення до степеня за Горнером
        String bin_string_b = new String(arrayToBin(b));
        int string_len = bin_string_b.length();
        long[] arr = new long[]{1};
        char chr;
        for(int i = string_len - 1; i > -1; i--){
            chr = bin_string_b.charAt(i);
            if(chr == '1'){
                arr = mul(a,arr);
            }
            a = mul(a,a);
        }
        return div(arr,this.module)[1];
    }



    public long[] getGCD(long[] a, long[] b){ // нсд
        long[] d = new long[1];
        d[0] = 1;
        while((isEven(a))&&(isEven(b))){ // робить хоча б одне число не парним
            bitShiftRight(a);
            bitShiftRight(b);
            d = bitshiftleftArray(d,1);
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
            b = sub(a,b);
            a = temp;
            a = ZerosCutting(a);
            b = ZerosCutting(b);

        }
        d = mul(d,a);
        return d;
    }

    public long[][] div(long[] a, long[] b){ //
        if( b == null){
            return new long[][]{new long[1],a.clone()};
        }
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
        int i = 0;
        while (compare(r,b) >= 0){
            t = longBitLength(r,2);
            temp = bitshiftleftArray(b, t - k);
            while (compare(r,temp) < 0){
                t = t - 1;
                temp = bitshiftleftArray(b, t - k);
            }
            r = sub(r,temp);
            long[] b2 = bitshiftleftArray(oneLong,t - k);
            q = add(q,b2);
            r = ZerosCutting(r);
        }
        return new long[][]{q,r};
    }

    public long[] getLCM(long[] a, long[] b){
        long[] mulprod = mul(a,b);
        long[] gcd = getGCD(a,b);
        return div(mulprod,gcd)[0];
    }

    private long[] preCalc(long[] n, int xlen){ // вираховує мю
        long[] b = new long[2*xlen + 1];
        b[b.length - 1] = 1;
        long[] rs = div(b,n)[0];
        return rs;
    }

    public long[] BarretRedution(long[] x){
        if( this.preCalc == null){
            System.out.println("ERROR: PRECALC MU IS NOT EXIST; PLEASE, SET THE MODULE."); // Повідомлення про те, що Мю не передобчислене або не існує
            return x;
        }
        if(this.module == null){
            System.out.println("ERROR: MODULE IS NOT EXIST; PLEASE, SET THE MODULE.");// Повідомлення про те, що модуль не існує або невідомий
            return x;
        }
        int arlen = x.length;
        int compare = compare(x,this.module);
        if( compare < 0 ){// arr < module -> залишок від ділення arr/module буде arr
            return x;
        }
        if( compare == 0){
            return new long[1];
        }
        if(arlen != 2*this.modulen){
            if(arlen < 2*this.modulen){
                x = funczero(2*this.modulen - arlen,x);
            }
            else{
                this.module = funczero(arlen-2*this.modulen,this.module);
            }
        }
        int k = x.length/2;
        long[] q;
        q = killLastDigits(x,k-1);
        q = mul(this.preCalc,q);
        q = killLastDigits(q,k+1);
        long[] qn = mul(q,this.module);
        long[] res = sub(x,qn);
        while (compare(res,this.module)>=0){
            res = sub(res,this.module);
        }
        return res;
    }

    private boolean verification(long[] a){
        if( this.preCalc == null){
            System.out.println("ERROR: PRECALC MU IS NOT EXIST; PLEASE, SET THE MODULE."); // Повідомлення про те, що Мю не передобчислене або не існує
            return false;
        }
        if(this.module == null){
            System.out.println("ERROR: MODULE IS NOT EXIST; PLEASE, SET THE MODULE.");// Повідомлення про те, що модуль не існує або невідомий
            return false;
        }
        if(a == null){
            return false;
        }
        return true;

    }


    public long[] Long_ModBarrett_Power(long[] a,long[] b){
        a = ZerosCutting(a);
        b = ZerosCutting(b);
        if(!verification(a)){
            return a;
        }
        if(a.length == 1){
            if(this.modulen > 1){
                if(this.module[1] != 0){
                    return a;
                }
            }
        }
        if(b==null){
            return new long[1];
        }
        if(b.length == 1){
            if(b[0] == 1){
                return BarretRedution(a);
            }
        }
        int base = 16;
        String bin_string_b = arrayToBin(b);
        int string_len = bin_string_b.length();
        long[] arr = new long[]{1};
        char chr;
        for(int i = string_len - 1; i > -1; i--){
            chr = bin_string_b.charAt(i);
            if(chr == '1'){
                arr = mul(a,arr);
                arr = BarretRedution(arr);
            }
            a = mul(a,a);
            a = BarretRedution(a);
        }
        return arr;
    }



    /*
    ***********************

        __________________PRIVATE__________________

    ***********************
    */

    private int getLen(long[] x){
        int l = x.length;
        while( l > 0 && x[l - 1] == 0){
            l--;
        }
        return l;
    }


    private long[] killLastDigits(long[] x, int k) {
        x = ZerosCutting(x);
        int len = x.length;
        if(k >= len){
            return new long[1];
        }
        long[] res = new long[len - k];
        for(int i = k; i < len; i++){
            res[i - k] = x[i];
        }
        return res;
    }

    private long[] add(long[] a, long[] b){
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
    private long[] sub(long[] a, long[] b){
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

    private long lshift(long[] res, int k,
                        long[] a, int count, int len){
        long temp;
        int i = len - 1;
        int count2 = 16 - count;
        long high_word = a[i];
        long low_word;
        long retval = high_word>>>count2;
        k++;
        long base = 65535;
        while(--i >= 0){
            low_word = a[i];
            temp = (((high_word<<count)&(base))|(low_word>>>count2));
            res[i + k] = temp;
            high_word = low_word;
        }
        res[i + k] = (high_word<<count)&(base);
        return retval;
    }

    private long[] bitshiftleftArray(long[] a, int m){
        if(m <= 0){
            return a;
        }
        int len = a.length;
        int k = m/16;
        int count = m%16;
        long[] res = new long[len + k + 1];
        if(count==0){
            for(int i = 0; i < len; i ++){
                res[ i + k ] = a[i];
            }
        }
        else{
            int new_len = len + k + 1;
            long outshift = lshift(res,k,a,count,len);
            count = 16 - count;
            res[new_len - 1] = (outshift<<count)>>count;
        }
        return res;
    }

    private long[] mul(long[] a,long[] b){
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
            c = add(c,temp); // додаємо
        }
        return c;
    }


    private long[] ZerosCutting(long[] arrey){ // зрізає старші нулі.
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

    private int LenShort(long base, long a){ //  к-ть біт в числі
        if(a == 0 ) {
            return 1;
        }
        int rez;
        rez = (int) (Math.log(a) / Math.log(base)) + 1;
        return rez;
    }

    private int compare( long[] a, long [] b){ // порівнює 2 числа
        int i = a.length;
        int j = b.length;
        while( i > 0 && a[i - 1] == 0){
            i--;
        }
        while( j > 0 && b[j - 1] == 0){
            j--;
        }
        if( i != j ){
            return Long.signum(i - j);
        }
        for( i = i ; i > 0 ; i--){
            if(a[i-1] != b[i-1]){
                return Long.signum(a[i-1] - b[i-1]);
            }
        }
        return 0; // повертає -1 , якщо com < 0; com - результат зрівнняня двох чисел покомпонентно
        // якщо ж жодного разу в циклі не увійшов if ( тобто усі компоненти ВеликихЧисле рівні )
        // , то повертає 0
    }

    private long[] funczero(int m, long[] rr){ // додає старші нулі
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

    private long[] multiplyNum(long b, long[] a){ //множення масиву на якесь число
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

    private long[] shiftArrayElem(long[] arr,int i){ //  збільшує алемент масивц, при цьому перші і будуть 0
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


    private int longBitLength(long[] arr,int bzs){ //рахує к-ть біт у всьому масиві
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

    private long[] retMin(long[] a, long[] b){
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





}
