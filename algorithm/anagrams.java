/**
 * 判断两个字符串是否互为颠倒字符串
 * @author SouthWindYin
 * 思路1
 * str1记录每个字符出现频数，记入hashmap，str1取完之后，str2出现一个字符，在hashmap中减去1，若hashmap中有值减为-1，则return false
 * 开始时判断str1和2长度是否一样，若不一样，直接return false
 * 思路2
 * 将两个字符串拆为char array，调用Arrays.sort，将两个字符数组排序，接着把排好序的两个字符数组组成字符串，直接比较字符串是否相等即可
 * 
 */
public class Solution{	
	public static boolean anagrams(String str1,String str2) {
    	if((str1==null)||(str2==null))
    		return false;
    	if(str1.length()!=str2.length())
    		return false;
    	if(str1.equals(""))
    		return true;
    	
    	char[] str1chars = str1.toCharArray();
    	char[] str2chars = str2.toCharArray();
    	Arrays.sort(str1chars);
    	Arrays.sort(str2chars);
    	
    	String sortedStr1 = String.valueOf(str1chars);
    	String sortedStr2 = String.valueOf(str2chars);
    	
    	if(sortedStr1.equals(sortedStr2))
    		return true;
    	else return false;
    }
	
	public static main(String[] args){
		System.out.println(anagrams("asdasd","aassdd"));
	}
}