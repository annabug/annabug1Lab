package com.company;

public class MyClass {
a

    long sravnit6(long[] a, long[] b){
        int len1 = a.length; int len2 = b.length;
        if( len1 != len2 ){
            if(len1 < len2){
                a = addzeros(a,len2 - len1);
            }
            else{
                b = addzeros(a,len1 - len2);
            }
        }
        for(int i = 0 ; i < a.length; i++){
            if(a[i]!=b[i]){
                return a[i] - b[i];
            }
        }
        return  0;
    }

    public long[] addzeros(long[] arr, int m){
        int len1 = arr.length;
        long[] resarr = new long[len1 + m];
        for(int i = 0; i < len1; i ++){
            resarr[i] = arr[i];
        }
        return resarr;
    }

    public long[] add(long[] a, long[] b){
        int len1 = a.length, len2 = b.length;
        if( len1 != len2 ){
            if(len1 < len2){
                a = addzeros(a,len2 - len1);
            }
            else{
                b = addzeros(a,len1 - len2);
            }
        }
        long[] arres = new long[a.length];
        long carry = 0;
        long mod = 1;
        mod = mod<<16;
        mod = mod - 1;
        for( int i = 0; i < a.length; i++){
            arres[i] = a[i] + b[i] + carry;
            carry = arres[i]>>16;
            arres[i] = arres[i]^mod;
        }
        arres[a.length] = arres[a.length] + carry;
        return arres;
    }

    public long[] sub(long[] a, long[] b){
        int len1 = a.length, len2 = b.length;
        if( len1 != len2 ){
            if(len1 < len2){
                a = addzeros(a,len2 - len1);
            }
            else{
                b = addzeros(a,len1 - len2);
            }
        }
        long carry = 0;
        long mod = 1;
        mod = mod<<16;
        mod = mod - 1;
        long[] arres = new long[a.length];
        for (int i = 0; i < b.length; i++) {
            long temp = (a[i] - b[i] + carry);
            if(temp < 0){
                carry = -1;
            }
            arres[i] = carry * mod + temp;
        }
        return arres;
    }




}
