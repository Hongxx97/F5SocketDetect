package com.ist.common.properties;

import com.ist.common.util.Base64;

public  final class  MakeCode {
	public MakeCode(){
		
	}
	public  String   getLastLinessMess(String str){
    	String  returnstr="";
    	try{
        	  Base64 base=new Base64();
              returnstr=base.encodeBase64(str);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return returnstr;
    }
	
    public  String  getRealLinessMess(String returnstr){
    	try{
    	//base64Ω‚√‹
        Base64 base=new Base64();
        returnstr=base.decodeBase64(returnstr);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return returnstr;
    }
    

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MakeCode getlinessutils=new MakeCode();
		System.out.println("22::"+getlinessutils.getLastLinessMess("aml5"));
		System.out.println("33::"+getlinessutils.getRealLinessMess("YW1sNQ=="));
	}

}
