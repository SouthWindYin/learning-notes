/**
 * �ж������ַ����Ƿ�Ϊ�ߵ��ַ���
 * @author SouthWindYin
 * ˼·1
 * str1��¼ÿ���ַ�����Ƶ��������hashmap��str1ȡ��֮��str2����һ���ַ�����hashmap�м�ȥ1����hashmap����ֵ��Ϊ-1����return false
 * ��ʼʱ�ж�str1��2�����Ƿ�һ��������һ����ֱ��return false
 * ˼·2
 * �������ַ�����Ϊchar array������Arrays.sort���������ַ��������򣬽��Ű��ź���������ַ���������ַ�����ֱ�ӱȽ��ַ����Ƿ���ȼ���
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