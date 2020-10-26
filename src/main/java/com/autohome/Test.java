package com.autohome;

public class Test {

    public static void main(String[] args) {
        String word = "abcabc";
        StringBuilder newWord = new StringBuilder(word);
        StringBuilder chWord ;
        int input1 = 1, input2 = 1;
        int index1 = word.length()-input1;
        int index2 = word.length()-input2;
        int i = 0;
        for(i = 0 ; i < word.length(); i++){
             chWord =new StringBuilder(String.valueOf(newWord.charAt(index1)));
             newWord = chWord.append(newWord.deleteCharAt(index1));

             if(word.equals(newWord.toString())){
                 break;
             }
            chWord =new StringBuilder(String.valueOf(newWord.charAt(index2)));
            newWord = chWord.append(newWord.deleteCharAt(index2));
            i++;
             if(word.equals(newWord.toString()))
                 break;
        }

        System.out.println(i);

    }
}
