package com.ist.f5;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ist.common.LogUtils;
import com.ist.common.properties.SysConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class LocalDetectInfo2F5  implements Runnable{
	
	public static final String MAIN_SERVER_LOG_FILE = "log4j-comm.properties";
	static {
		PropertyConfigurator.configure(SysConfig.getSysHomeDir()
				+ File.separator + MAIN_SERVER_LOG_FILE);
	}
	/** ����������־ */
	private static Logger logger = LogUtils.getLogger(LocalDetectInfo2F5.class.getName());

	
	private String local_detect_port=null;
	
	private String curr_compare_type=null;
	
	private String constant_receivef5_result=null;
	private String constant_return2f5_succees=null;
	private String constant_return2f5_failed=null;
	
	private String shell_detect_script=null;
	private String shell_detect_keyresult=null;
	private String shell_return2f5_succ=null;
	private String shell_return2f5_failed=null;
	private String shell_detect_user=null;
	
	private Map final_user_key_sum=null;
 

	
	
	
	public LocalDetectInfo2F5() {
		 
		InputStream inStream = LocalDetectInfo2F5.class
		.getResourceAsStream("/config/f5/detectinfo2f5_config.properties");
		Properties properties = new Properties();
		try {
			properties.load(inStream);
			 local_detect_port = properties.getProperty("local_detect_port");
			 
			 curr_compare_type=properties.getProperty("curr_compare_type");
				
			 constant_receivef5_result=properties.getProperty("constant_receivef5_result");
			 constant_return2f5_succees=properties.getProperty("constant_return2f5_succees");
			 constant_return2f5_failed=properties.getProperty("constant_return2f5_failed");
				
	 
			 shell_detect_script=properties.getProperty("shell_detect_script");
			 shell_detect_keyresult=properties.getProperty("shell_detect_keyresult");
			 shell_return2f5_succ=properties.getProperty("shell_return2f5_succ");
			 shell_return2f5_failed=properties.getProperty("shell_return2f5_failed");
			 shell_detect_user=properties.getProperty("shell_detect_user");
			 
			 final_user_key_sum=new HashMap();
			 String [] users_key_sum_arry= shell_detect_keyresult.split(";");
			 
			 for ( int i=0;i<users_key_sum_arry.length;i++){
			
				 String [] user_key_sum_arry= users_key_sum_arry[i].split(",");
				 
			 
				    if(final_user_key_sum.get(user_key_sum_arry[0])==null){
				        Map	key_sum=new HashMap();
				        key_sum.put(user_key_sum_arry[1],user_key_sum_arry[2]);
				    	final_user_key_sum.put(user_key_sum_arry[0],key_sum);
				    }else{
				    	Map	key_sum=(Map)final_user_key_sum.get(user_key_sum_arry[0]);
				    	 if(key_sum.get(user_key_sum_arry[1])==null){
						        key_sum.put(user_key_sum_arry[1],user_key_sum_arry[2]);
						    }else{
						    	 key_sum.put(user_key_sum_arry[1],
						    			 Integer.valueOf(user_key_sum_arry[1])+Integer.valueOf(user_key_sum_arry[2]));
						    }
				    }
				 
			 }
			 
			 System.out.println("final_user_key_sum " +final_user_key_sum.toString()); 
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		 
		
	}

	
	public void run() {

		ServerSocket server = null;
		Socket soc = null;
		InputStream in = null;
		OutputStream out = null;

		int port = Integer.parseInt(local_detect_port);
		// �������ļ��ж�ȡ�����˿�  �Ӷ˿��ж�ȡ����
		try {
			server = new ServerSocket(port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}		
		while(true){
			
			try {

				System.out.println("׼���յ�������F5���ݣ�hahaha ");
				soc = server.accept();
				in = soc.getInputStream(); 
				//Thread.sleep(2000);
				
				logger.info("׼���յ�������F5���ݣ�"+ in.available());
				out = soc.getOutputStream();
				// 1.��ȡ������
				byte[] reqMsg = new byte[in.available()]; 
				in.read(reqMsg);
				String reqStr = new String(reqMsg,"UTF-8");
				System.out.println("�յ�������F5���ݣ�"+reqStr);
				logger.info("�յ�������F5���ݣ�"+reqStr);
			 
				String resStr = new String();
			 
				if(!"".equals(reqStr) && reqStr != null&&constant_receivef5_result.equals(reqStr)){
					
					if("1".equals(curr_compare_type)  ){
					    	//  ���͸�F5���ݣ�д����
						    System.out.println("���͸�F5���ݣ�"+constant_return2f5_succees);
							logger.info("���͸�F5���ݣ�"+constant_return2f5_succees);
			 
							out.write(constant_return2f5_succees.getBytes("UTF-8"));
							out.flush();
							System.out.println("���͸�F5���ݣ����");
							
							constant_return2f5_failed=null;
						
					}else if ("2".equals(curr_compare_type) ){
						//��Ȿ�ص�weblogic�����Ƿ���� �����ڷ���666666��F5,�����ڷ���999999��F5
						 System.out.println("shell������͸�F5��"+shell_detect_script);
						try {
						   Map finalresultmap=new HashMap();
						   boolean  finalresult=false;
						   Map final_detect_user_key_sum =	exec_shellscript(shell_detect_script);
						   
						   System.out.println("final_detect_user_key_sum ��"+final_detect_user_key_sum.toString());
						   //̽�⵽��Map�ͽ��map�Ƚ�,����ֵд��finalresult
						   Iterator final_user_key_sum_iterator=final_user_key_sum.keySet().iterator();
						   
						   // �ȽϿ�ʼ
						   while (final_user_key_sum_iterator.hasNext()){
							   
							   
							   String user=(String)final_user_key_sum_iterator.next();
							   if(final_detect_user_key_sum.get(user)!=null){
								   //��֪�ؼ���
								   Map final_key_sum=(Map) final_user_key_sum.get(user);
								   //�ռ����Ĺؼ���
								   Map detect_key_sum=(Map) final_detect_user_key_sum.get(user);
								   //������֪�ؼ��ֲ쿴�ռ����Ĺؼ���
								   Iterator final_key_sum_iterator=final_key_sum.keySet().iterator();
								   
								   while(final_key_sum_iterator.hasNext()){
									   String final_key_value=(String)final_key_sum_iterator.next();
									   String final_key_sum_value =(String)final_key_sum.get(final_key_value);
									  
									   if(  detect_key_sum.keySet().contains(final_key_value)){
										     String detect_key_sum_value= detect_key_sum.get(final_key_value)+"";
										     System.out.println("detect_key_sum_value ��"+final_key_value+"   "+ detect_key_sum_value);
										     System.out.println("final_key_sum_value ��"+final_key_value+"   "+ final_key_sum_value);
										     if(detect_key_sum_value.equals(final_key_sum_value)){
										    	 finalresultmap.put(user+":"+final_key_value,detect_key_sum_value+":"+"true");
										     }else{
										    	 finalresultmap.put(user+":"+final_key_value,detect_key_sum_value+":"+"false");
										     }
										     
									   }
								   }
								   
							   }else{
								   finalresultmap.put(user,user+":"+false);
								   //�û��µ�map �ȽϽ���
							   }
							   
							   
						   }
						 System.out.println("finalresultmap ��"+finalresultmap);
						 Iterator finalresultmap_iterator=finalresultmap.keySet().iterator();
						 while(finalresultmap_iterator.hasNext()){
							 String finalresult0 =(String)finalresultmap.get((String)finalresultmap_iterator.next());
							  System.out.println("shell  finalresult0��"+finalresult0);
							  System.out.println("lastIndexOf  finalresult0��"+finalresult0.lastIndexOf("true"));
							 if(finalresult0.lastIndexOf("true")>0){
								 finalresult=true;
							 }else{
								 finalresult=false;
								 break;
							 }
							 
						 }
						 
						 if(finalresult==true){
							    System.out.println("shell������͸�F5��"+shell_return2f5_succ);
								logger.info("shell������͸�F5��"+shell_return2f5_succ);
								out.write(shell_return2f5_succ.getBytes("UTF-8"));
								out.flush();
								System.out.println("shell������͸�F5�����");
						 }else {
							    System.out.println("shell��� ���͸�F5��"+shell_return2f5_failed);
								logger.info("shell������͸�F5���ݣ�"+shell_return2f5_failed);
								out.write(shell_return2f5_failed.getBytes("UTF-8"));
								out.flush();
								System.out.println("shell������͸�F5�����");
						 }
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						
					}else {
				    	//  ���͸�F5���ݣ�д����
					    System.out.println("Ĭ���ǳ������͸�F5���ݲ��ԣ�"+constant_return2f5_succees);
						logger.info("���͸�F5���ݣ�"+constant_return2f5_succees);
		 
						out.write(constant_return2f5_succees.getBytes("UTF-8"));
						out.flush();
						System.out.println("���͸�F5���ݲ��ԣ����");
						
					}
					
				}else {
					//��̽���ַ���
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				
			} finally{
				// �ر����������
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					in = null;
				}
				if(out != null){
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					out = null;
				}
			}
		}
	}
	
	public Map exec_shellscript(String execStr)
	    throws Exception
	  {
	    Runtime runtime = Runtime.getRuntime();
	    String line = "";
	    String ln = "";
	    Map final_detect_user_key_sum=new HashMap();
	    try {
	      logger.debug("execStr==" + execStr);
	      Process proc = runtime.exec(execStr);

	      BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	      
	      
	     
	      //ѭ��shell���
	      while ((line = input.readLine()) != null) {
	    	  logger.debug(line);
	    	  System.out.println("line " +line); 
	    	  String [] cmd_result_array=line.split(" ");

	        	// ���user�µĹؼ���map
	    	  if(final_detect_user_key_sum.get(cmd_result_array[0])==null){
	    		  System.out.println("final_detect_user_key_sumd û���û�  "   ); 
	    		Map user_key_sum=new HashMap();
	    		 // �����֪�µĹؼ���map
	    		 Map key_sum=(Map)final_user_key_sum.get(cmd_result_array[0]);
	    		 System.out.println("�����ļ���Ҫ���ҵ����йؼ��ָ���  " +final_user_key_sum  );
	    		 //
	    		Iterator key_sum_iterator=key_sum.keySet().iterator() ;
	    	     
	    		 System.out.println(cmd_result_array[0]+"�û��������ļ���Ҫ���ҵĹؼ��ָ��� " +key_sum   ); 
	    		 // �����û��µĹؼ���
	    		  while (key_sum_iterator.hasNext()){
	    			String key= (String) key_sum_iterator.next();
	    			int sum=lookkeyfromcmdline(cmd_result_array[3],key);
	    			System.out.println("������"+cmd_result_array[3]+"��"+key+"�ؼ��ָ���  " +sum  ); 
	    			user_key_sum.put(key,sum);
	    		  }
	    		  final_detect_user_key_sum.put(cmd_result_array[0],user_key_sum);
	    		  
	    	 
	    	  }else{
	    		  
	    		  System.out.println("final_detect_user_key_sumd  ���û�  "   ); 
	    		  Map user_key_sum=(Map)final_detect_user_key_sum.get(cmd_result_array[0]);
	    		  
	    		  System.out.println("�����ļ���Ҫ���ҵ����йؼ��ָ���  " +final_user_key_sum  );
	    		  // �����֪�µĹؼ���map
		    	  Map key_sum=(Map)final_user_key_sum.get(cmd_result_array[0]);
		    	  
		    	  //
		    	  Iterator key_sum_iterator=key_sum.keySet().iterator();
		    	     
		    	  System.out.println(cmd_result_array[0]+"�û��������ļ���Ҫ���ҵĹؼ��ָ��� " +key_sum   );  
		    	  
		    	  while (key_sum_iterator.hasNext() ){
		    			String key= (String)key_sum_iterator.next();
		    			int sum=lookkeyfromcmdline(cmd_result_array[3],key);
		    			System.out.println("������"+cmd_result_array[3]+"��"+key+"�ؼ��ָ���  " +sum  ); 
		    			if(user_key_sum.get(key)!=null){
		    				System.out.println(cmd_result_array[0]+"�û����Ѿ����ҵ��Ĺؼ��ָ��� " +user_key_sum.get(key) ); 
		    				sum=sum+Integer.valueOf((String) user_key_sum.get(key));
		    			} 
		    			user_key_sum.put(key,sum);
		    			System.out.println(cmd_result_array[0]+"�û����Ѿ����ҵ��Ĺؼ��ָ��������� " +user_key_sum.get(key) );
		    	  }
		    	     
	    	  }
	    	  
	         }
           
	      input.close();
	      System.out.println("shell��� �������final_detect_user_key_sum" +final_detect_user_key_sum); 
	      BufferedReader error = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
	      if ((ln = error.readLine()) != null) {
	        logger.error(ln);
	        while ((ln = error.readLine()) != null) {
	          logger.error(ln);
	        }
	        error.close();
	        throw new Exception();
	      }
	     // retVal = true;
	    } catch (Exception e) {
	      throw e;
	    }
	     return final_detect_user_key_sum;
	  }
	


	public static int lookkeyfromcmdline(String cmdline,String key,int index){
		int key_sum=0;
		if(cmdline.substring(index).indexOf(key)>=0){
			index=key.length()+cmdline.substring(index).indexOf(key)+index;
			key_sum=1+lookkeyfromcmdline( cmdline, key, index);
		}
		return key_sum;
	}
	
	public static int lookkeyfromcmdline(String cmdline,String key){
		int key_sum=0;
		int nextindex=0;
		while(cmdline.substring(nextindex)!=null && !"".equals(cmdline.substring(nextindex))){
			
			if(cmdline.substring(nextindex).indexOf(key)>=0){
				key_sum=key_sum+1;
			}else{
				break;
			}
			nextindex=nextindex+key.length()+cmdline.substring(nextindex).indexOf(key);
		} 
		
		return key_sum;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LocalDetectInfo2F5 th = new LocalDetectInfo2F5();
		new Thread(th).start();
	}

}
