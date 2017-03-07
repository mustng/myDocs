public class HashObject {
	public String key;
	public int value;
	private int format;
	
	public HashObject(String k, int v, int f){
		format = f;
		key = k;
		value = v;
		
	}
	
	public int getFormat(){
		return format;
	}
	
}
