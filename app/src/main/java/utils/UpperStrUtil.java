package utils;

import org.json.JSONObject;

/**
 * Created by haopei on 2015/8/31.
 */
public class UpperStrUtil {//66565656aa
    /**
     * aa
     * @param src
     * @return
     */
    public static String convertString(String src)

    {
		JSONObject jsObj;

        char[] array = src.toCharArray();

        int temp = 0;
        int temp1 = 0;
        int temp2 = 0;
        for (int i = 0; i < array.length-1; i++)
        {
            temp = (int) array[i];
            temp1 = (int) array[i + 1];
            if(temp == 95 ){
                temp2++;
                if ( temp1 <= 122 && temp1 >= 97) {
                    array[i + 1] = (char) (temp1 - 32);
                }
            }
        }
       // System.out.println("temp2=="+temp2);
        delete(95,array);
        String sTemp = String.valueOf(array);
       // System.out.println("sTemp=="+sTemp);
       // System.out.println("sTemp=="+sTemp.length());
        String result =sTemp.substring(0, sTemp.length()-temp2);
        //print(array);
        return result;
    }

    public static void delete(int n,char[] a)// 删除数组中n的值
    {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == n) {
                for (int j = i; j < a.length - 1; j++) {
                    a[j] = a[j + 1];
                }
            }
        }
    }
}
