import java.math.BigInteger;
public class Week1{
    public static void main(String[] argvs){
        int len = 190 / 2;

        int[] map = new int[256];
        map['0'] = 0; map['1'] = 1; map['2'] = 2; map['3'] = 3;
        map['4'] = 4; map['5'] = 5; map['6'] = 6; map['7'] = 7;
        map['8'] = 8; map['9'] = 9; map['a'] = 10; map['b'] = 11;
        map['c'] = 12; map['d'] = 13; map['e'] = 14; map['f'] = 15;

        char[] revMap = new char[16];
        revMap[0] = '0'; revMap[1] = '1'; revMap[2] = '2'; revMap[3] = '3';
        revMap[4] = '4'; revMap[5] = '5'; revMap[6] = '6'; revMap[7] = '7';
        revMap[8] = '8'; revMap[9] = '9'; revMap[10] = 'a'; revMap[11] = 'b';
        revMap[12] = 'c'; revMap[13] = 'd'; revMap[14] = 'e'; revMap[15] = 'f';

        String[] texts = new String[10];
        texts[0] = "315c4eeaa8b5f8aaf9174145bf43e1784b8fa00dc71d885a804e5ee9fa40b16349c146fb778cdf2d3aff021dfff5b403b510d0d0455468aeb98622b137dae857553ccd8883a7bc37520e06e515d22c954eba5025b8cc57ee59418ce7dc6bc4";
        texts[1] = "234c02ecbbfbafa3ed18510abd11fa724fcda2018a1a8342cf064bbde548b12b07df44ba7191d9606ef4081ffde5ad46a5069d9f7f543bedb9c861bf29c7e205132eda9382b0bc2c5c4b45f919cf3a9f1cb74151f6d551f4480c82b2cb24cc";
        texts[2] = "32510ba9a7b2bba9b8005d43a304b5714cc0bb0c8a34884dd91304b8ad40b62b07df44ba6e9d8a2368e51d04e0e7b207b70b9b8261112bacb6c866a232dfe257527dc29398f5f3251a0d47e503c66e935de81230b59b7afb5f41afa8d661cb";
        texts[3] = "32510ba9aab2a8a4fd06414fb517b5605cc0aa0dc91a8908c2064ba8ad5ea06a029056f47a8ad3306ef5021eafe1ac01a81197847a5c68a1b78769a37bc8f4575432c198ccb4ef63590256e305cd3a9544ee4160ead45aef520489e7da7d83";
        texts[4] = "3f561ba9adb4b6ebec54424ba317b564418fac0dd35f8c08d31a1fe9e24fe56808c213f17c81d9607cee021dafe1e001b21ade877a5e68bea88d61b93ac5ee0d562e8e9582f5ef375f0a4ae20ed86e935de81230b59b73fb4302cd95d770c6";
        texts[5] = "32510bfbacfbb9befd54415da243e1695ecabd58c519cd4bd2061bbde24eb76a19d84aba34d8de287be84d07e7e9a30ee714979c7e1123a8bd9822a33ecaf512472e8e8f8db3f9635c1949e640c621854eba0d79eccf52ff111284b4cc61d1";
        texts[6] = "32510bfbacfbb9befd54415da243e1695ecabd58c519cd4bd90f1fa6ea5ba47b01c909ba7696cf606ef40c04afe1ac0aa8148dd066592ded9f8774b529c7ea125d298e8883f5e9305f4b44f915cb2bd05af51373fd9b4af511039fa2d96f83";
        texts[7] = "315c4eeaa8b5f8bffd11155ea506b56041c6a00c8a08854dd21a4bbde54ce56801d943ba708b8a3574f40c00fff9e00fa1439fd0654327a3bfc860b92f89ee04132ecb9298f5fd2d5e4b45e40ecc3b9d59e9417df7c95bba410e9aa2ca24c5";
        texts[8] = "271946f9bbb2aeadec111841a81abc300ecaa01bd8069d5cc91005e9fe4aad6e04d513e96d99de2569bc5e50eeeca709b50a8a987f4264edb6896fb537d0a716132ddc938fb0f836480e06ed0fcd6e9759f40462f9cf57f4564186a2c1778f";
        texts[9] = "466d06ece998b7a2fb1d464fed2ced7641ddaa3cc31c9941cf110abbf409ed39598005b3399ccfafb61d0315fca0a314be138a9f32503bedac8067f03adbf3575c3b8edc9ba7f537530541ab0f9f3cd04ff50d66f1d559ba520e89a2cb2a83";

        boolean[] dc = new boolean[len];
        char[] key = new char[len*2];
        
        // BigInteger space = new BigInteger("2020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020", 16);
        // BigInteger[] cts = new BigInteger[10];
        // for(int i = 0; i <= 9; i ++){
        //     cts[i] = new BigInteger(texts[i], 16);
        // }

        // int[][][] myster = new int[10][10][len];
        // for(int i = 0; i <= 9; i++){
        //     for(int j = 0; j <= 9; j++){
        //         if(i != j){
        //             String s = cts[i].xor(cts[j]).xor(space).toString(16);
        //             int actualLen = s.length();
        //             if(actualLen < len * 2){
        //                 StringBuilder sb = new StringBuilder();
        //                 for(int m = 0; m < len * 2 - actualLen; m++)
        //                     sb.append('0');
        //                 s = sb.append(s).toString();
        //             }
        //             for(int k = 0; k < len; k += 1){
        //                 myster[i][j][k] = map[s.charAt(2*k)] * 16 + map[s.charAt(2*k+1)];  
        //             }
        //         }
        //     }
        //     for(int k = 0; k < len; k += 1){
        //         boolean sp = true;
        //         for(int j = 0; j <= 9; j++){
        //             int temp = myster[i][j][k];
        //             if(i != j && !(temp >= 65 && temp <= 90 || temp >= 97 && temp <= 122 || temp == 32)){
        //                 sp = false;
        //                 break;
        //             }
        //         }
        //         if(sp){
        //             dc[k] = true;
        //             char[] word = new char[2];
        //             String s = new BigInteger(texts[i].substring(2*k, 2*k+2), 16).xor(new BigInteger("20", 16)).toString(16);
        //             if(s.length() < 2){
        //                 key[k*2] = '0';
        //                 key[k*2+1] = s.charAt(0);
        //             } else {
        //                 key[k*2] = s.charAt(0);
        //                 key[k*2+1] = s.charAt(1);
        //             }
        //         }
        //     }
        // }
        // for(int i = 0; i < len; i++){
        //     if(!dc[i]){
        //         key[i*2] = '0';
        //         key[i*2+1] = '0';
        //         System.out.println(i);
        //     }
        // }
        // // for(int i = 0; i < len * 2; i ++){
        // //     System.out.print(key[i]);
        // // }
        // // System.out.println();
        // char[][] pt = new char[10][len];
        // BigInteger b = new BigInteger(new String(key), 16);
        // for(int i = 0; i < 10; i ++){
        //     String s = b.xor(cts[i]).toString(16);
        //     int actualLen = s.length();
        //     if(actualLen < len * 2){
        //         StringBuilder sb = new StringBuilder();
        //         for(int m = 0; m < len * 2 - actualLen; m++)
        //             sb.append('0');
        //         s = sb.append(s).toString();
        //     }
        //     for(int k = 0; k < len; k += 1){
        //         int temp = map[s.charAt(2*k)] * 16 + map[s.charAt(2*k+1)];
        //         if(temp >= 65 && temp <= 90 || temp >= 97 && temp <= 122 || temp == 32)
        //             pt[i][k] = (char) temp;
        //         else
        //             pt[i][k] = '~';
        //     }
        // }
        // for(int k = 0; k < len; k ++){
        //     boolean valid = true;
        //     for(int i = 0; i < 10; i++){
        //         if(pt[i][k] == '~'){
        //             valid = false;
        //             break;
        //         }
        //     }
        //     if(!valid){
        //         for(int i = 0; i < 10; i++){
        //             pt[i][k] ='~';
        //         }     
        //     }
        // }
        // for(int i = 0; i < 10; i ++){
        //     System.out.println(new String(pt[i]));
        // }

        char[][] oriText = new char[10][len];
        oriText[0] = "We can factor the number 15 with qu~~tu~ computers. We can also factor the number 15~with a d~~".toCharArray();
        oriText[1] = "Euler would probably enjoy that now~~is~theorem becomes a corner stone of crypto - ~~nonymous~~".toCharArray();
        oriText[2] = "The nice thing about Keeyloq is now~~e cryptographers can drive a lot of fancy cars ~ Dan Bon~~".toCharArray();
        oriText[3] = "The ciphertext produced by a weak encryption algorithm looks as good as ciphertext p~oduced by ".toCharArray();
        oriText[4] = "You don't want to buy a set of car ~~ys~from a guy who specializes in stealing cars ~ Marc Ro~~".toCharArray();
        oriText[5] = "There are two types of cryptography~~ t~at which will keep secrets safe from your li~tle sist~~".toCharArray();
        oriText[6] = "There are two types of cyptography:~~ne~that allows the Government to use brute force to brea~~".toCharArray();
        oriText[7] = "We can see the point where the chip~~s ~nhappy if a wrong bit is sent and consumes m~re power~~".toCharArray();
        oriText[8] = "A (private-key)  encryption scheme ~~at~s 3 algorithms, namely a procedure for gener~ting key~~".toCharArray();
        oriText[9] = " The Concise OxfordDictionary (2006~~de~¬nes crypto as the art of  writing o r solvf~g codes~~ ".toCharArray();

        for(int k = 0; k < len; k++){
            for(int i = 0; i < 10; i++){
                if(oriText[i][k] != '~'){
                    dc[k] = true;
                    int temp = map[texts[i].charAt(2*k)] * 16 + map[texts[i].charAt(2*k+1)];
                    temp ^= (int) oriText[i][k];
                    key[2*k] = revMap[temp/16];
                    key[2*k+1] = revMap[temp%16];
                    break;
                }
            }
        }
        // for(int i = 0; i < len; i++){
        //     if(!dc[i]){
        //         key[i*2] = '0';
        //         key[i*2+1] = '0';
        //         System.out.println(i);
        //     }
        // }
        // for(int k = 0; k < len; k++){
        //     if(dc[k]){
        //         for(int i = 0; i < 10; i++){
        //             int temp = map[texts[i].charAt(2*k)] * 16 + map[texts[i].charAt(2*k+1)];
        //             int temp1 = map[key[2*k]] * 16 + map[key[2*k+1]];
        //             temp ^= temp1;
        //             oriText[i][k] = (char) temp;
        //         }
        //     }
        // }
        // for(int i = 0; i < 10; i ++){
        //     System.out.println(new String(oriText[i]));
        // }
        char[] target = "32510ba9babebbbefd001547a810e67149caee11d945cd7fc81a05e9f85aac650e9052ba6a8cd8257bf14d13e6f0a803b54fde9e77472dbff89d71b57bddef121336cb85ccb8f3315f4b52e301d16e9f52f904".toCharArray();
        for(int k = 0; k < len && k < target.length/2; k++){
            int temp = map[target[2*k]] * 16 + map[target[2*k+1]];
            int temp1 = map[key[2*k]] * 16 + map[key[2*k+1]];
            temp ^= temp1;
            System.out.print((char)temp);
        }
        System.out.println();
    }
}